/*   Ported from: doc.src.qthelp.qdoc
<snip>
//! [0]
        #include <QtHelp>
//! [0]


//! [1]
        CONFIG += help
//! [1]


//! [2]
    qhelpgenerator doc.qhp -o doc.qch
//! [2]


//! [3]
    <?xml version="1.0" encoding="utf-8" ?>
    <QHelpCollectionProject version="1.0">
        <docFiles>
            <register>
                <file>doc.qch</file>
            </register>
        </docFiles>
    </QHelpCollectionProject>
//! [3]


//! [4]
    qcollectiongenerator mycollection.qhcp -o mycollection.qhc
//! [4]


//! [5]
    ...
    <docFiles>
        <generate>
            <file>
                <input>doc.qhp</input>
                <output>doc.qch</output>
            </file>
        </generate>
        <register>
            <file>doc.qch</file>
        </register>
    </docFiles>
    ...
//! [5]


//! [6]
    QHelpEngineCore helpEngine("mycollection.qhc");
    ...

    // get all file references for the identifier
    QMap<QString, QUrl> links =
        helpEngine.linksForIdentifier(QLatin1String("MyDialog::ChangeButton"));

    // If help is available for this keyword, get the help data
    // of the first file reference.
    if (links.count()) {
        QByteArray helpData = helpEngine->fileData(links.constBegin().value());
        // show the documentation to the user
        if (!helpData.isEmpty())
            displayHelp(helpData);
    }
//! [6]


//! [7]
    <?xml version="1.0" encoding="UTF-8"?>
    <QtHelpProject version="1.0">
        <namespace>mycompany.com.myapplication.1_0</namespace>
        <virtualFolder>doc</virtualFolder>
        <customFilter name="My Application 1.0">
            <filterAttribute>myapp</filterAttribute>
            <filterAttribute>1.0</filterAttribute>
        </customFilter>
        <filterSection>
            <filterAttribute>myapp</filterAttribute>
            <filterAttribute>1.0</filterAttribute>
            <toc>
                <section title="My Application Manual" ref="index.html">
                    <section title="Chapter 1" ref="doc.html#chapter1"/>
                    <section title="Chapter 2" ref="doc.html#chapter2"/>
                    <section title="Chapter 3" ref="doc.html#chapter3"/>
                </section>
            </toc>
            <keywords>
                <keyword name="foo" id="MyApplication::foo" ref="doc.html#foo"/>
                <keyword name="bar" ref="doc.html#bar"/>
                <keyword id="MyApplication::foobar" ref="doc.html#foobar"/>
            </keywords>
            <files>
                <file>classic.css</file>
                <file>index.html</file>
                <file>doc.html</file>
            </files>
        </filterSection>
    </QtHelpProject>
//! [7]


//! [8]
    ...
    <virtualFolder>doc</virtualFolder>
    ...
//! [8]


//! [9]
    ...
    <customFilter name="My Application 1.0">
        <filterId>myapp</filterId>
        <filterId>1.0</filterId>
    </customFilter>
    ...
//! [9]


//! [10]
    ...
    <filterSection>
        <filterAttribute>myapp</filterAttribute>
        <filterAttribute>1.0</filterAttribute>
    ...
//! [10]


//! [11]
    ...
    <toc>
        <section title="My Application Manual" ref="index.html">
            <section title="Chapter 1" ref="doc.html#chapter1"/>
            <section title="Chapter 2" ref="doc.html#chapter2"/>
            <section title="Chapter 3" ref="doc.html#chapter3"/>
        </section>
    </toc>
    ...
//! [11]


//! [12]
    ...
    <keywords>
       <keyword name="foo" id="MyApplication::foo" ref="doc.html#foo"/>
       <keyword name="bar" ref="doc.html#bar"/>
       <keyword id="MyApplication::foobar" ref="doc.html#foobar"/>
    </keywords>
    ...
//! [12]


//! [13]
    ...
    <files>
        <file>classic.css</file>
        <file>index.html</file>
        <file>doc.html</file>
    </files>
    ...
//! [13]


</snip>
*/
import org.qtjambi.qt.*;
import org.qtjambi.qt.core.*;
import org.qtjambi.qt.gui.*;
import org.qtjambi.qt.xml.*;
import org.qtjambi.qt.network.*;
import org.qtjambi.qt.sql.*;
import org.qtjambi.qt.svg.*;


