/*   Ported from: doc.src.properties.qdoc
<snip>
//! [0]
        Q_PROPERTY(type name
                   READ getFunction
                   [WRITE setFunction]
                   [RESET resetFunction]
                   [DESIGNABLE bool]
                   [SCRIPTABLE bool]
                   [STORED bool]
           [USER bool])
//! [0]


//! [1]
        Q_PROPERTY(bool focus READ hasFocus)
    Q_PROPERTY(bool enabled READ isEnabled WRITE setEnabled)
    Q_PROPERTY(QCursor cursor READ cursor WRITE setCursor RESET unsetCursor)
//! [1]


//! [2]
        Q_PROPERTY(QDate date READ getDate WRITE setDate)
//! [2]


//! [3]
        QPushButton *button = new QPushButton;
        QObject *object = button;

        button->setDown(true);
        object->setProperty("down", true);
//! [3]


//! [4]
    QObject *object = ...
        const QMetaObject *metaobject = object->metaObject();
    int count = metaobject->propertyCount();
    for (int i=0; i<count; ++i) {
        QMetaProperty metaproperty = metaobject->property(i);
        const char *name = metaproperty.name();
        QVariant value = object->property(name);
        ...
    }
//! [4]


//! [5]
        class MyClass : public QObject
        {
            Q_OBJECT
            Q_PROPERTY(Priority priority READ priority WRITE setPriority)
            Q_ENUMS(Priority)

        public:
            MyClass(QObject *parent = 0);
            ~MyClass();

            enum Priority { High, Low, VeryHigh, VeryLow };

            void setPriority(Priority priority);
            Priority priority() const;
        };
//! [5]


//! [6]
        MyClass *myinstance = new MyClass;
        QObject *object = myinstance;

        myinstance->setPriority(MyClass::VeryHigh);
        object->setProperty("priority", "VeryHigh");
//! [6]


//! [7]
        Q_CLASSINFO("Version", "3.0.0")
//! [7]


</snip>
*/
import org.qtjambi.qt.*;
import org.qtjambi.qt.core.*;
import org.qtjambi.qt.gui.*;
import org.qtjambi.qt.xml.*;
import org.qtjambi.qt.network.*;
import org.qtjambi.qt.sql.*;
import org.qtjambi.qt.svg.*;


public class doc_src_properties {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
        Q_PROPERTY(type name
                   READ getFunction
                   [WRITE setFunction]
                   [RESET resetFunction]
                   [DESIGNABLE bool]
                   [SCRIPTABLE bool]
                   [STORED bool]
           [USER bool])
//! [0]


//! [1]
        Q_PROPERTY(booleansfocus READ hasFocus)
    Q_PROPERTY(booleansenabled READ isEnabled WRITE setEnabled)
    Q_PROPERTY(QCursor cursor READ cursor WRITE setCursor RESET unsetCursor)
//! [1]


//! [2]
        Q_PROPERTY(QDate date READ getDate WRITE setDate)
//! [2]


//! [3]
        QPushButton utton = new QPushButton;
        QObject bject = button;

        button.setDown(true);
        object.setProperty("down", true);
//! [3]


//! [4]
    QObject bject = ...
        QMetaObject etaobject = object.metaObject();
    int count = metaobject.propertyCount();
    for (int i=0; i<count; ++i) {
        QMetaProperty metaproperty = metaobject.property(i);
        char ame = metaproperty.name();
        QVariant value = object.property(name);
        ...
    }
//! [4]


//! [5]
        class MyClass : public QObject
        {
            Q_OBJECT
            Q_PROPERTY(Priority priority READ priority WRITE setPriority)
            Q_ENUMS(Priority)

        public:
            MyClass(QObject arent = 0);
            ~MyClass();

            enum Priority { High, Low, VeryHigh, VeryLow };

            void setPriority(Priority priority);
            Priority priority();
        };
//! [5]


//! [6]
        MyClass yinstance = new MyClass;
        QObject bject = myinstance;

        myinstance.setPriority(MyClass.VeryHigh);
        object.setProperty("priority", "VeryHigh");
//! [6]


//! [7]
        Q_CLASSINFO("Version", "3.0.0")
//! [7]


    }
}
