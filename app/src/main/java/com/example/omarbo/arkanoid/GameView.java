package com.example.omarbo.arkanoid;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.Random;

public class GameView extends View {
    private BrickCollection bricks;
    private Paddle paddle;
    private Ball ball;
    private boolean isPaddleMovingRight=false;
    private boolean isPaddleMovingleft=false;
    Random rnd = new Random();
    int direction =rnd.nextInt(2);
    private enum State {GET_READY, PLAYING, GAME_OVER};
    private State state = State.GET_READY;;
    private int h,w;
    public int Lives=3;
    private boolean win=false;
    Paint penMsg = new Paint(Paint.ANTI_ALIAS_FLAG);
    Canvas canvas;
    SoundPool soundPool;
    int beep1ID = -1;

    public GameView(Context context, AttributeSet atr) {
        super(context, atr);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);

        try{
            // Create objects of the 2 required classes
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Load our fx in memory ready for use
            descriptor = assetManager.openFd("beep1.ogg");
            beep1ID = soundPool.load(descriptor, 0);


        }catch(IOException e){
            // Print an error message to the console
            Log.e("error", "failed to load sound files");
        }
    }
    protected void onDraw(Canvas canvas) {
        this.canvas=canvas;
        super.onDraw(canvas);
        Paint p= new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        bricks.DrawBricks(canvas,p,ball);
        p.setColor(Color.RED);
        paddle.paddleDraw(canvas,p);
        p.setColor(Color.BLUE);
        ball.ballDraw(canvas,p);
        penMsg.setTextAlign(Paint.Align.CENTER);
        penMsg.setColor(Color.YELLOW);
        penMsg.setStyle(Paint.Style.FILL_AND_STROKE);
        penMsg.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        penMsg.setTextSize(55);

        for(int i=0; i< bricks.getBricks().length ;i++){
            if(bricks.getBricks()[i].isHit() == false ){
               break;
            }
            if(i==bricks.getBricks().length-1){
                win=true;
                state=State.GAME_OVER;
            }
        }

        if(ball.isGameOver()==true && Lives > 0 ){
            state=State.GET_READY;
            ball.setGameOver(false);
            Lives--;
        }
        if(Lives==0 && ball.isGameOver()==true ){
            state=State.GAME_OVER;
            ball.setGameOver(false);
        }



        switch (state) {
            case GET_READY:
                canvas.drawText("Click to PLAY!", (canvas.getWidth()/2), (canvas.getHeight() / 2)+100, penMsg);
                canvas.drawText("Score : " + bricks.getScore() , 200, 100, penMsg);
                canvas.drawText("Lives : " + Lives , canvas.getWidth()-200, 100, penMsg);
                break;
            case PLAYING:
                canvas.drawText("Score : " + bricks.getScore() , 200, 100, penMsg);
                canvas.drawText("Lives : " + Lives , canvas.getWidth()-200, 100, penMsg);
                ball.move(canvas.getWidth(), canvas.getHeight(), paddle.getTop(), paddle.getLeft(), paddle.getRight());
                if(bricks.isBrickTouched()){
                    soundPool.play(beep1ID, 1, 1, 0, 0, 1);
                    bricks.setBrickTouched(false);
                }else{
                    soundPool.stop(beep1ID);
                }
                if (isPaddleMovingleft == true)
                    paddle.move(-1);
                else if (isPaddleMovingRight == true)
                    paddle.move(1);
                else
                    paddle.move(0);
                break;
            case GAME_OVER:
                canvas.drawText("Score : " + bricks.getScore()  , 200, 100, penMsg);
                canvas.drawText("Lives : " + Lives , canvas.getWidth()-200, 100, penMsg);
                if(win){
                    canvas.drawText("You Win!", canvas.getWidth() / 2, canvas.getHeight() / 2, penMsg);
                }else{
                    canvas.drawText("GAME OVER - You Loss!", canvas.getWidth() / 2, canvas.getHeight() / 2, penMsg);
                }
                break;
        }

        invalidate();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int left=0;
        int top=(h/4)-(h/10);
        this.h=h;
        this.w=w;
        bricks=new BrickCollection(left , top , w/7,(h/12)+top,28,4,7);
        paddle=new Paddle((w/2)-(w/8),h-(h/16),(w/2)+(w/8),h-(h/30));
        ball= new Ball(w/2,h-((h/16)*2),h/50);
        if(direction==0)
            direction=-1;
        ball.setDx(direction*5);
        ball.setDy(-5);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction() ){
            case MotionEvent.ACTION_DOWN:
                if(state == State.GET_READY ) {
                    state = State.PLAYING;
                    int left=0;
                    int top=(h/4)-(h/10);
                    //bricks=new BrickCollection(left , top , w/7,(h/12)+top,28,4,7);
                    paddle=new Paddle((w/2)-(w/8),h-(h/16),(w/2)+(w/8),h-(h/30));
                    ball= new Ball(w/2,h-((h/16)*2),h/50);
                    if(direction==0)
                        direction=-1;
                    ball.setDx(direction*5);
                    ball.setDy(-5);
                }else if(state==State.GAME_OVER ){
                    state=state.GET_READY;
                    bricks.setScore(0);
                    int left=0;
                    Lives=3;
                    int top=(h/4)-(h/10);
                    bricks=new BrickCollection(left , top , w/7,(h/12)+top,28,4,7);
                    paddle=new Paddle((w/2)-(w/8),h-(h/16),(w/2)+(w/8),h-(h/30));
                    ball= new Ball(w/2,h-((h/16)*2),h/50);
                    if(direction==0)
                        direction=-1;
                    ball.setDx(direction*5);
                    ball.setDy(-5);

                }
                else {
                    if(state == State.PLAYING) {
                        if (event.getX() > getWidth() / 2) {
                            paddle.move(1);
                            isPaddleMovingRight = true;
                            isPaddleMovingleft = false;
                            invalidate();
                        } else {
                            paddle.move(-1);
                            isPaddleMovingleft = true;
                            isPaddleMovingRight = false;
                            invalidate();
                        }
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                isPaddleMovingleft=false;
                isPaddleMovingRight=false;
                paddle.move(0);
                invalidate();
                break;

        }
        return true;

    }


}
