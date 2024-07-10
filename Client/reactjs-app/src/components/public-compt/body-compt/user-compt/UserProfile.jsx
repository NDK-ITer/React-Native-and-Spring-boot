import { Avatar } from "@mui/material";
import { UserContext } from "../../../../contexts/UserContext"
import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Information from "./user-profile/Information";
import { GetUserProfile, UploadAvatar } from "../../../../services/apis/UserAPI"
import { toast } from "react-toastify";
import Contact from "./user-profile/Contact";
import { Modal, Button, Spinner } from 'react-bootstrap';
import AvatarPicker from "./user-profile/edit-compt/AvatarPicker";

const UserProfile = () => {
    const { user } = useContext(UserContext)
    const navigate = useNavigate();
    const [value, setValue] = useState(1);
    const [show, setShow] = useState(false);
    const [isLoading, setLoading] = useState(false);
    const [isActive, setIsActive] = useState(false);

    const [userProfile, setUserProfile] = useState({})

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const uploadAvatar = async (croppedImage) => {
        setLoading(true);
        try {
            const res = await UploadAvatar({
                avatar: croppedImage,
            });
            if(res.state == 1){
                handleClose()
                toast.success(res.mess);
            }else{
                toast.warning(res.mess);
            }
        } catch (error) {
            toast.error(error);
        } finally{
            setIsActive(false);
            setLoading(false);
        }
    }

    const getUserProfile = async () => {
        try {
            const res = await GetUserProfile()
            if (res.state == 1) {
                setUserProfile(res.data)
            } else {
                toast.warning(res.mess)
            }
        } catch (error) {
            toast.error(error)
        }
    }

    //#region Effect
    useEffect(() => {
        if (!user) {
            navigate('/auth/sign-in');
        } else {
            getUserProfile();
        }
    }, [user, navigate]);
    //#endregion

    return (<>
        <Modal show={show} onHide={handleClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>Select Avatar</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <AvatarPicker active={isActive} uploadAvatar={uploadAvatar} />
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Close
                </Button>
                <Button variant="primary" onClick={uploadAvatar}>
                    {isLoading && (<div style={{fontSize:'100%'}}><Spinner animation="border" /></div>)}Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
        <div style={{
            height: "100%",
        }}>
            <div style={{
                width: "100%",
                height: "100%",
                display: "flex",
            }}>
                <div className="menu-user-custom">
                    {user && user.avatar && <Avatar
                        className="avatar-user-custom"
                        src={user.avatar}
                        onClick={handleShow}
                    />}
                    <div className="options-user-custom">
                        <div
                            className={value == 1 ? "option-custom option-choose"
                                : "option-custom"}
                            onClick={() => setValue(1)}
                        >Information</div>
                        <div
                            className={value == 2 ? "option-custom option-choose"
                                : "option-custom"}
                            onClick={() => setValue(2)}
                        >Contract</div>
                        <div
                            className={value == 3 ? "option-custom option-choose"
                                : "option-custom"}
                            onClick={() => setValue(3)}
                        >Communicate</div>
                        <div
                            className={value == 4 ? "option-custom option-choose"
                                : "option-custom"}
                            onClick={() => setValue(4)}
                        >Work</div>
                    </div>
                </div>
                <div className="component-content">
                    {value == 1 && (<>
                        <Information user={userProfile} setUser={setUserProfile} />
                    </>)}
                    {value == 2 && (<>
                        <Contact user={userProfile} setUser={setUserProfile} />
                    </>)}
                    {value == 3 && (<>
                    </>)}
                    {value == 4 && (<>
                    </>)}
                </div>
            </div>
            {/* <div style={{
                width: "100%",
                height: "70%",
                display: "flex",
            }}>
                <div className="user-information-custom">
                    <Box sx={{ width: '100%', typography: 'body1' }}>
                        <TabContext value={value}>
                            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                                <TabList
                                    centered
                                    onChange={handleChange}
                                    style={{ width: "100%" }}
                                >
                                    <Tab label="Infor" style={{ fontWeight: "bolder" }} value="0" />
                                    <Tab label="Product" style={{ fontWeight: "bolder" }} value="1" />
                                    <Tab label="Follow" style={{ fontWeight: "bolder" }} value="2" />
                                </TabList>
                            </Box>
                            <TabPanel value="0"><Information /></TabPanel>
                            <TabPanel value="1"><Product /></TabPanel>
                            <TabPanel value="2"><FollowRate /></TabPanel>
                        </TabContext>
                    </Box>
                </div>
            </div> */}
        </div>
    </>)
}
export default UserProfile