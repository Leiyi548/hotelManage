<template>
    <div class='header'>
        <!-- 折叠按钮 -->
        <div class='collapse-btn' @click='collapseChage'>
            <i v-if='!collapse' class='el-icon-s-fold'></i>
            <i v-else class='el-icon-s-unfold'></i>
        </div>
        <div class='logo'>酒店管理系统</div>
        <div class='header-right'>
            <div class='header-user-con'>
                <!-- 全屏显示 -->
                <div class='btn-fullscreen' @click='handleFullScreen'>
                    <el-tooltip effect='dark' :content='fullscreen ? `取消全屏` : `全屏`' placement='bottom'>
                        <i class='el-icon-rank'></i>
                    </el-tooltip>
                </div>
                <!-- 用户头像 -->
                <div class='user-avator'>
                    <img src='../../assets/img/lujianbin.jpg' />
                </div>
                <!-- 用户名下拉菜单 -->
                <el-dropdown class='user-name' trigger='hover' @command='handleCommand'>
                    <span class='el-dropdown-link'>
                        {{ username }}
                        <i class='el-icon-caret-bottom'></i>
                    </span>

                    <el-dropdown-menu slot='dropdown'>
                        <el-dropdown-item divided command='dashboard' icon='el-icon-user'>个人信息</el-dropdown-item>
                        <el-dropdown-item divided command='modifyPassword' icon='el-icon-key'> 修改密码
                        </el-dropdown-item>
                        <el-dropdown-item divided command='loginout' icon='el-icon-switch-button'>退出登录
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>

                <!-- 修改密码弹出框-->
                <el-dialog title='修改密码' :visible.sync='modifyVisible' width='30%' :before-close='handleClose'>
                    <el-form ref='form' :rules='rules' :model='form' label-width='70px'>
                        <el-form-item label='用户名' prop='name'>
                            <el-input v-model='form.name' :disabled='true'></el-input>
                        </el-form-item>
                        <el-form-item label='旧密码' prop='oldPassword2'>
                            <el-input v-model='form.oldPassword2' clearable></el-input>
                        </el-form-item>
                        <el-form-item label='新密码' prop='newPassword1'>
                            <el-input v-model='form.newPassword1' clearable></el-input>
                        </el-form-item>
                        <el-form-item label='再次确认' prop='newPassword2' @keyup.enter.nat.native='saveModify'>
                            <el-input v-model='form.newPassword2' clearable></el-input>
                        </el-form-item>
                    </el-form>
                    <span slot='footer' class='dialog-footer'>
                        <el-button @click='cancelModify'>取 消</el-button>
                        <el-button type='primary' @click='saveModify'>确 定</el-button>
                    </span>
                </el-dialog>

            </div>
        </div>
    </div>
</template>
<script>
import bus from '../common/bus';

