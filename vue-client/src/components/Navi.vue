<template>
  <div class="navi">
    <el-menu
      class="el-menu-demo1"
      mode="horizontal"
      router
      >
      <el-menu-item index="/"><span>主页</span></el-menu-item>
      <el-menu-item class="menu-item" v-for="(v,i) in list" :key="i" :index="'/'+v.name"><span>{{v.title}}</span></el-menu-item> 
      <el-menu-item @click="logout" index="1" class="menu-item"> <span>退出登录</span> </el-menu-item>                                               
    </el-menu>

  </div>
</template>

<script>
import {getMenuList} from '@/service/genServ.js'
import router from '@/router/index.js'
import { mapState } from 'vuex'

export default {
  name: 'Navi',
    computed: {
      ...mapState([
      'username', 'jwtToken', 'identity',
      ])
    },
  components: {
  },
  created() {
    this.setMenu()
  },
  data() {
      return {
        squareUrl:
        'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
        list:[]
      }
    },
  methods:{
     logout() {
      // alert(username);
      this.$store.commit('logout')
      router.push('/login')
    },
      setMenu() {
        var uuu = this.username;
        var self = this;
        getMenuList({'user': uuu})
          .then(data => {
            self.list = data.data
          }
          )
      }
    }
}
</script>
<style scoped>
span{
  color: rgba(211, 209, 209, 0.89);
}

.navi{
  padding-left: 0;
  margin-left: 0;
 padding-bottom: 5px;
}
.menu-item{
  color: white;
}
.el-menu-demo1{
  padding-left: 0;
  margin-left: 0;
  border-radius: 20px;
  background-image: linear-gradient(to bottom right , rgb(92, 103, 211),rgb(118, 76, 163));
  width: 100%;
}
</style>
