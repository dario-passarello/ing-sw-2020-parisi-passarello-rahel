package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.NoPerimeterWinCondition;
import model.wincondition.StandardWinCondition;

import java.util.List;
import java.util.stream.Collectors;

public class Hera extends God {

    public Hera(){
        super("Hera", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new StandardBuild());
    }

    @Override
    public boolean hasSpecialStartPower() {
        return false;
    }

    @Override
    public void configureAllOtherWinConditions(List<God> targets) {
        //Set No Perimeter win condition to all other gods
        for(God g : targets.stream().filter(g -> !g.equals(this)).collect(Collectors.toList())){
            g.setWinCondition(new NoPerimeterWinCondition(g.getWinCondition()));
        }
    }
}
