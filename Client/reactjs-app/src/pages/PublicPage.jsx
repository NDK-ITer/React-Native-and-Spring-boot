import { Container } from "react-bootstrap"
import background from "../assets/images/public-background.png"
import PublicRoute from '../routes/public-route/PublicRoute'
import Header from "../components/public-compt/Header"
import "../assets/PublicPage.css"
import Body from "../components/public-compt/Body"
/**
 * 
 * option identify
 */
const PublicPage = () => {
    return (<>
        <div
            style={{
                backgroundImage: `url(${background})`,
                backgroundSize: "cover",
                backgroundRepeat: "no-repeat",
                backgroundPosition: "center",
            }}
        >
            <Container style={{
                display: "flex",
                flexDirection: "column",
                minHeight: "100vh",
                maxWidth: "100vw",
                padding: 0,
            }} className="custom-container">
                <Header />
                <Body>
                    <PublicRoute />
                </Body>
            </Container>
        </div>
    </>)
}

export default PublicPage