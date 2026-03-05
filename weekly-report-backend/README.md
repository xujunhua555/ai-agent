# 周报定时发送助手 - 后端

基于 Spring Boot 2.6 + MyBatis + MySQL。

## 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7 / 8.0（库名：weekly_report）

## 数据库初始化

1. 使用 MySQL 客户端或工具执行脚本：
   ```
   src/main/resources/db/schema.sql
   ```
2. 脚本会创建数据库 `weekly_report`、表结构及初始数据。
3. 默认管理员：用户名 `admin`，密码 `admin123`。

## 配置说明

- 数据库：`application.yml` 中已配置 `localhost:3306`，用户名 `root`，密码需与本地一致。
- 邮件：在 `application.yml` 的 `spring.mail` 下配置实际 SMTP 信息后，方可发送邮件。

## 运行

```bash
mvn spring-boot:run
```

服务启动后访问：http://localhost:8080/api

## 日志

- 使用 Logback，配置文件：`src/main/resources/logback-spring.xml`。
- 控制台与文件同时输出，日志文件在项目目录下 `logs/weekly-report.log`。

## API 概览

- `POST /api/auth/login` - 登录
- `GET /api/project/list` - 项目列表
- `GET/POST/DELETE /api/project/*` - 项目 CRUD
- `GET /api/weekly-report/list` - 周报列表（按年、周）
- `POST /api/weekly-report/save` - 保存周报
- `GET/POST/DELETE /api/email-recipient/*` - 收件人 CRUD
- `GET/POST/DELETE /api/schedule-task/*` - 定时任务 CRUD
