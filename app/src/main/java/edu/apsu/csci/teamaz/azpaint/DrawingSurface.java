package edu.apsu.csci.teamaz.azpaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
    private Paint paint;
    private Point offset;
    private CanvasableObject.ObjectType objectType;
    private int backgroundcolor;
    private int previousColor;
    private boolean erased;

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
        offset = new Point(0,0);
        erased = false;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10f);
        backgroundcolor = 0xffffffff;
    }

    //SetSettings is a copy method for this object that copies the settings from another of the same
    //type. This is primarily needed for pulling the object from the savedInstanceState bundle.
    public void setSettings(DrawingSurface surface){
        this.paint = surface.paint;
        this.objectType = surface.objectType;
        this.objects = surface.objects;
        this.offset = surface.offset;
        this.backgroundcolor = surface.backgroundcolor;
        this.erased = surface.erased;
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
    public void add(Point start, Point end){
        //adds object with current settings
//        Log.i("=================", "adding object");
//        Log.i("======", "" + offset.x+ " | " + offset.y );

        objects.add(new CanvasableObject(
                paint,
                new Point(start.x - offset.x , start.y -offset.y ),
                new Point(end.x - offset.x , end.y -offset.y ),
                objectType, erased));

        invalidate();
    }

    //RemovePrevious removes the last object from the objects method. This is primarily used for
    //refreshing the shape as the user moves their cursor. In addition, this method is also used
    //with the undo imageView onClick event.
    public void removePrevious(){
        if(!objects.isEmpty()) {
//            Log.i("====================", "Removing last object");

            for(int i = 0; i < objects.size(); i++){
//                Log.i("==================", "object "+ objects.get(i).toString());
            }

            objects.remove(objects.size() - 1);
            invalidate();
        }

    }

    //The addOffset method adds the values of point to the offset point. This is primarily used for
    //panning and drawing objects after panning.
    public void addOffset(Point point){
        this.offset.x += point.x;
        this.offset.y += point.y;
        invalidate();
    }

    //The clearSurface method clears the objects from the view so the view is redrawn blank.
    public void clearSurface(){
        objects.clear();
    }

    //Updates objects to the current backgroundcolor if erased is true
    public void updatedEraserObjects(){
        for (CanvasableObject object : objects){
            if(object.isErased()){
                Paint paint = object.getPaint();
                paint.setColor(backgroundcolor);
                object.setPaint(paint);
            }
        }
        invalidate();
    }

    //Getters and setters.
    public Paint getPaint(){
        return paint;
    }

    public CanvasableObject.ObjectType getObjectType(){
        return objectType;
    }

    public ArrayList<CanvasableObject> getObjects() {
        return objects;
    }

    public void setPaint(Paint paint){
        this.paint = paint;
        Log.i("=================", "setPintColor: " +  Integer.toString(paint.getColor()));
    }

    public void setObjectType(CanvasableObject.ObjectType objectType){
        this.objectType = objectType;
    }

    public void setObjects(ArrayList<CanvasableObject> objects) {
        this.objects = objects;
    }


    public int getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(int backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    public boolean isErased() {
        return erased;
    }

    public void setErased(boolean erased) {
        this.erased = erased;
    }

    public int getPreviousColor() {
        return previousColor;
    }

    public void setPreviousColor(int previousColor) {
        this.previousColor = previousColor;
    }
}
