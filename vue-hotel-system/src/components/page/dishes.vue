<template>
    <div>
        <el-tabs v-model='activeName' class='demo-tabs' @tab-click='handleClick'>
            <el-tab-pane label='最新推荐' name='first'>
                <ul v-if="activeName === 'first' ">
                    <li v-for='(item,key) in imgList1' :key='key'>
                        <img :src=item.src alt=''>
                        <p>{{ item.description }}</p>
                    </li>
                </ul>
            </el-tab-pane>
            <el-tab-pane label='最新发布' name='second'>
                <ul v-if="activeName === 'second' ">
                    <li v-for='(item,key) in imgList2' :key='key'>
                        <img :src=item.src alt=''>
                        <p>{{ item.description }}</p>
                    </li>
                </ul>
            </el-tab-pane>
            <el-tab-pane label='主食' name='third'>
                <ul v-if="activeName === 'third' ">
                    <li v-for='(item,key) in imgList3' :key='key'>
                        <img :src=item.src alt=''>
                        <p>{{ item.description }}</p>
                    </li>
                </ul>
            </el-tab-pane>
            <el-tab-pane label='西餐' name='fourth'>
                <ul v-if="activeName === 'fourth' ">
                    <li v-for='(item,key) in imgList4' :key='key'>
                        <img :src=item.src alt=''>
                        <p>{{ item.description }}</p>
                    </li>
                </ul>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
export default {
    name: 'dishes',
    data() {
        return {
            // first: '最新推荐',
            // second: '最新发布',
            // third: '主食',
            // fourth: '最新推荐',
            activeName: 'first',
            // more information please see https://home.meishichina.com/recipe.html
            imgList1: [
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816635021316021.jpg?x-oss-process=style/c320',
                    description: '手撕包菜'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816635020885151.jpg?x-oss-process=style/c320',
                    description: '小炒杏鲍菇'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634960382671.jpg?x-oss-process=style/c320',
                    description: '五指毛桃瘦肉炖鸽子'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634959891241.jpg?x-oss-process=style/c320',
                    description: '臭豆腐烧四季豆'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634960382671.jpg?x-oss-process=style/c320',
                    description: '五指毛桃瘦肉炖鸽子'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634841813631.jpg?x-oss-process=style/c320',
                    description: '银鱼蒸水蛋'
                }
            ],
            imgList2: [
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634843101431.jpg?x-oss-process=style/c320',
                    description: '自制鸡肉卷'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634695184941.jpg?x-oss-process=style/c320',
                    description: '大娃菜肉馅荞麦饺子'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634697074091.jpg?x-oss-process=style/c320',
                    description: '双色花馒头'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634694306681.jpg?x-oss-process=style/c320',
                    description: '杂菇蒸鸡'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634694694331.jpg?x-oss-process=style/c320',
                    description: '凉拌金针菇洋葱丝'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/18/2022091816634694306681.jpg?x-oss-process=style/c320',
                    description: '红糖糍粑'
                }
            ],
            imgList3: [
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/09/17/2022091716634115732501.jpg?x-oss-process=style/c320',
                    description: '茴香肉包'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/08/19/2022081916608740423931.jpg?x-oss-process=style/c320',
                    description: '牛肉拌粉'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/08/17/2022081716607016451541.jpg?x-oss-process=style/c320',
                    description: '瓜丝麦饼'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/05/19/2022051916529237496951.jpg?x-oss-process=style/c320',
                    description: '蒜苔鸡蛋炒饭'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/05/17/2022051716527524995971.jpg?x-oss-process=style/c320',
                    description: '韭菜鸡蛋素水饺'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2022/05/12/2022051216523261774961.jpg?x-oss-process=style/c320',
                    description: '墨鱼老鸭汤煮米粉'
                }
            ],
            imgList4: [
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2021/10/09/2021100916337520307728197577.jpg?x-oss-process=style/c320',
                    description: '日式风味黑鳕鱼西京烧'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2021/11/21/2021112116374668074221958079.jpg?x-oss-process=style/c320',
                    description: '番茄虾尾意面'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2021/05/31/2021053116224315761118197577.jpg?x-oss-process=style/c320',
                    description: '萌熊烤榴莲'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2021/05/05/2021050516202177753548197577.jpg?x-oss-process=style/c320',
                    description: '法式烤羊排'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2021/05/01/2021050116198406902968197577.jpg?x-oss-process=style/c320',
                    description: '西兰花鸡腿肉沙拉'
                },
                {
                    src: 'https://i3.meishichina.com/attachment/recipe/2021/04/19/2021041916188105024408197577.jpg?x-oss-process=style/c320',
                    description: '芒果鸡蛋三明治'
                }
            ]
        };
    }
};
</script>

<style scoped>
.demo-tabs {
    margin: 10px;
}

ul {
    list-style: none;
}

ul li {
    float: left;
    margin: 10px;
}

ul li p {
    text-align: center;
}
</style>
