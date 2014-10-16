/**
 * Created by Танюша on 1.10.2014.
 */
import java.awt.*;
/**
 * Created by Танюша on 15.10.2014.
 */
public class DrawablePoint {
    private Color color;
    private PointTypes Type;
    public int x, y;           // Экранные координаты в пикселах
    // соседние точки, с которыми эта соединена.
    private DrawablePoint connectedTo[] = new DrawablePoint[8];

    public DrawablePoint(int sx, int sy, Color clr, PointTypes type) {
        x = sx;
        y = sy;
        color = clr ;
        Type = type;
    }

    public PointTypes GetType() {
        return Type;
    }
    public void SetType(PointTypes type) {
        Type = type;
    }


    public void DrawOutline(Graphics g){
        g.setColor(Color.BLACK);  // тут будем изменять для смены цвета окаёма
        g.drawOval(x-8, y-8, (6*2)+2, (6*2)+2);
        g.setColor(color);
    }
    public void Paint (Graphics g) {
        // рисуем саму точку
        DrawBody(g);
        DrawConnections(g);
    }
    /**
     *  Рисуем саму точку.
     */
    private void DrawBody(Graphics g) {
        g.setColor(color);
        g.fillOval(x - 6, y - 6, 6 * 2, 6 * 2);
        g.setColor(Color.black);
        if (Type==PointTypes.Player1Dead | Type==PointTypes.Player2Dead |
                Type==PointTypes.SimplyDead ) {
            g.drawLine(x-6, y-6, x+6, y+6);
            g.drawLine(x+6, y-6, x-6, y+6);
        }
    }
    private int GetX(){return x;}
    private int GetY(){return y;}
    /**
     *   Рисуем соединения.
     */

    private void DrawConnections(Graphics g) {
        for(int i=0; i<8; i++) { // for each from connectedTo[]
            if(connectedTo[i]==null) break; // if no more points - end loop
            // draw line from this point to B point
            g.drawLine(x, y, connectedTo[i].GetX(), connectedTo[i].GetY() );
        }
    }

}
