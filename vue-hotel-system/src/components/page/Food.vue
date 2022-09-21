<template>
    <div>
        <!--  客户列表文字  -->
        <div class='crumbs'>
            <el-breadcrumb separator='/'>
                <el-breadcrumb-item><i class='el-icon-fork-spoon'></i> 餐桌列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class='container'>
            <div class='handle-box'>
                <el-button round plain type='primary' icon='el-icon-delete' class='handle-del mr10'>批量删除</el-button>
                &nbsp;&nbsp;
                <el-input
                    round
                    v-model='foodMsgs.name'
                    placeholder='请输入客户名字搜索,直接回车即可'
                    class='handle-input mr10'
                    clearable
                    prefix-icon='el-icon-search'>
                    <!--                @clear='handleSearch'-->
                    <!--                @keydown.enter.native='handleSearch'-->

                </el-input>
                <el-button plain round style='float: right' type='primary' icon='el-icon-plus' @click='handBook'>
                    添加预定
                </el-button>
            </div>
            <!-- 主列表-->
            <el-table :data='tableData' style='width: 100%' height='500'>
                <el-table-column type='selection' width='55' align='center'></el-table-column>
                <el-table-column prop='name' label='用户名' align='center' width='200'></el-table-column>
                <el-table-column prop='telephone' label='电话号码' align='center' width='200'></el-table-column>
                <el-table-column prop='eater_num' label='人数' align='center' width='200'></el-table-column>
                <el-table-column prop='desk_num' label='桌号' align='center' width='200'></el-table-column>
                <el-table-column label='状态' align='center' width='200'>
                    <template slot-scope='scope'>
                        <!--未订餐0 红色，订餐1 蓝色 -->
                        <el-tag
                            v-if='scope.row.state === 0'
                            :type="scope.row.state === 0 ? 'danger' : 'success'"
                        >未订餐
                        </el-tag>
                        <el-tag
                            v-if='scope.row.state === 1'
                            :type="scope.row.state === 1 ? 'success' : 'danger'"
                        >已订餐
                        </el-tag>
                    </template>
                </el-table-column>
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
                        <el-button
                            size='mini'
                            type='primary'
                            icon='el-icon-bell'
                            plain
                            @click='handleOrder(scope.$index, scope.row, scope.row.rank)'
                        >点餐
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!--添加弹出框-->
            <el-dialog title='添加' :visible.sync='addVisible' width='30%' :before-close='handleClose2'>
                <el-form ref='form' :model='form' label-width='70px' :rules='rules'>
                    <el-form-item label='用户名' prop='name'>
                        <el-input v-model='form.name'></el-input>
                    </el-form-item>
                    <el-form-item label='电话号码' prop='telephone'>
                        <el-input v-model='form.telephone'></el-input>
                    </el-form-item>
                    <el-form-item label='人数' prop='eater_num'>
                        <el-input v-model='form.eater_num'></el-input>
                    </el-form-item>
                    <el-form-item label='桌号' prop='desk_num'>
                        <el-input v-model='form.desk_num'></el-input>
                    </el-form-item>
                </el-form>
                <span slot='footer' class='dialog-footer'>
                    <el-button @click='addVisible = false'>取 消</el-button>
                    <el-button type='primary' @click='saveBook'>确 定</el-button>
                </span>
            </el-dialog>
            <!--添加弹出框结束-->

            <!--编辑弹出框-->
            <el-dialog title='编辑' :visible.sync='editVisible' width='30%' :before-close='handleClose'>
                <el-form ref='form' :model='form' label-width='70px'>
                    <el-form-item label='用户名' prop='name'>
                        <el-input v-model='form.name'></el-input>
                    </el-form-item>
                    <el-form-item label='电话号码' prop='telephone'>
                        <el-input v-model='form.telephone'></el-input>
                    </el-form-item>
                    <el-form-item label='人数' prop='eater_num'>
                        <el-input v-model='form.eater_num'></el-input>
                    </el-form-item>
                    <el-form-item label='桌号' prop='eater_num'>
                        <el-input v-model='form.desk_num'></el-input>
                    </el-form-item>
                </el-form>
                <span slot='footer' class='dialog-footer'>
                    <el-button @click='addVisible = false'>取 消</el-button>
                    <el-button type='primary' @click='saveReserve'>确 定</el-button>
                </span>
            </el-dialog>
            <!--编辑弹出框结束-->

            <!--点餐弹出框-->
            <el-dialog title='点餐' :visible.sync='orderVisible' width='60%' :before-close='handleClose3'>
                <el-table :data='foodTable' style='width: 100%' height='500' @selection-change='handleOrderSelection'>
                    <el-table-column type='selection' width='55' align='center'></el-table-column>
                    <el-table-column label='菜品图片' width='200px' align='center'>
                        <template width='40px' slot-scope='scope'>
                            <img style='width:80px;height:80px;border:none;' :src='scope.row.imgUrl'>
                        </template>
                    </el-table-column>
                    <el-table-column prop='foodName' label='菜品名称' align='center' width='200'></el-table-column>
                    <el-table-column prop='foodMoney' label='菜品金额' align='center' width='200'></el-table-column>
                    <el-table-column prop='num' label='点餐数量' width='200px' align='center'>
                        <template width='40px' slot-scope='scope'>
                            <el-input-number v-model='scope.row.foodNum' :min='1' :max='10'
                                             @change='handleOrderNumChange(scope.row.foodMoney,scope.row.selected)' />
                        </template>
                    </el-table-column>
                </el-table>
                <span slot='footer' class='dialog-footer'>
                <p align='right' style='margin:10px;color:red; '>￥{{ foodMoneySum }}</p>
                    <el-button @click='handleClose3'>取 消</el-button>
                    <el-button type='primary' @click='saveBook'>确 定</el-button>
                </span>
            </el-dialog>
            <!--点餐弹出框结束-->
        </div>
    </div>
