package edu.apsu.csci.teamaz.azpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

/*
    Contained in this file is the definition for the view the user draws on.
 */

public class DrawingSurface extends View implements Serializable{
    private ArrayList<CanvasableObject> objects;
    private SerializablePaint paint;
    private SerializablePoint offset;
    private CanvasableObject.ObjectType objectType;

    //Constructors
    public DrawingSurface(Context context) {
        super(context);
        setup(null);
    }

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    public void setSettings(DrawingSurface surface){
        this.paint = surface.paint;
        this.objectType = surface.objectType;
        this.objects = surface.objects;
        this.offset = surface.offset;
        invalidate();
    }


    //Set up for canvas
    private void setup(AttributeSet attrs){
        objects = new ArrayList<>();
        objectType = CanvasableObject.ObjectType.FREE;
        this.offset = new SerializablePoint(0,0);
        paint = new SerializablePaint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10f);
        //do something
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Draws each opject based on what type it is.
        for(CanvasableObject object : objects){
            switch (object.getType()){
                case FREE:
                case LINE:
                    canvas.drawLine(
                            object.getX1() + offset.x,
                            object.getY1() + offset.y,
                            object.getX2() + offset.x,
                            object.getY2() + offset.y,
                            object.getPaint()
                    );
                    break;
                case RECTANGLE:
                    canvas.drawRect(
                            object.getX1() + offset.x,
                            object.getY1() + offset.y,
                            object.getX2() + offset.x,
                            object.getY2() + offset.y,
                            object.getPaint()
                    );
            }
        }
        //do some stuff
    }

    //Adds passed points as object of objectType.
    public void add(SerializablePoint start, SerializablePoint end){
        //adds object with current settings
        Log.i("======", "" + offset.x+ " | " + offset.y );

        objects.add(new CanvasableObject(
                paint,
                new SerializablePoint(start.x - offset.x , start.y -offset.y ),
                new SerializablePoint(end.x - offset.x , end.y -offset.y ),
                objectType));
        invalidate();
    }

    //Removes the last object.
    public void removePrevious(){
        if(objects.size() > 1) {
            objects.remove(objects.size() - 1);
        }

    }

    public void addOffset(SerializablePoint point){
        this.offset.x += point.x;
        this.offset.y += point.y;
        invalidate();
    }

    public void setPaint(SerializablePaint paint){
        this.paint = paint;
    }

    public SerializablePaint getPaint(){
        return paint;
    }

    public CanvasableObject.ObjectType getObjectType(){
        return objectType;
    }

    public void setObjectType(CanvasableObject.ObjectType objectType){
        this.objectType = objectType;
    }

    public void clearSurface(){
        objects.clear();
    }


    public ArrayList<CanvasableObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<CanvasableObject> objects) {
        this.objects = objects;
    }
}
