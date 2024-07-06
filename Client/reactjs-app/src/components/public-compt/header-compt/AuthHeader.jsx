import React, { useState, useEffect } from 'react';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import IconButton from '@mui/material/IconButton';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { Link } from 'react-router-dom';
import LoginIcon from '@mui/icons-material/Login';
import HowToRegIcon from '@mui/icons-material/HowToReg';

const AuthHeader = () => {
    const [anchorEl, setAnchorEl] = useState(null);
    const [windowWidth, setWindowWidth] = useState(window.innerWidth);

    const handleDropdownToggle = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleDropdownClose = () => {
        setAnchorEl(null);
    };

    const handleResize = () => {
        setWindowWidth(window.innerWidth);
    };

    useEffect(() => {
        window.addEventListener('resize', handleResize);
        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    const renderLinksOrMenu = () => {
        if (windowWidth >= 1465) {
            // display normal
            return (
                <>
                    <Link
                        className='link-no-decoration'
                        to="/auth/sign-in"
                        style={{
                            textDecoration: 'none',
                            marginRight: '10px',
                            display: 'flex',
                            alignItems: 'center'
                        }}
                    >
                        <LoginIcon style={{ marginRight: '5px' }} />
                        Login
                    </Link>
                    <span style={{ margin: '0 5px' }}>|</span>
                    <Link
                        className='link-no-decoration'
                        to="/auth/sign-up"
                        style={{
                            textDecoration: 'none',
                            marginLeft: '10px',
                            display: 'flex',
                            alignItems: 'center'
                        }}
                    >
                        <HowToRegIcon style={{ marginRight: '5px' }} />
                        Register
                    </Link>
                </>
            );
        } else {
            // display menu
            return (
                <>
                    <IconButton
                        onClick={handleDropdownToggle}
                        aria-controls="menu"
                        aria-haspopup="true"
                        style={{ color: '#fff' }}
                    >
                        <MoreVertIcon />
                    </IconButton>
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
                        <MenuItem onClick={handleDropdownClose}>
                            <Link
                                className='link-no-decoration'
                                to="/auth/sign-in"
                                style={{ textDecoration: 'none', color: 'inherit' }}
                            >
                                <LoginIcon style={{ marginRight: '5px' }} />
                                Login
                            </Link>
                        </MenuItem>
                        <MenuItem onClick={handleDropdownClose}>
                            <Link
                                className='link-no-decoration'
                                to="/auth/sign-up"
                                style={{ textDecoration: 'none', color: 'inherit' }}
                            >
                                <HowToRegIcon style={{ marginRight: '5px' }} />
                                Register
                            </Link>
                        </MenuItem>
                    </Menu>
                </>
            );
        }
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
            <div style={{ position: 'relative', display: 'flex', alignItems: 'center' }}>
                {renderLinksOrMenu()}
            </div>
        </div>
    );
};

export default AuthHeader;
