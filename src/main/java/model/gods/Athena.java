package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

public class Athena extends God {

    public Athena(Player player){
        super(player, "Athena", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new StandardBuild());
        //INFLUENZA GLI AVVERSARI
    }

    @Override
    public void resetBehaviors() {
    }
}
