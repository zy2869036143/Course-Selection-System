import axios from "axios";

function userLoginReq(username, password, identity) {
    return axios.post('/api/auth/login',
    {
        username: username,
        password: password,
        identity: identity,
    }).then(res => {
        return res.data.accessToken
    })
}


function userSignupReq(username,password,real_name,gender,university, phone, major, role ){
    return axios.post('/api/auth/signup',{
       username: username,
       password: password,
       realName: real_name,
       gender: gender,
       university: university,
       phone: phone,
       major: major,
       role: role,
   }).then(res => {
       return res.data.message
   })
}

export {
    userLoginReq,
    userSignupReq
}