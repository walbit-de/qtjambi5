package org.qtjambi.autotests;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Before;

import org.qtjambi.qt.core.QByteArray;
import org.qtjambi.qt.core.QPoint;
import org.qtjambi.qt.core.QPointF;
import org.qtjambi.qt.core.QRect;
import org.qtjambi.qt.core.QRegExp;
import org.qtjambi.qt.core.QUrl;
import org.qtjambi.qt.core.Qt;
import org.qtjambi.qt.widgets.QApplication;
import org.qtjambi.qt.gui.QBitmap;
import org.qtjambi.qt.gui.QBrush;
import org.qtjambi.qt.gui.QColor;
import org.qtjambi.qt.gui.QImage;
import org.qtjambi.qt.gui.QKeySequence;
import org.qtjambi.qt.gui.QLinearGradient;
import org.qtjambi.qt.gui.QPainterPath;
import org.qtjambi.qt.gui.QPen;
import org.qtjambi.qt.gui.QPicture;
import org.qtjambi.qt.gui.QPixmap;
import org.qtjambi.qt.gui.QPolygon;
import org.qtjambi.qt.gui.QPolygonF;
import org.qtjambi.qt.gui.QRegion;

public class TestSwap {
	
	public final static byte[] RED5x5 = {
		(byte)0xffffff89,(byte)0x50,(byte)0x4e,(byte)0x47,(byte)0xd,(byte)0xa,(byte)0x1a,(byte)0xa,(byte)0x0,(byte)0x0,
		(byte)0x0,(byte)0xd,(byte)0x49,(byte)0x48,(byte)0x44,(byte)0x52,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x5,
		(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x5,(byte)0x8,(byte)0x2,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x2,
		(byte)0xd,(byte)0xffffffb1,(byte)0xffffffb2,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x1,(byte)0x73,(byte)0x52,(byte)0x47,
		(byte)0x42,(byte)0x0,(byte)0xffffffae,(byte)0xffffffce,(byte)0x1c,(byte)0xffffffe9,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x9,
		(byte)0x70,(byte)0x48,(byte)0x59,(byte)0x73,(byte)0x0,(byte)0x0,(byte)0xb,(byte)0x13,(byte)0x0,(byte)0x0,
		(byte)0xb,(byte)0x13,(byte)0x1,(byte)0x0,(byte)0xffffff9a,(byte)0xffffff9c,(byte)0x18,(byte)0x0,(byte)0x0,(byte)0x0,
		(byte)0x7,(byte)0x74,(byte)0x49,(byte)0x4d,(byte)0x45,(byte)0x7,(byte)0xffffffdd,(byte)0x1,(byte)0x8,(byte)0xd,
		(byte)0x1f,(byte)0x2a,(byte)0xffffff84,(byte)0xffffff99,(byte)0xfffffff0,(byte)0x15,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x1d,
		(byte)0x69,(byte)0x54,(byte)0x58,(byte)0x74,(byte)0x43,(byte)0x6f,(byte)0x6d,(byte)0x6d,(byte)0x65,(byte)0x6e,
		(byte)0x74,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x43,(byte)0x72,(byte)0x65,(byte)0x61,
		(byte)0x74,(byte)0x65,(byte)0x64,(byte)0x20,(byte)0x77,(byte)0x69,(byte)0x74,(byte)0x68,(byte)0x20,(byte)0x47,
		(byte)0x49,(byte)0x4d,(byte)0x50,(byte)0x64,(byte)0x2e,(byte)0x65,(byte)0x7,(byte)0x0,(byte)0x0,(byte)0x0,
		(byte)0x11,(byte)0x49,(byte)0x44,(byte)0x41,(byte)0x54,(byte)0x8,(byte)0xffffffd7,(byte)0x63,(byte)0xfffffffc,(byte)0xffffffcf,
		(byte)0xffffff80,(byte)0x2,(byte)0xffffff98,(byte)0x18,(byte)0x28,(byte)0xffffffe3,(byte)0x3,(byte)0x0,(byte)0x50,(byte)0xffffff91,
		(byte)0x1,(byte)0x9,(byte)0xffffffe0,(byte)0x5f,(byte)0xffffffd2,(byte)0x23,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x0,
		(byte)0x49,(byte)0x45,(byte)0x4e,(byte)0x44,(byte)0xffffffae,(byte)0x42,(byte)0x60,(byte)0xffffff82
	};
	
	public final static byte[] WHITE10x10 = {
		(byte)0xffffff89,(byte)0x50,(byte)0x4e,(byte)0x47,(byte)0xd,(byte)0xa,(byte)0x1a,(byte)0xa,(byte)0x0,(byte)0x0,
		(byte)0x0,(byte)0xd,(byte)0x49,(byte)0x48,(byte)0x44,(byte)0x52,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0xa,
		(byte)0x0,(byte)0x0,(byte)0x0,(byte)0xa,(byte)0x8,(byte)0x2,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x2,
		(byte)0x50,(byte)0x58,(byte)0xffffffea,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x1,(byte)0x73,(byte)0x52,(byte)0x47,
		(byte)0x42,(byte)0x0,(byte)0xffffffae,(byte)0xffffffce,(byte)0x1c,(byte)0xffffffe9,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x6,
		(byte)0x62,(byte)0x4b,(byte)0x47,(byte)0x44,(byte)0x0,(byte)0xffffffff,(byte)0x0,(byte)0xffffffff,(byte)0x0,(byte)0xffffffff,
		(byte)0xffffffa0,(byte)0xffffffbd,(byte)0xffffffa7,(byte)0xffffff93,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x9,(byte)0x70,(byte)0x48,
		(byte)0x59,(byte)0x73,(byte)0x0,(byte)0x0,(byte)0xb,(byte)0x13,(byte)0x0,(byte)0x0,(byte)0xb,(byte)0x13,
		(byte)0x1,(byte)0x0,(byte)0xffffff9a,(byte)0xffffff9c,(byte)0x18,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x7,(byte)0x74,
		(byte)0x49,(byte)0x4d,(byte)0x45,(byte)0x7,(byte)0xffffffdd,(byte)0x1,(byte)0x8,(byte)0xd,(byte)0x29,(byte)0x7,
		(byte)0x48,(byte)0x5a,(byte)0x3d,(byte)0x15,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x1d,(byte)0x69,(byte)0x54,
		(byte)0x58,(byte)0x74,(byte)0x43,(byte)0x6f,(byte)0x6d,(byte)0x6d,(byte)0x65,(byte)0x6e,(byte)0x74,(byte)0x0,
		(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x43,(byte)0x72,(byte)0x65,(byte)0x61,(byte)0x74,(byte)0x65,
		(byte)0x64,(byte)0x20,(byte)0x77,(byte)0x69,(byte)0x74,(byte)0x68,(byte)0x20,(byte)0x47,(byte)0x49,(byte)0x4d,
		(byte)0x50,(byte)0x64,(byte)0x2e,(byte)0x65,(byte)0x7,(byte)0x0,(byte)0x0,(byte)0x0,(byte)0x15,(byte)0x49,
		(byte)0x44,(byte)0x41,(byte)0x54,(byte)0x18,(byte)0xffffffd3,(byte)0x63,(byte)0xfffffffc,(byte)0xffffffff,(byte)0xffffffff,(byte)0x3f,
		(byte)0x3,(byte)0x6e,(byte)0xffffffc0,(byte)0xffffffc4,(byte)0xffffff80,(byte)0x17,(byte)0xffffff8c,(byte)0x54,(byte)0x69,(byte)0x0,
		(byte)0xffffffa5,(byte)0xffffffe3,(byte)0x3,(byte)0x11,(byte)0xffffffff,(byte)0xffffffd0,(byte)0x39,(byte)0xffffffb9,(byte)0x0,(byte)0x0,
		(byte)0x0,(byte)0x0,(byte)0x49,(byte)0x45,(byte)0x4e,(byte)0x44,(byte)0xffffffae,(byte)0x42,(byte)0x60,(byte)0xffffff82		
	};

	@Before
	public void setUp() {
		 if(QApplication.startingUp()){
			 QApplication.initialize(new String[]{});
		 }
	}
	
	@org.junit.Test
	public void swapQUrl(){
		String data1 = "http://0.0.0.0";
		String data2 = "http://1.1.1.1";
		QUrl object1 = new QUrl(data1);
		QUrl object2 = new QUrl(data2);
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.toDisplayString());
		assertEquals("swap: data2 == object1", data2, object1.toDisplayString());
	}
	
	@org.junit.Test
	public void swapQByteArrays(){
		String data1 = "data1";
		String data2 = "data2";
		QByteArray object1 = new QByteArray(data1);
		QByteArray object2 = new QByteArray(data2);
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.toString());
		assertEquals("swap: data2 == object1", data2, object1.toString());
	}
	
