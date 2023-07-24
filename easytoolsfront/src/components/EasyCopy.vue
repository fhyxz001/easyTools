<template>
        <div class="easyCopy">
            <div class="easyCopyitem">
                <p class="tip">
                    <a href="https://www.3dmgame.com/news/"><img class="supportWebImg" src="../assets/3DM.png"></a>
                    <a href="https://www.gcores.com/articles"><img class="supportWebImg" src="../assets/GCORES.png"></a>
                    <a href="https://news.dmzj.com/donghuaqingbao"><img class="supportWebImg" src="../assets/DMZJ.png"></a>
                    <a href="https://www.gao7.com/"><img class="supportWebImg" src="../assets/GAO7.png"></a>
                    <a href="http://www.hotacg.com/"><img class="supportWebImg" src="../assets/HotAcg.png"></a>
                    <a href="https://www.vgtime.com/topic/index.jhtml"><img class="supportWebImg" src="../assets/GameTime.png"></a>
                    <a href="https://www.ali213.net/news/new/"><img class="supportWebImg" src="../assets/YOUXIA.png"></a>
                </p>
                <div class="inputandout">
                    <div class="easyCopy-input">
                        <n-input type="text"  v-model:value="url" placeholder="请输入url"/>
                        <n-button style="float: right" type="primary" @click="parse">解析</n-button>
                    </div>
                    <!--                <div class="easyCopy-output"  v-if="title!=''" >-->
                    <div class="easyCopy-output"  >
                        <n-input type="textarea" :rows="2" v-model:value="title" placeholder="解析标题"/>
                        <n-button  @click="copy(title)">复制</n-button>
                    </div>
                    <div class="easyCopy-output">
                        <n-input type="textarea" :rows="2" v-model:value="content" placeholder="解析内容"/>
                        <n-button  @click="copy(content)">复制</n-button>
                    </div>
                </div>
            </div>
        </div>
</template>
<script>
import axios from 'axios'
import {NButton,NInput,useNotification } from "naive-ui";
export default {
    name: "easyCopy",
    components: {
        NButton,NInput,useNotification
    },
    data() {
        return {
            url: "",
            title: "",
            content: ""
        };
    },
    methods: {
        parse() {
            axios.get("/api/bbs/getNewsByUrl?url=" + this.url).then(res => {
                //将返回的title和content赋值给data中的title和content
                this.title = res.data.title;
                this.content = res.data.content;
            })
        },
        copy(text) {
            //带着格式复制
            let input = document.createElement("textarea");
            input.value = text;
            document.body.appendChild(input);
            input.select();
            document.execCommand("Copy");
            document.body.removeChild(input);
        },
        pasteUrl(){
            //获取剪切板内容
            navigator.clipboard.readText().then(clipText => {
                //将剪切板内容赋值给url
                this.url = clipText;
            });
        },
        resetUrl(){
            //重置url
            this.url = "";
        }
    }
}
</script>

<style scoped>
.supportWebImg{
    margin-right: 10px;
}
.supportWebImg:hover{
    /*  深色投影*/
    box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.5);
}
.easyCopy-input{
    /*  左边input右边button，长度大概400px，下间距10px*/
    display: flex;
    /*width:30%;*/
    margin-bottom: 10px;
}
.easyCopy-input button{
    margin-left: 5px;
}
.easyCopy-output{
    display: flex;
    /*width:30%;*/
    margin-bottom: 10px;
    white-space: pre-line;

}
.easyCopy-output button{
    margin-left: 5px;
}
.tip{
    width: 40%;
    margin-top: 40px;
}
.easyCopyitem{
    display: flex;
    margin-left: 5%;
}
.inputandout{
    margin-top: 40px;
    width: 400px;
}
.easyCopy{
/*    设置背景色为极浅灰色*/
    background: rgba(255, 255, 255, 0.96);
}

</style>
