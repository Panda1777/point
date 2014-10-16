/**
 * Created by Танюша on 1.10.2014.
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;

public class GameStruct extends JFrame /*implements  MouseListener*/ {
    private Game game;
    private final int RowsCount = 10;
    private final int ColsCount = 10;
    private int cellSize        = 30 ; // размер ячейки в пикселах

    GameStruct() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Game(RowsCount, ColsCount, cellSize, Color.green, Color.red);
        game.Start();
        add(game);
        //game.addMouseListener(this);
        pack();
        setTitle("Точки");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameStruct().setVisible(true);
            }
        });
    }

}
