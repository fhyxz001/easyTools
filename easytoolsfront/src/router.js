import { createRouter, createWebHashHistory, createWebHistory } from "vue-router"

import EasyCopy from "./components/easyCopy.vue"
import CardList from "./components/cardList.vue"
import GirlPrizeDraw  from "@/components/GirlPrizeDraw.vue";
import WaifuIm from "@/components/WaifuIm.vue";
const routes = [
    {
        path: "/",
        redirect: "/CardList",
    },
    { path: "/CardList", component: CardList },
    { path: "/EasyCopy", component: EasyCopy },
    { path: "/GirlPrizeDraw", component: GirlPrizeDraw},
    { path: "/WaifuIm", component: WaifuIm},
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})
export default router
