import java.util.ArrayList;
import java.util.List;

public class Bot extends Player {
    private List<Cards> hand = new ArrayList<>();
    public String name;
    public int stackSize;
    private boolean revealed;

    public Bot(Game game, String name, int stackSize) {
        super(game, name, stackSize);
        revealed = false;
    }

    public int botActing(Table table) {
        // Todo: Implement this
        return 2;
    }

}
