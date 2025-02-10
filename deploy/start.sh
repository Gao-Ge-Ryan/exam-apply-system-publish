#!/bin/bash
set -e

# 定义日志目录
LOG_DIR="/opt/logs"

# 创建日志目录（如果不存在）
mkdir -p "$LOG_DIR/jvmdump/"
mkdir -p "$LOG_DIR/gc/"

# 启动服务
echo "Starting service with JAR file..."
exec java -Xms1g -Xmx1g -Xmn300m -server -Xss256k -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/opt/logs/gc/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/opt/logs/jvmdump/ -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSClassUnloadingEnabled -XX:+TieredCompilation -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses -Duser.timezone=GMT+8 -jar /opt/jar/app.jar --spring.config.location=/opt/conf/application.yml
