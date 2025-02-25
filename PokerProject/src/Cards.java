public class Cards {
    char suit;
    int rank;
    double strength;

    public Cards(char suit, int rank) {
        if (suit != 'h' && suit != 'd' && suit != 'c' && suit != 's') {
            throw new IllegalArgumentException("Invalid suit type");
        }
        if (rank > 14 || rank < 2) {
            throw new IllegalArgumentException("Invalid rank");
        }
        this.rank = rank;
        this.suit = suit;
        strength = rank / 15.0;
    }

    public String toString() {
        String[] rankNames = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        return rankNames[rank - 2] + suit;
    }
}
