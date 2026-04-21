/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-08-18 19:48:45 UTC+08:00
 ****************************************************/
package org.example.extend.pkg4.impl;

import org.example.extend.pkg4.Swimming;

/**
 * @author Beau Dean
 */
public class Dog extends Animal implements Swimming {
    public Dog() {
    }
    
    public Dog(String name, int age) {
        super(name, age);
    }
    
    @Override
    public void eat() {
        System.out.println("吃骨头");
    }
    
    @Override
    public void swimming() {
        System.out.println("狗刨");
    }
}
