import { Avatar } from "@mui/material";
import { UserContext } from "../../../../contexts/UserContext"
import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabPanel from '@mui/lab/TabPanel';
import Information from "./user-profile/Information";
import Product from "./user-profile/Product";
import FollowRate from "./user-profile/FollowRate";

const UserProfile = () => {
    const { user } = useContext(UserContext)
    const navigate = useNavigate();
    const [value, setValue] = useState('0');

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    //#region Effect
    useEffect(() => {
        let timeoutId;
        const checkAndNavigate = () => {
            if (!user) {
                navigate('/auth/sign-in');
            }
        };
        timeoutId = setTimeout(checkAndNavigate, 10000);
        return () => {
            clearTimeout(timeoutId);
        };
    }, [user, navigate]);
    //#endregion

    return (<>
        <div style={{
            height: "100%",
        }}>
            <div style={{
                width: "100%",
                height: "30%",
                display: "flex",
            }}>
                <div className="overview-user-custom">
                    <div className="avatar-information-custom">
                        {user && user.avatar && <Avatar
                            className="avatar-custom"
                            src={user.avatar}
                            style={{
                                width: 130,
                                height: 130,
                                margin: "auto",
                                cursor: "pointer"
                            }}
                        />}
                    </div>
                </div>
            </div>
            <div style={{
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
            </div>
        </div>
    </>)
}
export default UserProfile