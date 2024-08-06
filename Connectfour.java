package com.mycompany.connectfour; //if file doesn't work, it's because the package name is different
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class Connectfour extends JPanel implements KeyListener {
 
    static int[][] b = new int[6][7];
    static int scale = 75; //size of the square
    static int shiftX = 200;
    static int shiftY = 100;
 
    static int turn = 1; //whose turn is it?
    static boolean gameover = false;
 
    public static void clear() {
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                b[i][j] = 0;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
 
    }
 
    @Override
    public void keyPressed(KeyEvent e) {
        char letter = e.getKeyChar();
        if (letter == 'r' | letter =='R') {
            clear();
            gameover = false;
        }
        if (gameover) {
            return;
        }
        if (letter >= '1' & letter <= '7') {
            int num = letter - '0';
            if (placeCounter(num)) {
                if (checkWin(num)) {
                    gameover = true;
                }
                changeTurn();
            }
        }
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
 
    }
 
    //method that puts a counter in the board
    public static boolean placeCounter(int col) {
        col--;
        if (b[0][col] != 0) {
            return false;
        }
        int i = 0;
 
        if (b[b.length - 1][col] == 0) {
            b[b.length - 1][col] = turn;
            return true;
        }
        while (b[i][col] == 0) {
            i++;
        }
        b[i - 1][col] = turn;
        return true;
    }
 
    public static void changeTurn() {
        if (turn == 1) {
            turn = 2;
        } else {
            turn = 1;
        }
    }
 
    //    
    //Check if a counter is part of a horizontal win
    public static boolean winH(int r, int c) {
        int counter = 1;
        int current = b[r][c];
        int col = c - 1;
        while (col > -1 && b[r][col] == current) {
            col--;
            counter++;
        }
        col = c + 1;
        while (col < b[r].length && b[r][col] == current) {
            col++;
            counter++;
        }
        return (counter >= 4);
    }
 
    public static boolean winV(int r, int c) {
        int counter = 1;
        int current = b[r][c];
 
        int row = r - 1;
        while (row > -1 && b[row][c] == current) {
            row--;
            counter++;
        }
        row = r + 1;
        while (row < b.length && b[row][c] == current) {
            row++;
            counter++;
        }
        return (counter >= 4);
    }
 
    public static boolean winD1(int r, int c) {
        int counter = 1;
        int current = b[r][c];
 
        int row = r - 1; //going up a piece
        int col = c - 1; //going back a piece
        while (row > -1 && col > -1 && b[row][col] == current) {
            row--;
            col--;
            counter++;
        }
        row = r + 1; // going down a piece
        col = c + 1; // going side a piece
        while (row < b.length && col < b[r].length && b[row][col] == current) {
            row++;
            col++;
            counter++;
        }
        return (counter >= 4);
    }
    
    public static boolean winD2(int r, int c) {
        int counter = 1;
        int current = b[r][c];
 
        int row = r - 1; //going up a piece
        int col = c + 1; //going back a piece
        while (row > -1 && col < 7 && b[row][col] == current) {
            row--;
            col++;
            counter++;
        }
        row = r + 1; // going down a piece
        col = c - 1; // going side a piece
        while (row < b.length && col > -1 && b[row][col] == current) {
            row++;
            col--;
            counter++;
        }
        return (counter >= 4);
    }
 
    public static boolean checkWin(int col) {
        col--;
        int row = 0;
        while (b[row][col] == 0) {
            row++;
        }
        return (winH(row, col) || winV(row, col) || winD1(row, col) || winD2(row, col));
    }
    //
 
    public void drawBoard(Graphics g) {
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                g.setColor(Color.BLACK);
                g.fillRect(shiftX + j * scale, shiftY + i * scale,
                        scale - 1, scale - 1);
                if (b[i][j] == 0) {
                    g.setColor(Color.BLUE);
                } else if (b[i][j] == 1) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }
                g.fillOval(shiftX + 4 + j * scale, shiftY + 4 + i * scale,
                        scale - 9, scale - 9);
            }
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.PLAIN, scale));
        for (int i = 1; i <= 7; i++) {
            g.drawString(i + "", shiftX + (i - 1) * scale + scale / 4, shiftY + scale * (b.length + 1));
        }
        if (gameover) {
            g.drawString("GAME OVER", 250, 80);
        }
    }
 
    //This is where the frame is painted.
    @Override
    public void paint(Graphics g) {
        super.paint(g);//you should always call the super-method first
        //Background
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 1000, 1000);
 
        drawBoard(g);
    }
 
    public static void main(String[] args) {
        //b[3][0] = 1;
        //b[5][5] = 2;
        //b[4][1] = 2;
        Connectfour expo = new Connectfour();
        JFrame f = new JFrame();
 
        f.setVisible(true);
        f.setSize(1000, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(expo);
        f.add(expo);
        //constantly repaint the frame
        while (true) {
            f.repaint();
        }
    }
}