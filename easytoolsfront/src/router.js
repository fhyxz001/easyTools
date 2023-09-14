import { createRouter, createWebHashHistory, createWebHistory } from "vue-router"

import EasyCopy from "./components/easyCopy.vue"
import CardList from "./components/cardList.vue"
import GirlPrizeDraw  from "@/components/GirlPrizeDraw.vue";
import WaifuIm from "@/components/WaifuIm.vue";
import MuchAddSelect from "@/components/MuchAddSelect.vue";
import HearthStoneIMBA  from "@/components/HearthStoneIMBA.vue";
const routes = [
    {
        path: "/",
        redirect: "/CardList",
    },
    { path: "/CardList", component: CardList },
    { path: "/EasyCopy", component: EasyCopy },
    { path: "/GirlPrizeDraw", component: GirlPrizeDraw},
    { path: "/WaifuIm", component: WaifuIm},
    {path: "/MuchAddSelect", component: MuchAddSelect},
    {path: "/HearthStoneIMBA", component: HearthStoneIMBA},
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})
export default router
