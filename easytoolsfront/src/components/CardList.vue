<template>
    <!--在这里写一个v-for循环，遍历存放卡片到首页展示 NCard-->
    <div v-for="item in itemList" :key="item">
        <NCard class="mainCard" :title="item.name" :hoverable="true" @click="jump(item.jump)">
            <template #cover>
<!--               <img :src="getImageUrl(item.srcImg)"/>-->
            </template>
        </NCard>
<!--        <div class="shell" @click="jump(item.jump)">-->
<!--            <div class="card">-->
<!--                <div class="wrapper" > &ndash;&gt;-->
<!--                    <img src="../assets/test.png" alt="" class="cover-image"/>-->
<!--                </div>-->
<!--                <img src="../assets/title.png" alt="" class="title"/>-->
<!--                <img :src="getImageUrl(item.srcImg)" class="character"/>-->
<!--            </div>-->
<!--        </div>-->
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
        const getImageUrl = (srcImg) =>{
            return new URL('../assets/'+srcImg, import.meta.url).href
        }
        return {
            itemList: [
                {
                    name: '萌享解析',
                    desc: '用于方便转载的新闻解析',
                    jump: '/EasyCopy',
                    srcImg: 'nxd.png'
                },
                {
                    name: '抽卡waifu.pics',
                    desc: '',
                    jump: '/GirlPrizeDraw',
                    srcImg: 'sz.jpg'
                },
                {
                    name: '抽卡waifu.im',
                    desc: '',
                    jump: '/WaifuIm',
                    srcImg: 'sit.png'
                },
                {
                    name: '多点优选',
                    desc: '',
                    jump: '/MuchAddSelect',
                    srcImg: 'sit.png'
                },
                {
                    name: 'HearthStone',
                    desc: '',
                    jump: '/HearthStoneIMBA',
                    srcImg: 'sit.png'
                }
            ],
            getImageUrl
        }
    },
    methods: {
        jump(url) {
            this.$router.push(url)
        },
    },
})
</script>
<style>
</style>
