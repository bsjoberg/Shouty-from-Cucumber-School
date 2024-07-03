package io.cucumber.shouty;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("io.cucumber.shouty")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, junit:target/cucumber-reports/output.xml") 
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "not @todo")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "io.cucumber.shouty")
public class RunCucumberTest {
}
