package com.pixelswordgames.numbers.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TimeBarView extends View {
    public TimeBarView(Context context) {
        super(context);
        init();
    }

    public TimeBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TimeBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private static final int HEIGHT = 30;
    private static final int RADIUS = HEIGHT / 2;
    private static final float WIDTH = HEIGHT / 7f;

    private Paint paint, innerPaint;
    private long maxMillis = 60000;
    private long curMillis = 60000;
    private Thread thread;
    private boolean isRunning;
    private float alpha, da;


    private void init(){
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(WIDTH);
        paint.setStyle(Paint.Style.STROKE);

        innerPaint = new Paint();
        innerPaint.setColor(Color.WHITE);

        da = 3.5f;
    }

    public void start(){
        if(thread != null)
            stop();

        thread = new Thread(() -> {
            while (isRunning) {
                if(curMillis <= maxMillis / 10) {
                    alpha += da;
                    if (alpha > 255) {
                        alpha = 255;
                        da = -da;
                    }
                    if(alpha < 0){
                        alpha = 0;
                        da = -da;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        innerPaint.setColor(Color.argb((int)alpha, 255, 255, 255));
                    }
                }
                curMillis -= 10;
                if(curMillis < 0){
                    curMillis = 0;
                }
                invalidate();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        isRunning = true;
        thread.start();
    }

    public void stop(){
        isRunning = false;
        thread.interrupt();
        thread = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, (int) (HEIGHT + (WIDTH * 2)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        float width = canvas.getWidth()-WIDTH;
        canvas.drawRoundRect(WIDTH, WIDTH,width,WIDTH + HEIGHT,RADIUS,RADIUS,paint);
        if(curMillis != 0) {
            width = width * curMillis / maxMillis;
            canvas.drawRoundRect(WIDTH, WIDTH, width, WIDTH + HEIGHT, RADIUS, RADIUS, innerPaint);
        }
    }

    public void setMaxMillis(long maxMillis) {
        this.maxMillis = maxMillis;
        invalidate();
    }

    public void setCurMillis(long curMillis) {
        this.curMillis = curMillis;
        if(curMillis >= maxMillis)
            setMaxMillis(curMillis);
        invalidate();
    }

    public void addTime(long millis){
        if(curMillis + millis >= maxMillis)
            curMillis = maxMillis;
        else curMillis += millis;
        invalidate();
    }
}
