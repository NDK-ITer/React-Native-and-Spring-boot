import { Routes, Route } from "react-router-dom";
import UserInformation from "../../components/public-compt/body-compt/user-compt/UserInformation";

const UserRoute = () => {
    return (<>
        <Routes>
            <Route path='/my-information' element={<UserInformation/>} />
        </Routes>
    </>)
}

export default UserRoute