call runcrud
if "%ERRORLEVEL%" == "0" goto startpage
echo.
echo runcrud.bat had errors - breaking work
goto fail

:startpage
start chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors

:end
echo.
echo WWW page should be opened in Chrome.