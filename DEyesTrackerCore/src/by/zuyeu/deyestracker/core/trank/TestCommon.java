/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.core.trank;

import org.apache.commons.collections4.queue.CircularFifoQueue;

/**
 *
 * @author Fieryphoenix
 */
public class TestCommon {

    public static void main(String[] args) {
        CircularFifoQueue<Integer> numbers = new CircularFifoQueue<>(3);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        System.out.println("first = " + numbers.peek());
        System.out.println("last = " + numbers.get(numbers.size() - 1));
    }
}
