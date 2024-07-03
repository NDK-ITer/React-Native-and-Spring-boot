import { Routes, Route } from "react-router-dom";
import AuthPage from "../pages/AuthPage";
import HomePage from "../pages/HomePage";

const AppRoute = () => {
    return (<>
        <Routes>
            <Route path='/auth/*' element={<AuthPage/>} />
            <Route path='/' element={<HomePage/>}/>
        </Routes>
    </>)
}
export default AppRoute;