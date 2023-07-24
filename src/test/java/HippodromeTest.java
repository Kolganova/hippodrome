import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HippodromeTest {

    @Test
    public void whenCreatingHippodromeArgIsNull_then_IllegalArgumentException_is_thrown() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void whenCreatingHippodromeArgIsNull_then_exception_message_is_correct() {
        String expectedExceptionMessage = "Horses cannot be null.";
        String exceptionMessage = null;
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        } finally {
            assertEquals(expectedExceptionMessage, exceptionMessage);
        }
    }

    @Test
    public void whenCreatingHippodromeListIsEmpty_then_IllegalArgumentException_is_thrown() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void whenCreatingHippodromeListIsEmpty_then_exception_message_is_correct() {
        String expectedExceptionMessage = "Horses cannot be empty.";
        String exceptionMessage = null;
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        } finally {
            assertEquals(expectedExceptionMessage, exceptionMessage);
        }
    }

    @Test
    public void whenGetHorses_then_init_in_constructor_was_correct() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            String name = "Horse" + i;
            horses.add(new Horse(name, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertIterableEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void whenMove_then_every_horse_is_moving() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            horses.add(mock(Horse.class));
        }

        new Hippodrome(horses).move();
        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    public void whenGetWinner_the_horse_with_the_biggest_distance_returns() {
        Horse horse1 = new Horse("Horse1", 100, 30);
        Horse horse2 = new Horse("Horse2", 60, 50);
        Horse horse3 = new Horse("Horse3", 110, 92);
        Horse horse4 = new Horse("Horse4", 80, 100);
        Hippodrome hippodrome = new Hippodrome(new ArrayList<>(List.of(horse1, horse2, horse3, horse4)));
        assertEquals(horse4, hippodrome.getWinner());
    }
}
