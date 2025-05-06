<template>
  <div class="layout-container">
    <div class="ocean">
      <div class="wave wave1"></div>
      <div class="wave wave2"></div>
      <div class="wave wave3"></div>
    </div>

    <div class="layout-header">
      <div class="header-content">
        <h1 class="site-title">时间漂流瓶</h1>
        <div class="user-info" v-if="authStore.isAuthenticated">
          <span class="username">{{ authStore.username }}</span>
          <el-button type="text" @click="handleLogout">退出登录</el-button>
        </div>
      </div>
    </div>

    <div class="layout-main">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

function handleLogout() {
  authStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  min-height: 100vh;
  background: linear-gradient(to bottom, #1e90ff, #87ceeb);
  position: relative;
  overflow: hidden;
}

.ocean {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.wave {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 200%;
  height: 100px;
  background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill="%23ffffff" fill-opacity="0.3" d="M0,192L48,197.3C96,203,192,213,288,229.3C384,245,480,267,576,250.7C672,235,768,181,864,181.3C960,181,1056,235,1152,234.7C1248,235,1344,181,1392,154.7L1440,128L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>');
  background-size: 1440px 100px;
  animation: wave 10s linear infinite;
}

.wave1 {
  bottom: 0;
  opacity: 0.3;
  animation-delay: 0s;
}

.wave2 {
  bottom: 10px;
  opacity: 0.2;
  animation-delay: -5s;
}

.wave3 {
  bottom: 20px;
  opacity: 0.1;
  animation-delay: -2s;
}

@keyframes wave {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}

.layout-header {
  position: relative;
  z-index: 1;
  padding: 20px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.site-title {
  color: #ffffff;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
  font-size: 2em;
  margin: 0;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 16px;
}

.username {
  color: #ffffff;
  font-size: 1.1em;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

:deep(.el-button--text) {
  color: #ffffff;
  font-size: 1.1em;
}

:deep(.el-button--text:hover) {
  color: #e6e6e6;
}

.layout-main {
  position: relative;
  z-index: 1;
  padding: 40px 20px;
  max-width: 1200px;
  margin: 0 auto;
}
</style> 