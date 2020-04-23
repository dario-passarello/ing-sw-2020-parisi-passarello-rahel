package model.movebehaviors;

import model.*;
import model.gods.Atlas;
import model.gods.God;
import model.gods.Minotaur;
import model.gods.Mortal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OpponentPushMoveTest {

    private Player p1, p2, p3;
    private God g1, g2;
    private Board board;
    private Game g;
    private Square[][] s;

    @Before
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
        g1 = new Minotaur();
        g2 = new Mortal();
        p1.setGod(g1);
        p2.setGod(g2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
    }

    @Test
    public void canUsePower(){
        //creating some buildings
        SquareTest.setSquareBuildLevel(s[0][0],1);
        SquareTest.setSquareBuildLevel(s[0][2],2);
        SquareTest.setSquareBuildLevel(s[0][4],3);
        SquareTest.setSquareBuildLevel(s[1][2],1);
        SquareTest.setSquareBuildLevel(s[1][3],2);
        SquareTest.setSquareBuildLevel(s[2][1],1);
        SquareTest.setSquareBuildLevel(s[2][3],3);
        SquareTest.setSquareBuildLevel(s[2][4],3);
        SquareTest.setSquareBuildLevel(s[3][1],2);
        SquareTest.setSquareBuildLevel(s[3][2],3);
        SquareTest.setSquareBuildLevel(s[3][3],3);
        SquareTest.setSquareBuildLevel(s[4][4],1);
        //placing some builder
        Builder b22 = new Builder(s[2][2], p1, 1);                 //b22 is the builder that is going to move
        Builder b11 = new Builder(s[1][1], p2, 1);
        Builder b12 = new Builder(s[1][2], p2, 2);
        Builder b13 = new Builder(s[1][3], p2, 3);
        Builder b21 = new Builder(s[2][1], p2, 4);
        Builder b23 = new Builder(s[2][3], p2, 5);
        Builder b31 = new Builder(s[3][1], p2, 6);
        Builder b32 = new Builder(s[3][2], p2, 7);
        Builder b33 = new Builder(s[3][3], p2, 8);

        List<Square> expectedList = Arrays.asList(s[1][1], s[1][2], s[1][1]);
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());

        SquareTest.setSquareBuildLevel(s[2][2],1);
        expectedList.addAll(Arrays.asList(s[1][3],s[3][1]));
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());

        SquareTest.setSquareBuildLevel(s[2][2],2);
        expectedList.addAll(Arrays.asList(s[2][3], s[3][2], s[3][3]));
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());

        SquareTest.setSquareBuildLevel(s[2][2],3);
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());


        b22.move(s[3][3]);          //Minotaur push
        Assert.assertFalse(s[2][2].getOccupant().isPresent());
        Assert.assertSame(s[3][3].getOccupant().orElse(null), b22);
        Assert.assertSame(s[4][4].getOccupant().orElse(null), b33);

    }

    @Test
    public void canNotUsePower(){

        //creating some buildings
        SquareTest.setSquareBuildLevel(s[1][3],1);
        SquareTest.setSquareBuildLevel(s[1][4],3);
        SquareTest.setSquareBuildLevel(s[2][2],2);
        SquareTest.setSquareBuildLevel(s[2][3],1);
        s[3][4].addDome();
        //placing some builder
        Builder b01 = new Builder(s[0][1], p1, 1);
        Builder b02 = new Builder(s[0][2], p2, 1);
        Builder b03 = new Builder(s[0][3], p2, 2);
        Builder b11 = new Builder(s[1][1], p1, 2);
        Builder b12 = new Builder(s[1][2], p1, 3);         // b12 is the builder that is going to move
        Builder b13 = new Builder(s[1][3], p2, 3);
        Builder b21 = new Builder(s[2][1], p2, 4);
        Builder b22 = new Builder(s[2][2], p2, 5);
        Builder b23 = new Builder(s[2][3], p2, 6);
        Builder b30 = new Builder(s[3][0], p1, 4);

        List<Square> expectedList = new ArrayList<>();              //b12 cannot move, so it's empty
        Assert.assertEquals(expectedList, b12.getWalkableNeighborhood());

    }


}