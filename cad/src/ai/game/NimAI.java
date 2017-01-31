/*******************
 * Christian A. Duncan
 * Edited By:
 * Khaled Abu-Ghazaleh
 * Chris Kosior
 * Glen Levine
 * CSC350: Intelligent Systems
 * Spring 2017
 *
 * AI Game Client
 * This project is designed to link to a basic Game Server to test
 * AI-based solutions.
 * See README file for more details.
 ********************/

package ai.game;

import java.util.Random;

/***********************************************************
 * The AI system for a NimGame.
 *   Most of the game control is handled by the Server but
 *   the move selection is made here - either via user or an attached
 *   AI system.
 ***********************************************************/
public class NimAI implements AI {
    public NimGame game;  // The game that this AI system is playing
    Random ran;

    public NimAI() {
        game = null;
        ran = new Random();
    }

    public void attachGame(Game g) {
        game = (NimGame) g;
    }

    /**
     * Returns the Move as a String "R,S"
     *    R=Row
     *    S=Sticks to take from that row
     **/
    public synchronized String computeMove() {

        if (game == null) {
            System.err.println("CODE ERROR: AI is not attached to a game.");
            return "0,0";
        }

        int[] rows = (int[]) game.getStateAsObject();

        int targetRow = 0;
        int takeCount = 1;
        int nimSum = 0;

        // Compute the nim sum by XOR'ing all rows sequentially
        for (int r = 0; r < rows.length; ++r) {
        	nimSum ^= rows[r];
        }

        if (nimSum > 0) {
          // In the case that we are "at an advantage"
        	for (int r = 0; r < rows.length; ++r) {
        		int rowSum = rows[r];
        		int rowXOR = nimSum ^ rows[r];
            // Can we optimize the current row?
        		if (rowXOR < rowSum) {
        			takeCount = rowSum - rowXOR;
        			targetRow = r;
        			break;
        		}
        	}
        } else {
          // In the case that all we can do is pray
        	targetRow = ran.nextInt(rows.length);
        }

        return targetRow + "," + takeCount;
    }

    /**
     * Inform AI who the winner is
     *   result is either (H)ome win, (A)way win, (T)ie
     **/
    public synchronized void postWinner(char result) {
        // This AI doesn't care.  No learning being done...
        game = null;  // No longer playing a game though.
    }
}
