package edu.apsu.csci.teamaz.azpaint;

import android.graphics.Paint;

import java.io.Serializable;

import java.io.Serializable;

 /*
  *  Contained in this file is the definition for the object that can be drawn on screen.
  */

public class CanvasableObject implements Serializable{
    //Specifies the type of object to be drawn when the use clicks or in the case of pan it specifies
    //that the screen needs to be panned instead of drawing a new object.
    public enum ObjectType implements Serializable {PAN, LINE, RECTANGLE};

    //Paint, start and end points, and an objectType enum for the current object.
    private SerializablePaint paint;
    private SerializablePoint startPoint;
    private SerializablePoint endPoint;
    private ObjectType type;

    //Constructor
    public CanvasableObject(Paint paint, SerializablePoint startPoint, SerializablePoint endPoint, ObjectType type) {
        this.paint = new SerializablePaint();
        this.paint.setStyle(paint.getStyle());
        this.paint.setStrokeWidth(paint.getStrokeWidth());
        this.paint.setColor(paint.getColor());

        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.type = type;
    }

    //Getters and Setters
    public Paint getPaint() {
        return paint;
    }

    public void setPaint(SerializablePaint paint) {
        this.paint = paint;
    }

    public SerializablePoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(SerializablePoint startPoint) {
        this.startPoint = startPoint;
    }

    public SerializablePoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(SerializablePoint endPoint) {
        this.endPoint = endPoint;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    //Getters for the individual points.
    public float getX1(){
        return startPoint.x;
    }
    public float getY1(){
        return startPoint.y;
    }
    public float getX2(){
        return endPoint.x;
    }
    public float getY2(){
        return endPoint.y;
    }

    //Overridden toString function for debugging.
    @Override
    public String toString() {
        return "x1: "+startPoint.x + " y1: " +startPoint.y + " x2: " +endPoint.x + " y2: "+ endPoint.y;
    }
}
