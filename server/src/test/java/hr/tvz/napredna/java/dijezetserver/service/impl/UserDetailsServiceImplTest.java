package hr.tvz.napredna.java.dijezetserver.service.impl;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql(scripts = "/db/init-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserDetailsServiceImplTest extends BaseTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldReturnUserDetailsWhenUserExists() {
        UserDetails result = userDetailsService.loadUserByUsername("test");

        assertNotNull(result);
        assertEquals("test", result.getUsername());
    }

    @Test
    void shouldThrowWhenUserDoesNotExist() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("iDontExist");
        });
    }
}