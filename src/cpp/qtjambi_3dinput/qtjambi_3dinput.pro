TARGET = org_qtjambi_qt3d_input

greaterThan(QT_MAJOR_VERSION, 4): VERSION = $$QT_VERSION

include(../qtjambi/qtjambi_include.pri)
include($$QTJAMBI_CPP/org_qtjambi_qt3d_input/org_qtjambi_qt3d_input.pri)

# We manually add these include paths, instead of using "QT += module" which is bad for us
#  as it created unnecessary hardwired linkage to libraries we may never reference any symbols from.
# To be able to remove this we need to make generator better/smarter about #include directives
#  and it might not include these files at all.
INCLUDEPATH += $$(QTDIR)/include/Qt3DCore

QT += core gui qml quick 3dcore 3drenderer 3dinput

DEPENDPATH += $$(QTDIR)/include/Qt3DInput/$$QT_VERSION
DEPENDPATH += $$(QTDIR)/include/Qt3DInput/$$QT_VERSION/Qt3DInput
DEPENDPATH += $$(QTDIR)/include/Qt3DInput/$$QT_VERSION/Qt3DInput/private
DEPENDPATH += $$PWD
INCLUDEPATH += $$(QTDIR)/include/Qt3DInput/$$QT_VERSION
INCLUDEPATH += $$(QTDIR)/include/Qt3DInput/$$QT_VERSION/Qt3DInput
INCLUDEPATH += $$(QTDIR)/include/Qt3DInput/$$QT_VERSION/Qt3DInput/private
INCLUDEPATH += $$PWD

