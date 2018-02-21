package com.company;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by dshunggggg on 29/09/2016.
 */

//Hàm này dùng để xử lí list Bóng, list Player (thêm , xóa, chạy update, render toàn bộ list Bóng, list Player)
public class Handler {

    //Tạo 1 list Bóng và 1 list Player
    LinkedList<Bubble> listBubble = new LinkedList<Bubble>();
    LinkedList<Player> listPlayer = new LinkedList<Player>();


    //Chạy update trong list bóng
    public void updateBubble(){
        for(int i = 0; i < listBubble.size(); i++){
            Bubble tempBubble = listBubble.get(i);
            tempBubble.update();
        }
    }

    //Chạy update trong listPlayer
    public void updatePlayer(){
        for(int i = 0; i < listPlayer.size(); i++){
            Player tempPlayer = listPlayer.get(i);
            tempPlayer.update();
        }
    }


    //Chạy render của list Bubble
    public void renderBubble (Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        for(int i = 0; i < listBubble.size(); i++){
            Bubble tempBubble = listBubble.get(i);
            tempBubble.render(g2d);
        }
    }


    //Chạy render của list Player
    public void renderPlayer (Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        for(int i = 0; i < listPlayer.size(); i++){
            Player tempPlayer = listPlayer.get(i);
            tempPlayer.render(g2d);
        }
    }


    //Thêm 1 bóng vào list Bóng
    public void addBubble(Bubble newBubble){
        this.listBubble.add(newBubble);
    }

    //Thêm 1 player vào list Player
    public void addPlayer(Player newPlayer){
        this.listPlayer.add(newPlayer);
    }


    //Xóa 1 bóng trong list Bóng
    public void removeBubble(Bubble oldBubble){
        this.listBubble.remove(oldBubble);
    }

    //Xóa 1 player vào list Player
    public void removePlayer(Player oldPlayer){
        this.listPlayer.remove(oldPlayer);
    }

}
