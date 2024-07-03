package io.cucumber.shouty.support;

import io.cucumber.java.en.When;

public class ShoutSteps {
    private final ShoutyWorld world;

    public ShoutSteps(ShoutyWorld world) {
        this.world = world;
    }

    @When("Sean shouts")
    public void sean_shouts() throws Throwable {
        world.shout("Hello, world");
    }

    @When("Sean shouts {string}")
    public void sean_shouts_message(String message) throws Throwable {
        world.shout(message);
    }

    @When("Sean shouts {int} messages containing the word {string}")
    public void sean_shouts_messages_containing_the_word(int count, String word) throws Throwable {
        String message = "a message containing the word " + word;
        for (int i = 0; i < count; i++) {
            world.shout(message);
        }
    }

    @When("Sean shouts the following message")
    public void sean_shouts_the_following_message(String message) throws Throwable {
        world.shout(message);
    }

    @When("Sean shouts a message")
    public void sean_shouts_a_message() throws Throwable {
        world.shout("here is a message");
    }

    @When("Sean shouts a long message")
    public void sean_shouts_a_long_message() throws Throwable {
        String longMessage = String.join(
                "\n",
                "A message from Sean",
                "that spans multiple lines");
        world.shout(longMessage);
    }

    @When("Sean shouts {int} over-long messages")
    public void sean_shouts_some_over_long_messages(int count) throws Throwable {
        String baseMessage = "A message from Sean that is 181 characters long ";
        String padding = "x";
        String overlongMessage = baseMessage + padding.repeat(181 - baseMessage.length());

        for (int i = 0; i < count; i++) {
            world.shout(overlongMessage);
        }
    }

}