import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom'
import LogoutComponent from './LogoutComponent'
import HeaderComponent from './HeaderComponent'
import ListProductsComponent from './ListProductsComponent'
import WelcomeComponent from './WelcomeComponent'
import LoginComponent from './LoginComponent'
import ProductComponent from './ProductComponent'
import AppHealthMonitor from './AppHealthMonitor'


import AuthProvider, { useAuth } from './security/AuthContext'

import './JustOrderApp.css'

function AuthenticatedRoute({children}) {
    const authContext = useAuth()
    
    if(authContext.isAuthenticated)
        return children

    return <Navigate to="/" />
}

function UnAuthenticatedRoute({children}) {
    const authContext = useAuth()
    
    if(!authContext.isAuthenticated)
        return children
    
// return <Navigate to="/welcome/username" />
return <Navigate to={`/welcome/${authContext.username}`} />;

}

export default function JustOrderApp() {

    return (
        <div className="container">
            <AuthProvider>
                <BrowserRouter>
                    <HeaderComponent />
                    <Routes>
                        <Route path='/' element={
                            <UnAuthenticatedRoute>
                                <LoginComponent />
                                <AppHealthMonitor/>
                            </UnAuthenticatedRoute> 
                        } />
                        <Route path='/login' element={
                            <UnAuthenticatedRoute>
                                <LoginComponent />
                                <AppHealthMonitor/>
                            </UnAuthenticatedRoute> 
                        } />
                        <Route path='/welcome' element={
                            <AuthenticatedRoute>
                                <WelcomeComponent />
                                <AppHealthMonitor/>                                
                            </AuthenticatedRoute> 
                        } />                          
                        <Route path='/welcome/' element={
                            <AuthenticatedRoute>
                                <WelcomeComponent />
                                <AppHealthMonitor/>                                
                            </AuthenticatedRoute> 
                        } />                        
                        <Route path='/welcome/:username' element={
                            <AuthenticatedRoute>
                                <WelcomeComponent />
                                <AppHealthMonitor/>
                            </AuthenticatedRoute> 
                        } />
                        
                        <Route path='/products' element={
                            <AuthenticatedRoute>
                                <ListProductsComponent /> 
                                <AppHealthMonitor/>                                
                            </AuthenticatedRoute>
                        } />
                        <Route path='/products/:id' element={
                            <AuthenticatedRoute>
                                <ProductComponent /> 
                            </AuthenticatedRoute>
                        } />

                        <Route path='/logout' element={
                            <AuthenticatedRoute>
                                <LogoutComponent /> 
                                <AppHealthMonitor/>
                            </AuthenticatedRoute>
                        } />
                        
                        <Route path="*" element={<Navigate to="/" replace />} />

                    </Routes>
                </BrowserRouter>
            </AuthProvider>
        </div>
    )
}
