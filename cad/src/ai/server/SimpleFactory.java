/*******************
 * Christian A. Duncan
 * CSC350: Intelligent Systems
 * Spring 2017
 *
 * AI Game Server Project
 * This project is designed to support multiple game platforms to test
 * AI-based solutions.
 * See README file for more details.
 ********************/

package ai.server;

import ai.game.*;

/***********************************************************
 * An instance of a GameFactory.  A very simple game.
 ***********************************************************/
public class SimpleFactory implements GameFactory {
    public Game newGame() { return new SimpleGame(); }
}
