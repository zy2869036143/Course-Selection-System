<template>
  <div>
      <div class="header">
        <Navi/> 
      </div>
      <el-container>
        <el-aside width="200px"> 
           <Sidebar v-bind:inPage=ccp @changeCCP='changeCCP' />
        </el-aside>
        <el-main>
            <!--标记信息 <span>{{ccp}}{{username}}</span> -->
            <component :is="ccp"></component>
        </el-main>
      </el-container>
  </div>
</template>
<script>
import Navi from '@/components/Navi'
import router from '@/router/index.js'
import userInfor from '../components/UserInfor.vue'
import Sidebar from '../components/Sidebar.vue'
import CourseTable from '../components/CourseTable.vue'
import Edit from "../components/Edit.vue"
import Entrance from "../components/Entrance.vue"
import { mapState } from 'vuex'
import Notice from "../components/Notice.vue"
import Score from "@/components/Score.vue"
import OpenCourse from "@/components/OpenCourse.vue"
import AlreadyCourse from "@/components/AlreadyCourse.vue"
import ReleaseNotice from "@/components/ReleaseNotice.vue"

export default {
  name: 'Home',
  components: {
    Navi,
    Notice,
    Score,
    Sidebar,
    userInfor,
    CourseTable,
    Edit,
    Entrance,
    OpenCourse,
    AlreadyCourse,
    ReleaseNotice,
  },
  data() {
    return{
        ccp: 'CourseTable',
        response: '',
    }
  },
  computed: {
    ...mapState([
      'username', 'jwtToken'
    ])
  },
  methods:{
    logout() {
      this.$store.commit('logout')
      router.push('/login')
    },
    changeCCP(current){
        this.ccp = current;
    },
  }
}
</script>
<style scoped>

</style>
