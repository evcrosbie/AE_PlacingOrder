package com.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"classpath:features"},
        glue = {"com/stepDefinitions"},
        dryRun = false,
        tags = "@credit_card_payment"
)

public class MainRunner extends AbstractTestNGCucumberTests  {
}
