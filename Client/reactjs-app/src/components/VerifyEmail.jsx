import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { VerifyEmail as verifyEmail } from "../services/apis/AuthAPI";
import background from "../assets/images/verify-email-background.png";
import { Player } from '@lottiefiles/react-lottie-player';
import SuccessfulVerify from "../assets/animation/verify-email-successful-animation.json";
import LoadingVerify from "../assets/animation/verify-email-loading-animation.json";
import FailVerify from "../assets/animation/verify-email-fail-animation.json";

const VerifyEmail = () => {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const token = queryParams.get('token');
    const [verifyEmailSuccessful, setVerifyEmailSuccessful] = useState(false)
    const [isLoading, setIsLoading] = useState(true)
    const [response, setResponse] = useState(undefined)

    const getVerifyEmail = async () => {
        setIsLoading(true)
        try {
            const res = await verifyEmail({
                token: token,
            });
            if (res.state == 1) {
                setVerifyEmailSuccessful(true)
            } else {
                setVerifyEmailSuccessful(false)
            }
            setResponse(res)
        } catch (error) {

        } finally {
            setIsLoading(false)
        }
    }

    useEffect(() => {
        if (token) {
            getVerifyEmail()
        } else {
            setIsLoading(false)
            setVerifyEmailSuccessful(false)
        }
    }, [])
    return (<>
        <div style={{
            display: "flex",
            minWidth: "100vw",
            minHeight: "100vh",
            backgroundImage: `url(${background})`,
            backgroundSize: "cover",
            backgroundRepeat: "no-repeat",
            backgroundPosition: "center",
        }}>
            <div style={{
                margin: "auto",
                borderRadius: "10%",
                backgroundColor: "#FFFFFFBE",
                height: "80vh",
                minWidth: "300px",
                width: "50%",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
            }}>
                {isLoading &&
                    <div>
                        <Player
                            autoplay
                            loop
                            src={LoadingVerify}
                        >
                        </Player>
                        <h2 style={{
                            textAlign: "center",
                            fontWeight: "bolder",
                            color: "yellow",
                        }}>
                            Processing...
                        </h2>
                    </div>
                }
                {!isLoading && verifyEmailSuccessful &&
                    <div>
                        <Player
                            autoplay
                            loop
                            src={SuccessfulVerify}
                        >
                        </Player>
                        <h2 style={{
                            textAlign: "center",
                            fontWeight: "bolder",
                            color: "green",
                        }}>
                            {response && response.mess}
                        </h2>
                    </div>
                }
                {!isLoading && !verifyEmailSuccessful &&
                    <div>
                        <Player
                            autoplay
                            loop
                            src={FailVerify}
                        >
                        </Player>
                        <h2 style={{
                            textAlign: "center",
                            fontWeight: "bolder",
                            color: "red",
                        }}>
                            {response && response.mess}
                        </h2>
                    </div>
                }
            </div>
        </div>
    </>)
}

export default VerifyEmail