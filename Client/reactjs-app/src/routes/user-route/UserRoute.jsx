import { Routes, Route } from "react-router-dom";
import UserProfile from "../../components/public-compt/body-compt/user-compt/UserProfile";

const UserRoute = () => {
    return (<>
        <Routes>
            <Route path='/my-information' element={<UserProfile/>} />
        </Routes>
    </>)
}

export default UserRoute