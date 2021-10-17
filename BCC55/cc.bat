@echo off
set old=%PATH%
set path=C:\devtrainer4\bcc55\bin;%path%
bcc32.exe -P -IC:\devtrainer4\bcc55\include -LC:\devtrainer4\bcc55\lib %1
set path=%old%
