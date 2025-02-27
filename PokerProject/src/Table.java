import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Collections;
import java.util.List;
import java.util.Random;
//import java.util.stream.Stream;

public class Table {
    List<Player> table = new ArrayList<>();
    List<Cards> deck;

    int toCall;
    int smallBlind;
    int bigBlind;
    int potSize;

    Player lastAction;
    Player toAct;

    public Table(Game game, List<Player> playerList) {
        deck = game.deck;
        playerList = table;
        toAct = playerList.get(0);

        // Blinds can set as a parameter in constructor in the future
        smallBlind = 1;
        bigBlind = 2;
        potSize = smallBlind + bigBlind;
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
    public void preFlopBettingRound() {
        // todo: Implement this
        return;
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
    public void bettingRound() {
        // todo: Implement this
        return;
    }

    /**
     * Flops the first three cards of the game
     * 
     * @return
     *         A list of 3 cards representing the flop
     */
    public List<Cards> flop() {
        int i = 0;
        List<Cards> flop = new ArrayList<>();
        int range;
        Random rand = new Random();
        while (i < 3) {
            range = rand.nextInt(deck.size());
            flop.add(deck.get(range));
            deck.remove(range);
            i++;
        }
        return flop;
    }

    /**
     * Used for the turn/river where one card is displayed at a time
     * 
     * @return
     *         The turn/ river card
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
     * Sets the hand of a player
     *
     * @param
     * player        The player who's hand is being dealt
     */
    public void setPlayerHand(Player player) {
        // Could set to return boolean indicating success
        int i = 0;
        List<Cards> hand = new ArrayList<>();
        int range;
        Random rand = new Random();
        while (i < 2) {

            range = rand.nextInt(deck.size());
            hand.add(deck.get(range));
            deck.remove(range);
            i++;
        }
        player.setHand(hand);
    }

}
