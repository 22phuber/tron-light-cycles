package ch.tron.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    private Turn turn;

    @BeforeEach
    void initialize(){
        turn = new Turn(0);
    }

    @Test
    void getNewDirection() {
        assertEquals(0, turn.getNewDirection());
    }

    @Test
    void getPositions() {
        turn.setPosx(1);
        turn.setPosy(1);
        assertEquals(1, turn.getPosx());
        assertEquals(1, turn.getPosy());
    }

    @Test
    void OnHold() {
        turn.setOnHold(true);
        assertTrue(turn.isOnHold());
    }
}