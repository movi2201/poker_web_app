import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public List<Cards> deck = new ArrayList<>(52); // Array of 52 cards (Like in Poker :) )
    public static final char[] suits = new char[] { 'd', 'h', 'c', 's' };
    public static final String[] names = new String[] { "Kurt Cobain", "Kendrick Lamar", "Kurt Hummel",
            "Lionel Messi" };

    public Game() {
        for (char suit : suits) {
            for (int i = 2; i < 15; i++) {
                deck.add(new Cards(suit, i));
            }
        }

    }

    public void dealCards(Table table) {
        List<Player> tableMembers = table.table;
        if (tableMembers.size() == 0) {
            System.out.println("Table has not been properly initialized");
            return;
        }
        for (Player player : tableMembers) {
            table.setPlayerHand(player);
        }
    }

    public void gameFlow(Table table) {
        table.initializeTable(this, 4);
        table.toString();
        table.preFlopBettingRound();
        while (table.table.size() > 1) {
            table.riverCards.addAll(table.flop());
            table.toString();
            table.bettingRound(0);
            table.riverCards.add(table.turnOrRiver());
            table.toString();
            table.bettingRound(0);
            table.riverCards.add(table.turnOrRiver());
            table.toString();
            table.bettingRound(0); // final round of betting
            // evaluate hands of winner
            if (table.table.size() != 1) {
                Player winner = table.table.get(0);
                double winningHand = 0;
                for (Player player : table.table) {
                    double thisHandValue = player.handEvaluator(table.riverCards);
                    if (thisHandValue > winningHand) {
                        winningHand = thisHandValue;
                        winner = player;
                    }
                    winner.stackSize += table.potSize;
                    table.potSize = 0;
                }

            } else {
                // give pot to the winner and reset pot size
                table.table.get(0).stackSize += table.potSize;
                table.potSize = 0;
            }
        }
    }

    public static void main(String[] args) {
        // Game game = new Game();

    }

}
