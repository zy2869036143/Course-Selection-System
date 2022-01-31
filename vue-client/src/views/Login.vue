<template>
     <!-- <card data-image="https://images.unsplash.com/photo-1479660656269-197ebb83b540?dpr=2&auto=compress,format&fit=crop&w=1199&h=798&q=80&cs=tinysrgb&crop=">
     </card> -->
    <div class="hello">
        <form  @submit.prevent="handleSubmit">
          <div class="login-container">
            <div class="left-container">
              <div class="title"><span>登录</span></div>
              <div class="input-container">
                <div class="id">
                    <span>我是老师</span>  <input name="iden" type="radio" v-model="identity" value="tea">   
                    <span>我是学生</span>  <input name="iden" type="radio" v-model="identity" value="stu">
                </div>
                <input type="text"     v-model="username" name="account" placeholder="用户名" id="username">
                <div v-show="!username" class="invalid-feedback">Username is required</div>
                <input type="password" v-model="password"  placeholder="密码">
                <div v-show="!password" class="invalid-feedback">Password is required</div>
              </div>
              <div class="message-container" > <span> <a href="http://lzyublog.top/" target="_blank">创作者的博客</a></span></div>
            </div>
            <div class="right-container">
              <div class="regist-container"> <span @click="toRegister">注册</span></div>
              <div class="action-container"> <input type="submit"  id="logon" value="登录"></div>
              <div class="remind-container">
              </div>
            </div>
          </div>
          <footer id="author" class="text-muted">&copy; 2021 李泽宇(202000300021)</footer>
        </form>
  </div>
  
</template>
<script>
// import card from '@/components/card.vue'
import { ElMessage } from 'element-plus'
import router from '@/router/index.js'
export default {
  name: 'Login',
  components:{
   // card
  },
  data () {
        return {
            username: '',
            password: '',
            identity: '',
        }
    },
  setup() {
    // 当用户没有同时查询星期和节次时
    const warn = (inf) => {
      ElMessage({
        showClose: true,
        message: inf,
        type: 'warning',
      })
    }
    // 用户选课成功，消息提醒
    const success = (cid) => {
      ElMessage({
        message: cid,
        type: 'success',
      })
    }
    return{
        success,
        warn,
    }
  },
  methods: {
    handleSubmit() {
        const { username, password, identity } = this;
        if (identity==""){
          this.warn("请选择您的身份");
        }else if(username=="") this.warn("请输入您的账号")
        else if(password=="") this.warn("请输入您的密码")
        if (username && password) {
            this.$store.dispatch('login', {
                username : username,
                password : password,
                identity : identity
            })
        }
    },
    toRegister(){
      router.push('/register');
    }
    ,
  //methods:结尾大括号
  }
}
</script>

<style scoped>
html {
  height: 100%;
}
.text-muted{
  padding-top: 2%;
  padding-left: 46%;
}
/*body{*/
/*  background-image: linear-gradient(to bottom right, rgb(114,135,254),rgb(130,88,186))*/
/*}*/
.login-container{
  width: 600px;
  height: auto;
  margin: 0 auto;
  margin-top: 10%;
  background-color: rgb(95, 76, 194);
  border-radius: 15px;
  box-shadow: 0 10px 50px 10px rgb(59,45,159  );
}
.login-container .left-container{
  display: inline-block;
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  width: 300px;
  padding: 60px;
  background-image: linear-gradient(to bottom right , rgb(118, 76, 163), rgb(92, 103, 211));
}
.login-container .left-container .title{
  color: #fff;
  font-size: 18px;
}
.login-container .left-container .title span{
  /* 底线 */
  border-bottom: 3px solid rgb(237,221,22);
}
.login-container .left-container  .input-container{
  padding: 20px 0 ;
}
.login-container .left-container  .input-container input{
  border: 0;
  background:none;
  /* outline: 0; */
  color:#fff;
  margin: 10px 0;
  padding: 5px 0;
  transition: .2s;
  border-bottom: 1px solid rgb(59,45,159  );
  outline: 0;
}
.login-container .left-container  .input-container input:hover{
  border-bottom-color: #fff;

}
.login-container .left-container .message-container{
  font-size: 14px;
  color: rgb(199, 191, 219);
  transition: .2s;
  cursor: pointer;
}
.login-container .left-container .message-container:hover{
  color: blue;
  transition: .2s;
}
::-webkit-input-placeholder{
  color: rgb(199, 191, 219);
}

.login-container .right-container{
  display: inline-block;
  vertical-align: top;
  width: 145px;
  height: 290px;
  /* height: 168px; */
  /* padding-top: 130px; */
  color: #fff;
}
.login-container .right-container .action-container input{
  padding: 10px;
  color: #fff;
  width: 50px;
  background-color: rgb(95,76,194);
  border: 1px solid rgb(237,221,22);
  text-align: center;
  border-radius: 50px;
  display: inline-block;
  line-height: 20px;
  margin-left: 50px;
  margin-top: 100px;
  position: absolute;
  transition: .2s;
  cursor: pointer;
  font-size: 5px;
}

.login-container .right-container .regist-container {
  text-align: center;
  font-size: 18px;
  margin-top: 60px;
}
.login-container .right-container .regist-container span{
  border-bottom: 3px solid rgb(237,221,22);
  transition: .2s;
  margin-bottom: 50px;
}

.login-container .right-container .regist-container span:hover{
  color: blue;
}

.login-container .right-container .action-container input:hover {
  background-color:rgb(237,221,22) ;
}
a {
  color: #fff;
  text-decoration: none;
  transition: .2s;
  cursor: pointer;
}
a:hover{
  color: blue;
}
.remind-container{
  color: red;
  width: auto;
  text-align: center;
  vertical-align: bottom;
  margin-top: 170px;
}
</style>