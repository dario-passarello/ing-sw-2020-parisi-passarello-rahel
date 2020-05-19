package view.GUI;

import view.Screen;
import view.ScreenFactory;
import view.ViewManager;

import java.util.List;

public class GUIScreenFactory implements ScreenFactory {

    ViewManager viewManager;

    public GUIScreenFactory(ViewManager viewManager){
        this.viewManager = viewManager;
    }

    @Override
    public Screen getMenuScreen(ViewManager client) {
        return null;
    }

    @Override
    public Screen getCreditsScreen(ViewManager client) {
        return null;
    }

    @Override
    public Screen getConnectionScreen(ViewManager client) {
        return null;
    }

    @Override
    public Screen getGodSelectionScreen(ViewManager client, String playerName, List<String> players) {
        return null;
    }

    @Override
    public Screen getGodPickScreen(ViewManager client, String playerName, List<String> players, List<String> godsAvailable) {
        return null;
    }

    @Override
    public Screen getBoardScreen(ViewManager client, String playerName, List<String> players) {
        return null;
    }

    @Override
    public Screen getWinnerScreen(ViewManager client, String winner, List<String> players) {
        return null;
    }
}
