# Docker 部署指南

## 快速开始

### 前置条件
- Docker 已安装
- Docker Compose 已安装

### 使用 Docker Compose 一键启动

```bash
# 进入项目目录
cd mining-backend

# 启动整个系统（MySQL + Spring Boot 应用）
docker-compose up -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down

# 完全清除（包括数据）
docker-compose down -v
```

## 访问应用

- **后端 API**: http://localhost:8080
- **数据库**: localhost:3306 (用户: root, 密码: root)

## 单独构建和运行 Docker 镜像

### 构建镜像

```bash
# 在项目根目录运行
docker build -t mining-detection:1.0.0 .

# 或使用自定义标签
docker build -t myregistry/mining-detection:latest .
```

### 运行容器

```bash
# 创建并启动容器
docker run -d \
  --name mining-app \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/mining_db \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=root \
  mining-detection:1.0.0
```

## 环境变量配置

支持以下环境变量：

```bash
# 数据库配置
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/mining_db
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=root

# 服务器配置
SERVER_PORT=8080
SERVER_SERVLET_CONTEXT_PATH=/

# 日志配置
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_COM_MINING=DEBUG

# JVM 配置
JAVA_OPTS=-Xms256m -Xmx512m
```

## Docker Compose 文件详解

```yaml
services:
  mysql:
    # MySQL 8.0 数据库
    # - 自动初始化数据库
    # - 健康检查确保数据库就绪
    
  app:
    # Spring Boot 应用
    # - 依赖 MySQL 容器
    # - 自动构建 Docker 镜像
    # - 健康检查 API 端点
```

## 常见操作

### 查看容器状态

```bash
# 查看所有容器
docker-compose ps

# 查看特定容器的日志
docker-compose logs mysql
docker-compose logs app

# 实时查看日志
docker-compose logs -f app
```

### 访问容器

```bash
# 进入应用容器的 shell
docker-compose exec app /bin/sh

# 进入数据库容器
docker-compose exec mysql mysql -u root -p
```

### 数据管理

```bash
# 备份数据库
docker-compose exec mysql mysqldump -u root -proot mining_db > backup.sql

# 恢复数据库
docker-compose exec -T mysql mysql -u root -proot mining_db < backup.sql

# 查看数据卷
docker volume ls | grep mining

# 删除数据卷
docker volume rm mining-backend_mysql-data
```

## 生产环境部署

### 使用外部数据库

修改 `docker-compose.yml`，移除 MySQL 服务，只保留应用服务：

```yaml
services:
  app:
    build: .
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://prod-db-server:3306/mining_db
      SPRING_DATASOURCE_USERNAME: prod_user
      SPRING_DATASOURCE_PASSWORD: prod_password_here
    ports:
      - "8080:8080"
```

### 使用 Kubernetes

创建 `k8s-deployment.yaml`:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mining-app
spec:
  replicas: 3
  selector:
    matchLabels:
      app: mining-app
  template:
    metadata:
      labels:
        app: mining-app
    spec:
      containers:
      - name: mining-app
        image: myregistry/mining-detection:1.0.0
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_DATASOURCE_URL
          value: jdbc:mysql://mysql-service:3306/mining_db
        - name: SPRING_DATASOURCE_USERNAME
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: username
        - name: SPRING_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: password
---
apiVersion: v1
kind: Service
metadata:
  name: mining-app-service
spec:
  type: LoadBalancer
  ports:
  - port: 80
    targetPort: 8080
  selector:
    app: mining-app
```

## 故障排除

### 应用启动失败

```bash
# 查看详细日志
docker-compose logs app | tail -100

# 常见原因：
# 1. 数据库未就绪 - 等待数据库启动
# 2. 数据库连接参数错误 - 检查环境变量
# 3. 初始化 SQL 脚本错误 - 检查 init-db.sql
```

### 数据库连接失败

```bash
# 检查数据库容器是否运行
docker-compose ps

# 测试数据库连接
docker-compose exec mysql mysql -u root -proot -e "SELECT 1;"

# 检查网络连接
docker-compose exec app ping mysql
```

### 端口被占用

```bash
# 修改 docker-compose.yml 中的端口映射
# ports:
#   - "8081:8080"  # 改为 8081

# 然后重启服务
docker-compose down
docker-compose up -d
```

## 性能优化

### JVM 内存配置

修改 `Dockerfile` 中的 JAVA_OPTS：

```dockerfile
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"
```

### 数据库优化

在 `docker-compose.yml` 中添加 MySQL 优化参数：

```yaml
mysql:
  command: --max-connections=1000 --default-storage-engine=InnoDB
```

## 清理和维护

```bash
# 删除未使用的容器和镜像
docker system prune

# 删除所有关于 mining 的容器和镜像
docker system prune --filter "label=mining-detection"

# 更新镜像
docker-compose pull
docker-compose up -d
```

## 监控和日志

### 集成 ELK Stack（可选）

可以添加 Elasticsearch、Logstash 和 Kibana 进行集中日志管理：

```yaml
services:
  # ... 现有服务 ...
  
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.0.0
    environment:
      - discovery.type=single-node
    ports:
      - "9200:9200"
  
  logstash:
    image: docker.elastic.co/logstash/logstash:8.0.0
    # 配置 logstash.conf
```

## 更多信息

- [Docker 官方文档](https://docs.docker.com/)
- [Docker Compose 文档](https://docs.docker.com/compose/)
- [Spring Boot Docker 指南](https://spring.io/guides/gs/spring-boot-docker/)
