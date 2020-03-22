/*   Ported from: doc.src.model-view-programming.qdoc
<snip>
//! [0]
    QAbstractItemModel *model = index.model();
//! [0]


//! [1]
    QModelIndex index = model->index(row, column, ...);
//! [1]


//! [2]
    QModelIndex indexA = model->index(0, 0, QModelIndex());
    QModelIndex indexB = model->index(1, 1, QModelIndex());
    QModelIndex indexC = model->index(2, 1, QModelIndex());
//! [2]


//! [3]
    QModelIndex index = model->index(row, column, parent);
//! [3]


//! [4]
    QModelIndex indexA = model->index(0, 0, QModelIndex());
    QModelIndex indexC = model->index(2, 1, QModelIndex());
//! [4]


//! [5]
    QModelIndex indexB = model->index(1, 0, indexA);
//! [5]


//! [6]
    QVariant value = model->data(index, role);
//! [6]


</snip>
*/
import org.qtjambi.qt.*;
import org.qtjambi.qt.core.*;
import org.qtjambi.qt.gui.*;
import org.qtjambi.qt.xml.*;
import org.qtjambi.qt.network.*;
import org.qtjambi.qt.sql.*;
import org.qtjambi.qt.svg.*;


public class doc_src_model-view-programming {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
    QAbstractItemModel odel = index.model();
//! [0]


//! [1]
    QModelIndex index = model.index(row, column, ...);
//! [1]


//! [2]
    QModelIndex indexA = model.index(0, 0, QModelIndex());
    QModelIndex indexB = model.index(1, 1, QModelIndex());
    QModelIndex indexC = model.index(2, 1, QModelIndex());
//! [2]


//! [3]
    QModelIndex index = model.index(row, column, parent);
//! [3]


//! [4]
    QModelIndex indexA = model.index(0, 0, QModelIndex());
    QModelIndex indexC = model.index(2, 1, QModelIndex());
//! [4]


//! [5]
    QModelIndex indexB = model.index(1, 0, indexA);
//! [5]


//! [6]
    QVariant value = model.data(index, role);
//! [6]


    }
}
