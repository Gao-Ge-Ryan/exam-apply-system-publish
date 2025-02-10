# 第一行FROM java:8 as builder指定要使用的基础镜像，在这种情况下，它是官方的Java 8镜像。as builder部分用于为中间镜像命名，该中间镜像用于编译应用程序。
FROM java:8 AS builder
# 将工作目录设置为`exam`
WORKDIR /exam
# 将当前目录（Dockerfile所在的目录）中的所有文件和目录复制到容器内的`exam`目录。
COPY . .
# 运行Gradle构建任务，不包括测试。此命令编译Java代码并创建可执行的JAR文件。
#RUN ./gradlew build -x test

#接下来的FROM指令指定了最终镜像的基础镜像，也是Java 8。
FROM java:8
# 为镜像添加了一个维护者标签
LABEL MAINTAINER="gaoge"
# ENV TZ Asia/Shanghai和ENV TIME_ZONE=Asia/Shanghai设置了容器内的时区。
ENV TZ Asia/Shanghai
ENV TIME_ZONE=Asia/Shanghai
# 将容器内的时区设置为指定的时区
RUN ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo $TIME_ZONE > /etc/timezone
# ENV SERVER_PORT 6060和ENV SERVER_ADDR 0.0.0.0:$SERVER_PORT设置了服务器的端口和地址
ENV SERVER_PORT 6666
ENV SERVER_ADDR 0.0.0.0:$SERVER_PORT
# 设置环境变量
ENV AUTHENTICATION_SECRET gaogle
# 将工作目录更改为 /opt/exam_backend
WORKDIR /opt/exam_backend
# 将从先前构建的中间镜像中复制可执行JAR文件到当前目录
COPY --from=builder /exam/build/libs/*GA.jar ./
# 将一个启动脚本复制到当前目录
COPY start.sh ./
# 将启动脚本设置为可执行
RUN chmod +x /opt/exam_backend/start.sh
# 声明容器将公开的端口
EXPOSE $SERVER_PORT
# 添加镜像元数据，指定镜像名称
LABEL name="exam-backend"
# 将启动脚本作为入口点运行,ENTRYPOINT 指定镜像的默认入口命令，该入口命令会在启动容器时作为根命令执行
ENTRYPOINT ["sh","/opt/exam_backend/start.sh"]