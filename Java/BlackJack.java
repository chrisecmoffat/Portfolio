import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//QUICK RUNDOWN:-------------------------------------------------------------
//Single deck, 2 cards are dealt for each player and dealer
//Ace values are 1 in deck array, changes to 11 accordingly
//Dealer second card and total are hidden until user selects STAY
//When new cards are pulled, that location in array sets value to 0
//Therefore card can be pulled only once
//Ace values are again checked on the new incoming cards and change according
//First and second cards are dynamic ace values
//Can change back to a 1 if total value is going to BUST
//Used deck gets printed to console to show cards used and zeroed out
//END RUNDOWN:----------------------------------------------------------------

public class blackJackGUI extends JFrame implements ActionListener {

	// Random number generator object
	Random generator = new Random();

	// Card values
	int card;
	int cardNo = 1;
	int cardPull = 0;
	int result = 1;
	int dealerCardSetup1;
	int dealerCardSetup2;
	int dealerNextCard;
	int dealerCardTotal = 0;
	int playerCardSetup1;
	int playerCardSetup2;
	int playerNextCard;
	int playerCardTotal = 0;

	// Size of deck array
	int[] cardDeck = new int[53];

	// Button array
	JButton[] btns = { new JButton("Hit Me"), new JButton("Stay") };

	// Labels array
	JLabel[] labels = { new JLabel("Dealer"), new JLabel("Player"),
			new JLabel(" Cards Showing"), new JLabel("TOTAL"),
			new JLabel("Card 1"), new JLabel("Card 2"),
			new JLabel("Next Card"), new JLabel("TOTAL"), new JLabel("Card 1"),
			new JLabel("Card 2"), new JLabel("Next Card"), new JLabel(""),
			new JLabel(""), new JLabel(""), new JLabel(""), new JLabel(""),
			new JLabel(""), new JLabel(""), new JLabel(""),
			new JLabel(new ImageIcon("strategycard.jpg")) };

