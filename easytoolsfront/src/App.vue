<template>
  <div id="app">
      <div class="Header">
          <Header/>
      </div>
      <div class="Main">
          <router-view></router-view>
      </div>
      <div class="Footer">
          <Footer/>
      </div>
  </div>
</template>
<script setup>
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'

</script>

<style scoped>

</style>
