echo off

set version=%1
if "%version%"==""   goto  missArg



echo =--------------开始打包jar 跳过测试-----------------
call mvnw package -DskipTests

echo =--------------开始制作docker 镜像  vip-hoody-site-api:%version%--------------

call docker build -t vip-hoody-site-api:%version% .
goto end

:missArg
echo 请在参数加上版本号 eg: build-docker-image.bat 1.3
goto end

:end