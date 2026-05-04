/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2026-05-04 04:45:12 UTC+08:00
 ****************************************************/
package host.fairy.queue.priority;

import host.fairy.queue.Queue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Beau Dean
 * @version 1.0
 */
public class HeapPriorotyQueueTest {
    @Test
    @DisplayName("offer")
    void offer() {
        Queue<Entity> queue = new HeapPriorityQueue<>(10);
        
        // todo: bug
        queue.offer(new Entity("A", 20));
        queue.offer(new Entity("B", 10));
        queue.offer(new Entity("C", 30));

        System.out.println(queue.toString());
    }
}
