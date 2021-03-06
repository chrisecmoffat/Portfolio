REM This is a cmd popup to change IP's on the fly
REM Changing static IP's in Windows panels is too slow when moving between data centers

@echo off
echo Choose:
echo [A] Set Bell IP
echo [B] Set Telus IP
echo [C] Set DHCP

:CHOICE
SET /P D=[A,B,C]?
for %%? in (A) do if /I "%D%"=="%%?" goto A
for %%? in (B) do if /I "%D%"=="%%?" goto B
for %%? in (C) do if /I "%D%"=="%%?" goto C
goto choice

:A
@echo off
echo "Setting Static IP Information"
netsh interface ipv4 set address "Local Area Connection 3" static X.X.X.X 255.255.255.0 X.X.X.X 1
netsh interface ipv4 add dns "Local Area Connection 3" X.X.X.X
goto end

:B
@echo off
echo "Setting Static IP Information"
netsh interface ipv4 set address "Local Area Connection 3" static X.X.X.X 255.255.255.0 X.X.X.X 1
netsh interface ipv4 add dns "Local Area Connection 3" X.X.X.X
goto end

:C
@echo off
echo Resetting IP Address and Subnet Mask For DHCP
netsh int ipv4 set address name = "Local Area Connection 3" source = dhcp
netsh int ipv4 set dnsservers name = "Local Area Connection 3" source = dhcp
goto end

:END
