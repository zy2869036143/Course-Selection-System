<template>
  <div class="register-container">
    <form>
      <div class="whole-container">
        <div class="title-container">
          <span>注册表</span>
        </div>
        <div class="left-container">
          <div class="label-container"><span>学号:</span></div>
          <div class="label-container"><span>真实姓名:</span></div>
          <div class="label-container"><span>性别:</span></div>
          <div class="label-container"><span>身份:</span></div>
          <div class="label-container"><span>所在学校:</span></div>
          <div class="label-container"><span>所在专业:</span></div>
          <div class="label-container"><span>电话号码:</span></div>
          <div class="label-container"><span>密码:</span></div>
          <div class="label-container"><span>确认密码:</span></div>
        </div>
        <div class="right-container">
          <div class="infor-container"><input type="text" v-model="username" placeholder="账号" required></div>
          <div class="infor-container"><input type="text" v-model="realname" placeholder="真实姓名" required></div>
          <div class="infor-container">
            <input type="radio" name="gender"  value="男" v-model="gender" required><span>男</span>
            <input type="radio" name="gender"  value="女" v-model="gender" required><span>女</span>
          </div>
          <div class="infor-container">
            <input type="radio"  value="teacher" v-model="role" required><span>教师</span>
            <input type="radio"  value="student" v-model="role" required><span>学生</span>
          </div>
          <div class="infor-container"><input type="text" v-model="school" placeholder="学校" required></div>
          <div class="infor-container"><input type="text" v-model="major" placeholder="专业" required></div>
          <div class="infor-container"><input type="text" v-model="phone" placeholder="电话" pattern="[0-9]{11}" required></div>
          <div class="infor-container"><input v-model="psw1" type="password"  placeholder="密码" minlength="4" required></div>
          <div class="infor-container"><input v-model="psw2" type="password"  placeholder="确认密码" minlength="4" required></div>
          <div v-show="psw1!=psw2">两次账号密码输入不一致</div>



        </div>
        <div class="remind-container"><span id="reminder"></span></div>
        <div class="submit-container">
          <input type="submit" value="注册账号" id="submit"  @click.prevent="handleSubmit">
        </div>
      </div>
    </form>
  </div>


  
          <!-- <el-form-item  prop="mobile">
              <el-input v-model="ruleForm.mobile" placeholder="请输入手机号"></el-input>
          </el-form-item>
          <el-form-item  prop="code">
            <el-input @click.prevent="submitForm('ruleForm')" v-model="ruleForm.code" placeholder="请输入验证码">
               </el-input>
              <el-button v-if="sending"  @click="getCode">获取验证码</el-button>
              <el-button v-else  :disabled="disabled">{{second}}秒后获取</el-button>
           
          </el-form-item> -->
</template>

