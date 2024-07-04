import React, { createContext, useState, useEffect } from 'react';
import { GetAllRole } from "../services/apis/AuthorAPI";

export const RoleContext = createContext();

export const RoleProvider = ({ children }) => {
    const [listRole, setListRole] = useState(undefined)

    const getAllRole = async () => {
        try {
            const res = await GetAllRole()
            if (res.state == 1) {
                setListRole(res.data)
            }
        } catch (error) {

        }
    }
    useEffect(() => {
        getAllRole()
    }, []);

    return (
        <RoleContext.Provider value={{ listRole }}>
            {children}
        </RoleContext.Provider>
    )
}