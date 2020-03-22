/*   Ported from: src.network.access.qnetworkaccessmanager.cpp
<snip>
//! [0]
        QNetworkAccessManager *manager = new QNetworkAccessManager(this);
        connect(manager, SIGNAL(finished(QNetworkReply*)),
                this, SLOT(replyFinished(QNetworkReply*)));

        manager->get(QNetworkRequest("http://www.trolltech.com"));
//! [0]


//! [1]
        QNetworkRequest request;
        request.setUrl("http://www.trolltech.com");
        request.setRawHeader("User-Agent", "MyOwnBrowser 1.0");

        QNetworkReply *reply = manager->get(request);
        connect(reply, SIGNAL(readyRead()), this, SLOT(slotReadyRead()));
        connect(reply, SIGNAL(error(QNetworkReply::NetworkError)),
                this, SLOT(slotError(QNetworkReply::NetworkError)));
        connect(reply, SIGNAL(sslErrors(QList<QSslError>)),
                this, SLOT(slotSslErrors(QList<QSslError)));
//! [1]


</snip>
*/
import org.qtjambi.qt.*;
import org.qtjambi.qt.core.*;
import org.qtjambi.qt.gui.*;
import org.qtjambi.qt.xml.*;
import org.qtjambi.qt.network.*;
import org.qtjambi.qt.sql.*;
import org.qtjambi.qt.svg.*;


public class src_network_access_qnetworkaccessmanager {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
        QNetworkAccessManager manager = new QNetworkAccessManager(this);
        connect(manager, SIGNAL(finished(QNetworkReply*)),
                this, SLOT(replyFinished(QNetworkReply*)));

        manager.get(QNetworkRequest("http://www.trolltech.com"));
//! [0]


//! [1]
        QNetworkRequest request;
        request.setUrl("http://www.trolltech.com");
        request.setRawHeader("User-Agent", "MyOwnBrowser 1.0");

        QNetworkReply eply = manager.get(request);
        connect(reply, SIGNAL(readyRead()), this, SLOT(slotReadyRead()));
        connect(reply, SIGNAL(error(QNetworkReply.NetworkError)),
                this, SLOT(slotError(QNetworkReply.NetworkError)));
        connect(reply, SIGNAL(sslErrors(QList<QSslError>)),
                this, SLOT(slotSslErrors(QList<QSslError)));
//! [1]


    }
}
