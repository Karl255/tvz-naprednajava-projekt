package hr.tvz.napredna.java.dijezetserver.job;

import hr.tvz.napredna.java.dijezetserver.service.CommentService;
import hr.tvz.napredna.java.dijezetserver.service.PinService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Component
public class OutdatedPinJob {

    private static final int OUTDATED_AFTER_MINUTES = 60;

    private final PinService pinService;
    private final CommentService commentService;
    private final Clock clock;

    public OutdatedPinJob(PinService pinService, CommentService commentService, Clock clock) {
        this.pinService = pinService;
        this.commentService = commentService;
        this.clock = clock;
    }

    @Scheduled(cron = "* */5 * * * *")
    @Transactional
    public void cleanupOutdatedPins() {
        System.out.println("cleanupOutdatedPins");
        var outdatedTimestamp = LocalDateTime.ofInstant(clock.instant().minus(OUTDATED_AFTER_MINUTES, ChronoUnit.MINUTES), ZoneOffset.systemDefault());
        var outdatedTopComments = commentService.findAllBefore(outdatedTimestamp);

        var outdatedPinIds = outdatedTopComments.stream().map(comment -> comment.getPin().getId()).toList();

        if (outdatedPinIds.isEmpty()) {
            return;
        }

        commentService.deleteByPinIds(outdatedPinIds);
        pinService.deleteByIds(outdatedPinIds);
    }
}
