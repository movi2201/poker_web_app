import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Player {
    private List<Cards> hand = new ArrayList<>();
    public String name;
    public int stackSize;
    private boolean revealed = false;

    public Player(List<Cards> hand, String name, int stackSize){
        this.hand = hand;
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
     * <p> Method analyzes the given community cards in combination with the players hand to determine the strength
     * of the best possible hand. It returns the highest value among the helper functions. </p>
     *
     * @param riverCards: List of the community cards which are available to all players at the table
     * @return A {@code double} representing the strength of the hand being evaluated. Higher numbers indicate better hand
     */
    public Double handEvaluator(List<Cards> riverCards){

        List<Double> possibilities = Arrays.asList(isHighCard(riverCards), isOnePair(riverCards), isTwoPair(riverCards),
                isThreeOfKind(riverCards), isStraight(riverCards), isFlush(riverCards),
                isFullHouse(riverCards), isFourOfKind(riverCards), isStraightFlush(riverCards));

        return Collections.max(possibilities);
    }
    /**
     * Evaluates the highest value card in someone's hand
     *
     * <p> Method adds the ranks of the riverCards into a list then compares the rank of cards in hand and replaces the
     * minimum of the river card with the card in hand.</p>
     *
     * @param riverCards: List of the community cards which are available to all players at the table
     * @return A {@code double} representing the strength of the high card being evaluated. Higher numbers indicate better high card
     */
    public double isHighCard(List<Cards> riverCards){
        // Todo: Add a way to compare more than just the first high card
        List<Integer> highCardStrengths = new ArrayList<>(5);
        int i = 0;
        while (i < 5){
            highCardStrengths.add(riverCards.get(i).rank);
            i++;
        }

        for (Cards cards: hand){
            int minStrength = highCardStrengths.stream().min(Integer::compare).orElse(-1); // Default value if empty
            int result = Integer.compare(cards.rank, minStrength);
            //	Returns 0 if a == b
            //	Returns a negative number if a < b
            //	Returns a positive number if a > b
            if (result > 0){
                highCardStrengths.set(highCardStrengths.indexOf(minStrength),cards.rank);
            }
        }
        highCardStrengths.sort(Integer::compare);
        return 0 + (highCardStrengths.getLast()/14.0);
    }
    /**
     * Evaluates the highest value pair in someone's hand
     *
     * <p> Method uses a nested for loop to iterate through totalCards to identify if two cards have the same rank.
     * If two cards are identified as equal, then the strength will be updated if it's a better pair than what's already
     * being stored. </p>
     *
     * @param riverCards: List of the community cards which are available to all players at the table
     * @return A {@code double} representing the strength of the hand being evaluated. Higher numbers indicate better hand
     */
    public double isOnePair(List<Cards> riverCards){
        //Todo: Track strength of the rest of the hand. Not just the strength of the pair
        List<Cards> totalCards = Stream.concat(getHand().stream(), riverCards.stream()).toList();
        int size = totalCards.size();
        boolean pairTracker = false;
        double pairStrength = 0;
        for (int i = 0; i<size; i++){
            for (int j = i+1; j != i; j++ ){
                if (j >= size) { // how to loop through the back of the list
                    j -= size;
                }
                pairTracker = totalCards.get(i).rank == totalCards.get(j).rank;
                if (pairTracker){
                    if (totalCards.get(i).strength > pairStrength){
                        pairStrength = totalCards.get(i).strength;
                    }
                }
            }
        }
        if (pairTracker){
            return 1+pairStrength;
        }
        else{
            return 0;
        }
    }
    public double isTwoPair(List<Cards> riverCards){
        //Todo: Implement this
        return 0;
    }
    /**
     * Evaluates how many of a single card there is in a hand.
     *
     * <p> Method uses a nested for loop to iterate through totalCards to identify if multiple cards have the same rank.
     * If x cards are identified as equal, then  xStrength will be updated if it's a better x than what's already
     * being stored. </p>
     *
     * @param riverCards: List of the community cards which are available to all players at the table
     * @return A {@code double} representing the strength of the hand being evaluated. Higher numbers indicate better hand
     */
    public double isThreeOfKind(List<Cards> riverCards){
        List<Cards> totalCards = Stream.concat(getHand().stream(), riverCards.stream()).toList();
        int size = totalCards.size();
        double twoStrength = 0;
        double threeStrength = 0;
        double fourStrength = 0;
        for (int i = 0; i < size; i++){
            int tracker = 1;
            for (int j = i+1; j != i; j++ ){
                if (j >= size) { // how to loop through the back of the list
                    j -= size;
                }
                if (totalCards.get(i).rank == totalCards.get(j).rank) {
                    tracker++;
                    if (tracker == 2) {
                        if (totalCards.get(i).strength > twoStrength) {
                            twoStrength = totalCards.get(i).strength;
                        }
                    }
                    else if(tracker == 3) {
                        if (totalCards.get(i).strength > threeStrength) {
                            threeStrength = totalCards.get(i).strength;
                        }
                    }
                    else if (tracker == 4) {
                        if (totalCards.get(i).strength > fourStrength) {
                            fourStrength = totalCards.get(i).strength;
                        }
                    }
                }
            }
        }
        if (fourStrength > 0 ){
            return 7+fourStrength;
        }
        if (threeStrength > 0){
            return 3 + threeStrength;
        }
        if (twoStrength > 0){
            return 1+ twoStrength;
        }
        else{
            return 0;
        }
    }
    public double isStraight(List<Cards> riverCards){
        //Todo: Implement this
        return 0;
    }
    public double isFlush(List<Cards> riverCards){
        //Todo: Implement this
        return 0;
    }
    public double isFullHouse(List<Cards> riverCards){
        //Todo: Implement this
        return 0;
    }
    public double isFourOfKind(List<Cards> riverCards){
        //Todo: Implement this
        return 0;
    }
    public double isStraightFlush(List<Cards> riverCards){
        //Todo: Implement this
        return 0;
    }
    public static void main(String[] args){

    }
}
