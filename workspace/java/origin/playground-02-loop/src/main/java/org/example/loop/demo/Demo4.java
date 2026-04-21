/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-08-12 21:03:32 UTC+08:00
 ****************************************************/
package org.example.loop.demo;

/**
 * @author Beau Dean
 */
public class Demo4 {

    public static void main(String[] args) {

        int sum = 0;

        for (int i = 1; i < 101; i++) {

            if (i % 2 == 0) {
                sum += i;
            }

        }

        System.out.println("1-100的偶数和为: " + sum);

    }

}
