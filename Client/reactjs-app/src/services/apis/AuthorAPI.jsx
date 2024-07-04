import Root from "../Root"

const controller = `authorize`

const GetAllRole = () => {
    return Root.get(`/api/${controller}/get-all-role`)
}

export {
    GetAllRole
}