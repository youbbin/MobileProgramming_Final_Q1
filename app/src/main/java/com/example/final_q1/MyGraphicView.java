package com.example.final_q1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyGraphicView extends View {
    final static int LINE = 1, RECTANGLE = 2, CIRCLE = 3, CURVE = 4, ERASE=5, CLEAR=6, EMBO=7, BLUR=8, NONE=0; // 메뉴에서 선택한 것을 구분하기 위해 사용
    static int curShape; // 선택된 도형이 선인지 원인지 사각형인지 저장
    static int color=Color.BLACK;
    static MaskFilter effect;
    static int effectNum;
    static MyShape currentShape;
    static ArrayList<MyShape> MyShapeArrayList = new ArrayList<>();
    private static Paint paint;
    public MyGraphicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    ArrayList<Point> points;
    MyGraphicView graphicView;

    public void setEffect(){
        if(effectNum==NONE){
            effect=null;
        }
        else if(effectNum==EMBO){
            EmbossMaskFilter embo=new EmbossMaskFilter(new float[] {1, 1, 1}, 0.4f, 5.0f, 10);
            effect=embo;
        }
        else{
            BlurMaskFilter blur = new BlurMaskFilter( 20, BlurMaskFilter.Blur.NORMAL );
            effect=blur;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(curShape==CURVE||curShape==ERASE){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    points =new ArrayList<>();
                    setEffect(); // 효과 적용
                    currentShape=new MyShape(curShape,points);
                    if(curShape==ERASE){
                        currentShape.color=Color.WHITE;
                    }
                    currentShape.color=color;
                    currentShape.effect=effect;
                    currentShape.startX = (int) event.getX();
                    currentShape.startY = (int) event.getY();
                    points.add(new Point(currentShape.startX, currentShape.startY, false));
                    break;
                case MotionEvent.ACTION_MOVE:
                    currentShape.startX = (int) event.getX();
                    currentShape.startY = (int) event.getY();
                    points.add(new Point(currentShape.startX, currentShape.startY, true));
                    MyShapeArrayList.add(currentShape);
                    this.invalidate();//화면 갱신 요청
                    break;
                case MotionEvent.ACTION_UP:
                    currentShape.points=points;
                    MyShapeArrayList.add(currentShape);
                    currentShape=null;
                    this.invalidate();//화면 갱신 요청
                    break;
            }
        }else{
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    graphicView=((MainActivity)MainActivity.context).graphicView;
                    graphicView.setVisibility(View.VISIBLE);
                    setEffect();
                    currentShape = new MyShape(curShape);
                    currentShape.color = color;
                    currentShape.effect=effect;
                    currentShape.startX = (int) event.getX();
                    currentShape.startY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    currentShape.stopX = (int) event.getX();
                    currentShape.stopY = (int) event.getY();
                    this.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    currentShape.stopX = (int) event.getX();
                    currentShape.stopY = (int) event.getY();
                    MyShapeArrayList.add(currentShape);
                    currentShape = null;
                    this.invalidate();
                    break;
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        for (MyShape currentShape : MyShapeArrayList) {
            paint.setColor(currentShape.color);
            paint.setMaskFilter(currentShape.effect);
            drawShape(currentShape, canvas, paint);
        }
        if (currentShape != null) {
            paint.setColor(currentShape.color);
            paint.setMaskFilter(currentShape.effect);
            drawShape(currentShape, canvas, paint);
        }
    }


    private static void drawShape(MyShape currentShape, Canvas canvas, Paint paint) {
        switch (currentShape.shape) {
            case LINE:
                canvas.drawLine(currentShape.startX, currentShape.startY,
                        currentShape.stopX, currentShape.stopY, paint);
                break;
            case CIRCLE:
                int radius = (int) Math.sqrt(Math.pow(currentShape.stopX - currentShape.startX, 2) +
                        Math.pow(currentShape.stopY - currentShape.startY, 2));
                canvas.drawCircle(currentShape.startX, currentShape.startY, radius, paint);
                break;
            case RECTANGLE:
                Rect rect = new Rect(currentShape.startX, currentShape.startY,
                        currentShape.stopX, currentShape.stopY);
                canvas.drawRect(rect, paint);
                break;
            case CURVE:
            case ERASE:
                for(int i =0; i<currentShape.points.size(); i++){
                    Point now=currentShape.points.get(i);//현재 좌표
                    if(now.isDraw){ //move 상태이면
                        Point before =currentShape.points.get(i-1); //이전좌표값
                        //이전좌표부터 현재좌표까지 그리기
                        canvas.drawLine(before.x, before.y, now.x, now.y, paint);
                    }
                }
                break;
        }
    }

    private static class MyShape {
        int shape,startX, startY, stopX, stopY, color;
        MaskFilter effect;
        ArrayList<Point> points;
        public MyShape(int shape) {
            this.shape = shape;
        }
        public MyShape(int shape, ArrayList<Point> points){
            this.shape=shape;
            this.points=points;
        }
    }
}
