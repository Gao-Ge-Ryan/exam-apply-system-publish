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

cp -f "build/libs/$JAR_NAME" "/opt/backend/jar/app.jar"


# 启动服务
echo "启动服务..."
if ! docker restart style-register; then
  echo "重启容器失败，请检查错误日志。"
  exit 1
fi

echo "容器已成功重启。"

