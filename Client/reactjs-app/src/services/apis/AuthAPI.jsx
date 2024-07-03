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
        email: props.Email,
    })
}

const VerifyOTP = (props) => {
    return Root.post(`/api/${controller}/verify-otp`, {
        otp: props.OTP,
    })
}

export {
    Login,
    Register,
    VerifyOTP,
    ForgotPassword
}