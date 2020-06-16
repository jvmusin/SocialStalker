package musin.seeker.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Semaphore;

@Component
@RequiredArgsConstructor
public class TimedSemaphoreFactory {

  private final TaskScheduler taskScheduler;

  public TimedSemaphore create(int limit, Duration period) {
    return new TimedSemaphoreImpl(new Semaphore(limit, true), period);
  }

  @RequiredArgsConstructor
  private class TimedSemaphoreImpl implements TimedSemaphore {
    private final Semaphore semaphore;
    private final Duration period;

    @Override
    public boolean acquire() {
      try {
        semaphore.acquire();
        return true;
      } catch (InterruptedException e) {
        // nothing to do here, semaphore not acquired
        return false;
      }
    }

    @Override
    public void scheduleRelease() {
      taskScheduler.schedule(semaphore::release, Instant.now().plus(period));
    }
  }
}
