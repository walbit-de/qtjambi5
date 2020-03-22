/*   Ported from: doc.src.qt4-intro.qdoc
<snip>
//! [0]
        QT -= gui
//! [0]


//! [1]
        QT += network opengl sql qt3support
//! [1]


//! [2]
        CONFIG += uic3
//! [2]


//! [3]
        #include <QClassName>
//! [3]


//! [4]
        #include <QString>
        #include <QApplication>
        #include <QSqlTableModel>
//! [4]


//! [5]
        #include <qclassname.h>
//! [5]


//! [6]
        #include <QtCore>
//! [6]


//! [7]
        using namespace Qt;
//! [7]


//! [8]
        QLabel *label1 = new QLabel("Hello", this);
        QLabel *label2 = new QLabel(this, "Hello");
//! [8]


//! [9]
        MyWidget::MyWidget(QWidget *parent, const char *name)
            : QWidget(parent, name)
        {
            ...
        }
//! [9]


//! [10]
        // DEPRECATED
        if (obj->inherits("QPushButton")) {
            QPushButton *pushButton = (QPushButton *)obj;
            ...
        }
//! [10]


//! [11]
        QPushButton *pushButton = qobject_cast<QPushButton *>(obj);
        if (pushButton) {
            ...
        }
//! [11]


//! [12]
        QLabel *label = new QLabel;
        QPointer<QLabel> safeLabel = label;
        safeLabel->setText("Hello world!");
        delete label;
        // safeLabel is now 0, whereas label is a dangling pointer
//! [12]


//! [13]
        QT += qt3support
//! [13]


//! [14]
        DEFINES += QT3_SUPPORT
//! [14]


//! [15]
        DEFINES += QT3_SUPPORT_WARNINGS
//! [15]


//! [16]
        DEFINES += QT3_SUPPORT
//! [16]


</snip>
*/
import org.qtjambi.qt.*;
import org.qtjambi.qt.core.*;
import org.qtjambi.qt.gui.*;
import org.qtjambi.qt.xml.*;
import org.qtjambi.qt.network.*;
import org.qtjambi.qt.sql.*;
import org.qtjambi.qt.svg.*;


public class doc_src_qt4-intro {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
        QT -= gui
//! [0]


//! [1]
        QT += network opengl sql qt3support
//! [1]


//! [2]
        CONFIG += uic3
//! [2]


//! [3]
        #include <QClassName>
//! [3]


//! [4]
        #include <QString>
        #include <QApplication>
        #include <QSqlTableModel>
//! [4]


//! [5]
        #include <qclassname.h>
//! [5]


//! [6]
        #include <QtCore>
//! [6]


//! [7]
        using namespace Qt;
//! [7]


//! [8]
        QLabel abel1 = new QLabel("Hello", this);
        QLabel abel2 = new QLabel(this, "Hello");
//! [8]


//! [9]
        MyWidget.MyWidget(QWidget arent, char ame)
            : QWidget(parent, name)
        {
            ...
        }
//! [9]


//! [10]
        // DEPRECATED
        if (obj.inherits("QPushButton")) {
            QPushButton ushButton = (QPushButton *)obj;
            ...
        }
//! [10]


//! [11]
        QPushButton ushButton = qobject_cast<QPushButton *>(obj);
        if (pushButton) {
            ...
        }
//! [11]


//! [12]
        QLabel abel = new QLabel;
        QPointer<QLabel> safeLabel = label;
        safeLabel.setText("Hello world!");
        delete label;
        // safeLabel is now 0, whereas label is a dangling pointer
//! [12]


//! [13]
        QT += qt3support
//! [13]


//! [14]
        DEFINES += QT3_SUPPORT
//! [14]


//! [15]
        DEFINES += QT3_SUPPORT_WARNINGS
//! [15]


//! [16]
        DEFINES += QT3_SUPPORT
//! [16]


    }
}
