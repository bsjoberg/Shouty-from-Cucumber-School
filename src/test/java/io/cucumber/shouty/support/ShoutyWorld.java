package io.cucumber.shouty.support;

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

}
