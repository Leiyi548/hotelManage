# hotelManage

<p align="center">
   hotelManage，一个基于springboot+Vue的前后端分离酒店管理系统
  	<br>
    <img src="https://img.shields.io/badge/jdk-1.8+-brightgreen.svg" ></img><img src="https://img.shields.io/badge/springboot-2.3.1-brightgreen.svg" ></img><img src="https://img.shields.io/badge/mybatisplus-3.3.2-brightgreen.svg" ></img><img src="https://img.shields.io/badge/swagger-2.9.2-brightgreen.svg" ></img><img src="https://img.shields.io/badge/vue-2.6.10-brightgreen.svg" ></img><img src="https://img.shields.io/badge/elementui-2.8.2-brightgreen.svg" ></img>
</p>

## 1.前言

**hotelMange** 是由于学校的数据库课设诞生

## 2.开始步骤

### 2.1 解释

- **guest** 为后端模块，可直接用 idea 打开运行

- **vue-manage-system**，可直接用 webstorm 打开运行
- 下载/克隆两个文件夹分别用 idea & webstorm 打开

### 2.2 guest 运行

- **将 guest.sql 导入 （存在 resource 目录下的）**
- 修改 application 中的数据库配置(用户名，密码，**端口号**)
- 直接运行即可

> **数据库版本请用 5.X.X 版本**
>
> 后端运行成功后可直接访问 swagger 文档：http://localhost:80/swagger-ui.html
>
> 已将前端文件放入后端，也可直接访问：http://localhost:8080

### 2.3 vue-manage-system 运行

- 安装项目依赖

```bash
 npm install --registry=https://registry.npm.taobao.org
```

- 开启服务器，浏览器访问 http://localhost:8080

```
npm run serve
```

## 3.项目截图

### 3.1 数据库结构

![sql](https://cdn.jsdelivr.net/gh/looniink/image/sql.png)

### 3.2 登录界面

![login](https://cdn.jsdelivr.net/gh/looniink/image/hotellogin.png)

### 3.3 预定界面

![book](https://cdn.jsdelivr.net/gh/looniink/image/book.png)

### 3.4 客户界面

### ![client](https://cdn.jsdelivr.net/gh/looniink/image/client.png)

## 4. 采用技术

|     技术     |            说明            |                                          官网                                           |
| :----------: | :------------------------: | :-------------------------------------------------------------------------------------: |
|  SpringBoot  |          MVC 框架          |    [ https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)    |
| MyBatis-Plus |          ORM 框架          |                                https://mp.baomidou.com/                                 |
|  Swagger-UI  |      接口文档生成工具      | [ https://github.com/swagger-api/swagger-ui](https://github.com/swagger-api/swagger-ui) |
|    Screw     |     数据库文档生成工具     |                          https://github.com/pingfangushi/screw                          |
|    Druid     |        数据库连接池        |          [ https://github.com/alibaba/druid](https://github.com/alibaba/druid)          |
|     JWT      |        JWT 登录支持        |                              https://github.com/jwtk/jjwt                               |
|    SLF4J     |          日志框架          |                                  http://www.slf4j.org/                                  |
|    Lombok    |      简化对象封装工具      |    [ https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok)    |
|    Nginx     | HTTP 和反向代理 web 服务器 |                                    http://nginx.org/                                    |
|    Vue.js    |          前端框架          |                                   https://vuejs.org/                                    |
|  Vue-router  |          路由框架          |                                https://router.vuejs.org/                                |
|     Vuex     |      全局状态管理框架      |                                 https://vuex.vuejs.org/                                 |
|   Element    |        前端 ui 框架        |                 [ https://element.eleme.io](https://element.eleme.io/)                  |
|    Axios     |       前端 HTTP 框架       |            [ https://github.com/axios/axios](https://github.com/axios/axios)            |

## 5.开发工具

|   工具   |        说明        |                    官网                     |
| :------: | :----------------: | :-----------------------------------------: |
|   IDEA   |   Java 开发 IDE    |   https://www.jetbrains.com/idea/download   |
| WebStorm |    前端开发 IDE    |     https://www.jetbrains.com/webstorm/     |
| X-shell  | Linux 远程连接工具 |       https://xshell.en.softonic.com/       |
|  X-ftp   | Linux 文件传输工具 | https://www.netsarang.com/zh/all-downloads/ |
| Navicat  |   数据库连接工具   |         https://www.navicat.com.cn/         |

## 6. 项目要求

![img](../hotelManage/img/03.jpeg)

## 7. TODO

- [-] 系统管理员账号信息以及密码修改
  - [x] 管理员可以修改普通用户
  - [ ] 管理员修改自身密码
- [ ] 客房管理：客房预定管理
- [ ] 客户管理：订单管理
- [ ] 特色菜品介绍：经济消费报表；财务报表
- [ ] 餐桌预定管理、餐饮消费管理
- [ ] 消费页面，添加消费时间，消费人员，房间号

1. 系统功能的完善
2. 当数据量过大的时候，访问会很慢，采用 Nginx
   以及多服务的想法，将整个系统进行优化。

## 8. bug

- [ ] 房间列表数据无法在前端更新，后端是有用的。
- [ ] 客户列表数据下边栏不会更新

## 9. 分工！！！

### 9.1. 廖

- [ ] 前端 UI

### 9.2. 丁

- [ ] 管理员修改密码

### 9.3. 李

- [ ] 经济消费报表和财务报表
