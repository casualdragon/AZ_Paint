package edu.apsu.csci.teamaz.azpaint;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Amy on 3/21/2017.
 */

public class DrawingSurface extends View{
    //Constructors
    public DrawingSurface(Context context) {
        super(context);
        setup(null);
    }

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    //Set up for canvas
    private void setup(AttributeSet attrs){
        //do something
    }

}
