import axios from "axios";
import { store } from "@/store/createStore.js"

function generalRequest(url, data) {
    return axios.post(
        url,
        {
            data: data
        },
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.data
    })
}

function samplePost() {
    return generalRequest('/api/teach/admin', null)
}

function getTerm() {
    return generalRequest('/api/course/term', null)
}

function getMenuList(data) {
    return generalRequest('/api/teach/getMenuList', data)
}

function setTerm(uid, term) {
    return axios.post(
        '/api/course/setTerm',
        {
            uid: uid,
            term: term
        },
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.message;
    })
}


function getAllCourses() {
    return generalRequest('/api/course/all',null)
}
function getUserInformation(uid){
    return axios.post(
        "/api/auth/infor",
        {
            id: uid
        },
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data
    })
}
function setUpCourse(data){
    return axios.post(
        '/api/course/setup',
        data,
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.message;
    })
}
function getmine(iid){
    return axios.post(
        '/api/course/mycourse',
        {
            id: iid
        },
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.data;
    })
}


function stuScore(iid){
    return axios.post(
        '/api/course/stuScore',
        {
            id: iid
        },
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.data;
    })
}
//  company
function postCourse(course, user){
    return axios.post(
        '/api/course/save',
        {
            courseId: course,
            userId: user
        },
        {
            headers: {
            Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.message;
    })
}
function eraseCourse(course, user, url){
    return axios.post(
        '/api/course/'+url,
        {
            courseId: course,
            userId: user
        },
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.message;
    })
}
function setScore(course, data){
    return axios.post(
        '/api/course/setScore',
        {
            courseId: course,
            data: data
        },
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.message;
    })
}

function userUpdate(data) {
    return axios.post('/api/auth/update',
        data,
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.message
    })
}
function changePassword(data) {
    return generalRequest('/api/teach/changePassword', data)
}
function openCourseSYS(term, sequence){
    return axios.post(
        '/api/course/osys',
        {
            term:  term  ,
            sequence: sequence
        },
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.message;
    })
}
function endCourseSYS(){
    return axios.post(
        '/api/course/esys',
        {},
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.message;
    })
}

function score(course, user){
    return axios.post(
        '/api/course/score',
        {
            courseId: course,
            userId: user
        },
        {
            headers: {
                Authorization: 'Bearer ' + store.state.jwtToken
            }
        }
    ).then(res => {
        return res.data.data;
    })
}

export {
    samplePost,
    getMenuList,
    getAllCourses,
    postCourse,
    getmine,
    userUpdate,
    getUserInformation,
    changePassword,
    eraseCourse,
    setUpCourse,
    score,
    endCourseSYS,
    getTerm,
    openCourseSYS,
    stuScore,
    setScore,
    setTerm,
}