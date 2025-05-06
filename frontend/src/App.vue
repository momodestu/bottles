<template>
  <div class="app-container">
    <router-view></router-view>
  </div>
</template>

<script setup lang="ts">
// 不再需要用户信息和登出逻辑
</script>

<style scoped>
.app-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}
</style> 