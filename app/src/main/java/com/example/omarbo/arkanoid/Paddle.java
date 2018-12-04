package com.example.omarbo.arkanoid;


import android.graphics.Canvas;
import android.graphics.Paint;

public class Paddle {
    public static float left,top , right , bottom;
    public int R=1,L=-1;
    float  cWidth,cHeight;
    public  Paddle(float left ,float  top ,float right ,float bottom){
        this.left=left;
        this.top=top;
        this.right=right;
        this.bottom=bottom;
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

    public  void paddleDraw(Canvas canvas, Paint paint){
        canvas.drawRect(this.left,this.top,this.right,this.bottom,paint);
        this.cHeight=canvas.getHeight();
        this.cWidth=canvas.getWidth();


    }
    public void move(int direction){
        if(direction==R&&right<cWidth){
            this.left+=15;
            this.right+=15;
        }
        else if(direction==L&&left>0){
            this.left-=15;
            this.right-=15;
        }
        else
            return;
    }
}

