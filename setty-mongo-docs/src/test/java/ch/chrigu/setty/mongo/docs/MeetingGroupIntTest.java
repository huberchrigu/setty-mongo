package ch.chrigu.setty.mongo.docs;

import ch.chrigu.setty.mongo.domain.user.User;
import ch.chrigu.setty.mongo.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest
public class MeetingGroupIntTest {
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    private User user;

    @Value("classpath:meeting-group.json")
    private Path meetingGroupBody;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
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

    @Test
    @WithMockUser(roles = "ADMIN")
    public void jsonSchemaShouldIncludeArrayOfUris() throws Exception {
        mockMvc.perform(
                get("/profile/meetingGroups")
                        .accept("application/schema+json")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.properties.members.type").value("array"));
    }
}
