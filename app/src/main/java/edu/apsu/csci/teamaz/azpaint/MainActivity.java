package edu.apsu.csci.teamaz.azpaint;

import android.graphics.drawable.ColorDrawable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

//Test

public class MainActivity extends AppCompatActivity {
    final static String SURFACE = "SURFACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawingSurface surface = (DrawingSurface) findViewById(R.id.canvas);

        if(savedInstanceState != null && savedInstanceState.containsKey(SURFACE)){
            surface.setSettings((DrawingSurface) savedInstanceState.getSerializable(SURFACE));
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
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(SURFACE, (DrawingSurface) findViewById(R.id.canvas));
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
                startPoint = new SerializablePoint((int) motionEvent.getX(), (int) motionEvent.getY());
                count = 0;
                Log.i("=======", "Touch DOWN");
                return true;
            }
            //Gets ending Point
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                endPoint = new SerializablePoint((int) motionEvent.getX(), (int) motionEvent.getY());
                surface.add(startPoint, endPoint);
                Log.i("=======", "Touch UP");
                return true;

            }
            //Otherwise it updates it with the current position
            else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                count++;
                endPoint = new SerializablePoint((int) motionEvent.getX(), (int) motionEvent.getY());

                if(surface.getObjectType() != CanvasableObject.ObjectType.FREE) {
                    surface.removePrevious();
                }

                surface.add(startPoint, endPoint);

                if(surface.getObjectType() == CanvasableObject.ObjectType.FREE){
                        surface.add(startPoint, endPoint);
                        startPoint = endPoint;
                }

                return true;
            }
            return false;
        }
    }
}
