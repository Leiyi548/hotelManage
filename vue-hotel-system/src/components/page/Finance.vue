<template>
    <div>
        <!--  客户列表文字  -->
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item> <i class="el-icon-school"></i> 财务报表 </el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <!--  头部容器      -->
        <div class="container">
            <div class="handle-box">
                &nbsp;
                <el-input
                    round
                    v-model="costTypes.name"
                    placeholder="请输入想要搜索的月份,直接回车即可"
                    class="handle-input mr10"
                    clearable
                    prefix-icon="el-icon-search"
                    @clear="handleSearch"
                    @keydown.enter.native="handleSearch"
                >
                </el-input>
            </div>

            <!-- 主列表 -->
            <el-table :data="tableData" style="width: 100%" border>
                <el-table-column prop="year" label="年份" align="center"></el-table-column>
                <el-table-column prop="month" label="月份" align="center"></el-table-column>
                <el-table-column prop="moneyTotal" label="收入" align="center"></el-table-column>
            </el-table>
            <p>2021年共收入{{ this.tableData2[2021] }}</p>
            <p>2022年共收入{{ this.tableData2[2022] }}</p>

            <!--  分页角标设置   -->
            <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="costTypes.pageIndex"
                    :page-size="costTypes.pageSize"
                    :total="tableData.length"
                    @current-change="handlePageChange"
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Cost',
    data() {
        return {
            costTypes: {
                id: '',
                name: '',
                money: '',
                pageSize: 50
            },

            value: '',
            tableData: [],
            tableData2: [],
            multipleSelection: [],
            delList: [],
            switchValue: true,
            editVisible: false,
            addVisible: false,
            pageTotal: 50, //总共有多少条数据
            form: {},
            idx: -1,
            id: -1
        };
    },
    created() {
        this.getAllfinancialStatement2();
    },
    methods: {
        //获取所有消费信息
        getAllCostType() {
            this.$http.get('http://localhost:8082/getAllCostType').then(res => {
                //console.log(res);
                this.tableData = res.data.data.costTypes;
            });
        },

        getAllfinancialStatement2() {
            this.$http.get('http://localhost:8082/financialStatement2').then(res => {
                this.tableData = res.data.data.mvo;
                this.tableData2 = res.data.data.ymap;
                // this.tableData2 = [res.data.data.ymap];
                console.log(this.tableData2[2021]);
                console.log(this.tableData2[2022]);
            });
        },

        // 编辑
        saveCostEdit() {
            //console.log(this.form);
            this.$http
                .post('http://localhost:8082/addCostType?id=' + this.form.id + '&money=' + this.form.money + '&name=' + this.form.name)
                .then(res => {
                    // console.log(res);
                    if (res.data.code === 200) {
                        //1.提示成功
                        this.$message.success(`修改成功`);
                        //2.关闭对话框
                        this.editVisible = false;
                        //3.更新视图
                        this.getAllCostType();
                        //4.清空输入文本框
                        this.form = {};
                    } else {
                        this.$message.warning('修改失败');
                    }
                });
        },

        // 添加
        saveCost() {
            // console.log(this.form);
            this.$http
                .post('http://localhost:8082/addCostType?id=0' + '&money=' + this.form.money + '&name=' + this.form.name)
                .then(res => {
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

        //删除预定信息
        handleDelete(index, row, costId) {
            if (localStorage.getItem('ms_username') === 'admin') {
                // 二次确认删除
                this.$confirm('确定要删除吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        this.$http.delete('http://localhost:8082//deleteCostType?id=' + costId).then(res => {
                            if (res.data.code === 200) {
                                this.$message.success('删除成功');
                                this.tableData.splice(index, 1);
                                this.getAllCostType();
                            } else {
                                this.$message.warning('删除失败');
                            }
                        });
                    })
                    .catch(() => {});
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
                .then(res => {
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
            this.$http.get('http://localhost:8082/getCostTypeByName?name=' + this.costTypes.name).then(res => {
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
        },

        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = '';
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].roomId + ' ';
            }
            this.$message.error(`删除了${str}`);
            this.multipleSelection = [];
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
