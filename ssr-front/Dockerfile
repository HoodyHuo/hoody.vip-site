#使用node:8.16-alpine 作为基础进行构建
FROM node:8.16-alpine 

#创建/app 目录作为部署目录,创建容器实例时,挂载此目录
RUN mkdir -p /app
#更改alpine的安装源指向阿里源
RUN echo "http://mirrors.aliyun.com/alpine/v3.9/main/" > /etc/apk/repositories
#安装 bash 和 busybox
RUN apk update \
        && apk upgrade \
        && apk add --no-cache bash \
        bash-doc \
        bash-completion \
        && /bin/bash \
        && apk add --no-cache busybox \
        && rm -rf /var/cache/apk/*
#移动工作目录到 /app
WORKDIR /app
#设置node环境变量为production
ENV NODE_ENV=production
#设置容器启动时执行的命令
ENTRYPOINT [ "npm","start" ]
