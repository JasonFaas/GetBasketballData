package net.jasonfaas.aws.helloworld.client;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {

    @Test
    public void testHelloWorld() {
        Assert.assertEquals("Hello World!", new App().getHelloWorld());
    }

    @Test
    public void testService() {

    }
}
