package ru.netology;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable{

    private static final Random random = new Random();

    private final BlockingQueue<String> aQueue;
    private final BlockingQueue<String> bQueue;
    private final BlockingQueue<String> cQueue;

    public Producer(BlockingQueue<String> aQueue, BlockingQueue<String> bQueue, BlockingQueue<String> cQueue) {
        this.aQueue = aQueue;
        this.bQueue = bQueue;
        this.cQueue = cQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10_000; i++) {
            String randomString = generateText("abc", 100_000);
            try {
                aQueue.offer(randomString, 10, TimeUnit.SECONDS);
                bQueue.offer(randomString, 10, TimeUnit.SECONDS);
                cQueue.offer(randomString, 10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            aQueue.offer(Commands.DONE.name(), 10, TimeUnit.SECONDS);
            bQueue.offer(Commands.DONE.name(), 10, TimeUnit.SECONDS);
            cQueue.offer(Commands.DONE.name(), 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static String generateText(String letters, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
