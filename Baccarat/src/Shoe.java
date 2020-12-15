import java.util.Collections;

public class Shoe extends CardCollection {

	/*A constructor that allows
	 * the number of decks to be specified as 
	 * 4, 6 or 8.
	 */
	public Shoe(int numDecks) {
		super();
		int i;
		for( i = 0; i < numDecks; i++)
			for(char rank : Card.getRanks())
				for(char suit : Card.getSuits())
					cards.add(new Card(rank,suit));
		
	}
	
	//Remove the ¡®top card¡¯ from the shoe and return it.
	public Card deal() throws IllegalArgumentException {
		if( !cards.isEmpty() ) {
			Card ret = cards.get(0);
			cards.remove(0);
			return ret;
		}else
			throw new IllegalArgumentException();
	}
	
	//Randomly shuffle the cards in the shoe.
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Shoe s = new Shoe(4);
		System.out.println(s.deal());

	}

}
