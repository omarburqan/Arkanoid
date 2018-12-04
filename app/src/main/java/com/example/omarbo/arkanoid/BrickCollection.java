package com.example.omarbo.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class BrickCollection {
    public Brick[] getBricks() {
        return bricks;
    }

    public int getNumOfBricks() {
        return numOfBricks;
    }

    public void setNumOfBricks(int numOfBricks) {
        this.numOfBricks = numOfBricks;
    }
private boolean brickTouched;
    private Brick[] bricks;
    private int numOfRows;
    private int numOfColums;
    private  int numOfBricks;
    private int Score=0;

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public boolean isBrickTouched() {
        return brickTouched;
    }

    public void setBrickTouched(boolean brickTouched) {
        this.brickTouched = brickTouched;
    }

    BrickCollection(float left , float  top , float right , float bottom, int numOfBricks, int numOfRows, int numOfColums){
        this.brickTouched=false;
        bricks=new Brick[numOfBricks];
        this.numOfBricks=numOfBricks;
        this.numOfRows=numOfRows;
        this.numOfColums=numOfColums;
        float width =right ,height=bottom-top;
        int y=0;
        for(int j=0;j<numOfRows;j++) {
            for ( int i= 0;i < numOfColums; i++) {
                bricks[y] = new Brick(left, top, right, bottom);
                left=right+10;
                right+=width;
                y++;
            }
            top = bottom+10;
            bottom+=height;
            left=0;
            right=width;
        }
    }
    public void  DrawBricks(Canvas canvas, Paint paint,Ball ball){

        for (int i=0 ; i<numOfBricks;i++){
            if(bricks[i].isHit()==false){
                if((ball.getCy()+ball.getRadius()>=bricks[i].getTop() &&  ball.getCy()-ball.getRadius()<=bricks[i].getBottom()) &&
                        (ball.getCx()+ball.getRadius()>=bricks[i].getLeft()&& ball.getCx()-ball.getRadius()<=bricks[i].getRight())) {
                    bricks[i].setHit(true);
                    Score+=10;
                    brickTouched=true;
                    if((ball.getCx())>bricks[i].getRight() || (ball.getCx())<bricks[i].getLeft())
                    {
                        ball.setDx(ball.getDx()*-1);
                    }


                    if(ball.getCy()<bricks[i].getTop()||ball.getCy()>bricks[i].getBottom())
                    {
                        ball.setDy(ball.getDy()*-1);

                    }


                }
            }
                  paint.setColor(Color.GREEN);
                  if(bricks[i].isHit()==false)
                    bricks[i].brickDraw(canvas, paint);
        }
    }
}

