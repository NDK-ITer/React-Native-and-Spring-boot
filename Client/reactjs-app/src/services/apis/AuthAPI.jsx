import Root from '../Root';
import { format, parseISO } from 'date-fns';

const rootUrlClient = `${window.location.protocol}//${window.location.hostname}:${window.location.port}`;
const controller = 'authenticate'

const Login = (props) => {
    return Root.post(`/api/${controller}/sign-in`, {
        email: props.email,
        password: props.password,
    })
}

const Register = (props) => {
    const parsedDate = parseISO(props.dob.toString());
    const formattedDate = format(parsedDate, "yyyy-MM-dd'T'HH:mm:ss");
    return Root.post(`/api/${controller}/sign-up`, {
        email: props.email,
        displayName: props.displayName,
        firstName: props.firstName,
        lastName: props.lastName,
        password: props.password,
        clientURL: `${rootUrlClient}/verify-email?token=`,
        dob: formattedDate
    })
}

const ForgotPassword = (props) => {
    return Root.post(`/api/${controller}/forgot-password`, {
        email: props.email,
    })
}

const VerifyOTP = (props) => {
    return Root.post(`/api/${controller}/verify-otp`, {
        otp: props.otp,
    })
}

const ResetPassword = (props) => {
    return Root.post(`/api/${controller}/reset-password?tokenResetPassword=${props.token}`, {
        newPassword: props.newPassword
    })
} 

const VerifyEmail = (props) => {
    return Root.get(`/api/${controller}/verify-email?tokenVerifyEmail=${props.token}`)
}

export {
    Login,
    Register,
    VerifyOTP,
    ForgotPassword,
    ResetPassword,
    VerifyEmail
}