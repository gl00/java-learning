package basic.class_object.enum_type;

public class Card3 {

	private final Suit suit;
	private final Rank rank;

	public Card3(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}

	@Override
	public String toString() {
		return rank + " of " + suit;
	}

}
