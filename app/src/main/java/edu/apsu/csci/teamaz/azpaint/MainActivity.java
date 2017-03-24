package edu.apsu.csci.teamaz.azpaint;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

        ImageView colorChart = (ImageView) findViewById(R.id.colorChart);
        colorChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBoxColor();
            }
        });
        ImageView lineWeight = (ImageView) findViewById(R.id.lineWeight);
        lineWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBoxLineWeight();
            }
        });
    }

    private void DialogBoxColor(){
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
    private void DialogBoxLineWeight(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_line_weight);

        dialog.show();

        Button confirm = (Button) dialog.findViewById(R.id.confirm_button_line);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_button_line);

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

        SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekBar);
        seekBar.setMax(250);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView tv = (TextView) dialog.findViewById(R.id.textview_line);
                int val = Integer.parseInt(tv.getText().toString());
                if(i == 0){
                    val = 1;
                }
                tv.setText(Integer.toString(val));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
