package io.cucumber.shouty;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

public class NetworkTest {
    @Test
    public void broadcasts_a_message_to_all_listeners() {
        Network network = new Network();
        String message = "Free bagels!";
        Person lucy = mock(Person.class);
        network.subscribe(lucy);
        network.broadcast(message);
        verify(lucy).hear(message);
    }
}
