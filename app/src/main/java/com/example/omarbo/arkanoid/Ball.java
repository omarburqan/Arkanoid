package com.example.omarbo.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;


public class Ball {
    private  float cx,cy,radius,direction;
    int dx,dy;
    private  int speed=1;
    private boolean canMove=true;
    private boolean gameOver=false;

    public Ball(float cx , float cy, float radius){
        this.cx=cx;
        this.cy=cy;
        this.radius=radius;
        this.dx=0;
        this.dy=0;
    }
    public  void ballDraw(Canvas canvas, Paint paint){
        canvas.drawCircle(this.cx,this.cy,this.radius,paint);
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public float getCx() {
        return cx;
    }

    public float getCy() {
        return cy;
    }

    public float getRadius() {
        return radius;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public void move(int w, int h, float top, float left , float right)
    {
        this.cx+= dx;
        this.cy+= dy;

        // check if ball out of left or right side
        if((cx-radius)<=0 || (cx+radius)>=w)
        {
            dx = -dx;
        }

        // check if ball out of bottom or up side
        if((cy+radius)>=h || (cy-radius)<=0)
        {
            dy = -dy;
        }

        if((cx+radius)>=left&&(cx-radius)<=right&&(cy+radius)>=top)//paddle
        {
            dy = -dy;

        }
        if((cy)>top+10){ // under paddle
            gameOver=true;

        }


    }


}
