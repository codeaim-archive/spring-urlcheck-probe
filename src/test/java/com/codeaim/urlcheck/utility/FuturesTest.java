package com.codeaim.urlcheck.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class FuturesTest
{
    @Test
    public void complete()
    {
        ArrayList<CompletableFuture<String>> completableFutures = new ArrayList<>();
        CompletableFuture<List<String>> complete = Futures.complete(completableFutures);

        Assert.assertTrue(complete.isDone());
    }

    @Test
    public void createClass()
    {
        new Futures();
    }
}
