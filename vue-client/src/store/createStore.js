import {createStore } from 'vuex';
import VuexPersist from 'vuex-persist';
import router from '@/router/index.js';
import { userLoginReq } from '@/service/userServ.js'
import { ElMessage } from 'element-plus'
const warn = (inf) => {
  ElMessage({
    showClose: true,
    message: inf,
    type: 'error',
  })
}
const vuexLocalStorage = new VuexPersist({
    key: 'vuex', // The key to store the state on in the storage provider.
    storage: window.localStorage, // or window.sessionStorage or localForage
    // Function that passes the state and returns the state with only the objects you want to store.
    // reducer: state => state,
    // Function that passes a mutation and lets you decide if it should update the state in localStorage.
    // filter: mutation => (true)
  })
  
const store = createStore({
    state () {
        return {
            loggedIn : false,
            username : "",
            jwtToken: "",
            identity: '',
        }
      },
      mutations: {
        login (state, {username, jwtToken, identity}) {
            state.loggedIn = true,
            state.username = username,
            state.jwtToken = jwtToken,
            state.identity = identity
        },
        logout(state) {
            state.loggedIn = false,
            state.username = "",
            state.jwtToken = "",
            state.identity = ""
        }
      },
      actions : {
        login({commit}, {username, password, identity}){
          userLoginReq(username, password, identity)
          .then(
            (jwtToken) => {
            console.log(jwtToken)
            commit('login', {username, jwtToken, identity})
            router.push('/')
          }
          ).catch(error => {
            warn("登陆失败！信息不匹配。")
            console.error('There was an error!', error);
          });
        }
      },
    plugins: [vuexLocalStorage.plugin]
  });
  
export {store}