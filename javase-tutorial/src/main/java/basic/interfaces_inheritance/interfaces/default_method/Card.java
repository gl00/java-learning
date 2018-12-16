package basic.interfaces_inheritance.interfaces.default_method;

public interface Card extends Comparable<Card> {

    enum Suit {
        DIAMONDS(1, "Diamonds"), CLUBS(2, "Clubs"), HEARTS(3, "Hearts"), SPADES(4, "Spades");

        private int value;
        private String text;

        Suit(int value, String text) {
            this.value = value;
            this.text = text;
        }

        public int value() {
            return value;
        }

        public String text() {
            return text;
        }
    }

    enum Rank {
        DEUCE(2, "Deuce"), THREE(3, "Three"), FOUR(4, "Four"), FIVE(5, "Five"), SIX(6, "Six"),
        SEVEN(7, "Seven"), EIGHT(8, "Eight"), NINE(9, "Nine"), TEN(10, "Ten"), JACK(11, "Jack"),
        QUEEN(12, "Queen"), KING(13, "King"), ACE(14, "Ace");

        private int value;
        private String text;

        Rank(int value, String text) {
            this.value = value;
            this.text = text;
        }

        public int value() {
            return value;
        }

        public String text() {
            return text;
        }
    }

    Suit getSuit();

    Rank getRank();
}
