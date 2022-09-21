<template>
    <div>
        <!--  客户列表文字  -->
        <div class='crumbs'>
            <el-breadcrumb separator='/'>
                <el-breadcrumb-item><i class='el-icon-lx-recharge'></i> 消费管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <!--  头部容器      -->
        <div class='container'>
            <div class='handle-box'>
                <el-button round plain type='primary' icon='el-icon-delete' class='handle-del mr10'
                           @click='delAllSelection'
                >批量删除
                </el-button>
                <el-button plain round type='primary' icon='el-icon-plus' @click='handBook'>添加项目</el-button>
                &nbsp;
                <el-input
                    round
                    v-model='costTypes.name'
                    placeholder='请输入消费名称搜索,直接回车即可'
                    class='handle-input mr10'
                    clearable
                    prefix-icon='el-icon-search'
                    @clear='handleSearch'
                    @keydown.enter.native='handleSearch'
                >
                </el-input>
            </div>

            <!-- 主列表 -->
            <el-table :data='tableData' style='width: 100%' border @selection-change='handleSelectionChange'>
                <el-table-column type='selection' width='55' align='center'></el-table-column>
                <el-table-column prop='name' label='消费项目' align='center'></el-table-column>
                <el-table-column prop='money' label='消费金额' align='center'></el-table-column>

                <el-table-column label='操作' align='center'>
                    <template slot-scope='scope'>
                        <el-button size='mini' icon='el-icon-edit' @click='handleEdit(scope.$index, scope.row)'>编辑
                        </el-button>
                        <el-button
                            plain
                            size='mini'
                            type='danger'
                            icon='el-icon-delete'
                            @click='handleDelete(scope.$index, scope.row, scope.row.id)'
                        >删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 编辑弹出框 -->
            <el-dialog title='编辑' :visible.sync='editVisible' width='30%' :before-close='handleClose'>
                <el-form ref='form' :model='form' :rules='rules' label-width='70px'>
                    <el-form-item label='消费项目' prop='name'>
                        <el-input v-model='form.name'></el-input>
                    </el-form-item>
                    <el-form-item label='消费金额' prop='money' @keyup.enter.native='saveCostEdit'>
                        <el-input v-model='form.money'></el-input>
                    </el-form-item>
                </el-form>
                <span slot='footer' class='dialog-footer'>
                    <el-button @click='cancelCostEdit'>取 消</el-button>
                    <el-button type='primary' @click='saveCostEdit'>确 定</el-button>
                </span>
            </el-dialog>

            <!-- 添加弹出框 -->
            <el-dialog title='添加' :visible.sync='addVisible' width='30%' :before-close='handleClose2'>
                <el-form ref='form' :model='form' :rules='rules' label-width='70px'>
                    <el-form-item label='消费项目' prop='name'>
                        <el-input v-model='form.name'></el-input>
                    </el-form-item>
                    <el-form-item label='消费金额' prop='money'>
                        <el-input v-model='form.money'></el-input>
                    </el-form-item>
                </el-form>
                <span slot='footer' class='dialog-footer'>
                    <el-button @click='cancelCost'>取 消</el-button>
                    <el-button type='primary' @click='saveCost'>确 定</el-button>
                </span>
            </el-dialog>

            <!--  分页角标设置   -->
            <div class='pagination'>
                <el-pagination
                    background
                    layout='total, prev, pager, next'
                    :current-page='costTypes.pageIndex'
                    :page-size='costTypes.pageSize'
                    :total='tableData.length'
                    @current-change='handlePageChange'
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import { createLogger } from 'vuex';

