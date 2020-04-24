package model.movebehaviors;

import model.*;
import model.gods.Athena;
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

class BlockUpMoveTest {

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
        g1 = new Athena();
        g2 = new Mortal();
        p1.setGod(g1);
        p2.setGod(g2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
    }

    //here we test BlockUpMove and NoUpMove all at once
    @Test
    public void enemyShouldNotMoveUp(){
        Builder b01 = new Builder(s[0][1], p1, 1);
        Builder b44 = new Builder(s[4][4], p2, 1);

        SquareTest.setSquareBuildLevel(s[0][0], 1);
        SquareTest.setSquareBuildLevel(s[3][3], 1);

        b01.move(s[0][0]);
        Set<Square> expected = new HashSet<>(Arrays.asList(s[4][3], s[3][4]));
        Set<Square> actual = new HashSet<>(b44.getWalkableNeighborhood());
        for(Square s : actual){
            System.out.println("["+s.getCoordinate().getX()+","+s.getCoordinate().getY()+"]");
        }
        Assert.assertEquals(expected, actual);
    }
}