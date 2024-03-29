package br.ol.pacman.actor;

import br.ol.pacman.PacmanActor;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanGame.State;
import br.ol.pacman.infra.ShortestPathFinder;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Ghost class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */

// Changes Made:
//	-Inky now uses the same assets as Blinky (red)
//	-Clyde now uses the same assets as Inky (cyan/blue)
//	-Clyde and Inky now flash different colors when vulnerable
//	 to help Player 2 distinguish which ghosts are their targets.

public class Ghost extends PacmanActor {
    
    public Pacman pacman;
    public int type;
    public Point[] initialPositions = { 
        new Point(18, 11), new Point(16, 14), 
        new Point(18, 14), new Point(20, 14)};
    public int cageUpDownCount;

    public static enum Mode { CAGE, NORMAL, VULNERABLE, DIED }
    public Mode mode = Mode.CAGE;
    
    public int dx;
    public int dy;
    public int col;
    public int row;
    
    public int direction = 0;
    public int lastDirection;
    
    public List<Integer> desiredDirections = new ArrayList<Integer>();
    public int desiredDirection;
    public static final int[] backwardDirections = { 2, 3, 0, 1 };
    
    public long vulnerableModeStartTime;
    public boolean markAsVulnerable;
    
    // in this version, i'm using path finder just to return the ghost to the center (cage)
    public ShortestPathFinder pathFinder; 
    
    public Ghost(PacmanGame game, Pacman pacman, int type) {
        super(game);
        this.pacman = pacman;
        this.type = type;
        this.pathFinder = new ShortestPathFinder(game.maze);
    }

    private void setMode(Mode mode) {
        this.mode = mode;
        modeChanged();
    }
    
    @Override
    public void init() {
        String[] ghostFrameNames = new String[8 + 4 + 4];
        for (int i=0; i<8; i++) {
            ghostFrameNames[i] = "/res/ghost_" + type + "_" + i + ".png";
        }
        
        if (type % 2 == 0) {
        	// Ghosts that go after Player 1 (or Default)
        	for (int i=0; i<4; i++) {
                ghostFrameNames[8 + i] = "/res/ghost_vulnerable_" + i + ".png";
            }
        }
        else {
        	// Ghosts that go after Player 2
        	for (int i=0; i<4; i++) {
                ghostFrameNames[8 + i] = "/res/ghost_vulnerabl2_" + i + ".png";
            }	
        }
        
        
//        for (int i=0; i<4; i++) {
//            ghostFrameNames[8 + i] = "/res/ghost_vulnerable_" + i + ".png";
//        }
        for (int i=0; i<4; i++) {
            ghostFrameNames[12 + i] = "/res/ghost_died_" + i + ".png";
        }
        loadFrames(ghostFrameNames);
        collider = new Rectangle(0, 0, 8, 8);
        setMode(Mode.CAGE);
    }
    
    private int getTargetX(int col) {
        return col * 8 - 3 - 32;
    }

    private int getTargetY(int row) {
        return (row + 3) * 8 - 2;
    }

    public void updatePosition() {
        x = getTargetX(col);
        y = getTargetY(row);
    }
    
    private void updatePosition(int col, int row) {
        this.col = col;
        this.row = row;
        updatePosition();
    }
    
    private boolean moveToTargetPosition(int targetX, int targetY, int velocity) {
        int sx = (int) (targetX - x);
        int sy = (int) (targetY - y);
        int vx = Math.abs(sx) < velocity ? Math.abs(sx) : velocity;
        int vy = Math.abs(sy) < velocity ? Math.abs(sy) : velocity;
        int idx = vx * (sx == 0 ? 0 : sx > 0 ? 1 : -1);
        int idy = vy * (sy == 0 ? 0 : sy > 0 ? 1 : -1);
        x += idx;
        y += idy;
        return sx != 0 || sy != 0;
    }

