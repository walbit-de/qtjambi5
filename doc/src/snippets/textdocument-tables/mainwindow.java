import org.qtjambi.qt.*;
import org.qtjambi.qt.core.*;
import org.qtjambi.qt.gui.*;

import java.util.*;

public class mainwindow extends QMainWindow
{
    public mainwindow()
    {
        QMenu fileMenu = new QMenu(tr("File"));

        QAction saveAction = fileMenu.addAction(tr("Save..."));
        saveAction.setShortcut(tr("Ctrl+S"));
        QAction quitAction = fileMenu.addAction(tr("Exit"));
        quitAction.setShortcut(tr("Ctrl+Q"));

        QMenu showMenu = new QMenu(tr("Show"));

        QAction showTableAction = showMenu.addAction(tr("Table"));

        menuBar().addMenu(fileMenu);
        menuBar().addMenu(showMenu);

        editor = new QTextEdit();

    //! [0] //! [1]
        QTextCursor cursor = new QTextCursor(editor.textCursor());
    //! [0]
        cursor.movePosition(QTextCursor.MoveOperation.Start);
    //! [1]

        int rows = 11;
        int columns = 4;

    //! [2]
        QTextTableFormat tableFormat = new QTextTableFormat();
        tableFormat.setBackground(new QColor("#e0e0e0"));
        List<QTextLength> raints = new Vector<QTextLength>();

       raints.add(new QTextLength(QTextLength.Type.PercentageLength, 16));
       raints.add(new QTextLength(QTextLength.Type.PercentageLength, 28));
       raints.add(new QTextLength(QTextLength.Type.PercentageLength, 28));
       raints.add(new QTextLength(QTextLength.Type.PercentageLength, 28));
       tableFormat.setColumnWidthConstraints(raints);
    //! [3]
        QTextTable table = cursor.insertTable(rows, columns, tableFormat);
    //! [2] //! [3]

        int column;
        int row;
        QTextTableCell cell = new QTextTableCell();
        QTextCursor cellCursor = new QTextCursor();

        QTextCharFormat charFormat = new QTextCharFormat();
        charFormat.setForeground(new QBrush(new QPen(new QColor(Qt.GlobalColor.black))));

    //! [4]
        cell = table.cellAt(0, 0);
        cellCursor = cell.firstCursorPosition();
        cellCursor.insertText(tr("Week"), charFormat);
    //! [4]

    //! [5]
        for (column = 1; column < columns; ++column) {
            cell = table.cellAt(0, column);
            cellCursor = cell.firstCursorPosition();
            cellCursor.insertText(tr("Team") + String.valueOf(column), charFormat);
        }

        for (row = 1; row < rows; ++row) {
            cell = table.cellAt(row, 0);
            cellCursor = cell.firstCursorPosition();
            cellCursor.insertText(String.valueOf(row), charFormat);

            for (column = 1; column < columns; ++column) {
                if ((row - 1) % 3 == column - 1) {
    //! [5] //! [6]
                    cell = table.cellAt(row, column);
                    QTextCursor cellCursor = cell.firstCursorPosition();
                    cellCursor.insertText(tr("On duty"), charFormat);
                }
    //! [6] //! [7]
            }
    //! [7] //! [8]
        }
    //! [8]

        saveAction.triggered.connect(this, "saveFile()");
        quitAction.triggered.connect(this, "close()");
        showTableAction.triggered.connect(this, "showTable()");

        setCentralWidget(editor);
        setWindowTitle(tr("Text Document Tables"));
    }

    void saveFile()
    {
        String fileName = QFileDialog.getSaveFileName(this,
            tr("Save document as:"), "", tr("XML (.xml)"));

        if (!fileName.isEmpty()) {
            if (writeXml(fileName))
                setWindowTitle(fileName);
            else
                QMessageBox.warning(this, tr("Warning"),
                    tr("Failed to save the document."), QMessageBox.Cancel,
                    QMessageBox.NoButton);
        }
    }

    void showTable()
    {
        QTextCursor cursor = editor.textCursor();
        QTextTable table = cursor.currentTable();

        if (!table)
            return;

        QTableWidget tableWidget =
            new QTableWidget(table.rows(), table.columns());

    //! [9]
        for (int row = 0; row < table.rows(); ++row) {
            for (int column = 0; column < table.columns(); ++column) {
                QTextTableCell tableCell = table.cellAt(row, column);
    //! [9]
                QTextFrame_iterator it;
                String text = "";
                for (it = tableCell.begin(); !(it.atEnd()); it.next()) {
                    QTextBlock childBlock = it.currentBlock();
                    if (childBlock.isValid())
                        text += childBlock.text();
                }

                QTableWidgetItem newItem = new QTableWidgetItem(text);
                tableWidget.setItem(row, column, newItem);
                /*
    //! [10]
                processTableCell(tableCell);
    //! [10]
               */
    //! [11]
            }
    //! [11] //! [12]
        }
    //! [12]

        tableWidget.setWindowTitle(tr("Table Contents"));
        tableWidget.show();
    }

    boolean writeXml(String fileName)
    {
        XmlWriter documentWriter = new XmlWriter(editor.document());

        QDomDocument domDocument = documentWriter.toXml();
        QFile file = new QFile(fileName);

        if (file.open(QIODevice.OpenMode.WriteOnly)) {
            QTextStream textStream = new QTextStream(file);
            textStream.setCodec(QTextCodec.codecForName("UTF-8"));

            textStream.writeString(domDocument.toString(1).toUtf8());
            file.close();

            return true;
        }
        else
            return false;
    }
}
