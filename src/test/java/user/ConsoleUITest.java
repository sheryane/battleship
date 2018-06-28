package user;

import model.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleUITest {

    private ConsoleUI ui;
    @Mock
    private JavaConsoleDelegate javaConsoleDelegate;

    @Before
    public void init() {
//        javaConsoleDelegate = mock(JavaConsoleDelegate.class);
        ui = new ConsoleUI(javaConsoleDelegate);
    }

    @Test
    public void whenUserNeedsToBeNotifiedThenInvokeConsoleDelegate() {
        //given
        String messageToPrint = "Hello World!";
        //when
        ui.notifyUser(messageToPrint);
        //then
        verify(javaConsoleDelegate).printToConsole(eq(messageToPrint));
    }

    @Test
    public void whenUserShootAtC4ThenPointXIs2AndYIs3() {
        //given
        String userInput = "C4";
        when(javaConsoleDelegate.readFromConsole()).thenReturn(userInput);
        //when
        Point shotLocation = ui.askUserForShot();
        //then
        assertEquals((Integer) 2, shotLocation.getX());
        assertEquals((Integer) 3, shotLocation.getY());
    }

    @Test
    public void whenUserShootAta7ThenPointXs0AndYIs6() {
        //given
        String userInput = "a7";
        when(javaConsoleDelegate.readFromConsole()).thenReturn(userInput);
        //when
        Point shotLocation = ui.askUserForShot();
        //then
        assertEquals((Integer) 0, shotLocation.getX());
        assertEquals((Integer) 6, shotLocation.getY());
    }

    @Test
    public void whenUserShootAtF10ThenPointXIs5AndYIs9() {
        //given
        String userInput = "F10";
        when(javaConsoleDelegate.readFromConsole()).thenReturn(userInput);
        //when
        Point shotLocation = ui.askUserForShot();
        //then
        assertEquals((Integer) 5, shotLocation.getX());
        assertEquals((Integer) 9, shotLocation.getY());
    }

    @Test
    public void whenUserShootAtAAAThenPointIsOutOfBounds() {
        //given
        String userInput = "AAA";
        when(javaConsoleDelegate.readFromConsole()).thenReturn(userInput);
        //when
        Point shotLocation = ui.askUserForShot();
        //then
        assertEquals((Integer) (-1), shotLocation.getX());
        assertEquals((Integer) (-1), shotLocation.getY());
    }


}