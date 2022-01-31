<template>
     <div class="ma">
        <div class="left">
            <ul class="ctc">
                <li><span class="compul"></span>必修</li>
                <li><span class="limit"></span>限修</li>
                <li><span class="common"></span>任选</li>
                <li><span class="gc"></span>公选</li>
                <li><span class="chose"></span>选修</li>
                <li><span class="commui"></span>交流</li>
                <li><span class="other"></span>其他</li>
            </ul>
            <table  id="schedule">
                <th ><p>周\节次</p></th>
                <th ><p>星期一</p></th>
                <th ><p>星期二</p></th>
                <th ><p>星期三</p></th>
                <th ><p>星期四</p></th>
                <th ><p>星期五</p></th>
                <th ><p>星期六</p></th>
                <th ><p>星期日</p></th>

              <tr>
                <td  class="lable"><p>第一大节</p></td>
                <td id="11"></td>
                <td id="12"></td>
                <td id="13"></td>
                <td id="14"></td>
                <td id="15"></td>
                <td id="16"></td>
                <td id="17"></td>
              </tr>
               <tr>
                <td  class="lable"><p>第二大节</p></td>
                <td id="21"></td>
                <td id="22"></td>
                <td id="23"></td>
                <td id="24"></td>
                <td id="25"></td>
                <td id="26"></td>
                <td id="27"></td>
              </tr>
               <tr>
                <td  class="lable"><p>第三大节</p></td>
                <td id="31"></td>
                <td id="32"></td>
                <td id="33"></td>
                <td id="34"></td>
                <td id="35"></td>
                <td id="36"></td>
                <td id="37"></td>
              </tr>
               <tr>
                <td class="lable"><p>第四大节</p></td>
                <td id="41"></td>
                <td id="42"></td>
                <td id="43"></td>
                <td id="44"></td>
                <td id="45"></td>
                <td id="46"></td>
                <td id="47"></td>
              </tr>
               <tr>
                <td class="lable"><p>第五大节</p></td>
                <td id="51"></td>
                <td id="52"></td>
                <td id="53"></td>
                <td id="54"></td>
                <td id="55"></td>
                <td id="56"></td>
                <td id="57"></td>
              </tr>
                <tr>
                    <td class="lable"><p>备注</p></td>
                    <td colspan="7"></td>
                </tr>
            </table>
        </div>
     </div>
</template>
<script>
import {getmine} from '../service/genServ.js'
import { mapState } from 'vuex'

export default {
    name: 'courseTable',
    data(){
        return{
        courseTable: [],
        // map数据库课程属性和css类名
        map:{
                "必修": "c-compul",
                "限选": "c-limit",
                "任选": "c-common",
                "公选": "c-gc",
                "选修": "c-chose",
                "交流": "c-commui",
                "其他": "c-other",
            }
        }
    },
    computed: {
        ...mapState([
      'username', 'jwtToken'
        ])
    },
    created(){
        this.getMyCourse();
        // 下面这个方法修改了dom对象的元素，而dom对象在creat阶段尚未生成，故调用会出错。
        // this.createTable();
    },
    // 钩子函数，DOM加载完毕之后执行
    // mounted() {
    //     this.$nextTick(this.createTable())      
    // },
    // watch:{
    //     'courseTable': function(){
    //         this.$$nextTick(this.createTable());
    //     }
    // },
    methods: {
    // 获取学生已经选择的课程
        getMyCourse(){
            // 清空
            this.courseTable=[];
            getmine(this.username).then(res => {
          // (判断字典下一项是否为空千万不能用!==,只能用!=)
            for(var i=0; res[i+'']!=null;++i){
                this.courseTable.push(res[i+'']);
            }
            this.createTable();
            })

        },
        createTable(){
            // var td = document.getElementById("11");
            // td.className = "c-compul";
            // td.innerHTML = "c-compul";
            // alert(this.courseTable.length);
            for(var i=0; i < this.courseTable.length; ++i){
                var time = this.courseTable[i]['time'];
                // alert(time);
                var courseName = this.courseTable[i]['name'];
                var teacher = this.courseTable[i]['teacher'];
                var places = this.courseTable[i]['place'].split(";");
                var times = time.split(";")
                for(var p=1; p< times.length; p++){
                    var grid = document.getElementById(times[p]);
                    grid.className = this.map[this.courseTable[i]['property']];
                    //  grid.innerHTML = "<p>" + courseName + "</p>" + "<div class='grid'>"+ times[p] +"</div>"+
                    //                 "<div class='grid' size='1'>"+teacher+"</div>" + "<div class='grid'> " + places[p-1]+"</div>";
                    grid.innerHTML = "<p>" + courseName + "</p>" +
                                    "<div class='grid' size='1'>"+teacher+"</div>" + "<div class='grid'> " + places[p-1]+"</div>";
                    grid.style.borderRadius='8px';
                }
            }
        },
    }
}
</script>
<style scoped>
p{
    color: white;
}
th{
    background-color: rgb(124,107,213)
    
}
.lable{
    background-color:  rgb(120,110,220);
}
.grid{
    size: 12px;
    color: #565656;
}
.ma{
    height: 90%;
}
.ma .left{
    display: inline-block;
    vertical-align: top;
    width: 70%;
    height: 100%;
}