	@org.junit.Test
	public void swapQRegExp(){
		String data1 = "[abc]";
		String data2 = "[def]";
		QRegExp object1 = new QRegExp(data1);
		QRegExp object2 = new QRegExp(data2);
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.pattern());
		assertEquals("swap: data2 == object1", data2, object1.pattern());
	}
	
	@org.junit.Test
	public void swapQPen(){
		QColor data1 = new QColor(Qt.GlobalColor.white);
		QColor data2 = new QColor(Qt.GlobalColor.black);
		QPen object1 = new QPen(data1, 2);
		QPen object2 = new QPen(data2, 4);
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.color());
		assertEquals("swap: data2 == object1", data2, object1.color());
		assertEquals("swap: object1.width() == 4", 4, object1.width());
		assertEquals("swap: object2.width() == 2", 2, object2.width());
	}
	
	@org.junit.Test
	public void swapQBrush(){
		QColor data1 = new QColor(Qt.GlobalColor.white);
		QLinearGradient data2 = new QLinearGradient(0,0,1,1);
		QBrush object1 = new QBrush(data1);
		QBrush object2 = new QBrush(data2);
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.color());
		assertEquals("swap: data2 == object1", data2, object1.gradient());
	}
	
	@org.junit.Test
	public void swapQPainterPath(){
		QPointF data1 = new QPointF(0, 0);
		QPointF data2 = new QPointF(1000, 1000);
		QPainterPath object1 = new QPainterPath(data1);
		QPainterPath object2 = new QPainterPath(data2);
		object2.swap(object1);
		assertEquals("swap: data1.length == 1", 1, object2.elementCount());
		assertEquals("swap: data2.length == 1", 1, object1.elementCount());
		assertEquals("swap: data1 == object2", data1, object2.elementAt(0).toPoint());
		assertEquals("swap: data2 == object1", data2, object1.elementAt(0).toPoint());
	}
	
	@org.junit.Test
	public void swapQKeySequence(){
		String data1 = "Ctrl+A";
		String data2 = "Alt+S";
		QKeySequence object1 = new QKeySequence(data1);
		QKeySequence object2 = new QKeySequence(data2);
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.toString());
		assertEquals("swap: data2 == object1", data2, object1.toString());
	}
	
	@org.junit.Test
	public void swapQPolygon(){
		QPoint data1 = new QPoint(0, 0);
		QPoint data2 = new QPoint(1000, 1000);
		QPolygon object1 = new QPolygon(Collections.singletonList(data1));
		QPolygon object2 = new QPolygon(Collections.singletonList(data2));
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.at(0));
		assertEquals("swap: data2 == object1", data2, object1.at(0));
	}
	
	@org.junit.Test
	public void swapQPolygonF(){
		QPointF data1 = new QPointF(0, 0);
		QPointF data2 = new QPointF(1000, 1000);
		QPolygonF object1 = new QPolygonF(Collections.singletonList(data1));
		QPolygonF object2 = new QPolygonF(Collections.singletonList(data2));
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.at(0));
		assertEquals("swap: data2 == object1", data2, object1.at(0));
	}
	
	@org.junit.Test
	public void swapQRegion(){
		QRegion object1 = new QRegion(80, 10, 10, 10);
		QRegion object2 = new QRegion(90, 10, 10, 10);
		QRect data1 = object1.boundingRect();
		QRect data2 = object2.boundingRect();
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.boundingRect());
		assertEquals("swap: data2 == object1", data2, object1.boundingRect());
	}
	
	@org.junit.Test
	public void swapQPixmap(){
		QPixmap object1 = new QPixmap();
		QPixmap object2 = new QPixmap();
		object1.loadFromData(RED5x5);
		object2.loadFromData(WHITE10x10);
		QRect data1 = object1.rect();
		QRect data2 = object2.rect();
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.rect());
		assertEquals("swap: data2 == object1", data2, object1.rect());
	}
	
	@org.junit.Test
	public void swapQBitmap(){
		QBitmap object1 = new QBitmap();
		QBitmap object2 = new QBitmap();
		object1.loadFromData(RED5x5);
		object2.loadFromData(WHITE10x10);
		QRect data1 = object1.rect();
		QRect data2 = object2.rect();
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.rect());
		assertEquals("swap: data2 == object1", data2, object1.rect());
	}
	
	@org.junit.Test
	public void swapQImage(){
		QImage object1 = new QImage();
		QImage object2 = new QImage();
		object1.loadFromData(RED5x5);
		object2.loadFromData(WHITE10x10);
		QRect data1 = object1.rect();
		QRect data2 = object2.rect();
		object2.swap(object1);
		assertEquals("swap: data1 == object2", data1, object2.rect());
		assertEquals("swap: data2 == object1", data2, object1.rect());
	}
	
	@org.junit.Test
	public void swapQPicture(){
		QPicture object1 = new QPicture();
		QPicture object2 = new QPicture();
		object1.setData(RED5x5);
		object2.setData(WHITE10x10);
		object2.swap(object1);
		assertEquals("swap: data1 == object2", new QByteArray(RED5x5), new QByteArray(object2.data()));
		assertEquals("swap: data2 == object1", new QByteArray(WHITE10x10), new QByteArray(object1.data()));
	}
}
