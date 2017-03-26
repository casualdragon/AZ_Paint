package edu.apsu.csci.teamaz.azpaint;

import android.graphics.drawable.ColorDrawable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

//Test

public class MainActivity extends AppCompatActivity {
    final static String SURFACE = "SURFACE";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawingSurface surface = (DrawingSurface) findViewById(R.id.canvas);

//        if (savedInstanceState != null){
//            for(String key : savedInstanceState.keySet()) {
//                Log.i("====================", "Keys: " + key);
//            }
//        }

        if(savedInstanceState != null && savedInstanceState.containsKey(SURFACE)){
            DrawingSurface drawingSurface = (DrawingSurface) savedInstanceState.getSerializable(SURFACE);
            surface.setSettings(drawingSurface);
            Log.i("===================", "Reading Bundle");
        }

        surface.setOnTouchListener(new CanvasTouchListener());

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

        ImageView colorChart = (ImageView) findViewById(R.id.colorChart);
        colorChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedInstanceState.putSerializable("SURFACE", surface);
                new DialogBoxColor(surface);
            }
        });
        ImageView lineWeight = (ImageView) findViewById(R.id.lineWeight);
        lineWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogBoxLineWeight(surface);
            }
        });
        ImageView clear = (ImageView) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surface.clearSurface();
                surface.invalidate();
                surface.setBackgroundColor(0xFFFFFFFF);
            }
        });

        ImageView eraser = (ImageView) findViewById(R.id.eraser);
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color =((ColorDrawable)surface.getBackground()).getColor();
                SerializablePaint paint = surface.getPaint();
                paint.setColor(color);
                surface.setPaint(paint);
            }
        });

        ImageView undo = (ImageView) findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surface.removePrevious();
                surface.invalidate();
            }
        });

        ImageView pan = (ImageView) findViewById(R.id.pan);
        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surface.setObjectType(CanvasableObject.ObjectType.PAN);
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("==================", "In onSavedInstanceState");
        outState.putSerializable(SURFACE, (DrawingSurface) findViewById(R.id.canvas));
        super.onSaveInstanceState(outState);
    }

    //Records points and sends them to
    private class CanvasTouchListener implements View.OnTouchListener {
        private SerializablePoint startPoint;
        private SerializablePoint endPoint;
        DrawingSurface surface;
        private int count;

        public CanvasTouchListener() {
            super();
            surface = (DrawingSurface) findViewById(R.id.canvas);
            count = 0;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Log.i("=======", "Touch Registered");

            //Gets starting Point
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                startPoint = null;
                endPoint = null;
                startPoint = new SerializablePoint((int) motionEvent.getX(), (int) motionEvent.getY());
                count = 0;
                Log.i("=======", "Touch DOWN");
                return true;
            }
            //Gets ending Point
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if(surface.getObjectType() != CanvasableObject.ObjectType.PAN) {
                    endPoint = new SerializablePoint((int) motionEvent.getX(), (int) motionEvent.getY());
                    surface.add(startPoint, endPoint);
                    Log.i("=======", "Touch UP");
                } else{
                    int x, y;
                    x = (int) motionEvent.getX() - startPoint.x;
                    y = (int) motionEvent.getY() - startPoint.y;
                    surface.addOffset(new SerializablePoint(x,y));
                }
                return true;

            }
            //Otherwise it updates it with the current position
            else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                if(surface.getObjectType() != CanvasableObject.ObjectType.PAN) {
                    count++;
                    endPoint = new SerializablePoint((int) motionEvent.getX(), (int) motionEvent.getY());

                    if (surface.getObjectType() != CanvasableObject.ObjectType.FREE) {
                        surface.removePrevious();
                    }

                    surface.add(startPoint, endPoint);

                    if (surface.getObjectType() == CanvasableObject.ObjectType.FREE) {
                        surface.add(startPoint, endPoint);
                        startPoint = endPoint;
                    }
                } else {
                    int x, y;
                    x = (int) motionEvent.getX() - startPoint.x;
                    y = (int) motionEvent.getY() - startPoint.y;
                    startPoint.x += x;
                    startPoint.y += y;
                    surface.addOffset(new SerializablePoint(x,y));
                }


                return true;
            }
            return false;
        }
    }
}
