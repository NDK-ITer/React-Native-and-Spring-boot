import { UserContext } from '../contexts/UserContext';
import { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
/**
 * 
 * mandatory identify
 */
const UserPage = () => {
    const {user} = useContext(UserContext);
    const navigate = useNavigate();

    useEffect(() => {
        if (user) {
            navigate(`/auth/sign-in`)
        }
    })
    return (<>
    </>)
}
export default UserPage