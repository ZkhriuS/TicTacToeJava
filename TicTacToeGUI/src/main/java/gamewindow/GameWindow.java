package gamewindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ContainerAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameWindow extends JFrame {
    private final StartWindow startWindow;
    private GamePanel gamePanel;
    public GameWindow(int height, int width, int posX, int posY){
        super("TIC-TAC-TOE: Game");
        setSize(width, height);
        setLocation(posX, posY);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        startWindow = new StartWindow(this);
        constructGamePanel();
    }
    private void initComponents(){
        gamePanel = new GamePanel(this);
    }
    private void constructGamePanel(){
        initComponents();
        add(gamePanel);

    }

}
