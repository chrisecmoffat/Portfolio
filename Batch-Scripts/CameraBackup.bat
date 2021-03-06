REM Every 6 months, Task Scheduler would run this script to zip up all pictures and delete the remaining .jpg's
REM This saved space and logged security pictures for retention

TITLE Camera Cleanup

@ECHO ON
@ECHO ----------------------------------------------------------------------------------->>C:\camtest\images\Camera_1.log
DATE /T>>C:\camtest\images\Camera_1.log
TIME /T>>C:\camtest\images\Camera_1.log
@ECHO -- START -->>C:\camtest\images\Camera_1.log
@ECHO OFF

REM If backup folder does not exist, create backup folder for Camera_1
IF NOT EXIST C:\camtest\images\Backup\Camera_1 MKDIR C:\camtest\images\Backup\Camera_1>>C:\camtest\images\Camera_1.log

REM Makes zip folder
MKDIR C:\Camera_1Zip>>C:\camtest\images\Camera_1.log

REM Sets winzip path, runs and zips contents of images folder, If pictures do not exist, jump to :NOIMAGES, zip log file
PATH="C:\Program Files\Winzip"
IF EXIST C:\camtest\images\*.jpg GOTO EXISTS
GOTO NOIMAGES

:EXISTS
WZZIP C:\Camera_1Zip\Camera_1.zip C:\camtest\images\*.jpg>>C:\camtest\images\Camera_1.log

REM Deletes all JPG images in images folder
DEL C:\camtest\images\*.jpg>>C:\camtest\images\Camera_1.log

REM Grabs date and time in acceptable windows format, sets variable, renames ZIP file to appropiate name including date and time
FOR /F "TOKENS=1-4 DELIMS=/ " %%i IN ('DATE /T') DO SET mmddyyyy=%%j-%%i-%%k%
FOR /F "TOKENS=1-2 DELIMS=: " %%l IN ('TIME /T') DO SET hhmm=%%l%%m
REN C:\Camera_1Zip\Camera_1.zip Camera_1-%mmddyyyy%-%hhmm%.zip>>C:\camtest\images\Camera_1.log

REM Copies zip to backup folder
COPY C:\Camera_1Zip\*.zip C:\camtest\images\Backup\Camera_1>>C:\camtest\images\Camera_1.log

:NOIMAGES
FOR /F "TOKENS=1-4 DELIMS=/ " %%i IN ('DATE /T') DO SET mmddyyyy=%%j-%%i-%%k%
FOR /F "TOKENS=1-2 DELIMS=: " %%l IN ('TIME /T') DO SET hhmm=%%l%%m
REM Removes all files and folder
RD /Q /S C:\Camera_1Zip>>C:\camtest\images\Camera_1.log

@ECHO -- FINISH -->>C:\camtest\images\Camera_1.log
@ECHO ----------------------------------------------------------------------------------->>C:\camtest\images\Camera_1.log

REM Adds the latest log to the already created archive
WZZIP -A C:\camtest\images\Backup\Camera_1\Camera_1-%mmddyyyy%-%hhmm%.zip C:\camtest\images\Camera_1.log
