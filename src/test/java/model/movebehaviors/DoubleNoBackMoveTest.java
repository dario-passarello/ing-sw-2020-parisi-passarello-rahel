package model.movebehaviors;

import model.*;
import model.buildbehaviours.BuildBehavior;
import model.buildbehaviours.DoubleSameBuild;
import model.gods.Artemis;
import model.gods.Atlas;
import model.gods.God;
import model.gods.Mortal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DoubleNoBackMoveTest {

    private Player p1, p2;
    private God g1, g2;
    private Board board;
    private Game g;
    private Square[][] s;

    @BeforeEach
    public void init(){
        List<String> names = Arrays.asList("player1", "player2");
        try {
            g = new Game(names, 2);
        } catch (DuplicateNameException e) {
            System.err.println(e.getMessage());
        }
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = g.getPlayers().get(0);
        p2 = g.getPlayers().get(1);
        g1 = new Artemis();
        g2 = new Mortal();
        p1.setGod(g1);
        p2.setGod(g2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
    }

    @Test
    public void youShouldNotComeBack(){
        Builder b22 = new Builder(s[2][2], p1, 1);

        Set<Square> expected = new HashSet<>(Arrays.asList(  s[1][1], s[1][2],s[1][3],
                                                             s[2][1], s[2][3],
                                                             s[3][1], s[3][2], s[3][3]));
        Set<Square> actual = new HashSet<>(b22.getWalkableNeighborhood());
        Assert.assertTrue(expected.equals(actual));

        Assert.assertTrue(b22.move(s[1][2]));

        expected = new HashSet<>(Arrays.asList(  s[0][1], s[0][2],s[0][3],
                                                 s[1][1], s[1][3],
                                                 s[2][1], s[2][3]));
        actual = new HashSet<>(b22.getWalkableNeighborhood());
        Assert.assertTrue(expected.equals(actual));

        Assert.assertFalse(b22.move(s[1][3]));
    }

    @Test
    public void copyTest(){
        MoveBehavior copy = g1.getMoveBehavior().copyBehavior();
        Assert.assertTrue(copy instanceof DoubleNoBackMove);
        g1.getMoveBehavior().reset();

    }

}