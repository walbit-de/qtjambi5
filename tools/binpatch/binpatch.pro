######################################################################
# Automatically generated by qmake (2.01a) to 24. mai 10:52:00 2007
######################################################################

TEMPLATE = app
TARGET =
DEPENDPATH += .
INCLUDEPATH += .

# Input
SOURCES += main.cpp

QT =
CONFIG -= qt
CONFIG += console

win32-msvc* {
    QMAKE_CFLAGS_RELEASE -= -MD
    QMAKE_CFLAGS_DEBUG -= -MDd
    QMAKE_CXXFLAGS_RELEASE -= -MD
    QMAKE_CXXFLAGS_DEBUG -= -MDd

    QMAKE_CFLAGS_RELEASE += -ML
    QMAKE_CFLAGS_DEBUG += -MLd
    QMAKE_CXXFLAGS_RELEASE += -ML
    QMAKE_CXXFLAGS_DEBUG += -MLd

    DEFINES += _CRT_SECURE_NO_DEPRECATE

    win32-msvc2005:CONFIG-=embed_manifest_exe
} linux-g++* {
    QMAKE_LFLAGS = -Wl,--rpath,\\\$\$ORIGIN/lib
} mac* {
    CONFIG -= app_bundle
    CONFIG += ppc x86
}
