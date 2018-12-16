package basic.class_object.exercise;

public class Card {
	private int rank;
	private int suit;

	// Kinds of suits
	public static final int DIAMONDS = 1;
	public static final int CLUBS = 2;
	public static final int HEARTS = 3;
	public static final int SPADES = 4;

	// Kinds of ranks
	public static final int ACE = 1;
	public static final int DEUCE = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;

	public Card(int rank, int suit) {
		assert isValidRank(rank);
		assert isValidSuit(suit);
		this.rank = rank;
		this.suit = suit;
	}

	public int getSuit() {
		return suit;
	}

	public int getRank() {
		return rank;
	}

	public static boolean isValidRank(int rank) {
		return ACE <= rank && rank <= KING;
	}

	public static boolean isValidSuit(int suit) {
		return DIAMONDS <= suit && suit <= SPADES;
	}

	public static String rankToString(int rank) {
		switch (rank) {
		case 1:
			return "Ace";
		case 2:
			return "Duece";
		case 3:
			return "Three";
		case 4:
			return "Four";
		case 5:
			return "Five";
		case 6:
			return "Six";
		case 7:
			return "Seven";
		case 8:
			return "eight";
		case 9:
			return "Nine";
		case 10:
			return "Ten";
		case 11:
			return "Jack";
		case 12:
			return "Queen";
		case 13:
			return "King";
		default:
			// Handle an illegal argument. There are generally two
			// ways to handle invalid arguments, throwing an exception
			// (see the section on Handling Exceptions) or return null
			return null;
		}
	}

	public static String suitToString(int suit) {
		switch (suit) {
		case DIAMONDS:
			return "Diamonds";
		case CLUBS:
			return "Clubs";
		case HEARTS:
			return "Hearts";
		case SPADES:
			return "Spades";
		default:
			return null;
		}
	}

	public static void main(String[] args) {

		// must run program with -ea flag (java -ea ..) to
		// use assert statements
		assert rankToString(ACE) == "Ace";
		assert rankToString(DEUCE) == "Duece";
		assert rankToString(THREE) == "Three";
		assert rankToString(FOUR) == "Four";
		assert rankToString(FIVE) == "Five";
		assert rankToString(SIX) == "Six";
		assert rankToString(SEVEN) == "Seven";
		assert rankToString(EIGHT) == "Eight";
		assert rankToString(NINE) == "Nine";
		assert rankToString(TEN) == "Ten";
		assert rankToString(JACK) == "Jack";
		assert rankToString(QUEEN) == "Queen";
		assert rankToString(KING) == "King";

		assert suitToString(DIAMONDS) == "Diamonds";
		assert suitToString(CLUBS) == "Clubs";
		assert suitToString(HEARTS) == "Hearts";
		assert suitToString(SPADES) == "Spades";
	}

}
