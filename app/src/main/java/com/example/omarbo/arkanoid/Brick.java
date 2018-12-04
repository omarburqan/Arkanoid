package com.example.omarbo.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick {
    private  float left,top , right , bottom;
    private boolean hit;
    public  Brick(float left ,float  top ,float right ,float bottom){
        this.left=left;
        this.top=top;
        this.right=right;
        this.bottom=bottom;
        this.hit=false;
    }
    public  void brickDraw(Canvas canvas,Paint paint){
        canvas.drawRect(this.left,this.top,this.right,this.bottom,paint);


    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

}
