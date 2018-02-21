package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by dshunggggg on 16/09/2016.
 */
public class Frame extends JFrame{
    BufferedImage newgameBtn = ImageIO.read(new File("res/newgameBtn.png"));

    public Frame(int width, int height, String title, BubbleShooter bb) throws IOException {

        //Frame trông đẹp hơn
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Khởi tạo 1 frame mới có tên là title
        JFrame frame = new JFrame(title);

        //Tạo nút exit
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set size cho frame
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));


        //Có thể thay đổi kích thước frame hay ko ?
        frame.setResizable(false);

        //Đặt vị trí của frame ở giữa màn hình
        frame.setLocationRelativeTo(null);

        //Thêm các bb của BubbleShooter vào màn hình
        frame.add(bb);

        //Có dòng này thì mới nhìn thấy frame và các thứ bên trong
        frame.setVisible(true);

        //Khởi tạo bb
        bb.start();
    }
}
