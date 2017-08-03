# Minesweeper
Description

I was tasked with creating the game minesweeper in java. Upon loading the game, the user is prompted for their difficulty (easy, medium, or hard). The difficulty determines the number of bombs, and number of tiles on the board.


What works what does not

Everything should be working fine. Clicking the bombs prompts the user with a Game Over, and clearing out all the squares gives a Win screen. There are some bugs with "click detection" mainly with the smile reset button. Sometimes when you click on it, the click doesn't register, and you have to try again. I think this is mainly an issue with StdDraw, but it could be my code. Another bug is sometimes the win screen doesn't display. I think I fixed this bug, but it's possible it still occurs.


Bonuses

For bonuses, I used a bunch of sprites, and made sure every element of the game had some sort of graphic. Also, I have a timer that counts how long it takes for you to win or lose the game.