export default {
    data() {
        // const checkPassword = (rule, value, callback) => {
        //     if (!value) {
        //         console.log('oldPassword' + this.form.oldPassword);
        //         return callback(new Error('密码不能为空'));
        //     } else if (value != this.form.oldPassword) {
        //         return callback(new Error('原密码错误'));
        //     } else {
        //         callback();
        //     }
        // };


        var checkPassword = (rule, value, callback) => {
            if (value !== this.form.oldPassword) {
                callback(new Error('密码错误，请重新输入！'));
            } else if (value.length < 3 || value > 16) {
                callback(new Error('密码长度不符！'));
            } else {
                callback();
            }
        };
        var checkPassword2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
                // password 是表单上绑定的字段
            } else if (value !== this.form.newPassword1) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            collapse: false,
            fullscreen: false,
            message: 2,
            modifyVisible: false,
            tableData: [],
            form: {
                oldPassword: '',
                oldPassword2: '',
                password: '',
                newPassword1: '',
                newPassword2: '',
                name: ''
            },
            rules: {
                oldPassword2: [{ validator: checkPassword, trigger: ['change', 'blur'] }],
                newPassword2: [{ validator: checkPassword2, trigger: 'change' }]
            }
        };
    },
    computed: {
        username() {
            let username = localStorage.getItem('ms_username');
            return username ? username : this.name;
        }
    },
    created() {
        this.getBackground();
    },
    methods: {
        // 用户名下拉菜单选择事件
        handleCommand(command) {
            if (command == 'loginout') {
                // 删除本地存放的数据
                localStorage.removeItem('ms_username');
                this.$message.success(`退出登录`);
                this.$router.push('/login');
            } else if (command == 'dashboard') {
                this.$message.success(`个人信息`);
                this.$router.push('/dashboard');
            } else {
                // this.getBackground();
                this.modifyVisible = true;
                // this.$message.success(``);
            }
        },
        // 处理对话框关闭
        handleClose() {
            this.form.password = '';
            this.form.newPassword1 = '';
            this.form.newPassword2 = '';
            this.$message.info(`取消修改`);
            this.modifyVisible = false;
        },
        // 获得当前用户的账号和密码
        getBackground() {
            this.$http.get('http://localhost:8082/getBackground').then(res => {
                // this.tableData = res.data.background;
                this.form.name = res.data.data.background.backId;
                this.form.oldPassword = res.data.data.background.password;
            });
        },

        // 修改密码
        cancelModify() {
            this.form.password = '';
            this.form.newPassword1 = '';
            this.form.newPassword2 = '';
            this.$message.info(`取消修改`);
            this.modifyVisible = false;
        },

        saveModify() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    this.$http
                        .put('http://localhost:8082/updateBackgroundPwd?' +
                            'backId=' + this.form.name
                            + '&password=' + this.form.oldPassword
                            + '&newBackId=' + this.form.name
                            + '&newPassword=' + this.form.newPassword2)
                        .then(res => {
                            console.log(res);
                            if (res.data.code === 200) {
                                //1.提示成功
                                this.$message.success(`密码成功修改，请重新登录`);
                                //2.关闭对话框
                                this.modifyVisible = false;
                                // 3. 退出
                                localStorage.removeItem('ms_username');
                                this.$router.push('/login');
                            } else {
                                this.$message.warning('修改失败');
                                return false;
                            }
                        });
                } else {
                    this.$message.warning('修改失败');
                }
            });
        },

        // 侧边栏折叠
        collapseChage() {
            this.collapse = !this.collapse;
            bus.$emit('collapse', this.collapse);
        },
        // 全屏事件
        handleFullScreen() {
            let element = document.documentElement;
            if (this.fullscreen) {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
            } else {
                if (element.requestFullscreen) {
                    element.requestFullscreen();
                } else if (element.webkitRequestFullScreen) {
                    element.webkitRequestFullScreen();
                } else if (element.mozRequestFullScreen) {
                    element.mozRequestFullScreen();
                } else if (element.msRequestFullscreen) {
                    // IE11
                    element.msRequestFullscreen();
                }
            }
            this.fullscreen = !this.fullscreen;
        }
    },
    mounted() {
        if (document.body.clientWidth < 1500) {
            this.collapseChage();
        }
    }
};
</script>
<style scoped>
.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
}

.collapse-btn {
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
}

.header .logo {
    float: left;
    width: 250px;
    line-height: 70px;
}

.header-right {
    float: right;
    padding-right: 50px;
}

.header-user-con {
    display: flex;
    height: 70px;
    align-items: center;
}

.btn-fullscreen {
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
}

.btn-bell,
.btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
}

.btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
}

.btn-bell .el-icon-bell {
    color: #fff;
}

.user-name {
    margin-left: 10px;
}

.user-avator {
    margin-left: 20px;
}

.user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}

.el-dropdown-link {
    color: #fff;
    cursor: pointer;
}

.el-dropdown-menu__item {
    text-align: center;
}
</style>
