import com.badlogic.angrybirds.Level;
import com.badlogic.angrybirds.Pigs.Pig;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class GameTest {
    private Level level;
    List<Pig> pigs;

    @Before
    public void setUp() {
        Level level = new Level(3);
        pigs = level.getPigs();
    }

    @Test
    public void TestNumofPigs() {
        assertTrue(pigs.size() == 4);
    }
}
