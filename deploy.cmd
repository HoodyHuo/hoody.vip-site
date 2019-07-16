echo off
:begin
echo 请选择需要执行的内容:
echo 1:build vue-Front
echo 2:build vue-Admin
echo 3:build Springboot-API
echo 4:build All of them
echo 5:build deploy to ECS
echo 6:quit
set all="false"
choice /c:123456 /m:"please select:"
if %errorlevel% == 6 goto end
if %errorlevel% == 5 goto deploy
if %errorlevel% == 4 goto buildAll
if %errorlevel% == 3 goto buildApi
if %errorlevel% == 2 goto buildAdmin
if %errorlevel% == 1 goto buildFront

:buildFront
if exist vue-front\dist (
echo ---------------------delete /vue-fornt/dist---------------------
call rmdir /q /s vue-front\dist
)
echo ---------------------start build vue-front .....---------------------
call npm run build:prod -prefix ./vue-front
echo --------------------- finished build vue-front ! ---------------------
if %all%=="true" (
goto buildAdmin
) else (
pause>nul
goto begin)

:buildAdmin
if exist vue-admin\dist (
echo ---------------------delete /vue-fornt/dist---------------------
call rmdir /q /s vue-admin\dist
)
echo ---------------------start build vue-admin .....---------------------
call npm run build:prod -prefix ./vue-admin
echo --------------------- finished build vue-admin ! ---------------------
if %all%=="true" (
goto buildApi
) else (
pause>nul
goto begin)

:buildApi
cd Springboot-server
echo --------------------- Clean  Springboot-server ..... ---------------------
call mvnw clean
echo --------------------- start build Springboot-server ..... ---------------------
call mvnw package -DskipTests
cd ..
echo --------------------- finished build Springboot-server ! ---------------------
pause>nul
goto begin


:buildAll
set all="true"
goto buildFront


:deploy
echo 444
pause>nul
goto begin


:end
echo I want to quit
