package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.DoubleNoBackMove;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

public class Artemis extends God {

    public Artemis(){
        super("Artemis", new StandardWinCondition(), new NoStartTurn(), new DoubleNoBackMove(new StandardMove()), new StandardBuild());
    }


}
