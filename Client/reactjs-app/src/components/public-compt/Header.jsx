import Avatar from '@mui/material/Avatar';
import { useContext } from 'react';
import { UserContext } from '../../contexts/UserContext';
import { Link } from 'react-router-dom';

const Header = () => {
    const { user } = useContext(UserContext);
    return (<>
        <div className="header-public-container">
            <div
                className="element-header"
                style={{
                    width: "25%",
                }}
            >
                logo
            </div>
            <div
                className="element-header"
                style={{
                    width: "60%",
                }}
            >
                nope
            </div>
            <div
                className="element-header"
                style={{
                    width: "15%",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                }}
            >
                {user ? <>
                    <Avatar
                        src={user.avatar}
                        sx={{
                            maxWidth: 54, maxHeight: 54,
                            minWidth: 54, minHeight: 54,
                        }}
                    />
                </> : <>
                    <Link to="/auth/sign-in" className='.link-no-decoration'>Login</Link>
                    <Link to="/auth/sign-up" className='.link-no-decoration'>Register</Link>
                </>}
            </div>
        </div>
    </>)
}

export default Header