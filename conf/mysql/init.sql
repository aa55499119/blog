-- ============================================
-- 微服务数据库初始化
-- ============================================

CREATE DATABASE IF NOT EXISTS `micro_alibaba` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `micro_alibaba`;

-- 用户表
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `balance` int DEFAULT '0' COMMENT '余额(分)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除: 0未删, 1已删',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 商品表
CREATE TABLE IF NOT EXISTS `t_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL COMMENT '商品名称',
  `description` varchar(1024) DEFAULT NULL COMMENT '商品描述',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `stock` int DEFAULT '0' COMMENT '库存',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除: 0未删, 1已删',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 订单表
CREATE TABLE IF NOT EXISTS `t_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `quantity` int NOT NULL COMMENT '数量',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `status` tinyint DEFAULT '0' COMMENT '状态: 0待支付, 1已支付, 2已取消',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 仓储表 (Seata 分布式事务演示)
CREATE TABLE IF NOT EXISTS `t_storage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `total_stock` int DEFAULT '0' COMMENT '总库存',
  `used_stock` int DEFAULT '0' COMMENT '已用库存',
  `residue_stock` int DEFAULT '0' COMMENT '剩余库存',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓储表';

-- Seata AT 模式所需 undo_log 表
CREATE TABLE IF NOT EXISTS `undo_log` (
  `branch_id` bigint NOT NULL COMMENT '分支事务ID',
  `xid` varchar(128) NOT NULL COMMENT '全局事务ID',
  `context` varchar(128) DEFAULT NULL COMMENT '上下文',
  `rollback_info` longblob COMMENT '回滚日志',
  `log_status` int DEFAULT '0' COMMENT '日志状态',
  `log_created` datetime DEFAULT CURRENT_TIMESTAMP,
  `log_modified` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`branch_id`),
  KEY `idx_xid` (`xid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Seata AT undo log';

-- 初始化测试数据
INSERT INTO `t_user` (`username`, `password`, `balance`) VALUES ('zhangsan', '123456', 10000);
INSERT INTO `t_user` (`username`, `password`, `balance`) VALUES ('lisi', '123456', 5000);

INSERT INTO `t_product` (`name`, `description`, `price`, `stock`) VALUES ('iPhone 15', 'Apple iPhone 15', 6999.00, 100);
INSERT INTO `t_product` (`name`, `description`, `price`, `stock`) VALUES ('MacBook Pro', 'Apple MacBook Pro M3', 14999.00, 50);

INSERT INTO `t_storage` (`product_id`, `total_stock`, `used_stock`, `residue_stock`) VALUES (1, 100, 0, 100);
INSERT INTO `t_storage` (`product_id`, `total_stock`, `used_stock`, `residue_stock`) VALUES (2, 50, 0, 50);
