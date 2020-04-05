package model.gods;

import model.Player;
import model.buildbehaviours.BeneathBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

/**
 * Allows the player to build a block beneath itself
 */
public class Zeus extends God {

    public Zeus(Player player){
        super(player, "Zeus", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new BeneathBuild(new StandardBuild()));
    }

    @Override
    public void resetBehaviors() {
    }
}
