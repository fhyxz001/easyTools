<template>
  <!--    在网站底部加一个放备案号的地方-->
    <div class="footer">
        <a>备案号：</a>
        <a href="https://beian.miit.gov.cn/#/Integrated/index" style="color: #333; text-decoration: none;">鲁ICP备2021040011号</a>
    </div>
</template>

<script>
export default {
    name: "footer"
}
</script>

<style scoped>
.footer{
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 30px;
    line-height: 30px;
    text-align: center;
    background-color: #f5f5f5;
}
</style>