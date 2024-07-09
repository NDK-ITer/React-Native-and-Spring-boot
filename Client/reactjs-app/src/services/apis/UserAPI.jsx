import Root from "../Root";

const controller = `user`

const GetUserProfile = () => {
    return Root.get(`api/${controller}/my-information/get`);
}

export {
    GetUserProfile
}