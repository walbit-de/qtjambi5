/*   Ported from: doc.src.unix-signal-handlers.qdoc
<snip>
//! [0]
    class MyDaemon : public QObject
    {
    Q_OBJECT

      public:
        MyDaemon(QObject *parent = 0, const char *name = 0);
        ~MyDaemon();

    // Unix signal handlers.
    static void hupSignalHandler(int unused);
    static void termSignalHandler(int unused);

      public slots:
        // Qt signal handlers.
        void handleSigHup();
        void handleSigTerm();

      private:
    static int sighupFd[2];
        static int sigtermFd[2];

    QSocketNotifier *snHup;
        QSocketNotifier *snTerm;
    };
//! [0]


//! [1]
    MyDaemon::MyDaemon(QObject *parent, const char *name)
             : QObject(parent,name)
    {
        if (::socketpair(AF_UNIX, SOCK_STREAM, 0, sighupFd))
       qFatal("Couldn't create HUP socketpair");

        if (::socketpair(AF_UNIX, SOCK_STREAM, 0, sigtermFd))
       qFatal("Couldn't create TERM socketpair");
        snHup = new QSocketNotifier(sighupFd[1], QSocketNotifier::Read, this);
        connect(snHup, SIGNAL(activated(int)), this, SLOT(handleSigHup()));
        snTerm = new QSocketNotifier(sigtermFd[1], QSocketNotifier::Read, this);
        connect(snTerm, SIGNAL(activated(int)), this, SLOT(handleSigTerm()));

    ...
    }
//! [1]


//! [2]
    static int setup_unix_signal_handlers()
    {
    struct sigaction hup, term;

    hup.sa_handler = MyDaemon::hupSignalHandler;
        sigemptyset(&hup.sa_mask);
        hup.sa_flags = 0;
        hup.sa_flags |= SA_RESTART;

        if (sigaction(SIGHUP, &hup, 0) > 0)
       return 1;

        term.sa_handler = MyDaemon::termSignalHandler;
        sigemptyset(&term.sa_mask);
        term.sa_flags |= SA_RESTART;

        if (sigaction(SIGTERM, &term, 0) > 0)
       return 2;

    return 0;
    }
//! [2]


//! [3]
    void MyDaemon::hupSignalHandler(int)
    {
    char a = 1;
    ::write(sighupFd[0], &a, sizeof(a));
    }

    void MyDaemon::termSignalHandler(int)
    {
    char a = 1;
    ::write(sigtermFd[0], &a, sizeof(a));
    }
//! [3]


//! [4]
    void MyDaemon::handleSigTerm()
    {
        snTerm->setEnabled(false);
        char tmp;
        ::read(sigtermFd[1], &tmp, sizeof(tmp));

    // do Qt stuff

    snTerm->setEnabled(true);
    }

    void MyDaemon::handleSigHup()
    {
        snHup->setEnabled(false);
        char tmp;
        ::read(sighupFd[1], &tmp, sizeof(tmp));

    // do Qt stuff

        snHup->setEnabled(true);
    }
//! [4]


</snip>
*/
import org.qtjambi.qt.*;
import org.qtjambi.qt.core.*;
import org.qtjambi.qt.gui.*;
import org.qtjambi.qt.xml.*;
import org.qtjambi.qt.network.*;
import org.qtjambi.qt.sql.*;
import org.qtjambi.qt.svg.*;


public class doc_src_unix-signal-handlers {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
    class MyDaemon : public QObject
    {
    Q_OBJECT

      public:
        MyDaemon(QObject arent = 0, char ame = 0);
        ~MyDaemon();

    // Unix signal handlers.
    static void hupSignalHandler(int unused);
    static void termSignalHandler(int unused);

      public slots:
        // Qt signal handlers.
        void handleSigHup();
        void handleSigTerm();

      private:
    static int sighupFd[2];
        static int sigtermFd[2];

    QSocketNotifier nHup;
        QSocketNotifier nTerm;
    };
//! [0]


//! [1]
    MyDaemon.MyDaemon(QObject arent, char ame)
             : QObject(parent,name)
    {
        if (.socketpair(AF_UNIX, SOCK_STREAM, 0, sighupFd))
       qFatal("Couldn't create HUP socketpair");

        if (.socketpair(AF_UNIX, SOCK_STREAM, 0, sigtermFd))
       qFatal("Couldn't create TERM socketpair");
        snHup = new QSocketNotifier(sighupFd[1], QSocketNotifier.Read, this);
        connect(snHup, SIGNAL(activated(int)), this, SLOT(handleSigHup()));
        snTerm = new QSocketNotifier(sigtermFd[1], QSocketNotifier.Read, this);
        connect(snTerm, SIGNAL(activated(int)), this, SLOT(handleSigTerm()));

    ...
    }
//! [1]


//! [2]
    static int setup_unix_signal_handlers()
    {
    struct sigaction hup, term;

    hup.sa_handler = MyDaemon.hupSignalHandler;
        sigemptyset(up.sa_mask);
        hup.sa_flags = 0;
        hup.sa_flags |= SA_RESTART;

        if (sigaction(SIGHUP, up, 0) > 0)
       return 1;

        term.sa_handler = MyDaemon.termSignalHandler;
        sigemptyset(erm.sa_mask);
        term.sa_flags |= SA_RESTART;

        if (sigaction(SIGTERM, erm, 0) > 0)
       return 2;

    return 0;
    }
//! [2]


//! [3]
    void MyDaemon.hupSignalHandler(int)
    {
    char a = 1;
    .write(sighupFd[0], , sizeof(a));
    }

    void MyDaemon.termSignalHandler(int)
    {
    char a = 1;
    .write(sigtermFd[0], , sizeof(a));
    }
//! [3]


//! [4]
    void MyDaemon.handleSigTerm()
    {
        snTerm.setEnabled(false);
        char tmp;
        .read(sigtermFd[1], mp, sizeof(tmp));

    // do Qt stuff

    snTerm.setEnabled(true);
    }

    void MyDaemon.handleSigHup()
    {
        snHup.setEnabled(false);
        char tmp;
        .read(sighupFd[1], mp, sizeof(tmp));

    // do Qt stuff

        snHup.setEnabled(true);
    }
//! [4]


    }
}
