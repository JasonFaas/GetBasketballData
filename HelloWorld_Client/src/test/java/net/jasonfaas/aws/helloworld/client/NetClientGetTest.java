package net.jasonfaas.aws.helloworld.client;

import org.junit.Assert;
import org.junit.Test;


public class NetClientGetTest {

    @Test
    public void testStuff() throws Exception {
//        String url = "http://localhost:8080";
//        String url = "http://default-environment-zvvmdifi8c.elasticbeanstalk.com/";
        String url = "http://my2ndelasticbeansta-env.elasticbeanstalk.com/";
        String mainPart = "/hello";
        String clientCall = new NetClientGet().getClientCall(url + mainPart);
        Assert.assertTrue(clientCall.contains("Hello Servlet"));

    }
}