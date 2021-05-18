package com.pixelswordgames.numbers.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimatedCharsView extends View {

    public AnimatedCharsView(Context context) {
        super(context);
        init();
    }

    public AnimatedCharsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatedCharsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AnimatedCharsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private static final int MAX_CHARS = 70;
    private static final String[] CHARS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "*", "/", "=", "%", "(", "!", "(", ">", "<"};
    private List<CharObj> chars;
    private Paint paint;
    private Thread thread;
    private boolean isRunning;

    private void init(){
        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(64f);
        paint.setTextAlign(Paint.Align.CENTER);

        chars = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        if(chars.size() == 0)
            createChars(getWidth(), getHeight());

        for(CharObj c : chars) {
            c.draw(canvas, paint);
        }
    }

    public void start(){
        if(thread != null)
            stop();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    update();
                    invalidate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

    private void update(){
        for(CharObj c : chars) {
            c.update((float) 0.1);
        }
    }

    private void createChars(int maxWidth, int maxHeight){
        for(int i = 0; i < MAX_CHARS - chars.size(); ++i){
            chars.add(new CharObj(
                    new Random().nextInt(maxWidth),
                    new Random().nextInt(maxHeight),
                    new Random().nextInt(360),
                    new Random().nextInt(2),
                    new Random().nextInt(8) + 8,
                    new Random().nextInt(20) - 10
            ));
        }
    }




    private static final class CharObj {
        private String c;
        private float x, y, a;
        private final float dx;
        private final float dy;
        private final float da;

        public CharObj(float x, float y, float a, float dx, float dy, float da) {
            this.c = getRandomChar();
            this.x = x;
            this.y = y;
            this.a = a;
            this.dx = dx;
            this.dy = dy;
            this.da = da;
        }

        public void update(float fElapse){
            x += dx * fElapse;
            y += dy * fElapse;
            a += da * fElapse;
        }

        private String getRandomChar(){
            return CHARS[new Random().nextInt(CHARS.length)];
        }

        public void draw(Canvas canvas, Paint paint){
            canvas.save();
            canvas.rotate(a, x, y);
            canvas.drawText(c, x, y, paint);
            canvas.restore();

            if(x > paint.getTextSize() + canvas.getWidth() ||
               y > paint.getTextSize() + canvas.getHeight() ||
               x < -paint.getTextSize() ||
               y < -paint.getTextSize()){
                x = new Random().nextInt(canvas.getWidth());
                y = -(paint.getTextSize()-1);
                a = new Random().nextInt(360);
                c = getRandomChar();
            }
        }
    }
}
