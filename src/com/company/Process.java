package com.company;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by dshunggggg on 05/10/2016.
 */
public class Process {

    //Điểm
    public static int score = 0;

    //Thua hay ko ?
    public static boolean gameOver = false ;

    //Bóng đang bay hay ko ?
    public static boolean isFlying = false;

    static Random random = new Random();

    //Tạo 1 mảnh bóng đang đợi dựa trên màu
    public static int[] waitingBubbles = {random.nextInt(5)+1, random.nextInt(5)+1, random.nextInt(5)+1, random.nextInt(5)+1};

    //Mảng bóng mà nó gắn với gốc
    public static int[] solidBubble;

    //Số bóng của mảng bên trên
    public static int iSolid;


    //Mảng bóng sẽ bị xóa
    public static int[] willBeRemoved;
    //Số bóng của mảng trên
    public static int iWillBeRemoved;

    //Mảng 2 chiều chứa các bóng thuộc vào màu nào (5 màu : 1-5)
    public static int[][] bubbleColor;

    //Đưa bóng vào mảng màu, cũng như đưa bóng vào mảng solid
    public static void findSameColor(Handler handler){

        //Khởi tạo
        bubbleColor = new int[6][200];

        //Set toàn bộ phần tử của màng màu là -1
        for(int i = 1; i < 6; i++){
            Arrays.fill(bubbleColor[i], -1);
        }

        //Ban đầu số bóng gắn với gốc  = 0
        iSolid = 0;
        //Khởi tạo
        solidBubble = new int[200];
        //Set toàn bộ phần tử của mảng là -1
        Arrays.fill(solidBubble, -1);

        //Số bóng trong mảng màu
        int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0;

        for(int i = 0; i < handler.listBubble.size(); i++){
            Bubble tempBubble = handler.listBubble.get(i);

            //Cho toàn bộ các bóng trong mảng là bỏng nổi, sau đó, lấy bóng của hàng trên cùng là gốc, bóng nào giao với nó thì chuyển thành true, những bóng mà vẫn false thì đương nhiên là bóng nổi.
            tempBubble.isSolid = false;

            //Tìm các bóng trên cùng và set giá trị isSolid = true
            if(Math.abs(tempBubble.vertex.y - Hex.lastBubble.y ) <= 2){
                if(!contains(solidBubble, i)){
                    solidBubble[iSolid] = i;
                    iSolid++;
                    tempBubble.isSolid = true;
                }
            }

            //Xử lí màu của bóng đang xét, thêm vào mảng màu tương ứng và tăng số đếm của mảng đó
            int c = tempBubble.color;
            switch (c){
                case 1 : {
                    if(!contains(bubbleColor[1], i)) {
                        bubbleColor[1][i1] = i;
                        i1++;
                    }
                    break;
                }

                case 2 : {
                    if(!contains(bubbleColor[2], i)) {
                        bubbleColor[2][i2] = i;
                        i2++;
                    }
                    break;
                }

                case 3 : {
                    if(!contains(bubbleColor[3], i)) {
                        bubbleColor[3][i3] = i;
                        i3++;
                    }
                    break;
                }

                case 4 : {
                    if(!contains(bubbleColor[4], i)) {
                        bubbleColor[4][i4] = i;
                        i4++;
                    }
                    break;
                }

                case 5 : {
                    if(!contains(bubbleColor[5], i)) {
                        bubbleColor[5][i5] = i;
                        i5++;
                    }
                    break;
                }
            }

        }

        //Sắp xếp mảng từ lớn -> bé
        for(int i = 1; i < 6; i++){
            bubbleColor[i] = sortArray(bubbleColor[i]);
        }
        solidBubble = sortArray(solidBubble);
    }

    //Tìm các bóng cùng màu với bóng đang xét, dựa trên tạo độ của bóng trong list Bóng (listBubble) và màu của nó
    public static void findClusters(Handler handler, int index, int color){
        //Khởi tạo
        willBeRemoved = new int[200];
        iWillBeRemoved = 0;
        Arrays.fill(willBeRemoved, -1);

        Bubble mainBubble = handler.listBubble.get(index);
        for(int i = 0; i < bubbleColor[color].length; i++){

            //Vì đã sắp xếp giá trị của mảng rồi nên khi gặp -1 nghĩa là đã hết -> break;
            if(bubbleColor[color][i] == -1) break;

            Bubble tempBubble = handler.listBubble.get(bubbleColor[color][i]);

            //Xét va chạm giữa 2 quả bóng cùng màu đang xét, nếu va chạm thì thêm vào mảng bóng sẽ bị xóa
            if(Hex.createRect(tempBubble.vertex).getBounds2D().intersects(Hex.createRect(mainBubble.vertex).getBounds2D())){
                if(!contains(willBeRemoved, bubbleColor[color][i])){
                    willBeRemoved[iWillBeRemoved] = bubbleColor[color][i];
                    iWillBeRemoved++;
                }
            }
        }

        //Sau khi tạo đc mảng các bóng cùng màu ,  thì xét các bóng cùng màu mà giao với các bóng trong đó.
        int j = 0;
        do{
            Bubble mBubble = handler.listBubble.get(willBeRemoved[j]);
            for(int i = 0; i < bubbleColor[color].length; i++){
                if(bubbleColor[color][i] == -1) break;
                Bubble tBubble = handler.listBubble.get(bubbleColor[color][i]);
                if(Hex.createRect(mBubble.vertex).getBounds().intersects(Hex.createRect(tBubble.vertex).getBounds())){
                    if(!contains(willBeRemoved, bubbleColor[color][i])){
                        willBeRemoved[iWillBeRemoved] = bubbleColor[color][i];
                        iWillBeRemoved++;
                    }
                }
            }
            j++;
        }while(willBeRemoved[j] != -1);

        //Sau vòng lặp trên, nếu số bóng trong mảng bóng sẽ bị xóa mà  >3 thì bắt đầu công việc xóa
        if(iWillBeRemoved >= 3){

            //Tìm các bóng gắn với gốc
            findSolidBubbles(handler);

            //Những bóng mà ko có trong mảng bóng gắn với gốc và chưa có trong mảng bóng sẽ bị xóa thì đương nhiên phải thêm vào mảng bóng sẽ bị xóa
            for(int i = 0; i < handler.listBubble.size(); i++){
                if(!contains(solidBubble, i) && !contains(willBeRemoved, i)){
                    willBeRemoved[iWillBeRemoved] = i;
                    iWillBeRemoved++;
                }
            }

            //Sắp xếp
            willBeRemoved = sortArray(willBeRemoved);

            //Xóa và cộng điểm
            for(int i = 0; i < iWillBeRemoved; i++){
                score += 500;
                Bubble temp = handler.listBubble.get(willBeRemoved[i]);
                temp.isRemoved = true;
                handler.removeBubble(temp);
            }
        }

        //Xác lập lại tọa độ thua
        Hex.gameOverPoint = findMaxY(handler);
    }

