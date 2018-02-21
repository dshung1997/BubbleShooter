package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



//Hàm này để xử lí toàn bộ thông tin liên quan đến mảng lục giác (tạo ra mảng lục giác để lưu bóng)
public class Hex
{
    //Tọa độ của bóng trên cùng (lưu tọa độ này để còn thêm 1 hàng nữa phía sau)
    public static Point lastBubble;

    //Tọa độ y của của bóng dưới cùng (xử lí thua)
    public static int gameOverPoint;

    //Hàng nằm ở vị trí y = 0 là chẵn hay lẻ ?
    public static boolean isEven = true;

    //Khoảng cách từ đỉnh của bóng : gần đường thẳng y = 0 nhất và bên dưới đường thẳng y = 0;
    public static int curY = 0;

    //Lề trái
    public static int bordersX=0;

    //Xem ảnh hex.PNG để biết 4 biến s,a,r,h
    public static int s=0;
    public static int a=0;
    public static int r=0;
    public static int h=0;

    //Biến rc là khoảng cách để in ra hình quả bóng, sao cho nó nằm chính giữa của hình lục giác
    public static int rc=0;

    //Set lề trái
    public static void setBORDERS(int X){
        bordersX = X;
        curY = 0;
    }


    //Set kích thước chung cho 1 hình lục giác
    public static void setSize(int size) {
        a = size;
        r = a/2;
        s = (int) (a / 1.73205);
        h = s/2;
        rc = (int) (s * 0.866025403);

        //Khởi tạo 2 biến này thôi :v
        lastBubble = new Point(bordersX + r,0);
        gameOverPoint = -42 + 2*s;
    }

    //Tạo hình chữ nhật bound xung quanh hình lục giác dựa trên tọa độ đỉnh
    public static Rectangle createRect (Point p){
        int x = p.x - r - 2 ;
        int y = p.y - 2;
        return new Rectangle(x, y, a + 4, 2*s + 4);
    }

    //Tạo hình lục giác dựa trên tọa độ đỉnh
    public static Polygon creatHex (Point p) {
        int x = p.x;
        int y = p.y;

        int[] cx,cy;
        cx = new int[] {x,  x+r,    x+r,    x,          x-r,    x-r};
        cy = new int[] {y,  y+h,    y+h+s,  y+h+s+h,    y+h+s,  y+h};

        return new Polygon(cx,cy,6);
    }

    //Vẽ bóng dựa trên tọa độ đỉnh và màu
    public static void drawHex(Point p, int color, Graphics2D g2d) {

        BufferedImage bb = null;
        BufferedImage bound = null;
        try {
            bb = ImageIO.read(new File("res/bu"+color+".png"));
//            bound = ImageIO.read(new File("res/bound.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Polygon poly = creatHex(p);
//        g2d.drawPolygon(poly);
        g2d.drawImage(bb, p.x - rc , p.y + (s-rc)-1 , null);
//        g2d.drawImage(bound, p.x - rc - 5 , p.y + (s-rc)-1 - 5, null);
    }

    //Lấy tọa độ của điểm trên màn hình, chuyển thành tọa độ trong mảng 2 chiều khi hàng đầu tiên là hàng lẻ
    public static Point getGridPositionOdd(Point p) {

        int mx = (int) p.getX();
        int my = (int) p.getY();

        //Trừ bớt lề trái và lề trên(curY kiểu kiểu như lề trên)
        mx -= bordersX;
        my -= curY;

        //Lấy tọa độ tạm thời của điểm đó trên mảng 2 chiều. Chia khung hình hiện tại thành các ô (s+h)*a; xem ảnh odd.png hoặc even.png để hiểu hơn
        int y = (int) (my / (s+h));
        int x = (int) (mx / a);

        //Khoảng cách từ điểm hiện tại tới đường biên gần nhất
        int dx = mx - x*a;
        int dy = my - y*(s+h);

        //Đoạn này thì chỉ có giải thích trực tiếp thôi
        if (y % 2 == 0) {

            if (dx > r) {
                if (dx * h + dy * r < 2 * h * r) {
                    y--;
                }
            }
            if (dx < r) {
                if (dy * r < dx * h) {
                    y--;
                } else if (dy * r > dx * h) {
                    x--;
                }
            }

        } else {

            if (dx > r) {
                if (dy * r < h * dx - h * r) {
                    y--;
                }
            }
            if (dx < r) {
                if (dx * h + dy * r < h * r) {
                    y--;
                    x--;
                }
            }

        }

        return new Point(y, x);
    }


    //Lấy tọa độ của điểm trên màn hình, chuyển thành tọa độ trong mảng 2 chiều khi hàng đầu tiên là hàng chẵn
    public static Point getGridPositionEven(Point p) {

        int mx = (int) p.getX();
        int my = (int) p.getY();

        mx -= bordersX;
        my -= curY;

        int y = (int) (my / (s+h));
        int x = (int) (mx / a);

        int dx = mx - x*a;
        int dy = my - y*(s+h);

        if (y % 2 == 0) {

            if (dx > r) {
                if (dy * r < h * dx - h * r) {
                    y--;
                }
            }
            if (dx < r) {
                if (dx * h + dy * r < h * r) {
                    y--;
                    x--;
                }
            }
        } else {

            if (dx > r) {
                if (dx * h + dy * r < 2 * h * r) {
                    y--;
                }
            }
            if (dx < r) {
                if (dy * r < dx * h) {
                    y--;
                } else if (dy * r > dx * h) {
                    x--;
                }
            }

        }

        return new Point(y, x);
    }

    //Nếu hàng đầu chẵn lẻ thì xử lí sao ?
    public static Point getGridPosition(Point p){
        if(isEven) {
            return getGridPositionEven(p);
        }
        else {
            return getGridPositionOdd(p);
        }
    }

    //Trả về tọa độ của đỉnh 1 bóng dựa trên tọa độ trong mảng 2 chiều
    public static Point getVertexFromGrid(int i, int j){
        if (isEven) return new Point((j + 1) * a - ((i + 1) % 2) * r + bordersX, +i * (s + h) + curY);
        else return new Point((j + 1) * a - ((i) % 2) * r + bordersX, +i * (s + h) + curY);
    }

    //Lấy tọa độ tâm từ tọa độ đỉnh
    public static Point getCenterFromVertex(Point p){
        return new Point(p.x, p.y+s);
    }

    //Lấy tọa độ đỉnh từ tọa độ tâm
    public static Point getVertexFromCenter(Point p){
        return new Point(p.x, p.y-s);
    }

    //Quan sát sự thay đổi của CurY
    public static void viewCurY(){

        //2 biến này tăng lên 1 pixel sau mỗi lần update
        lastBubble.y++;
        gameOverPoint++;

        //Xử lí thua hay không sau mỗi lần bóng đã chạm
        if(!Process.isFlying){
            if(gameOverPoint >= (505)) Process.gameOver = true;
        }

        //Tăng curY theo update
        curY++;

        //Trả về curY mới khi nó vượt qua (s+h), (s+h) là độ rộng của 1 hàng trong mảng 2 chiều trên khung màn hình
        if(curY >= (s+h)){
            curY = curY % (s+h) ;
            if(isEven) isEven = false;
            else isEven = true;
        }
    }

}