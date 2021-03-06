package useCase;

import adapter.device.DeviceDriver;
import entity.Config;
import entity.ScriptManager;
import entity.ScriptResult;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScriptManagerTest {
    private final String SIMPLE_TEST_DATA = "./src/test/resources/simple_test_data.xlsx";
    private String SIMPLE_SCRIPT = "./src/test/resources/scriptForMapperTest.txt";
    private ScriptManager sm;
    Mockery context = new JUnit4Mockery();
    DeviceDriver mockDriver;

    @Before
    public void setUp() {
        try {
            mockDriver = context.mock(DeviceDriver.class);
            Config config = new Config("", "", 0, 0, SIMPLE_TEST_DATA, SIMPLE_SCRIPT, false);
            sm = new ScriptManager(config, mockDriver);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void getAllPathTest() {
        List<Path> allPath = null;
        try {
            allPath = sm.getAllFilesPath("./src/test/resources");
        } catch (IOException e) {
            Assert.fail();
        }
        assertEquals(3, allPath.size());
    }

    @Test
    public void executeScriptTest() {
        context.checking(new Expectations() {{
            oneOf(mockDriver).startService();
            oneOf(mockDriver).restartAppAndCleanData();
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.widget.TextView' and @text='list']");
            oneOf(mockDriver).waitAndClickElement("//*[@resource-id='org.dmfs.tasks:id/task_list_spinner']");
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.widget.ImageButton']");
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='0']");
            oneOf(mockDriver).waitAndTypeText("//*[@resource-id='android:id/input']", "456 ");
            oneOf(mockDriver).restartApp();
            oneOf(mockDriver).stopApp();
            oneOf(mockDriver).stopService();
        }});

        sm.execute();
        System.out.print(sm.summary());
    }

    @Test
    public void formatInformationAfterExecuteScript() {
        context.checking(new Expectations() {{
            oneOf(mockDriver).startService();
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.widget.TextView' and @text='list']");
            oneOf(mockDriver).waitAndClickElement("//*[@resource-id='org.dmfs.tasks:id/task_list_spinner']");
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.widget.ImageButton']");
            oneOf(mockDriver).waitAndClickElement("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='0']");
            oneOf(mockDriver).waitAndTypeText("//*[@resource-id='android:id/input']", "456 ");
            oneOf(mockDriver).restartApp();
            oneOf(mockDriver).stopApp();
            oneOf(mockDriver).stopService();
            oneOf(mockDriver).restartAppAndCleanData();
        }});

        sm.execute();
        System.out.print(sm.summary());
    }


    private class AssertFailedException extends RuntimeException {
        public AssertFailedException(String s) {
            super(s);
        }
    }
}
