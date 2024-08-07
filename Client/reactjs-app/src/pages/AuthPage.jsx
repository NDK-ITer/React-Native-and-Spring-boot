
import { Container } from 'react-bootstrap';
import AuthRoute from "../routes/auth-route/AuthRoute"
import background from "../assets/images/auth-background.png";
import { useContext, useEffect } from 'react';
import { UserContext } from '../contexts/UserContext';
import { useNavigate } from 'react-router-dom';

const AuthPage = () => {
    const { user } = useContext(UserContext)
    const navigate = useNavigate()
    useEffect(() => {
        if (user) {
            navigate(-1);
        }
    })
    return (<>
        <div style={{
            backgroundImage: `url(${background})`,
            backgroundSize: "cover",
            backgroundRepeat: "no-repeat",
            backgroundPosition: "center",
        }}>
            <Container style={{
                display: "flex",
                minHeight: "100vh",
            }}>
                <AuthRoute />
            </Container>
        </div>
    </>)
}

export default AuthPage