package com.example.final_q1;

public class Point {
    float x, y; //좌표값
    boolean isDraw ;//그리기 상태

    public Point(float x, float y, boolean isDraw) {
        this.x = x;
        this.y = y;
        this.isDraw = isDraw;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", isDraw=" + isDraw +
                '}';
    }
}
