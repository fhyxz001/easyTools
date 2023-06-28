<template>
    <!--在这里写一个v-for循环，遍历存放卡片到首页展示 NCard-->
    <div v-for="item in itemList" :key="item">
        <NCard class="mainCard" :title="item.name" :hoverable="true" @click="jump(item.jump)">
            <template #cover>
                <img src="../assets/logo.png" style="">
            </template>
        </NCard>
    </div>
</template>

<script>
import { defineComponent } from 'vue'
import { NButton,NCard,NSpace,NLayout,NLayoutHeader,NLayoutContent,NLayoutFooter,NLayoutSider } from 'naive-ui'
import router from "@/router";

export default defineComponent({
    components: {
        NButton,NCard,NSpace,NLayout,NLayoutHeader,NLayoutContent,NLayoutFooter,NLayoutSider
    },
    setup() {
        return {
            itemList: [
                {
                    name: '萌享解析',
                    desc: '用于方便转载的新闻解析',
                    jump: '/EasyCopy'
                },
                {
                    name: '待续',
                    desc: '',
                    jump: '/Hearthstone'
                }
            ]
        }
    },
    methods: {
        jump(url) {
            this.$router.push(url)
        }
    }
})
</script>
<style>
</style>