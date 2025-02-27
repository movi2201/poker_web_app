import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    /**
     * Brief description of the class or method.
     *
     * <p>
     * Additional details about the functionality, usage, or behavior.
     * Can be multiple lines.
     * </p>
     *
     * @param
     * 
     * @return
     */
    public List<Cards> flop() {
        int i = 0;
        List<Cards> flop = new ArrayList<>();
        int range;
        while (i < 3) {
            Random rand = new Random();
            range = rand.nextInt(deck.size());
            flop.add(deck.get(range));
            deck.remove(range);
            i++;
        }
        return flop;
    }

    /**
     * Brief description of the class or method.
     *
     * <p>
     * Additional details about the functionality, usage, or behavior.
     * Can be multiple lines.
     * </p>
     *
     * @param
     * 
     * @return
     */
    public Cards turnOrRiver() {

        Cards turn;
        Random rand = new Random();
        int range = rand.nextInt(deck.size());
        turn = (deck.get(range));
        deck.remove(range);
        return turn;
    }

    /**
     * Brief description of the class or method.
     *
     * <p>
     * Additional details about the functionality, usage, or behavior.
     * Can be multiple lines.
     * </p>
     *
     * @param
     * 
     * @return
     */
    public void setPlayerHand(Player player) {
        int i = 0;
        List<Cards> hand = new ArrayList<>();
        int range;
        while (i < 2) {
            Random rand = new Random();
            range = rand.nextInt(deck.size());
            hand.add(deck.get(range));
            deck.remove(range);
            i++;
        }
        player.setHand(hand);
    }

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.deck.toString());
    }

}
