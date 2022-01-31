<template>
  <Navi/>
  <div  class="form-div">
      <el-form ref="editForm" :model="editForm" :rules="editRules" label-position="left" label-width="100px" status-icon style="margin-top: 15px;">
        <br/>
        <el-row align="middle">
          <el-col :span="4">
            &nbsp;
          </el-col>
          <el-col :span="10">
            <el-form-item label="旧密码" prop="oldPas">
              <el-input v-model="oldPassword" :type="passw" style="width: 200px;background-color: #f4f4f5"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4">
            &nbsp;
          </el-col>
          <el-col :span="10">
            <el-form-item label="新密码" prop="oldPas">
              <el-input v-model="newPassword" :type="passw"  style="width: 200px;background-color: #f4f4f5"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4">
            &nbsp;
          </el-col>
          <el-col :span="10">
            <el-form-item label="确认密码" prop="oldPas">
              <el-input v-model="newPassword1" :type="passw"  style="width: 200px;background-color: #f4f4f5"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row >
          <el-col :span="4">
          &nbsp;
          </el-col>
          <el-col :span="10">
            <el-form-item align="center">
              <el-button type="primary" @click="submit">提交</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
   </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import { mapState } from 'vuex'
import Navi from '@/components/Navi'
import { changePassword } from '@/service/genServ.js'
export default {
  name: 'ChangePassword',
  computed: {
    ...mapState([
      'username', 'jwtToken', 'identity'
    ])
  },
  components: {
    Navi,
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
  data() {
    return {
      passw: 'password',
      oldPassword: '',
      newPassword: '',
      newPassword1: '',
    }
  },
  methods: {
    submit() {
      if (this.newPassword !== this.newPassword1) {
        this.warn("口令不一致");
      } else {
        changePassword({'user': this.username, 'identity': this.identity ,'oldPassword': this.oldPassword, 'newPassword': this.newPassword }).then(response => {
          this.a = response;
          if (response.msg == null) {
            this.success("修改密码成功。")
          }else{
            this.warn("修改密码失败。")
          }
        })
      }
    }
  }
}
</script>