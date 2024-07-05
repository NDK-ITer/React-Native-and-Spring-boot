import { useState } from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { Register } from '../../services/apis/AuthAPI';
import { toast } from 'react-toastify';
import CircularProgress from '@mui/material/CircularProgress';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';

const SignUp = () => {
    const [email, setEmail] = useState('')
    const [displayName, setDisplayName] = useState('');
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [isLoading, setLoading] = useState(false);

    const registerUser = async () => {
        if (confirmPassword != password) {
            return;
        } else {
            setLoading(true);
            try {
                const res = await Register({
                    email: email,
                    displayName: displayName,
                    firstName: firstName,
                    lastName: lastName,
                    password: password,
                    dob: new Date().toISOString(),
                })
                if (res.state == 1) {
                    toast.success(res.mess);
                }
                else {
                    toast.warning(res.mess);
                }
            } catch (error) {
                toast.error(error);
            } finally {
                setLoading(false);
            }
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
            }}
        >
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
                Sign up
            </Typography>
            <Box component="form" noValidate sx={{ mt: 3 }}>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            required
                            fullWidth
                            name="display-name"
                            label="Display Name"
                            type="text"
                            id="display-name"
                            value={displayName}
                            onChange={(e) => setDisplayName(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            autoComplete="given-name"
                            name="firstName"
                            required
                            fullWidth
                            id="firstName"
                            label="First Name"
                            autoFocus
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            required
                            fullWidth
                            id="lastName"
                            label="Last Name"
                            name="lastName"
                            autoComplete="family-name"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            required
                            fullWidth
                            id="email"
                            label="Email Address"
                            name="email"
                            autoComplete="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="new-password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            required
                            fullWidth
                            name="confirm-password"
                            label="Confirm Password"
                            type="password"
                            id="confirm-password"
                            autoComplete="new-password"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                        />
                        <div>
                            {(password !== confirmPassword) && (
                                <>
                                    <Typography color="error" sx={{ mt: 1 }}>
                                        * password is not confirm
                                    </Typography>
                                </>
                            )}
                        </div>
                    </Grid>
                </Grid>
                <Button
                    fullWidth
                    variant="contained"
                    sx={{ mt: 3, mb: 2 }}
                    onClick={async () => await registerUser()}
                >
                    {isLoading ? <>
                        <CircularProgress color='inherit' size={25} />
                    </> : <>
                        <KeyboardArrowUpIcon />
                    </>}Sign Up
                </Button>
                <Grid container justifyContent="flex-end">
                    <Grid item xs>
                        <Link to="/auth/forgot-password" variant="body2" className='link-no-decoration'>
                            Forgot password?
                        </Link>
                    </Grid>
                    <Grid item>
                        <Link to="/auth/sign-in" variant="body2" className='link-no-decoration'>
                            Already have an account? Sign in
                        </Link>
                    </Grid>
                </Grid>
            </Box>
        </Box>
    </>);
}
export default SignUp