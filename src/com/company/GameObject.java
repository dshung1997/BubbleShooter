package com.company;

import java.awt.*;

/**
 * Created by dshunggggg on 30/09/2016.
 */

//Tạo 1 interface chung cho các đối tượng bóng sau này
public abstract class GameObject {

    //Màu của bóng là từ 1-5, đúng với 5 file ảnh bên trong Res
    protected int color;

    //Vận tốc của bóng
    protected int velX, velY;

    //Tọa độ đỉnh của bóng
    protected Point vertex;

    //Tọa độ tâm của bóng
    protected Point center;

    //Đã bị xóa chưa ?
    public boolean isRemoved;

    //Có được gắn với gốc không ? (xử lí trường hợp bóng nổi)
    public boolean isSolid;

    public GameObject(Point p, int color){
        this.color = color;
        this.vertex = p;
        center = Hex.getCenterFromVertex(vertex);
    }

    //Hàm xử lí thông số
    public abstract void update();

    //Hàm xử lí ảnh
    public abstract void render(Graphics2D g2d);

}
