package io.cucumber.shouty;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepDefs {
    private String messageFromSean;
    private static final int DEFAULT_RANGE = 100;
    private Network network = new Network(DEFAULT_RANGE);
    private Map<String, Person> people;

    @Before
    public void createNetwork() {
        people = new HashMap<String, Person>();
    }

    @Given("a person named {word}") 
    public void a_person_named(String name) throws Throwable {
        people.put(name, new Person(network, 0));
    }

    @Given("the range is {int}")
    public void the_range_is(int range) throws Throwable {
        network = new Network(range);
    }

    @Given("people located at")
    public void people_located_at(io.cucumber.datatable.DataTable dataTable) {
        for (Map<String, String> personData : dataTable.asMaps()) {
            people.put(personData.get("name"), new Person(network, Integer.parseInt(personData.get("location"))));
        }    
    }

    @When("Sean shouts")
    public void sean_shouts() throws Throwable {
        people.get("Sean").shout("Hello, world");
    }

    @When("Sean shouts {string}")
    public void sean_shouts(String message) throws Throwable {
        people.get("Sean").shout(message);
        messageFromSean = message;
    }

    @Then("Lucy should hear a shout")
    public void lucy_should_hear_a_shout() throws Throwable {
        assertEquals(1, people.get("Lucy").getMessagesHeard().size());
    }
   
    @Then("Larry should not hear a shout")
    public void larry_should_not_hear_a_shout() throws Throwable {
        assertEquals(0, people.get("Larry").getMessagesHeard().size());
    }

    @Then("Lucy should hear Sean's message")
    public void lucy_hears_Sean_s_message() throws Throwable {
        assertEquals(Collections.singletonList(messageFromSean), people.get("Lucy").getMessagesHeard());
    }

    @Then("Larry should not hear Sean's message")
    public void larry_does_not_hear_Sean_s_message() throws Throwable {
        List<String> heardByLarry = people.get("Larry").getMessagesHeard();
        assertThat(heardByLarry, not(hasItem(messageFromSean)));
    }
}
