FROM mysql:8.0.21
# 复制初始化脚本到容器中
COPY ./db/init_db.sql /docker-entrypoint-initdb.d/
# 设置脚本权限
RUN chmod a+x /docker-entrypoint-initdb.d/init_db.sql
