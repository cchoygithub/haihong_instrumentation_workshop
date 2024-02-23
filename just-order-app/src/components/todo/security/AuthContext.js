import React, { createContext, useContext, useState, useEffect } from "react";
import { apiClient } from "../api/ApiClient";


import { executeBasicAuthenticationService } from "../api/AuthenticationApiService";

export const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export default function AuthProvider({ children }) {
    const [isAuthenticated, setAuthenticated] = useState(false);
    const [username, setUsername] = useState(null);
    const [token, setToken] = useState(null);

    async function login(username, password) {
        const baToken = 'Basic ' + window.btoa(username + ":" + password);

        try {
            const response = await executeBasicAuthenticationService(baToken);

            if (response.status === 200) {
                setAuthenticated(true);
                setUsername(username);
                setToken(baToken);
     
                // Save token to local storage
                localStorage.setItem('baToken', baToken);
                localStorage.setItem('baTokenusername', username);        
                configureAxios(baToken,username); 
                return true;
            } else {
                logout();
                return false;
            }
        } catch (error) {
            logout();
            return false;
        }
    }

    function logout() {
        setAuthenticated(false);
        setToken(null);
        setUsername(null);

        // Remove token from local storage on logout
        localStorage.removeItem('baToken');
        localStorage.removeItem('baTokenusername');
        configureAxios(null,null); 
    }

    // Check if token exists in local storage on component mount
    useEffect(() => {
        const tokenFromStorage = localStorage.getItem('baToken');
        const usernameFromStorage = localStorage.getItem('baTokenusername');
        console.log("tokenFromStorage",tokenFromStorage)
        console.log("usernameFromStorage",usernameFromStorage)    
          
        if (tokenFromStorage) {
            setAuthenticated(true);
            setToken(tokenFromStorage);            
            setUsername(usernameFromStorage);

            configureAxios(tokenFromStorage,usernameFromStorage);             

        }
    }, []);

    const configureAxios = (token,username) => {
        if (token) {
          apiClient.defaults.headers.common['Authorization'] =  token;
          apiClient.defaults.headers.common['workshopusername'] = username;          
        } else {
          delete apiClient.defaults.headers.common['Authorization'];
          delete apiClient.defaults.headers.common['workshopusername'];
        }
      };
    

    return (
        <AuthContext.Provider value={{ isAuthenticated, login, logout, username, token }}>
            {children}
        </AuthContext.Provider>
    );
}
