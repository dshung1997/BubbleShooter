package com.company;

import java.awt.*;

/**
 * Created by dshunggggg on 29/09/2016.
 */

//Kế thừa từ GameObject
public class Bubble extends GameObject {

    public Bubble(Point p, int color){super(p, color);}

    //Di chuyển xuống 1 pixel sau mỗi lần update
    public void update(){
        vertex.y += 1;
        center.y += 1;
    }

    @Override
    public void render(Graphics2D g2d){
//
//        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
//        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
//        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        //In ra hình của bóng
        Hex.drawHex(vertex, color, g2d);

        if(isRemoved){
            g2d.dispose();
        }
    }

}
