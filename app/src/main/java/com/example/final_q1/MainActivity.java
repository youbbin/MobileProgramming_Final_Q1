package com.example.final_q1;

import static android.graphics.Color.BLACK;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    final static int LINE = 1, RECTANGLE = 2, CIRCLE = 3, CURVE = 4, ERASE=5, CLEAR=6, EMBO=7, BLUR=8, NONE=0; // 메뉴에서 선택한 것을 구분하기 위해 사용
    final static int BLACK = 1, RED = 2, BLUE = 3, GREEN = 4, YELLOW=5;
    static int curColor=Color.BLACK;
    static int curShape; // 선택된 도형이 선인지 원인지 사각형인지 저장
    Button btnLine, btnRect, btnCircle, btnCurve, btnErase, btnClear, btnEmbo, btnBlur;
    Button prevButton;
    static int effectNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Painter");

        btnLine=(Button)findViewById(R.id.btnLine);
        btnRect=(Button)findViewById(R.id.btnRect);
        btnCircle=(Button)findViewById(R.id.btnCircle);
        btnCurve=(Button)findViewById(R.id.btnCurve);
        btnErase=(Button)findViewById(R.id.btnErase);
        btnClear=(Button)findViewById(R.id.btnClear);
        btnEmbo=(Button)findViewById(R.id.btnEmbo);
        btnBlur=(Button)findViewById(R.id.btnBlur);

        prevButton=btnLine;

        btnLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevButton.setTextColor(Color.BLACK);
                btnLine.setTextColor(Color.RED);
                prevButton=btnLine;
                MyGraphicView.curShape=LINE;
                MyGraphicView.color = curColor;
                MyGraphicView.effectNum=effectNum;
            }
        });
        btnRect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curShape=RECTANGLE;
                prevButton.setTextColor(Color.BLACK);
                btnRect.setTextColor(Color.RED);
                prevButton=btnRect;
                MyGraphicView.curShape=RECTANGLE;
                MyGraphicView.color = curColor;
                MyGraphicView.effectNum=effectNum;
            }
        });
        btnCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curShape=CIRCLE;
                prevButton.setTextColor(Color.BLACK);
                btnCircle.setTextColor(Color.RED);
                prevButton=btnCircle;
                MyGraphicView.curShape=CIRCLE;
                MyGraphicView.color = curColor;
                MyGraphicView.effectNum=effectNum;
            }
        });
        btnCurve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curShape=CURVE;
                prevButton.setTextColor(Color.BLACK);
                btnCurve.setTextColor(Color.RED);
                prevButton=btnCurve;
                MyGraphicView.curShape=CURVE;
                MyGraphicView.color = curColor;

            }
        });
        btnErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curShape=ERASE;
                prevButton.setTextColor(Color.BLACK);
                btnErase.setTextColor(Color.RED);
                prevButton=btnErase;
                MyGraphicView.curShape=ERASE;
                MyGraphicView.color=Color.WHITE;
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyGraphicView.clear();
                prevButton.setTextColor(Color.BLACK);
                btnClear.setTextColor(Color.RED);
                prevButton=btnClear;
            }
        });
        btnEmbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(effectNum==NONE||effectNum==BLUR){
                    effectNum=EMBO;
                    EmbossMaskFilter embo = new EmbossMaskFilter(new float[] {2, 2, 2}, 0.5f, 6, 5);
                    MyGraphicView.effectNum=EMBO;
                    
                    btnEmbo.setTextColor(Color.RED);
                    btnBlur.setTextColor(Color.BLACK);
                    return;
                }
                if(effectNum==EMBO){ // 이미 Embo 효과 적용되어있을때 또버튼 누른 경우
                    btnEmbo.setTextColor(Color.BLACK);
                    effectNum=NONE; // 효과 제거
                    MyGraphicView.effectNum=NONE;
                    return;
                }
            }
        });
        btnBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(effectNum==NONE||effectNum==EMBO){
                    effectNum=BLUR;
                    BlurMaskFilter blur = new BlurMaskFilter( 10, BlurMaskFilter.Blur.NORMAL);
                    MyGraphicView.effectNum=BLUR;
                    MyGraphicView.effect=blur;
                    btnBlur.setTextColor(Color.RED);
                    btnEmbo.setTextColor(Color.BLACK);
                    return;
                }
                if(effectNum==BLUR){
                    effectNum=NONE;
                    MyGraphicView.effectNum=NONE;
                    MyGraphicView.effect=null;
                    return;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "검정색");
        menu.add(0, 2, 0, "빨간색");
        menu.add(0, 3, 0, "파란색");
        menu.add(0, 4, 0, "초록색");
        menu.add(0, 5, 0, "노란색");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(curShape!=ERASE){
            switch (item.getItemId()) {
                case 1:
                    MyGraphicView.color =Color.BLACK;
                    curColor=Color.BLACK;
                    return true;
                case 2:
                    MyGraphicView.color = Color.RED;
                    curColor=Color.RED;
                    return true;
                case 3:
                    MyGraphicView.color = Color.BLUE;
                    curColor=Color.BLUE;
                    return true;
                case 4:
                    MyGraphicView.color = Color.GREEN;
                    curColor=Color.GREEN;
                    return true;
                case 5:
                    MyGraphicView.color = Color.YELLOW;
                    curColor=Color.YELLOW;
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}