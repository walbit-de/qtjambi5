import org.qtjambi.qt.gui.*;


public class doc_src_styles {
    public static void main(String args[]) {
        QApplication.initialize(args);
/*
 * This file contains mostly unused snippets.
 * no need to port.
 * ...
//! [0]
    opt.init(q);
        if (down)
        opt.state |= QStyle.State_Sunken;
    if (tristate && noChange)
        opt.state |= QStyle.State_NoChange;
    else
        opt.state |= checked ? QStyle.State_On :
        QStyle.State_Off;
    if (q.testAttribute(Qt.WA_Hover) &&  q.underMouse()) {
        if (hovering)
        opt.state |= QStyle.State_MouseOver;
        else
        opt.state &= ~QStyle.State_MouseOver;
    }
    opt.text = text;
    opt.icon = icon;
    opt.iconSize = q.iconSize();
//! [0]


//! [1]
    state = QStyle.State_None;
    if (widget.isEnabled())
        state |= QStyle.State_Enabled;
    if (widget.hasFocus())
        state |= QStyle.State_HasFocus;
    if (widget.window().testAttribute(Qt.WA_KeyboardFocusChange))
        state |= QStyle.State_KeyboardFocusChange;
    if (widget.underMouse())
        state |= QStyle.State_MouseOver;
    if (widget.window().isActiveWindow())
        state |= QStyle.State_Active;
#ifdef Q_WS_MAC
    extern booleansqt_mac_can_clickThrough(QWidget ); //qwidget_mac.cpp
    if (!(state & QStyle.State_Active) && !qt_mac_can_clickThrough(widget))
        state &= ~QStyle.State_Enabled;
#endif
#ifdef QT_KEYPAD_NAVIGATION
    if (widget.hasEditFocus())
        state |= QStyle.State_HasEditFocus;
#endif

    direction = widget.layoutDirection();
    rect = widget.rect();
    palette = widget.palette();
    fontMetrics = widget.fontMetrics();
//! [1]


//! [2]
    QStylePainter p(this);
    QStyleOptionButton opt = d.getStyleOption();
    p.drawControl(QStyle.CE_CheckBox, opt);
//! [2]


//! [3]
            QStyleOptionButton subopt = tn;
            subopt.rect = subElementRect(SE_CheckBoxIndicator, btn, widget);
            drawPrimitive(PE_IndicatorCheckBox, ubopt, p, widget);
            subopt.rect = subElementRect(SE_CheckBoxContents, btn, widget);
            drawControl(CE_CheckBoxLabel, ubopt, p, widget);

            if (btn.state & State_HasFocus) {
                QStyleOptionFocusRect fropt;
                fropt.QStyleOption.operator=(tn);
                fropt.rect = subElementRect(SE_CheckBoxFocusRect, btn, widget);
                drawPrimitive(PE_FrameFocusRect, ropt, p, widget);
            }
//! [3]


//! [4]
        QStyleOptionButton tn = qstyleoption_cast<QStyleOptionButton *>(opt)
            uint alignment = visualAlignment(btn.direction, Qt.AlignLeft | Qt.AlignVCenter);

            if (!styleHint(SH_UnderlineShortcut, btn, widget))
                alignment |= Qt.TextHideMnemonic;
            QPixmap pix;
            QRect textRect = btn.rect;
            if (!btn.icon.isNull()) {
                pix = btn.icon.pixmap(btn.iconSize, btn.state & State_Enabled ? QIcon.Normal : QIcon.Disabled);
                drawItemPixmap(p, btn.rect, alignment, pix);
                if (btn.direction == Qt.RightToLeft)
                    textRect.setRight(textRect.right() - btn.iconSize.width() - 4);
                else
                    textRect.setLeft(textRect.left() + btn.iconSize.width() + 4);
            }
            if (!btn.text.isEmpty()){
                drawItemText(p, textRect, alignment | Qt.TextShowMnemonic,
                    btn.palette, btn.state & State_Enabled, btn.text, QPalette.WindowText);
            }
//! [4]
*/
    }
}