    private boolean moveToGridPosition(int col, int row, int velocity) {
        int targetX = getTargetX(col);
        int targetY = getTargetY(row);
        return moveToTargetPosition(targetX, targetY, velocity);
    }
    
    private void adjustHorizontalOutsideMovement() {
        if (col == 1) {
            col = 34;
            x = getTargetX(col);
        }
        else if (col == 34) {
            col = 1;
            x = getTargetX(col);
        }
    }
    
    @Override
    public void updateTitle() {
        int frameIndex = 0;
        x = pacman.x + 17 + 17 * type;
        y = 200;
        if (pacman.direction == 0) {
            frameIndex = 8 + (int) (System.nanoTime() * 0.00000001) % 2;
        }
        else if (pacman.direction == 2) {
            frameIndex = 2 * pacman.direction + (int) (System.nanoTime() * 0.00000001) % 2;
        }
        frame = frames[frameIndex];
    }
    
    @Override
    public void updatePlaying() {
        switch (mode) {
            case CAGE: updateGhostCage(); break;
            case NORMAL: updateGhostNormal(); break;
            case VULNERABLE: updateGhostVulnerable(); break;
            case DIED: updateGhostDied(); break;
        }
        updateAnimation();
    }

    public void updateAnimation() {
        int frameIndex = 0;
        switch (mode) {
            case CAGE: 
            case NORMAL:
                frameIndex = 2 * direction + (int) (System.nanoTime() * 0.00000001) % 2;
                if (!markAsVulnerable) {
                    break;
                }
            case VULNERABLE:
                if (System.currentTimeMillis() - vulnerableModeStartTime > 5000) {
                    frameIndex = 8 + (int) (System.nanoTime() * 0.00000002) % 4;
                }
                else {
                    frameIndex = 8 + (int) (System.nanoTime() * 0.00000001) % 2;
                }
                break;
            case DIED:
                frameIndex = 12 + direction;
                break;
        }
        frame = frames[frameIndex];
    }