public class doc_src_qthelp {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
        #include <QtHelp>
//! [0]


//! [1]
        CONFIG += help
//! [1]


//! [2]
    qhelpgenerator doc.qhp -o doc.qch
//! [2]


//! [3]
    <?xml version="1.0" encoding="utf-8" ?>
    <QHelpCollectionProject version="1.0">
        <docFiles>
            <register>
                <file>doc.qch</file>
            </register>
        </docFiles>
    </QHelpCollectionProject>
//! [3]


//! [4]
    qcollectiongenerator mycollection.qhcp -o mycollection.qhc
//! [4]


//! [5]
    ...
    <docFiles>
        <generate>
            <file>
                <input>doc.qhp</input>
                <output>doc.qch</output>
            </file>
        </generate>
        <register>
            <file>doc.qch</file>
        </register>
    </docFiles>
    ...
//! [5]


//! [6]
    QHelpEngineCore helpEngine("mycollection.qhc");
    ...

    // get all file references for the identifier
    QMap<QString, QUrl> links =
        helpEngine.linksForIdentifier(QLatin1String("MyDialog.ChangeButton"));

    // If help is available for this keyword, get the help data
    // of the first file reference.
    if (links.count()) {
        QByteArray helpData = helpEngine.fileData(links.constBegin().value());
        // show the documentation to the user
        if (!helpData.isEmpty())
            displayHelp(helpData);
    }
//! [6]


//! [7]
    <?xml version="1.0" encoding="UTF-8"?>
    <QtHelpProject version="1.0">
        <namespace>mycompany.com.myapplication.1_0</namespace>
        <virtualFolder>doc</virtualFolder>
        <customFilter name="My Application 1.0">
            <filterAttribute>myapp</filterAttribute>
            <filterAttribute>1.0</filterAttribute>
        </customFilter>
        <filterSection>
            <filterAttribute>myapp</filterAttribute>
            <filterAttribute>1.0</filterAttribute>
            <toc>
                <section title="My Application Manual" ref="index.html">
                    <section title="Chapter 1" ref="doc.html#chapter1"/>
                    <section title="Chapter 2" ref="doc.html#chapter2"/>
                    <section title="Chapter 3" ref="doc.html#chapter3"/>
                </section>
            </toc>
            <keywords>
                <keyword name="foo" id="MyApplication.foo" ref="doc.html#foo"/>
                <keyword name="bar" ref="doc.html#bar"/>
                <keyword id="MyApplication.foobar" ref="doc.html#foobar"/>
            </keywords>
            <files>
                <file>classic.css</file>
                <file>index.html</file>
                <file>doc.html</file>
            </files>
        </filterSection>
    </QtHelpProject>
//! [7]


//! [8]
    ...
    <virtualFolder>doc</virtualFolder>
    ...
//! [8]


//! [9]
    ...
    <customFilter name="My Application 1.0">
        <filterId>myapp</filterId>
        <filterId>1.0</filterId>
    </customFilter>
    ...
//! [9]


//! [10]
    ...
    <filterSection>
        <filterAttribute>myapp</filterAttribute>
        <filterAttribute>1.0</filterAttribute>
    ...
//! [10]


//! [11]
    ...
    <toc>
        <section title="My Application Manual" ref="index.html">
            <section title="Chapter 1" ref="doc.html#chapter1"/>
            <section title="Chapter 2" ref="doc.html#chapter2"/>
            <section title="Chapter 3" ref="doc.html#chapter3"/>
        </section>
    </toc>
    ...
//! [11]


//! [12]
    ...
    <keywords>
       <keyword name="foo" id="MyApplication.foo" ref="doc.html#foo"/>
       <keyword name="bar" ref="doc.html#bar"/>
       <keyword id="MyApplication.foobar" ref="doc.html#foobar"/>
    </keywords>
    ...
//! [12]


//! [13]
    ...
    <files>
        <file>classic.css</file>
        <file>index.html</file>
        <file>doc.html</file>
    </files>
    ...
//! [13]


    }
}
