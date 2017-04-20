var dealerDisplay1 = document.querySelector("#dealerDisplay1");
var dealerDisplay2 = document.querySelector("#dealerDisplay2");
var dealerNextCard1 = document.querySelector("#dealerNextCard");
var dealerTotal = document.querySelector("#dealerTotal");

var playerDisplay1 = document.querySelector("#playerDisplay1");
var playerDisplay2 = document.querySelector("#playerDisplay2");
var playerNextCard1 = document.querySelector("#playerNextCard");
var playerTotal = document.querySelector("#playerTotal");

var resultDisplay = document.querySelector("#result1");

var stayBtn = document.querySelector("#stay");
var hitBtn = document.querySelector("#hit");
var resetButton = document.querySelector("#reset");

var gameOver = false;

// Card values
var card;
var cardNo = 1;

var cardPull = 0;
var result = 1;

var dealerCardSetup1;
var dealerCardSetup2;
var dealerNextCard;

var playerCardSetup1;
var playerCardSetup2;
var playerNextCard;

var dealerCardTotal = 0;
var playerCardTotal = 0;

// Size of deck array
var cardDeck = new Array(53);

init();

function init() {
  setupDeck();
  setupCards();
  checkBlackJack();
  // resultDisplay.textContent = "test";
}

// 2 loops; 1-13(one suit) and 1-4(4 suits in a deck)
// Then resets values 11-13(face cards) to 10
function setupDeck() {
		for (var i = 0; i < 4; i++) {
			for (var j = 1; j < 14; j++) {
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
function setupCards() {
  dealerCardSetup1 = dealerCardpull();
  playerCardSetup1 = playerCardpull();
  dealerCardSetup2 = dealerCardpull();
  playerCardSetup2 = playerCardpull();
  console.log(dealerCardSetup1);
  console.log(dealerCardSetup2);
  console.log(playerCardSetup1);
  console.log(playerCardSetup2);

  checkAcePlayerSetup();
  checkAceDealerSetup();
}

// Randomly searches through 1 - 52. Uses that number to pull from the deck
// array
// When selected a valid non 0 number, it will pass that value to card
// variable
// Then will set that array value to 0, so it cannot be used again.
function dealerCardpull() {
  do {
     cardPull = getRandomIntInclusive(1,52);
  } while (cardPull === 0 || cardDeck[cardPull] === 0);
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
function playerCardpull() {
  do {
    cardPull = getRandomIntInclusive(1,52);
  } while (cardPull === 0 || cardDeck[cardPull] === 0);
  card = cardDeck[cardPull];
  cardDeck[cardPull] = 0;
  playerCardTotal = playerCardTotal + card;
  checkAcePlayer();
  return card;
}

// Checks if an Ace can be 11 or 1
// If the total minus the ace is less than 11, it will make the ace 11
// If the total is greater than 10, the hand will bust, so ace becomes a 1.
function checkAceDealer() {
	if (card === 1 && (dealerCardTotal - card < 11))
		card = 11;
	if (card === 1 && (dealerCardTotal - card > 10))
		card = 1;
}

// Checks if an Ace can be 11 or 1
// If the total minus the ace is less than 11, it will make the ace 11
// If the total is greater than 10, the hand will bust, so ace becomes a 1.
function checkAcePlayer() {
  if (card === 1 && (playerCardTotal - card < 11))
    card = 11;
  if (card === 1 && (playerCardTotal - card > 10))
    card = 1;
}

// Because my ace value is set as a 1 from the setupCards(); method
// This changes either the first 2 cards to 11
// Only first or second can be 11. If you draw 2 aces, first card will be 11
// Second card will be 1
function checkAcePlayerSetup() {
  if (playerCardSetup1 === 1)
    playerCardSetup1 = 11;
  if (playerCardSetup2 === 1 && playerCardSetup1 != 1)
    playerCardSetup2 = 11;
  playerCardTotal = playerCardSetup1 + playerCardSetup2;
}

// Because my ace value is set as a 1 from the setupCards(); method
// This changes either the first 2 cards to 11
// Only first or second can be 11. If you draw 2 aces, first card will be 11
// Second card will be 1
function checkAceDealerSetup() {
  if (dealerCardSetup1 === 1)
    dealerCardSetup1 = 11;
  if (dealerCardSetup2 === 1 && dealerCardSetup1 != 1)
    dealerCardSetup2 = 11;
  dealerCardTotal = dealerCardSetup1 + dealerCardSetup2;
}

// Checks for blackjack after the dealer and player have received both
// starting cards
// Checks first when both are equal, then only dealer, then only player
function checkBlackJack() {
	if (dealerCardTotal === 21 && playerCardTotal === 21) {
    dealerDisplay1.textContent = dealerCardSetup2;
    dealerTotal.textContent = dealerCardTotal;
    resultDisplay.textContent = "Dealer and Player both have BlackJack, Push!";
    gameOver = true;
	}
	if (dealerCardTotal === 21) {
    dealerDisplay1.textContent = dealerCardSetup1;
    dealerTotal.textContent = dealerCardTotal;
    resultDisplay.textContent = "BlackJack, Dealer Wins!";
    gameOver = true;
	}
	if (playerCardTotal === 21) {
    dealerDisplay1.textContent = dealerCardSetup1;
    dealerTotal.textContent = dealerCardTotal;
    resultDisplay.textContent = "BlackJack, Player Wins!";
    gameOver = true;
	}
  dealerDisplay2.textContent = dealerCardSetup2;
  playerDisplay1.textContent = playerCardSetup1;
  playerDisplay2.textContent = playerCardSetup2;
  playerTotal.textContent = playerCardTotal;
}

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
stayBtn.addEventListener("click", function() {
  if (!gameOver) {
    alert("Good Luck Player!");
    dealerDisplay1.textContent = dealerCardSetup1;
    dealerDisplay2.textContent = dealerCardSetup2;
    dealerTotal.textContent = dealerCardTotal;

		while (dealerCardTotal < 17) {
			dealerNextCard = dealerCardpull();
			if (dealerNextCard === 11)
				dealerCardTotal = dealerCardTotal + 10;
			dealerNextCard1.textContent = dealerNextCard;
			dealerTotal.textContent = dealerCardTotal;
		}
		// This will change the value of an ace(11) from the first card
		// placed
		// If the hand will bust
		if (dealerCardTotal > 21 && dealerCardSetup1 === 11) {
			dealerCardSetup1 = 1;
			dealerCardTotal = (dealerCardTotal - 10);
      dealerNextCard1.textContent = dealerNextCard;
      dealerTotal.textContent = dealerCardTotal;
      dealerDisplay1.textContent = dealerCardSetup1;
		}
		// This will change the value of an ace(11) from the second card
		// placed
		// If the hand will bust
		else if (dealerCardTotal > 21 && dealerCardSetup2 === 11) {
			dealerCardSetup2 = 1;
			dealerCardTotal = (dealerCardTotal - 10);
      dealerNextCard1.textContent = dealerNextCard;
      dealerTotal.textContent = dealerCardTotal;
      dealerDisplay2.textContent = dealerCardSetup2;
		}
		// After loop is done, 4 combinations are played out
		// Dealer Busts, Dealer Wins, Player Wins(by having higher value),
		// and Push
		else if (dealerCardTotal > 21) {
      resultDisplay.textContent = "Dealer Busts, Player Wins!";
      gameOver = true;
    }
    else if (dealerCardTotal < 22 && dealerCardTotal > playerCardTotal) {
      resultDisplay.textContent = "Dealer Wins!";
      gameOver = true;
    }
		else if (dealerCardTotal < 22 && playerCardTotal > dealerCardTotal) {
      resultDisplay.textContent = "Player Wins!";
      gameOver = true;
    }
		else if (dealerCardTotal < 22 && dealerCardTotal === playerCardTotal) {
      resultDisplay.textContent = "Dealer & Player Push.";
      gameOver = true;
    }
  }
});

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
hitBtn.addEventListener("click", function() {
  if (!gameOver) {
    if (playerCardTotal < 22) {
      playerNextCard = playerCardpull();
      if (playerNextCard === 11)
        playerCardTotal = playerCardTotal + 10;
      playerNextCard1.textContent = playerNextCard;
      playerTotal.textContent = playerCardTotal;
    }
    // This will change the value of an ace(11) from the first card
    // placed
    // If the hand will bust
    if (playerCardTotal > 21 && playerCardSetup1 == 11) {
      playerCardSetup1 = 1;
      playerCardTotal = (playerCardTotal - 10);
      playerNextCard1.textContent = playerNextCard;
      playerTotal.textContent = playerCardTotal;
      playerDisplay1.textContent = playerCardSetup1;
    }
    // This will change the value of an ace(11) from the second card
    // placed
    // If the hand will bust
    if (playerCardTotal > 21 && playerCardSetup2 == 11) {
      playerCardSetup2 = 1;
      playerCardTotal = (playerCardTotal - 10);
      playerNextCard1.textContent = playerNextCard;
      playerTotal.textContent = playerCardTotal;
      playerDisplay2.textContent = playerCardSetup2;
    }
    if (playerCardTotal > 21) {
      dealerNextCard1.textContent = dealerNextCard;
      dealerTotal.textContent = dealerCardTotal;
      dealerDisplay1.textContent = dealerCardSetup1;

      playerNextCard1.textContent = playerNextCard;
      playerDisplay2.textContent = playerCardSetup2;
      playerTotal.textContent = playerCardTotal;
      resultDisplay.textContent = "Player Busts, Dealer Wins!";
      gameOver = true;
    }
  }
});

resetButton.addEventListener("click", function() {
  reset();
});


function getRandomIntInclusive(min, max) {
	min = Math.ceil(min);
	max = Math.floor(max);
	return Math.floor(Math.random() * (max - min + 1)) + min;
}

function reset() {
  playerDisplay1.textContent = 0;
  playerDisplay2.textContent = 0;
  playerNextCard1.textContent = 0;
  playerTotal.textContent = 0;

  dealerDisplay1.textContent = 0;
  dealerDisplay2.textContent = 0;
  dealerNextCard1.textContent = 0;
  dealerTotal.textContent = 0;

  resultDisplay.textContent = "";

  gameOver = false;

  init();
}
