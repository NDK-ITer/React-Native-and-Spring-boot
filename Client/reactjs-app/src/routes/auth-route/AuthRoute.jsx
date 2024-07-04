import { Routes, Route } from "react-router-dom"; 
import SignIn from "../../components/authenticate-compt/SignIn";
import SignUp from "../../components/authenticate-compt/SignUp";
import ForgotPassword from "../../components/authenticate-compt/ForgotPassword";
import ResetPassword from "../../components/authenticate-compt/ResetPassword";

const AuthRoute = () => {
    return (<>
        <Routes>
            <Route path='/sign-in' element={<SignIn/>}/>
            <Route path='/sign-up' element={<SignUp/>}/>
            <Route path='/forgot-password' element={<ForgotPassword/>} />
            <Route path='/reset-password' element={<ResetPassword/>} />
        </Routes>
    </>)
}

export default AuthRoute