import javax.swing.*;

import java.awt.*;
//import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class Game implements MouseListener {

    private final int WIDTH = 700;
    private final int LENGTH = 700;

    private final int COLUMNS = 10;
    private final int ROWS = 10;

    private int difficulty;

    private JFrame frame;
    private JPanel mineFrame;

    private Mine[][] mines = new Mine[ROWS][COLUMNS];

    private boolean flag = false;

    private boolean firstClick;

    public Game() {
        frame = new JFrame("Minesweeper");

        initializeDifficulty();
        initializeMines();
        initializeFrame();
    }

    private void initializeFrame() {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(mineFrame, BorderLayout.NORTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void initializeDifficulty() {
        firstClick = true;

        difficulty = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter difficulty from 1 - 100", "Minesweeper",
                JOptionPane.INFORMATION_MESSAGE));

        if (difficulty > 80) {
            difficulty = 80;
        }

        if (difficulty < 1) {
            difficulty = 1;
        }
    }

    private void initializeMines() {
        mineFrame = new JPanel();

        mineFrame.setLayout(new GridLayout(ROWS, COLUMNS));
        mineFrame.setPreferredSize(new Dimension(WIDTH, LENGTH));

        for (int i = 0; i < ROWS; i++) {
            for (int h = 0; h < COLUMNS; h++) {
                mines[i][h] = new Mine(this);

                mines[i][h].setEnabled(true);
                // mines[i][h].addActionListener(this);
                mines[i][h].addMouseListener(this);

                mineFrame.add(mines[i][h]);
            }
        }
    }

    private Integer checkSurroundings(int x, int y) {
        Integer surroundingMines = 0;
        int startX;
        int startY;
        int endX;
        int endY;

        if (x == 0) {
            startX = x;
            endX = x + 1;
        } else if (x == ROWS - 1) {
            startX = x - 1;
            endX = x;
        } else {
            startX = x - 1;
            endX = x + 1;
        }

        if (y == 0) {
            startY = y;
            endY = y + 1;
        } else if (y == ROWS - 1) {
            startY = y - 1;
            endY = y;
        } else {
            startY = y - 1;
            endY = y + 1;
        }

        for (int i = startX; i < endX + 1; i++) {
            for (int h = startY; h < endY + 1; h++) {
                if (i != x || h != y) {
                    if (mines[i][h].isMine()) {
                        surroundingMines++;
                    }
                }
            }
        }

        return surroundingMines;
    }

    private void firstAction(int i, int h) {
        mines[i][h].setMine(false);

        mines[i][h].setBackground(Color.GRAY);
        mines[i][h].setText(checkSurroundings(i, h).toString());
        mines[i][h].setOn(false);

        firstClick = false;

    }

    // private void action(ActionEvent e) {
    // for (int i = 0; i < COLUMNS; i++) {
    // for (int h = 0; h < ROWS; h++) {
    // if (e.getSource() == mines[i][h] && mines[i][h].getOn()) {
    // doAction(i, h);
    // }
    // }
    // }
    // }

    private void doAction(int i, int h) {
        if (!flag) {
            if (mines[i][h].isMine()) {
                mines[i][h].setBackground(Color.RED);
                mines[i][h].setOn(false);
                lose();
            } else {
                mines[i][h].setBackground(Color.GRAY);
                mines[i][h].setText("" + checkSurroundings(i, h).toString());
                mines[i][h].setOn(false);
                checkWin();
            }
        } else {
            if (mines[i][h].getBackground() == Color.LIGHT_GRAY) {
                mines[i][h].setBackground(Color.CYAN);
            } else {
                mines[i][h].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    private void lose() {
        if (JOptionPane.showConfirmDialog(frame, "You lose. Would you like to reset") == JOptionPane.YES_OPTION) {
            frame.dispose();
            frame = null;
            frame = new JFrame();

            initializeDifficulty();
            initializeMines();
            initializeFrame();
        } else {
            frame.dispose();
        }
    }

    private void checkWin() {
        int count = 0;

        for (int i = 0; i < ROWS; i++) {
            for (int h = 0; h < COLUMNS; h++) {
                if (!mines[i][h].getOn() || mines[i][h].isMine()) {
                    count++;
                }
            }
        }

        if (count >= (ROWS * COLUMNS)) {
            if (JOptionPane.showConfirmDialog(frame, "You Win. Would you like to reset") == JOptionPane.YES_OPTION) {
                frame.dispose();
                frame = null;
                frame = new JFrame();

                initializeDifficulty();
                initializeMines();
                initializeFrame();
            } else {
                frame.dispose();
            }
        }
    }

    // @Override
    // public void actionPerformed(ActionEvent e) {
    // if (firstClick) {
    // firstAction(e);
    // } else {
    // action(e);
    // checkFlag(e);
    // }
    // }

    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            flag = true;
        } else {
            flag = false;
        }

        for (int i = 0; i < COLUMNS; i++) {
            for (int h = 0; h < ROWS; h++) {
                if (e.getSource().equals(mines[i][h])) {
                    if (firstClick) {
                        firstAction(i, h);
                        firstClick = false;
                    } else {
                        doAction(i, h);
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}