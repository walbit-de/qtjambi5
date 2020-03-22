set ANT_HOME=C:\opt\apache-ant-1.10.7
set JAVA_HOME=C:\opt\JDEV\JDK8_202_x64
set JAVA_HOME_TARGET=C:\opt\JDEV\JDK8_202_x64

REM SET _ROOT=C:\Qt\Qt-5.12.8
SET _ROOT=C:\Users\xeox\Downloads\qt5
SET QTDIR=C:\Users\xeox\Downloads\qt5\qtbase

REM Set up \Microsoft Visual Studio 2013, where <arch> is \c amd64, \c x86, etc.
CALL "C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\vcvarsall.bat" x64

SET PATH=%ANT_HOME%\bin;%QTDIR%\bin;%_ROOT%\qtbase\bin;%_ROOT%\gnuwin32\bin;C:\ant\apache-ant-1.9.7\bin;%PATH%

REM Uncomment the below line when using a git checkout of the source repository
SET PATH=%_ROOT%\qtrepotools\bin;%PATH%

set CL=/MP
SET QMAKESPEC=win32-msvc
SET _ROOT=