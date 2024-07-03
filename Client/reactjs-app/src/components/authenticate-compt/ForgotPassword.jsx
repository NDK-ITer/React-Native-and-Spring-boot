import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { useState } from 'react';
import { ForgotPassword as forgotPass, VerifyOTP } from '../../services/apis/AuthAPI';
import { toast } from 'react-toastify';
import { Grid } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import CircularProgress from '@mui/material/CircularProgress';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import LoopIcon from '@mui/icons-material/Loop';
import * as React from 'react-bootstrap';
import CountdownTimer from '../common-compt/CountdownTimer';

const ForgotPassword = () => {
    const [email, setEmail] = useState('');
    const [otp, setOTP] = useState('')
    const [isLoading, setLoading] = useState(false);
    const [isVerifyOTP, setIsVerifyOTP] = useState(false);
    const [timeOut, setTimeOut] = useState();
    const [enableGetOTP, setEnableGetOTP] = useState(false)

    const nag = useNavigate()

    const getOTP = async () => {
        setLoading(true);
        try {
            const res = await forgotPass({ email: email });
            if (res.state == 1) {
                toast.success(res.mess);
                setTimeOut(parseInt(res.data.time, 10));
                setIsVerifyOTP(true);
            } else {
                toast.warning(res.mess);
            }
        } catch (error) {
            toast.error(error);
        } finally {
            setLoading(false);
        }
    }

    const verifyOTP = async () => {
        setLoading(true);
        try {
            const res = await VerifyOTP({ otp: otp });
            if (res.state == 1) {
                const token = res.data.tokenResetPassword;
                nag(`/auth/reset-password?token=${token}`)
            } else {
                toast.warning(res.mess);
            }
        } catch (error) {
            toast.error(error);
        } finally {
            setLoading(false);
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
                padding: 2,
                borderRadius: 2,
                boxShadow: 12,
                maxWidth: "636.52px",
                width: "636.52px",
                minHeight: "636.52px",
            }}
        >
            <Avatar sx={{ m: 5, bgcolor: 'secondary.main', marginTop: '10%' }}>
                <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
                {isVerifyOTP ? "Verify OTP" : "Forgot Password"}
            </Typography>
            <div style={{ marginTop: '5%' }}>
                {isVerifyOTP ? <>
                    <div>
                        {email}
                    </div>
                </> : <>
                    <div>
                        Input your email to get OTP
                    </div>
                </>}
            </div>
            <Box component="form" noValidate sx={{ mt: 5 }}>
                {isVerifyOTP ? <>

                    <TextField
                        margin="normal"
                        required
                        label="OTP"
                        value={otp}
                        onChange={(e) => setOTP(e.target.value)}
                        style={{
                            maxWidth: "86%",
                            minWidth: "84%",
                            width: "auto",
                        }}
                    />
                    <React.Button
                        disabled={!enableGetOTP}
                        onClick={() => { getOTP(); setEnableGetOTP(false) }}
                        variant="outline-primary"
                        style={{
                            marginTop: "4%",
                            fontWeight: "bolder",
                            fontSize: "80%",
                            marginLeft: "3%",
                            width: '12%',
                        }}
                    >
                        <CountdownTimer
                            setState={setEnableGetOTP}
                            setTimeCount={setTimeOut}
                            timeCount={timeOut}
                        /><LoopIcon />
                    </React.Button>

                </> : <>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        label="Email Address"
                        autoComplete="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </>}
                {isVerifyOTP ? <>
                    <Button
                        onClick={async () => await verifyOTP()}
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        {isLoading ? <>
                            <CircularProgress color='inherit' size={25} />
                        </> : <>
                            <KeyboardArrowUpIcon />
                        </>} Submit
                    </Button>
                </> : <>
                    <Button
                        onClick={async () => await getOTP()}
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        {isLoading ? <>
                            <CircularProgress color='inherit' size={25} />
                        </> : <>
                            <ArrowForwardIosIcon />
                        </>} Next
                    </Button>
                </>}
                <Grid container justifyContent="flex-end">
                    <Grid item xs>
                        <Link to="/auth/sign-in" variant="body2" className='link-no-decoration'>
                            Sign in ?
                        </Link>
                    </Grid>
                    <Grid item>
                        <Link to="/auth/sign-up" variant="body2" className='link-no-decoration'>
                            Sign Up ?
                        </Link>
                    </Grid>
                </Grid>
            </Box>
        </Box>
    </>)
}

export default ForgotPassword