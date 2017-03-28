package edu.apsu.csci.teamaz.azpaint;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class used to create and display the dialog to select a color.
 * The color picker allows the user to select what color to draw and
 * allows the user to set the selected color as the background.
 *
 */

public class DialogBoxColor {
    private final Paint paint;

    public DialogBoxColor(final DrawingSurface surface){
        paint = surface.getPaint();

        Log.i("=====================", "ColorPickerPaint: " +Integer.toString(paint.getColor()));

        Context context = surface.getContext();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_color);
        dialog.findViewById(R.id.colorDisplay).setBackgroundColor(paint.getColor());

        dialog.show();

        Button confirm = (Button) dialog.findViewById(R.id.confirm_button_color);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_button_color);
        Button background = (Button)dialog.findViewById(R.id.backgroud_button);

        final ImageView colorchart =(ImageView)dialog.findViewById(R.id.colorchart);
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.colorchart);

        final TextView redEditText = (TextView) dialog.findViewById(R.id.red);
        final TextView greenEditText = (TextView) dialog.findViewById(R.id.green);
        final TextView blueEditText = (TextView) dialog.findViewById(R.id.blue);

        redEditText.setText(Integer.toString(Color.red(paint.getColor())));
        greenEditText.setText(Integer.toString(Color.green(paint.getColor())));
        blueEditText.setText(Integer.toString(Color.blue(paint.getColor())));

        //The ontouch event allows us to get the selected color from the exact place the user
        //selected
        colorchart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();

                    //Used for debugging purposes
                    //Log.i("============", "x: " + x + " y: " + y);

                    TextView colorDisplay = (TextView) dialog.findViewById(R.id.colorDisplay);

                    //This adjust the touched x and y value due to scaling issues
                    int convertedX = (int)((double) x / colorchart.getWidth() * bitmap.getWidth());
                    int convertedY = (int)((double) y / colorchart.getHeight() * bitmap.getHeight());

                    //Used for debugging purposes
                    //Log.i("============", "cx: " + convertedX + " cy: " + convertedY);

                    int pixel = bitmap.getPixel(convertedX, convertedY);

                    int red = Color.red(pixel);
                    int blue = Color.blue(pixel);
                    int green = Color.green(pixel);

                    //This displays the selected int values of the color by the color's rgb values
                    redEditText.setText(Integer.toString(red));
                    blueEditText.setText(Integer.toString(blue));
                    greenEditText.setText(Integer.toString(green));
                    paint.setARGB(255,red,green,blue);

                    colorDisplay.setBackgroundColor(Color.argb(255, red, green, blue));
                }

                return false;
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save the color and change color of the object
                surface.setPaint(paint);
                dialog.cancel();
            }
        });
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This sets the background color of the surface view
                //and sets the int backgroundcolor in the surface view,
                //which the int backgroundcolor is used for saving the value for the rotations
                surface.setBackgroundColor(paint.getColor());
                surface.setBackgroundcolor(paint.getColor());
                surface.updatedEraserObjects();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }
}
