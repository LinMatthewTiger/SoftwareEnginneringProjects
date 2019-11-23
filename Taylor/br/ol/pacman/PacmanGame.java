package br.ol.pacman;

import br.ol.pacman.actor.Background;
import br.ol.pacman.actor.Food;
import br.ol.pacman.actor.GameOver;
import br.ol.pacman.actor.Ghost;
import br.ol.pacman.actor.HUD;
import br.ol.pacman.actor.Initializer;
import br.ol.pacman.actor.OLPresents;
import br.ol.pacman.actor.Pacman;
import br.ol.pacman.actor.Player2;
import br.ol.pacman.actor.Point;
import br.ol.pacman.actor.PowerBall;
import br.ol.pacman.actor.Ready;
import br.ol.pacman.actor.Title;
import br.ol.pacman.infra.Actor;
import br.ol.pacman.infra.Dim;
import br.ol.pacman.infra.Game;
import br.ol.pacman.infra.Points;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * PacmanGame class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */

// Changes Made:
//	-Added a new player2 class and object for initial multiplayer testing
//	-Removed player2 object and implemented 2n Pacman object for Player 2 to use
//	-Made it to where 2 ghosts go after player 1 and 2 go after player 2

public class PacmanGame extends Game implements Serializable {

	// maze[row][col] 
	// 36 x 31 
	// cols: 0-3|4-31|32-35
	public int maze[][] = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
			{1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
			{1,1,1,1,1,3,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,3,1,1,1,1,1},
			{1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
			{1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
			{1,1,1,1,1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1,1,1,1,1},
			{1,1,1,1,1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1,1,1,1,1},
			{1,1,1,1,1,2,2,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,0,1,1,0,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,0,1,1,0,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,0,0,0,0,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,2,2,2,2,2,2,2,0,0,0,1,1,0,0,0,0,1,1,0,0,0,2,2,2,2,2,2,2,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
			{1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
			{1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1},
			{1,1,1,1,1,3,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,3,1,1,1,1,1},
			{1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1},
			{1,1,1,1,1,2,2,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1,1,1,1,1},
			{1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1},
			{1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1},
			{1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
	};

	public static enum State { INITIALIZING, OL_PRESENTS, TITLE, READY, READY2
		, PLAYING, PACMAN_DIED, GHOST_CATCHED, LEVEL_CLEARED, GAME_OVER }

	public State state = State.INITIALIZING;
	public int lives = 6;
	public int score;
	public int hiscore;

	public Ghost catchedGhost;
	public int currentCatchedGhostScoreTableIndex = 0;
	public final int[] catchedGhostScoreTable = { 200, 400, 800, 1600 };

	public int foodCount;
	public int currentFoodCount;

	public PacmanGame() {
		screenSize = new Dim(224, 288);
		screenScale = new Points(2, 2);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		if (this.state != state) {
			this.state = state;
			broadcastMessage("stateChanged");
		}
	}

	public void addScore(int point) {
		score += point;
		if (score > hiscore) {
			hiscore = score;
		}
	}

	public String getScore() {
		String scoreStr = "0000000" + score;
		scoreStr = scoreStr.substring(scoreStr.length() - 7, scoreStr.length());
		return scoreStr;
	}

	public String getHiscore() {
		String hiscoreStr = "0000000" + hiscore;
		hiscoreStr = hiscoreStr.substring(hiscoreStr.length() - 7, hiscoreStr.length());
		return hiscoreStr;
	}

	@Override
	public void init() {
		addAllObjs();
		initAllObjs();
	}

	private void addAllObjs() {
		Pacman pacman = new Pacman(this);
		Pacman pacman2 = new Pacman(this);

		pacman.setPlayerNum(1);
		pacman2.setPlayerNum(2);
		Player2 player2 = new Player2(this);

		actors.add(new Initializer(this));
		actors.add(new OLPresents(this));
		actors.add(new Title(this));
		actors.add(new Background(this));
		foodCount = 0;
		for (int row=0; row<31; row++) {
			for (int col=0; col<36; col++) {
				if (maze[row][col] == 1) {
					maze[row][col] = -1; // wall convert to -1 for ShortestPathFinder
				}
				else if (maze[row][col] == 2) {
					maze[row][col] = 0;
					actors.add(new Food(this, col, row));
					foodCount++;
				}
				else if (maze[row][col] == 3) {
					maze[row][col] = 0;
					actors.add(new PowerBall(this, col, row));
				}
			}
		}
		for (int i=0; i<4; i++) {

			if (i % 2 == 0) {
				// Ghosts that will go after player 1 (Blinky and Inky)
				actors.add(new Ghost(this, pacman, i));
			}
			else {
				// Ghosts that will go after player 2 (Pinky and Clyde)
				actors.add(new Ghost(this, pacman2, i));
			}

		}
		actors.add(pacman);
		actors.add(pacman2);

		actors.add(new Point(this, pacman));
		actors.add(new Ready(this));
		actors.add(new GameOver(this));
		actors.add(new HUD(this));
	}

	private void initAllObjs() {
		for (Actor actor : actors) {
			actor.init();
		}
	}

	// ---

	public void restoreCurrentFoodCount() {
		currentFoodCount = foodCount;
	}

	public boolean isLevelCleared() {
		return currentFoodCount == 0;
	}

	public void startGame() {
		setState(State.READY);
	}

	public void startGhostVulnerableMode() {
		currentCatchedGhostScoreTableIndex = 0;
		broadcastMessage("startGhostVulnerableMode");
	}

	public void ghostCatched(Ghost ghost) {
		catchedGhost = ghost;
		setState(State.GHOST_CATCHED);
	}

	public void nextLife() {
		lives--;
		if (lives == 0) {
			setState(State.GAME_OVER);
		}
		else {
			setState(State.READY2);
		}
	}

	public void levelCleared() {
		setState(State.LEVEL_CLEARED);
	}

	public void nextLevel() {
		setState(State.READY);
	}

	public void returnToTitle() {
		lives = 3;
		score = 0;
		setState(State.TITLE);
	}

}
