package edu.apsu.csci.teamaz.azpaint;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

//Test

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawingSurface surface = (DrawingSurface) findViewById(R.id.canvas);
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
    }



    //Records points and sends them to
    private class CanvasTouchListener implements View.OnTouchListener {
        private Point startPoint;
        private Point endPoint;
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
                startPoint = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
                count = 0;
                Log.i("=======", "Touch DOWN");
                return true;
            }
            //Gets ending Point
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                endPoint = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
                surface.add(startPoint, endPoint);
                Log.i("=======", "Touch UP");
                return true;

            }
            //Otherwise it updates it with the current position
            else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                count++;
                endPoint = new Point((int) motionEvent.getX(), (int) motionEvent.getY());

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
