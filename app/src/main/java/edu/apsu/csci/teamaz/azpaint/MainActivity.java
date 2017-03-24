package edu.apsu.csci.teamaz.azpaint;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

//Test

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawingSurface surface = (DrawingSurface) findViewById(R.id.canvas);
        surface.setOnTouchListener(new CanvasTouchListener());

        ImageView colorChart = (ImageView) findViewById(R.id.colorChart);
        colorChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBox();
            }
        });
    }

    private void DialogBox(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_color);

        dialog.show();

        Button confirm = (Button) dialog.findViewById(R.id.confirm_button_color);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_button_color);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save the color and change color of the object
            }
        });

        final ImageView colorchart =(ImageView)dialog.findViewById(R.id.colorchart);
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) colorchart.getBackground();
        final Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.colorchart);
//        final Bitmap bitmap = bitmapDrawable.getBitmap();

        colorchart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();

                    Log.i("============", "x: " + x + " y: " + y);

                    TextView colorDisplay = (TextView) dialog.findViewById(R.id.colorDisplay);

                    int convertedX = (int)((double) x / colorchart.getWidth() * bitmap.getWidth());
                    int convertedY = (int)((double) y / colorchart.getHeight() * bitmap.getHeight());

                    Log.i("============", "cx: " + convertedX + " cy: " + convertedY);

                    int pixel = bitmap.getPixel(convertedX, convertedY);

                    int red = Color.red(pixel);
                    int blue = Color.blue(pixel);
                    int green = Color.green(pixel);

                    EditText redEditText = (EditText) dialog.findViewById(R.id.red);
                    EditText greenEditText = (EditText) dialog.findViewById(R.id.green);
                    EditText blueEditText = (EditText) dialog.findViewById(R.id.blue);

                    redEditText.setText(Integer.toString(red));
                    blueEditText.setText(Integer.toString(blue));
                    greenEditText.setText(Integer.toString(green));

                    colorDisplay.setBackgroundColor(Color.argb(255, red, green, blue));
                }
                return false;
            }
        });


    }

    //Records points and sends them to
    private class CanvasTouchListener implements View.OnTouchListener{
        private Point startPoint;
        private Point endPoint;
        DrawingSurface surface;

        public CanvasTouchListener(){
            super();
            surface = (DrawingSurface) findViewById(R.id.canvas);
        }


        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Log.i("=======", "Touch Registered");

            //Gets starting Point
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                startPoint = new Point((int)motionEvent.getX(),(int)motionEvent.getY());
                Log.i("=======", "Touch DOWN");
                return true;
            }
            //Gets ending Point
            else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                endPoint = new Point((int)motionEvent.getX(),(int)motionEvent.getY());
                surface.add(startPoint, endPoint);
                Log.i("=======", "Touch UP");
                return true;

            }
            //Otherwise it updates it with the current position
            else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                surface.removePrevious();
                endPoint = new Point((int)motionEvent.getX(),(int)motionEvent.getY());
                surface.add(startPoint, endPoint);
                surface.invalidate();
                return true;
            }
            return false;
        }
    }


}
