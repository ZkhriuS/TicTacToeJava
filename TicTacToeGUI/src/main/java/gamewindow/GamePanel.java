package gamewindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static gamewindow.GameState.GAME;

public class GamePanel extends JPanel {
    private final int PADDING = 20;

    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW = "Ничья!";
    private int cellSizeX, cellSizeY;
    private final GameWindow gameWindow;
    GamePanel(GameWindow gameWindow){
        super();
        this.gameWindow = gameWindow;
        GameLogic.initField();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(GameLogic.getState()==GAME)
                    update(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            render(g);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void render(Graphics graphics) throws InterruptedException {
        renderField(graphics);
    }

    private void renderField(Graphics graphics) throws InterruptedException {
        cellSizeX = getWidth()/GameLogic.getCellCountX();
        cellSizeY = getHeight()/GameLogic.getCellCountY();
        int y = 0;
        for(int i=0; i<GameLogic.getCellCountX(); i++){
            graphics.drawLine(0, y, getWidth(), y);
            y+=cellSizeY;
        }
        int x=0;
        for(int i=0; i<GameLogic.getCellCountY(); i++){
            graphics.drawLine(x, 0, x, getHeight());
            x+=cellSizeX;
        }
        for(int cellY=0; cellY<GameLogic.getCellCountX(); cellY++){
            for (int cellX=0; cellX<GameLogic.getCellCountY(); cellX++){
                FieldState dot = GameLogic.getFieldStateAt(cellX, cellY);
                if(dot==FieldState.HUMAN_DOT){
                    drawCross(cellX, cellY, graphics);
                }
                if(dot==FieldState.AI_DOT){
                    graphics.drawOval(cellX*cellSizeX+PADDING,cellY*cellSizeY+PADDING, cellSizeX-2*PADDING, cellSizeY-2*PADDING);
                }
            }
        }
        if (GameLogic.getState() != GAME){
            showMessage(graphics);
        }
    }

    private void drawCross(int x, int y, Graphics g){
        g.drawLine(
                x*cellSizeX+PADDING,
                y*cellSizeY+PADDING,
                (x+1)*cellSizeX-PADDING,
                (y+1)*cellSizeY-PADDING
        );
        g.drawLine(
                x*cellSizeX+PADDING,
                (y+1)*cellSizeY-PADDING,
                (x+1)*cellSizeX-PADDING,
                y*cellSizeY+PADDING
        );
    }

    private void update(MouseEvent event){
        int x = event.getX()/cellSizeX;
        int y = event.getY()/cellSizeY;
        if(GameLogic.humanTurn(x, y)){
            repaint();
            if(GameLogic.gameChecks(FieldState.HUMAN_DOT)){
                return;
            }
            GameLogic.aiTurn();
            repaint();
            if(GameLogic.gameChecks(FieldState.AI_DOT)){
                return;
            }
        }
    }
    private void showMessage(Graphics g) throws InterruptedException {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, getHeight() / 2, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman", Font.BOLD, 48));
        switch (GameLogic.getState()) {
            case DRAW -> g.drawString(MSG_DRAW, 180, getHeight() / 2 + 60);
            case HUMAN_WIN -> g.drawString(MSG_WIN_HUMAN, 20, getHeight() / 2 + 60);
            case AI_WIN -> g.drawString(MSG_WIN_AI, 70, getHeight() / 2 + 60);
        }
    }
}