import Avatar from '@mui/material/Avatar';
import { useState, useContext } from 'react';
import { UserContext } from '../../../contexts/UserContext';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import LogoutIcon from '@mui/icons-material/Logout';
import PermIdentityIcon from '@mui/icons-material/PermIdentity';
import ContactEmergencyIcon from '@mui/icons-material/ContactEmergency';
import PasswordIcon from '@mui/icons-material/Password';
import { useNavigate } from 'react-router-dom';
import { Logout } from '../../../services/apis/AuthAPI'
import { toast } from 'react-toastify';

const UserHeader = () => {
    const [anchorEl, setAnchorEl] = useState(null);
    const { user, logout } = useContext(UserContext);
    const navigate = useNavigate()

    const logoutUser = async() => {
        try {
            const res = await Logout();
            if(res.state == 1){
                logout()
            }
        } catch (error) {
            toast.error(error);
        }
    }

    const handleDropdownToggle = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleDropdownClose = () => {
        setAnchorEl(null);
    };

    return (
        <div style={{ position: 'relative', display: 'inline-block' }}>
            <Avatar
                onClick={handleDropdownToggle}
                src={user.avatar}
                className="avatar-custom"
                style={{
                    width: 45,
                    height: 45,
                    cursor: 'pointer',
                }}
            />
            <Menu
                id="menu"
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleDropdownClose}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'center',
                }}
            >
                <MenuItem onClick={() => { handleDropdownClose(); navigate("/user/my-information") }}>
                    <div sty>
                        <PermIdentityIcon style={{ marginRight: '8px' }} /> My Information
                    </div>
                </MenuItem>
                <MenuItem onClick={handleDropdownClose}>
                    <div>
                        <ContactEmergencyIcon style={{ marginRight: '8px' }} /> My Page
                    </div>
                </MenuItem>
                <MenuItem onClick={handleDropdownClose}>
                    <div>
                        <PasswordIcon style={{ marginRight: '8px' }} /> Change Password
                    </div>
                </MenuItem>
                <div style={{ borderTop: '1px solid #ccc', margin: '4px 0' }} />
                <MenuItem onClick={async() => await logoutUser()}>
                    <div style={{ color: 'red' }}>
                        <LogoutIcon style={{ marginRight: '8px' }} /> Logout
                    </div>
                </MenuItem>
            </Menu>
        </div>
    );
};

export default UserHeader;
