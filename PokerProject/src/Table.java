import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Collections;
import java.util.List;
import java.util.Random;
//import java.util.stream.Stream;
import java.util.Scanner;

public class Table {
    List<Player> table = new ArrayList<>();
    List<Cards> deck;
    List<Cards> riverCards = new ArrayList<>();

    int toCall;
    int smallBlind;
    int bigBlind;
    int potSize;

    Player lastAction;
    Player toAct;

    public Table(Game game, List<Player> playerList) {
        deck = game.deck;
        table = playerList;
        lastAction = playerList.get(0);

        // Blinds can be set as a parameter in constructor in the future
        smallBlind = 1;
        bigBlind = 2;

    }

    public List<Player> initializeTable(Game game, int numPlayers) {
        Random r = new Random();
        for (int i = 0; i < numPlayers - 1; i++) {
            if (numPlayers > Game.names.length + 1) {
                numPlayers = Game.names.length;
            } else {
                int nameIdx = r.nextInt(4);
                table.add(new Bot(game, Game.names[nameIdx], 100));
            }
        }
        return table;
    }

    /**
     * Used for betting before the flop
     * <p>
     * Validates small blind and bigblind result then calls betting round starting
     * on the UTG position
     * </p>
     * 
     */
    public void preFlopBettingRound() {
        // todo: Implement this
        boolean smallBlindResult = table.get(0).betValidation(this, smallBlind);
        boolean bigBlindResult = table.get(1).betValidation(this, bigBlind);

        if (!smallBlindResult) {
            System.out.println("Small blind failed");
            return;
        } else {
            if (!bigBlindResult) {
                System.out.println("Big blind failed");
                return;
            } else {
                lastAction = table.get(1); // big blind
                for (Player players : table) {
                    setPlayerHand(players);
                }
                bettingRound(2);

            }

        }

    }

    /**
     * Used for non betting post flop
     *
     * <p>
     * Circularly iterates through table until the player who opened the action is
     * reached again. If tableMember is a bot then the appropriate bot method is
     * called and if it's not then user input is taken to indicate what the player
     * wants to do.
     * </p>
     *
     * @param
     * startPosition        {@code int} indicates where in table the betting should
     *                      start at
     */
    public void bettingRound(int startPosition) {
        for (int i = startPosition; table.get(i).equals(lastAction); i++) {
            if (i >= table.size()) {
                i -= table.size();
            } else {
                Player tableMember = table.get(i);
                if (tableMember instanceof Bot) {
                    Bot robot = (Bot) tableMember;
                    int botAction = robot.botActing(this);
                    if (botAction > 0) {
                        potSize += botAction;
                        if (botAction > toCall) {
                            toCall = botAction;
                        }
                    }
                } else if (tableMember instanceof Player) {
                    // Take in user input to determine what to do
                    Scanner s = new Scanner(System.in);

                    // Todo: add something to catch unwanted behavior
                    boolean correctAction = false;
                    while (!correctAction) {
                        System.out.println("It's your action what would you like to do? ");
                        String action = s.nextLine();
                        switch (action) {
                            case "check":
                                if (toCall > 0) {
                                    System.out.println("Oops can't check right now");
                                } else {
                                    System.out.println(tableMember.name + " checks");
                                    correctAction = true;
                                }
                            case "fold":
                                System.out.println(tableMember.name + " folds");
                                table.remove(tableMember);
                                correctAction = true;
                            case "bet":
                                if (toCall > 0) {
                                    System.out.println("Did you mean to raise or call?");
                                } else {
                                    System.out.println("How much would you like to bet? ");
                                    int betAmt = s.nextInt();
                                    // add a check to make sure this is correct
                                    if (tableMember.betValidation(this, betAmt)) {
                                        System.out.println(tableMember.name + "bets");
                                        toCall = betAmt;
                                        lastAction = tableMember;
                                        correctAction = true;
                                    } else {
                                        System.out.println("Bet validation failed");
                                    }
                                }
                            case "raise":
                                if (toCall == 0) {
                                    System.out.println("Did you mean to bet?");
                                } else {
                                    System.out.println("How much would you like to bet? ");
                                    int betAmt = s.nextInt();
                                    // add a check to make sure this is correct
                                    if (tableMember.betValidation(this, betAmt)) {
                                        System.out.println(tableMember.name + "bets");
                                        toCall = betAmt;
                                        lastAction = tableMember;
                                        correctAction = true;
                                    } else {
                                        System.out.println("Bet validation failed");
                                    }
                                }
                            case "call":
                                if (toCall == 0) {
                                    System.out.println("Oops nothing to call right now");
                                } else {
                                    if (tableMember.betValidation(this, toCall)) {
                                        System.out.println(tableMember.name + "calls");
                                        correctAction = true;
                                    } else {
                                        System.out.println("Bet validation failed");
                                    }

                                }

                            default:
                                System.out.println("Not an option");
                        }
                    }
                    s.close();
                } else {
                    System.out.println("Member of table is not a bot or a player");
                    return;
                }
            }
        }
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

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Pot size: " + potSize);
        for (Player player : table) {
            if (player.isRevealed()) {
                s.append(player.name + "\n" + player.getHand().toString());
            } else {
                s.append(player.name + "\n[][]");
            }
        }
        s.append("River cards: " + riverCards.toString());
        return s.toString();
    }
}
