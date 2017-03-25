package edu.apsu.csci.teamaz.azpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/*
    Contained in this file is the definition for the view the user draws on.
 */

public class DrawingSurface extends View{
    ArrayList<CanvasableObject> objects;
    SerializablePaint paint;
    CanvasableObject.ObjectType objectType;

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

    //Set up for canvas
    private void setup(AttributeSet attrs){
        objects = new ArrayList<>();
        objectType = CanvasableObject.ObjectType.RECTANGLE;
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
    public void add(Point start, Point end){
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
    public void setPaintColor(int red,  int green, int blue){
        paint.setARGB(255, red, green, blue);
    }
    public void setLineWeight(int weight){

    }

}
