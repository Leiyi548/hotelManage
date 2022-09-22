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
                <el-button round plain type='primary' icon='el-icon-delete' class='handle-del mr10'
                           @click='delAllSelection'>批量删除
                </el-button>
                &nbsp;&nbsp;
                <el-input
                    round
                    v-model='foodMsgs.name'
                    placeholder='请输入客户名字搜索,直接回车即可'
                    class='handle-input mr10'
                    clearable
                    prefix-icon='el-icon-search'
                    @clear='handleSearch'
                    @keydown.enter.native='handleSearch'
                >

                </el-input>
                <el-button plain round style='float: right' type='primary' icon='el-icon-plus' @click='addVisible=true'>
                    添加预定
                </el-button>
            </div>
            <!-- 主列表-->
            <el-table :data='tableData' style='width: 100%' height='500' @selection-change='handleSelectionChange'>
                <el-table-column type='selection' width='55' align='center'></el-table-column>
                <el-table-column prop='reserverName' label='用户名' align='center' width='200'></el-table-column>
                <el-table-column prop='reserverTel' label='电话号码' align='center' width='200'></el-table-column>
                <el-table-column prop='eaterNum' label='人数' align='center' width='200'></el-table-column>
                <el-table-column prop='deskNum' label='桌号' align='center' width='200'></el-table-column>
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
                            type='info'
                            icon='el-icon-message'
                            plain
                            @click='handleDetails(scope.$index, scope.row, scope.row.rank)'
                        >详情
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
            <!--主列表结束-->

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
                    <el-button type='primary' @click='addReserver'>确 定</el-button>
                </span>
            </el-dialog>
            <!--添加弹出框结束-->

            <!--编辑弹出框-->
            <el-dialog title='编辑' :visible.sync='editVisible' width='30%' :before-close='handleClose'>
                <el-form ref='form' :model='form' label-width='70px'>
                    <el-form-item label='用户名' prop='name'>
                        <el-input v-model='form.reserverName'></el-input>
                    </el-form-item>
                    <el-form-item label='电话号码' prop='telephone'>
                        <el-input v-model='form.reserverTel'></el-input>
                    </el-form-item>
                    <el-form-item label='人数' prop='eater_num'>
                        <el-input v-model='form.eaterNum'></el-input>
                    </el-form-item>
                    <el-form-item label='桌号' prop='desk_num'>
                        <el-input v-model='form.deskNum'></el-input>
                    </el-form-item>
                </el-form>
                <span slot='footer' class='dialog-footer'>
                    <el-button @click='addVisible = false'>取 消</el-button>
                    <el-button type='primary' @click='saveEdit'>确 定</el-button>
                </span>
            </el-dialog>
            <!--编辑弹出框结束-->

            <!--点餐弹出框-->
            <el-dialog title='点餐' :visible.sync='orderVisible' width='60%' :before-close='handleClose3'>
                <el-table :data='foodTable' ref='foodTable' style='width: 100%' height='500'
                          @selection-change='handleOrderSelection'>
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
                    <el-button type='primary' @click='submitInformation'>确 定</el-button>
                </span>
            </el-dialog>
            <!--点餐弹出框结束-->

            <!--详情弹出框-->
            <el-dialog title='详细信息' :visible.sync='detailsVisible' width='40%' :before-close='handleClose4'>
                <el-table :data='detailsData' style='width: 100%' height='500'>
                    <el-table-column prop='dishName' label='菜品名称' align='center' width='200' />
                    <el-table-column prop='num' label='菜品数量' align='center' width='200' />
                    <el-table-column prop='price' label='菜品价格' align='center' width='200' />
                </el-table>
                <p align='right' style='margin:10px;color:red; '>￥{{ this.currentMoney }}</p>
            </el-dialog>
            <!--详情弹出框结束-->
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
            detailsVisible: false,
            // 多选内容
            multipleSelection: [],
            form: [],
            foodMsgs: [],
            // 表格展示数据
            tableData: [],
            // 详情表格数据
            detailsData: [],
            // 菜单数据
            foodTable: [
                {
                    foodId: 1,
                    foodName: '手撕包菜',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816635021316021.jpg?x-oss-process=style/c320',
                    foodMoney: 25,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 2,
                    foodName: '小炒杏鲍菇',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816635020885151.jpg?x-oss-process=style/c320',
                    foodMoney: 35,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 3,
                    foodName: '瘦肉炖鸽子',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634960382671.jpg?x-oss-process=style/c320',
                    foodMoney: 56,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 4,
                    foodName: '臭豆腐烧四季豆',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634959891241.jpg?x-oss-process=style/c320',
                    foodMoney: 25,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 5,
                    foodName: '银鱼蒸水蛋',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634841813631.jpg?x-oss-process=style/c320',
                    foodMoney: 30,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 6,
                    foodName: '自制鸡肉卷',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634843101431.jpg?x-oss-process=style/c320',
                    foodMoney: 35,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 7,
                    foodName: '大娃菜肉馅荞麦饺子',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634695184941.jpg?x-oss-process=style/c320',
                    foodMoney: 36,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 8,
                    foodName: '双色花馒头',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634697074091.jpg?x-oss-process=style/c320',
                    foodMoney: 10,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 9,
                    foodName: '杂菇蒸鸡',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634694306681.jpg?x-oss-process=style/c320',
                    foodMoney: 45,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 10,
                    foodName: '凉拌金针菇洋葱丝',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634694694331.jpg?x-oss-process=style/c320',
                    foodMoney: 29,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 11,
                    foodName: '红糖糍粑',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634694306681.jpg?x-oss-process=style/c320',
                    foodMoney: 26,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 12,
                    foodName: '茴香肉包',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/09/17/2022091716634115732501.jpg?x-oss-process=style/c320',
                    foodMoney: 20,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 13,
                    foodName: '牛肉拌粉',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/08/19/2022081916608740423931.jpg?x-oss-process=style/c320',
                    foodMoney: 22,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 14,
                    foodName: '瓜丝麦饼',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/08/17/2022081716607016451541.jpg?x-oss-process=style/c320',
                    foodMoney: 22,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 15,
                    foodName: '韭菜鸡蛋素水饺',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/05/17/2022051716527524995971.jpg?x-oss-process=style/c320',
                    foodMoney: 20,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 16,
                    foodName: '墨鱼老鸭汤煮米粉',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2022/05/12/2022051216523261774961.jpg?x-oss-process=style/c320',
                    foodMoney: 26,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 17,
                    foodName: '日式风味黑鳕鱼西京烧',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/10/09/2021100916337520307728197577.jpg?x-oss-process=style/c320',
                    foodMoney: 88,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 18,
                    foodName: '番茄虾尾意面',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/11/21/2021112116374668074221958079.jpg?x-oss-process=style/c320',
                    foodMoney: 42,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 19,
                    foodName: '萌熊烤榴莲',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/05/31/2021053116224315761118197577.jpg?x-oss-process=style/c320',
                    foodMoney: 46,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 20,
                    foodName: '法式烤羊排',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/05/05/2021050516202177753548197577.jpg?x-oss-process=style/c320',
                    foodMoney: 47,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 21,
                    foodName: '西兰花鸡腿肉沙拉',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/05/01/2021050116198406902968197577.jpg?x-oss-process=style/c320',
                    foodMoney: 43,
                    foodNum: 1,
                    selected: false
                },
                {
                    foodId: 22,
                    foodName: '芒果鸡蛋三明治',
                    imgUrl: 'https://i3.meishichina.com/attachment/recipe/2021/04/19/2021041916188105024408197577.jpg?x-oss-process=style/c320',
                    foodMoney: 43,
                    foodNum: 1,
                    selected: false
                }
            ],
            // 点餐总金额
            foodMoneySum: 0,
            // 当前点餐的人姓名
            currentGuestName: '',
            // 当前点餐的人号码
            currentGuestPhone: '',
            // 当前用餐认人数
            currentGuestNum: '',
            // 当前桌号
            currentDeskNum: '',
            // 当前总金额
            currentMoney: '',
            // 删除名额列表
            delList: [],
            rules: {
                name: [{ validator: checkName, trigger: ['change', 'blur'] }],
                telephone: [{ validator: checkPhone, trigger: ['change', 'blur'] }],
                eater_num: [{ validator: checkPeopleNum, trigger: ['change', 'blur'] }],
                desk_num: [{ validator: checkDeskNum, trigger: ['change', 'blur'] }]
            }
        };
    },
    created() {
        this.getAllReserverMsg();
    },
    methods: {

        // 获得所有预定信息
        getAllReserverMsg() {
            this.$http.get('http://localhost:8082/getAllReservers').then((res) => {
                // console.log('===res===');
                // console.log(res);
                // console.log('===res end===');
                if (res.data.data == null) {
                    this.tableData = [];
                } else {
                    console.log(res);
                    this.tableData = res.data.data.reservers;
                }
            });
        },
        // 搜索信息: 根据姓名搜索
        handleSearch() {
            console.log(this.foodMsgs.name);
            // DONE: 搜索信息: 根据姓名搜索
            this.$http.get('http://localhost:8082/fuzzyReserver?reserverName=' + this.foodMsgs.name).then((res => {
                if (res.data.code === 200) {
                    console.log(res.data.data);
                    // this.tableData = res.data.data
                } else {
                    this.$message.error('抱歉没有该数据');
                }
            }));
        },
        // 保存编辑修改
        saveEdit() {
            this.$http.put(
                'http://localhost:8082/updateReserver?reserverName=' +
                this.form.reserverName +
                '&reserverTel=' + this.form.reserverTel +
                '&eaterNum=' + this.form.eaterNum +
                '&deskNum=' + this.form.deskNum
            ).then((res) => {
                if (res.data.code === 200) {
                    //1.提示成功
                    this.$message.success(`修改成功`);
                    //2.关闭对话框
                    this.editVisible = false;
                    //3.更新视图
                    this.getAllBookMsgs();
                    //4.清空输入文本框
                    this.form = {};
                } else {
                    this.$message.warning('修改失败');
                }
            });
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
            this.delList = [];
            for (let i = 0; i < val.length; i++) {
                this.delList.push(val[i].reserverName);
            }
        },
        // 获得json长度
        getJsonLength() {
            var length = 0;
            for (var item in this.tableData) {
                length++;
            }
            return length;
        },
        // 删除多选信息
        delAllSelection() {
            const length = this.multipleSelection.length;
            if (length == 0) {
                this.$message.error('未选择内容');
                return;
            }
            var i = 0;
            for (i; i < this.delList.length; i++) {
                // DONE: 删除多选内容
                this.$http.get(
                    'http://localhost:8082/deleteReserver?reserverName=' +
                    this.delList[i]
                ).then((res) => {
                    if (res.data.code === 200) {
                        //1.提示成功
                        this.$message.success(`删除成功`);
                        //更新数据
                        this.getAllReserverMsg();
                    } else {
                        this.$message.danger(`删除失败`);
                    }
                });
            }
            // DONE: 更新删除后的数据
            this.multipleSelection = [];
            if (this.getJsonLength() == this.delList.length) {
                this.tableData = [];
            } else {
                this.getAllReserverMsg();
            }
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
                // DONE: 单向删除
                .then(() => {
                    // 删除语句写在这
                    this.$http.get(
                        'http://localhost:8082/deleteReserver?reserverName=' +
                        row.reserverName
                    ).then((res) => {
                        if (res.data.code === 200) {
                            //1.提示成功
                            this.$message.success(`删除成功`);
                            //更新数据
                            this.getAllReserverMsg();
                        } else {
                            this.$message.danger(`删除失败`);
                        }
                    });
                });
        },
        // 详情操作
        handleDetails(index, row) {
            console.log('==row==');
            console.log(row);
            console.log('==row end==');
            this.currentMoney = row.money;
            if (row.state === 0) {
                this.$message.warning('请先点餐');
            } else {
                this.detailsVisible = true;
                this.$http.get('http://localhost:8082/seekReserverDetails?reserverName=' +
                    row.reserverName +
                    '&reserverTel=' + row.reserverTel
                ).then((res) => {
                    if (res.data.code === 200) {
                        console.log('===res===');
                        console.log(res);
                        console.log('===res end===');
                        this.detailsData = res.data.data.list;
                        console.log('===detailsData');
                        console.log(this.detailsData);
                        console.log('===');
                    }
                });
            }
            // 更新总价格
            this.currentMoney = 0;
            for (var item in this.detailsData) {
                this.currentMoney += this.detailsData[item].price;
                // console.log(this.detailsData[item]);
                console.log('currentGuestName: ' + this.currentGuestName);
            }
            // 更新数据库内容
            this.$http.put(
                'http://localhost:8082/updateReserver?reserverName=' +
                row.reserverName +
                '&reserverTel=' + row.reserverTel +
                '&eaterNum=' + row.eaterNum +
                '&deskNum=' + row.deskNum +
                '&money=' + this.currentMoney +
                '&state=1'
            ).then((res) => {
                if (res.data.code === 200) {
                    //1.提示成功
                    // this.$message.success(`点餐成功`);
                    //3.更新视图
                    this.getAllReserverMsg();
                } else {
                    // this.$message.warning('点餐失败');
                }
            });

        },
        // 点餐操作
        handleOrder(index, row) {
            this.idx = index;
            this.form = row;
            console.log('输出点餐操作信息');
            console.log(this.idx);
            console.log(this.form);
            console.log('输出点餐操作信息结束');
            // 获得当前点击用户的姓名
            this.currentGuestName = this.form.reserverName;
            this.currentGuestPhone = this.form.reserverTel;
            this.currentDeskNum = this.form.deskNum;
            this.currentEaterNum = this.form.eaterNum;
            this.currentMoney = this.form.money;
            // 显示点餐
            this.orderVisible = true;
            // alert(this.currentGuestName);
        },
        // 重置属性
        resetSelected() {
            for (var item in this.foodTable) {
                this.foodTable[item].selected = false;
            }
        },
        resetNumber() {
            for (var item in this.foodTable) {
                this.foodTable[item].foodNum = 1;
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
            // 被点击的数据
            this.multipleSelection = val;
            //
            // 总金额
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
                // 更新总价格
                this.updatePrice();
            }
        },
        // 编辑弹出框的关闭
        handleClose() {
            this.$message.info('取消编辑');
            // 缺少恢复数据
            // this.getAllBookMsgs();
            this.editVisible = false;
        },
        // 添加弹出框的关闭
        handleClose2() {
            this.$message.info('取消添加');
            // 缺少恢复数据
            // this.getAllBookMsgs();
            this.addVisible = false;
        },
        // 点餐弹出框的关闭
        handleClose3() {
            this.$message.info('取消点餐');
            // 缺少恢复数据
            // this.getAllBookMsgs();
            // 重置数据
            this.resetNumber();
            // 清除所有多选
            this.$refs.foodTable.clearSelection();
            this.multipleSelection = [];
            this.orderVisible = false;
        },
        // 将点餐信息发送
        submitInformation() {
            // 发送的名字
            // this.currentGuestName
            // 发送的电话
            // this.currentGuestPhone
            // 点餐的数量
            // foodTable.foodNum
            // 当前被点餐的名称
            // 当前消费的金额
            for (var item in this.foodTable) {
                if (this.foodTable[item].selected == true) {
                    console.log('===');
                    console.log('客户名称:' + this.currentGuestName);
                    console.log('客户号码：' + this.currentGuestPhone);
                    console.log('菜品名称：' + this.foodTable[item].foodName);
                    console.log('菜品数量：' + this.foodTable[item].foodNum);
                    console.log('点餐金额：' + this.foodTable[item].foodMoney);
                    console.log('===');

                    // DONE: 传输数据给后端数据库
                    this.$http.post('http://localhost:8082/orderMenu?reserverName=' +
                        this.currentGuestName +
                        '&reserverTel=' + this.currentGuestPhone +
                        '&dishName=' + this.foodTable[item].foodName +
                        '&num=' + this.foodTable[item].foodNum +
                        '&price=' + this.foodTable[item].foodMoney
                    ).then((res) => {
                        if (res.data.code === 200) {
                        } else {
                            this.$message.warning('点餐失败');
                            return;
                        }
                    });
                }
            }
            // 2.更新数据库
            this.$http.put(
                'http://localhost:8082/updateReserver?reserverName=' +
                this.currentGuestName +
                '&reserverTel=' + this.currentGuestPhone +
                '&eaterNum=' + this.currentEaterNum +
                '&deskNum=' + this.currentDeskNum +
                '&money=' + this.foodMoneySum +
                '&state=1'
            ).then((res) => {
                if (res.data.code === 200) {
                    //1.提示成功
                    // this.$message.success(`点餐成功`);
                    //3.更新视图
                    this.getAllReserverMsg();
                    //4.清空输入文本框
                    this.form = {};
                } else {
                    this.$message.warning('点餐失败');
                }
            });
            // 1. 提示成功
            this.$message.success(`点餐成功`);
            // 2. 关闭点餐框
            this.orderVisible = false;
        },
        // 详情信息弹出框的关闭
        handleClose4() {
            this.$message.info('取消浏览');
            // 缺少恢复数据
            // this.getAllBookMsgs();
            this.detailsVisible = false;
        },
        // 添加预定信息
        addReserver() {
            // alert('hello');
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    this.$http.post(
                        'http://localhost:8082/addReserver?reserverName=' +
                        this.form.name +
                        '&reserverTel=' +
                        this.form.telephone +
                        '&eaterNum=' +
                        this.form.eater_num +
                        '&deskNum=' +
                        this.form.desk_num
                    ).then((res) => {
                        if (res.data.code === 200) {
                            // 1.提示成功
                            this.$message.success(`添加成功`);
                            // 2.关闭对话框
                            this.addVisible = false;
                            // 3.更新视图
                            this.getAllReserverMsg();
                            //4.清空输入文本框
                            this.form = {};
                        } else {
                            this.$message.warning('添加失败');
                        }
                    });
                } else {
                    this.$message.warning('请输入正确的信息');
                }
            });

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