package edu.apsu.csci.teamaz.azpaint;

import java.io.Serializable;

 /*
  *  Contained in this file is serializable point. This may not be needed. Future test serializing
  *  point are needed.
  */

public class SerializablePoint implements Serializable {

    public int x;
    public int y;

    //Constructor
    public SerializablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }



}
