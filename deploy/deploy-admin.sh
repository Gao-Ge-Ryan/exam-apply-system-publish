#!/bin/bash

# 设置变量
REPO_URL="git@gitee.com:Gao-Ge-Ryan/style-register-frontend-admin.git"
PROJECT_DIR="/opt/style-register-frontend"
DEPLOY_DIR="/opt/style-register-frontend-dist/admin"

# 检查是否安装了必要的软件
echo "检查必要软件..."
for cmd in git npm; do
  if ! command -v $cmd &> /dev/null; then
    echo "$cmd 未安装，请安装后重试。"
    exit 1
  fi
done

echo "所有必要的软件已安装。"

# 拉取代码
echo "拉取代码..."
if [ -d "$PROJECT_DIR/style-register-frontend-admin" ]; then
  cd "$PROJECT_DIR/style-register-frontend-admin"
  git fetch origin
  git reset --hard origin/master
else
  mkdir -p "$PROJECT_DIR"
  cd "$PROJECT_DIR"
  ehco "哈哈哈"
  git clone "$REPO_URL"
  cd "$PROJECT_DIR/style-register-frontend-admin"
fi
chmod -R +x "$PROJECT_DIR"

# 安装依赖
echo "安装项目依赖..."
if ! npm install; then
  echo "依赖安装失败，请检查错误日志。"
  exit 1
fi

echo "依赖安装成功。"

# 构建项目
echo "开始构建项目..."
if ! npm run build; then
  echo "构建失败，请检查错误日志。"
  exit 1
fi

echo "构建成功。"

# 部署
echo "开始部署..."
if [ ! -d "$DEPLOY_DIR" ]; then
  mkdir -p "$DEPLOY_DIR"
fi

# 清理旧的部署文件
rm -rf "$DEPLOY_DIR/*"

# 拷贝新的构建文件
cp -r "$PROJECT_DIR/style-register-frontend-admin/dist/*" "$DEPLOY_DIR/dist/"

# 检查部署结果
if [ $? -eq 0 ]; then
  echo "部署成功，静态文件已拷贝至 $DEPLOY_DIR。"
else
  echo "部署失败，请检查权限和路径。"
  exit 1
fi

# 提示完成
echo "Vue 项目部署完成，静态文件路径：$DEPLOY_DIR"

