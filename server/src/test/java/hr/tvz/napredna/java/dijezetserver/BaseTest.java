package hr.tvz.napredna.java.dijezetserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.napredna.java.dijezetserver.dto.CommentDto;
import hr.tvz.napredna.java.dijezetserver.dto.UserDto;
import hr.tvz.napredna.java.dijezetserver.mapper.CommentMapper;
import hr.tvz.napredna.java.dijezetserver.mapper.PinMapper;
import hr.tvz.napredna.java.dijezetserver.model.Comment;
import hr.tvz.napredna.java.dijezetserver.model.IssueType;
import hr.tvz.napredna.java.dijezetserver.model.Line;
import hr.tvz.napredna.java.dijezetserver.model.Pin;
import hr.tvz.napredna.java.dijezetserver.model.Station;
import hr.tvz.napredna.java.dijezetserver.model.User;
import hr.tvz.napredna.java.dijezetserver.model.UserRefreshToken;
import hr.tvz.napredna.java.dijezetserver.model.UserRole;
import hr.tvz.napredna.java.dijezetserver.request.UserRequest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = {DiJeZetServerApplication.class})
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
public class BaseTest {
    //STATIONS
    protected static final List<Station> STATIONS = List.of(
            new Station(1L, "Borongaj"),
            new Station(2L, "Držićeva"),
            new Station(3L, "Žitnjak")
    );
    protected static final Station STATION = STATIONS.getFirst();

    //LINES
    protected static final List<Line> LINES = List.of(
            new Line(1L, "2"),
            new Line(2L, "13"),
            new Line(3L, "3")
    );
    protected static final Line LINE = LINES.getFirst();

    //USERS
    protected static final List<User> USERS = List.of(
            new User(1L, "FirstUser", null, UserRole.USER),
            new User(2L, "second_user", null, UserRole.USER),
            new User(3L, "operatorUser", null, UserRole.OPERATOR)
    );
    protected static final User USER = USERS.getFirst();
    protected static final List<UserDto> USER_DTOS = List.of(new UserDto(USER.getId(), USER.getUsername(), USER.getRole()));
    protected static final UserDto USER_DTO = USER_DTOS.getFirst();
    protected static final UserRequest USER_REQUEST = new UserRequest(USER.getUsername(), "randompassword");

    //PINS
    protected static final List<Pin> PINS = List.of(new Pin(1L, STATION, LINE, BigDecimal.valueOf(1.2), BigDecimal.valueOf(100), USER));
    protected static final Pin PIN = PINS.getFirst();

    //COMMENTS
    protected static final List<Comment> COMMENTS = List.of(
            new Comment(1L, "Prvi komentar", USER, PIN, null, List.of(), IssueType.DIRTY, null)
    );
    protected static final Comment COMMENT = COMMENTS.getFirst();
    protected static final CommentDto COMMENT_DTO = CommentMapper.toDto(COMMENT, PinMapper.toDto(COMMENT.getPin(), null, null), List.of());

    //TOKENS
    protected static final UserRefreshToken USER_REFRESH_TOKEN_ACTIVE = new UserRefreshToken(1L, USER, "refreshToken", LocalDateTime.now().plusMinutes(2));
    protected static final UserRefreshToken USER_REFRESH_TOKEN_EXPIRED = new UserRefreshToken(1L, USER, "refreshToken", LocalDateTime.now().minusHours(1));

    protected String toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
