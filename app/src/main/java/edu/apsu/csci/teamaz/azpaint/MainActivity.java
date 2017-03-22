package edu.apsu.csci.teamaz.azpaint;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

//Test

public class MainActivity extends AppCompatActivity {

    private ImageView colorchart;
    private TextView colorDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menudrawingsurface, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.color:
                DialogBox(item.getItemId());
                break;
            case R.id.line:
                break;
            case R.id.rectangle:
                break;
            case R.id.thickness:
                DialogBox(item.getItemId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogBox(int itemId){
       /* AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Color");
        builder.setView(R.layout.dialog_color);
        builder.show();
        final AlertDialog alertDialog = builder.create();
        */

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_color);

        dialog.show();

        colorchart =(ImageView)dialog.findViewById(R.id.colorchart);
        Log.i("===================", "colorchart:" + colorchart);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) colorchart.getBackground();
        Log.i("===================", "bitmap:" + bitmapDrawable);

        final Bitmap bitmap = bitmapDrawable.getBitmap();

        colorchart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int)motionEvent.getX();
                int y = (int)motionEvent.getY();
                int pixel = bitmap.getPixel(x,y);

                String hexVal = Integer.toHexString(pixel);
                colorDisplay = (TextView) dialog.findViewById(R.id.colorDisplay);
                int red = Color.red(pixel);
                int blue = Color.blue(pixel);
                int green = Color.green(pixel);

                Log.i("===============", "Color: " + Color.argb(255, red, green, blue));

                colorDisplay.setBackgroundColor(Color.argb(255, red, green, blue));

                return false;
            }
        });


    }

}
