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
  *  Contained in this file is the definition for the view the user draws on.
  */

public class DrawingSurface extends View implements Serializable{
    //List of objects that are drawn, the current paint, the offset for panning and the current
    //type of object being drawn.
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

    //Default setup for this view.
    private void setup(AttributeSet attrs){
        objects = new ArrayList<>();
        objectType = CanvasableObject.ObjectType.LINE;
        offset = new SerializablePoint(0,0);

        paint = new SerializablePaint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10f);
        //do something
    }

    //SetSettings is a copy method for this object that copies the settings from another of the same
    //type. This is primarily needed for pulling the object from the savedInstanceState bundle.
    public void setSettings(DrawingSurface surface){
        this.paint = surface.paint;
        this.objectType = surface.objectType;
        this.objects = surface.objects;
        this.offset = surface.offset;
        invalidate();
    }

    //The override of the onDraw method. This override takes the objects list and draws it based
    //on the type of shape it is.
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Draws each object based on what type it is.
        for(CanvasableObject object : objects){
            switch (object.getType()){
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
    }

    //Add creates and adds a new object based on the current objectType and the points passed to the
    //method.
    public void add(SerializablePoint start, SerializablePoint end){
        //adds object with current settings
        Log.i("=================", "adding object");
        Log.i("======", "" + offset.x+ " | " + offset.y );

        objects.add(new CanvasableObject(
                paint,
                new SerializablePoint(start.x - offset.x , start.y -offset.y ),
                new SerializablePoint(end.x - offset.x , end.y -offset.y ),
                objectType));

        invalidate();
    }

    //RemovePrevious removes the last object from the objects method. This is primarily used for
    //refreshing the shape as the user moves their cursor.
    public void removePrevious(){
        if(!objects.isEmpty()) {
            Log.i("====================", "Removing last object");

            for(int i = 0; i < objects.size(); i++){
                Log.i("==================", "object "+ objects.get(i).toString());
            }

            objects.remove(objects.size() - 1);
            invalidate();
        }

    }

    //The addOffset method adds the values of point to the offset point. This is primarily used for
    //panning and drawing objects after panning.
    public void addOffset(SerializablePoint point){
        this.offset.x += point.x;
        this.offset.y += point.y;
        invalidate();
    }

    //The clearSurface method clears the objects from the view so the view is redrawn blank.
    public void clearSurface(){
        objects.clear();
    }

    //Getters and setters.
    public SerializablePaint getPaint(){
        return paint;
    }

    public CanvasableObject.ObjectType getObjectType(){
        return objectType;
    }

    public ArrayList<CanvasableObject> getObjects() {
        return objects;
    }

    public void setPaint(SerializablePaint paint){
        this.paint = paint;
    }

    public void setObjectType(CanvasableObject.ObjectType objectType){
        this.objectType = objectType;
    }

    public void setObjects(ArrayList<CanvasableObject> objects) {
        this.objects = objects;
    }







}
