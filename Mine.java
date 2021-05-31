import javax.swing.*;

import java.awt.*;

import java.util.Random;

public class Mine extends JButton {
    private Game game;
    private Random rdm;

    private boolean mine;

    private boolean on;

    public Mine(Game game) {
        this.game = game;

        rdm = new Random();

        on = true;

        if (rdm.nextInt(100) < game.getDifficulty()) {
            mine = true;
        } else {
            mine = false;
        }

        setFont(new Font("Calibri", Font.BOLD, 30));

        setBackground(Color.LIGHT_GRAY);
        setForeground(Color.GREEN);
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(Boolean b) {
        mine = b;
    }

    public void setOn(Boolean b) {
        on = b;
    }

    public Boolean getOn() {
        return on;
    }
}