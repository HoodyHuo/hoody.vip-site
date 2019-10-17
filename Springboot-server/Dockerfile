FROM adoptopenjdk/openjdk11-openj9:alpine

#VOLUME /hoody-site/logs
#VOLUME /hoody-site/uploadFile

COPY target/vip-hoody-site-api.jar /hoody-site/vip-hoody-site-api.jar
#COPY data/uploadFile /hoody-site/uploadFile
#作为外部配置文件 springboot 读取当前目录指的是 启动jar 时所在的目录
COPY src/main/resources/application-prod.yml /hoody-site/config/application-prod.yml

#更新Alpine的软件源为国内（清华大学）的站点，因为从默认官源拉取实在太慢了。。。
RUN echo "http://mirrors.aliyun.com/alpine/v3.9/main/" > /etc/apk/repositories
RUN apk update \
        && apk upgrade \
        && apk add --no-cache bash \
        bash-doc \
        bash-completion \
        && /bin/bash \
        && apk add --no-cache busybox \
        && rm -rf /var/cache/apk/*

ENV LANG C.UTF-8

WORKDIR /hoody-site
ENTRYPOINT ["java","-jar","/hoody-site/vip-hoody-site-api.jar","--spring.profiles.active=prod"]