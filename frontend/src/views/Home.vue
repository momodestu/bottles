<template>
  <MainLayout>
    <div v-if="!currentBottle" class="choice-container">
      <h2 class="subtitle">选择你的行动</h2>
      <div class="choice-buttons">
        <el-button type="primary" size="large" @click="getRandomBottle" class="action-button">
          <i class="el-icon-fishing"></i>
          拾取漂流瓶
        </el-button>
        <el-button type="success" size="large" @click="goToWrite" class="action-button">
          <i class="el-icon-edit"></i>
          写漂流瓶
        </el-button>
      </div>
        </div>
    <el-card v-else class="bottle-card" :class="{ 'bottle-float': true }">
          <template #header>
            <div class="bottle-header">
          <span class="bottle-author">{{ currentBottle.username }}</span>
              <span class="time">{{ formatTime(currentBottle.createdAt) }}</span>
            </div>
          </template>
          <div class="bottle-content" v-html="currentBottle.content"></div>
      <img v-if="currentBottle.imageUrl" :src="currentBottle.imageUrl" class="bottle-image" />
      <p class="bottle-info">
        <i class="el-icon-view"></i>
        拾取次数: {{ currentBottle.pickCount }}
      </p>
          <template #footer>
            <div class="bottle-footer">
          <el-button @click="handleThrowBack" class="throw-button">
            <i class="el-icon-delete"></i>
            扔回海里
          </el-button>
          <el-button type="success" @click="goToWrite" class="write-button">
            <i class="el-icon-edit"></i>
            写漂流瓶
          </el-button>
            </div>
          </template>
        </el-card>
  </MainLayout>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import axios from 'axios'
import { useAuthStore } from '@/stores/auth'
import MainLayout from '@/layouts/MainLayout.vue'

dayjs.extend(relativeTime)

interface Bottle {
  id: number
  username: string
  content: string
  imageUrl?: string
  createdAt: string
  pickCount: number
}

const router = useRouter()
const authStore = useAuthStore()
const currentBottle = ref<Bottle | null>(null)

function formatTime(time: string) {
  return dayjs(time).fromNow()
}

async function getRandomBottle() {
  try {
    const response = await axios.get('/api/bottles/random')
    currentBottle.value = response.data
  } catch (error) {
    ElMessage.error('获取漂流瓶失败')
  }
}

function handleThrowBack() {
  currentBottle.value = null
}

function goToWrite() {
  router.push('/write')
}

onMounted(() => {
  if (!authStore.isAuthenticated) {
    router.push('/login')
  }
})
</script>

<style scoped>
.choice-container {
  text-align: center;
  padding: 40px 0;
  color: #ffffff;
}
.subtitle {
  font-size: 2em;
  margin-bottom: 30px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}
.choice-buttons {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin-top: 20px;
}
.action-button {
  padding: 15px 30px;
  font-size: 1.2em;
  border-radius: 25px;
  transition: transform 0.3s ease;
}
.action-button:hover {
  transform: translateY(-5px);
}
.bottle-card {
  max-width: 800px;
  margin: 0 auto;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 15px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  transition: transform 0.3s ease;
}
.bottle-float {
  animation: float 6s ease-in-out infinite;
}
@keyframes float {
  0% { transform: translateY(0px); }
  50% { transform: translateY(-10px); }
  100% { transform: translateY(0px); }
}
.bottle-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
}
.bottle-author {
  font-weight: bold;
  color: #409EFF;
}
.time {
  color: #909399;
  font-size: 14px;
}
.bottle-content {
  margin: 20px 0;
  line-height: 1.8;
  color: #303133;
  font-size: 1.1em;
}
.bottle-image {
  max-width: 100%;
  margin: 20px 0;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
.bottle-info {
  color: #909399;
  font-size: 14px;
  margin-top: 10px;
  display: flex;
  align-items: center;
  gap: 5px;
}
.bottle-footer {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
}
.throw-button, .write-button {
  padding: 12px 25px;
  border-radius: 20px;
  transition: all 0.3s ease;
}
.throw-button:hover, .write-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}
</style> 