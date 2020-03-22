import org.qtjambi.qt.*;
import org.qtjambi.qt.core.*;
import org.qtjambi.qt.gui.*;
import org.qtjambi.qt.xml.*;
import org.qtjambi.qt.network.*;
import org.qtjambi.qt.sql.*;
import org.qtjambi.qt.svg.*;


public class src_sql_kernel_qsqldatabase {
    public static void main(String args[]) {
        QApplication.initialize(args);
    {
//! [0]
    // WRONG
    QSqlDatabase db = QSqlDatabase.database("sales");
    QSqlQuery query = new QSqlQuery("SELECT NAME, DOB FROM EMPLOYEES", db);
    QSqlDatabase.removeDatabase("sales"); // will output a warning

    // "db" is now a dangling invalid database connection,
    // "query" contains an invalid result set
//! [0]
    }
    {
//! [1]
    {
        QSqlDatabase db = QSqlDatabase.database("sales");
        QSqlQuery query = new QSqlQuery("SELECT NAME, DOB FROM EMPLOYEES", db);
    }
    // Both "db" and "query" are destroyed because they are out of scope
    QSqlDatabase.removeDatabase("sales"); // correct
//! [1]
    }
    {
/*
//! [2]
     QSqlDatabase.registerSqlDriver("MYDRIVER",
                                     new QSqlDriverCreator<MyDatabaseDriver>);
     QSqlDatabase db = QSqlDatabase.addDatabase("MYDRIVER");
//! [2]
*/
    }
    {
        QSqlDatabase db;
//! [3]
    // ...
    db = QSqlDatabase.addDatabase("QODBC");
    db.setDatabaseName("DRIVER={Microsoft Access Driver (*.mdb)};FIL={MS Access};DBQ=myaccessfile.mdb");
    if (db.open()) {
        // success!
    }
    // ...
//! [3]
    }

    {
        QSqlDatabase db = QSqlDatabase.addDatabase("QODBC");
//! [4]
    // ...
    // MySQL connection
    db.setConnectOptions("CLIENT_SSL=1;CLIENT_IGNORE_SPACE=1"); // use an SSL connection to the server
    if (!db.open()) {
        db.setConnectOptions(); // clears the connect option string
    // ...
    }
    // ...
    // PostgreSQL connection
    db.setConnectOptions("requiressl=1"); // enable PostgreSQL SSL connections
    if (!db.open()) {
        db.setConnectOptions(); // clear options
        // ...
    }
    // ...
    // ODBC connection
    db.setConnectOptions("SQL_ATTR_ACCESS_MODE=SQL_MODE_READ_ONLY;SQL_ATTR_TRACE=SQL_OPT_TRACE_ON"); // set ODBC options
    if (!db.open()) {
        db.setConnectOptions(); // don't try to set this option
        // ...
    }
//! [4]
    }
/*
//! [5]
        #include "qtdir/src/sql/drivers/psql/qsql_psql.cpp"
//! [5]
*/
/*
//! [6]
    PGconn on = PQconnectdb("host=server user=bart password=simpson dbname=springfield");
        QPSQLDriver rv =  new QPSQLDriver(con);
        QSqlDatabase db = QSqlDatabase.addDatabase(drv); // becomes the new default connection
        QSqlQuery query;
        query.exec("SELECT NAME, ID FROM STAFF");
        // ...
//! [6]
*/
/*
//! [7]
         unix:LIBS += -lpq
     win32:LIBS += libpqdll.lib
//! [7]
*/
    {
//! [8]
        QSqlDatabase db = new QSqlDatabase();
        System.out.println(db.isValid());    // Returns false

        db = QSqlDatabase.database("sales");
        System.out.println(db.isValid());    // Returns true if "sales" connection exists

        QSqlDatabase.removeDatabase("sales");
        System.out.println(db.isValid());    // Returns false
//! [8]
    }
    }
}
