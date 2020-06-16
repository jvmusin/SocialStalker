package musin.seeker.util;

public interface TimedSemaphore {
  boolean acquire() throws InterruptedException;

  void scheduleRelease();
}
