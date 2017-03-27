package edu.apsu.csci.teamaz.azpaint;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

 /*
  *  Contained in this file is the main activity for the application.
  *
  *  Features:
  *     *Clear
  *         #Description - Allows the user to erase all lines and rectangles and changes the
  *                        canvas to white.
  *         #Classes - DrawingSurface, CanvasableObject
  *         #Methods - clearSurface(), invalidate(), setBackground(int color)
  *         #Variables - DrawingSurface surface, ArrayList<CanvasableObject> objects
  *     *Undo
  *         #Description - Allows the user to delete the last drawn object
  *         #Classes - DrawingSurface, CanvasableObject
  *         #Methods - removePrevious(), invalidate()
  *         #Variables - DrawingSurface surface, ArrayList<CanvasableObject> objects
  *     *Set the Background Color
  *         #Description - Changes the background color of the canvas
  *         #Classes - DrawingSurface, DialogBoxColor
  *         #Methods - setBackgroundColor(int color), setBackgroundcolor(int color),
  *                    onTouch(View view, MotionEvent motionEvent)
  *         #Variables - DrawingSurface surface, int background
  *     *Eraser
  *         #Description - Creates a line of the background color, which dynamically changes with
  *                        the background color
  *         #Classes - DrawingSurface, CanvasableObject
  *         #Methods - updatedEraserObjects()
  *         #Variables - boolean erased, DrawingSurface surface,
  *                      ArrayList<CanvasableObject> objects
  *     *Pan
  *         #Description - Allows the canvas to be shifted around, which is determined by the user
  *         #Classes - DrawingSurface
  *         #Methods - calculateOffset(MotionEvent motionevent), addOffset(Point offset),
  *                    onTouch(View view, MotionEvent motionEvent)
  *         #Variables - CanvasableObject.ObjectType objectType
  */

public class MainActivity extends AppCompatActivity {
    // Keys for saveInstanceState.
    final static String SURFACE = "SURFACE";

    //Checks if the line is erasing
    private boolean erased;
    //Overriden onCreate method this activity. This override gets data from saveInstanceState if it
    //is available then sets the onClickListeners for all the buttons and the DrawingSurface.
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        erased = false;

        final DrawingSurface surface = (DrawingSurface) findViewById(R.id.canvas);

        //Data from saved instance state is read here.
        if(savedInstanceState != null && savedInstanceState.containsKey(SURFACE)){
            DrawingSurface drawingSurface = (DrawingSurface) savedInstanceState.getSerializable(SURFACE);
            surface.setSettings(drawingSurface);
            surface.setBackgroundColor(drawingSurface.getBackgroundcolor());
//            Log.i("===================", "Reading Bundle");
        }


        //Listeners
        surface.setOnTouchListener(new CanvasTouchListener());

        //The next two onClickListeners set the objectType of the surface to the corresponding type.
        ImageView lineView = (ImageView) findViewById(R.id.line);
        lineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surface.setObjectType(CanvasableObject.ObjectType.LINE);
            }
        });

        ImageView rectView = (ImageView) findViewById(R.id.rectangle);
        rectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surface.setObjectType(CanvasableObject.ObjectType.RECTANGLE);
            }
        });

        //The next two onClickListeners open the corresponding dialogbox.
        final ImageView colorChart = (ImageView) findViewById(R.id.colorChart);
        colorChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erased = false;
                surface.setErased(erased);
                new DialogBoxColor(surface);
            }
        });

        ImageView lineWeight = (ImageView) findViewById(R.id.lineWeight);
        lineWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surface.setErased(erased);
                new DialogBoxLineWeight(surface);
            }
        });

        //This button clears the surface and tells it to redraw.
        ImageView clear = (ImageView) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surface.clearSurface();
                surface.invalidate();
                surface.setBackgroundColor(0xFFFFFFFF);
            }
        });

        //This button allows the use to draw an object the same color as the background effectively
        //hiding other objects.
        ImageView eraser = (ImageView) findViewById(R.id.eraser);
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erased = !erased;
                surface.setObjectType(CanvasableObject.ObjectType.LINE);
                surface.setErased(erased);
                int color =((ColorDrawable)surface.getBackground()).getColor();
                Paint backgroundcolor = surface.getPaint();
                backgroundcolor.setColor(color);
                if(erased) {
                    colorChart.setVisibility(View.GONE);
                }else {
                    colorChart.setVisibility(View.VISIBLE);
                }
            }
        });

        //This button removes the last added object from the surface. A bug here is making it so the
        //user needs to click twice before the first object is removed. Earlier fix caused other issues.
        ImageView undo = (ImageView) findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surface.removePrevious();
                surface.invalidate();
            }
        });

        //This button sets the surface objectType to pan so the user can pan.
        ImageView pan = (ImageView) findViewById(R.id.pan);
        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surface.setObjectType(CanvasableObject.ObjectType.PAN);
            }
        });
    }

    //Override for onSavedInstanceState. This override saves the surface when the screen is rotated.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SURFACE, (DrawingSurface) findViewById(R.id.canvas));
        super.onSaveInstanceState(outState);
    }

    //This listener records points and sends them to the surface.
    private class CanvasTouchListener implements View.OnTouchListener {
        //Startpoint is where the user clicks initially.
        //Endpoint is where the user releases their click or where the cursor is for ACTION_MOVE.
        private Point startPoint;
        private Point endPoint;
        DrawingSurface surface;

        //Default constructor saves the surface to the object for later use.
        public CanvasTouchListener() {
            super();
            surface = (DrawingSurface) findViewById(R.id.canvas);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
//            Log.i("=======", "Touch Registered");

            //Clears points and gets starting point.
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                startPoint = null;
                endPoint = null;
                startPoint = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
//                Log.i("=======", "Touch DOWN");
                return true;
            }
            //Gets ending Point and adds the object unless user is panning.
            //If the user is panning it sends the new offset to the surface.
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if(surface.getObjectType() != CanvasableObject.ObjectType.PAN) {
                    updateLine(motionEvent);
                    Log.i("=======", "Touch UP");
                }
                else{
                    calculateOffset(motionEvent);
                }
                return true;
            }
            //Updates and adds line for current position allowing the drawn line to be seen while it
            //is being drawn. If the user is panning it adds the offset.
            else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                if(surface.getObjectType() != CanvasableObject.ObjectType.PAN) {
                    updateLine(motionEvent);
                } else {
                    Point offset = calculateOffset(motionEvent);
                    startPoint.x += offset.x;
                    startPoint.y += offset.y;
                }
                return true;
            }
            return false;
        }

        //Adds the current line to the surface and removes previous update if they exist
        private void updateLine(MotionEvent motionEvent) {
            if(endPoint != null){
                surface.removePrevious();
            }
            endPoint = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
            surface.add(startPoint, endPoint);
        }

        //Calculates the offset for pannings
        @NonNull
        private Point calculateOffset(MotionEvent motionEvent) {
            Point offset = new Point(0,0);
            offset.x = (int) motionEvent.getX() - startPoint.x;
            offset.y = (int) motionEvent.getY() - startPoint.y;
            surface.addOffset(offset);
            return offset;
        }
    }
}
