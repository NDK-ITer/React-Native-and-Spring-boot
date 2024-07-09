import { Avatar } from "@mui/material";
import { UserContext } from "../../../../contexts/UserContext"
import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const UserProfile = () => {
    const { user } = useContext(UserContext)
    const navigate = useNavigate();

    //#region Effect
    useEffect(() => {
        if (!user) {
            navigate(`/auth/sign-in`)
        }
    })
    //#endregion

    return (<>
        <div style={{
            height: "100%",
        }}>
            <div style={{
                width: "100%",
                height: "35%",
                display: "flex",
            }}>
                <div className="overview-user-custom">
                    <div className="avatar-information-custom">
                        {user && user.avatar && <Avatar
                            className="avatar-custom"
                            src={user.avatar}
                            style={{
                                margin: "auto",
                                cursor: "pointer",
                                '&:hover': {
                                    transform: 'scale(1.1)',
                                    filter: "brightness(0.9)",
                                }
                            }}
                        />}

                    </div>
                    <div></div>
                </div>
            </div>
            <div style={{
                width: "100%",
                height: "65%",
            }}>

            </div>
        </div>
    </>)
}
export default UserProfile