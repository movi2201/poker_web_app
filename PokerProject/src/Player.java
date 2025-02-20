import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.DoubleStream;

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

        List<Double> possibilities = Arrays.asList(isOnePair(riverCards), isTwoPair(riverCards),
                isThreeOfKind(riverCards), isStraight(riverCards), isFlush(riverCards),
                isFullHouse(riverCards), isFourOfKind(riverCards), isStraightFlush(riverCards));

        return Collections.max(possibilities);
    }
    public double isOnePair(List<Cards> riverCards){
        //Todo: Implement this
        return 0;
    }
    public double isTwoPair(List<Cards> riverCards){
        //Todo: Implement this
        return 0;
    }
    public double isThreeOfKind(List<Cards> riverCards){
        //Todo: Implement this
        return 0;
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
}
