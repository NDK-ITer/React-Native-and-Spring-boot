import { Avatar } from "@mui/material";
import { UserContext } from "../../../../contexts/UserContext"
import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const UserProfile = () => {
    const { user } = useContext(UserContext)
    const navigate = useNavigate();
    const [value, setValue] = useState(1);

    //#region Effect
    useEffect(() => {
        if (!user) {
            navigate('/auth/sign-in');
        }
    }, [user, navigate]);
    //#endregion

    return (<>
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
                        <div
                            className={value == 5 ? "option-custom option-choose"
                                : "option-custom"}
                            onClick={() => setValue(5)}
                        >Follow Rate</div>
                    </div>
                </div>
                <div className="component-content">
                    
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