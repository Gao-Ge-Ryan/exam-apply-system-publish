FROM java:8

# 添加镜像元数据
LABEL name="style-register" \
      maintainer="gaogle"

# 设置时区
ENV TZ=Asia/Shanghai

# 配置容器内时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 设置服务器端口和地址
ENV SERVER_PORT=8080
ENV SERVER_ADDR=0.0.0.0:$SERVER_PORT

# 定义工作目录
WORKDIR /opt
# 复制并设置启动脚本为可执行
COPY start.sh /opt/bin/start.sh
RUN chmod +x /opt/bin/start.sh

# 暴露服务端口
EXPOSE $SERVER_PORT

# 设置启动脚本为默认入口点
ENTRYPOINT ["/opt/bin/start.sh"]
