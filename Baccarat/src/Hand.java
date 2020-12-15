
public class Hand extends CardCollection {

	@Override
	/*
	 * override the default version of the toString method with a new version that returns
	 * a string containing two-character representations of each card, 
	 * separated from each other by a space
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for( Card i : cards) {
			sb.append(i);
			sb.append(" ");
		}
		
		return sb.toString();
	}
	
	/*Add to Hand a method called value 
	 * that returns the total points value of the cards in the hand,
	 * according to the rules of Baccarat.
	 */
	public int value() {
		int ret = 0;
		
		for( Card i : cards) {
			ret += i.value();
		}
		
		ret %= 10;
		
		return ret;
	}
	
	public static void main(String[] args) {
		Hand a = new Hand();
		a.add(new Card("AC"));
		a.add(new Card("4D"));
		a.add(new Card("JS"));
		
		System.out.println(a.value());
		
	}

}
