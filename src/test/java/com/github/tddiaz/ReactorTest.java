package com.github.tddiaz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

class ReactorTest {

    @Test
    void test1() {
        Flux.just("hello", "world")
                .subscribe(System.out::println);
    }

    @Test
    void test2() {
        String[] words = {"the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};

        Flux.fromArray(words)
                .subscribe(System.out::println);
    }

    @Test
    void test3() {
        String[] words = {"the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};

//        Flux.fromArray(words)
//                .zipWith(Flux.range(1, 50), (word, line) -> line + ". " + word)
//                .subscribe(System.out::println);

        Flux.fromArray(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, 50), (word, line) -> line + ". " + word)
                .subscribe(System.out::println);
    }

    @Test
    void test4() {

        Flux fastClock = Flux.interval(Duration.ofSeconds(1)).map(tick -> "fast clock:" + tick);
        Flux slowClock = Flux.interval(Duration.ofSeconds(2)).map(tick -> "slow clock:" + tick);

        Flux clock = Flux.merge(fastClock, slowClock);
        clock.subscribe(System.out::println);
    }

    @AfterEach
    void after() throws InterruptedException {
        Thread.sleep(5_000);
    }
}
