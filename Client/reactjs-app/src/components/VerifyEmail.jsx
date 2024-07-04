import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { VerifyEmail as verifyEmail } from "../services/apis/AuthAPI";

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
            console.log(res)
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
            console.log(token);
            getVerifyEmail()
        } else {
            setIsLoading(false)
            setVerifyEmailSuccessful(false)
        }
    }, [])
    return (<>
        {isLoading && <div>Processing {response && response.mess}</div>}
        {!isLoading && verifyEmailSuccessful && <div>Successful {response && response.mess}</div>}
        {!isLoading && !verifyEmailSuccessful && <div>Fail {response && response.mess}</div>}
    </>)
}

export default VerifyEmail