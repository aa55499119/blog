## 项目结构

```
microservice-alibaba/
+--- common/              # 公共模块（DTO、异常、全局处理器）
+--- gateway/             # API 网关（Spring Cloud Gateway）
+--- user-service/        # 用户服务（端口 8081）
+--- product-service/     # 商品服务（端口 8082）
+--- order-service/       # 订单服务（端口 8083）
+--- storage-service/     # 仓储服务（端口 8084）
+--- conf/                # 基础设施配置
    +--- mysql/init.sql
    +--- rocketmq/broker.conf
    +--- seata/
+--- docker-compose.yml   # 一键启动基础设施
```

## 技术栈

| 组件 | 技术选型 |
|------|----------|
| 注册/配置中心 | Nacos 2.3 |
| API 网关 | Spring Cloud Gateway |
| 服务调用 | OpenFeign + LoadBalancer |
| 流控熔断 | Sentinel |
| 消息队列 | RocketMQ 5.2 |
| 缓存 | Redis 7.2 |
| 数据库 | MySQL 8.0 |
| ORM | MyBatis Plus 3.5 |
| 分布式事务 | Seata AT 模式 |
| API 文档 | Knife4j (Swagger) |

## 快速启动

### 1. 启动基础设施（Docker）

docker compose up -d

### 2. 数据库初始化

MySQL 启动后自动执行 conf/mysql/init.sql。

### 3. 编译并启动

mvn clean package -DskipTests
java -jar gateway/target/gateway-1.0.0.jar
java -jar user-service/target/user-service-1.0.0.jar
java -jar product-service/target/product-service-1.0.0.jar
java -jar order-service/target/order-service-1.0.0.jar
java -jar storage-service/target/storage-service-1.0.0.jar

## 核心流程

下单流程：POST /api/order/create?userId=1&productId=1&quantity=1
  order-service 调用 product-service 扣减库存，创建订单，全程 Seata 事务保障。

## 网关路由

| 路由 | 目标服务 |
|------|----------|
| /api/user/** | user-service |
| /api/product/** | product-service |
| /api/order/** | order-service |
| /api/storage/** | storage-service |

## 访问地址

- 网关: http://localhost:8888
- Nacos: http://localhost:8848/nacos
- RocketMQ Dashboard: http://localhost:8081
- Sentinel: http://localhost:9090

## 配置

Nacos: localhost:8848
MySQL: root/root123
Redis: redis123
RocketMQ: localhost:9876
Seata: localhost:8091
