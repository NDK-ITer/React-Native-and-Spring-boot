import { Routes, Route } from "react-router-dom";
import AuthPage from "../pages/AuthPage";

const AppRoute = () => {
    return (<>
        <Routes>
            <Route path='/auth/*' element={<AuthPage/>} />
            <Route />
        </Routes>
    </>)
}
export default AppRoute;