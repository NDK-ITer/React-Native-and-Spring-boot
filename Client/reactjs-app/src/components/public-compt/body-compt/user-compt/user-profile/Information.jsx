import { toast } from "react-toastify"
import { GetUserProfile } from "../../../../../services/apis/UserAPI"
import { useEffect, useState } from "react"
import { Typography, Paper, Grid } from '@mui/material';
import EmailIcon from '@mui/icons-material/Email';
import CallIcon from '@mui/icons-material/Call';
import CakeIcon from '@mui/icons-material/Cake';
import BadgeIcon from '@mui/icons-material/Badge';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import ErrorIcon from '@mui/icons-material/Error';
import { Button, Modal } from 'react-bootstrap';

const Information = () => {
    const [user, setUser] = useState()

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const getUserProfile = async () => {
        try {
            const res = await GetUserProfile()
            if (res.state == 1) {
                setUser(res.data)
            } else {
                toast.warning(res.mess)
            }
        } catch (error) {
            toast.error(error)
        }
    }

    useEffect(() => {
        getUserProfile()
    }, [])
    return (<>
        <Modal show={show} onHide={handleClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>Modal heading</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                Woohoo, you are reading this text in a modal!
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Close
                </Button>
                <Button variant="primary" onClick={handleClose}>
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
        {user && (<>
            <Typography variant="h5" gutterBottom>
                <div
                    style={{
                        fontWeight: "bolder",
                        textAlign: "center",
                        fontFamily: "Dancing Script",
                        fontSize: "200%",
                        color: "blue",
                        borderRadius: "10px",
                    }}
                >{user.displayName}</div>
            </Typography>
            <div style={{
                display: "flex",
                flexWrap: "wrap",
                justifyContent: "center",
                gap: "20px",
                maxHeight: "37vh",
            }}>
                <Paper
                    style={{
                        padding: '20px',
                        width: "45%",
                        minWidth: "300px",
                        alignContent: "center",
                    }}
                    className="profile-prop"
                    onClick={handleShow}
                >
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <Typography variant="body1" style={{
                                fontFamily: "Dancing Script",
                                fontWeight: "bolder",
                                fontSize: "150%",
                            }}>
                                <BadgeIcon /> : {user.fullName}
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography variant="body1" style={{
                                fontFamily: "Dancing Script",
                                fontWeight: "bolder",
                                fontSize: "150%",
                            }}>
                                <CakeIcon /> : {new Date(user.dateOfBirth).toLocaleDateString('en-US', { day: 'numeric', month: 'numeric', year: 'numeric' })}
                            </Typography>
                        </Grid>
                    </Grid>
                </Paper>

                <Paper style={{
                    padding: '20px',
                    width: "45%",
                    minWidth: "300px",
                    alignContent: "center",
                }}>
                    <Grid container spacing={2}>
                        <Grid item xs={12}
                            className="profile-prop"
                        >
                            <Typography variant="body1"
                                className={user.isVerifyEmail ? "email-is-verify" : "email-is-not-verify"}
                                style={{
                                    fontFamily: "Dancing Script",
                                    fontWeight: "bolder",
                                    fontSize: "150%",
                                }}
                            >
                                <EmailIcon /> : {user.email} {user.isVerifyEmail ? <CheckCircleIcon /> : <ErrorIcon />}
                            </Typography>
                        </Grid>
                        <Grid item xs={12} className="profile-prop">
                            <Typography variant="body1" style={{
                                fontFamily: "Dancing Script",
                                fontWeight: "bolder",
                                fontSize: "150%",
                            }}>
                                <CallIcon /> : {user.phoneNumber == null ? ' -no set- ' : <>{user.phoneNumber}</>}
                            </Typography>
                        </Grid>
                    </Grid>
                </Paper>
            </div>
        </>)}
    </>)
}

export default Information