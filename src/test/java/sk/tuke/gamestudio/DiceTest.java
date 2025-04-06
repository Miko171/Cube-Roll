package sk.tuke.gamestudio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.cube_roll.core.Dice;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {
    private Dice dice;

    @BeforeEach
    void setUp() {
        dice = new Dice();
    }

    @Test
    void testInitialDiceSetup() {
        assertEquals(1, dice.getTopSide());
        assertEquals(6, dice.getBottomSide());
        assertEquals(2, dice.getFrontSide());
        assertEquals(5, dice.getBackSide());
        assertEquals(3, dice.getLeftSide());
        assertEquals(4, dice.getRightSide());
    }

    @Test
    void testSetAndGetMarkedSide() {
        dice.setMarkedSide(3);
        assertEquals(3, dice.getMarkedSide());
    }

    @Test
    void testIsMarkedSideBottom() {
        dice.setMarkedSide(6);
        assertTrue(dice.isMarkedSideBottom());
        dice.setMarkedSide(3);
        assertFalse(dice.isMarkedSideBottom());
    }

    @Test
    void testRollDiceUp() {
        dice.rollDice('W');
        assertEquals(5, dice.getTopSide());
        assertEquals(2, dice.getBottomSide());
        assertEquals(1, dice.getFrontSide());
        assertEquals(6, dice.getBackSide());
    }

    @Test
    void testRollDiceDown() {
        dice.rollDice('S');
        assertEquals(2, dice.getTopSide());
        assertEquals(5, dice.getBottomSide());
        assertEquals(6, dice.getFrontSide());
        assertEquals(1, dice.getBackSide());
    }

    @Test
    void testRollDiceRight() {
        dice.rollDice('D');
        assertEquals(3, dice.getTopSide());
        assertEquals(4, dice.getBottomSide());
        assertEquals(2, dice.getFrontSide());
        assertEquals(5, dice.getBackSide());
    }

    @Test
    void testRollDiceLeft() {
        dice.rollDice('A');
        assertEquals(4, dice.getTopSide());
        assertEquals(3, dice.getBottomSide());
        assertEquals(2, dice.getFrontSide());
        assertEquals(5, dice.getBackSide());
    }
}
