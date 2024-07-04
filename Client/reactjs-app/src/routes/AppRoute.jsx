import { Routes, Route } from "react-router-dom";
import AuthPage from "../pages/AuthPage";
import PublicPage from "../pages/PublicPage";
import VerifyEmail from "../components/VerifyEmail";

const AppRoute = () => {
    return (<>
        <Routes>
            <Route path='/auth/*' element={<AuthPage/>} />
            <Route path='/' element={<PublicPage/>}/>
            <Route path='/verify-email' element={<VerifyEmail/>}/>
        </Routes>
    </>)
}
export default AppRoute;