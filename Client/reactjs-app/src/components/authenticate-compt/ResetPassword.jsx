import { Link, useLocation } from 'react-router-dom';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import { ResetPassword as resetPassword } from "../../services/apis/AuthAPI"
import { useState } from 'react';
import { toast } from 'react-toastify';
import CircularProgress from '@mui/material/CircularProgress';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { Grid } from '@mui/material';


const ResetPassword = () => {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const token = queryParams.get('token');

    const [newPassword, setNewPassword] = useState('')
    const [confirmNewPassword, setConfirmNewPassword] = useState('')
    const [isLoading, setLoading] = useState(false);

    const userResetPassword = async () => {
        setLoading(true);
        try {
            const res = await resetPassword({
                token: token,
                newPassword: newPassword
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
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main', marginTop: '10%' }}>
                <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
                Reset Password
            </Typography>
            <Box component="form" noValidate sx={{ mt: 1 }}>
                <TextField
                    margin="normal"
                    fullWidth
                    label="New Password"
                    type="password"
                    autoFocus
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                />
                <TextField
                    margin="normal"
                    fullWidth
                    label="Confirm New Password"
                    type="password"
                    autoComplete="current-password"
                    value={confirmNewPassword}
                    onChange={(e) => setConfirmNewPassword(e.target.value)}
                />
                <div>
                    {(newPassword !== confirmNewPassword) && (
                        <>
                            <Typography color="error" sx={{ mt: 1 }}>
                                * password is not confirm
                            </Typography>
                        </>
                    )}
                </div>
                <Button
                    onClick={async () => await userResetPassword()}
                    fullWidth
                    variant="contained"
                    sx={{ mt: 5, mb: 2 }}
                >
                    {isLoading ? <>
                        <CircularProgress color='inherit' size={25} />
                    </> : <>
                        <KeyboardArrowUpIcon />
                    </>}
                    Submit
                </Button>
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
export default ResetPassword