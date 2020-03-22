/*   Ported from: doc.src.designer-manual.qdoc
<snip>
//! [0]
CONFIG += uitools
//! [0]


//! [1]
#include <QtUiTools>
//! [1]


//! [2]
    void on_<widget name>_<signal name>(<signal parameters>);
//! [2]


//! [3]
    CONFIG += release
//! [3]


//! [4]
    target.path = $$[QT_INSTALL_PLUGINS]/designer
    INSTALLS += target
//! [4]


//! [5]
    QT += script
//! [5]


//! [6]
            widget.text = 'Hi - I was built ' + new Date().toString();
//! [6]


//! [7]
    class MyExtension: public QObject,
                       public QdesignerContainerExtension
    {
        Q_OBJECT
        Q_INTERFACE(QDesignerContainerExtension)

        ...
    }
//! [7]


//! [8]
        QObject *ANewExtensionFactory::createExtension(QObject *object,
                const QString &iid, QObject *parent) const
        {
            if (iid != Q_TYPEID(QDesignerContainerExtension))
                return 0;

            if (MyCustomWidget *widget = qobject_cast<MyCustomWidget*>
                    (object))
                return new MyContainerExtension(widget, parent);

            return 0;
        }
//! [8]


//! [9]
        QObject *AGeneralExtensionFactory::createExtension(QObject *object,
                const QString &iid, QObject *parent) const
        {
            MyCustomWidget *widget = qobject_cast<MyCustomWidget*>(object);

            if (widget && (iid == Q_TYPEID(QDesignerTaskMenuExtension))) {
                 return new MyTaskMenuExtension(widget, parent);

            } else if (widget && (iid == Q_TYPEID(QDesignerContainerExtension))) {
                return new MyContainerExtension(widget, parent);

            } else {
                return 0;
            }
        }
//! [9]


//! [10]
    void MyPlugin::initialize(QDesignerFormEditorInterface *formEditor)
    {
        if (initialized)
            return;

        QExtensionManager *manager = formEditor->extensionManager();
        Q_ASSERT(manager != 0);

        manager->registerExtensions(new MyExtensionFactory(manager),
                                    Q_TYPEID(QDesignerTaskMenuExtension));

        initialized = true;
    }
//! [10]


</snip>
*/
import org.qtjambi.qt.*;
import org.qtjambi.qt.core.*;
import org.qtjambi.qt.gui.*;
import org.qtjambi.qt.xml.*;
import org.qtjambi.qt.network.*;
import org.qtjambi.qt.sql.*;
import org.qtjambi.qt.svg.*;


public class doc_src_designer-manual {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
CONFIG += uitools
//! [0]


//! [1]
#include <QtUiTools>
//! [1]


//! [2]
    void on_<widget name>_<signal name>(<signal parameters>);
//! [2]


//! [3]
    CONFIG += release
//! [3]


//! [4]
    target.path = $$[QT_INSTALL_PLUGINS]/designer
    INSTALLS += target
//! [4]


//! [5]
    QT += script
//! [5]


//! [6]
            widget.text = 'Hi - I was built ' + new Date().toString();
//! [6]


//! [7]
    class MyExtension: public QObject,
                       public QdesignerContainerExtension
    {
        Q_OBJECT
        Q_INTERFACE(QDesignerContainerExtension)

        ...
    }
//! [7]


//! [8]
        QObject NewExtensionFactory.createExtension(QObject bject,
                Stringsid, QObject arent)
        {
            if (iid != Q_TYPEID(QDesignerContainerExtension))
                return 0;

            if (MyCustomWidget idget = qobject_cast<MyCustomWidget*>
                    (object))
                return new MyContainerExtension(widget, parent);

            return 0;
        }
//! [8]


//! [9]
        QObject GeneralExtensionFactory.createExtension(QObject bject,
                Stringsid, QObject arent)
        {
            MyCustomWidget idget = qobject_cast<MyCustomWidget*>(object);

            if (widget && (iid == Q_TYPEID(QDesignerTaskMenuExtension))) {
                 return new MyTaskMenuExtension(widget, parent);

            } else if (widget && (iid == Q_TYPEID(QDesignerContainerExtension))) {
                return new MyContainerExtension(widget, parent);

            } else {
                return 0;
            }
        }
//! [9]


//! [10]
    void MyPlugin.initialize(QDesignerFormEditorInterface ormEditor)
    {
        if (initialized)
            return;

        QExtensionManager manager = formEditor.extensionManager();
        Q_ASSERT(manager != 0);

        manager.registerExtensions(new MyExtensionFactory(manager),
                                    Q_TYPEID(QDesignerTaskMenuExtension));

        initialized = true;
    }
//! [10]


    }
}
