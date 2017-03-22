package edu.apsu.csci.teamaz.azpaint;

import android.content.Context;
import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity {

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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        switch(itemId) {
            case R.id.color:
                builder.setTitle("Color");
                builder.setView(R.layout.dialog_color);
                View colorchart = (View) findViewById(R.id.colorchart);
                colorchart.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        
                        return false;
                    }
                });
                break;
            case R.id.thickness:
                builder.setTitle("Thickness");

                break;
            default:
                Log.i("=============", "An error was encountered!");

        }
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

}
