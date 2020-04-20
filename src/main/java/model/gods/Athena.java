package model.gods;

import model.buildbehaviours.StandardBuild;
import model.movebehaviors.NoUpMove;
import model.movebehaviors.StandardMove;
import model.wincondition.StandardWinCondition;

import java.util.List;
import java.util.stream.Collectors;

public class Athena extends God {

    public Athena(){
        super( "Athena",
                new StandardWinCondition(),
                new StandardMove(),
                new StandardBuild(),
                false,
                false);
        //INFLUENZA GLI AVVERSARI
    }


    @Override
    public void setAllMoveBehaviors(List<God> targets) {
        //Apply temporary debuff to other players
        for(God g : targets.stream().filter(g -> g.equals(this)).collect(Collectors.toList())) {
            g.setMoveBehavior(new NoUpMove(g.getMoveBehavior()));
        }
    }
}
