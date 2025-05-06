<template>
  <div class="create-bottle-container">
    <!-- 海洋波浪背景 -->
    <div class="ocean">
      <div class="wave"></div>
      <div class="wave"></div>
    </div>
    
    <!-- 漂流瓶表单卡片 -->
    <div class="bottle-card">
      <h2 class="title">写下你的漂流瓶</h2>
      
      <el-form :model="form" :rules="rules" ref="formRef">
        <!-- 内容输入区 -->
        <el-form-item prop="content">
          <el-input
            type="textarea"
            v-model="form.content"
            :rows="5"
            placeholder="写下你想说的话..."
            class="bottle-textarea"
          ></el-input>
        </el-form-item>
        
        <!-- 图片上传 -->
        <el-form-item>
          <el-upload
            action="/api/upload"
            :on-success="handleUploadSuccess"
            :before-upload="beforeUpload"
            :show-file-list="false"
          >
            <el-button type="primary" icon="el-icon-upload">添加图片</el-button>
            <div slot="tip" class="upload-tip">支持JPG/PNG格式，大小不超过2MB</div>
          </el-upload>
        </el-form-item>
        
        <!-- 提交按钮 -->
        <el-form-item>
          <el-button 
            type="primary" 
            @click="submitForm" 
            :loading="loading"
            class="submit-btn"
          >
            放入大海
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        content: '',
        imageUrl: ''
      },
      loading: false,
      rules: {
        content: [
          { required: true, message: '请填写漂流瓶内容', trigger: 'blur' },
          { min: 10, message: '至少输入10个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm() {
      // ... 提交逻辑 ...
    },
    handleUploadSuccess(response) {
      // ... 上传成功处理 ...
    },
    beforeUpload(file) {
      // ... 上传前验证 ...
    }
  }
}
</script>

<style scoped>
.create-bottle-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(to bottom, #1e90ff, #00bfff);
  position: relative;
  overflow: hidden;
}

.ocean {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100px;
}

.wave {
  background: url('~@/assets/wave.svg') repeat-x;
  position: absolute;
  bottom: 0;
  width: 6400px;
  height: 100px;
  animation: wave 7s cubic-bezier(0.36,0.45,0.63,0.53) infinite;
}

.wave:nth-of-type(2) {
  bottom: 10px;
  opacity: 0.5;
  animation: wave 10s cubic-bezier(0.36,0.45,0.63,0.53) infinite;
}

@keyframes wave {
  0% { transform: translateX(0); }
  100% { transform: translateX(-1600px); }
}

.bottle-card {
  width: 90%;
  max-width: 600px;
  background: rgba(255,255,255,0.9);
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.1);
  z-index: 1;
}

.title {
  text-align: center;
  color: #1e90ff;
  margin-bottom: 30px;
  font-size: 28px;
}

.bottle-textarea {
  font-size: 16px;
  line-height: 1.6;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 18px;
  margin-top: 20px;
}

.upload-tip {
  font-size: 12px;
  color: #999;
  margin-top: 10px;
}
</style>