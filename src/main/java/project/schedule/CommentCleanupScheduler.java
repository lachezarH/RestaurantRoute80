package project.schedule;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.repository.CommentsRepository;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.HOURS;

@Component
public class CommentCleanupScheduler {

  private final CommentsRepository commentsRepository;

  public CommentCleanupScheduler(
      CommentsRepository commentsRepository) {
    this.commentsRepository = commentsRepository;
  }

  //second:0 minute:0 hours:2 dayOfMonth:1/15 month:every day: ? year:every
  @Scheduled(cron = "0 0 2 1/15 * ? *")
  public void cleanUpOldAnnouncements() {
    Instant deleteUpTo = Instant.now().minus(168, HOURS);
    commentsRepository.deleteByUpdatedOnBefore(deleteUpTo);
  }
}
