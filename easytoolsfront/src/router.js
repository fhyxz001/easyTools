import { createRouter, createWebHashHistory, createWebHistory } from "vue-router"

import EasyCopy from "./components/easyCopy.vue"
import CardList from "./components/cardList.vue"

const routes = [
    {
        path: "/",
        redirect: "/CardList",
    },
    { path: "/CardList", component: CardList },
    { path: "/EasyCopy", component: EasyCopy },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})
export default router