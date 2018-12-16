package basic.interfaces_inheritance.interfaces.default_method;

import java.util.Comparator;

public class SortByRankThenSuit implements Comparator<Card> {

	@Override
	public int compare(Card o1, Card o2) {
		int compVal = o1.getRank().value() - o2.getRank().value();
		if (compVal != 0) {
			return compVal;
		}
		return compVal = o1.getSuit().value() - o2.getSuit().value();
	}

}
