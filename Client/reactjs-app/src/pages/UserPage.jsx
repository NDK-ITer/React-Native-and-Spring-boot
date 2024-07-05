import { UserContext } from '../contexts/UserContext';
import { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import UserRoute from '../routes/user-route/UserRoute';
/**
 * 
 * mandatory identify
 */
const UserPage = () => {
    const { user } = useContext(UserContext);
    const navigate = useNavigate();

    useEffect(() => {
        if (!user) {
            navigate(`/auth/sign-in`)
        }
    })
    return (<>
        <UserRoute />
    </>)
}
export default UserPage