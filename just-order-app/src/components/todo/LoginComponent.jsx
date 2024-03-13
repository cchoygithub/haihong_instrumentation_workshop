import { useState } from 'react'
import {useNavigate} from 'react-router-dom'
import { useAuth } from './security/AuthContext'

function LoginComponent() {

    const [username, setUsername] = useState('nruser')

    const [password, setPassword] = useState('abc123')

    const [showErrorMessage, setShowErrorMessage] = useState(false)

    const navigate = useNavigate()

    const authContext = useAuth()


    function handleUsernameChange(event) {
        setUsername(event.target.value)
    }

    function handlePasswordChange(event) {
        setPassword(event.target.value)
    }

    async function handleSubmit() {
        if(await authContext.login(username, password)){
            navigate(`/welcome/${username}`)
        } else {
            setShowErrorMessage(true)
        }
    }

    return (
        <div className="Login">
            <h1 className="bordered-text">login to order</h1>
            {showErrorMessage && <div className="errorMessage">Authentication Failed. 
                                                            Please check your credentials.</div>}
            <div className="LoginForm">
                <div>
                    <label className="input-label" >UserName</label>
                    <input className='wide-input' type="text" name="username" value={username} onChange={handleUsernameChange}/>
                </div>
                <div>
                    <label className="input-label">Password</label>
                    <input className='wide-input' type="password" name="password" value={password} onChange={handlePasswordChange}/>
                </div>
                <div>
                    <button type="button" className="btn btn-info btn-lg wider-button" name="login" onClick={handleSubmit}>login</button>
                </div>
            </div>
        </div>  
    )
}

export default LoginComponent