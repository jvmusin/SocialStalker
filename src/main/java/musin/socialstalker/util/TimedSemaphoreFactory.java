package musin.socialstalker.util;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Positive;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Semaphore;

/**
 * A {@link TimedSemaphore} factory that releases a semaphore
 * after some period of time after calling {@link AcquireResult#close()}.
 */
@Component
@RequiredArgsConstructor
public class TimedSemaphoreFactory {

  private final TaskScheduler taskScheduler;

  /**
   * Creates a {@link TimedSemaphore} with the given number of permits.
   *
   * <p>It will release a permit after a given {@code period} of time.
   *
   * @param permits the number of permits
   * @param period  the period of time between {@link AcquireResult#close()} and the actual release of permit.
   * @return the newly created {@link TimedSemaphore}
   */
  public TimedSemaphore create(@Positive int permits, @NotNull Duration period) {
    return new TimedSemaphoreImpl(new Semaphore(permits, true), period);
  }

  @RequiredArgsConstructor
  private class TimedSemaphoreImpl implements TimedSemaphore {
    private final Semaphore semaphore;
    private final Duration period;

    @Override
    public AcquireResult acquire() throws InterruptedException {
      semaphore.acquire();
      return () -> taskScheduler.schedule(semaphore::release, Instant.now().plus(period));
    }
  }
}