<script>
import { ElMessage } from 'element-plus'
import router from '@/router/index.js'
import {userSignupReq} from '@/service/userServ.js'
export default {
  name: "register",
  data () {
    // const validate_mobile = (rule, value, callback) => {
    //     let reg = /^[1]([3-9])[0-9]{9}$/;
    //     if (!value) {
    //       callback("请输入手机号");
    //     } else if (!reg.test(value)) {
    //       callback("请输入正确的手机号");
    //     } else {
    //       callback();
    //     }
    //   };
        return {
            username: '',
            realname: '',
            gender: '',
            role: '',
            school: '',
            major: '',
            phone: '',
            psw1: '',
            psw2:'',
            message: '',
            //  sending: true,    //是否发送验证码
            //                   disabled: false,  //是否禁发验证码
            //                   second:59,     

            //                         rules: {
            //                     mobile: [{ required: true, validator: validate_mobile, trigger: "blur" }],
            //                     code: [{ required: true, message: "请输入验证码", trigger: "blur" }]
            //                   },
            //                   ruleForm:{
            //                     mobile:'',
            //                     code:''
            //                   },
                             

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
    const error = (inf) => {
      ElMessage({
        showClose: true,
        message: inf,
        type: 'error',
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
        error
    }
  },
    methods: {
  // timeDown() {
  //     let result = setInterval(()=>{
  //     --this.second;
  //     if(this.second < 0) {
  //       clearInterval(result);
  //       this.sending  = true;
  //       this.disabled = false;
  //       this.second = 59;
  //       }   
  //     }, 1000);
  //   },
  //    //获取验证码
  //   getCode(){
  //     if(!this.sending){
  //       return;
  //     }
  //     if(this.ruleForm.mobile == ''){
  //       this.$message.error('请输入手机号')
  //     }else{
  //     //api接口封装请求
  //       this.$http.user.getCode({
  //         mobile:this.ruleForm.mobile
  //       }).then(res=>{
  //         this.sending  = false;
  //         this.disabled = true;
  //         this.timeDown();
  //       })
  //     }
  //   },
  // //提交
  //   submitForm(formName) {
  //     this.$refs[formName].validate(valid => {
  //       if (valid) {
  //         console.log('提交')
  //       }
  //     });
  //   },
      sleep(numberMillis) { 
        var now = new Date(); 
        var exitTime = now.getTime() + numberMillis; 
        while (now.getTime() < exitTime) { 
            now = new Date();
        } 
      },





      handleSubmit() {
        //this.t = true;
        if([this.username, this.realname, this.gender, this.role, this.school, this.major, this.phone, this.psw1, this.psw2].includes("")){
        this.warn("注册表信息需全部填充")
        return false;
        }
        if(this.phone.length!=11){
            this.warn("请填写11位电话号码")
            return false;
        }
        if(this.psw1.length<6){
            this.warn("密码长度至少为6位!!")
            return false;
        }
        // var t = true;

        if (this.psw1==this.psw2) {
          //  username: username,
          //  password: password,
          //  real_name: real_name,
          //  gender: gender,
          //  university: university,
          //  phone: phone,
          //  major: major,
          //  role: role
          userSignupReq(this.username, this.psw1, this.realname, this.gender, this.school, this.phone, this.major, this.role).then(res=>{
            if(res=='User registered successfully!'){
              this.message = res;
              this.success("注册成功!");
              router.push("/login");
              return;
            }else if (res=="Error: Username is already taken!"){
              this.error("用户学号已被占用!");
            }
          });
        }else{
          this.warn("口令不一致!")
          return false;
        }
        //alert(this.message+"!");
        // this.sleep(100);
        // if(this.message=='User registered successfully!')  {this.warn("用户学号已被占用!");}
      },
    }
}
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}
html{
  height: 100%;
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  background-image: linear-gradient(to bottom right ,rgb(114,135,254),rgb(130,88,186) );
}
body .whole-container{
  margin-top: 6%;
  width: 500px;
  height: auto;
  margin-left: 35%;
  background-color: rgb(95, 76, 194);
  box-shadow: 0 10px 50px 10px rgb(59,45,159  );
  background-image: linear-gradient(to bottom right , rgb(118, 76, 163), rgb(92, 103, 211));
  border-radius: 15px;
  height: auto;
  color: #fff;
}
body .whole-container .title-container{
  /* text-align: center; */
  padding-top: 20px;
  margin-left: 170px;
  margin-top: 10px;
  font-size: 20px;
}
body .whole-container .title-container span{
  border-bottom:  3px solid rgb(237,221,22);
}
body .whole-container .left-container{
  display: inline-block;
  padding: 70px;
  padding-top: 40px;
  text-align: right;
  padding-top: 4px;
  width: 70px;
  margin-top: 36px ;
  font-size: 14px;

}
input{
  border: 0;
  background-image: none;
}
body .whole-container .right-container{
  display: inline-block;
  vertical-align: top;
  width: 250px;
  font-size: 14px;
  margin-top: 36px;
  text-align: center;
}


body .whole-container .left-container .label-container {
  margin-bottom: 10px;
  margin-top: 11px;
}



body .whole-container .right-container .name-container{
  margin-bottom: 0px;
  margin-top:23%;
}


body .whole-container .right-container .infor-container{
  margin-bottom: 10px;
  margin-top: 13px;
}
::-webkit-input-placeholder{
  color: rgb(199,291,219);
}
body .whole-container .right-container .infor-container input{
  border: 0;
  background: none;
  color: #fff;
  outline: 0;
  border-bottom: 1px solid #fff;
  transition: .2s;
}
body .whole-container .right-container .infor-container input:hover{
  border-bottom-color: rgb(59,45,159);

}
body .whole-container .submit-container{
  text-align: center;
  padding-top: 0;
  padding-bottom: 40px;
}

body .whole-container .submit-container input{
  background-color:rgb(95,76,194) ;
  width: 180px;
  height: 50px;
  color: #fff;
  border: 1px solid rgb(237,221,22);
  transition: .2s;
  cursor: pointer;
  border-radius: 50px;
}
body .whole-container .remind-container{
  padding-bottom: 5px;
  text-align: center;
  color: rgb(228, 3, 3);
}

body .whole-container .submit-container input:hover{
  background-color:rgb(201, 183, 25) ;
}
</style>