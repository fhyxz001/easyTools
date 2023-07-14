<template>
    <div class="girlprizedraw">
        <div>
            <div class="prizeDraw-config">
                <n-space>
                    <n-input-group-label>抽取标签：{{included_tags}}</n-input-group-label>
                    <br>
                    <n-button style="margin: 3px;" v-for="item in tagList" :key="item.tag" :value="item.tag" @click="included_tags = item.tag">
                        {{item.cn}}
                    </n-button>
                </n-space>
                <br>
                <n-space>
                    <n-input-group-label>是否包含nsfw：</n-input-group-label>
                    <n-switch v-model:value="is_nsfw"></n-switch>
                </n-space>
                <br>
                <n-space>
                    <n-input-group-label>是否包含gif：</n-input-group-label>
                    <n-switch v-model:value="gif"></n-switch>
                </n-space>
                <br>
                <n-space>
                    <n-input-group-label>是否返回多张图(30)：</n-input-group-label>
                    <n-switch v-model:value="many"></n-switch>
                </n-space>
                <br>
                <n-button @click="draw">抽取</n-button>
                &nbsp;
                <n-button @click="clean">清空</n-button>
                <br>
            </div>
        </div>
        <div v-for="img in imgList">
            <div class="girlsImgItem">
            <img :src="img.url" @click="showInfo(img)" style="width: 300px"/>
            </div>
        </div>
    </div>
</template>

<script>
import {NButton,NSwitch,NCheckbox,NTag,NRadio,NRadioButton} from "naive-ui";
import axios from "axios";
import {ref,defineComponent} from "vue";
export default {
    name: "WaifuIm",
    components: {
        NButton,NSwitch,NCheckbox,NTag,NRadio,NRadioButton
    },
    setup() {
        return {
            imgList:[],
            tagList:[{"tag":"maid","type":"versatile","cn":"女仆"},{"tag":"waifu","type":"versatile","cn":"老婆"},{"tag":"marin-kitagawa","type":"versatile","cn":"北川真凛"},{"tag":"mori-calliope","type":"versatile","cn":"茉莉卡洛"},{"tag":"raiden-shogun","type":"versatile","cn":"雷电将军"},{"tag":"oppai","type":"versatile","cn":"大胸"},{"tag":"selfies","type":"versatile","cn":"自拍"},{"tag":"uniform","type":"versatile","cn":"制服"},{"tag":"ass","type":"nsfw","cn":"屁股"},{"tag":"hentai","type":"nsfw","cn":"变态"},{"tag":"milf","type":"nsfw","cn":"熟女"},{"tag":"oral","type":"nsfw","cn":"口交"},{"tag":"paizuri","type":"nsfw","cn":"乳交"},{"tag":"ecchi","type":"nsfw","cn":"H成分"},{"tag":"ero","type":"nsfw","cn":"エロ"}],
            included_tags:ref('waifu'),
            is_nsfw:ref(false),
            gif:ref(false),
            many:ref(false),//开启的话会返回30张图像
        }
    },
    methods: {
        draw(){
            axios.get("https://api.waifu.im/search?"
                +"&included_tags="+this.included_tags
                +"&is_nsfw="+this.is_nsfw
                +"&gif="+this.gif
                +"&many="+this.many).then(res => {
                this.imgList = res.data.images
                this.$forceUpdate()
            })
        },
        showInfo(img){
            console.log(img)
        },
        clean(){
            this.imgList=[]
        }
    },
    mounted() {
    },
    watch(){

    }
}
</script>

<style scoped>

.girlprizedraw{
    display: flex;
    flex-direction: column;
}
.config{
    display: inline-block;
    margin: 5px;
}
.girlsImgItem{

}
</style>
