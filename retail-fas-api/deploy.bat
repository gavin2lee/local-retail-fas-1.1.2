@echo off

rem -- Lipsion
color 1f
:menu
echo   ________________________________________________________________
echo  ^|                                                                ^|
echo  ^|                     Maven  -  控制面板                         ^|
echo  ^|                                                                ^|
echo  ^|  0 - clean package -D...skip=true -Pwindows                    ^|
echo  ^|  1 - clean package -D...skip=true -Punix                       ^|
echo  ^|________________________________________________________________^|
:input
set /p input=-^> 请选择打包类型: 

if "%input%"== "0" goto windows
if "%input%"== "1" goto unix
goto end

:windows
echo  # 编译Windows环境包 #
mvn clean package -Dmaven.test.skip=true -Pwindows&&pause&&cls&& call compile.bat

:unix
echo  # 编译Unix环境包 #
mvn clean package -Dmaven.test.skip=true -Punix&&pause&&cls&& call compile.bat


rem for /d %%d in (*) do (
rem  if exist %%d\POM.xml set dao_dir=%%d
rem )
rem echo 当前目录是:%dao_dir%
rem &&pause 
:end
echo 结束
prompt
popd