	public blackJackGUI() {

		// Frame attributes
		setSize(600, 377);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Single Deck Black Jack");

		// Creating panels
		JPanel north = new JPanel(new FlowLayout());
		JPanel west = new JPanel(new GridLayout(2, 1));
		JPanel east = new JPanel(new FlowLayout());
		JPanel south = new JPanel(new FlowLayout());
		JPanel centre = new JPanel(new BorderLayout());
		JPanel northCentre = new JPanel(new GridLayout(2, 4));
		JPanel southCentre = new JPanel(new GridLayout(2, 4));
		JPanel centreCentre = new JPanel(new BorderLayout());

		// Setting background colours for each panel
		north.setBackground(Color.RED);
		south.setBackground(Color.GREEN);
		east.setBackground(Color.BLACK);
		centre.setBackground(Color.BLACK);
		northCentre.setBackground(Color.BLACK);
		southCentre.setBackground(Color.BLACK);
		centreCentre.setBackground(Color.BLACK);

		// Changing font size for buttons
		labels[2].setFont(labels[2].getFont().deriveFont(42.0f));
		labels[11].setFont(labels[2].getFont().deriveFont(21.0f));
		labels[12].setFont(labels[2].getFont().deriveFont(21.0f));
		labels[13].setFont(labels[2].getFont().deriveFont(21.0f));
		labels[14].setFont(labels[2].getFont().deriveFont(21.0f));
		labels[15].setFont(labels[2].getFont().deriveFont(21.0f));
		labels[16].setFont(labels[2].getFont().deriveFont(21.0f));
		labels[17].setFont(labels[2].getFont().deriveFont(21.0f));
		labels[18].setFont(labels[2].getFont().deriveFont(21.0f));
		labels[3].setFont(labels[2].getFont().deriveFont(16.0f));
		labels[4].setFont(labels[2].getFont().deriveFont(16.0f));
		labels[5].setFont(labels[2].getFont().deriveFont(16.0f));
		labels[6].setFont(labels[2].getFont().deriveFont(16.0f));
		labels[7].setFont(labels[2].getFont().deriveFont(16.0f));
		labels[8].setFont(labels[2].getFont().deriveFont(16.0f));
		labels[9].setFont(labels[2].getFont().deriveFont(16.0f));
		labels[10].setFont(labels[2].getFont().deriveFont(16.0f));

		// Changing colour for button text
		labels[2].setForeground(Color.WHITE);
		labels[3].setForeground(Color.WHITE);
		labels[4].setForeground(Color.WHITE);
		labels[5].setForeground(Color.WHITE);
		labels[6].setForeground(Color.WHITE);
		labels[7].setForeground(Color.WHITE);
		labels[8].setForeground(Color.WHITE);
		labels[9].setForeground(Color.WHITE);
		labels[10].setForeground(Color.WHITE);
		labels[11].setForeground(Color.WHITE);
		labels[12].setForeground(Color.WHITE);
		labels[13].setForeground(Color.WHITE);
		labels[14].setForeground(Color.WHITE);
		labels[15].setForeground(Color.WHITE);
		labels[16].setForeground(Color.WHITE);
		labels[17].setForeground(Color.WHITE);
		labels[18].setForeground(Color.WHITE);

		// Adding panels and buttons to frame
		north.add(labels[0]);
		add(north, BorderLayout.NORTH);

		south.add(labels[1]);
		add(south, BorderLayout.SOUTH);

		centreCentre.add(labels[2], BorderLayout.CENTER);
		centre.add(centreCentre, BorderLayout.CENTER);

		northCentre.add(labels[3]);
		northCentre.add(labels[4]);
		northCentre.add(labels[5]);
		northCentre.add(labels[6]);
		northCentre.add(labels[11]);
		northCentre.add(labels[12]);
		northCentre.add(labels[13]);
		northCentre.add(labels[14]);
		centre.add(northCentre, BorderLayout.NORTH);

		southCentre.add(labels[7]);
		southCentre.add(labels[8]);
		southCentre.add(labels[9]);
		southCentre.add(labels[10]);
		southCentre.add(labels[15]);
		southCentre.add(labels[16]);
		southCentre.add(labels[17]);
		southCentre.add(labels[18]);
		centre.add(southCentre, BorderLayout.SOUTH);

		add(centre, BorderLayout.CENTER);

		west.add(btns[1]);
		west.add(btns[0]);
		add(west, BorderLayout.WEST);

		east.add(labels[19]);
		add(east, BorderLayout.EAST);

		setVisible(true);

		// Add action listener for each button
		btns[0].addActionListener(this);
		btns[1].addActionListener(this);

		// Sets the deck and draws the first 2 cards from methods
		setupDeck();
		setupCards();

		// This sets the JLabels to each card value
		labels[12].setText(Integer.toString(dealerCardSetup1));
		labels[13].setText("XX");
		labels[11].setText("XX");

		labels[16].setText(Integer.toString(playerCardSetup1));
		labels[17].setText(Integer.toString(playerCardSetup2));
		labels[15].setText(Integer.toString(playerCardTotal));

		// Checks for blackjack in dealer and players first 2 cards
		checkBlackJack();
	}

