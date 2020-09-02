package Soap.ProjectSOAP;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestRunner.Status;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.support.SoapUIException;
import com.eviware.soapui.tools.SoapUITestCaseRunner;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(Parameterized.class)
public class RunnerSoapUI_parametered {
	private String testCaseName;

	public RunnerSoapUI_parametered(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	@Parameters(name = "{0}")
	public static Collection<String[]> getTestCases() throws XmlException, IOException, SoapUIException {
		final ArrayList<String[]> testCases = new ArrayList<String[]>();
		WsdlProject project = new WsdlProject("C:/Users/formation/Documents/SquashTA-1.10.0-RELEASE-workspace/ProjectSOAP/src/test/java/resources/NOAA-soapui-project.xml");
		List<TestSuite> testSuites = project.getTestSuiteList();
		for (TestSuite suite : testSuites) {
			List<TestCase> lTestCases = suite.getTestCaseList();
			for (TestCase testCase : lTestCases) {
				if (!testCase.isDisabled()) {
					testCases.add(new String[] { testCase.getName() });
				}
			}
		}
		return testCases;

	}

	@Test
	public void testSoapUITestCase() throws XmlException, IOException, SoapUIException {
		System.out.println("Nom du cas de test SoapUI : " + testCaseName);
		assertTrue(true);
		assertTrue(runSoapUITestCase(this.testCaseName));
	}

	public static boolean runSoapUITestCase(String testCase) throws XmlException, IOException, SoapUIException {
		TestRunner.Status exitValue = TestRunner.Status.INITIALIZED;
		WsdlProject soapuiProject = new WsdlProject("C:/Users/formation/Documents/SquashTA-1.10.0-RELEASE-workspace/ProjectSOAP/src/test/java/resources/NOAA-soapui-project.xml");
		List<TestSuite> testSuites = soapuiProject.getTestSuiteList();
		for (TestSuite suite : testSuites) {
			if (suite == null) {
				System.err.println("runner soapUI, la suite de test est null : " + suite);
				return false;
			}
			TestCase soapuiTestCase = suite.getTestCaseByName(testCase);
			if (soapuiTestCase == null) {
				System.err.println("runner soapUI, le cas de test est null : " + testCase);
			} else {
				TestCaseRunner runner = soapuiTestCase.run(new PropertiesMap(), false);
				exitValue = runner.getStatus();
			}

			System.out.println("cas de test soapUI terminé ('" + suite + "':'" + testCase + "') : " + exitValue);
		}
		if (exitValue == TestRunner.Status.FINISHED) {
			return true;
		} else {
			return false;

		}
	}
}
