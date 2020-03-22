/****************************************************************************
 **
 ** (C) 2007-2009 Nokia. All rights reserved.
 **
 ** This file is part of Qt Jambi.
 **
 ** ** $BEGIN_LICENSE$
** Commercial Usage
** Licensees holding valid Qt Commercial licenses may use this file in
** accordance with the Qt Commercial License Agreement provided with the
** Software or, alternatively, in accordance with the terms contained in
** a written agreement between you and Nokia.
** 
** GNU Lesser General Public License Usage
** Alternatively, this file may be used under the terms of the GNU Lesser
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
** If you are unsure which license is appropriate for your use, please
** contact the sales department at qt-sales@nokia.com.
** $END_LICENSE$

 **
 ** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 ** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 **
 ****************************************************************************/

package org.qtjambi.qt.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

class QJdbcSqlResult extends QSqlResult
{
    public QJdbcSqlResult(QSqlDriver db, Connection c)
    {
        super(db);

        // the result is wrapped in Queries, usually on the C++ side
        // disable the garbage collector and enable it again when the C++
        // object is gone
        this.disableGarbageCollection();
        this.connection = c;
    }

    public Object handle()
    {
        return statement;
    }

    protected Object data(int i)
    {
        try {
            return QJdbcSqlUtil.javaToQt(resultSet.getObject(i + 1));
        } catch (SQLException ex) {
            setError(ex, tr("Unable to retrieve data"), QSqlError.ErrorType.StatementError);
        }
        return null;
    }

    protected boolean isNull(int i)
    {
        try {
            resultSet.getObject(i + 1);
            return resultSet.wasNull();
        } catch (SQLException ex) {
            setError(ex, tr("Unable to retrieve null status"), QSqlError.ErrorType.StatementError);
        }
        return false;
    }

    protected boolean fetchPrevious()
    {
        if (resultSet == null)
            return false;

        try {
            if (!resultSet.previous())
                return false;
        } catch (SQLException ex) {
            setError(ex, tr("Unable to fetch previous"), QSqlError.ErrorType.StatementError);
            return false;
        }
        setAt(at() - 1);
        return true;
    }

    protected boolean fetchNext()
    {
        if (resultSet == null)
            return false;

        try {
            if (!resultSet.next()) {
                return false;
            }
        } catch (SQLException ex) {
            setError(ex, tr("Unable to fetch next"), QSqlError.ErrorType.StatementError);
            return false;
        }
        setAt(at() + 1);
        return true;
    }

    protected boolean fetch(int i)
    {
        if (resultSet == null)
            return false;

        try {
            if (resultSet.absolute(i + 1)) {
                setAt(i);
                return true;
            }
        } catch (SQLException ex) {
            setError(ex, tr("Unable to fetch row"), QSqlError.ErrorType.StatementError);
            return false;
        }

        return false;
    }

    protected boolean fetchFirst()
    {
        if (resultSet == null)
            return false;

        try {
            if (resultSet.first()) {
                setAt(0);
                return true;
            }
        } catch (SQLException ex) {
            setError(ex, tr("Unable to fetch first"), QSqlError.ErrorType.StatementError);
            return false;
        }

        return false;
    }

    protected boolean fetchLast()
    {
        if (resultSet == null)
            return false;

        try {
            if (resultSet.last()) {
                setAt(resultSet.getRow() - 1);
            }
        } catch (SQLException ex) {
            setError(ex, tr("Unable to fetch last"), QSqlError.ErrorType.StatementError);
            return false;
        }

        return false;
    }

    protected int size()
    {
        return -1;
    }

    protected int numRowsAffected()
    {
        return updateCount;
    }

    protected boolean reset(String query)
    {
        clearStatement();

        // create a new statement
        try {
            if (isForwardOnly()) {
                statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                    ResultSet.CONCUR_READ_ONLY);
            } else {
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
            }
        } catch (SQLException ex) {
            setError(ex, tr("Unable to create statement"), QSqlError.ErrorType.StatementError);
            statement = null;
            return false;
        }

        // execute the query
        boolean executionResult;
        try {
            if (connection.getMetaData().supportsGetGeneratedKeys())
                executionResult = statement.execute(query, Statement.RETURN_GENERATED_KEYS);
            else
                executionResult = statement.execute(query, Statement.NO_GENERATED_KEYS);
        } catch (SQLException ex) {
            setError(ex, tr("Unable to execute query"), QSqlError.ErrorType.ConnectionError);
            resultSet = null;
            return false;
        }

