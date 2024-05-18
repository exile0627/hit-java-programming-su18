package edu.hit.java.gui;

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
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tetrist {

	public static void main(String[] args) {
		Music.play("D:\\2855.wav");
		final JFrame frame = new JFrame("Tetris Game");
		final GamePapel a = new GamePapel();
		frame.addKeyListener(a);
		frame.add(a);
		a.timer.start();
		JMenuBar menu = new JMenuBar();
		// help menu
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutitem = new JMenuItem("About ");
		aboutitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.showHelp();
			}
		});

		helpMenu.add(aboutitem);
		menu.add(helpMenu);
		frame.setJMenuBar(menu);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(300, 100, 400, 520);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}

class Tetris {
	int TetriminoType; // ����������
	Random ran = new Random(); // ʹ��Random������

	int nextb = ran.nextInt(7); // ��һ���������ͣ�
	int nextt = ran.nextInt(4); // ��һ���������״��
	int score = 0; // ������
	int rotateState; // ������״̬
	int x, y; // ������ʼλ�õ�����
	int[][] map = new int[23][13];

	// ��һά���������״��������״������S�� Z�� L�� J�� I�� O�� T 7��
	// �ڶ�ά������״̬
	// ����άΪ�������
	final int shapes[][][] = new int[][][] {
			// ���ͷ���
			{ { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
					{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },
			// s�ͷ���
			{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
			// z�ͷ���
			{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
			// ��l�ͷ���
			{ { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
			// ���ͷ���
			{ { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
			// ��l�ͷ���
			{ { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
			// t�ͷ���
			{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
					{ 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 } } };

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
			JOptionPane.showMessageDialog(null, "Game Over!");
			newMap();
			score = 0;
		}
	}

	// collision Detect
	public boolean collisionDetect(int x, int y, int blockType, int turnState) {
		// a,bΪ�������(ÿ����״��ÿ��״̬)
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

	// ��ת�ķ���
	void rotate() {
		int tempturnState = rotateState;
		rotateState = (rotateState + 1) % 4;
		if (collisionDetect(x, y, TetriminoType, rotateState)) {
			rotateState = tempturnState;
		}
	}

	// ���Ƶķ���
	void left() {
		if ( !collisionDetect(x - 1, y, TetriminoType, rotateState)) {
			x = x - 1;
		}
	}

	// ���Ƶķ���
	void right() {
		if ( !collisionDetect(x + 1, y, TetriminoType, rotateState)) {
			x = x + 1;
		}
	}

	// ����ķ���
	void fall() {
		if ( !collisionDetect(x, y + 1, TetriminoType, rotateState)) {
			y = y + 1;
		}

	}

	// ���еķ���
	void clearLines() {
		int c = 0;
		int lines = 0; // ����ȷ���������˼���
		for (int yy = 0; yy < 22; yy++) {
			c = 0;
			for (int xx = 0; xx < 12; xx++) {
				if (map[yy][xx] == 1) {
					c = c + 1;
					if (c == 10) {
						lines++;
						for (int cy = yy; cy > 0; cy--) {
							for (int e = 1; e < 11; e++) {
								map[cy][e] = map[cy - 1][e];
							}
						}
					}
				}
			}
		}
		// ȷ�����з���
		switch (lines) {
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

	// �ѵ�ǰ���map
	void addToMap(int x, int y, int blockType, int turnState) {
		int j = 0;
		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 4; b++) {
				if (map[y + a][x + b + 1] == 0) {
					map[y + a][x + b + 1] = shapes[blockType][turnState][j];
				}
				j++;
			}
		}
	}

}

// ����һ������˹������
class GamePapel extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	private boolean pause = false;
	final Timer timer = new Timer(500, new TimerListener());
	Tetris tetris = new Tetris();

	GamePapel() {
		tetris.newTetrimino();
		tetris.newMap();
	}

	public void showHelp() {
		JOptionPane.showMessageDialog(null, "Welcole to play Tetris Game!\nEscape Start/Pause\n", "Ablout",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// ������ĵķ���
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		// ����ǰ����
		for (int j = 0; j < 16; j++) {
			if (tetris.shapes[tetris.TetriminoType][tetris.rotateState][j] == 1) {
				// ����������
				g.setColor(Color.BLUE);
				g.fill3DRect((j % 4 + tetris.x + 1) * 20, (j / 4 + tetris.y) * 20, 20, 20, true);

			}
		}
		// ����һ������(�Ҳ�)
		for (int j = 0; j < 16; j++) {
			if (tetris.shapes[tetris.nextb][tetris.nextt][j] == 1) {
				g.setColor(Color.BLUE);
				g.fill3DRect((j % 4 + 1) * 20 + 250, (j / 4) * 20 + 40, 20, 20, true);

			}
		}
		// ���Ѿ��̶��ķ���
		for (int j = 0; j < 22; j++) {
			for (int i = 0; i < 12; i++) {
				if (tetris.map[j][i] == 2) { // ��Χǽ
					g.setColor(Color.BLACK);
					g.fill3DRect(i * 20, j * 20, 20, 20, true);

				}
				if (tetris.map[j][i] == 1) { // ���̶��ķ���
					g.setColor(Color.GREEN);
					g.fill3DRect(i * 20, j * 20, 20, 20, true);

				}
			}
		}
		// ����������ɫ
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		g.drawString("Score = " + tetris.score, 250, 10);
		// ����
		g.setColor(Color.RED);
		g.drawString("Next Tetrimino: ", 250, 30);
	}

	// ���̼���
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			if (!pause) tetris.fall();
			break;
		case KeyEvent.VK_UP:
			if (!pause) tetris.rotate();
			break;
		case KeyEvent.VK_RIGHT:
			if (!pause) tetris.right();
			break;
		case KeyEvent.VK_LEFT:
			if (!pause) tetris.left();
			break;
		case KeyEvent.VK_ESCAPE:
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
		repaint();

	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tetris.fall();
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
		File file = new File(path);
		URL url;
		try {
			url = file.toURL();
			AudioClip clip = Applet.newAudioClip(url);
			// clip.play();
			clip.loop();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}