</template>

<script>
export default {
    name: 'Food',
    // reference: https://blog.csdn.net/yy2017220302028/article/details/104048783
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
        // 检测人数
        const checkPeopleNum = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('人数不能为空'));
            } else {
                // 总结需要11位
                // 第一位必须是1
                // 第二个是34578
                // 其余任意一个数字
                const reg = /^[0-9]*[1-9][0-9]*$/;
                if (reg.test(value)) {
                    callback();
                } else {
                    return callback(new Error('请输入数字'));
                }
            }
        };
        // 检测桌号
        const checkDeskNum = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('桌号不能为空'));
            } else {
                // 总结需要11位
                // 第一位必须是1
                // 第二个是34578
                // 其余任意一个数字
                const reg = /^[0-9]*[1-9][0-9]*$/;
                if (reg.test(value)) {
                    callback();
                } else {
                    return callback(new Error('请输入数字'));
                }
            }
        };
        return {
            addVisible: false,
            editVisible: false,
            orderVisible: false,
            form: [],
            foodMsgs: [],
            tableData: [
                {
                    name: '张无忌',
                    telephone: '19179326967',
                    eater_num: '3',
                    desk_num: '4',
                    state: 1
                },
                {
                    name: '张无忌',
                    telephone: '19179326967',
                    eater_num: '3',
                    desk_num: '4',
                    state: 0
                },
                {
                    name: '张无忌',
                    telephone: '19179326967',
                    eater_num: '3',
                    desk_num: '4',
                    state: 0
                },
                {
                    name: '张无忌',
                    telephone: '19179326967',
                    eater_num: '3',
                    desk_num: '4',
                    state: 0
                },
                {
                    name: '张无忌',
                    telephone: '19179326967',
                    eater_num: '3',
                    desk_num: '4',
                    state: 0
                }
            ],
            foodTable: [
                {
                    foodName: '手撕包菜',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816635021316021.jpg?x-oss-process=style/c320',
                    foodMoney: 25,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '小炒杏鲍菇',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816635020885151.jpg?x-oss-process=style/c320',
                    foodMoney: 35,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '瘦肉炖鸽子',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634960382671.jpg?x-oss-process=style/c320',
                    foodMoney: 56,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '臭豆腐烧四季豆',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634959891241.jpg?x-oss-process=style/c320',
                    foodMoney: 25,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '银鱼蒸水蛋',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634841813631.jpg?x-oss-process=style/c320',
                    foodMoney: 30,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '自制鸡肉卷',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634843101431.jpg?x-oss-process=style/c320',
                    foodMoney: 35,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '大娃菜肉馅荞麦饺子',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634695184941.jpg?x-oss-process=style/c320',
                    foodMoney: 36,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '双色花馒头',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634697074091.jpg?x-oss-process=style/c320',
                    foodMoney: 10,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '杂菇蒸鸡',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634694306681.jpg?x-oss-process=style/c320',
                    foodMoney: 45,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '凉拌金针菇洋葱丝',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634694694331.jpg?x-oss-process=style/c320',
                    foodMoney: 29,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '红糖糍粑',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634694306681.jpg?x-oss-process=style/c320',
                    foodMoney: 26,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '茴香肉包',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/17/2022091716634115732501.jpg?x-oss-process=style/c320',
                    foodMoney: 20,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '牛肉拌粉',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/08/19/2022081916608740423931.jpg?x-oss-process=style/c320',
                    foodMoney: 22,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '瓜丝麦饼',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/08/17/2022081716607016451541.jpg?x-oss-process=style/c320',
                    foodMoney: 22,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '韭菜鸡蛋素水饺',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/05/17/2022051716527524995971.jpg?x-oss-process=style/c320',
                    foodMoney: 20,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '墨鱼老鸭汤煮米粉',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/05/12/2022051216523261774961.jpg?x-oss-process=style/c320',
                    foodMoney: 26,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '日式风味黑鳕鱼西京烧',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/10/09/2021100916337520307728197577.jpg?x-oss-process=style/c320',
                    foodMoney: 88,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '番茄虾尾意面',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/11/21/2021112116374668074221958079.jpg?x-oss-process=style/c320',
                    foodMoney: 42,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '萌熊烤榴莲',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/05/31/2021053116224315761118197577.jpg?x-oss-process=style/c320',
                    foodMoney: 46,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '法式烤羊排',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/05/05/2021050516202177753548197577.jpg?x-oss-process=style/c320',
                    foodMoney: 47,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '西兰花鸡腿肉沙拉',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/05/01/2021050116198406902968197577.jpg?x-oss-process=style/c320',
                    foodMoney: 43,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodName: '芒果鸡蛋三明治',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/04/19/2021041916188105024408197577.jpg?x-oss-process=style/c320',
                    foodMoney: 43,
                    foodNum: 1,
                    selected: false
                }
            ],
            foodMoneySum: 0,
            rules: {
                name: [{ validator: checkName, trigger: ['change', 'blur'] }],
                telephone: [{ validator: checkPhone, trigger: ['change', 'blur'] }],
                eater_num: [{ validator: checkPeopleNum, trigger: ['change', 'blur'] }],
                desk_num: [{ validator: checkDeskNum, trigger: ['change', 'blur'] }]
            }
        };
    },
    methods: {
        handBook() {
            this.addVisible = true;
            this.form = {};
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 删除操作
        handleDelete(index, row) {
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    // 删除语句写在这
                });
        },
        // 点餐操作
        handleOrder(index, row) {
            this.idx = index;
            this.form = row;
            this.orderVisible = true;
        },
        // 重置属性
        resetSelected() {
            for (var item in this.foodTable) {
                this.foodTable[item].selected = false;
            }
        },
        // 更新价格
        updatePrice() {
            this.foodMoneySum = 0;
            for (var item in this.foodTable) {
                if (this.foodTable[item].selected) {
                    this.foodMoneySum += this.foodTable[item].foodNum * this.foodTable[item].foodMoney;
                }
            }
        },
        // 点餐多选操作
        handleOrderSelection(val) {
            this.multipleSelection = val;
            this.orderList = [];
            this.foodMoneySum = 0;
            this.resetSelected();
            for (let i = 0; i < val.length; i++) {
                this.foodMoneySum += val[i].foodMoney * val[i].foodNum;
                val[i].selected = true;
            }
        },
        // 点餐数字操作
        handleOrderNumChange(money, selected) {
            if (selected) {
                this.updatePrice();
            }
        },
        // 编辑弹出框的关闭
        handleClose() {
            this.$message.info('取消编辑');
            // 缺少恢复数据
            // this.getAllBookMsgs();
            this.editVisible = false;
            done();
        },
        // 添加弹出框的关闭
        handleClose2() {
            this.$message.info('取消添加');
            // 缺少恢复数据
            // this.getAllBookMsgs();
            this.addVisible = false;
            done();
        },
        // 点餐弹出框的关闭
        handleClose3() {
            this.$message.info('取消点餐');
            // 缺少恢复数据
            // this.getAllBookMsgs();
            this.orderVisible = false;
            done();
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