import {NavLink} from 'react-router-dom'
import { useAuth } from './security/AuthContext'

function HeaderComponent() {

    const authContext = useAuth()
    const isAuthenticated = authContext.isAuthenticated

    function logout() {
        authContext.logout()
    }

    return (
        
        <header className="border-bottom border-light border-5 mb-5 p-2">
            <div className="container">
                <div className="row">
                    <nav className="navbar navbar-expand-sm navbar-light bg-light">
                        <a className="navbar-brand ms-3 fs-3 fw-bold text-black" href="https://github.com/haihongren/instrumentation_workshop"> 
                        <img src="/just-order-app-logo.png" alt="Just Order App Logo" className="me-2 rounded-circle" /> workshop@github</a>
                        <div className="collapse navbar-collapse">
                            <ul className="navbar-nav">
                                <li className="nav-item">
    
                                   {isAuthenticated && <NavLink className="nav-link" to={`/welcome/${authContext.username}`}>Home</NavLink>}
 
                                </li>
                                <li className="nav-item">
                                    {isAuthenticated 
                                            && <NavLink className="nav-link" to="/products">Order Phone</NavLink>}                                    
                                </li>
                            </ul>
                        </div>
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                {!isAuthenticated &&
                                    <NavLink className="nav-link" to="/login">Login</NavLink> }
                            </li>
                            <li className="nav-item">
                                {isAuthenticated &&
                                    <NavLink className="nav-link" to="/logout" onClick={logout}>Logout</NavLink>}
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </header>

    )
}

export default HeaderComponent