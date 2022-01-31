<template>
  <el-form
    ref="ruleForm"
    :model="ruleForm"
    :rules="rules"
    label-width="120px"
    class="demo-ruleForm"
  >

   <el-form-item label="学号" prop="username" class="item">
      <el-input v-model="ruleForm.username" :disabled='username!==""'></el-input>
    </el-form-item>

    <el-form-item label="真实姓名" prop="name" class="item">
      <el-input v-model="ruleForm.name"></el-input>
    </el-form-item>
    <!-- <el-form-item label="性别" prop="region">
      <el-select v-model="ruleForm.region" placeholder="下拉选择">
        <el-option label="Zone one" value="shanghai"></el-option>
        <el-option label="Zone two" value="beijing"></el-option>
      </el-select>
    </el-form-item> -->

    <el-form-item label="性别" prop="gender" class="item">
      <el-radio-group v-model="ruleForm.gender">
        <el-radio label="男" name="gender"></el-radio>
        <el-radio label="女" name="gender"></el-radio>
        </el-radio-group>
    </el-form-item>
    
    <el-form-item label="学校" prop="university" class="item">
      <el-input v-model="ruleForm.university"></el-input>
    </el-form-item>

    <el-form-item label="专业" prop="major" class="item">
      <el-input v-model="ruleForm.major"></el-input>
    </el-form-item>
   
    <!-- <el-form-item label="自我简介" prop="desc" class="item">
      <el-input v-model="ruleForm.desc" type="textarea" height="200px"></el-input>
    </el-form-item> -->
    
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')"
        >保存</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { mapState } from 'vuex'
import {getUserInformation} from '../service/genServ.js'
import { userUpdate} from '../service/genServ.js'
import { ElMessage } from 'element-plus'

export default {
  setup() {
    // 用户选课成功，消息提醒
    const success = (cid) => {
      ElMessage({
        message: cid,
        type: 'success',
      })
    }
    return{
        success,
    }
    },
  computed: {
      ...mapState([
      'username', 'jwtToken', 'identity'
      ])
  },
  created(){
    this.getInfor();
  },
  mounted() {
        this.$nextTick(this.getInfor());      
  },

  data() {
    return {
      ruleForm: {
        username: this.username,
        name: '',
        desc: '',
        university: '',
        major: '',
        gender: '',
      },
      user: '1',
      rules: {
        username: [
          {
            required: true,
          }
        ],
        name: [
          {
            required: true,
            message: '请输入您的真实姓名',
            trigger: 'blur',
          },
          {
            min: 2,
            max: 5,
            message: '姓名应该为2-5个汉字',
            trigger: 'blur',
          },
        ],
        gender : [
          {     required: true,
                message: "请选择一个",
                trigger: 'blur',
          } 
        ],
        university :[
            {
                required: true,
                message: "请输入您所在大学的全称",
                trigger: 'blur',
            }
        ],
        major: [
            {
                required: true,
                message: "请输入您所学专业",
                trigger: 'blur',
            }
        ]
      },
    }
  },
  methods: {
    myUpdate(){
      userUpdate(this.ruleForm).then(res =>{
        if(res == 'success'){
          this.success("更新成功");
        }
      })
    },

    getInfor(){
      getUserInformation(this.username).then(res =>{
        this.user = res;
        this.ruleForm.username = this.user['userName'];
        this.ruleForm.name = this.user['realName'];
        this.ruleForm.gender = this.user['gender'];
        this.ruleForm.university = this.user['university'];
        this.ruleForm.major = this.user['major']; 
        this.ruleForm.desc =  this.user['desc'];
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.myUpdate();
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  },
}
</script>

<style scoped>
.item{
    padding-top: 41px;
}
</style>