package view.CLI;

import model.Player;
import network.Client;
import utils.Coordinate;
import view.screens.Screen;
import view.screens.ScreenFactory;
import view.ViewManager;

import java.util.List;
import java.util.logging.Level;

/**
 * This is the ScreenFactory of the CLI. This class is responsible for creating the instances of all the other screens
 */
public class CLIScreenFactory implements ScreenFactory {

    private final ViewManager viewManager;
    private InputListener cliListener;

    /**
     * The class constructor
     * @param viewManager The client caller
     */
    public CLIScreenFactory(ViewManager viewManager){
        this.viewManager = viewManager;
    }




    @Override
    public void initialize(Screen firstScreen) {
        Client.logger.setLevel(Level.SEVERE);



        // Set the thread that reads from STDIN
        cliListener = new InputListener();
        cliListener.setScreen((InputProcessor) firstScreen);
        Thread inputReader = new Thread(cliListener);
        inputReader.start();

    }

    @Override
    public Screen getMenuScreen() {
        Screen menuScreen = new CLIMenuScreen(viewManager);
        if(cliListener != null) cliListener.setScreen((InputProcessor) menuScreen);
        return menuScreen;
    }

    @Override
    public Screen getCreditsScreen() {
        return null;
    }

    @Override
    public Screen getConnectionScreen() {
        Screen connectionscreen = new CLIConnectionScreen(viewManager);
        cliListener.setScreen((InputProcessor) connectionscreen);
        return connectionscreen;
    }

    @Override
    public Screen getGodSelectionScreen(String activePlayer) {
        Screen godselectionscreen = new CLIGodSelectionScreen(viewManager, activePlayer);
        cliListener.setScreen((InputProcessor) godselectionscreen);
        return godselectionscreen;
    }

    @Override
    public Screen getGodPickScreen(String activePlayer, List<String> godsAvailable) {
        Screen pickgodscreen = new CLIPickGodScreen(viewManager, activePlayer, godsAvailable);
        cliListener.setScreen((InputProcessor) pickgodscreen);
        return pickgodscreen;
    }

    @Override
    public Screen getBoardScreen(String activePlayer, List<Player> players, List<Coordinate> preHighlightedCoordinates) {
        Screen boardScreen = new CLIBoardScreen(viewManager, activePlayer, players, preHighlightedCoordinates);
        cliListener.setScreen((InputProcessor) boardScreen);
        return boardScreen;
    }



    @Override
    public Screen getWinnerScreen(List<Player> players) {
        Screen winnerScreen = new CLIWinnerScreen(viewManager, players);
        cliListener.setScreen((InputProcessor) winnerScreen);
        return winnerScreen;
    }

    @Override
    public Screen getConnectionErrorScreen() {
        Screen connectionErrorScreen;
        if(cliListener.getScreen() instanceof CLIBoardScreen)
             connectionErrorScreen = new CLIConnectionErrorScreen(viewManager, true);
        else connectionErrorScreen = new CLIConnectionErrorScreen(viewManager, false);
        cliListener.setScreen((InputProcessor) connectionErrorScreen);
        return connectionErrorScreen;
    }


}
