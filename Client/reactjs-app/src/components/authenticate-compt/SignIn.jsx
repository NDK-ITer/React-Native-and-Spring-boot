import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { useContext, useState } from 'react';
import { toast } from 'react-toastify';
import { Login } from '../../services/apis/AuthAPI';
import { UserContext } from '../../contexts/UserContext';


const SignIn = () => {

    const { login } = useContext(UserContext);
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('');

    const loginPost = async () => {
        try {
            const res = await Login({
                email: email,
                password: password,
            })
            if (res.state == 1) {
                login(res.data);
            } else if (res.state == 0) {
                toast.warning(res.mess);
            }
        } catch (error) {
            toast.error(error);
        }
    }

    return (<>

        <CssBaseline />
        <Box
            sx={{
                margin: "auto",
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                backgroundColor: '#DDDDDDAF',
                padding: 5,
                borderRadius: 2,
                boxShadow: 12,
                maxWidth: "636.52px",
                minHeight: "636.52px",
            }}
        >
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main', marginTop:'10%' }}>
                <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
                Sign in
            </Typography>
            <Box component="form" noValidate sx={{ mt: 1 }}>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="email"
                    label="Email Address"
                    name="email"
                    autoComplete="email"
                    autoFocus
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <Button
                    onClick={async () => await loginPost()}
                    fullWidth
                    variant="contained"
                    sx={{ mt: 3, mb: 2 }}
                >
                    Sign In
                </Button>
                <Grid container>
                    <Grid item xs>
                        <Link to="/auth/forgot-password" variant="body2" className='link-no-decoration'>
                            Forgot password?
                        </Link>
                    </Grid>
                    <Grid item>
                        <Link to="/auth/sign-up" variant="body2" className='link-no-decoration'>
                            Don't have an account? Sign Up
                        </Link>
                    </Grid>
                </Grid>
            </Box>
        </Box>
    </>);
}
export default SignIn