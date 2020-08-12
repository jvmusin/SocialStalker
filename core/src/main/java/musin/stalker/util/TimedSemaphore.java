package musin.stalker.util;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

/**
 * A semaphore with {@link Semaphore} inside.
 *
 * <p>{@link Semaphore#release()} is handled by a factory that creates the {@link TimedSemaphore}.
 *
 * <p>After acquiring you'll get an {@link AcquireResult} on which you should
 * call {@link AcquireResult#close()} method after doing the work.
 *
 * <p>Calling {@link AcquireResult#close()} will handle the factory to release a permit in some time.
 *
 * <p>Note that {@link AcquireResult} is {@link AutoCloseable}, so use it in try-with-resources initializer block.
 *
 * @see TimedSemaphoreFactory
 */
public interface TimedSemaphore {
  /**
   * Tries to acquire a permit. Blocks the calling thread until a permit is available.
   *
   * <p>If the calling thread was interrupted at the moment of the call of while waiting for a permit,
   * {@link InterruptedException} is thrown.
   *
   * @return {@link AcquireResult} on which you should call {@link AutoCloseable#close()} after doing the work
   * @throws InterruptedException if the calling thread was interrupted
   * @see Semaphore#acquire()
   */
  AcquireResult acquire() throws InterruptedException;

  /**
   * Acquires a permit, executes the task and then releases the permit.
   *
   * @param task the task to execute
   * @param <T>  type of result of the task
   * @return result of a task
   */
  @SneakyThrows
  default <T> T execute(Callable<T> task) {
    try (AcquireResult ignored = acquire()) {
      return task.call();
    }
  }
}
