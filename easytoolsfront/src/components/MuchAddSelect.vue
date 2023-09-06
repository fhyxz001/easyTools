<template>
    <div>
        <div class="centerContent" v-if="centerPopState">
                <n-form
                        ref="form"
                        :model="form"
                        label-width="100px"
                        label-placement="left"
                >
<!--                    最优路线、合计路径长度，调用接口次数-->
                    <n-form-item
                            label="最优路线"
                    >
                        <n-input
                                type="textarea"
                            v-model:value="form.bestPath" readonly placeholder=""/>
                    </n-form-item>
                    <n-form-item
                            label="合计路径长度"
                    >
                        <n-input v-model:value="form.totalDistance" readonly placeholder=""/>
                    </n-form-item>
                    <n-form-item
                            label="高德调用接口次数"
                    >
                        <n-input v-model:value="form.apiCount" readonly placeholder=""/>
                    </n-form-item>
                    <n-button type="info" @click="centerPopState=false">关闭</n-button>
                </n-form>
        </div>
        <div class="header-left">
<!--            <n-select class="headerLeftSelect"-->
<!--                      placeholder="城市"-->
<!--                      default-value=""-->
<!--                      :options="cityOptions"-->
<!--                      v-model:value="city"-->
<!--            />-->
            <n-input class="headerLeftInput" @updateValue="addressNameChangeHandle" v-model:value="addressName"
                     placeholder="请输入地址"/>
            <n-button type="info" class="header-left-btn" @click="search">搜索</n-button>
            <n-button type="info" class="header-left-btn" @click="analyze">规划</n-button>
            <n-button type="info" class="header-left-btn" @click="reset">重置</n-button>

        </div>
        <div id="map"/>
        <div class="leftPanel" v-if="addressName!=''">
            <!--          list列表，分别是序号、地址、操作-->
            <div v-for="(item,index) in addressList" :key="index" class="leftPanelItem">
                <div>{{ item.district }}</div>
                <div style="margin: 4px;">{{ item.name }}</div>
                <div style="margin: 4px;">
                    <n-button size="tiny" @click="quickLocation(item)">定位</n-button>
                    <n-button size="tiny" @click="addAddress(item)">添加</n-button>
                </div>
            </div>
        </div>
        <div class="bottomCard">
            <div v-for="item in addList">
                <n-card
                        :title="item.name"
                        closable
                        @close="cardRemove(item)"
                >
                    <div class="cardFrontBtn">
                        <div>{{ item.district }}</div>
                        <div>
                            <n-button
                                    dashed
                                    round
                                    style="margin-left: 4px"
                                    :type="item.isStart?'info':'default'"
                                    @click="setStart(item)">始发
                            </n-button>
                        </div>
                    </div>
                </n-card>
            </div>
        </div>
    </div>
</template>
<script>
import {NButton, NInput, NSelect, NDropdown, NCard,NForm,NFormItem} from 'naive-ui'
import AMapLoader from '@amap/amap-jsapi-loader'
import axios from "axios";

export default {
    name: "MuchAddSelect",
    components: {NButton, NInput, NSelect, NDropdown, NCard,NForm,NFormItem},
    mounted() {
        this.loadmap()
    },
    data() {
        return {
            centerPopState:false,
            form:{
                bestPath:'',
                totalDistance:'',
                apiCount:''
            },
            map: null,
            addressName: '',
            city: '',
            addressOptions: [],
            addressList: [],
            addList: [],
            loading: false,
            cityOptions: [],
        }
    },
    methods: {
        reset(){
            this.addressName=''
            this.city=''
            this.addressList=[]
            this.addList=[]
            this.$forceUpdate();
        },
        loadmap() {
            window._AMapSecurityConfig = {
                securityJsCode: 'c834466aaf9257b82c1c38cdfc827ee4'
            }
            AMapLoader.load({
                "key": "647e84d16041ed4d41128c5812f4ac7b",
                "version": "2.0",
                "plugins": ['AutoCompletez'],
            }).then((AMap) => {
                this.map = new AMap.Map('map', {
                    zoom: 8
                });
            }).catch((e) => {
                console.log(e)
            })
        },
        search() {
            axios.post("/api/Map/search", {
                address: this.addressName,
                city: this.city
            }).then((res) => {
                if (res.status === 200) {
                    this.addressList = res.data.tips;
                    console.log(this.addressList)
                    this.$forceUpdate();
                }
            })
        },
        quickLocation(item) {
            let location = item.location
            let locationArr = location.split(',')
            console.log(locationArr[0], locationArr[1]);
        },
        addressNameChangeHandle() {
            this.addressList = []
            this.$forceUpdate();
        },
        cardRemove(item) {
            let index = this.addList.indexOf(item)
            this.addList.splice(index, 1)
        },
        addAddress(item) {
            // this.addList.push(item)
            //首先判断addList里面有没有这个item，如果有就不添加，没有就添加
            let flag = false
            for (let i = 0; i < this.addList.length; i++) {
                if (this.addList[i].id === item.id) {
                    flag = true
                    break
                }
            }
            if (!flag) {
                if (this.addList.length >= 5) {
                    alert("最多添加5个地址")
                    return
                } else {
                    if (this.addList.length === 0) {
                        item.isStart = true
                    }
                    this.addList.push(item)
                }
            }
        },
        setStart(item) {
            for (let i = 0; i < this.addList.length; i++) {
                this.addList[i].isStart = false
            }
            item.isStart = true
        },
        analyze() {
            if (this.addList.length < 2) {
                alert("请添加至少两个地址")
                return
            }
            axios.post("/api/Map/analyze", this.addList, null).then((res) => {
                this.centerPopState = true
                this.form = res.data
                this.$forceUpdate();
            });
        }

    }
}
</script>


<style scoped>
.centerContent{
/*    整个屏幕的中心位置*/
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    width: 600px;
    height: 300px;
    z-index: 999;
    border : 1px solid #ccc;
    background-color: #ffffff;
    padding: 1%;
}
.header-left {
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 4px;
    width: 400px;
    /*    浮动的 没有背景*/
    position: absolute;
    top: 60px;
    left: 0;
    z-index: 999;
}

#map {
    width: 100vw;
    height: calc(100vh - 7vh)
}

.headerLeftInput {
    margin: 4px;
    width: 210px;
}

.header-left-btn {
    margin: 4px;
}

.leftPanel {
    /*写一个侧边框额弹出面板*/
    position: absolute;
    top: 12%;
    left: 0.5%;
    width: 400px;
    /*height: 80%;*/
    background-color: #fff;
    z-index: 1;
    box-shadow: 0 0 10px rgba(0, 0, 0, .2);
    /*    透明*/
    opacity: 0.96;
    transition: all .5s;
}

.headerLeftSelect {
    width: 100px;
}

.leftPanelItem {
    /*    flex，从左到右，最右边的贴右边*/
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 4px;
    border-bottom: 1px solid #ccc;
}

.bottomCard {
    position: absolute;
    bottom: 0;
    right: 0;
    display: flex;
    /*    从右到左*/
    flex-direction: row-reverse;
    width: 100%;
    box-shadow: 0 0 10px rgba(0, 0, 0, .2);
    /*    透明*/
    opacity: 0.96;
    transition: all .5s;
}

.cardFrontBtn {
    /*    从左到右*/
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 4px;
}
</style>
