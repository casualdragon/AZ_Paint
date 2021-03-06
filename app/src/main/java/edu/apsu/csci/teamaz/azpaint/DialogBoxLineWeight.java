package edu.apsu.csci.teamaz.azpaint;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * This class used to create and display the dialog for the line weight.
 * The line weight allows the user to select how thick to draw a line or rectangle and
 * allows the user to set the selected color as the background.
 * This dialog also uses a seekbar.
 *
 */

public class DialogBoxLineWeight {
    private Paint paint;

    public DialogBoxLineWeight(final DrawingSurface surface){
        paint = surface.getPaint();
        Context context = surface.getContext();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_line_weight);

        dialog.show();

        Button confirm = (Button) dialog.findViewById(R.id.confirm_button_line);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_button_line);

        SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekBar);

        //Sets previous value
        int strokeWidth = (int)paint.getStrokeWidth();
        ((TextView) dialog.findViewById(R.id.textview_line)).setText(Integer.toString(strokeWidth));

        //Sets up the seekbar
        seekBar.setProgress(strokeWidth);
        seekBar.setMax(250);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView tv = (TextView) dialog.findViewById(R.id.textview_line);
                if(i == 0){
                    i = 1;
                }
                tv.setText(Integer.toString(i));
                paint.setStrokeWidth(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save the line weight and change stroke of the object
                surface.setPaint(paint);
                dialog.cancel();
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
