This project covers all the required features and functionality :

1.Game Window and Elements: The game window is created using a JFrame, and the game elements (paddle, ball, and bricks) are represented by Rectangle objects.
2.Player Input: The keyPressed method handles player input for moving the paddle left or right.
3.Game Initialization: The BrickBreakerGame constructor initializes the game elements, sets up the key listener, and starts the game loop thread.
4.Brick Initialization: The initializeBricks method creates a 2D array of Rectangle objects representing the bricks, with the number of rows and columns determined by the current level.
5.Rendering: The paint method is overridden to render the game elements on the game window. It also displays the score, lives, and level information.
6.Game Loop: The run method is the game loop that continuously updates the game state by calling the moveBall, checkCollisions, and checkGameState methods.
7.Ball Movement: The moveBall method updates the ball's position and checks for collisions with the walls.
8.Collision Detection: The checkCollisions method detects collisions between the ball and the paddle, as well as the ball and the bricks. It also handles brick removal, score increments, and power-up spawning.
9.Game State Management: The checkGameState method checks for game over conditions (when the player loses all lives), level completion (when all bricks are broken), and applies power-ups if available.
10.Power-Ups: The powerUpType variable determines the type of power-up (expanding or shrinking the paddle), which is applied in the checkGameState method.
11.Ball Reset: The resetBallPosition method resets the ball's position and velocity when a new life or level starts.
12.Main Method: The main method creates the JFrame and adds the BrickBreakerGame instance to it, making the game visible.
This implementation covers all the mentioned project details, including paddle and ball gameplay, brick arrangements, score tracking, power-ups, multiple levels with increasing difficulty, and game over conditions.





