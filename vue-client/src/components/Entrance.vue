<template>
     <div class="entrance">
        <table>
            <th>选课学期</th>
            <th>选课时间</th>
            <th>操作</th>
            <tr>
                <td v-if="exist">{{term}}</td>
                <td v-if="exist">正在进行</td>
                <td v-if="exist"><router-link to="/chosecourses">进入选课</router-link></td>
            </tr>
        </table>
    </div>
</template>

<script>
import {getTerm} from '@/service/genServ.js'
import {setTerm} from '@/service/genServ.js'
import {mapState} from 'vuex'

export default {
   name : 'entrance',
   data(){
       return{
         term: '',
         exist: false,
       }
   },
    computed: {
        ...mapState([
        'username', 'jwtToken', 'identity',
        ])
    },
   created(){
       this.getCurrentTerm();
   },
   methods: {
        // 子组件给父组件传值
        toFather(page) {
            alert("入口"+page);
            this.$emit('changeCCP', page);
        },
        getCurrentTerm(){
        getTerm().then(res =>{
            this.term = res[0]['term'];
            this.exist = true;
            if(this.exist){
                setTerm(this.username, this.term);
            }
        })
    },
    },
   
}
</script>

<style scoped>
th{
    width: 350px;
}
.entrance table{
    text-align: center;
    margin: 0 auto;
}
.entrance table tr td{
    width: 15%;
}
.li{
    color: green;
}
</style>