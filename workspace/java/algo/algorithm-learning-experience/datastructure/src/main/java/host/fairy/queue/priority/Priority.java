/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-09-17 15:13:41 UTC+08:00
 ****************************************************/
package host.fairy.queue.priority;

/**
 * Priority Interface
 *
 * @author Beau Dean
 * @version 1.0
 */
public interface Priority {
    /**
     * Get priority, the smaller the value, the higher the priority.
     *
     * @return Priority value.
     */
    int getPriority();
}
