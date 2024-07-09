import React, { createContext, useState, useEffect } from 'react';
import Cookies from 'js-cookie';

export const UserContext = createContext();

export const UserProvider = ({ children }) => {
    const [user, setUser] = useState(undefined)
    useEffect(() => {
        let userFromCookie = Cookies.get('user');
        if (userFromCookie) {
            userFromCookie = JSON.parse(userFromCookie);
            setUser(userFromCookie);
        }
    }, []);

    const login = (props) => {
        Cookies.set('token', props.token);
        delete props.token;
        Cookies.set('tokenCode', props.tokenCode);
        delete props.tokenCode;
        Cookies.set('user', JSON.stringify(props));
        setUser(props);
    };

    const logout = () => {
        Cookies.remove('user');
        Cookies.remove("token");
        setUser(undefined);
    };

    return (
        <UserContext.Provider value={{ user, login, logout }}>
            {children}
        </UserContext.Provider>
    );
}