package controller;

import model.Game;
import model.Turn;
import network.messages.toclient.ExceptionMessage;
import network.messages.toclient.StateErrorMessage;
import utils.Coordinate;
import view.RemoteView;

public class TurnController extends StateMachineController{

    TurnController(Game model, Controller controller){
        super(model,controller);
    }

    public void firstMove(RemoteView caller, int builderID, Coordinate coordinate, boolean specialPower){
        boolean callerCorrect = checkTurnCorrectness(caller);
        if(callerCorrect) {
            try {
                boolean allowedAction = game.getCurrentTurn().firstSelection(builderID, coordinate, specialPower);
                if (!allowedAction){
                    sendStateError(caller);
                }
            }
            catch(IllegalArgumentException exception){
                sendExceptionError(caller, exception);
            }
        }
    }

    public void selectCoordinate(RemoteView caller, Coordinate coordinate, boolean specialPower){
        boolean callerCorrect = checkTurnCorrectness(caller);
        if(callerCorrect) {
            try {
                boolean allowedAction = game.getCurrentTurn().selectCoordinate(coordinate, specialPower);
                if (!allowedAction){
                    sendStateError(caller);
                }
            }
            catch(IllegalArgumentException exception){
                sendExceptionError(caller, exception);
            }
        }
    }

    public void endPhase(RemoteView caller){
        boolean callerCorrect = checkTurnCorrectness(caller);
        if(callerCorrect) {
            try {
                boolean allowedAction = game.getCurrentTurn().endPhase();
                if (!allowedAction){
                    sendStateError(caller);
                }
            }
            catch(IllegalStateException exception){
                sendExceptionError(caller, exception);
            }
        }
    }

    @Override
    protected void sendStateError(RemoteView remoteview) {
        Turn.State state = game.getCurrentTurn().getStateID();
        controller.sendMessage(remoteview, new StateErrorMessage<>(state));
    }

    private boolean checkTurnCorrectness(RemoteView caller){
        if(game.getGameState() != game.turnState) {
            controller.sendMessage(caller, new StateErrorMessage<>(game.getGameState().getStateIdentifier()));
            return false;
        }
        String callerUsername = caller.getPlayerName();
        String effectiveUsername = game.getCurrentTurn().getCurrentPlayer().getName();
        if(!callerUsername.equals(effectiveUsername)){
            controller.sendMessage(caller, new ExceptionMessage(new IllegalCallerException()));
            return false;
        }
        return true;
    }

}
