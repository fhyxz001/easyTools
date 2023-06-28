<template>
    <div class="headerflex">
    <n-menu v-model:value="activeKey" mode="horizontal" disabled:true :options="menuOptions" />
    </div>
</template>

<script>
import { defineComponent, h, ref } from "vue";
import { NIcon,NMenu,NButton } from "naive-ui";
import {
    StarHalfSharp as StarHalfSharp
} from "@vicons/ionicons5";

function renderIcon(icon) {
    return () => h(NIcon, null, { default: () => h(icon) });
}

const menuOptions = [
    {
        label: () => h(
            "a",
            {
                // href: "/",
                // target: "_blank",
                // rel: "noopenner noreferrer"
            },
            "EasyTools~ 简单易用的工具箱"
        ),
        key: "hear-the-wind-sing",
        icon: renderIcon(StarHalfSharp)
    }
];
export default defineComponent({
    components: {
        NMenu,NButton
    },
    setup() {
        return {
            activeKey: ref(null),
            menuOptions
        };
    }
});
</script>
<style scoped>
.headerflex{
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
    height: 60px;
    background-color: #fff;
    box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
}

</style>
