package entity.report;

import entity.ScriptInformation;
import org.hamcrest.Factory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class ReportGeneratorTest {
    ScriptReportGenerator rg;
    ScriptInformation scriptInformation;
    @Before
    public void setUp() {
        rg = new ScriptReportGenerator();
        scriptInformation = TestHelper.createCorrectScriptInformation();
    }

    @Test
    public void generateScriptInfoHeader() {
        System.out.println(rg.generateScriptInfoHeader(scriptInformation));
        assertThat(rg.generateScriptInfoHeader(scriptInformation),
                allOf(containsString("Starting"),
                        containsString("Test Name")));
    }

    @Test
    public void generateScriptInfoFooter() {
        System.out.println(rg.generateScriptInfoFooter(scriptInformation));
        assertThat(rg.generateScriptInfoFooter(scriptInformation),
                allOf(containsString("Spending Time"),
                        containsString("10.0s"),
                        containsString("Status"),
                        containsString("PASS")));
    }

    @Test
    public void generateScriptInfoBody() {
        scriptInformation.executionFailed("This is a error.");
        System.out.println(rg.generateScriptInfoBody(scriptInformation));
        assertThat(rg.generateScriptInfoBody(scriptInformation),
                allOf(containsString("Assert Error"),
                        containsString("This is a error")));
    }

    @Test
    public void generateScriptInfoException() {
        scriptInformation.executionFailed("Throw Timeout Checked Exception");

        String result = rg.generateScriptInfoExceptionBody(scriptInformation);

        assertThat(result,
                allOf(containsString("Exception Error"),
                        containsString("Throw Timeout Checked Exception")));
    }

    @Test
    public void generateScriptSummaryHeader() {
        ScriptInformation scriptInformation1 = new ScriptInformation("Test2");
        scriptInformation1.setExecutionTime(10000);

        List<ScriptInformation> scriptInformations = Arrays.asList(scriptInformation, scriptInformation1);

        System.out.println(rg.generateScriptSummary(scriptInformations));

        assertThat(rg.generateScriptSummary(scriptInformations),
                allOf(containsString("ScriptName"),
                        containsString("Times"),
                        containsString("Status")));
    }

    @Test
    public void generateScriptSummaryFooter() {
        ScriptInformation scriptInformation1 = TestHelper.createFailedScriptInformation();


        List<ScriptInformation> scriptInformations = Arrays.asList(scriptInformation, scriptInformation1);

        String scriptSummaryTable  = rg.generateScriptSummary(scriptInformations);
        System.out.print(scriptSummaryTable);

        assertThat(scriptSummaryTable,
                allOf(  containsString("Total"),
                        containsString("0.33 min"),
                        containsString(passedOf(1)),
                        containsString(failedOf(1))
                        ));
    }

    String passedOf(int times) {
        return times + " " + new ColorHelper().paintingGreen("passed");
    }

    String failedOf(int times) {
        return times + " " + new ColorHelper().paintingRed("failed");
    }

    @Test
    public void generateScriptSummaryBody() {

        List<ScriptInformation> scriptInformationList = TestHelper.createScriptInformationList();

        List<String> result = rg.getScriptBodyList(scriptInformationList);

        for(int i = 0; i < scriptInformationList.size(); i++) {
            String information = result.get(i);
            assertThat(information, containsString(scriptInformationList.get(i).getScriptName()));
            assertThat(information, containsString(TimeHelper.transformToSecondFormat(scriptInformationList.get(i).getExecutionTime())));
            assertThat(information, containsString(scriptInformationList.get(i).resultStatus()));

        }
    }

}