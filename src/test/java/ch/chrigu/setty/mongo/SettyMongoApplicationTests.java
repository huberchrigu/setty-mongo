package ch.chrigu.setty.mongo;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class SettyMongoApplicationTests {

    @Test
    public void contextLoads() {
    }

}

