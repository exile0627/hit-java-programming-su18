/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hit.java.project2018;

import java.applet.Applet;
import java.applet.AudioClip;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TwoplayerTetris1170300914n1170300910 extends JFrame {

    public static void main(String[] args) {
        Music.play("时间飞行.wav");//在JAVA里,写路径的时候不能使用"\",而使用"\\"和"/"来代替
        mainpanel a = new mainpanel();
        a.setVisible(true);
    }
}

//主界面的设置
class mainpanel extends JFrame {

    public mainpanel() {
        JPanel jp = new JPanel();
        jp.setLayout(null);     //使用setbounds控制组件大小位置，需将其布局方式设为null

        JLayeredPane layeredPane = new JLayeredPane();    //创建一个JLayeredPane用于分层的。
        ImageIcon img = new ImageIcon("123.jpg");
        jp.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        // 把背景图片显示在一个标签里面  
        JLabel label = new JLabel(img);    //创建一个Label用于存放图片，作为背景。
        label.setBounds(0, 0, 400, 500);
        jp.add(label);

        //标签设置
        JLabel la = new JLabel("快来一局简单又好玩的游戏吧！");
        la.setBounds(50, 50, 500, 20);
        la.setFont(new Font("黑体", Font.BOLD, 20)); //字体设置（“字体”，样式，字号）
        jp.add(la);

        JButton sg = new JButton("单人游戏");
        sg.setFont(new Font("黑体", Font.PLAIN, 18));
        sg.setBackground(Color.PINK);
        sg.addActionListener(new ActionListener() { //对按钮进行监听
            public void actionPerformed(ActionEvent arg0) {
                Game a = new Game();      //开始新的一局单人游戏
            }
        });
        sg.setBounds(100, 100, 200, 50);      //设置位置大小
        jp.add(sg);

        JButton dg = new JButton("双人游戏");
        dg.setFont(new Font("黑体", Font.PLAIN, 18));
        dg.setBackground(Color.PINK);
        dg.addActionListener(new ActionListener() { //对按钮进行监听
            public void actionPerformed(ActionEvent arg0) {
                Gamedouble a = new Gamedouble();  //开始新的一局双人游戏
            }
        });
        dg.setBounds(100, 200, 200, 50);        //设置位置大小
        jp.add(dg);

        layeredPane.add(jp, JLayeredPane.DEFAULT_LAYER);     //将jp放到最底层。
        layeredPane.add(sg, JLayeredPane.MODAL_LAYER);       //将sg,dg,la放在高一层的地方
        layeredPane.add(dg, JLayeredPane.MODAL_LAYER);
        layeredPane.add(la, JLayeredPane.MODAL_LAYER);

        this.setLayeredPane(layeredPane);  //加入窗口
        this.setSize(400, 500);      //设置大小
        this.setTitle("俄罗斯方块");        //设置标题
        this.setLocation(100, 100);     //设置显示位置
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
//设置双人游戏的窗口

class Gamedouble {

    public Gamedouble() {
        JFrame f = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(null);      //使用setbounds，布局设为null 

        final GamePapel a = new GamePapel(); //新建玩家一的界面
        a.setBounds(110, 10, 400, 500);//设置界面大小，位置
        panel.add(a);
        final GamePapel b = new GamePapel();//新建玩家界面
        b.setBounds(620, 10, 450, 500);//设置大小位置
        panel.add(b);

        a.m = 0;
        a.n = 1;//参数m,n便于将玩家一玩家二分别对应不同按键的监听
        b.m = 1;
        b.n = 0;
        a.timer.start();//游戏开始
        b.timer.start();
        f.addKeyListener(a);//玩家一加入监听
        f.addKeyListener(b);//玩家二加入监听

        //设置菜单栏
        JMenuBar menu = new JMenuBar();
        // help menu
        JMenu helpMenu = new JMenu("帮助");//菜单帮助设置
        JMenuItem newgame = new JMenuItem("新游戏");//新游戏设置
        newgame.addActionListener(new ActionListener() {//加入监听
            public void actionPerformed(ActionEvent e) {//将两玩家的界面初始化
                a.stop = false;
                a.timer.start();
                a.tetris.newMap();
                a.tetris.newTetrimino();
                a.tetris.i=0;
                a.score = 0;
                b.stop = false;
                b.timer.start();
                b.tetris.newMap();
                b.tetris.newTetrimino();
                b.tetris.i=0;
                b.score = 0;
            }
        });
        JMenuItem aboutitem = new JMenuItem("About ");
        aboutitem.addActionListener(new ActionListener() {//加入监听
            public void actionPerformed(ActionEvent e) {
                a.showHelp();//显示帮助对话框
            }
        });

        JMenuItem pause1 = new JMenuItem("玩家一暂停/继续");
        pause1.addActionListener(new ActionListener() {//加入监听
            public void actionPerformed(ActionEvent e) {
                if (!a.pause) {//若pause为false
                    a.pause = true;//更改其值为true
                    a.repaint(); //重新画界面显示暂停界面
                    a.timer.stop();//时间停止
                } else {
                    a.pause = false;
                    a.timer.start();
                }
            }
        });

        JMenuItem pause2 = new JMenuItem("玩家二暂停/继续");
        pause2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!b.pause) {//若pause为false
                    b.pause = true;//更改其值为true
                    b.repaint();//重新画界面显示暂停界面
                    b.timer.stop();//时间停止
                } else {
                    b.pause = false;
                    b.timer.start();
                }
            }
        });

        helpMenu.add(aboutitem);  //将菜单项加入菜单
        menu.add(pause1);  //加入菜单
        menu.add(pause2);
        menu.add(helpMenu);
        menu.add(newgame);
        f.setJMenuBar(menu);  //将菜单栏加入窗口

        //显示玩家一及其操作
        JLabel p1 = new JLabel("玩家一");
        p1.setBounds(30, 200, 90, 30);
        p1.setFont(new Font("黑体", Font.PLAIN, 18));
        panel.add(p1);
        JLabel p11 = new JLabel("wasd操作");
        p11.setFont(new Font("黑体", Font.PLAIN, 18));
        p11.setBounds(20, 250, 90, 30);
        panel.add(p11);

        //显示玩家二及其操作
        JLabel p2 = new JLabel("玩家二");
        p2.setBounds(540, 200, 90, 30);
        p2.setFont(new Font("黑体", Font.PLAIN, 18));
        panel.add(p2);
        JLabel p22 = new JLabel("↑↓←→");
        p22.setBounds(530, 250, 100, 50);
        p22.setFont(new Font("黑体", Font.PLAIN, 18));
        panel.add(p22);

        //设置窗口信息
        f.setTitle("双人游戏"); //设置标题
        f.add(panel);
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);   //设置关闭按钮
        f.setBounds(500, 200, 1000, 520);   //石河子窗口大小位置
        f.setVisible(true);
        f.setResizable(false);
    }
}

