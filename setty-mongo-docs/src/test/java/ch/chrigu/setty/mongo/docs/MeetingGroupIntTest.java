package ch.chrigu.setty.mongo.docs;

import ch.chrigu.setty.mongo.domain.user.User;
import ch.chrigu.setty.mongo.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingGroupIntTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    private User user;

    @Value("classpath:meeting-group.json")
    private Path meetingGroupBody;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        user = userRepository.insert(new User("Huber", "Christoph"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void meetingGroupShouldBeCreated() throws Exception {
        String body = Files.newBufferedReader(meetingGroupBody).lines().reduce("", (a, b) -> a + "\n" + b);

        mockMvc.perform(post("/meetingGroups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.replace("{user}", "http://localhost:8080/users/" + user.getId())))
                .andExpect(status().isCreated())
                .andDo(document("create-meeting-group"));
    }
}
