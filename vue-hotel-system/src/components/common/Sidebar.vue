<template>
    <!-- element-plus-collapse https://element-plus.gitee.io/en-US/component/menu.html#collapse -->
    <div class='sidebar'>
        <el-menu
            class='sidebar-el-menu'
            :default-active='onRoutes'
            :collapse='collapse'
            background-color='#324157'
            text-color='#bfcbd9'
            active-text-color='#20a0ff'
            unique-opened
            router
        >
            <template v-for='item in items'>
                <template v-if='item.subs'>
                    <el-submenu :index='item.index' :key='item.index'>
                        <template slot='title'>
                            <i :class='item.icon'></i>
                            <span slot='title'>{{ item.title }}</span>
                        </template>
                        <template v-for='subItem in item.subs'>
                            <el-submenu v-if='subItem.subs' :index='subItem.index' :key='subItem.index'>
                                <template slot='title'>{{ subItem.title }}</template>
                                <el-menu-item v-for='(threeItem, i) in subItem.subs' :key='i' :index='threeItem.index'
                                >{{ threeItem.title }}
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item v-else :index='subItem.index' :key='subItem.id'>{{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index='item.index' :key='item.index'>
                        <i :class='item.icon'></i>
                        <span slot='title'>{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';

export default {
    data() {
        return {
            // 默认是收缩的
            // 更多el-icon 图片去这个网站去搜索 https://cloud.tencent.com/developer/section/1489867
            collapse: false,
            items: [
                {
                    // icon: 'el-icon-lx-home',
                    icon: 'el-icon-user',
                    index: 'dashboard',
                    title: '个人信息'
                },
                {
                    // icon: 'el-icon-lx-home',
                    icon: 'el-icon-apple',
                    index: 'dishes',
                    title: '特色菜品'
                },
                {
                    icon: 'el-icon-lx-addressbook',
                    index: 'reserve',
                    title: '预定列表'
                },
                {
                    icon: 'el-icon-school',
                    index: 'room',
                    title: '房间列表'
                },
                {
                    icon: 'el-icon-lx-friend',
                    index: 'table',
                    title: '客户列表'
                },
                {
                    icon: 'el-icon-fork-spoon',
                    index: 'food',
                    title: '餐桌列表'
                },
                {
                    icon: 'el-icon-lx-recharge',
                    index: 'cost',
                    title: '消费管理'
                },
                {
                    icon: 'el-icon-lx-profile',
                    index: 'permission',
                    title: '前台管理'
                },
                {
                    icon: 'el-icon-s-check',
                    index: 'economy',
                    title: '经济消费报表'
                },
                {
                    icon: 'el-icon-s-claim',
                    index: 'finance',
                    title: '财务报表'
                }
            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
    width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}

.sidebar > ul {
    height: 100%;
}
</style>
