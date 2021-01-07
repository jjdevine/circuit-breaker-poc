package com.jonathandevinesoftware;

import java.util.Random;

public class Service {

    public String doSomething() {
        Random random = new Random();

        if(random.nextInt(10) > 5) {
            throw new RuntimeException("fail");
        }

        return "success";
    }

    public String fallback() {
        return "using fallback";
    }
}