//单人游戏窗口设置
class Game {

    public Game() {
        final JFrame frame = new JFrame("Tetris Game");
        final GamePapel a = new GamePapel();
        frame.addKeyListener(a);  //加入键盘监听
        frame.add(a);
        a.timer.start();
        a.m = 1;  //使用上下左右键有效
        a.n = 0;
        //菜单的设置
        JMenuBar menu = new JMenuBar();
        // help menu
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem aboutitem = new JMenuItem("关于");
        aboutitem.addActionListener(new ActionListener() {//加入监听
            public void actionPerformed(ActionEvent e) {
                a.showHelp();
            }
        });
        
        //将菜单加入窗口
        helpMenu.add(aboutitem);
        menu.add(helpMenu);
        frame.setJMenuBar(menu);
        
        //设置窗口信息
        frame.setTitle("单人游戏");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setBounds(500, 100, 400, 520);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

// 创建一个俄罗斯方块类
class GamePapel extends JPanel implements KeyListener {

    int score = 0; // 分数；

    class Tetris {

        int TetriminoType; // 代表方块类型
        Random ran = new Random(); // 使用Random函数；
        int i = 0; //gameover字样显示与否；
        int nextb = ran.nextInt(7); // 下一个方块类型；
        int nextt = ran.nextInt(4); // 下一个方块的形状；
        int rotateState; // 代表方块状态
        int x, y; // 方块起始位置的坐标
        int[][] map = new int[23][13];

        // 第一维代表方块的形状，方块形状类型有S、 Z、 L、 J、 I、 O、 T 7种
        // 第二维代表方块状态
        // 第三维为方块矩阵
        final int shapes[][][] = new int[][][]{
            // 棒型方块
            {{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0}},
            // s型方块
            {{0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}},
            // z型方块
            {{1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}},
            // 右l型方块
            {{0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}},
            // 田型方块
            {{1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},
            // 左l型方块
            {{1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}},
            // t型方块
            {{0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0}}};

        // initialize the map
        public void newMap() {
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 23; j++) {
                    map[j][i] = 0;
                }
            }
            // draw walls
            for (int i = 0; i < 12; i++) {// right-1
                map[21][i] = 2;
            }
            for (int j = 0; j < 22; j++) {// lower-1
                map[j][11] = 2;
                map[j][0] = 2;
            }
        }

        // new Tetrimino
        public void newTetrimino() {
            TetriminoType = nextb;
            rotateState = nextt;
            nextb = ran.nextInt(7);
            nextt = ran.nextInt(4);
            x = 4;
            y = 0;
            if (collisionDetect(x, y, TetriminoType, rotateState)) {
                timer.stop();
                stop = true;
                i = 1;
            }
        }

        // collision Detect
        public boolean collisionDetect(int x, int y, int blockType, int turnState) {
            // a,b为相对坐标(每个形状的每个状态)
            for (int a = 0; a < 4; a++) {
                for (int b = 0; b < 4; b++) {
                    if (((shapes[blockType][turnState][a * 4 + b] == 1) && (map[y + a][x + b + 1] == 1))
                            || ((shapes[blockType][turnState][a * 4 + b] == 1) && (map[y + a][x + b + 1] == 2))) {
                        return true;
                    }
                }
            }
            return false;
        }

        // 旋转的方法
        void rotate() {
            int tempturnState = rotateState;
            rotateState = (rotateState + 1) % 4;
            if (collisionDetect(x, y, TetriminoType, rotateState)) {
                rotateState = tempturnState;
            }
        }

        // 左移的方法
        void left() {
            if (!collisionDetect(x - 1, y, TetriminoType, rotateState)) {
                x = x - 1;
            }
        }

        // 右移的方法
        void right() {
            if (!collisionDetect(x + 1, y, TetriminoType, rotateState)) {
                x = x + 1;
            }
        }

        // 下落的方法
        void fall() {
            if (!collisionDetect(x, y + 1, TetriminoType, rotateState)) {
                y = y + 1;
            }

        }

        //Tetris的clearLines方法：消去积满的行
        void clearLines() {
            int c = 0;//用来记录每一行的块数
            int lines = 0; // 用来累计本次消了几行
            for (int yy = 0; yy < 22; yy++) {//从上至下遍历每一行
                c = 0;//初始化
                for (int xx = 1; xx < 12; xx++) {//横坐标1到11，一行一共10个块
                    if (map[yy][xx] == 1) {//对于确定的yy行而言
                        c = c + 1;//累加
                        if (c == 10) {//若一行10个都满了
                            lines++;
                            for (int cy = yy; cy > 0; cy--) {//从下至上替换，从满行的这一行开始，每一行都由上一行替换
                                for (int e = 1; e < 11; e++) {//一行10个位置的状态都被上一行替换
                                    map[cy][e] = map[cy - 1][e];
                                }
                            }
                        }
                    }
                }//若没满，则判断下一行
            }//消去完成
            switch (lines) {//一次最多消去4行
                case 1:
                    score = score + 10;
                    break;
                case 2:
                    score = score + 40;
                    break;
                case 3:
                    score = score + 90;
                    break;
                case 4:
                    score = score + 160;
                    break;
                default:
                    break;
            }
        }


        //addToMap函数：把当前四格骨牌添加到地图
        void addToMap(int x, int y, int blockType, int turnState) {
            int j = 0;
            for (int a = 0; a < 4; a++) {
                for (int b = 0; b < 4; b++) {
                    if (map[y + a][x + b + 1] == 0) {//若对应的该地图位置为原本为空
                        map[y + a][x + b + 1] = shapes[blockType][turnState][j];//则将该位置状态改为四格骨牌状态
                    }
                    j++;//下一个四格骨牌块
                }
            }
        }

    }

    int m;  //m=1使用键盘上下左右操作有效
    int n;  //n=1使用键盘WASD操作有效
    private static final long serialVersionUID = 1L;
    boolean pause = false;      //设置暂停和停止游戏参数，便于游戏界面在不同状态下的绘制
    boolean stop = false;
    final Timer timer = new Timer(500, new TimerListener());
    Tetris tetris = new Tetris();

    GamePapel() {
        tetris.newTetrimino();
        tetris.newMap();
    }

    public void showHelp() {
        JOptionPane.showMessageDialog(null, "欢迎来到俄罗斯方块游戏！\n点击Esc暂停游戏哦~", "关于",
                JOptionPane.INFORMATION_MESSAGE);    //弹出对话框
    }

    // 画方块的的方法
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        // 画当前方块
        for (int j = 0; j < 16; j++) {
            if ((pause || stop) && tetris.shapes[tetris.TetriminoType][tetris.rotateState][j] == 1) {
                g.setColor(Color.RED);  //当暂停或游戏结束时，当前方块为红色
                g.fill3DRect((j % 4 + tetris.x + 1) * 20, (j / 4 + tetris.y) * 20, 20, 20, true);
            } else if (tetris.shapes[tetris.TetriminoType][tetris.rotateState][j] == 1) {
                // 画矩形区域
                g.setColor(Color.ORANGE); //正常游戏中，当前方块为橙色
                g.fill3DRect((j % 4 + tetris.x + 1) * 20, (j / 4 + tetris.y) * 20, 20, 20, true);

            }
        }
        // 画下一个方块(右侧)
        for (int j = 0; j < 16; j++) {
            if (tetris.shapes[tetris.nextb][tetris.nextt][j] == 1) {
                g.setColor(Color.ORANGE);  //下一个方块为橙色
                g.fill3DRect((j % 4 + 1) * 20 + 250, (j / 4) * 20 + 60, 20, 20, true);

            }
        }
        // 画已经固定的方块
        for (int j = 0; j < 22; j++) {
            for (int i = 0; i < 12; i++) {
                if (tetris.map[j][i] == 2) { // 画围墙
                    g.setColor(Color.LIGHT_GRAY);
                    g.fill3DRect(i * 20, j * 20, 20, 20, true);
                }
                if ((pause || stop) && tetris.map[j][i] == 1) {
                    g.setColor(Color.RED);
                    g.fill3DRect(i * 20, j * 20, 20, 20, true);
                } else if (tetris.map[j][i] == 1) { // 画固定的方块
                    g.setColor(Color.PINK);
                    g.fill3DRect(i * 20, j * 20, 20, 20, true);

                }
            }
        }
        // 设置字体颜色
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.drawString("得分 = " + score, 250, 20);
        // 设置
        g.setColor(Color.RED);
        g.drawString("下一个方块: ", 250, 50);

        if (tetris.i == 1) {    //当游戏结束时，显示GAME OVER字样
            g.setColor(Color.BLACK);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
            g.drawString("GAME OVER", 50, 200);
        }
    }

