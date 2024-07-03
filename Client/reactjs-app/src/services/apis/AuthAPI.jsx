import Root from '../Root';

const controller = 'authenticate'

const Login = (props) => {
    return Root.post(`/api/${controller}/sign-in`, {
        email: props.email,
        password: props.password,
    })
}

const Register = (props) => {
    return Root.post(`/api/${controller}/sign-up`, {
        email: props.email,
        displayName: props.displayName,
        firstName: props.firstName,
        lastName: props.lastName,
        password: props.password,
        dob: props.dob.toString()
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

export {
    Login,
    Register,
    VerifyOTP,
    ForgotPassword,
    ResetPassword
}