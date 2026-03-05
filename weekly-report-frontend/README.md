# 周报定时发送助手 - 前端

基于 Vue 3 + TypeScript + Vite + Pinia + Vue Router。

## 功能

- 登录
- 工作台概览
- 项目管理（按项目维护）
- 周报收集（按项目、按周填写当周信息）
- 收件人管理（指定邮件收件人）
- 定时发送任务配置

## 环境要求

- Node.js 18+
- npm 或 pnpm

## 安装与运行

```bash
npm install
npm run dev
```

开发环境：http://localhost:5173，接口通过 Vite 代理到后端 `http://localhost:8080/api`。

## 构建

```bash
npm run build
```

产物在 `dist/`，可部署到任意静态服务器；需配置反向代理将 `/api` 转发到后端。

## 默认登录

与后端初始数据一致：用户名 `admin`，密码 `admin123`。
