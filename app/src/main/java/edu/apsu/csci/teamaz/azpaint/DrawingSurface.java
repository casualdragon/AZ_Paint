package edu.apsu.csci.teamaz.azpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

/*
    Contained in this file is the definition for the view the user draws on.
 */

public class DrawingSurface extends View implements Serializable{
    private ArrayList<CanvasableObject> objects;
    private SerializablePaint paint;
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
            invalidate();
    }


    //Set up for canvas
    private void setup(AttributeSet attrs){
        objects = new ArrayList<>();
        objectType = CanvasableObject.ObjectType.FREE;
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
                            object.getX1(),
                            object.getY1(),
                            object.getX2(),
                            object.getY2(),
                            object.getPaint()
                    );
                    break;
                case RECTANGLE:
                    canvas.drawRect(
                            object.getX1(),
                            object.getY1(),
                            object.getX2(),
                            object.getY2(),
                            object.getPaint()
                    );
            }
        }
        //do some stuff
    }

    //Adds passed points as object of objectType.
    public void add(SerializablePoint start, SerializablePoint end){
        //adds object with current settings
        objects.add(new CanvasableObject(paint, start,end, objectType));
        invalidate();
    }

    //Removes the last object.
    public void removePrevious(){
        if(objects.size() > 1) {
            objects.remove(objects.size() - 1);
        }
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
