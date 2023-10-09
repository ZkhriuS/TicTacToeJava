package gamewindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentListener;

public class StartWindow extends JFrame {
    private static final String START_GAME = "Новая игра";
    private static final String EXIT_GAME = "Выйти из игры";

    private GameWindow gameWindow;
    private JPanel panelStart;
    private JButton btnStart, btnExit;
    StartWindow(GameWindow gameWindow){
        super("TIC-TAC-TOE: Start");
        this.gameWindow = gameWindow;
        setSize(gameWindow.getWidth(), gameWindow.getHeight());
        setLocation(gameWindow.getX(), gameWindow.getY());
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        constructStartPanel();
    }

    private void initComponents(){
        panelStart = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton(START_GAME);
        btnExit = new JButton(EXIT_GAME);

        btnExit.addActionListener(e -> System.exit(0));
        btnStart.addActionListener(e -> startGame());
    }
    private void constructStartPanel(){
        initComponents();
        add(panelStart, BorderLayout.SOUTH);
        panelStart.add(btnStart);
        panelStart.add(btnExit);
    }
    private void startGame(){
        setVisible(false);
        gameWindow.setVisible(true);
    }
}
