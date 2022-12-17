# 基于java镜像创建新镜像
FROM java:8
# 作者
MAINTAINER gaoge

# Jar包部署
ADD  exam-apply-system-1.1.0-SNAPSHOT.jar exam-apply-system-1.1.0-SNAPSHOT.jar

# 暴露2020端口
EXPOSE 2020

# 运行jar包
ENTRYPOINT ["nohup","java","-jar","exam-apply-system-1.1.0-SNAPSHOT.jar","&"]
