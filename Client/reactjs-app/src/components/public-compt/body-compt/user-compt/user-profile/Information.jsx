import { toast } from "react-toastify"
import { EditUserProfile } from "../../../../../services/apis/UserAPI"
import { useEffect, useState } from "react"
import { Typography, Paper, Grid } from '@mui/material';
import EmailIcon from '@mui/icons-material/Email';
import CallIcon from '@mui/icons-material/Call';
import CakeIcon from '@mui/icons-material/Cake';
import BadgeIcon from '@mui/icons-material/Badge';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import ErrorIcon from '@mui/icons-material/Error';
import BorderColorIcon from '@mui/icons-material/BorderColor';
import { Button, Modal, Form, Spinner } from 'react-bootstrap';
import DatePicker from 'react-datepicker';

const Information = ({ user, setUser }) => {
    // const [user, setUser] = useState({})
    const [show, setShow] = useState(false);
    const [isLoading, setIsLoading] = useState(false)

    const [userEdit, setUserEdit] = useState(user)

    const handleClose = () => setShow(false);
    const handleShow = () => {
        setUserEdit(user)
        setShow(true)
    };

    const editUserProfile = async () => {
        setIsLoading(true);
        try {
            const res = await EditUserProfile({
                displayName: userEdit.displayName,
                firstName: userEdit.firstName,
                lastName: userEdit.lastName,
                dateOfBirth: userEdit.dateOfBirth,
            })
            if (res.state == 1) {
                setUser(res.data)
                setUserEdit(user)
                handleClose()
                toast.success(res.mess)
            } else {
                toast.warning(res.mess)
            }
        } catch (error) {
            toast.error(error)
        } finally {
            setIsLoading(false);
        }
    }

    return (<>
        <Modal show={show} onHide={handleClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>Edit Information</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form.Label htmlFor="inputPassword5">Display Name</Form.Label>
                <Form.Control type="text" value={userEdit.displayName}
                    onChange={(e) => { setUserEdit(pre => ({ ...pre, displayName: e.target.value })) }}
                />
                <Form.Label htmlFor="inputPassword5">First Name</Form.Label>
                <Form.Control type="text" value={userEdit.firstName}
                    onChange={(e) => { setUserEdit(pre => ({ ...pre, firstName: e.target.value })) }}
                />
                <Form.Label htmlFor="inputPassword5">Last Name</Form.Label>
                <Form.Control type="text" value={userEdit.lastName}
                    onChange={(e) => { setUserEdit(pre => ({ ...pre, lastName: e.target.value })) }}
                />
                <Form.Label htmlFor="inputPassword5">Birthday</Form.Label><br/>
                <DatePicker
                    selected={new Date(userEdit.dateOfBirth)}
                    onChange={(date) => { setUserEdit(pre => ({ ...pre, dateOfBirth: date })) }}
                    showTimeSelect
                    className="form-control"
                />
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Close
                </Button>
                <Button variant="primary" onClick={() => editUserProfile()}>
                    {isLoading ?
                        <>Processing...<Spinner animation="border" /></>
                        :
                        <>Save Changes</>
                    }
                </Button>
            </Modal.Footer>
        </Modal>
        {user && (<>
            <div className="information-areal">
                <Paper className="custom-page">
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <Typography variant="body1" >
                                <BadgeIcon /> Display Name
                                <div className="property-custom">
                                    {user.displayName}
                                </div>
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography variant="body1" >
                                <BadgeIcon /> Full Name
                                <div className="property-custom" >
                                    {user.fullName}
                                </div>
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography variant="body1" >
                                <BadgeIcon /> First Name
                                <div className="property-custom">
                                    {user.firstName}
                                </div>
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography variant="body1" >
                                <BadgeIcon /> Last Name
                                <div className="property-custom">
                                    {user.lastName}
                                </div>
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography variant="body1" >
                                <CakeIcon /> Birthday
                                <div className="property-custom">
                                    {new Date(user.dateOfBirth).toLocaleDateString('en-US', { day: 'numeric', month: 'numeric', year: 'numeric' })}
                                </div>
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography variant="body1"
                                className="edit-btn-custom"
                                onClick={handleShow}
                            >
                                <BorderColorIcon />
                            </Typography>
                        </Grid>
                    </Grid>
                </Paper>
            </div>
        </>)}
    </>)
}

export default Information