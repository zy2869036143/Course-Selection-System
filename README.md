# CourseSelectionSystem
山东大学软件学院本科二年级web技术课程设计项目之一-----选课系统
本项目采用前后端分离模式开发。前端使用vue3 + element-plus + echarts + axios，后端使用spring boot + spring security + jpa + mysql。
# 课程得分
Score: 97; Rank: Number One.
# 如何运行本项目
## 步骤一
拉取源码到您的本地
## 步骤二
使用IntelliJ IDEA打开后端项目，IDEA会为您自动导入依赖。
## 步骤三
初始化数据库，运行后端项目源码中 sql文件夹下的init.sql文件，完成数据库的创建
## 步骤四
修改后端项目resources目录下的application.properties文件，将其中的
spring.datasource.url= jdbc:mysql://202.194.14.120:3306/webteach?useSSL=false
spring.datasource.username= webteach
spring.datasource.password= webteach
修改为您的数据库链接、用户名和密码
## 步骤五
打开前端项目，并进入前端项目的根目录下。
使用npm install命令安装前端依赖
## 步骤六
使用npm run serve 运行前端项目，之后在浏览器端口输入localhost:8080即可。

注：后端springboot项目已经继承了前端项目，您在进行完前四个步骤后即可在浏览器端口输入localhost:9091运行本项目。步骤五和六是单独运行前端项目的步骤。
