package edu.apsu.csci.teamaz.azpaint;

import java.io.Serializable;

/**
 * Created by nonam on 3/26/2017.
 */

public class SerializablePoint implements Serializable {

    public int x;
    public int y;

    public SerializablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }



}
