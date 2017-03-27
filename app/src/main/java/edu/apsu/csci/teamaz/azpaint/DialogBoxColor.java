package edu.apsu.csci.teamaz.azpaint;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
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
    private final Paint paint;

    public DialogBoxColor(final DrawingSurface surface){
        paint = surface.getPaint();

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

        final EditText redEditText = (EditText) dialog.findViewById(R.id.red);
        final EditText greenEditText = (EditText) dialog.findViewById(R.id.green);
        final EditText blueEditText = (EditText) dialog.findViewById(R.id.blue);

        redEditText.addTextChangedListener(new textWatcher(Color.red(paint.getColor())));
        greenEditText.addTextChangedListener(new textWatcher(Color.green(paint.getColor())));
        blueEditText.addTextChangedListener(new textWatcher(Color.blue(paint.getColor())));

        redEditText.setText(Integer.toString(Color.red(paint.getColor())));
        greenEditText.setText(Integer.toString(Color.green(paint.getColor())));
        blueEditText.setText(Integer.toString(Color.blue(paint.getColor())));

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
                surface.setBackgroundColor(paint.getColor());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    private class textWatcher implements TextWatcher{

        private int color;
        textWatcher(int color){
            this.color = color;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
