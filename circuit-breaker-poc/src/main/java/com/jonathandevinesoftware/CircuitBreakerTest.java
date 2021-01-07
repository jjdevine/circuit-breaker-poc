package com.jonathandevinesoftware;

import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.Fallback;

import java.time.Duration;

/**
 * Hello world!
 *
 */
public class CircuitBreakerTest
{
    public static void main( String[] args ) throws Exception
    {

        FailSafeCircuitBreaker();
    }

    private static void FailSafeCircuitBreaker() throws Exception {

        Service service = new Service();

        CircuitBreaker<String> breaker = new CircuitBreaker<String>()
                .withDelay(Duration.ofSeconds(5)) //60 seconds til transition to half open
                .withFailureThreshold(3, Duration.ofMinutes(1)); //open circuit with 3 failures in one minute

        Fallback<String> fallback = Fallback.of(service.fallback());


        for(int x=0; x<30; x++) {

            String result = Failsafe.with(fallback, breaker).get(() -> service.doSomething()).toString();
            System.out.println(result);
            System.out.println(String.format("Circuit breaker state is %s", breaker.getState()));
            Thread.sleep(500);
        }
    }

}
