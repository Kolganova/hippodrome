import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HorseTest {

    @Test
    public void whenCreatingHorseWithNameIsNotNull_then_IllegalArgumentException_is_thrown() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 90, 80));
    }

    @Test
    public void whenCreatingHorseWithNameIsNull_then_exception_message_is_correct() {
        String expectedExceptionMessage = "Name cannot be null.";
        String exceptionMessage = null;
        try {
            new Horse(null, 90, 80);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        } finally {
            assertEquals(expectedExceptionMessage, exceptionMessage);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r"})
    public void whenCreatingHorseWithNameIsBlankString_then_IllegalArgumentException_is_thrown(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(value, 90, 80));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r"})
    public void whenCreatingHorseWithNameIsBlankString_then_exception_message_is_correct(String value) {
        String expectedExceptionMessage = "Name cannot be blank.";
        String exceptionMessage = null;
        try {
            new Horse(value, 90, 80);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        } finally {
            assertEquals(expectedExceptionMessage, exceptionMessage);
        }
    }

    @Test
    public void whenCreatingHorseWithNegativeSpeed_then_IllegalArgumentException_is_thrown() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("TestName", -90, 80));
    }

    @Test
    public void whenCreatingHorseWithNegativeSpeed_then_exception_message_is_correct() {
        String expectedExceptionMessage = "Speed cannot be negative.";
        String exceptionMessage = null;
        try {
            new Horse("TestName", -90, 80);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        } finally {
            assertEquals(expectedExceptionMessage, exceptionMessage);
        }
    }

    @Test
    public void whenCreatingHorseWithNegativeDistance_then_IllegalArgumentException_is_thrown() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("TestName", 90, -80));
    }

    @Test
    public void whenCreatingHorseWithNegativeDistance_then_exception_message_is_correct() {
        String expectedExceptionMessage = "Distance cannot be negative.";
        String exceptionMessage = null;
        try {
            new Horse("TestName", 90, -80);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        } finally {
            assertEquals(expectedExceptionMessage, exceptionMessage);
        }
    }

    @Test
    public void whenGetName_returns_correct_name_which_was_set_in_constructor() {
        String name = "Horse1";
        Horse horse = new Horse(name, 90, 80);
        assertEquals(horse.getName(), name);
    }

    @Test
    public void whenGetSpeed_returns_correct_speed_which_was_set_in_constructor() {
        double speed = 90;
        Horse horse = new Horse("TestName", speed, 80);
        assertEquals(horse.getSpeed(), speed);
    }

    @Test
    public void whenGetDistance_returns_correct_distance_which_was_set_in_constructor_or_zero_if_constructor_was_with_2_parameters() {
        double distance = 80;
        Horse horse1 = new Horse("TestName", 90, 80);
        assertEquals(horse1.getDistance(), distance);
        Horse horse2 = new Horse("TestName", 90);
        assertEquals(horse2.getDistance(), 0);
    }

    @Test
    public void whenMethodMoveCallMethodGetRandomDouble_then_gives_correct_args() {
        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("TestHorse", 80).move();
            mockStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.34, 0.8, 0.99999, 0.0, 0.01})
    public void whenMethodMoveInitDistance_formula_counts_correct(double random) {
        double speed = 100;
        double distance = 60;
        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("TestName", speed, distance);
            mockStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse.move();
            assertEquals(distance + speed * random, horse.getDistance());
        }
    }

}
