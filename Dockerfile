FROM openjdk:8-jdk-alpine

MAINTAINER hoody "rnmdo@163.com"

VOLUME /hoody-site/logs
VOLUME /hoody-site/uploadFile

COPY ./target/vip-hoody-site-api-0.0.1.jar /hoody-site/vip-hoody-site-api.jar
COPY ./uploadFile /hoody-site/uploadFile
#作为外部配置文件 springboot 读取当前目录指的是 启动jar 时所在的目录
COPY ./src/main/resources/application-ecs.yml /application-ecs.yml

#更新Alpine的软件源为国内（清华大学）的站点，因为从默认官源拉取实在太慢了。。。
RUN echo "http://mirrors.aliyun.com/alpine/v3.9/main/" > /etc/apk/repositories
RUN apk update \
        && apk upgrade \
        && apk add --no-cache bash \
        bash-doc \
        bash-completion \
        && rm -rf /var/cache/apk/* \
        && /bin/bash \
        && apk add --no-cache busybox

ENV LANG C.UTF-8

#RUN ["chmod", "+x", "/hoody-site/vip-hoody-site-api.jar"]
ENTRYPOINT ["java","-jar","/hoody-site/vip-hoody-site-api.jar","--spring.profiles.active=ecs"]

#ENTRYPOINT ["ping","-4","dev-mysql"]