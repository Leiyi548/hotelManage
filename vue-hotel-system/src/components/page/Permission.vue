<template>
    <div>
        <!--  客户列表文字  -->
        <div class='crumbs'>
            <el-breadcrumb separator='/'>
                <el-breadcrumb-item><i class='el-icon-lx-profile'></i> 前台管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <!--  头部容器      -->
        <div class='container'>
            <div class='handle-box'>
                <el-button round plain type='primary' icon='el-icon-delete' class='handle-del mr10'
                           @click='delAllSelection'
                >批量删除
                </el-button>
                <el-button plain round type='primary' icon='el-icon-plus' @click='handBook'>添加前台</el-button>
            </div>

            <!-- 主列表 -->
            <el-table :data='tableData' border style='width: 100%' @selection-change='handleSelectionChange'>
                <!-- 多选框 -->
                <el-table-column type='selection' width='55' align='center'></el-table-column>
                <el-table-column prop='frontId' label='前台工号' align='center'></el-table-column>
                <el-table-column prop='name' label='前台姓名' align='center'></el-table-column>
                <el-table-column prop='password' label='前台密码' align='center'></el-table-column>
                <el-table-column prop='phone' label='前台电话' align='center'></el-table-column>

                <el-table-column label='操作' align='center'>
                    <template slot-scope='scope'>
                        <el-button size='mini' icon='el-icon-edit' @click='handleEdit(scope.$index, scope.row)'>编辑
                        </el-button>
                        <el-button
                            plain
                            size='mini'
                            type='danger'
                            icon='el-icon-delete'
                            @click='handleDelete(scope.$index, scope.row, scope.row.frontId)'
                        >删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 编辑弹出框 -->
            <el-dialog title='编辑' :visible.sync='editVisible' width='30%' :before-close='handleClose'>
                <el-form ref='form' :model='form' :rules='rules' label-width='80px'>
                    <el-form-item label='前台工号'>
                        <el-input v-model='form.frontId' :disabled='true'></el-input>
                    </el-form-item>
                    <el-form-item label='前台姓名' prop='name'>
                        <el-input v-model='form.name' clearable auto-complete='false'></el-input>
                    </el-form-item>
                    <el-form-item label='前台密码' prop='password'>
                        <el-input v-model='form.password' maxlength='16' minlength='6' clearable
                                  auto-complete='false'></el-input>
                    </el-form-item>
                    <el-form-item label='联系电话' prop='phone' @keyup.enter.native='saveEdit'>
                        <el-input v-model='form.phone' clearable></el-input>
                    </el-form-item>
                </el-form>
                <span slot='footer' class='dialog-footer'>
                    <el-button @click='cancelEdit'>取 消</el-button>
                    <el-button type='primary' @click='saveEdit'>确 定</el-button>
                </span>
            </el-dialog>

            <!-- 添加弹出框 -->
            <el-dialog title='添加' :visible.sync='addVisible' width='30%' :before-close='handleClose2'>
                <el-form ref='form' :model='form' :rules='rules' label-width='70px'>
                    <el-form-item label='前台工号' prop='frontId'>
                        <el-input v-model='form.frontId'></el-input>
                    </el-form-item>
                    <el-form-item label='前台姓名' prop='name'>
                        <el-input v-model='form.name'></el-input>
                    </el-form-item>
                    <el-form-item label='前台密码' prop='password'>
                        <el-input v-model='form.password' maxlength='16'></el-input>
                    </el-form-item>
                    <el-form-item label='联系电话' prop='phone' @keyup.enter.native='saveFront'>
                        <el-input v-model='form.phone'></el-input>
                    </el-form-item>
                </el-form>
                <span slot='footer' class='dialog-footer'>
                    <el-button @click='cancelFront'>取 消</el-button>
                    <el-button type='primary' @click='saveFront()'>确 定</el-button>
                </span>
            </el-dialog>

            <!--  分页角标设置   -->
            <div class='pagination'>
                <el-pagination
                    background
                    layout='total, prev, pager, next'
                    :current-page='fronts.pageIndex'
                    :page-size='fronts.pageSize'
                    :total='tableData.length'
                    @current-change='handlePageChange'
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import { fetchData2 } from '../../api/index';
import { createLogger } from 'vuex';

