import java.util.ArrayList;
import java.util.List;

public class Game {
    public List<Cards> deck = new ArrayList<>(); // Array of 52 cards (Like in Poker :) )
    public static final char[] suits = new char[] { 'd', 'h', 'c', 's' };

    public Game() {
        for (char suit : suits) {
            for (int i = 2; i < 15; i++) {
                deck.add(new Cards(suit, i));
            }
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.deck.toString());
    }

}
