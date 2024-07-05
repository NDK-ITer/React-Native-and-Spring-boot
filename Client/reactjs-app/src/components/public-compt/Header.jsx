import { useContext } from 'react';
import { UserContext } from '../../contexts/UserContext';

import AuthHeader from './header-compt/AuthHeader';
import UserHeader from './header-compt/UserHeader';

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
                    height: "100%",
                }}
            >
                {user ? <>
                    <UserHeader/>
                </> : <>
                    <AuthHeader/>
                </>}
            </div>
        </div>
    </>)
}

export default Header