        return getResultSet(executionResult);
    }

    protected boolean prepare(String query)
    {
        clearStatement();

        // create a new prepared statement
        try {
            if (isForwardOnly()) {
                statement = connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY,
                                    ResultSet.CONCUR_UPDATABLE);
            } else {
                statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
            }
        } catch (SQLException ex) {
            setError(ex, tr("Unable to prepare statement"), QSqlError.ErrorType.StatementError);
            statement = null;
            return false;
        }

        return true;
    }

    protected boolean exec()
    {
        if ((statement == null) || !(statement instanceof PreparedStatement))
            return false;

        PreparedStatement ps = (PreparedStatement)statement;

        try {
            for (int i = 0; i<boundValueCount(); ++i) {
                ps.setObject(i + 1, QJdbcSqlUtil.qtToJava(boundValue(i)));
            }
        } catch (SQLException ex) {
            setError(ex, tr("Unable to bind parameters"), QSqlError.ErrorType.StatementError);
            return false;
        }

        // execute the query
        boolean executionResult;
        try {
            executionResult = ps.execute();
        } catch (SQLException ex) {
            setError(ex, tr("Unable to execute query"), QSqlError.ErrorType.ConnectionError);
            resultSet = null;
            return false;
        }

        return getResultSet(executionResult);
    }

    private final boolean getResultSet(boolean executionResult)
    {
        // true means "I have a result set", false means "I may have an update count"
        if (executionResult) {
            try {
                resultSet = statement.getResultSet();
                setSelect(true);
            } catch (SQLException ex) {
                setError(ex, tr("Unable to retrieve result set"), QSqlError.ErrorType.StatementError);
                // the query succeeded, said it has a result set, but then changed its mind - treat it as error
                return false;
            }
        } else {
            try {
                updateCount = statement.getUpdateCount();
            } catch (SQLException ex) {
                setError(ex, tr("Unable to retrieve number of rows affected"), QSqlError.ErrorType.StatementError);
                // the query succeeded, but we have no clue what happened - treat it as success, since it might be a NOOP
            }
        }

        setActive(true);
        setAt(QSql.Location.BeforeFirstRow.value());

        return true;
    }

    protected QSqlRecord record()
    {
        if (resultSet == null)
            return new QSqlRecord();
        if (cols != null)
            return cols;

        ResultSetMetaData meta;
        try {
            meta = resultSet.getMetaData();
        } catch (SQLException ex) {
            setError(ex, tr("Unable to retrieve meta data"), QSqlError.ErrorType.StatementError);
            return null;
        }

        if (meta == null)
            return null;

        cols = new QSqlRecord();

        try {
            for (int i = 1; i < meta.getColumnCount() + 1; ++i) {
                QSqlField f = new QSqlField(meta.getColumnName(i),
                               QJdbcSqlUtil.javaTypeToVariantType(meta.getColumnClassName(i)));
                f.setAutoValue(meta.isAutoIncrement(i));
                f.setLength(meta.getColumnDisplaySize(i)); // ### length/scale?
                f.setPrecision(meta.getPrecision(i));
                meta.isNullable(i);
                f.setRequiredStatus(QJdbcSqlUtil.toRequiredStatus(meta.isNullable(i)));
                f.setReadOnly(meta.isReadOnly(i));

                cols.append(f);
            }
        } catch (SQLException ex) {
            setError(ex, tr("Unable to retrieve metadata details"), QSqlError.ErrorType.StatementError);
            return null;
        }

        return cols;
    }

    protected Object lastInsertId()
    {
        if (statement == null)
            return null;

        try {
            ResultSet keys = connection.getMetaData().supportsGetGeneratedKeys()
                             ? statement.getGeneratedKeys()
                             : null;

            if (keys == null)
                return null;

            // empty results - we have auto-generated keys, but we don't!
            if (!keys.next())
                return null;

            return keys.getObject(1);

        } catch (SQLException ex) {
            setError(ex, tr("Unable to retrieve last insert ID"), QSqlError.ErrorType.StatementError);
        }

        return null;
    }

    protected void disposed()
    {
        this.reenableGarbageCollection();
        super.disposed();
    }

    private final void clearStatement()
    {
        this.clear();

        setLastError(new QSqlError());
        setActive(false);
        setSelect(false);
        setAt(QSql.Location.BeforeFirstRow.value());

        // close old result set
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                // error while closing the old statement -
                // not much we can do, just warn on console.
                System.err.println(ex);
            }
            resultSet = null;
        }

        // close old statement
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
            statement = null;
        }

        cols = null;
        updateCount = -1;
    }

    private void setError(SQLException e, String driverText, QSqlError.ErrorType t)
    {
        setLastError(QJdbcSqlUtil.getError(e, driverText, t));
    }

    private int updateCount = 0;
    private ResultSet resultSet = null;
    private Statement statement = null;
    private Connection connection = null;
    private QSqlRecord cols = null;
}

