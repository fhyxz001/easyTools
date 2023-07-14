<template>
    <div class="girlprizedraw">
  <div>
      <div class="prizeDraw-config">
          <n-space>
              开启R18:&nbsp;<n-switch v-model:value="R18Start" @change="R18StartChange"/>
          </n-space>
          <br>
          <n-space>
              接口请求配置：{{apiConfig.type}},{{apiConfig.category}}
          </n-space>
          <div v-if="!R18Start">
              配置:
              <div class="config" v-for="item in SFWconfig">
                  <NButton strong secondary @click="changeCategory(item.name)" >{{item.cn}}</NButton>
              </div>
          </div>
          <div v-if="R18Start">
              配置:
              <div class="config" v-for="item in NSFWconfig">
                  <NButton strong secondary @click="changeCategory(item.name)">{{item.cn}}</NButton>
              </div>
          </div>
      </div>
      <NButton strong secondary type="info" @click="prizeDraw">抽取</NButton>
      &nbsp;
      <NButton strong secondary @click="prizeDrawNums(10)">十连</NButton>
      &nbsp;
      <NButton dashed  @click="imgList=[];this.$forceUpdate()">清空</NButton>
      &nbsp;
      <NButton strong secondary @click="saveImgList" disabled>保存全部</NButton>
  </div>
    <div class="girsImg" v-for="img in imgList">
          <img :src="img.url" style="width: 300px"/>
    </div>
    </div>
</template>

<script>
import {NButton,NSwitch} from "naive-ui";
import axios from "axios";
import {ref,defineComponent} from "vue";
import {seededRandom} from "three/src/math/MathUtils";
export default {
    name: "GirlPrizeDraw",
    components: {
        NButton,NSwitch
    },
    setup() {
        return {
            imgList:[],
            SFWconfig:[{name:'waifu', cn:'老婆'}, {name:'neko', cn:'猫娘'}, {name:'shinobu', cn:'忍者'}, {name:'megumin', cn:'梅露露'}, {name:'bully', cn:'欺负'}, {name:'cuddle', cn:'拥抱'}, {name:'cry', cn:'哭泣'}, {name:'hug', cn:'拥抱'}, {name:'awoo', cn:'嗷呜'}, {name:'kiss', cn:'亲吻'}, {name:'lick', cn:'舔'}, {name:'pat', cn:'拍'}, {name:'smug', cn:'得意'}, {name:'bonk', cn:'敲击'}, {name:'yeet', cn:'扔出'}, {name:'blush', cn:'脸红'}, {name:'smile', cn:'微笑'}, {name:'wave', cn:'挥手'}, {name:'highfive', cn:'击掌'}, {name:'handhold', cn:'牵手'}, {name:'nom', cn:'咬'}, {name:'bite', cn:'咬'}, {name:'glomp', cn:'猛抱'}, {name:'slap', cn:'打耳光'}, {name:'kill', cn:'杀死'}, {name:'kick', cn:'踢'}, {name:'happy', cn:'开心'}, {name:'wink', cn:'眨眼'}, {name:'poke', cn:'戳'}, {name:'dance', cn:'跳舞'}, {name:'cringe', cn:'尴尬'}],
            NSFWconfig:[{name:'waifu', cn:'老婆'}, {name:'neko', cn:'猫娘'}, {name:'trap', cn:'陷阱'}, {name:'blowjob', cn:'口交'}],
            R18Start: ref(false),
            apiConfig:{
                type:'sfw',
                category:'waifu'
            }
        }
    },
    methods: {
        saveImgList(){
            if(this.imgList.length>0){
                this.imgList.forEach(imgUrl=>{
                    //下载imgUrl图片到本地
                    this.downloadImage(imgUrl)
                })
            }
        },
        downloadImage(imgUrl){
            const link = document.createElement('a');
            link.href = imgUrl
            link.download = seededRandom()+'.jpg'
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link)
        },
        ApiConfigReset(){
            this.apiConfig.category='waifu'
        },
        prizeDraw(){
            axios.get("https://api.waifu.pics/"+this.apiConfig.type+"/"+this.apiConfig.category).then(res => {
                //将返回的图片url添加到imgList中
                this.imgList.push(res.data)
                this.$forceUpdate()
            })
        },
        prizeDrawNums(nums){
            //调用10次prizeDraw
            for(let i = 0;i<nums;i++){
                this.prizeDraw()
            }
        },
        changeCategory(item){
            this.apiConfig.category=item;
            this.$forceUpdate()
        },
        R18StartChange(){
            this.ApiConfigReset()
            if(this.R18Start){
                //开启R18
                this.apiConfig.type='nsfw'
            }else {
                //关闭R18
                this.apiConfig.type='sfw'
            }
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
.girsImg{
    margin: 5px;
}
</style>
