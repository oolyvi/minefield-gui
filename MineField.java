package game;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MineField implements MouseListener {
	JFrame frame;
	Buttons[][] board = new Buttons[10][10];
	int openButton;

	public MineField() {
		openButton = 0;

		frame = new JFrame("Mayin Tarlasi");
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(10, 10));

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) { // board'i grid layout add etdik ki her defesinde
																// gridLayout cagirmayaq
				Buttons b = new Buttons(row, col);
				frame.add(b); // mayin buton sahelerini qurduq
				b.addMouseListener(this); // listener elavesi ve this yazdiq cunki eyni classdadir
				board[row][col] = b;
			}
		}

		generateMine(); // mayin yaradan method
		updateCount(); // noqte etrafindaki mayin sayini sayan
		// print(); //duz isleyib islemediyini print'le baxirdiq
		// printMine(); //mayinli bolgeleri gosterir

		frame.setVisible(true);
	}

	public void generateMine() {
		int i = 0;

		while (i < 10) {
			int randRow = (int) (Math.random() * board.length);
			int randCol = (int) (Math.random() * board[0].length);

			while (board[randRow][randCol].isMine()) {
				randRow = (int) (Math.random() * board.length);
				randCol = (int) (Math.random() * board[0].length);
			}
			board[randRow][randCol].setMine(true); // true edib mayin add edirik
			i++;
		}
	} // isMine mayin oldugunu gosterir

	public void print() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col].isMine()) {
					board[row][col].setIcon(new ImageIcon("mine.png"));
				} else { // yoxdursa count yazdir updateCount()
					board[row][col].setText(board[row][col].getCount() + ""); // int string xetasindan qurtulduq +'ile
					board[row][col].setEnabled(false);
				}

			}
		}
	}

	public void printMine() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col].isMine()) {
					board[row][col].setIcon(new ImageIcon("mine.png"));
				}
			}
		}
	}

	public void updateCount() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col].isMine()) {
					counting(row, col); // bu methodu asagida tanimlayiriq
				}
			}
		}
	}

	public void counting(int row, int col) {
		for (int i = row - 1; i <= row + 1; i++) { // setir icindeki saga sola baxmaq
			for (int k = col - 1; k <= col + 1; k++) {
				try {
					int value = board[i][k].getCount(); // en yuxari solda 1 sola gede bilmir deye yazdiq bunu
					board[i][k].setCount(++value);
				} catch (Exception e) {

				}

			}
		}
	}

//bu methodda meselen etrafinda 0 olan mayina basmisansa, onun erazisindeki 0 olan mayin erazisi acilacaq kimi
	public void open(int r, int c) {
		if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c].getText().length() > 0
				|| board[r][c].isEnabled() == false) {
			return; // void'de return yazirsansa kod orada bitir
		} else if (board[r][c].getCount() != 0) {
			board[r][c].setText(board[r][c].getCount() + "");
			board[r][c].setEnabled(false);
			openButton++;
		} else {
			openButton++;
			board[r][c].setEnabled(false);
			open(r - 1, c);
			open(r + 1, c);
			open(r, c - 1);
			open(r, c + 1);
		}

	}

	// interface'in unimplemented methodlari
	@Override
	public void mouseClicked(MouseEvent e) {
		Buttons b = (Buttons) e.getComponent();
		if (e.getButton() == 1) { // sol tik
			System.out.println("Sol tik");
			if (b.isMine()) {
				JOptionPane.showMessageDialog(frame, "Mayina basdiniz Oyun Bitti");
				print(); // oyun bitir deye her seyi acib ortaya tokur
			} else {
				open(b.getRow(), b.getCol());
				if (openButton == board.length * board[0].length - 10) {
					JOptionPane.showMessageDialog(frame, "Tebrikler, oyunu qazandiniz");
					print(); // qazananda her sey ortada gorunur
				}
			}
		} else if (e.getButton() == 3) { // sag tik //2 de orta scroll
			System.out.println("Sag tik");
			if (!b.isFlag()) {
				b.setIcon(new ImageIcon("flag.png"));
				b.setFlag(true); // bayraq oldugunu goster
			} else {
				b.setIcon(null);
				b.setFlag(false);
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
