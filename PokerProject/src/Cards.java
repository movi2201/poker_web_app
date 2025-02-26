import java.util.Map;

public class Cards {

    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_GREY_BACKGROUND = "\u001b[0m";
    public static final String ANSI_BLACK = "\u001B[30m";

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
        String color = "";
        Map<Character, Character> prettySuits = Map.of(
                'h', '♥',
                'd', '♦',
                's', '♠',
                'c', '♣');
        if (suit == 'h' || suit == 'd') {
            color = ANSI_RED_BRIGHT;
        } else {
            color = ANSI_BLACK;
        }
        return color + rankNames[rank - 2] + prettySuits.get(suit) + ANSI_GREY_BACKGROUND;
    }
}
