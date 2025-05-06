import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

interface LoginResponse {
  token: string
  type: string
  username: string
}

interface ErrorResponse {
  message: string
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    username: localStorage.getItem('username') || '',
    isAuthenticated: !!localStorage.getItem('token')
  }),

  actions: {
    async login(username: string, password: string) {
      try {
        const response = await axios.post<LoginResponse>('/api/auth/login', {
          username,
          password
        })

        const { token, username: responseUsername } = response.data

        // 保存token和用户名
        localStorage.setItem('token', token)
        localStorage.setItem('username', responseUsername)

        // 更新状态
        this.token = token
        this.username = responseUsername
        this.isAuthenticated = true

        // 设置axios默认header
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`

        // 跳转到首页
        await router.push('/')
      } catch (error: any) {
        if (error.response?.data?.message) {
          throw new Error(error.response.data.message)
        }
        throw new Error('登录失败，请稍后重试')
      }
    },

    async register(username: string, email: string, password: string) {
      try {
        await axios.post('/api/auth/register', {
          username,
          email,
          password
        })
      } catch (error: any) {
        if (error.response?.data?.message) {
          throw new Error(error.response.data.message)
        }
        throw new Error('注册失败，请稍后重试')
      }
    },

    logout() {
      // 清除本地存储
      localStorage.removeItem('token')
      localStorage.removeItem('username')

      // 清除状态
      this.token = ''
      this.username = ''
      this.isAuthenticated = false

      // 清除axios默认header
      delete axios.defaults.headers.common['Authorization']

      // 跳转到登录页
      router.push('/login')
    }
  }
}) 