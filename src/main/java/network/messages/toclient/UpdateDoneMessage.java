package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

public class UpdateDoneMessage implements Message<Screen> {

    @Override
    public void execute(Screen target) {
        target.receiveUpdateDone();
    }
}