    //Bóng nào giao với bóng mà có isSolid = true thì cũng chuyển sang true
    public static void findSolidBubbles(Handler handler){

        for(int i = 0; i < iSolid; i++){

            if(solidBubble[i] == -1) break;
            if(contains(willBeRemoved, solidBubble[i])) continue;

            Bubble temp1 = handler.listBubble.get(solidBubble[i]);

            for(int j = 0; j < handler.listBubble.size(); j++){

                if(solidBubble[i] == j) continue;
                if(contains(willBeRemoved, j)) continue;

                Bubble temp2 = handler.listBubble.get(j);

                if((Hex.createRect(temp1.vertex).getBounds().intersects(Hex.createRect(temp2.vertex).getBounds()))  ){
                    temp2.isSolid = true;
                    if(!contains(solidBubble, j)){
                        solidBubble[iSolid] = j;
                        iSolid++;
                    }
                }
            }
        }
    }


    //Sắp xếp mảng cho trước theo từ lớn đến bé
    public static int[] sortArray(int[] arr){
        for(int k = 0; k < arr.length-1; k++){
            for(int l = k+1; l < arr.length; l++){
                if(arr[l] > arr[k]){
                    int temp = arr[l];
                    arr[l] = arr[k];
                    arr[k] = temp;
                }
            }
        }

        return arr;
    }

    //Xét xem mảng array có chứa phần tử m hay ko ?
    public static boolean contains(int[] array, int m) {
        boolean contains = IntStream.of(array).anyMatch(x -> x == m);
        return contains;
    }

    //Thêm 1 bóng vào tọa độ p
    public static Bubble addOneBubble(Point p){
        int color = random.nextInt(5)+1;
        Bubble nb = new Bubble(p, color);
        return nb;
    }

    //Thêm 1 hàng bóng vào phía trên của mảng bóng
    public static void addOneRow(Handler handler){
        Point p = Hex.lastBubble;

        if(Hex.lastBubble.x == (Hex.bordersX + Hex.r)){
            p.x += Hex.r;
        }
        else p.x -= Hex.r;
        p.y -= (Hex.s + Hex.h);
        for(int i = 0; i < 8; i++){
            handler.addBubble(addOneBubble(new Point(p.x + i*(Hex.a), p.y)));
        }

        Hex.lastBubble = new Point(p.x , p.y);
    }

    //Tạo 1 người chơi mới ở 1 vị trí cố định, màu thì luôn lấy màu của waitingBubble[0]
    public static void newPlayer(Handler handler){
        Point np = new Point(BubbleShooter.WIDTH / 2, 570);
        Player player = new Player(np, waitingBubbles[0], handler);
        handler.addPlayer(player);
    }

    //Gắn bóng vào mảng.
    public static void snapBubble(Handler handler, Point p, int color){
        Point pGrid = Hex.getGridPosition(p);
        Point pVertex = Hex.getVertexFromGrid(pGrid.x, pGrid.y);
        Bubble a = new Bubble(pVertex, color);
        handler.addBubble(a);
        int index = handler.listBubble.indexOf(a);
        Process.findSameColor(handler);
        Process.findClusters(handler, index, color);
        isFlying = false;
    }

    //Làm mới mảng bóng đợi, sau khi bóng player bắn đi thì xóa phần tử [0], dịch cả mảng lên (5 -> 4, 4 -> 3 ,...), set giá trị mới cho phần tử [5]
     public static void renewWaitingBubble(){
        for(int i = 0; i < waitingBubbles.length - 1; i++){
            waitingBubbles[i] = waitingBubbles[i+1];
        }
        waitingBubbles[waitingBubbles.length - 1] = random.nextInt(5) + 1;
    }

    //Tìm tọa độ Y lớn nhất, xét trong các đỉnh của các bóng trong mảng, trả về 1 điểm thua =  giá trị kia + 2*hex.s, s là gì thì xem lại trong class Hex
    public static int findMaxY(Handler handler){
        int max = 0;
        for(int i = 0; i < handler.listBubble.size(); i++){
            Bubble temp = handler.listBubble.get(i);
            if(max < temp.vertex.y) max = temp.vertex.y;
        }
        max += 2*(Hex.s);
        return max;
    }
}
