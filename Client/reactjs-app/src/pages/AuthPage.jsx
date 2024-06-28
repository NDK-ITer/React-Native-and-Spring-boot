import { Container } from "react-bootstrap"
import AuthRoute from "../routes/auth/AuthRoute"

const AuthPage = () => {
    return (<>
        <Container>
            <AuthRoute />
        </Container>
    </>)
}

export default AuthPage