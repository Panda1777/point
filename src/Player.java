/**
 * Created by Танюша on 1.10.2014.
 */
import java.awt.Color;

public class Player {
    private Color color;
    private PointTypes type; //какой игрок
    public Player(Color clr, PointTypes t) {
        color = clr;
        type = t;
    }
    public Color GetColor ()  {
        return color;
    }
    public PointTypes GetType() {
        return type;
    }

}
