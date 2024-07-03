package io.cucumber.shouty.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.cucumber.shouty.Network;
import io.cucumber.shouty.Person;

public class ShoutyWorld {

    private static final int DEFAULT_RANGE = 0;
    public Network network = new Network(ShoutyWorld.DEFAULT_RANGE);
    public Map<String, Person> people = new HashMap<String, Person>();
    public Map<String, List<String>> messagesShoutedBy = new HashMap<String, List<String>>();

    
    public void shout(String message) {
        people.get("Sean").shout(message);
        List<String> messages = messagesShoutedBy.get("Sean");
        if (messages == null) {
            messages = new ArrayList<String>();
            messagesShoutedBy.put("Sean", messages);
        }
        messages.add(message);
    }
}
