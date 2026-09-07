package testRunner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import io.cucumber.junit.platform.engine.Constants;

/**
 * Only one Test Runner is needed to control execution of all Cucumber
 * tests.
 *
 * FILTER_TAGS_PROPERTY_NAME below is set to run all six activities
 * together so the HTML/XML/JSON reports below have something from every
 * activity to show. Narrow it to a single tag any time, e.g. "@activity6",
 * or combine a subset, e.g. "@activity4 or @activity5".
 *
 * PLUGIN_PROPERTY_NAME (Activity 7) now generates three report formats
 * in the target/ folder after each run:
 *   - target/HTML_Report.html  (human-readable)
 *   - target/XML_Report.xml   (JUnit format, useful for CI tools)
 *   - target/JSON_Report.json (machine-readable)
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
    key = Constants.GLUE_PROPERTY_NAME,
    value = "stepDefinitions")
@ConfigurationParameter(
    key = Constants.FILTER_TAGS_PROPERTY_NAME,
    value = "@activity1 or @activity2 or @activity3 or @activity4 or @activity5 or @activity6")
@ConfigurationParameter(
    key = Constants.PLUGIN_PROPERTY_NAME,
    value = "pretty,html:target/HTML_Report.html,junit:target/XML_Report.xml,json:target/JSON_Report.json")
public class TestRunner {
}
