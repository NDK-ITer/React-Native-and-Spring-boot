import { UserContext } from "../../../../contexts/UserContext"
import { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const UserInformation = () => {
    const { user } = useContext(UserContext)
    const navigate = useNavigate();

    useEffect(() => {
        if (!user) {
            navigate(`/auth/sign-in`)
        }
    })
    return (<>
        
    </>)
}
export default UserInformation