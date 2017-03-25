package edu.apsu.csci.teamaz.azpaint;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by nonam on 3/25/2017.
 */

public class DialogBoxLineWeight {
    public DialogBoxLineWeight(final DrawingSurface surface){
        Context context = surface.getContext();
        final Dialog dialog = new Dialog(context);
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

        final CustomPaint customPaint = new CustomPaint();
        SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekBar);
        seekBar.setMax(250);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView tv = (TextView) dialog.findViewById(R.id.textview_line);
                if(i == 0){
                    i = 1;
                }
                tv.setText(Integer.toString(i));
                customPaint.setLineWeight(i);
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
                surface.setLineWeight(customPaint.getLineWeight());
                dialog.cancel();
            }
        });
    }
}