export default {
    name: 'Cost',
    data() {
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

        const checkMoney = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('价格不能为空'));
            } else {
                // 1-9 开头，后跟是 0-9，可以跟小数点，但小数点后要带上 1-2 位小数，类似 2,2.0,2.1,2.22 等
                const reg = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/;
                if (reg.test(value)) {
                    callback();
                } else {
                    return callback(new Error('请输入正确的价格（1-9 开头，后跟是 0-9，可以跟小数点，但小数点后要带上 1-2 位小数）'));
                }
            }
        };
        return {
            costTypes: {
                id: '',
                name: '',
                money: '',
                pageSize: 50
            },

            value: '',
            tableData: [],
            multipleSelection: [],
            delList: [],
            switchValue: true,
            editVisible: false,
            addVisible: false,
            pageTotal: 50, //总共有多少条数据
            form: {
                name: '',
                money: ''
            },
            idx: -1,
            id: -1,
            rules: {
                name: [{ validator: checkName, trigger: ['change', 'blur'] }],
                money: [{ validator: checkMoney, trigger: ['change', 'blur'] }]
            }
        };
    },
    created() {
        this.getAllCostType();
    },
    methods: {
        //获取所有消费信息
        getAllCostType() {
            this.$http.get('http://localhost:8082/getAllCostType').then((res) => {
                if (res.data.data == null) {
                    this.tableData = [];
                } else {
                    this.tableData = res.data.data.costTypes;
                }
            });
        },

        cancelCostEdit() {
            this.$message.info('取消编辑');
            // 1.关闭对话框
            this.editVisible = false;
            // 2.更新数据
            this.getAllCostType();
        },

        // 编辑
        saveCostEdit() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    this.$http
                        .post('http://localhost:8082/addCostType?id=' + this.form.id + '&money=' + this.form.money + '&name=' + this.form.name)
                        .then((res) => {
                            // console.log(res);
                            if (res.data.code === 200) {
                                //1.提示成功
                                this.$message.success(`编辑成功`);
                                //2.关闭对话框
                                this.editVisible = false;
                                //3.更新视图
                                this.getAllCostType();
                                //4.清空输入文本框
                                this.form = {};
                            } else {
                                this.$message.warning('编辑失败');
                            }
                        });
                } else {
                    // 不成功出现显示
                    this.$message.warning('编辑失败');
                    return false;
                }
            });
        },

        cancelCost() {
            this.$message.info('取消添加');
            // 1.关闭对话框
            this.addVisible = false;
            // 2.更新数据
            this.getAllCostType();
        },

        // 添加
        saveCost() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    this.$http
                        .post('http://localhost:8082/addCostType?id=0' + '&money=' + this.form.money + '&name=' + this.form.name)
                        .then((res) => {
                            //console.log(res);
                            if (res.data.code === 200) {
                                //1.提示成功
                                this.$message.success(`添加成功`);
                                //2.关闭对话框
                                this.addVisible = false;
                                //3.更新视图
                                this.getAllCostType();
                                //4.清空输入文本框
                                this.form = {};
                            } else {
                                this.$message.warning('添加失败');
                            }
                        });
                } else {
                    this.$message.warning('添加失败');
                    return false;
                }
            });
        },

        //删除预定信息
        handleDelete(index, row, costId) {
            if (localStorage.getItem('ms_username') === 'admin') {
                // 二次确认删除
                this.$confirm('确定要删除吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        this.$http.delete('http://localhost:8082//deleteCostType?id=' + costId).then((res) => {
                            if (res.data.code === 200) {
                                this.$message.success('删除成功');
                                this.tableData.splice(index, 1);
                                this.getAllCostType();
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

        //添加消费信息框
        handBook() {
            if (localStorage.getItem('ms_username') === 'admin') {
                this.addVisible = true;
                this.form = {};
            } else {
                this.$message.error('抱歉您没有该权限');
            }
        },

        //添加消费信息
        saveBook() {
            //console.log(this.form);
            this.$http
                .post('http://localhost:8082/addCostType?id=' + this.form.id + '&money=' + this.form.money + '&name=' + this.form.name)
                .then((res) => {
                    //console.log(res);
                    if (res.data.code === 200) {
                        //1.提示成功
                        this.$message.success(`添加成功`);
                        //2.关闭对话框
                        this.addVisible = false;
                        //3.更新视图
                        this.getAllCostType();
                        //4.清空输入文本框
                        this.form = {};
                    } else {
                        this.$message.warning('添加失败');
                    }
                });
        },

        // 关闭编辑操作
        handleClose(done) {
            this.$message.info('取消编辑');
            this.getAllCostType();
            done();
        },

        handleClose2(done) {
            this.$message.info('取消添加');
            this.getAllCostType();
            done();
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

        // 搜索功能
        handleSearch() {
            //console.log(this.costTypes.name);
            this.$http.get('http://localhost:8082/getCostTypeByName?name=' + this.costTypes.name).then((res) => {
                //console.log(res.data);
                if (res.data.code === 200) {
                    this.tableData = res.data.data.costTypes;
                } else {
                    this.$message.error('抱歉没有该数据');
                }
            });
        },

        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
            this.delList = [];
            console.log(val);
            for (let i = 0; i < val.length; i++) {
                this.delList.push(val[i].id);
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
            // for (let i = 0; iCostTypCostType; i++) {
            //     str += this.multipleSelection[i].name + ' ';
            // }
            // this.$message.error(`删除了${str}`);
            for (i; i < this.delList.length; i++) {
                this.$http.delete('http://localhost:8082/deleteCostType?id=' + this.delList[i]).then(res => {
                    if (res.data.code === 200) {
                        this.$message.success('删除成功');
                        this.getAllCostType();
                    } else {
                        this.$message.warning('删除失败');
                        this.getAllCostType();
                    }
                });
            }
            // 重制multipleSelection
            this.multipleSelection = [];
            // 更新删除后的数据
            if (this.getJsonLength() == this.delList.length) {
                this.tableData = [];
            } else {
                this.getAllCostType();
            }
        },

        // 分页导航
        handlePageChange(val) {
            this.$set(this.costTypes, 'pageIndex', val);
            this.getAllCostType();
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
    margin-right: 2px;
}
</style>
