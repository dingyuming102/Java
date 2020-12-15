
public class BaccaratCard extends Card{

	//Constructors
	public BaccaratCard(char rank, char suit) {
		super(rank, suit);
		// TODO Auto-generated constructor stub
	}

	//Constructors
	public BaccaratCard(String code) {
		super(code);
		// TODO Auto-generated constructor stub
	}


	@Override
	//Override the value method inherited from Card so that it returns the points value of a card in Baccarat.
	public int value() {
		int ret;
		char c = super.getRank();
		
		if( c=='T' || c=='J' || c=='Q' || c=='K')
			ret = 0;
		else if( c == 'A')
			ret = 1;
		else
			ret = c - '0';
		
		return ret;
	}


}
