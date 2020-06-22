package model.buildbehaviours;

import model.Board;
import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoubleNoPerimeterBuild extends BuildDecorator {

    // Keeps track of the order of the builds
    private boolean second;

    public DoubleNoPerimeterBuild(BuildBehavior buildBehavior) {
        this.wrappedBuildBehavior = buildBehavior;
        this.second = false;
    }

    public boolean build(Square dest) {
        if (!second) {
            second = true;
            wrappedBuildBehavior.build(dest);
            return true;
        }
        else return wrappedBuildBehavior.build(dest);
    }

    public Set<Square> neighborhood(Square src) {
        if (!second) return this.wrappedBuildBehavior.neighborhood(src);
        else {
            Set<Square> buildable = wrappedBuildBehavior.neighborhood(src);
            Set<Square> remove = new HashSet<>();
            for (Square square : buildable) {
                if (square.isPerimetral()) remove.add(square); //LOL
            }
            buildable.removeAll(remove);
            return buildable;
        }
    }

    @Override
    public BuildBehavior copyBehavior() {
        return new DoubleNoPerimeterBuild(wrappedBuildBehavior.copyBehavior());
    }

    @Override
    public void reset() {
        second = false;
        wrappedBuildBehavior.reset();
    }


}
