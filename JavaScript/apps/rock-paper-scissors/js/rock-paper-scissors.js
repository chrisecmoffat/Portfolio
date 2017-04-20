var rockBtn = document.querySelector("#rock");
var paperBtn = document.querySelector("#paper");
var scissorsBtn = document.querySelector("#scissors");

var result = document.querySelector("#result").id;
var p1choice = document.querySelector("#p1choice");
var p2choice = document.querySelector("#p2choice");

var resetButton = document.querySelector("#reset");
var randomButton = document.querySelector("#random");

var p1Display = document.querySelector("#p1Display");
var p2Display = document.querySelector("#p2Display");
var resultDisplay = document.querySelector("#result");

var winningScoreDisplay = document.querySelector("p span");

var winningScore = 5;
var gameOver = false;

var p1Score = 0;
var p2Score = 0;
var randomNum = 0;
var p1Choose = 0;
var p2Choose = 0;

// 1 - rock
// 2 - paper
// 3 - scissors

rockBtn.addEventListener("click", function() {
  if(!gameOver) {
  	p1Choose = 1;
  	p2Choose = getRandomIntInclusive(1,3);
    compareValues(p1Choose, p2Choose);

    if (p1Score === 3) {
      gameOver = true;
      document.querySelector("#result").id = 'winner';
      resultDisplay.textContent = "You Won!";
      // result = 'winner';
    } else if (p2Score === 3) {
      gameOver = true;
      document.querySelector("#result").id = 'loser';
      resultDisplay.textContent = "You Lost!";
    }
    p1Display.textContent = p1Score;
    p2Display.textContent = p2Score;
  }
});


paperBtn.addEventListener("click", function() {
  if(!gameOver) {
    p1Choose = 2;
    p2Choose = getRandomIntInclusive(1,3);
    compareValues(p1Choose, p2Choose);

    if (p1Score === 3) {
      gameOver = true;
      document.querySelector("#result").id = 'winner';
      resultDisplay.textContent = "You Won!";
    } else if (p2Score === 3) {
      gameOver = true;
      document.querySelector("#result").id = 'loser';
      resultDisplay.textContent = "You Lost!";
    }
    p1Display.textContent = p1Score;
    p2Display.textContent = p2Score;
  }
});


scissorsBtn.addEventListener("click", function() {
  if(!gameOver) {
    p1Choose = 3;
    p2Choose = getRandomIntInclusive(1,3);
    compareValues(p1Choose, p2Choose);

    if (p1Score === 3) {
      gameOver = true;
      document.querySelector("#result").id = 'winner';
      resultDisplay.textContent = "You Won!";
    } else if (p2Score === 3) {
      gameOver = true;
      document.querySelector("#result").id = 'loser';
      resultDisplay.textContent = "You Lost!";
    }
    p1Display.textContent = p1Score;
    p2Display.textContent = p2Score;
  }
});

resetButton.addEventListener("click", function() {
  reset();
});


randomButton.addEventListener("click", function() {
  if(!gameOver) {
    p1Choose = getRandomIntInclusive(1,3);
    p2Choose = getRandomIntInclusive(1,3);
    compareValues(p1Choose, p2Choose);

    if (p1Score === 3) {
      gameOver = true;
      document.querySelector("#result").id = 'winner';
      resultDisplay.textContent = "You Won!";
    } else if (p2Score === 3) {
      gameOver = true;
      document.querySelector("#result").id = 'loser';
      resultDisplay.textContent = "You Lost!";
    }
    p1Display.textContent = p1Score;
    p2Display.textContent = p2Score;
  }
});

function compareValues(p1, p2) {
  p1choice.textContent = playerChoice(p1Choose);
  p2choice.textContent = playerChoice(p2Choose);

  // tie
  if (p1 === p2) {
    resultDisplay.textContent = "Tie";
  }
  // rock
  else if (p1 === 1) {
    if (p2 === 3) {
      resultDisplay.textContent = "Winner";
      p1Score++;
    } else {
      resultDisplay.textContent = "Loser";
      p2Score++;
    }
  }
  // paper
  else if (p1 === 2) {
    if (p2 === 1) {
      resultDisplay.textContent = "Winner";
      p1Score++;
    } else {
      resultDisplay.textContent = "Loser";
      p2Score++;
    }
  }
  // scissors
  else {
    if (p2 === 2) {
      resultDisplay.textContent = "Winner";
      p1Score++;
    } else {
      resultDisplay.textContent = "Loser";
      p2Score++;
    }
  }
}

function playerChoice(num) {
  if (num === 1) {
    return "Rock";
  } else if (num === 2) {
    return "Paper";
  } else {
    return "Scisssors";
  }
}

function getRandomIntInclusive(min, max) {
	min = Math.ceil(min);
	max = Math.floor(max);
	return Math.floor(Math.random() * (max - min + 1)) + min;
}

function reset() {
  if (p1Score === 3)
    document.querySelector("#winner").id = 'result';
  else if (p2Score === 3)
    document.querySelector("#loser").id = 'result';
  p1Score = 0;
  p2Score = 0;
  p1Display.textContent = p1Score;
  p2Display.textContent = p2Score;
  p1choice.textContent = " ";
  p2choice.textContent = " ";
  resultDisplay.textContent = " ";
  gameOver = false;
}
