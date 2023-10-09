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
    public static final String LABEL_CHOICE_MODE = "Выберите режим игры";
    public static final String BTN_HUMAN_VERSUS_AI = "Человек против компьютера";
    public static final String BTN_HUMAN_VERSUS_HUMAN = "Человек против человека";
    public static final String SIZE_PREFIX = "Установленный размер поля: ";
    public static final String WIN_LENGTH_PREFIX = "Установленная длина: ";
    public static final String LABEL_CHOICE_SIZE = "Выберите размеры поля";
    public static final String LABEL_CHOICE_WIN_LENGTH = "Выберите длину для победы";
    public static final int MODE_HVA = 0;
    public static final int MODE_HVH = 1;

    public static final int MIN_SIZE = 3;
    public static final int MAX_SIZE = 10;

    private final GameWindow gameWindow;
    private JButton btnStart, btnExit;
    private  JRadioButton btnHVH, btnHVA;
    private Label lblMode, lblSize, lblSizePrefix, lblWinCount, lblWinCountPrefix;
    private JSlider sliderSize, sliderWin;

    StartWindow(GameWindow gameWindow){
        super("TIC-TAC-TOE: Start");
        this.gameWindow = gameWindow;
        setLocation(gameWindow.getX(), gameWindow.getY());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new GridLayout(3,1));
        mainPanel.add(constructModePanel());
        mainPanel.add(constructSizePanel());
        mainPanel.add(constructWinCountPanel());
        add(constructStartPanel(), BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
        setSize(gameWindow.getWidth(), gameWindow.getHeight());
        setResizable(false);
    }

    private void initStartPanelComponents(){
        btnStart = new JButton(START_GAME);
        btnExit = new JButton(EXIT_GAME);

        btnExit.addActionListener(e -> System.exit(0));
        btnStart.addActionListener(e -> startGame());
    }
    private void initModePanelComponents(){
        btnHVA = new JRadioButton(BTN_HUMAN_VERSUS_AI);
        btnHVH = new JRadioButton(BTN_HUMAN_VERSUS_HUMAN);
        lblMode = new Label(LABEL_CHOICE_MODE);
    }
    private void initSizePanelComponents(){
        lblSize = new Label(LABEL_CHOICE_SIZE);
        lblSizePrefix = new Label(SIZE_PREFIX+MIN_SIZE+"X"+MIN_SIZE);
        sliderSize = new JSlider(MIN_SIZE, MAX_SIZE, MIN_SIZE);
        sliderSize.addChangeListener(l -> {
            int value = sliderSize.getValue();
            lblSizePrefix.setText(SIZE_PREFIX+value+"X"+value);
            sliderWin.setMaximum(value);
        });
    }
    private void initWinCountPanelComponents(){
        lblWinCount = new Label(LABEL_CHOICE_WIN_LENGTH);
        lblWinCountPrefix = new Label(WIN_LENGTH_PREFIX+sliderSize.getValue());
        sliderWin = new JSlider(MIN_SIZE, sliderSize.getValue());
        sliderWin.addChangeListener(l ->lblWinCountPrefix.setText(WIN_LENGTH_PREFIX+sliderWin.getValue()));
    }
    private Component constructStartPanel(){
        JPanel panelStart = new JPanel(new GridLayout(1, 2));
        initStartPanelComponents();
        panelStart.add(btnStart);
        panelStart.add(btnExit);
        return panelStart;
    }
    private Component constructModePanel(){
        JPanel panelMode = new JPanel(new GridLayout(3, 1));
        initModePanelComponents();
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(btnHVH);
        modeGroup.add(btnHVA);
        btnHVA.setSelected(true);
        panelMode.add(lblMode);
        panelMode.add(btnHVA);
        panelMode.add(btnHVH);
        return panelMode;
    }
    private Component constructSizePanel(){
        JPanel panelSize = new JPanel(new GridLayout(3, 1));
        initSizePanelComponents();
        panelSize.add(lblSize);
        panelSize.add(lblSizePrefix);
        panelSize.add(sliderSize);
        return panelSize;
    }
    private Component constructWinCountPanel(){
        JPanel panelWinCount = new JPanel(new GridLayout(3,1));
        initWinCountPanelComponents();
        panelWinCount.add(lblWinCount);
        panelWinCount.add(lblWinCountPrefix);
        panelWinCount.add(sliderWin);
        return panelWinCount;
    }
    private void startGame(){
        setVisible(false);
        int size = sliderSize.getValue();
        GameLogic.setCellCountX(size);
        GameLogic.setCellCountY(size);
        GameLogic.setWinNumber(sliderWin.getValue());
        gameWindow.constructGamePanel();
        gameWindow.setVisible(true);
    }
}
