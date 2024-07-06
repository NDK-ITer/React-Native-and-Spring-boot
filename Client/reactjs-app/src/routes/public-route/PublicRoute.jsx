import { Routes, Route } from "react-router-dom"; 
import UserPage from "../../pages/UserPage";

const PublicRoute = () => {
    return (<>
        <Routes>
            <Route path='/user/*' element={<UserPage/>} />
        </Routes>
    </>)
}

export default PublicRoute