/*
http://localhost:8082/getAllFront
fronts
*/

export default {
    name: 'RoomType',
    data() {
        // 验证前台id
        const checkId = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('id号不能为空'));
            } else {
                // 6位数，数字
                const reg = /^[0-9][0-9]{3}$/;
                if (reg.test(value)) {
                    callback();
                } else {
                    return callback(new Error('请输入正确的id号(4位)'));
                }
            }
        };

        // 验证手机号码
        const checkPhone = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('手机号不能为空'));
            } else {
                // 总结需要11位
                // 第一位必须是1
                // 第二个是34578
                // 其余任意一个数字
                const reg = /^1[0-9]\d{9}$/;
                if (reg.test(value)) {
                    callback();
                } else {
                    return callback(new Error('请输入正确的手机号(11位，开头为1)'));
                }
            }
        };

        // 验证姓名
        const checkName = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('姓名不能为空'));
            } else {
                // 中文
                const reg = /^[\u4e00-\u9fa5]{2,4}$/;
                if (reg.test(value)) {
                    callback();
                } else {
                    return callback(new Error('请输入正确的姓名（中文2-4位）'));
                }
            }
        };

        // 验证密码
        const checkPassword = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('密码不能为空'));
            } else {
                // 密码必须为6-18位字母、数字、特殊符号的：
                const reg1 = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>?,.\/]).{6,18}$/;
                // 密码必须为6-18位字母、数字
                const reg2 = /^(?![^a-zA-Z]+$)(?!\D+$)/;
                if (reg2.test(value)) {
                    callback();
                } else {
                    return callback(new Error('请输入正确的密码（6-18位字母、数字）'));
                }
            }
        };

        return {
            fronts: {
                frontId: '',
                name: '',
                password: '',
                phone: '',
                pageIndex: 1, //当前在第几页
                pageSize: 50 //每页展示多少条数据
            },

            // form: {},
            form: {
                frontId: '',
                name: '',
                pageIndex: '',
                password: '',
                phone: ''
            },
            //
            rules: {
                phone: [{ validator: checkPhone, trigger: ['change', 'blur'] }],
                password: [{ validator: checkPassword, trigger: ['change', 'blur'] }],
                name: [{ validator: checkName, trigger: ['change', 'blur'] }],
                frontId: [{ validator: checkId, trigger: ['change', 'blur'] }]
                // password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
            },

            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            addVisible: false,
            pageTotal: 50, //总共有多少条数据
            idx: -1,
            id: -1
        };
    },
    created() {
        this.getAllFront();
    },
    methods: {
        //获取所有前台
        getAllFront() {
            this.$http.get('http://localhost:8082/getAllFront').then(res => {
                //console.log(res);
                if (res.data.data == null) {
                    this.tableData = [];
                } else {
                    this.tableData = res.data.data.fronts;
                }
            });
        },


        handleClose(done) {
            this.$message.info('取消编辑');
            this.getAllFront();
            done();
        },

        handleClose2(done) {
            this.$message.info('取消添加');
            this.getAllFront();
            done();
        },

        // getFrontLength(){
        //
        //  },

        //删除预定信息
        handleDelete(index, row, typeId) {
            if (localStorage.getItem('ms_username') === 'admin') {
                // 二次确认删除
                this.$confirm('确定要删除吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        this.$http.delete('http://localhost:8082/deleteFront?id=' + typeId).then(res => {
                            console.log(res);
                            if (res.data.code === 200) {
                                this.$message.success('删除成功');
                                this.tableData.splice(index, 1);
                                this.getAllFront();
                            } else {
                                this.$message.warning('删除失败');
                            }
                        });
                    })
                    .catch(() => {
                    });
            } else {
                this.$message.error('抱歉您没有该权限');
            }
        },

        //添加预定信息框
        handBook() {
            if (localStorage.getItem('ms_username') === 'admin') {
                this.addVisible = true;
                this.form = {};
            } else {
                this.$message.error('抱歉您没有该权限');
            }
        },

        //添加前台
        saveFront() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    // 验证成功要做的事
                    this.$http
                        .post(
                            'http://localhost:8082/addFront?frontId=' +
                            this.form.frontId +
                            '&name=' +
                            this.form.name +
                            '&password=' +
                            this.form.password +
                            '&phone=' +
                            this.form.phone
                        )
                        .then(res => {
                            //console.log(res);
                            if (res.data.code === 200) {
                                //1.提示成功
                                this.$message.success(`添加成功`);
                                //2.关闭对话框
                                this.addVisible = false;
                                //3.更新视图
                                this.getAllFront();
                                //4.清空输入文本框
                                this.form = {};
                            } else {
                                this.$message.warning('添加失败');
                            }
                        });
                    this.editVisible = false;

                } else {
                    // 不成功出现显示
                    this.$message.warning('添加失败');
                    return false;
                }
            });
            //console.log(this.form);
        },

        cancelFront() {
            this.$message.info('取消添加');
            // 1.关闭对话框
            this.addVisible = false;
            // 2.更新数据
            this.getAllFront();
        },

        // 编辑操作
        handleEdit(index, row) {
            if (localStorage.getItem('ms_username') === 'admin') {
                this.idx = index;
                this.form = row;
                this.editVisible = true;
            } else {
                this.$message.error('抱歉您没有该权限');
            }
        },

        //编辑前台
        saveEdit() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    this.$http
                        .post(
                            'http://localhost:8082/addFront?frontId=' +
                            this.form.frontId +
                            '&name=' +
                            this.form.name +
                            '&password=' +
                            this.form.password +
                            '&phone=' +
                            this.form.phone
                        )
                        .then(res => {
                            //console.log(res);
                            if (res.data.code === 200) {
                                //1.提示成功
                                this.$message.success(`编辑成功`);
                                //2.关闭对话框
                                this.editVisible = false;
                                //3.更新视图
                                this.getAllFront();
                                //4.清空输入文本框
                                this.form = {};
                            } else {
                                this.$message.warning('编辑失败');
                            }
                        });
                } else {
                    this.$message.warning('编辑失败');
                }
            });
        },

        cancelEdit() {
            this.$message.info('取消编辑');
            // 1.关闭对话框
            this.editVisible = false;
            // 2.更新数据
            this.getAllFront();
        },

        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
            this.delList = [];
            for (let i = 0; i < val.length; i++) {
                this.delList.push(val[i].frontId);
            }
        },

        getJsonLength() {
            var length = 0;
            for (var item in this.tableData) {
                length++;
            }
            return length;
        },

        delAllSelection() {
            const length = this.multipleSelection.length;
            var i = 0;
            if (length == 0) {
                this.$message.error('未选择内容');
                return;
            }
            // let str = '';
            // this.delList = this.delList.concat(this.multipleSelection);
            // 获得被删除的人名总和
            // for (let i = 0; i < length; i++) {
            //     str += this.multipleSelection[i].name;
            // }
            for (i; i < this.delList.length; i++) {
                this.$http.delete('http://localhost:8082/deleteFront?id=' + this.delList[i]).then(res => {
                    if (res.data.code === 200) {
                        this.$message.success('删除成功');
                        this.getAllFront();
                    } else {
                        this.$message.warning('删除失败');
                        this.getAllFront();
                        return;
                    }
                });
            }
            // this.$message.error(`删除了 ${str}`);
            // 重制multipleSelection
            this.multipleSelection = [];
            // 更新删除后的数据
            if (this.getJsonLength() == this.delList.length) {
                this.tableData = [];
            } else {
                this.getAllFront();
            }
        },

        // 分页导航
        handlePageChange(val) {
            this.$set(this.fronts, 'pageIndex', val);
            this.getAllFront();
        }
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}

.table {
    width: 100%;
    font-size: 14px;
}

.red {
    color: #ff0000;
}

.mr10 {
    margin-right: 10px;
}
</style>
