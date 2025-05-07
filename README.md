# 时间漂流瓶项目文档

## 项目概述
一个基于Spring Boot和Vue.js的漂流瓶社交应用，用户可以：
- 创建包含文字和图片的漂流瓶
- 随机拾取他人投放的漂流瓶
- 查看漂流瓶被拾取次数

## 技术栈
### 后端
- Java 17
- Spring Boot 3.x
- MyBatis-Plus
- MySQL

### 前端
- Vue 3
- Element Plus
- Vite

## 项目结构
bottles/
├── frontend/        # 前端代码
│   ├── src/
│   │   ├── views/   # 页面组件
│   │   ├── layouts/ # 布局组件
│   ├── public/
├── src/             # 后端代码
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/bottle/
│   │   │   │   ├── controller/ # API控制器
│   │   │   │   ├── service/    # 业务逻辑
│   │   │   │   ├── mapper/     # 数据访问
│   │   │   │   ├── entity/     # 数据库实体
│   ├── resources/   # 配置文件
├── uploads/         # 上传文件存储

## 核心功能实现

### 漂流瓶创建流程
1. 前端调用`/api/upload`上传图片
2. 提交漂流瓶内容到`/api/bottles`
3. 后端存储到数据库并返回结果

### 随机获取漂流瓶
```java
@GetMapping("/random")
public ResponseEntity<?> getRandomBottle() {
    BottleVO bottle = bottleService.getRandomBottle();
    bottleService.incrementPickCount(bottle.getId());
    return ResponseEntity.ok(bottle);
}