FROM node:8.16-alpine
RUN mkdir -p /app
COPY . /app
EXPOSE 3000
ENV NODE_ENV=production
ENV HOST 0.0.0.0
RUN echo "http://mirrors.aliyun.com/alpine/v3.9/main/" > /etc/apk/repositories
RUN apk update \
        && apk upgrade \
        && apk add --no-cache bash \
        bash-doc \
        bash-completion \
        && /bin/bash \
        && apk add --no-cache busybox \
        && rm -rf /var/cache/apk/*
WORKDIR /app
RUN npm config set registry https://registry.npm.taobao.org
RUN npm install
RUN npm run build
ENTRYPOINT [ "npm","start" ]