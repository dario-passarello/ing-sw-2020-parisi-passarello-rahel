package model.gods;

import model.buildbehaviours.AnyDomeBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

/**
 * Allows the player to build a dome at any level
 */
public class Atlas extends God {


    public Atlas(){
        super("Atlas", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new AnyDomeBuild());
    }
}
