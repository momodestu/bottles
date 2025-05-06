<template>
  <div class="login-bg">
    <div class="bubble bubble1"></div>
    <div class="bubble bubble2"></div>
    <div class="bubble bubble3"></div>
    <div class="login-container">
      <el-card class="login-card">
        <template #header>
          <h2 class="login-title">登录</h2>
        </template>
        <el-form :model="form" :rules="rules" ref="formRef">
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="用户名" class="input-style">
              <template #prefix>
                <el-icon><User /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" type="password" placeholder="密码" class="input-style">
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleLogin" :loading="loading" class="login-btn" block>
              登录
            </el-button>
          </el-form-item>
          <div class="register-link">
            还没有账号？ <router-link to="/register">立即注册</router-link>
          </div>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'

const router = useRouter()
const authStore = useAuthStore()
const loading = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 100, message: '长度在 6 到 100 个字符', trigger: 'blur' }
  ]
}

const formRef = ref()

const handleLogin = async () => {
  if (!formRef.value) return
  try {
    await formRef.value.validate()
    loading.value = true
    await authStore.login(form.username, form.password)
    ElMessage.success('登录成功')
  } catch (error: any) {
    ElMessage.error(error.message || '登录失败，请稍后重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-bg {
  min-height: 100vh;
  width: 100vw;
  background: linear-gradient(135deg, #6dd5fa 0%, #2980b9 100%);
  position: relative;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}
.bubble {
  position: absolute;
  border-radius: 50%;
  opacity: 0.15;
  z-index: 1;
  background: #fff;
  animation: float-bubble 8s infinite ease-in-out;
}
.bubble1 { width: 120px; height: 120px; left: 10vw; bottom: 10vh; animation-delay: 0s; }
.bubble2 { width: 80px; height: 80px; right: 15vw; top: 15vh; animation-delay: 2s; }
.bubble3 { width: 60px; height: 60px; left: 50vw; top: 30vh; animation-delay: 4s; }
@keyframes float-bubble {
  0% { transform: translateY(0); }
  50% { transform: translateY(-30px); }
  100% { transform: translateY(0); }
}
.login-container {
  width: 100vw;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2;
}
.login-card {
  width: 100%;
  max-width: 400px;
  border-radius: 18px;
  box-shadow: 0 8px 32px rgba(44, 62, 80, 0.18);
  background: rgba(255,255,255,0.98);
  animation: float-card 6s ease-in-out infinite;
}
@keyframes float-card {
  0% { transform: translateY(0); }
  50% { transform: translateY(-8px); }
  100% { transform: translateY(0); }
}
.login-title {
  text-align: center;
  font-size: 2em;
  font-weight: bold;
  background: linear-gradient(90deg, #2980b9, #6dd5fa);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0;
}
.input-style :deep(.el-input__wrapper) {
  border-radius: 12px;
  box-shadow: none;
  border: 1.5px solid #e0eafc;
  transition: border-color 0.3s;
}
.input-style :deep(.el-input__wrapper.is-focus) {
  border-color: #6dd5fa;
  box-shadow: 0 0 0 2px #6dd5fa33;
}
.login-btn {
  border-radius: 20px;
  background: linear-gradient(90deg, #6dd5fa, #2980b9);
  border: none;
  font-size: 1.1em;
  font-weight: bold;
  letter-spacing: 2px;
  transition: box-shadow 0.3s, background 0.3s;
}
.login-btn:hover {
  background: linear-gradient(90deg, #2980b9, #6dd5fa);
  box-shadow: 0 4px 16px #6dd5fa55;
}
.register-link {
  text-align: center;
  margin-top: 20px;
  color: #2980b9;
  font-size: 1em;
}
.register-link a {
  color: #27a6e6;
  font-weight: bold;
  text-decoration: underline;
  transition: color 0.2s;
}
.register-link a:hover {
  color: #2980b9;
}
</style> 