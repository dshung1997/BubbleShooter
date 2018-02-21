package com.company;

import java.awt.*;

/**
 * Created by dshunggggg on 01/10/2016.
 */

//
public class Player extends GameObject {

    private int a = Hex.a;

    private Handler handler;

    public Player(Point p, int color, Handler handler){
        super(p, color);
        this.handler = handler;
        velX = 0;
        velY = 0;
    }

    public void update(){

        //Tăng 1 pixel theo update
        center.y += velY;
        center.x += velX;

        //Bật lại khi gặp tường
        if (center.x <= Hex.bordersX + 28 || center.x >= BubbleShooter.WIDTH - a/2-22) velX *= -1;

        //Set tâm theo tạo độ đỉnh
        vertex = Hex.getVertexFromCenter(center);

        //Nếu đang bay thì kiểm tra va chạm
        if(Process.isFlying) checkCollision();
    }

    @Override
    public void render(Graphics2D g2d){
        Hex.drawHex(vertex, color, g2d);

        if(isRemoved){
            g2d.dispose();
        }
    }

    //Kiểm tra va chạm
    public void checkCollision() {
        for (int i = 0; i < handler.listBubble.size(); i++) {
            Bubble tempBubble = handler.listBubble.get(i);
            if (checkCollision(center, tempBubble.center)) {
                velX = 0;
                velY = 0;
                handler.removePlayer(this);
                afterCollision();
                return;
            }
        }
    }

    //Xử lí sau va chạm
    public void afterCollision(){
        Process.snapBubble(handler, center, color);
    }

    //Va chạm của 2 bóng
    public boolean checkCollision(Point p1, Point p2){
        double a = 0.0;
        a = Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
        if(a < 50) return true;
        else return false;
    }

}
