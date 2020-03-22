/****************************************************************************
**
** Copyright (C) 2009-2015 Peter Droste, Omix Visualization GmbH & Co. KG. All rights reserved.
**
** This file is part of Qt Jambi.
**
** $BEGIN_LICENSE$
**
** GNU Lesser General Public License Usage
** This file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
**
** In addition, as a special exception, Nokia gives you certain
** additional rights. These rights are described in the Nokia Qt LGPL
** Exception version 1.0, included in the file LGPL_EXCEPTION.txt in this
** package.
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
**
** $END_LICENSE$
**
** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
**
****************************************************************************/


#ifndef _Included_org_qtjambi_qt_qml_QQmlList
#define _Included_org_qtjambi_qt_qml_QQmlList

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <QtCore/qglobal.h>
#ifdef Q_OS_MAC
#  include <JavaVM/jni.h>
#else
#  include <jni.h>
#endif
#include <QtQml>

/* Header for class org_qtjambi_qt_qml_QQmlListReference */

class Q_DECL_EXPORT QtjambiQmlListProperty : public QQmlListProperty<QObject>{
public:
    typedef void* (*CopyFunction)(void *);
    typedef void (*DeleteFunction)(void *);

    QtjambiQmlListProperty();
    QtjambiQmlListProperty(const QtjambiQmlListProperty&);
    QtjambiQmlListProperty(QObject *o, QList<QObject*> &list);
    ~QtjambiQmlListProperty();

    virtual QObject *object();

    virtual void append(QObject* object);

    virtual int count();

    virtual QObject* at(int index);

    virtual void clear();

    virtual bool canAppend();

    virtual bool canAt();

    virtual bool canClear();

    virtual bool canCount();

private:
    QtjambiQmlListProperty(QObject *o, void* d, CopyFunction g, DeleteFunction f, AppendFunction a, CountFunction c, AtFunction t, ClearFunction r );

    template<class T>
    static void appendListProperty(QQmlListProperty<QObject> * propertyList, QObject* newEntry){
        QQmlListProperty<T >* list = static_cast<QQmlListProperty<T >*>(propertyList->data);
        list->append(list, static_cast<T *>(newEntry));
    }

    template<class T>
    static int countListProperty(QQmlListProperty<QObject> * propertyList){
        QQmlListProperty<T >* list = static_cast<QQmlListProperty<T >*>(propertyList->data);
        return list->count(list);
    }

    template<class T>
    static QObject* atListProperty(QQmlListProperty<QObject> * propertyList, int index){
        QQmlListProperty<T >* list = static_cast<QQmlListProperty<T >*>(propertyList->data);
        return static_cast<QObject*>(list->at(list, index));
    }

    template<class T>
    static void clearListProperty(QQmlListProperty<QObject> * propertyList){
        QQmlListProperty<T >* list = static_cast<QQmlListProperty<T >*>(propertyList->data);
        list->clear(list);
    }

    template<class T>
    static void deleteListProperty(void* data){
        QQmlListProperty<T >* list = static_cast<QQmlListProperty<T >*>(data);
        delete list;
    }

    template<class T>
    static void* copyListProperty(void* data){
        if(data){
            QQmlListProperty<T >* list = static_cast<QQmlListProperty<T >*>(data);
            return new QQmlListProperty<T >(list->object,
                                            list->data,
                                            list->append,
                                            list->count,
                                            list->at,
                                            list->clear);
        }else{
            return Q_NULLPTR;
        }
    }

public:
    template<class T>
    static QtjambiQmlListProperty* createFrom(const QQmlListProperty<T>& d)
    {
        QtjambiQmlListProperty::AppendFunction appendFunction = Q_NULLPTR;
        QtjambiQmlListProperty::CountFunction countFunction = Q_NULLPTR;
        QtjambiQmlListProperty::AtFunction atFunction = Q_NULLPTR;
        QtjambiQmlListProperty::ClearFunction clearFunction = Q_NULLPTR;
        if(d.append){
            appendFunction = &QtjambiQmlListProperty::appendListProperty<T>;
        }
        if(d.count){
            countFunction = &QtjambiQmlListProperty::countListProperty<T>;
        }
        if(d.at){
            atFunction = &QtjambiQmlListProperty::atListProperty<T>;
        }
        if(d.clear){
            clearFunction = &QtjambiQmlListProperty::clearListProperty<T>;
        }

        return new QtjambiQmlListProperty(
                    d.object,
                    const_cast<void*>(static_cast<const void*>(&d)),
                    &copyListProperty<T>,
                    &deleteListProperty<T>,
                    appendFunction,
                    countFunction,
                    atFunction,
                    clearFunction
                );
    }
private:
    CopyFunction m_copyFunction;
    DeleteFunction m_deleteFunction;
};

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_qtjambi_qt_qml_QQmlListReference
 * Method:    listElementType
 * Signature: (J)Ljava/lang/Class;
 */
JNIEXPORT jclass JNICALL Java_org_qtjambi_qt_qml_QQmlListReference__1_1qt_1listElementType
  (JNIEnv *, jobject, jlong);

#ifdef __cplusplus
}
#endif
#endif
