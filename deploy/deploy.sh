#!/bin/bash

# 设置变量
REPO_URL="git@gitee.com:Gao-Ge-Ryan/style-register.git"
PROJECT_DIR="/opt/style-register"
JAR_NAME="register-backend-1.0.0-GA.jar"
DEPLOY_DIR="/opt/style-register-jar"

# 检查是否安装了必要的软件
echo "检查必要软件..."
for cmd in git java gradle; do
  if ! command -v $cmd &> /dev/null; then
    echo "$cmd 未安装，请安装后重试。"
    exit 1
  fi
done

echo "所有必要的软件已安装。"

# 拉取代码
echo "拉取代码..."
if [ -d "$PROJECT_DIR" ]; then
  cd "$PROJECT_DIR"
  git fetch origin
  git reset --hard origin/master
  cd "$PROJECT_DIR/register-backend"
else
  git clone "$REPO_URL"
  cd "$PROJECT_DIR/register-backend"
fi
chmod -R +x "$PROJECT_DIR"

# 构建项目
echo "开始构建项目..."
if ! ./gradlew clean bootJar -x test --no-daemon; then
  echo "构建失败，请检查错误日志。"
  exit 1
fi

echo "构建成功。"

# 部署
echo "开始部署..."
if [ ! -d "$DEPLOY_DIR" ]; then
  mkdir -p "$DEPLOY_DIR"
fi

cp "build/libs/$JAR_NAME" "$DEPLOY_DIR/"

# 停止旧服务
if pgrep -f "$DEPLOY_DIR/$JAR_NAME" > /dev/null; then
  echo "停止旧服务..."
  pkill -f "$DEPLOY_DIR/$JAR_NAME"
fi

# 启动服务
echo "启动服务..."
nohup java -jar "$DEPLOY_DIR/$JAR_NAME" > "$DEPLOY_DIR/application.log" 2>&1 &

# 设置最大尝试次数
MAX_RETRIES=60
RETRIES=0

# 循环检测服务是否启动
while ! pgrep -f "$DEPLOY_DIR/$JAR_NAME" > /dev/null; do
  if [ $RETRIES -ge $MAX_RETRIES ]; then
    echo "服务启动失败，请检查日志。"
    exit 1
  fi
  RETRIES=$((RETRIES + 1))
  sleep 1  # 每隔1秒检测一次
done

echo "服务启动成功。日志保存在 $DEPLOY_DIR/application.log"

