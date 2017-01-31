/*******************
 * Christian A. Duncan
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
        
        int r = 0;
        int take = 1;
        int nimSum = 0;
        
        for (int i = 0; i < rows.length; ++i) {
        	nimSum ^= rows[i];
        }
        
        if (nimSum > 0) {
        	for (int i = 0; i < rows.length; ++i) {
        		int row = rows[i];
        		int xor = nimSum ^ rows[i];
        		if (xor < row) {
        			take = row - xor;
        			r = i;
        			break;
        		}
        	}
        } else {
        	r = ran.nextInt(rows.length);
        }

        return r + "," + take;
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
