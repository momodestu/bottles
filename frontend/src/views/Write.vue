<template>
  <div class="write-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h1>写漂流瓶</h1>
          <el-button @click="goBack">返回</el-button>
        </div>
      </el-header>
      
      <el-main>
        <el-card class="write-card">
          <el-form :model="form" :rules="rules" ref="formRef">
            <el-form-item prop="content">
              <el-input
                v-model="form.content"
                type="textarea"
                :rows="10"
                placeholder="写下你想说的话..."
              />
            </el-form-item>
            
            <el-form-item>
              <el-upload
                class="image-upload"
                action="/api/upload"
                :headers="uploadHeaders"
                :before-upload="beforeUpload"
                :on-success="handleUploadSuccess"
                :on-error="handleUploadError"
                :show-file-list="false"
                accept="image/*"
                name="file"
              >
                <el-button type="primary">上传图片</el-button>
                <template #tip>
                  <div class="el-upload__tip">
                    只能上传jpg/png文件，且不超过2MB
                  </div>
                </template>
              </el-upload>
              <div v-if="form.image" class="image-preview">
                <img :src="form.image" alt="预览图" />
                <el-button type="danger" size="small" @click="removeImage">删除图片</el-button>
              </div>
            </el-form-item>
            
            <el-form-item>
              <el-button type="primary" @click="handleSubmit" :loading="loading" block>
                扔进大海
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()
const loading = ref(false)

const form = reactive({
  content: '',
  image: ''
})

const rules = {
  content: [
    { required: true, message: '请输入内容', trigger: 'blur' },
    { min: 1, max: 1000, message: '长度在 1 到 1000 个字符', trigger: 'blur' }
  ]
}

const formRef = ref()

const uploadHeaders = {
  Authorization: `Bearer ${authStore.token}`
}

const beforeUpload = (file: File) => {
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isImage) {
    ElMessage.error('只能上传图片文件！')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过 2MB！')
    return false
  }
  return true
}

const handleUploadSuccess = (response: any) => {
  if (response.code === 200) {
    form.image = response.data
    ElMessage.success('图片上传成功')
  } else {
    ElMessage.error(response.message || '图片上传失败')
  }
}

const handleUploadError = (error: any) => {
  console.error('Upload error:', error)
  ElMessage.error('图片上传失败，请稍后重试')
}

const removeImage = () => {
  form.image = ''
}

const goBack = () => {
  router.push('/')
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    loading.value = true
    
    const response = await fetch('/api/bottles', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${authStore.token}`
      },
      body: JSON.stringify({
        content: form.content,
        image: form.image
      })
    })

    if (!response.ok) {
      throw new Error('提交失败')
    }

    ElMessage.success('漂流瓶已扔进大海')
    router.push('/')
  } catch (error: any) {
    ElMessage.error(error.message || '提交失败，请稍后重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.write-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.write-card {
  max-width: 800px;
  margin: 0 auto;
}

.image-upload {
  margin-bottom: 20px;
}

.image-preview {
  margin-top: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.image-preview img {
  max-width: 200px;
  max-height: 200px;
  object-fit: contain;
}
</style> 