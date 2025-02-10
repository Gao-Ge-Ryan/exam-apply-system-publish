#!/bin/bash
set -e

# 定义日志目录
LOG_DIR="/opt/logs"

# 创建日志目录（如果不存在）
mkdir -p "$LOG_DIR"

# 启动服务
echo "Starting service with JAR file..."
exec java -jar /opt/app.jar --spring.config.location=/opt/conf/application.yml
