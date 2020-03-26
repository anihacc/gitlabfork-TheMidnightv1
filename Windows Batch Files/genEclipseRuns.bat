@echo off
title Generating run files for Eclipse...
cd ..
call gradlew.bat genEclipseRuns
pause
