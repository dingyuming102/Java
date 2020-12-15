import java.util.Scanner;

public class Game {
	private Shoe shoe;
	private Hand player = new Hand();
	private Hand banker = new Hand();
	
	

	public Game(int numDecks) {
		this.shoe = new Shoe(numDecks);
		shoe.shuffle();
	}
	
	public void nuturalcards(Hand a) {
		a.add(shoe.deal());
		a.add(shoe.deal());
	}
	
	/* If player satisfy stand,return 1
	 * else return 0.
	 */
	public boolean stand(Hand a) {
		if( a.size()==2 && a.value() > 5 )
			return true;
		else
			return false;
	}	
	
	/* Plays a realistic round of Baccarat,
	 * following the full Punto Banco rules.
	 */
	public void playRound() {
		//StringBuffer sb = new StringBuffer();
		this.nuturalcards(player);
		this.nuturalcards(banker);
		System.out.print("Player: "+player+"= "+player.value()+"\n");
		System.out.print("Banker: "+banker+"= "+banker.value()+"\n");
		if(this.stand(player)) {
			if(!this.stand(banker))
				System.out.print("Dealing third card to banker...\n");
				banker.add(shoe.deal());
		}else {
			System.out.print("Dealing third card to player...\n");
			Card third = shoe.deal();
			player.add(third);
			if(banker.value() <= 2) {
				System.out.print("Dealing third card to banker...\n");
				banker.add(shoe.deal());
			}else if(banker.value() == 3 && third.value() != 8) {
				System.out.print("Dealing third card to banker...\n");
				banker.add(shoe.deal());
			}else if(banker.value() == 4 && third.value() >=2 && third.value() <=7) {
				System.out.print("Dealing third card to banker...\n");
				banker.add(shoe.deal());
			}else if(banker.value() == 5 && third.value() >=4 && third.value() <=7) {
				System.out.print("Dealing third card to banker...\n");
				banker.add(shoe.deal());
			}else if(banker.value() == 6 && third.value() >=6 && third.value() <=7) {
				System.out.print("Dealing third card to banker...\n");
				banker.add(shoe.deal());
			}else if(banker.value() == 6 && third.value() >=6 && third.value() <=7) {
				System.out.print("Dealing third card to banker...\n");
				banker.add(shoe.deal());
			}
		}
		
		System.out.print("Player: "+player+"= "+player.value()+"\n");
		System.out.print("Banker: "+banker+"= "+banker.value()+"\n");
		if(banker.value() == player.value())
			System.out.print("Tie!\n");
		else if(banker.value() >= player.value())
			System.out.print("Banker win!\n");
		else if(banker.value() <= player.value())
			System.out.print("Player win!\n");
		
		//System.out.print(sb);
	}
	
	/*
	 * repeatedly calls playRound until the shoe is exhausted.
	 * Once thegame finished,
	 * display summary statistics showing the number of roundsplayed,
	 * number of player wins, number of banker wins and number of ties.
	 */
	public void play() {
		int player_win = 0;
		int banker_win = 0;
		int ties = 0;
		int i = 0;
		try {
			for(i=0;true;i++) {
				player.discard();
				banker.discard();
				System.out.println("Round: "+(i+1));
				this.playRound();
				if(banker.value() == player.value())
					ties++;
				else if(banker.value() >= player.value())
					banker_win++;
				else if(banker.value() <= player.value())
					player_win++;
				
				
			}
		}catch (IllegalArgumentException e) {
			System.out.println(i+" rounds played");
			System.out.println(player_win+" player wins");
			System.out.println(banker_win+" banker wins");
			System.out.println(ties+" ties");
		}
	}
	
	/*
	 * End each round by asking the user 
	 * if they want to play another round, 
	 * terminating thegame if they respond negatively
	 */
	public void playWithPrompt() {
		int player_win = 0;
		int banker_win = 0;
		int ties = 0;
		int i = 0;
		String prompt = "y";
		Scanner in = new Scanner(System.in);
		try {
			for(i=0;prompt.equals("y");i++) {
				player.discard();
				banker.discard();
				System.out.println("Round: "+(i+1));
				this.playRound();
				if(banker.value() == player.value())
					ties++;
				else if(banker.value() >= player.value())
					banker_win++;
				else if(banker.value() <= player.value())
					player_win++;
				
				System.out.print("Another round?(y/n): ");
				prompt=in.nextLine();
				while(!prompt.equals("y") && !prompt.equals("n"))
					prompt=in.nextLine();
			}
			System.out.println(i+" rounds played");
			System.out.println(player_win+" player wins");
			System.out.println(banker_win+" banker wins");
			System.out.println(ties+" ties");
		}catch (IllegalArgumentException e) {
			System.out.println(i+" rounds played");
			System.out.println(player_win+" player wins");
			System.out.println(banker_win+" banker wins");
			System.out.println(ties+" ties");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Testing 1: test that calling the value method returns the expected results.");
		BaccaratCard a = new BaccaratCard('A','C');
		BaccaratCard b = new BaccaratCard('4','D');
		BaccaratCard c = new BaccaratCard('Q','S');
		
		System.out.println(a.value());
		System.out.println(b.value());
		System.out.println(c.value());
		
		System.out.println("Testing 2: test the hand¡¯s contents and value.");
		
		Hand h = new Hand();
		BaccaratCard d = new BaccaratCard("AC");
		BaccaratCard e = new BaccaratCard('4','D');
		h.add(d);
		h.add(e);
		System.out.println(h);
		System.out.println(h.value());
		BaccaratCard f = new BaccaratCard("KD");
		h.add(f);
		System.out.println(h);
		System.out.println(h.value());
		
		System.out.println("Testing 3:test playRound");
		Game game1 = new Game(4);
		game1.playRound();
		
		System.out.println("Testing 4: test play");
		
		Game game2 = new Game(8);
		game2.play();
		
		System.out.println("Testing 5: test playPrompt");
		Game game3 = new Game(8);
		game3.playWithPrompt();
		
		
	}

}