	// 2 loops; 1-13(one suit) and 1-4(4 suits in a deck)
	// Then resets values 11-13(face cards) to 10
	public void setupDeck() {
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < 14; j++) {
				cardDeck[cardNo] = j;
				cardNo++;
			}
		}
		cardDeck[11] = 10;
		cardDeck[12] = 10;
		cardDeck[13] = 10;
		cardDeck[24] = 10;
		cardDeck[25] = 10;
		cardDeck[26] = 10;
		cardDeck[37] = 10;
		cardDeck[38] = 10;
		cardDeck[39] = 10;
		cardDeck[50] = 10;
		cardDeck[51] = 10;
		cardDeck[52] = 10;
	}

	// This calls my cardpull method for respective dealer and player
	// Places these valid values into 1 and 2 cards, checks for ace
	// Will alter card value accordingly
	public void setupCards() {
		dealerCardSetup1 = dealerCardpull();
		playerCardSetup1 = playerCardpull();
		dealerCardSetup2 = dealerCardpull();
		playerCardSetup2 = playerCardpull();
		checkAcePlayerSetup();
		checkAceDealerSetup();
	}

	// Randomly searches through 1 - 52. Uses that number to pull from the deck
	// array
	// When selected a valid non 0 number, it will pass that value to card
	// variable
	// Then will set that array value to 0, so it cannot be used again.
	public int dealerCardpull() {
		do {
			cardPull = generator.nextInt(53);
		} while (cardPull == 0 || cardDeck[cardPull] == 0);
		card = cardDeck[cardPull];
		cardDeck[cardPull] = 0;
		dealerCardTotal = dealerCardTotal + card;
		checkAceDealer();
		return card;
	}

	// Randomly searches through 1 - 52. Uses that number to pull from the deck
	// array
	// When selected a valid non 0 number, it will pass that value to card
	// variable
	// Then will set that array value to 0, so it cannot be used again.
	public int playerCardpull() {
		do {
			cardPull = generator.nextInt(53);
		} while (cardPull == 0 || cardDeck[cardPull] == 0);
		card = cardDeck[cardPull];
		cardDeck[cardPull] = 0;
		playerCardTotal = playerCardTotal + card;
		checkAcePlayer();
		return card;
	}

	// Checks if an Ace can be 11 or 1
	// If the total minus the ace is less than 11, it will make the ace 11
	// If the total is greater than 10, the hand will bust, so ace becomes a 1.
	public void checkAceDealer() {
		if (card == 1 && (dealerCardTotal - card < 11))
			card = 11;
		if (card == 1 && (dealerCardTotal - card > 10))
			card = 1;
	}

	// Checks if an Ace can be 11 or 1
	// If the total minus the ace is less than 11, it will make the ace 11
	// If the total is greater than 10, the hand will bust, so ace becomes a 1.
	public void checkAcePlayer() {
		if (card == 1 && (playerCardTotal - card < 11))
			card = 11;
		if (card == 1 && (playerCardTotal - card > 10))
			card = 1;
	}

	// Because my ace value is set as a 1 from the setupCards(); method
	// This changes either the first 2 cards to 11
	// Only first or second can be 11. If you draw 2 aces, first card will be 11
	// Second card will be 1
	public void checkAcePlayerSetup() {
		if (playerCardSetup1 == 1)
			playerCardSetup1 = 11;
		if (playerCardSetup2 == 1 && playerCardSetup1 != 1)
			playerCardSetup2 = 11;
		playerCardTotal = playerCardSetup1 + playerCardSetup2;
	}

	// Because my ace value is set as a 1 from the setupCards(); method
	// This changes either the first 2 cards to 11
	// Only first or second can be 11. If you draw 2 aces, first card will be 11
	// Second card will be 1
	public void checkAceDealerSetup() {
		if (dealerCardSetup1 == 1)
			dealerCardSetup1 = 11;
		if (dealerCardSetup2 == 1 && dealerCardSetup1 != 1)
			dealerCardSetup2 = 11;
		dealerCardTotal = dealerCardSetup1 + dealerCardSetup2;
	}

	// Checks for blackjack after the dealer and player have received both
	// starting cards
	// Checks first when both are equal, then only dealer, then only player
	public void checkBlackJack() {
		if (dealerCardTotal == 21 && playerCardTotal == 21) {
			labels[13].setText(Integer.toString(dealerCardSetup2));
			labels[11].setText(Integer.toString(dealerCardTotal));
			delay(1000);
			JOptionPane.showMessageDialog(null,
					"Dealer and Payer have **BLACKJACK BLACKJACK**, Push!");
			result = JOptionPane.showConfirmDialog(null,
					"Would You Like To Play Again?",
					"Would You Like To Continue?", JOptionPane.YES_NO_OPTION);
			dispose();
			if (result == 0)
				mainJack.main(null);
		}
		if (dealerCardTotal == 21) {
			labels[13].setText(Integer.toString(dealerCardSetup2));
			labels[11].setText(Integer.toString(dealerCardTotal));
			delay(1000);
			JOptionPane
					.showMessageDialog(null,
							"**BLACKJACK BLACKJACK** DEALER WINS!! Sorry, Player Loses!");
			result = JOptionPane.showConfirmDialog(null,
					"Would You Like To Play Again?",
					"Would You Like To Continue?", JOptionPane.YES_NO_OPTION);
			dispose();
			if (result == 0)
				mainJack.main(null);
		}
		if (playerCardTotal == 21) {
			labels[13].setText(Integer.toString(dealerCardSetup2));
			labels[11].setText(Integer.toString(dealerCardTotal));
			delay(1000);
			JOptionPane.showMessageDialog(null,
					"**BLACKJACK BLACKJACK** PLAYER WINS!!");
			result = JOptionPane.showConfirmDialog(null,
					"Would You Like To Play Again?",
					"Would You Like To Continue?", JOptionPane.YES_NO_OPTION);
			dispose();
			if (result == 0)
				mainJack.main(null);
		}
	}

	// Delay method
	public void delay(int delay) {
		try {

			Thread.sleep(delay);// sleeps for 'delay' milliseconds
		} catch (InterruptedException e) {
		}
	}

	// Explanation method in order to show single deck takes the respective
	// cards only
	public void showDeck() {
		for (int i = 1; i < 53; i++)
			System.out.print(cardDeck[i] + " ");
		System.out.println(" ");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ACTION LISTENTER FOR STAY BUTTON
		// When player hits the Stay button, the hidden cards of the dealer show
		// up
		// Followed by a popup and a delay so the player can see what he is up
		// against
		// If the dealer is less than 17, it will loop into pulling the next
		// available
		// Random card, still checking for aces along the way
		// Ace checker(changer) is in the CardPull(); method already
		// If an ace comes alone, to counter the total, if statement adds 10 if
		// next card
		// Is 11, which would be changed by the ace checker
		// Then setting the text to the JLabels for the player to see total and
		// next card
		if (e.getActionCommand() == "Stay") {
			labels[13].setText(Integer.toString(dealerCardSetup2));
			labels[11].setText(Integer.toString(dealerCardTotal));
			JOptionPane.showMessageDialog(null, "Good Luck Player!");
			delay(1000);
			while (dealerCardTotal < 17) {
				dealerNextCard = dealerCardpull();
				if (dealerNextCard == 11)
					dealerCardTotal = dealerCardTotal + 10;
				labels[14].setText(Integer.toString(dealerNextCard));
				labels[11].setText(Integer.toString(dealerCardTotal));
				delay(1000);
			}
			// This will change the value of an ace(11) from the first card
			// placed
			// If the hand will bust
			if (dealerCardTotal > 21 && dealerCardSetup1 == 11) {
				dealerCardSetup1 = 1;
				dealerCardTotal = (dealerCardTotal - 10);
				labels[14].setText(Integer.toString(playerNextCard));
				labels[11].setText(Integer.toString(playerCardTotal));
				labels[12].setText(Integer.toString(playerCardSetup1));
			}
			// This will change the value of an ace(11) from the second card
			// placed
			// If the hand will bust
			if (dealerCardTotal > 21 && dealerCardSetup2 == 11) {
				dealerCardSetup2 = 1;
				dealerCardTotal = (dealerCardTotal - 10);
				labels[14].setText(Integer.toString(dealerNextCard));
				labels[11].setText(Integer.toString(dealerCardTotal));
				labels[13].setText(Integer.toString(dealerCardSetup2));
			}
			// After loop is done, 4 combinations are played out
			// Dealer Busts, Dealer Wins, Player Wins(by having higher value),
			// and Push
			if (dealerCardTotal > 21) {
				JOptionPane.showMessageDialog(null,
						"Dealer Busts, Player Wins!");
				showDeck(); // Method to show deck array with zeroed deck values
				result = JOptionPane.showConfirmDialog(null,
						"Would You Like To Play Again?",
						"Would You Like To Continue?",
						JOptionPane.YES_NO_OPTION);
				dispose();
				if (result == 0)
					mainJack.main(null);
			}
			if (dealerCardTotal < 22 && dealerCardTotal > playerCardTotal) {
				JOptionPane.showMessageDialog(null,
						"Dealer Wins! Sorry, Player Loses!");
				showDeck(); // Method to show deck array with zeroed deck values
				result = JOptionPane.showConfirmDialog(null,
						"Would You Like To Play Again?",
						"Would You Like To Continue?",
						JOptionPane.YES_NO_OPTION);
				dispose();
				if (result == 0)
					mainJack.main(null);
			}
			if (dealerCardTotal < 22 && dealerCardTotal < playerCardTotal) {
				JOptionPane.showMessageDialog(null,
						"Congratulations, Player Wins!");
				showDeck(); // Method to show deck array with zeroed deck values
				result = JOptionPane.showConfirmDialog(null,
						"Would You Like To Play Again?",
						"Would You Like To Continue?",
						JOptionPane.YES_NO_OPTION);
				dispose();
				if (result == 0)
					mainJack.main(null);
			}
			if (dealerCardTotal < 22 && dealerCardTotal == playerCardTotal) {
				JOptionPane.showMessageDialog(null, "Dealer & Player Push.");
				showDeck(); // Method to show deck array with zeroed deck values
				result = JOptionPane.showConfirmDialog(null,
						"Would You Like To Play Again?",
						"Would You Like To Continue?",
						JOptionPane.YES_NO_OPTION);
				dispose();
				if (result == 0)
					mainJack.main(null);
			}
		}
		// ACTION LISTENTER FOR HIT ME BUTTON
		// When player hits the Hit Me button, checks if total is under 22
		// Pulls random card, still checking for aces along the way
		// Ace checker(changer) is in the CardPull(); method already
		// If an ace comes alone, to counter the total, if statement adds 10 if
		// next card
		// Is 11, which would be changed by the ace checker
		// Then setting the text to the JLabels for the player to see total and
		// next card
		// When player goes over 21 and busts, dealer cards are revealed
		if (e.getActionCommand() == "Hit Me") {
			if (playerCardTotal < 22) {
				playerNextCard = playerCardpull();
				if (playerNextCard == 11)
					playerCardTotal = playerCardTotal + 10;
				labels[18].setText(Integer.toString(playerNextCard));
				labels[15].setText(Integer.toString(playerCardTotal));
			}
			// This will change the value of an ace(11) from the first card
			// placed
			// If the hand will bust
			if (playerCardTotal > 21 && playerCardSetup1 == 11) {
				playerCardSetup1 = 1;
				playerCardTotal = (playerCardTotal - 10);
				labels[18].setText(Integer.toString(playerNextCard));
				labels[15].setText(Integer.toString(playerCardTotal));
				labels[16].setText(Integer.toString(playerCardSetup1));
			}
			// This will change the value of an ace(11) from the second card
			// placed
			// If the hand will bust
			if (playerCardTotal > 21 && playerCardSetup2 == 11) {
				playerCardSetup2 = 1;
				playerCardTotal = (playerCardTotal - 10);
				labels[18].setText(Integer.toString(playerNextCard));
				labels[15].setText(Integer.toString(playerCardTotal));
				labels[17].setText(Integer.toString(playerCardSetup2));
			}
			if (playerCardTotal > 21) {
				labels[13].setText(Integer.toString(dealerCardSetup2));
				labels[11].setText(Integer.toString(dealerCardTotal));
				JOptionPane.showMessageDialog(null,
						"Player Busts! Sorry, You Lose!");
				showDeck(); // Method to show deck array with zeroed deck values
				result = JOptionPane.showConfirmDialog(null,
						"Would You Like To Play Again?",
						"Would You Like To Continue?",
						JOptionPane.YES_NO_OPTION);
				dispose();
				if (result == 0)
					mainJack.main(null);
			}
		}
	}
}
