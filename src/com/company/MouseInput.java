package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by dshunggggg on 23/09/2016.
 */
public class MouseInput implements MouseMotionListener, MouseListener {
    private Handler handler;

    public MouseInput(Handler handler){
        this.handler = handler;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //hàm này tính velX, velY từ tâm của player tới tọa độ curson
        if(!Process.isFlying){
            Point p = e.getPoint();
            double k = 30.0;
            for(int i = 0; i < handler.listPlayer.size(); i++){
                GameObject tempObject = handler.listPlayer.get(i);

                //Khoảng cách theo x
                double disX = Math.abs(p.x - tempObject.center.x);

                //Khoảng cách theo y
                double disY = Math.abs(p.y - tempObject.center.y);

                //Khoảng cách
                double dis = Math.sqrt(Math.pow(disX, 2) + Math.pow(disY, 2));

                //Nếu quá gần thì bỏ
                if (dis > 4) {
                    if (tempObject.center.x >= p.x) {
                        tempObject.velX = ((int) (-k * disX / dis));
                    } else {
                        tempObject.velX = ((int) (+k * disX / dis));
                    }

                    if (tempObject.center.y >= p.y) {
                        tempObject.velY = ((int) (-k * disY / dis));
                    } else {
                        tempObject.velY = ((int) (+k * disY / dis));
                    }
                } else {
                    tempObject.velX = 0;
                    tempObject.velY = 0;
                }
                break;

            }

            //Sau khi bắn thì thêm 1 Player mới
            Process.newPlayer(handler);

            //Set đang bay = true
            Process.isFlying = true;

            //Làm mới bóng đang chờ
            Process.renewWaitingBubble();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
