package edu.apsu.csci.teamaz.azpaint;

/**
 * Created by nonam on 3/25/2017.
 */

public class CustomPaint {
        private int red;
        private int green;
        private int blue;
        private int lineWeight;
        public CustomPaint(){
            red= 0;
            green = 0;
            blue = 0;
            lineWeight = 1;
        }
        public void setColor(int red, int green, int blue){
            this.red= red;
            this.green = green;
            this.blue = blue;
        }
        public void setLineWeight(int lineWeight){
            this.lineWeight = lineWeight;
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
        public int getLineWeight(){
            return lineWeight;
        }
}

