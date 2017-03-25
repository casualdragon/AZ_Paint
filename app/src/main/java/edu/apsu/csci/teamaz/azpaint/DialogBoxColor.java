package edu.apsu.csci.teamaz.azpaint;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nonam on 3/25/2017.
 */

public class DialogBoxColor {
    public DialogBoxColor(Context context, final DrawingSurface surface){
        final int r;

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_color);

        dialog.show();

        final Button confirm = (Button) dialog.findViewById(R.id.confirm_button_color);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_button_color);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        final ImageView colorchart =(ImageView)dialog.findViewById(R.id.colorchart);
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.colorchart);
        final ColorP colorP = new ColorP();
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

                    final int red = Color.red(pixel);
                    final int blue = Color.blue(pixel);
                    final int green = Color.green(pixel);

                    EditText redEditText = (EditText) dialog.findViewById(R.id.red);
                    EditText greenEditText = (EditText) dialog.findViewById(R.id.green);
                    EditText blueEditText = (EditText) dialog.findViewById(R.id.blue);

                    redEditText.setText(Integer.toString(red));
                    blueEditText.setText(Integer.toString(blue));
                    greenEditText.setText(Integer.toString(green));
                    colorP.setColor(red, blue, green);

                    colorDisplay.setBackgroundColor(Color.argb(255, red, green, blue));
                }

                return false;
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("=================", "In onclick for confirm");
                //Save the color and change color of the object
                surface.setPaintColor(colorP.getRed(), colorP.getBlue(), colorP.getGreen());
                dialog.cancel();
            }
        });

    }
    private class ColorP{
        private int red;
        private int green;
        private int blue;
        public  ColorP(){
            red= 0;
            green = 0;
            blue = 0;
        }
        public void setColor(int red, int green, int blue){
            this.red= red;
            this.green = green;
            this.blue = blue;
        }

        public int getRed() {
            return red;
        }

        public int getGreen() {
            return green;
        }

        public int getBlue() {
            return blue;
        }
    }
}
