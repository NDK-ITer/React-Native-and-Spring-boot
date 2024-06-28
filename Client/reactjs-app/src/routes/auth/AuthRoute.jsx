import { Routes, Route } from "react-router-dom"; 
import SignIn from "../../components/admin-compt/SignIn";
import SignUp from "../../components/admin-compt/SignUp";

const AuthRoute = () => {
    return (<>
        <Routes>
            <Route path='/sign-in' element={<SignIn/>}/>
            <Route path='/sign-up' element={<SignUp/>}/>
        </Routes>
    </>)
}

export default AuthRoute