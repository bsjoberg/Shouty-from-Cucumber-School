package io.cucumber.shouty;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.shouty.support.ShoutyWorld;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepDefs {
    private final ShoutyWorld world;

    public StepDefs(ShoutyWorld world) {
        this.world = world;
    }

    static class Whereabouts {
        public String name;
        public Integer location;

        public Whereabouts(String name, int location) {
            this.name = name;
            this.location = location;
        }
    }

    @DataTableType
    public Whereabouts defineWhereabouts(Map<String, String> entry) {
        return new Whereabouts(entry.get("name"), Integer.parseInt(entry.get("location")));
    }


    @Given("the range is {int}")
    public void the_range_is(int range) throws Throwable {
        world.network = new Network(range);
    }

    @Given("a person named {word}")
    public void a_person_named(String name) throws Throwable {
        world.people.put(name, new Person(name, world.network, 0));
    }

    @Given("{person} is located at {int}")
    public void person_is_located_at(Person person, Integer location) {
        person.moveTo(location);
    }

    @Given("Sean has bought {int} credits")
    public void sean_has_bought_credits(int credits) {
        world.people.get("Sean").setCredits(credits);
    }

    @When("Sean shouts")
    public void sean_shouts() throws Throwable {
        shout("Hello, world");
    }

    @When("Sean shouts {string}")
    public void sean_shouts_message(String message) throws Throwable {
        shout(message);
    }

    @When("Sean shouts {int} messages containing the word {string}")
    public void sean_shouts_messages_containing_the_word(int count, String word) throws Throwable {
        String message = "a message containing the word " + word;
        for (int i = 0; i < count; i++) {
            shout(message);
        }
    }

    @When("Sean shouts the following message")
    public void sean_shouts_the_following_message(String message) throws Throwable {
        shout(message);
    }

    @When("Sean shouts a message")
    public void sean_shouts_a_message() throws Throwable {
        shout("here is a message");
    }

    @When("Sean shouts a long message")
    public void sean_shouts_a_long_message() throws Throwable {
        String longMessage = String.join(
                "\n",
                "A message from Sean",
                "that spans multiple lines");
        shout(longMessage);
    }

    @When("Sean shouts {int} over-long messages")
    public void sean_shouts_some_over_long_messages(int count) throws Throwable {
        String baseMessage = "A message from Sean that is 181 characters long ";
        String padding = "x";
        String overlongMessage = baseMessage + padding.repeat(181 - baseMessage.length());

        for (int i = 0; i < count; i++) {
            shout(overlongMessage);
        }
    }

    private void shout(String message) {
        world.people.get("Sean").shout(message);
        List<String> messages = world.messagesShoutedBy.get("Sean");
        if (messages == null) {
            messages = new ArrayList<String>();
            world.messagesShoutedBy.put("Sean", messages);
        }
        messages.add(message);
    }

    @Then("Lucy should hear Sean's message")
    public void lucy_hears_Sean_s_message() throws Throwable {
        List<String> messages = world.messagesShoutedBy.get("Sean");
        assertEquals(messages, world.people.get("Lucy").getMessagesHeard());
    }

    @Then("Lucy should hear a shout")
    public void lucy_should_hear_a_shout() throws Throwable {
        assertEquals(1, world.people.get("Lucy").getMessagesHeard().size());
    }

    @Then("{word} should not hear a shout")
    public void person_should_not_hear_a_shout(String name) throws Throwable {
        assertEquals(0, world.people.get(name).getMessagesHeard().size());
    }

    @Then("Lucy hears the following messages:")
    public void lucy_hears_the_following_messages(DataTable expectedMessages) {
        List<List<String>> actualMessages = new ArrayList<List<String>>();
        List<String> heard = world.people.get("Lucy").getMessagesHeard();
        for (String message : heard) {
            actualMessages.add(Collections.singletonList(message));
        }
        expectedMessages.diff(DataTable.create(actualMessages));
    }

    @Then("Lucy hears all Sean's messages")
    public void lucy_hears_all_Sean_s_messages() throws Throwable {
        List<String> heardByLucy = world.people.get("Lucy").getMessagesHeard();
        List<String> messagesFromSean = world.messagesShoutedBy.get("Sean");

        // Hamcrest's hasItems matcher wants an Array, not a List.
        String[] messagesFromSeanArray = messagesFromSean.toArray(new String[messagesFromSean.size()]);
        assertThat(heardByLucy, hasItems(messagesFromSeanArray));
    }

    @Then("Sean should have {int} credits")
    public void sean_should_have_credits(int expectedCredits) {
        assertEquals(expectedCredits, world.people.get("Sean").getCredits());
    }
}
