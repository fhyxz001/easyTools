<template>
<div class="game">
    <div class="main_space">
        <div class="rival_hand">
            <div class="rival_magic_crystal"></div>
        </div>
        <div class="rival_battle"></div>
        <div class="my_battle"></div>
        <div class="my_hand">
            <div class="card_front_list" v-for="card in myhandCardList">
                <div class="card_front"
                     @mousedown="startDragging"
                     @mousemove="drag"
                     @mouseup="stopDragging"

                >
                    <div class="card_front_title">{{card.cardName}}</div>
                    <div class="card_front_cost">{{card.cost}}费</div>
                    <div class="card_front_attk">{{card.attk}}atk</div>
                    <div class="card_front_hp">{{card.hp}}hp</div>
                </div>
            </div>
            <div class="my_magic_crystal"></div>
        </div>
        <div class="end_turn">
            <button>结束回合</button>
        </div>
    </div>
    <div class="deck_space">
        <div class="rival_deck"></div>
        <div class="my_deck"></div>
    </div>
</div>
</template>

<script>
export default {
    name: "HearthStoneIMBA",
    data(){
        return{
            card:{

            },
            myhandCardList:[
                {cardName:'精灵弓箭手',attk:'1',hp:'1',cost:'1',cardType:'0',x:0, y:0, isDragging:false, offsetX:0, offsetY:0},
                {cardName:'淡水鳄',attk:'2',hp:'3',cost:'2',cardType:'0',x:0, y:0, isDragging:false, offsetX:0, offsetY:0},
                {cardName:'工程师学徒',attk:'2',hp:'4',cost:'3',cardType:'0',x:0, y:0, isDragging:false, offsetX:0, offsetY:0},
                {cardName:'竞技场守卫',attk:'6',hp:'5',cost:'6',cardType:'0',x:0, y:0, isDragging:false, offsetX:0, offsetY:0},
            ],
            mybattleCardList:[],
            rivalhandCardList:[
                {cardName:'1',attk:'1',hp:'1',cost:'1',cardType:'0'},
                {cardName:'2',attk:'2',hp:'2',cost:'2',cardType:'0'}],
            rivalbattleCardList:[],
        }
    },
    methods:{
        startDragging(event) {
            this.card.isDragging = true;
            this.card.offsetX = event.clientX - this.card.x;
            this.card.offsetY = event.clientY - this.card.y;

            // 添加样式以实现拖拽效果
            this.$refs.card.style.transition = 'none';
            this.$refs.card.style.zIndex = '1000';
            this.$refs.card.style.cursor = 'grabbing';
        },
        drag(event) {
            if (this.card.isDragging) {
                this.card.x = event.clientX - this.card.offsetX;
                this.card.y = event.clientY - this.card.offsetY;
            }
        },
        stopDragging() {
            if (this.card.isDragging) {
                // 恢复默认样式
                this.$refs.card.style.transition = 'transform 0.3s ease';
                this.$refs.card.style.transform = 'none';
                this.$refs.card.style.zIndex = 'auto';
                this.$refs.card.style.cursor = 'grab';

                this.card.isDragging = false;
            }
        }
    }
}
</script>

<style scoped>
.game{
}
.main_space{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 70%;
    height: 80%;
    background-color: #f5f5f5;
}
.rival_hand{
    border: 1px solid black;
    height: 20%;
}
.rival_battle{
    border: 1px solid black;
    height: 30%;
    background-color: #f6fbff;
}
.my_battle{
    border: 1px solid black;
    height: 30%;
    background-color: #f6fbff;
}
.my_hand{
    border: 1px solid black;
    height: 20%;
    display: flex;
    justify-content: center;
}
.end_turn{
    width: 80px;
    height: 50px;
    border: 1px solid black;
/*    靠右居中*/
    position: absolute;
    top: 50%;
    right: 0;
    transform: translate(0, -50%);
    background-color: #f6fbff;
    text-align: center;
    line-height: 50px;
}
.deck_space{
    background-color: #e2e2f8;
    height: 87%;
    width: 14.5%;
    position: absolute;
    right: 0;
}
.rival_deck{

}
.my_deck{

}
.card_front_list{

}
.card_front{
    width: 100px;
    height: 150px;
    background-color: #f6fbff;
    border: 1px solid black;
/*    只读*/
    user-select:none;
    cursor: grab;
    margin: 5px;
}
/*当鼠标移动到card_front的时候上移放大*/
.card_front:hover{
    transform: translateY(-20px);
}
.card_front:active{
    cursor: grabbing;
}

.card_front_attk{

}
</style>
