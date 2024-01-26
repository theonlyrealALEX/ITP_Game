# Maze Runner Game

## Table of Contents


- [Start](#start)
- [Additional Game Mechanics](#additionalgamemechanics)
- [Classes](#classes)
- [Classes/Structure](#classes/structure)


## Start!
### How to run the game?

1. Pull it from the GitHUB.
2. Edit the configurations, make sure to use java 17 SDK with desktop.test.
3. Run the code.

### Start the Game
1. After running the code, the "select screen" with selections of the available maps appear, select the level you want to play.
2. It brings you to the "menu screen", where you can choose to start the game with the selected map or quit game which close the entire game window.

### Play the game
1. The main character can move up, down, left, right, you can control it with the direction keys on the key board.You can't walk through walls.
2. The game starts with 3 lives. To get out of the maze you need to collect one or more keys (the information is provided at HUD which is on the top center of the game screen).
3. You win the game if you are able to collect the sufficient amount of keys and get out of the maze from the exit; you loose the game if you lost all the lives during the game.
4. There are 2 kinds of obstacles that reduces your life, one is the moving animies which detects your location and goes after you, another one is the blue traps which you need to avoid.
5. To increase your life you can walk to the red spinning heart, and your life will increase by 1. The status of your lives and collection of keys is showed at the game HUD.
6. You can pause the game by pressing "ESC" key on your keyboard

### Pause game
1. Click "continue game" to continue the existing game.
2. Click "start again" to start a new game with the selected map.
3. Click "select new map" to end the game and go back to the select screen.
4. Click "quit game" to close the game window.

### Victory
1. If you managed to collect the sufficient amount of keys and get out of the maze, you win the game.
2. Click "choose new map" to select another map.
3. Click "play again" to play the same game again.

### Loose
1. If you loose all the lives inside the maze, you loose the game.
2. Click "play again" to start the same game again.
3. Click "back to menu" to select a new map.


## Additional Game Mechanics
1. Enemies move intelligently towards the main character: All the moving enemies in this game will be moving towards the main character, so it is harder for the player to avoid them.
2. Collectable lives: The lives are collectable by walking throught the spinning red hearts in the maze.



## Classes

Please check out the UML diagram: ([https://img.shields.io/github/license/:user/:repo.svg](https://drive.google.com/file/d/1M7xw0M8C7JkSlSbCO7nkR9EaGFN3kLQ4/view?usp=sharing))

The main Logic of the Game is in the GameScreen class. It handles most of the events and triggers the correct responses. It can be compared to a Controller in the MVC-Model. It also handles the Camera Logic for changing the view when the Player approaches the end of the visible screen.
The GameEngine with its subclasses (GameMap, Player, Enemy, MapObject) is the Model of the game. It is responsible for Modeling the behavior of the Characters and Map. There are static map objects that are not movable (MapObject (Abstract): Trap, Path, Wall, EntryPoint, ExitPoint, …) and movable Objects such as the Player and Enemy. The static objects and Enemies are loaded from the Map-File, or randomly distributed on the Paths. The Player’s coordinates are set to the Location of the EntryPoint.
The Screen classes are responsible for the visualization of the different screens and the HUD (‘View’ in MVC).

## Classes

### Screens
#### GameHUD:

The GameHUD class extends the LibGDX Stage and implements the Screen interface. It utilizes a SpriteBatch for rendering and manages various UI elements, including animations, labels, and camera settings. The HUD displays the player's remaining lives and key status.

Constructor
- GameHUD(MazeRunnerGame game)
Initializes the HUD with default values.
Sets up the layout using a LibGDX Table.
Creates heart and key animations.
Associates the HUD with the main game.
- GameHUD(MazeRunnerGame game, boolean key, int lives)
Overloaded constructor allowing customization of key and lives.
Similar to the default constructor with parameterized key and lives.

Public Methods
- isKey(): boolean
Returns the current key status.
- setKey(boolean key)
Sets the key status.
- getLives(): Integer
Returns the current number of lives.
- decrementsLives()
Decreases the lives count if greater than 0.
- incrementLifes()
Increases the lives count.

Animations
The class includes methods for creating heart and key animations. These animations continuously cycle through frames, providing a dynamic visual effect.


User Interface Elements
The HUD consists of heart and key images, as well as corresponding labels for key status and lives. These elements are organized in a LibGDX Table for proper layout.

Input Processing
- show()
Sets the input processor to the HUD.

Lifecycle Methods
- render(float delta)
Updates and draws the HUD during the game loop.
- resize(int width, int height)
Placeholder for handling screen resizing.
- dispose()
Disposes of resources, including the sprite batch.




#### SelectScreen:

#### MenuScreen:
The MenuScreen class in the Maze Runner Game project is responsible for displaying the main menu of the game. It extends the LibGDX Screen class and sets up the UI components for the menu, including animations and buttons. Below is an overview of the class.

Constructor
- MenuScreen(MazeRunnerGame game)
* Initializes the main menu screen.
* Sets up the camera, viewport, and stage for UI elements.
* Creates UI components, including a title label, animated fire, and buttons to go to the game or quit.

Animations
- createFireAnimation()
* Creates an animated fire image using a sequence of frames.
* Utilizes LibGDX Actions to define the animation sequence.

Input Processing
- show()
* Sets the input processor to the stage, enabling the reception of input events.

Lifecycle Methods
- render(float delta)
* Renders the main menu screen.
* Clears the screen and updates/draws the stage.
resize(int width, int height)
* Updates the stage viewport on screen resize.
- dispose()
* Disposes of resources, including the stage, when the screen is no longer in use.

#### PauseMenuScreen:
#### GameOverScreen:

#### VictoryScreen:


### Model and Controller Classess
MazeRunnerGame
The provided Skeleton Class for the Game

GameEngine
Implements the Model of the Game. Has a GameMap for storing the Map, and also the Player and also Enemies.
 GameMap
Models the static and dynamic game map.

Player
Models the Player
 Enemy
Models the Enemy.

MapObject
Superclass for all static map objects.