.ma .left ul{
    width: 100%;
    /* height: 3%; */
    text-align: center;
}

.ma .left table{
    width: 1000px;
    height: 95%;
}

.ma .left table tr td{
    color: white;
    text-align: center;
}


.ma .left table th{
    height: 5%;
    color: white;
}

.ma .left table tr td{
    color: white;
    width: 300px;
    height: 100px;
    text-align: center;
}




.ctc{
    text-align: left;
}

.ctc li{
    color: white;
    vertical-align: top;
    font-size: 7px;
    padding-left: 5px;
    display: inline;
}

.compul{
    display: inline-block;
    width: 1rem;
    height: 1rem;
    border-radius: 10px;
    margin-right: 0.25rem;
    background-color: #FBC2C2;
}

.c-compul{
    background-color: #FBC2C2;
}

.limit{
    display: inline-block;
    width: 1rem;
    height: 1rem;
    border-radius: 10px;
    margin-right: 0.25rem;
    background-color: #CDDDFC;
}

.c-limit{
    background-color: #CDDDFC;
}

.common{
    display: inline-block;
    width: 1rem;
    height: 1rem;
    border-radius: 10px;
    margin-right: 0.25rem;
    /* width: 2px;
    height: 2px; */
    background-color: #BEEDF2;
    margin: 0px 3px 0px 0px;
}

.c-common{
    background-color: #BEEDF2;
}

.gc{
    display: inline-block;
    width: 1rem;
    height: 1rem;
    border-radius: 10px;
    margin-right: 0.25rem;
    /* width: 2px;
    height: 2px; */
    background-color: #FCD9B5;
    margin: 0px 3px 0px 0px;
}
.c-gc{
    background-color: #FCD9B5;
}
.chose{
    display: inline-block;
    width: 1rem;
    height: 1rem;
    border-radius: 10px;
    margin-right: 0.25rem;
    /* width: 2px;
    height: 2px; */
    background-color: #C8C6F6;
    margin: 0px 3px 0px 0px;
}
.c-chose{
    background-color: #C8C6F6;
}
.commui{
    display: inline-block;
    width: 1rem;
    height: 1rem;
    border-radius: 10px;
    margin-right: 0.25rem;
    /* width: 2px;
    height: 2px; */
    background-color: #f7B3FF;
    margin: 0px 3px 0px 0px;
}
.c-commui{
    background-color: #f7B3FF;
}

.other{
    display: inline-block;
    width: 1rem;
    height: 1rem;
    border-radius: 10px;
    margin-right: 0.25rem;
    /* width: 2px;
    height: 2px; */
    background-color: #F7F7F8;
    margin: 0px 3px 0px 0px;
}
.c-other{
    background-color: #F7F7F8;

}

table{
    
  border-style: solid;
}
th, td{
    border: 1px;
      border-style: solid;

}
.ma .message-container{
    height: 100%;
    padding-left: 5%;
    display: inline-block;
}
.ma .message-container .title{
    color: white;
}

.ma .message-container .message-title{
    color: red;
}
</style>
