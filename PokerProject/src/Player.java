import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.Random;

public class Player {
    private List<Cards> hand = new ArrayList<>();
    public String name;
    public int stackSize;
    private boolean revealed = false;

    public Player(Game game, String name, int stackSize) {
        this.name = name;
        this.stackSize = stackSize;
    }

    // Getters and setters for all variables in player class

    public void setHand(List<Cards> hand) {
        this.hand = hand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }

    public String getName() {
        return name;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public int getStackSize() {
        return stackSize;
    }

    public List<Cards> getHand() {
        return hand;
    }

    /**
     * Evaluates the strength of a poker hand by calling helper functions
     *
     * <p>
     * Method analyzes the given community cards in combination with the players
     * hand to determine the strength
     * of the best possible hand. It returns the highest value among the helper
     * functions.
     * </p>
     *
     * @param riverCards: List of the community cards which are available to all
     *                    players at the table
     * @return A {@code double} representing the strength of the hand being
     *         evaluated. Higher numbers indicate better hand
     */
    public Double handEvaluator(List<Cards> riverCards) {

        List<Double> possibilities = Arrays.asList(isHighCard(riverCards), isTwoPair(riverCards),
                isXPair(riverCards), isStraight(riverCards), isFlush(riverCards),
                isFullHouse(riverCards), isStraightFlush(riverCards));

        return Collections.max(possibilities);
    }

    /**
     * Evaluates the highest value card in someone's hand
     *
     * <p>
     * Method adds the ranks of the riverCards into a list then compares the rank of
     * cards in hand and replaces the
     * minimum of the river card with the card in hand.
     * </p>
     *
     * @param riverCards: List of the community cards which are available to all
     *                    players at the table
     * @return A {@code double} representing the strength of the high card being
     *         evaluated. Higher numbers indicate better high card
     */
    public double isHighCard(List<Cards> riverCards) {
        // Todo: Add a way to compare more than just the first high card
        List<Integer> highCardRanks = new ArrayList<>();
        List<Cards> totalCards = Stream.concat(getHand().stream(), riverCards.stream()).toList();

        for (Cards cards : totalCards) {
            highCardRanks.addLast(cards.rank);
        }
        Collections.sort(highCardRanks);
        return 0 + (highCardRanks.getLast() / 15.0);
    }

    /**
     * Evaluates whether there is a two pair present.
     *
     * <p>
     * Method uses nested for loops to iterate through totalCards to find two pairs
     * that are a different rank
     * </p>
     *
     * @param riverCards: List of the community cards which are available to all
     *                    players at the table
     * @return A {@code double} representing the strength of the two pair.
     *         Higher numbers indicate better hand
     */
    public double isTwoPair(List<Cards> riverCards) {
        List<Cards> totalCards = Stream.concat(getHand().stream(), riverCards.stream()).toList();
        List<Integer> twoPairRanks = new ArrayList<>(2);
        int size = totalCards.size();
        int pairOneRank = 0;
        int pairTwoRank = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (j >= size) { // how to loop through the back of the list
                    j -= size;
                }
                int iRank = totalCards.get(i).rank;
                int jRank = totalCards.get(j).rank;

                if (iRank == jRank) {
                    if (pairOneRank == 0) {
                        pairOneRank = jRank;
                        twoPairRanks.add(pairOneRank);
                    } else if (pairOneRank != jRank && pairTwoRank == 0) {
                        pairTwoRank = iRank;
                        twoPairRanks.add(pairTwoRank);
                    } else {
                        // if both pairOne and pairTwo are populated, then update the lower one
                        int minPair = Collections.min(twoPairRanks);
                        if (jRank > minPair) {
                            twoPairRanks.add(twoPairRanks.indexOf(minPair), jRank);
                        }

                    }
                }

            }
        }
        Collections.sort(twoPairRanks);
        if (twoPairRanks.size() == 2) {
            return 2 + twoPairRanks.get(1) / 15.0 + twoPairRanks.get(0) / 150.0;
        } else {
            return 0;
        }
    }

    /**
     * Evaluates how many of a single card there is in a hand.
     *
     * <p>
     * Method uses a nested for loop to iterate through totalCards to identify if
     * multiple cards have the same rank.
     * If x cards are identified as equal, then xStrength will be updated if it's a
     * better x than what's already
     * being stored.
     * </p>
     *
     * @param riverCards: List of the community cards which are available to all
     *                    players at the table
     * @return A {@code double} representing the strength of the hand being
     *         evaluated. Higher numbers indicate better hand
     */
    public double isXPair(List<Cards> riverCards) {
        List<Cards> totalCards = Stream.concat(getHand().stream(), riverCards.stream()).toList();
        int size = totalCards.size();
        double twoStrength = 0;
        double threeStrength = 0;
        double fourStrength = 0;
        for (int i = 0; i < size; i++) {
            int tracker = 1;
            for (int j = i + 1; j < size; j++) {
                if (j >= size) { // how to loop through the back of the list
                    j -= size;
                }
                if (totalCards.get(i).rank == totalCards.get(j).rank) {
                    tracker++;
                    if (tracker == 2) {
                        if (totalCards.get(i).strength > twoStrength) {
                            twoStrength = totalCards.get(i).strength;
                        }
                    } else if (tracker == 3) {
                        if (totalCards.get(i).strength > threeStrength) {
                            threeStrength = totalCards.get(i).strength;
                        }
                    } else if (tracker == 4) {
                        if (totalCards.get(i).strength > fourStrength) {
                            fourStrength = totalCards.get(i).strength;
                        }
                    }
                }
            }
        }
        if (fourStrength > 0) {
            return 7 + fourStrength;
        }
        if (threeStrength > 0) {
            return 3 + threeStrength;
        }
        if (twoStrength > 0) {
            return 1 + twoStrength;
        } else {
            return 0;
        }
    }

    /**
     * Evaluates whether or not there is a straight present.
     *
     * <p>
     * Method adds ranks of cards in totalCards into straightRanks list then sorts
     * straightRanks list in ascending order. If statement that checks if all 5
     * cards that are needed for a-5 low straight. Also check for subsequent higher
     * 2-6 and 3-7 straights and returns accordingly
     * Then a while loop iterates through straightRanks list to validate if there
     * are at five consecutive cards that are in sequence.
     * </p>
     *
     * @param riverCards: List of the community cards which are available to all
     *                    players at the table
     * @return A {@code double} representing the strength of the straight. Higher
     *         numbers indicate better hand
     */
    public double isStraight(List<Cards> riverCards) {
        List<Cards> totalCards = Stream.concat(getHand().stream(), riverCards.stream()).toList();
        List<Integer> straightRanks = new ArrayList<>(5);
        for (Cards cards : totalCards) {
            straightRanks.addLast(cards.rank);
        }
        // Handling ace as low card in the straight. Checks if all 5 cards need for a-5
        // straight are present in the list
        if (straightRanks.indexOf(14) != -1 && straightRanks.indexOf(2) != -1 && straightRanks.indexOf(3) != -1
                && straightRanks.indexOf(4) != -1 && straightRanks.indexOf(5) != -1) {
            if (straightRanks.indexOf(6) != -1) {
                if (straightRanks.indexOf(7) != -1) {
                    return 4 + 7.0 / 15; // 3,4,5,6,7 straight
                } else {
                    return 4 + 6.0 / 15; // 2,3,4,5,6 straight
                }
            } else {
                return 4 + 5.0 / 15; // A,2,3,4,5 straight
            }
        }
        Collections.sort(straightRanks);

        int size = straightRanks.size();
        int i = 0;
        int consecutive = 1;
        int output = 0;
        int difference;
        while (i < size - 1) {
            difference = straightRanks.get(i + 1) - straightRanks.get(i);
            if (difference == 1) {
                consecutive++;
                if (consecutive >= 5) {
                    output = straightRanks.get(i + 1);
                }

            } else {
                if (difference != 0) {
                    // reset consecutive if chain is ever broken by something that's not a duplicate
                    consecutive = 1;
                }
            }
            i++;
        }
        if (output > 0) {
            return 4 + output / 15.0;
        } else {
            return 0;
        }

    }

    /**
     * Evaluates whether or not there is a flush present (5+ cards of the same suit)
     *
     * <p>
     * Method uses a nested for loop to iterate through totalCards to identify if
     * multiple cards have the same suit. Once a card is identified as having the
     * same suit flushRanks list is added to. Once flushRanks has a size greater
     * than 5, the
     * minimum of the list will be replaced if a greater rank is identified.
     * by a better rank.
     * 
     * </p>
     *
     * @param riverCards: List of the community cards which are available to all
     *                    players at the table
     * @return A {@code double} representing the strength of the flush being
     *         evaluated. Higher numbers indicate better hand. 0 if there is no
     *         flush.
     */
    public double isFlush(List<Cards> riverCards) {
        // Todo: test this
        List<Cards> totalCards = Stream.concat(getHand().stream(), riverCards.stream()).toList();
        int size = totalCards.size();
        List<Integer> flushRanks = new ArrayList<>(5);
        for (int i = 0; i < size; i++) {
            flushRanks.clear();
            flushRanks.add(totalCards.get(i).rank);
            for (int j = i + 1; j < size; j++) {
                if (j >= size) { // how to loop through the back of the list
                    j -= size;
                }
                if (totalCards.get(i).suit == totalCards.get(j).suit) {
                    int thisRank = totalCards.get(j).rank;
                    int minRank = flushRanks.stream().min(Integer::compare).orElse(-1);
                    if (flushRanks.size() < 5) {
                        flushRanks.addLast(thisRank);
                    } else {
                        if (thisRank > minRank) {
                            flushRanks.add(flushRanks.indexOf(minRank), thisRank);
                        }
                    }
                }
            }
            if (flushRanks.size() >= 5) {
                return 5 + Collections.max(flushRanks) / 15.0;
            }
        }
        return 0;

    }

    /**
     * Evaluates whether there is a full house present.
     *
     * <p>
     * Method uses nested for loops to iterate through totalCards to find a set and
     * a pair that are a different rank
     * </p>
     *
     * @param riverCards: List of the community cards which are available to all
     *                    players at the table
     * @return A {@code double} representing the strength of the full house.
     *         Higher numbers indicate better hand
     */
    public double isFullHouse(List<Cards> riverCards) {

        List<Cards> totalCards = Stream.concat(getHand().stream(), riverCards.stream()).toList();
        int size = totalCards.size();
        int pairOneRank = 0;
        int tripsRank = 0;
        for (int i = 0; i < size; i++) {
            int tripsTracker = 1;
            for (int j = i + 1; j < size + i; j++) {
                if (j >= size) { // how to loop through the back of the list
                    j -= size;
                }
                if (j == i) {
                    break;
                }
                int iRank = totalCards.get(i).rank;
                int jRank = totalCards.get(j).rank;

                if (iRank == jRank) {
                    tripsTracker++;
                    if (tripsTracker == 3) {
                        if (jRank > tripsRank) {
                            tripsRank = jRank;
                        }
                        if (tripsRank == pairOneRank) {
                            pairOneRank = 0;
                        }
                    } else {
                        pairOneRank = jRank;
                    }
                }
            }
        }
        if (tripsRank > 0 && pairOneRank > 0) {
            return 6 + tripsRank / 15.0 + pairOneRank / 150.0;
        } else {
            return 0;
        }
    }

    /**
     * Evaluates whether there is a straight flush present.
     *
     * <p>
     * Method creates new list straightRanks of all the ranks of the cards +n*100
     * (where n is an integer representing the suit of the card) in totalCards. Then
     * there is a check to see if the a-5 low straight is present. Then finally the
     * sorted straightRanks list is traversed trying to find five cards in
     * consecutive order.
     * </p>
     *
     * @param riverCards: List of the community cards which are available to all
     *                    players at the table
     * @return A {@code double} representing the strength of the straight flush.
     *         Higher numbers indicate better hand
     */
    public double isStraightFlush(List<Cards> riverCards) {
        List<Cards> totalCards = Stream.concat(getHand().stream(), riverCards.stream()).toList();
        List<Integer> straightRanks = new ArrayList<>(5);
        for (Cards cards : totalCards) {
            char suit = cards.suit;
            int suitVal = 0;
            // arbitrary suit numbers so that straight flush can be monitored
            switch (suit) {
                case 'h' -> suitVal = 100;
                case 'd' -> suitVal = 200;
                case 'c' -> suitVal = 300;
                case 's' -> suitVal = 400;
                default -> suitVal = 1;
            }
            straightRanks.addLast(cards.rank + suitVal);
        }
        // Handling ace as low card in the straight. Checks if all 5 cards need for a-5
        // straight flush are present in the list

        for (int i = 1; i < 5; i++) {
            if (straightRanks.indexOf(14 + i * 100) != -1 && straightRanks.indexOf(2 + i * 100) != -1
                    && straightRanks.indexOf(3 + i * 100) != -1
                    && straightRanks.indexOf(4 + i * 100) != -1 && straightRanks.indexOf(5 + i * 100) != -1) {
                if (straightRanks.indexOf(6 + i * 100) != -1) {
                    if (straightRanks.indexOf(7 + i * 100) != -1) {
                        return 8 + 7.0 / 15; // 3,4,5,6,7 straight
                    } else {
                        return 8 + 6.0 / 15; // 2,3,4,5,6 straight
                    }
                } else {
                    return 8 + 5.0 / 15; // A,2,3,4,5 straight
                }
            }
        }
        Collections.sort(straightRanks);
        int size = straightRanks.size();
        int i = 0;
        int consecutive = 1;
        int output = 0;
        while (i < size - 1) {
            if (straightRanks.get(i + 1) - straightRanks.get(i) == 1) {
                consecutive++;
                if (consecutive >= 5) {
                    output = straightRanks.get(i + 1) % 100;
                }

            } else {
                // reset consecutive if chain is ever broken
                consecutive = 1;
            }
            i++;
        }
        if (output > 0) {
            return 8 + output / 15.0;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        Player player1 = new Player(game, "Marlon", 0);
        Player player2 = new Player(game, "Pavel", 0);
        List<Cards> riverCards = new ArrayList<>();

        // riverCards.add(new Cards('s', 9));
        // riverCards.add(new Cards('d', 2));
        // riverCards.add(new Cards('d', 11));
        // riverCards.add(new Cards('c', 4));
        // riverCards.add(new Cards('h', 8));
        // List<Cards> test = new ArrayList<>();
        // test.add(new Cards('h', 11));
        // test.add(new Cards('h', 11));
        // System.out.println("Player One's hand" + test.toString());

        riverCards.addAll(game.flop());
        riverCards.add(game.turnOrRiver());
        riverCards.add(game.turnOrRiver());
        System.out.println("River cards: " + riverCards.toString());
        game.setPlayerHand(player1);
        game.setPlayerHand(player2);
        System.out.println("Player one's hand: " + player1.getHand());
        System.out.println("Player two's hand: " + player2.getHand());

        System.out.println("High card tester: " + player1.isHighCard(riverCards));
        System.out.println("Two pair tester: " + player1.isTwoPair(riverCards));
        System.out.println("X pair tester: " + player1.isXPair(riverCards));
        System.out.println("Straight tester: " + player1.isStraight(riverCards));
        System.out.println("Flush tester: " + player1.isFlush(riverCards));
        System.out.println("Full house tester: " + player1.isFullHouse(riverCards));
        System.out.println("Straight flush tester: " + player1.isStraightFlush(riverCards));

        System.out.println("Player one's hand evaluated at: " + player1.handEvaluator(riverCards));
        System.out.println("Player two's hand evaluated at: " + player2.handEvaluator(riverCards));

    }
}