    private void updateGhostCage() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    Point initialPosition = initialPositions[type];
                    updatePosition(initialPosition.x, initialPosition.y);
                    x -= 4;
                    cageUpDownCount = 0;
                    if (type == 0) {
                        instructionPointer = 6;
                        break;
                    }
                    else if (type == 2) {
                        instructionPointer = 2;
                        break;
                    }
                    instructionPointer = 1;
                case 1:
                    if (moveToTargetPosition((int) x, 134 + 4, 1)) {
                        break yield;
                    }
                    instructionPointer = 2;
                case 2:
                    if (moveToTargetPosition((int) x, 134 - 4, 1)) {
                        break yield;
                    }
                    cageUpDownCount++;
                    if (cageUpDownCount <= type * 2) {
                        instructionPointer = 1;
                        break yield;
                    }
                    instructionPointer = 3;
                case 3:
                    if (moveToTargetPosition((int) x, 134, 1)) {
                        break yield;
                    }
                    instructionPointer = 4;
                case 4:
                    if (moveToTargetPosition((int) 105, 134, 1)) {
                        break yield;
                    }
                    instructionPointer = 5;
                case 5:
                    if (moveToTargetPosition((int) 105, 110, 1)) {
                        break yield;
                    }
                    if ((int) (2 * Math.random()) == 0) {
                        instructionPointer = 7;
                        continue yield;
                    }
                    instructionPointer = 6;
                case 6:
                    if (moveToTargetPosition((int) 109, 110, 1)) {
                        break yield;
                    }
                    desiredDirection = 0;
                    lastDirection = 0;
                    updatePosition(18, 11);
                    instructionPointer = 8;
                    continue yield;
                case 7:
                    if (moveToTargetPosition((int) 101, 110, 1)) {
                        break yield;
                    }
                    desiredDirection = 2;
                    lastDirection = 2;
                    updatePosition(17, 11);
                    instructionPointer = 8;
                case 8:
                    setMode(Mode.NORMAL);
                    break yield;
            }
        }
    }
    
    private PacmanCatchedAction pacmanCatchedAction = new PacmanCatchedAction();
    
    private class PacmanCatchedAction implements Runnable {
        public void run() {
            game.setState(State.PACMAN_DIED);
        }
    }
    
    private void updateGhostNormal() {
        if (checkVulnerableModeTime() && markAsVulnerable) {
            setMode(Mode.VULNERABLE);
            markAsVulnerable = false;
        }
        
        // for debbuging purposes
//        if (Keyboard.keyPressed[KeyEvent.VK_Q] && type == 0) {
//            game.currentCatchedGhostScoreTableIndex = 0;
//            game.ghostCatched(Ghost.this);
//        }
//        else if (Keyboard.keyPressed[KeyEvent.VK_W] && type == 1) {
//            game.currentCatchedGhostScoreTableIndex = 0;
//            game.ghostCatched(Ghost.this);
//        }
//        else if (Keyboard.keyPressed[KeyEvent.VK_E] && type == 2) {
//            game.currentCatchedGhostScoreTableIndex = 0;
//            game.ghostCatched(Ghost.this);
//        }
//        else if (Keyboard.keyPressed[KeyEvent.VK_R] && type == 3) {
//            game.currentCatchedGhostScoreTableIndex = 0;
//            game.ghostCatched(Ghost.this);
//        }
        
        if (type == 0 || type == 1) {
            updateGhostMovement(true, pacman.col, pacman.row, 1, pacmanCatchedAction, 0, 1, 2, 3); // chase movement
        }
        else {
            updateGhostMovement(false, 0, 0, 1, pacmanCatchedAction, 0, 1, 2, 3); // random movement
        }
    }
    
    private GhostCatchedAction ghostCatchedAction = new GhostCatchedAction();
    
    private class GhostCatchedAction implements Runnable {
        public void run() {
            game.ghostCatched(Ghost.this);
        }
    }
    
    private void updateGhostVulnerable() {
        if (markAsVulnerable) {
            markAsVulnerable = false;
        }
        
        updateGhostMovement(true, pacman.col, pacman.row, 1, ghostCatchedAction, 2, 3, 0, 1); // run away movement
        // return to normal mode after 8 seconds
        if (!checkVulnerableModeTime()) {
            setMode(Mode.NORMAL);
        }
    }
    
    private boolean checkVulnerableModeTime() {
        return System.currentTimeMillis() - vulnerableModeStartTime <= 8000;
    }
    
    private void updateGhostDied() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    pathFinder.find(col, row, 18, 11);
                    instructionPointer = 1;
                case 1:
                    if (!pathFinder.hasNext()) {
                        instructionPointer = 3;
                        continue yield;
                    }
                    Point nextPosition = pathFinder.getNext();
                    col = nextPosition.x;
                    row = nextPosition.y;
                    instructionPointer = 2;
                case 2:
                    if (!moveToGridPosition(col, row, 4)) {
                        if (row == 11 && (col == 17 || col == 18)) {
                            instructionPointer = 3;
                            continue yield;
                        }
                        instructionPointer = 1;
                        continue yield;
                    }
                    break yield;
                case 3:
                    if (!moveToTargetPosition(105, 110, 4)){
                        instructionPointer = 4;
                        continue yield;
                    }
                    break yield;
                case 4:
                    if (!moveToTargetPosition(105, 134, 4)){
                        instructionPointer = 5;
                        continue yield;
                    }
                    break yield;
                case 5:
                    setMode(Mode.CAGE);
                    instructionPointer = 4;
                    break yield;
            }
        }
    }    
    
    private void updateGhostMovement(boolean useTarget, int targetCol, int targetRow
            , int velocity, Runnable collisionWithPacmanAction, int ... desiredDirectionsMap) {
        
        desiredDirections.clear();
        if (useTarget) {
            if (targetCol - col > 0) {
                desiredDirections.add(desiredDirectionsMap[0]);
            }
            else if (targetCol - col < 0) {
                desiredDirections.add(desiredDirectionsMap[2]);
            }
            if (targetRow - row > 0) {
                desiredDirections.add(desiredDirectionsMap[1]);
            }
            else if (targetRow - row < 0) {
                desiredDirections.add(desiredDirectionsMap[3]);
            }
        }
        if (desiredDirections.size() > 0) {
            int selectedChaseDirection = (int) (desiredDirections.size() * Math.random());
            desiredDirection = desiredDirections.get(selectedChaseDirection);
        }
        
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    if ((row == 14 && col == 1 && lastDirection == 2) 
                            || (row == 14 && col == 34 && lastDirection == 0)) {
                        adjustHorizontalOutsideMovement();
                    }
                    
                    double angle = Math.toRadians(desiredDirection * 90);
                    dx = (int) Math.cos(angle);
                    dy = (int) Math.sin(angle);
                    if (useTarget && game.maze[row + dy][col + dx] == 0
                            && desiredDirection != backwardDirections[lastDirection]) {
                        
                        direction = desiredDirection;
                    }
                    else {
                        do {
                            direction = (int) (4 * Math.random());
                            angle = Math.toRadians(direction * 90);
                            dx = (int) Math.cos(angle);
                            dy = (int) Math.sin(angle);
                        }
                        while (game.maze[row + dy][col + dx] == -1               
                            || direction == backwardDirections[lastDirection]);
                    }
                    
                    col += dx;
                    row += dy;
                    instructionPointer = 1;
                case 1:
                    if (!moveToGridPosition(col, row, velocity)) {
                        lastDirection = direction;
                        instructionPointer = 0;
                        // adjustHorizontalOutsideMovement();
                    }
                    if (collisionWithPacmanAction != null && checkCollisionWithPacman()) {
                        collisionWithPacmanAction.run();
                    }
                    break yield;
            }
        }        
    }

    @Override
    public void updateGhostCatched() {
        if (mode == Mode.DIED) {
            updateGhostDied();
            updateAnimation();
        }
    }

    @Override
    public void updatePacmanDied() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 1500) {
                        break yield;
                    }
                    visible = false;
                    setMode(Mode.CAGE);
                    updateAnimation();
                    break yield;
            }
        }
        updateAnimation();
    }

    @Override
    public void updateLevelCleared() {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 1500) {
                        break yield;
                    }
                    visible = false;
                    setMode(Mode.CAGE);
                    updateAnimation();
                    instructionPointer = 2;
                case 2:
                    break yield;
            }
        }
    }
   
    private boolean checkCollisionWithPacman() {
        pacman.updateCollider();
        updateCollider();
        return pacman.collider.intersects(collider);
    }

    @Override
    public void updateCollider() {
        collider.setLocation((int) (x + 4), (int) (y + 4));
    }
    
    private void modeChanged() {
        instructionPointer = 0;
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (game.getState() == PacmanGame.State.TITLE) {
            updateTitle();
            visible = true;
        }
        else if (game.getState() == PacmanGame.State.READY) {
            visible = false;
        }
        else if (game.getState() == PacmanGame.State.READY2) {
            setMode(Mode.CAGE);
            updateAnimation();
            Point initialPosition = initialPositions[type];
            updatePosition(initialPosition.x, initialPosition.y); // col, row
            x -= 4;
        }
        else if (game.getState() == PacmanGame.State.PLAYING && mode != Mode.CAGE) {
            instructionPointer = 0;
        }
        else if (game.getState() == PacmanGame.State.PACMAN_DIED) {
            instructionPointer = 0;
        }
        else if (game.getState() == PacmanGame.State.LEVEL_CLEARED) {
            instructionPointer = 0;
        }
    }
    
    public void showAll() {
        visible = true;
    }

    public void hideAll() {
        visible = false;
    }
    
    public void startGhostVulnerableMode() {
        vulnerableModeStartTime = System.currentTimeMillis();
        markAsVulnerable = true;
    }
    
    public void died() {
        setMode(Mode.DIED);
    }
        
}
