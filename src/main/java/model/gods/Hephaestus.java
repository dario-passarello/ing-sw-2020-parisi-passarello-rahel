package model.gods;

import model.Player;
import model.buildbehaviours.DoubleSameBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.wincondition.StandardWinCondition;

/**
 * Allows the player to build one additional block (not a dome) on top of the first block
 */
public class Hephaestus extends God {

    public Hephaestus(){
        super("Hephaestus",
                new StandardWinCondition(),
                new StandardMove(),
                new DoubleSameBuild(new StandardBuild()),
                false,
                false);
    }

}
