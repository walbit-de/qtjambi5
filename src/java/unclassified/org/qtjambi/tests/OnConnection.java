/****************************************************************************
**
** Copyright (C) 1992-2009 Nokia. All rights reserved.
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

package org.qtjambi.tests;

import org.qtjambi.qt.gui.*;

public class OnConnection extends QWidget
{

    public OnConnection() {
    QVBoxLayout layout = new QVBoxLayout(this);

    QPushButton ok = new QPushButton("OK", this);
    ok.setObjectName("okButton");
    ok.setCheckable(true);
    layout.addWidget(ok);

    QPushButton cancel = new QPushButton("cancel", this);
    cancel.setObjectName("cancelButton");
    layout.addWidget(cancel);

    connectSlotsByName();
    }

    public void on_okButton_clicked() {
    System.out.println("ok button clicked");
    }

    public void on_cancelButton_clicked() {
    System.out.println("cancel button clicked");
    QApplication.quit();
    }

    public void on_okButton_toggled(boolean ok)
    {
    System.out.println("ok button toggled: " + ok);
    }

    public static void main(String args[])
    {
    QApplication.initialize(args);

    OnConnection oc = new OnConnection();
    oc.show();

    QApplication.execStatic();
    QApplication.shutdown();
    }
}
