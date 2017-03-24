package edu.apsu.csci.teamaz.azpaint;

import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by nonam on 3/23/2017.
 */

//A class for the different types of objects that can be drawn
public class CanvasableObject {
    public enum ObjectType {LINE, RECTANGLE};

    private Paint paint;
    private Point startPoint;
    private Point endPoint;

    private ObjectType type;

    //Constructor
    public CanvasableObject(Paint paint, Point startPoint, Point endPoint, ObjectType type) {
        this.paint = new Paint();
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

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

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


}
