package ru.netology;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//https://github.com/netology-code/jd-homeworks/blob/video/concurrent_collections/README.md
public class Main {

    private static BlockingQueue<String> aQueue = new ArrayBlockingQueue<>(100);
    private static BlockingQueue<String> bQueue = new ArrayBlockingQueue<>(100);
    private static BlockingQueue<String> cQueue = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {
        new Thread(new Producer(aQueue, bQueue, cQueue)).start();
        new Thread(new Consumer("a", aQueue)).start();
        new Thread(new Consumer("b", bQueue)).start();
        new Thread(new Consumer("c", cQueue)).start();
    }
}