package ch.chrigu.setty.mongo;

import org.springframework.boot.SpringApplication;

public class TestSettyMongoApplication {

    public static void main(String[] args) {
        SpringApplication.from(SettyMongoApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
