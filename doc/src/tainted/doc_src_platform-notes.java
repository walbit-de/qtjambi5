/*   Ported from: doc.src.platform-notes.qdoc
<snip>
//! [0]
    undefined reference to `_vt.11QPushButton'
//! [0]


//! [1]
    ANSI C++ forbids declaration ... with no type
//! [1]


//! [2]
    c:\program.obj not found
//! [2]


//! [3]
    -universal -sdk /Developer/SDKs/MacOSX10.4u.sdk
//! [3]


//! [4]
    QMAKE_MAC_SDK=/Developer/SDKs/MacOSX10.4u.sdk
    CONFIG+=x86 ppc
//! [4]


//! [5]
    /usr/bin/ld: /System/Library/Frameworks/Carbon.framework/Carbon
    load command 20 unknown cmd field
    /usr/bin/ld: /usr/lib/libSystem.dylib
    load command 6 unknown cmd field
//! [5]


//! [6]
    ld: common symbols not allowed with MH_DYLIB output format with the -multi_module option
    /usr/local/mysql/lib/libmysqlclient.a(my_error.o) definition of common _errbuff (size 512)
    /usr/bin/libtool: internal link edit command failed
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


public class doc_src_platform-notes {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
    undefined reference to `_vt.11QPushButton'
//! [0]


//! [1]
    ANSI C++ forbids declaration ... with no type
//! [1]


//! [2]
    c:\program.obj not found
//! [2]


//! [3]
    -universal -sdk /Developer/SDKs/MacOSX10.4u.sdk
//! [3]


//! [4]
    QMAKE_MAC_SDK=/Developer/SDKs/MacOSX10.4u.sdk
    CONFIG+=x86 ppc
//! [4]


//! [5]
    /usr/bin/ld: /System/Library/Frameworks/Carbon.framework/Carbon
    load command 20 unknown cmd field
    /usr/bin/ld: /usr/lib/libSystem.dylib
    load command 6 unknown cmd field
//! [5]


//! [6]
    ld: common symbols not allowed with MH_DYLIB output format with the -multi_module option
    /usr/local/mysql/lib/libmysqlclient.a(my_error.o) definition of common _errbuff (size 512)
    /usr/bin/libtool: internal link edit command failed
//! [6]


    }
}
