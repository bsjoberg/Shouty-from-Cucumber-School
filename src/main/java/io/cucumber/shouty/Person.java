package io.cucumber.shouty;

import java.util.ArrayList;
import java.util.List;

public class Person {  
    private final List<String> messagesHeard = new ArrayList<String>();
    private final Network network;

    public Person(Network network) {
        this.network = network;
        network.subscribe(this);
    }

    public void shout(String message) {
        network.broadcast(message);
    }

    public List<String> getMessagesHeard() {
        return messagesHeard;
    }

    public void hear(String message) {
        messagesHeard.add(message);
    }

}
