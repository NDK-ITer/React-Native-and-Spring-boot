
import { Container } from 'react-bootstrap';
import AuthRoute from "../routes/auth/AuthRoute"
import background  from "../assets/images/auth-background.png";
const AuthPage = () => {
    return (<>
        <Container style={{
            display: "flex",
            minHeight: "100vh",
            backgroundImage: `url(${background})`,
            backgroundSize: "cover",
            backgroundRepeat: "no-repeat",
            backgroundPosition: "center",
        }}>
            <AuthRoute />
        </Container>
    </>)
}

export default AuthPage