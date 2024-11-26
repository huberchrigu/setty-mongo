package ch.chrigu.setty.mongo.docs;

import ch.chrigu.setty.mongo.TestcontainersConfiguration;
import ch.chrigu.setty.mongo.domain.user.User;
import ch.chrigu.setty.mongo.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(TestcontainersConfiguration.class)
public class MeetingGroupIntTest {
    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private User user;

    @Value("classpath:meeting-group.json")
    private Path meetingGroupBody;

    @Autowired
    public MeetingGroupIntTest(MockMvc mockMvc, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUp() {
        user = userRepository.insert(new User("Huber", "Christoph"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void meetingGroupShouldBeCreated() throws Exception {
        final String body;
        try (var reader = Files.newBufferedReader(meetingGroupBody)) {
            body = reader.lines().reduce("", (a, b) -> a + "\n" + b);
        }

        mockMvc.perform(post("/meetingGroups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body.replace("{user}", "http://localhost:8080/users/" + user.getId())))
                .andExpect(status().isCreated())
                .andDo(document("create-meeting-group"));
    }
}
