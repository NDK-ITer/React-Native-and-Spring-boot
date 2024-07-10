import Root from "../Root";

const controller = `user`

const GetUserProfile = () => {
    return Root.get(`api/${controller}/my-information/get`);
}

const EditUserProfile = (props) => {
    return Root.put(`api/${controller}/my-information/edit`,{
        displayName: props.displayName,
        firstName: props.firstName,
        lastName: props.lastName,
        dateOfBirth: props.dateOfBirth,
    });
}

const UpdateEmail = (props) => {
    return Root.post(`api/${controller}/email/update`,{})
}

const UploadAvatar = (props) => {
    const formData = new FormData();
    formData.append('avatar', props.avatar);
    return Root.post(`api/${controller}/avatar/upload`,formData,{
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
}

export {
    GetUserProfile,
    EditUserProfile,
    UploadAvatar,
}