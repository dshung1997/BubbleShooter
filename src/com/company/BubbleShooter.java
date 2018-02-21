package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/* Class này để xử lí luồng, in hình ảnh và vòng lặp game */

public class BubbleShooter extends Canvas implements Runnable {

//    public enum STATE{
//        MENU,
//        GAME
//    }

    //public static STATE state = STATE.MENU;

    //Chiều dài, chiều rộng của frame
    public static final int WIDTH = 440, HEIGHT = 720;

    //Luồng
    private Thread thread;

    //Biến này quyết định vòng lặp
    private boolean running = false;

    //Khai báo các ảnh
    BufferedImage bodyGun = ImageIO.read(new File("res/bodyGun.png"));
    BufferedImage headGun = ImageIO.read(new File("res/headGun.png"));
    BufferedImage backgroundGame = ImageIO.read(new File("res/backgroundGame.png"));
    BufferedImage top = ImageIO.read(new File("res/top.png"));
    BufferedImage gameover = ImageIO.read(new File("res/gameOver.png"));


    private Handler handler;

    //Tạo 1 font mới
    private Font font0;

    public BubbleShooter() throws IOException {

        font0 = new Font("Berlin Sans FB", Font.BOLD, 30);

        handler = new Handler();

        JFrame frame = new Frame(WIDTH, HEIGHT, "Bubble :)", this);

        //Tạo 6 hàng bóng
        Process.addOneRow(handler);
        Process.addOneRow(handler);
        Process.addOneRow(handler);
        Process.addOneRow(handler);
        Process.addOneRow(handler);
        Process.addOneRow(handler);


        //Tạo bóng của người chơi
        Process.newPlayer(handler);

        //Thêm sự kiện của mouse
        MouseInput ml = new MouseInput(handler);
        addMouseListener(ml);
    }

    //Khởi tạo thread và bắt đầu vòng lặp
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    //Dừng thread và kết thúc
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        } catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    //Hàm này có chức năng thực hiện vòng lặp , thực hiện update và render trong 1 khoảng thời gian dựa trên các biến khai báo ở dưới...
    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 20.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running == true){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1){
                update();
                delta--;
            }

            if(running){
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
            }

            if(Math.abs( Hex.lastBubble.y + 10) <= 1){
                Process.addOneRow(handler);
            }
            System.out.println(Hex.gameOverPoint);
        }
        stop();
    }

    //Hàm update có chức năng xử lí các thông số
    private void update(){
        if(!Process.gameOver)
        {
            handler.updateBubble();
            handler.updatePlayer();
            Hex.viewCurY();
        }
    }

    //Hàm này có chức năng xử lí hình ảnh dựa trên các thông số đã đc update ở hàm update bên trên
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        if(!Process.gameOver) {

            //Vẽ ảnh nền
            g.drawImage(backgroundGame, 0, 0, this);

            //Vẽ ảnh đầu súng
            g.drawImage(headGun, 185, 503, this);

            handler.renderBubble(g);
            handler.renderPlayer(g);

            g.drawImage(bodyGun, 142, 553, this);

            Graphics2D g2d = (Graphics2D) g;
            for (int i = 0; i < 4; i++) {
                Hex.drawHex(new Point(220 + i * 60, 630), Process.waitingBubbles[i], g2d);
            }

            g.drawImage(top, 0, 0, this); //Create an image to hide some part of bubbleInGrid grid.

            //Set font mặc định cho lần dùng tới
            g.setFont(font0);

            //Set màu
            g.setColor(new Color(0xCFE9F5));

            //In điểm
            g.drawString(String.valueOf(Process.score), 30, 650);
//        }
//        else {
//            menu.render(g);
//        }
        }
        else{
            g.drawImage(gameover, 70, 300, this);
        }
        g.dispose();
        bs.show();

    }

    public static void main(String[] args) throws IOException {

        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                Hex.setBORDERS(1);
                Hex.setSize(50);
                try {
                    new BubbleShooter();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
