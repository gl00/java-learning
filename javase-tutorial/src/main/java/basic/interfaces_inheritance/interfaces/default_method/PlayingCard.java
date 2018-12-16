package basic.interfaces_inheritance.interfaces.default_method;

public class PlayingCard implements Card {

	private Rank rank;
	private Suit suit;

	public PlayingCard(Rank rank, Suit suit) {
		super();
		this.rank = rank;
		this.suit = suit;
	}

	@Override
	public Suit getSuit() {
		return suit;
	}

	@Override
	public Rank getRank() {
		return rank;
	}

	@Override
	public int compareTo(Card o) {
		return this.hashCode() - o.hashCode();
	}

	@Override
	public int hashCode() {
		return ((suit.value() - 1) * 13) + rank.value();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayingCard other = (PlayingCard) obj;
		if (rank != other.rank)
			return false;
        return suit == other.suit;
    }

	@Override
	public String toString() {
		return this.rank.text() + " of " + this.suit.text();
	}

	public static void main(String[] args) {
		PlayingCard playingCard1 = new PlayingCard(Rank.ACE, Suit.DIAMONDS);
		PlayingCard playingCard2 = new PlayingCard(Rank.KING, Suit.SPADES);
		System.out.println(playingCard1);
		System.out.println(playingCard2);
	}

}
