package edu.apsu.csci.teamaz.azpaint;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

//Test

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawingSurface surface = (DrawingSurface) findViewById(R.id.canvas);
        surface.setOnTouchListener(new CanvasTouchListener());

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
    }



    //Records points and sends them to
    private class CanvasTouchListener implements View.OnTouchListener {
        private Point startPoint;
        private Point endPoint;
        DrawingSurface surface;

        public CanvasTouchListener() {
            super();
            surface = (DrawingSurface) findViewById(R.id.canvas);
        }


        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Log.i("=======", "Touch Registered");

            //Gets starting Point
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                startPoint = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
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
                surface.removePrevious();
                endPoint = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
                surface.add(startPoint, endPoint);
                surface.invalidate();
                return true;
            }
            return false;
        }
    }
}
