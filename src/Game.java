/**
 * Created by Танюша on 1.10.2014.
 */
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel implements MouseListener {
    boolean IsStarted = false;
    public int rows = 20;           // количество горизонталей
    public int cols = 20;
    public int cellSize = 30 ;
    private Player Player1, Player2;
    private boolean FirstPlayerToMove;
    private DrawablePoint points[][]; // array to hold all possible  points
    private DrawablePoint LastPoint;
    private final int Circle[][] = { {-1,-1}, {-1,0},{-1,1},
            { 0,-1},        { 0,1},
            { 1,-1}, { 1,0},{ 1,1} };
    /**
     * Главный конструктор.
     * @param r Количество горизонталей
     * @param c Количество вертикалей
     * @param cellsize Размер ячейки в пикселах
     * @param color1 Цвет точек первого игрока
     * @param color2 Цвет точек второго игрока
     */
    Game(int r, int c, int cellsize, Color color1, Color color2) {
        rows = r;
        cols = c;
        cellSize = cellsize;
        Player1 = new Player(color1, PointTypes.Player1);
        Player2 = new Player(color2, PointTypes.Player2);
        points = new DrawablePoint[rows][cols];
        LastPoint = null;
        IsStarted = FirstPlayerToMove = false;
        setPreferredSize(new Dimension( (r+1)*cellSize, (c+1)*cellSize ) );
        setVisible(true);
        addMouseListener(this);
    }
    /**
     * Начать игру.
     */
    public void Start() {
        IsStarted = FirstPlayerToMove = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.translate(cellSize, cellSize);
        // рисуем линии
        for (int i = 0; i < rows; i++)
            g.drawLine(0, cellSize*i, cols*cellSize-cellSize, cellSize*i);
        for (int j = 0; j < cols; j++)
            g.drawLine( j * cellSize, rows*cellSize-cellSize,  j * cellSize, 0 );
        // Рисуем точки.
        for(int i=0; i<rows; i++)
            for(int j=0; j<cols; j++)
                if (points[i][j]!=null)
                    points[i][j].Paint(g);

        if (LastPoint != null)
            LastPoint.DrawOutline(g);
        g.translate(-cellSize, -cellSize);
    }
    boolean WasIHere[][];
    boolean gorec(int x, int y) {
        if (WasIHere[x][y]) return false;
        if (x==0 | y==0 | x==rows-1 | y==cols-1)
            return true;
        WasIHere[x][y] = true;
        return (gorec(x-1,y) | gorec(x+1,y) | gorec(x,y-1) | gorec(x,y+1) );

    }
    /**
     * Обновляем информацию на доске после очередного хода
     */
    private void UpdateLinks(PointTypes whos) {
        WasIHere =   new boolean[rows][cols];
        // пометим куда ходить нельзя.
        for (int i=0; i<rows; i++)
            for (int j=0; j<cols; j++) {
                if (points[i][j]==null) continue;
                if (points[i][j].GetType()!=whos )
                    WasIHere[i][j] = true;
            }

        for (int i=0; i<rows; i++)
            for (int j=0; j<cols; j++) {
                if (WasIHere[i][j] | points[i][j]==null) continue;
                //WasIHere[i][j] = true;
                boolean ans = gorec(i,j);
                if (!ans)
                    points[i][j].SetType(PointTypes.SimplyDead);
            }
    }
    public void mouseReleased(MouseEvent e) {
        int screenX = e.getX(); // куда кликнули
        int screenY = e.getY();
        if (!mouseWasClicked(screenX, screenY) )
            JOptionPane.showMessageDialog(this, "Вы нажали не туда");
        repaint();
    }
    // Обработка события щелчка мыши на поле.

    public boolean mouseWasClicked(int x, int y) {
        if (!IsStarted) return false;
        int pointX = screen2field(x); //какая точка нажата
        int pointY = screen2field(y);
        if ( !goodCoords(pointX, pointY) ) return false;
        Player CurPlayer;
        PointTypes underAttack;
        if (FirstPlayerToMove) {
            CurPlayer = Player1; underAttack = PointTypes.Player2;
        }
        else {
            CurPlayer = Player2;
            underAttack = PointTypes.Player1;
        }

        if (points[pointX][pointY]!=null) return true;
        points[pointX][pointY] = new DrawablePoint(pointX*cellSize, pointY*cellSize,
                CurPlayer.GetColor(), CurPlayer.GetType() );
        FirstPlayerToMove = !FirstPlayerToMove;
        UpdateLinks(underAttack);

        repaint();
        return true;
    }

    private int screen2field(int coord) { // преобразуем экранные координаты в номер клетки
        return (coord-cellSize/2) /cellSize;
    }

    private boolean goodCoords(int fieldX, int fieldY) {
        if(fieldX>=0 && fieldY>=0 && fieldX<cols && fieldY<rows)
            return true;
        return false;
    }


    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

