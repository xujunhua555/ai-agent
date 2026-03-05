-- 周报定时发送助手 - 数据库初始化脚本
-- MySQL 5.8 兼容

CREATE DATABASE IF NOT EXISTS weekly_report DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE weekly_report;

-- 用户表（登录）
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    username VARCHAR(64) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(128) NOT NULL COMMENT '密码（加密存储）',
    real_name VARCHAR(64) DEFAULT NULL COMMENT '真实姓名',
    email VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    status TINYINT DEFAULT 1 COMMENT '状态 0 禁用 1 启用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 角色表
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    code VARCHAR(64) NOT NULL UNIQUE COMMENT '角色编码',
    name VARCHAR(64) NOT NULL COMMENT '角色名称',
    description VARCHAR(256) DEFAULT NULL COMMENT '描述',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表（支持树形，parent_id 为 0 表示菜单/一级）
DROP TABLE IF EXISTS sys_permission;
CREATE TABLE sys_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    parent_id BIGINT DEFAULT 0 COMMENT '父级 ID，0 为顶级',
    code VARCHAR(128) NOT NULL COMMENT '权限编码，如 user:list',
    name VARCHAR(64) NOT NULL COMMENT '权限名称',
    type VARCHAR(16) DEFAULT 'menu' COMMENT '类型 menu 菜单 button 按钮',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 用户 - 角色关联
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    role_id BIGINT NOT NULL COMMENT '角色 ID',
    PRIMARY KEY (user_id, role_id),
    KEY idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联';

-- 角色 - 权限关联
DROP TABLE IF EXISTS sys_role_permission;
CREATE TABLE sys_role_permission (
    role_id BIGINT NOT NULL COMMENT '角色 ID',
    permission_id BIGINT NOT NULL COMMENT '权限 ID',
    PRIMARY KEY (role_id, permission_id),
    KEY idx_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联';

-- 邮件收件人配置表
DROP TABLE IF EXISTS email_recipient;
CREATE TABLE email_recipient (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    name VARCHAR(64) DEFAULT NULL COMMENT '收件人用户名',
    email VARCHAR(128) NOT NULL COMMENT '收件人邮箱',
    group_id BIGINT DEFAULT NULL COMMENT '所属组别 ID',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认收件人 0 否 1 是',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件收件人表';

-- 邮件收件人组别表
DROP TABLE IF EXISTS email_group;
CREATE TABLE email_group (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    name VARCHAR(64) NOT NULL UNIQUE COMMENT '组别名称',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件收件人组别表';

-- 邮件发件人配置表
DROP TABLE IF EXISTS email_sender;
CREATE TABLE email_sender (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    name VARCHAR(64) DEFAULT NULL COMMENT '发件人名称',
    email VARCHAR(128) NOT NULL COMMENT '发件邮箱地址',
    password VARCHAR(256) NOT NULL COMMENT '发件邮箱密码',
    group_id BIGINT DEFAULT NULL COMMENT '所属组别 ID',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认发件人 0 否 1 是',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件发件人表';

-- 定时发送任务表
DROP TABLE IF EXISTS schedule_task;
CREATE TABLE schedule_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    task_name VARCHAR(128) NOT NULL COMMENT '任务名称',
    cron_expression VARCHAR(64) NOT NULL COMMENT 'Cron 表达式',
    sender_id BIGINT DEFAULT NULL COMMENT '发件人 ID',
    recipient_ids VARCHAR(256) DEFAULT NULL COMMENT '收件人 ID 列表，逗号分隔',
    subject VARCHAR(256) NOT NULL COMMENT '邮件主题',
    content TEXT NOT NULL COMMENT '邮件正文',
    enabled TINYINT DEFAULT 1 COMMENT '是否启用 0 否 1 是',
    last_run_time DATETIME DEFAULT NULL COMMENT '上次执行时间',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时发送任务表';

-- 初始管理员（密码：admin123，实际应使用 BCrypt 等加密，此处示例）
INSERT INTO sys_user (username, password, real_name, email, status) VALUES
('admin', 'admin123', '管理员', 'admin@example.com', 1);

-- 初始角色与权限
INSERT INTO sys_role (code, name, description) VALUES
('admin', '超级管理员', '拥有全部权限'),
('user', '普通用户', '仅查看基础信息');

INSERT INTO sys_permission (parent_id, code, name, type, sort_order) VALUES
(0, 'dashboard', '工作台', 'menu', 10),
(0, 'recipient', '收件人管理', 'menu', 40),
(0, 'group', '组别管理', 'menu', 45),
(0, 'sender', '发件人管理', 'menu', 48),
(0, 'schedule', '定时发送管理', 'menu', 50),
(0, 'system', '系统管理', 'menu', 100),
(4, 'user:list', '用户管理', 'menu', 1),
(4, 'role:list', '角色管理', 'menu', 2),
(5, 'user:add', '新增用户', 'button', 1),
(5, 'user:edit', '编辑用户', 'button', 2),
(5, 'user:delete', '删除用户', 'button', 3),
(6, 'role:add', '新增角色', 'button', 1),
(6, 'role:edit', '编辑角色', 'button', 2),
(6, 'role:delete', '删除角色', 'button', 3);

-- 超级管理员拥有所有权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 1, id FROM sys_permission;

-- 普通用户：工作台、收件人、组别、定时发送
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 2, id FROM sys_permission WHERE code IN ('dashboard', 'recipient', 'group', 'sender', 'schedule');

-- 管理员用户绑定超级管理员角色
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 示例收件人组别
INSERT INTO email_group (name, sort_order) VALUES
('服务端开发组', 10),
('产品设计组', 20),
('客户端 ios 组', 30),
('客户端安卓组', 40),
('客户端鸿蒙组', 50),
('前端开发组', 60),
('手厅郑州项目组', 70);

-- 示例收件人（假设上面的 email_group 自增 ID 从 1 开始按顺序插入）
INSERT INTO email_recipient (name, email, group_id, is_default) VALUES
('张三', 'zhangsan@example.com', 1, 1),
('李四', 'lisi@example.com', 6, 0);

-- 示例发件人（默认使用当前系统邮箱配置）
INSERT INTO email_sender (name, email, password, is_default) VALUES
('系统默认发件人', 'dongxiaobo@richinfo.cn', 'your_password_here', 1);
