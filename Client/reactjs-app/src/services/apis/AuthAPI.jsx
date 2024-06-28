import Root from '../Root';

const controller = 'auth'

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

export {
    Login,
    Register
}