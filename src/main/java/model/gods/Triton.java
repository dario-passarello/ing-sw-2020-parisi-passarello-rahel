package model.gods;

import model.Square;
import model.buildbehaviors.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.UnlimitedPerimetralMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Triton extends God{

    public Triton(){
        super("Triton", new StandardWinCondition(), new NoStartTurn(), new UnlimitedPerimetralMove(new StandardMove()), new StandardBuild());
    }

}
