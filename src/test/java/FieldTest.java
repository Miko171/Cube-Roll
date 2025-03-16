import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.cube_roll.core.Field;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    private Field field;

    @BeforeEach
    void setUp() {
        field = new Field(6, 6, 2, 4, 4, 4); // 6x6 pole, štart (2,2), cieľ (4,4)
        field.setLivesCount(3);
        field.setMaxLives(5);
    }

    @Test
    void testFieldInitialization() {
        assertEquals(6, field.getRowCount());
        assertEquals(6, field.getColumnCount());
        assertEquals(2, field.getDiceRow());
        assertEquals(4, field.getDiceCol());
        assertEquals(4, field.getFinishR());
        assertEquals(4, field.getFinishC());
    }

    @Test
    void testIsFinishTile() {
        field.moveDice('S'); // Posun dole
        field.moveDice('S'); // Znova dole (na cieľ)
        assertTrue(field.isFinishTile());
    }

    @Test
    void testIsWallTile() {
        assertTrue(field.isWallTile(0, 0)); // Stena na kraji
        assertFalse(field.isWallTile(2, 2)); // Stred nie je stena
    }

    @Test
    void testLoseLife() {
        field.loseLife();
        assertEquals(2, field.getLivesCount());
    }

    @Test
    void testMoveDice() { // sme na 2,4
        field.moveDice('W'); // Hore
        assertEquals(1, field.getDiceRow());
        assertEquals(4, field.getDiceCol());

        field.moveDice('W'); // Hore znova ale je tam stena
        assertEquals(1, field.getDiceRow()); // Nemal by sa hýbať
        assertEquals(4, field.getDiceCol());
    }

    @Test
    void testGetLastDirection() {
        field.moveDice('A');
        assertEquals('D', field.getLastDirection());
    }
}
