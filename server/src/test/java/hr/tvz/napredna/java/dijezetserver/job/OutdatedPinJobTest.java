package hr.tvz.napredna.java.dijezetserver.job;

import hr.tvz.napredna.java.dijezetserver.BaseTest;
import hr.tvz.napredna.java.dijezetserver.service.CommentService;
import hr.tvz.napredna.java.dijezetserver.service.PinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class OutdatedPinJobTest extends BaseTest {

    @Mock
    private PinService pinService;

    @Mock
    private CommentService commentService;

    private Clock fixedClock;
    private OutdatedPinJob job;

    @BeforeEach
    void setUp() {
        // Fixed time: 2025-06-25 12:00
        fixedClock = Clock.fixed(LocalDateTime.of(2025, 6, 25, 12, 0)
                .toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

        job = new OutdatedPinJob(pinService, commentService, fixedClock);
    }

    @Test
    void shouldDeleteOutdatedPinsAndComments() {
        LocalDateTime expectedTimestamp = LocalDateTime.ofInstant(
                fixedClock.instant().minus(60, ChronoUnit.MINUTES),
                ZoneOffset.systemDefault()
        );

        when(commentService.findAllBefore(expectedTimestamp)).thenReturn(List.of(COMMENT_DTO));

        job.cleanupOutdatedPins();

        verify(commentService).findAllBefore(expectedTimestamp);
        verify(commentService).deleteByPinIds(List.of(1L));
        verify(pinService).deleteByIds(List.of(1L));
    }

    @Test
    void shouldDoNothingIfNoOutdatedComments() {
        LocalDateTime expectedTimestamp = LocalDateTime.ofInstant(
                fixedClock.instant().minus(60, ChronoUnit.MINUTES),
                ZoneOffset.systemDefault()
        );

        when(commentService.findAllBefore(expectedTimestamp)).thenReturn(List.of());

        job.cleanupOutdatedPins();

        verify(commentService).findAllBefore(expectedTimestamp);
        verifyNoMoreInteractions(commentService, pinService);
    }
}
