public class Cards {
    char suit;
    int rank;
    public Cards(char suit, int rank){
        this.rank = rank;
        this.suit = suit;
    }
    public String toString(){
        String[] rankNames = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        return rankNames[rank-2] + suit;
    }
}
