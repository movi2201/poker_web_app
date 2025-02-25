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
        Random rand = new Random();
        List<Cards> deck = game.deck;
        List<Cards> hand = new ArrayList<>();
        int index1 = rand.nextInt(52);
        int index2 = rand.nextInt(53);

        hand.add(deck.get(index1));
        deck.remove(index1);
        hand.add(deck.get(index2));
        deck.remove(index2);

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

    public double isTwoPair(List<Cards> riverCards) {
        // Todo: Implement this
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
        // Todo: Test this
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
        while (i < size - 1) {
            if (straightRanks.get(i + 1) - straightRanks.get(i) == 1) {
                consecutive++;
                if (consecutive >= 5) {
                    output = straightRanks.get(i + 1);
                }

            } else {
                // reset consecutive if chain is ever broken
                consecutive = 1;
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

    public double isFullHouse(List<Cards> riverCards) {
        // Todo: Implement this
        return 0;
    }

    public double isStraightFlush(List<Cards> riverCards) {
        // Todo: Implement this
        return 0;
    }

    public static void main(String[] args) {
        Game game = new Game();
        Player player1 = new Player(game, "Marlon", 0);
        List<Cards> riverCards = new ArrayList<>();

        riverCards.add(new Cards('h', 2));
        riverCards.add(new Cards('h', 9));
        riverCards.add(new Cards('c', 4));
        riverCards.add(new Cards('c', 5));
        riverCards.add(new Cards('c', 8));
        System.out.println("River cards: " + riverCards.toString());

        List<Cards> highCardTest = new ArrayList<>();
        highCardTest.add(new Cards('h', 2));
        highCardTest.add(new Cards('h', 9));
        System.out.println("Marlon's hand" + highCardTest.toString());

        player1.setHand(highCardTest);
        System.out.println("High card tester: " + player1.isHighCard(riverCards));
        System.out.println("Two pair tester: " + player1.isTwoPair(riverCards));
        System.out.println("X pair tester: " + player1.isXPair(riverCards));
        System.out.println("Straight tester: " + player1.isStraight(riverCards));
        System.out.println("Flush tester: " + player1.isFlush(riverCards));
        System.out.println("Full house tester: " + player1.isFullHouse(riverCards));
        System.out.println("Straight flush tester: " + player1.isStraightFlush(riverCards));

        System.out.println("Hand evaluator tester: " + player1.handEvaluator(riverCards));

    }
}
