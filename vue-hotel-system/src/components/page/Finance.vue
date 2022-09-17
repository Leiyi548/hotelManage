<template>
    <div>
        <!--  客户列表文字  -->
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item> <i class="el-icon-school"></i> 财务报表 </el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <!-- 主列表 -->
        <el-table :data="tableData" style="width: 100%" border :default-sort="{ prop:'year',order:'descending' }">
            <el-table-column
                prop="year"
                label="年份"
                align="center"
                sortable
                :filters="[
                    { text: '2021', value: '2021' },
                    { text: '2022', value: '2022' }
                ]"
                :filter-method="filterHandler"
            ></el-table-column>
            <el-table-column prop="month" label="月份" align="center"></el-table-column>
            <el-table-column prop="moneyTotal" label="收入" align="center"></el-table-column>
        </el-table>
        <p align="right">2021年共收入{{ this.tableData2[2021] }}</p>
        <p align="right">2022年共收入{{ this.tableData2[2022] }}</p>

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

        // 分页导航
        handlePageChange(val) {
            this.$set(this.costTypes, 'pageIndex', val);
            this.getAllCostType();
        },

        filterHandler(value, row, column) {
            var property = column['property'];
            return row[property] == value;
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
