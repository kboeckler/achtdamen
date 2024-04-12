import de.kevinboeckler.dojo.Feld
import de.kevinboeckler.dojo.solve
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainKtTest{

    @Test
    fun cannotSee() {
        assertTrue(Feld(1, 1).cannotSee(Feld(2, 3)))
        assertFalse(Feld(1, 1).cannotSee(Feld(2, 2)))
        assertFalse(Feld(2, 3).cannotSee(Feld(3, 2)))
    }

    @Test
    fun solveOne() {
        assertNotNull(solve(setOf(Feld(1, 1)), 1))
    }
}