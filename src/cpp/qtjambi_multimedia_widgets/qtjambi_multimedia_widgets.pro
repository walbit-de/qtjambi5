TARGET = org_qtjambi_qt_multimedia_widgets

greaterThan(QT_MAJOR_VERSION, 4): VERSION = $$QT_VERSION

include(../qtjambi/qtjambi_include.pri)
include($$QTJAMBI_CPP/org_qtjambi_qt_multimedia_widgets/org_qtjambi_qt_multimedia_widgets.pri)

# libQtMultimedia.so.4.7.4 is only dependant on libQtCore.so.4 libQtGui.so.4
QT = core gui widgets multimedia multimediawidgets