    //对键盘的的监听设置
    public void keyPressed(KeyEvent e) {//按下某个键时调用此方法。

        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:  //当键盘输入为下
                if (!pause && m == 1) {
                    tetris.fall();
                }
                break;
            case KeyEvent.VK_UP:
                if (!pause && m == 1) {  //当键盘输入为下
                    tetris.rotate();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!pause && m == 1) {  //当键盘输入为右
                    tetris.right();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!pause && m == 1) { //当键盘输入为左
                    tetris.left();
                }
                break;
            case KeyEvent.VK_S:
                if (!pause && n == 1) { //当键盘输入为S
                    tetris.fall();
                }
                break;
            case KeyEvent.VK_W:
                if (!pause && n == 1) { //当键盘输入为W
                    tetris.rotate();
                }
                break;
            case KeyEvent.VK_D:
                if (!pause && n == 1) { //当键盘输入为D
                    tetris.right();
                }
                break;
            case KeyEvent.VK_A:
                if (!pause && n == 1) { //当键盘输入为A
                    tetris.left();
                }
                break;
            case KeyEvent.VK_ESCAPE:    //当键盘输入为Esc 暂停键
                if (!pause) {
                    pause = true;
                    timer.stop();
                } else {
                    pause = false;
                    timer.start();
                }
                break;
            default:
                break;
        }
        repaint();  //重新画界面

    }

    public void keyReleased(KeyEvent e) {  //释放某个键时调用此方法。
    }

    public void keyTyped(KeyEvent e) {      //键入某个键时调用此方法。
    }
    
    //实现定时任务
    class TimerListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {//时间开始时
            tetris.fall();   //方块下落
            if (tetris.collisionDetect(tetris.x, tetris.y + 1, tetris.TetriminoType, tetris.rotateState)) {
                tetris.addToMap(tetris.x, tetris.y, tetris.TetriminoType, tetris.rotateState);
                tetris.clearLines();
                tetris.newTetrimino();
            }
            repaint();
        }
    }
}

class Music {

    static void play(String path) {
        File file = new File(path);//通过将给定路径名字符串转换成抽象路径名来创建一个新File实例。
        URL url;//通过使用URL类，可以经由URL完成读取和修改数据的操作
        try {
            url = file.toURI().toURL();//将file转换成一个链接，可以网络访问
            AudioClip clip = Applet.newAudioClip(url);
            clip.loop();//以循环方式开始播放clip
        } catch (MalformedURLException e) {//URL协议、格式或者路径错误
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
