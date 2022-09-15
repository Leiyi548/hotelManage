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
                <el-table-column prop="name" label="消费项目" align="center"></el-table-column>
                <el-table-column prop="money" label="消费总金额" align="center"></el-table-column>
            </el-table>

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
        this.getAllCostType();
    },
    methods: {
        //获取所有消费信息
        getAllCostType() {
            this.$http.get('http://localhost:8082/getAllCostType').then(res => {
                //console.log(res);
                this.tableData = res.data.data.costTypes;
            });
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
