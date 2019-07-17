:begin
echo off
echo 请选择需要执行的内容:
echo 1:build vue-Front
echo 2:build vue-Admin
echo 3:build Springboot-API
echo 4:build All and deploy to ECS
echo 5:quit 
set all="false"
choice /c:12345 /m:"please select:"
if %errorlevel% == 5 goto end
if %errorlevel% == 4 goto buildAndDeploy
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
goto begin)

:buildApi
cd Springboot-server
echo --------------------- Clean  Springboot-server ..... ---------------------
call mvnw clean
echo --------------------- start build Springboot-server ..... ---------------------
call mvnw package -DskipTests
cd ..
echo --------------------- finished build Springboot-server ! ---------------------
if %all%=="true" (
goto deploy
) else (
goto begin)


:buildAndDeploy
set all="true"
goto buildFront


:deploy
echo --------------------- Deploy to ECS ..... ---------------------
call WinSCP /script=ScpScript.txt
echo --------------------- Deploy to ECS  DONE..... ---------------------
goto begin


:end
echo quit
