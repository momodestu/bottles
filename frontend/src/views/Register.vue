<template>
  <!-- 注册页面背景容器 -->
  <div class="register-bg">
    <!-- 背景气泡装饰元素 -->
    <div class="bubble bubble1"></div>
    <div class="bubble bubble2"></div>
    <div class="bubble bubble3"></div>
    
    <!-- 注册表单主容器 -->
    <div class="register-container">
      <!-- 注册卡片组件 -->
      <el-card class="register-card">
        <!-- 卡片标题区域 -->
        <template #header>
          <h2 class="register-title">注册</h2>
        </template>
        
        <!-- 注册表单 -->
        <el-form :model="form" :rules="rules" ref="formRef">
          <!-- 用户名输入项 -->
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="用户名" class="input-style">
              <template #prefix>
                <el-icon><User /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          
          <!-- 邮箱输入项 -->
          <el-form-item prop="email">
            <el-input v-model="form.email" placeholder="邮箱" class="input-style">
              <template #prefix>
                <el-icon><Message /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          
          <!-- 密码输入项 -->
          <el-form-item prop="password">
            <el-input v-model="form.password" type="password" placeholder="密码" class="input-style">
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          
          <!-- 确认密码输入项 -->
          <el-form-item prop="confirmPassword">
            <el-input v-model="form.confirmPassword" type="password" placeholder="确认密码" class="input-style">
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          
          <!-- 注册按钮 -->
          <el-form-item>
            <el-button type="primary" @click="handleRegister" :loading="loading" class="register-btn" block>
              注册
            </el-button>
          </el-form-item>
          
          <!-- 登录链接 -->
          <div class="login-link">
            已有账号？ <router-link to="/login">立即登录</router-link>
          </div>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
// 导入Vue相关功能
import { ref, reactive } from 'vue'
// 导入路由功能
import { useRouter } from 'vue-router'
// 导入状态管理
import { useAuthStore } from '@/stores/auth'
// 导入Element Plus组件
import { ElMessage } from 'element-plus'
// 导入Element Plus图标
import { User, Lock, Message } from '@element-plus/icons-vue'

// 初始化路由实例
const router = useRouter()
// 初始化认证存储
const authStore = useAuthStore()
// 加载状态
const loading = ref(false)

// 注册表单数据
const form = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

/**
 * 密码验证规则
 * @param rule 验证规则对象
 * @param value 输入的密码值
 * @param callback 验证回调函数
 */
const validatePass = (rule: any, value: string, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error('两次输入密码不一致!'))
  } else {
    callback()
  }
}

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 100, message: '长度在 6 到 100 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validatePass, trigger: 'blur' }
  ]
}

// 表单引用
const formRef = ref()

/**
 * 处理注册逻辑
 * 1. 验证表单
 * 2. 调用注册API
 * 3. 处理成功/失败情况
 */
async function handleRegister() {
  if (!formRef.value) return
  try {
    // 验证表单
    await formRef.value.validate()
    // 设置加载状态
    loading.value = true
    // 调用注册方法
    await authStore.register(
      form.username,
      form.email,
      form.password
    )
    // 注册成功提示
    ElMessage.success('注册成功，即将跳转到登录页面')
    // 1.5秒后跳转到登录页
    setTimeout(() => {
      router.push('/login')
    }, 1500)
  } catch (e: any) {
    // 处理邮箱已存在错误
    if (e.response?.data?.message?.includes('Duplicate entry') || 
        e.response?.data?.message?.includes('users.email')) {
      ElMessage.error('该邮箱已被注册，请使用其他邮箱或尝试找回密码')
      formRef.value.validateField('email')
    } else if (e.message) {
      // 处理其他已知错误
      ElMessage.error(e.message)
    } else {
      // 处理未知错误
      ElMessage.error('注册失败，请稍后再试')
    }
  } finally {
    // 重置加载状态
    loading.value = false
  }
}
</script>

<style scoped>
.register-bg {
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
.register-container {
  width: 100vw;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2;
}
.register-card {
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
.register-title {
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
.register-btn {
  border-radius: 20px;
  background: linear-gradient(90deg, #6dd5fa, #2980b9);
  border: none;
  font-size: 1.1em;
  font-weight: bold;
  letter-spacing: 2px;
  transition: box-shadow 0.3s, background 0.3s;
}
.register-btn:hover {
  background: linear-gradient(90deg, #2980b9, #6dd5fa);
  box-shadow: 0 4px 16px #6dd5fa55;
}
.login-link {
  text-align: center;
  margin-top: 20px;
  color: #2980b9;
  font-size: 1em;
}
.login-link a {
  color: #27a6e6;
  font-weight: bold;
  text-decoration: underline;
  transition: color 0.2s;
}
.login-link a:hover {
  color: #2980b9;
}
</style>