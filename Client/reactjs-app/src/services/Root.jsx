import axios from 'axios';
import { toast } from "react-toastify";
import Cookies from 'js-cookie';

const instance = axios.create({
    baseURL: `http://localhost:7001/`,
    timeout: 15000,
    withCredentials: true
});

instance.interceptors.request.use(function (config) {
    const jwt  = Cookies.get('token');
    if (jwt) {
        config.headers[`Authorization`] = `Bearer ${jwt}`
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});

instance.interceptors.response.use(function (res) {
    return res.data;
}, function (error) {
    toast(error);
    return Promise.reject(error);
});

export default instance;