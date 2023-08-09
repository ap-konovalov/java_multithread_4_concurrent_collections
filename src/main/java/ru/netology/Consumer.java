package ru.netology;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Consumer implements Runnable {

    private final String checkedChar;

    private final BlockingQueue<String> queue;

    private Map<Integer, String> resultMap = new ConcurrentHashMap<>(1);

    public Consumer(String checkedChar, BlockingQueue<String> queue) {
        this.checkedChar = checkedChar;
        this.queue = queue;
    }

    @Override
    public void run() {
        String currentString;
        try {
            while (!((currentString = queue.poll(15, TimeUnit.SECONDS)).equals(Commands.DONE.name()))) {
                int currentStringCharsCount = calculateCountOfChar(currentString);
                if (!resultMap.isEmpty()) {
                    checkAndUpdateResult(currentString, currentStringCharsCount);
                } else {
                    resultMap.put(currentStringCharsCount, currentString);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        printResult();
    }

    private void checkAndUpdateResult(String currentString, int currentStringCharsCount) {
        if (currentStringCharsCount > resultMap.keySet().stream().findFirst().orElseThrow()) {
            resultMap.clear();
            resultMap.put(currentStringCharsCount, currentString);
        }
    }

    private void printResult() {
        Integer resultKey = resultMap.keySet().stream().findFirst().orElseThrow();
        System.out.println("Наибольшее количество символов '" + checkedChar + "' - " + resultKey + " шт. содержится в " +
                "строке:\n" + resultMap.get(resultKey));
    }

    private int calculateCountOfChar(String word) {
        Pattern pattern = Pattern.compile("[^" + checkedChar + "]*" + checkedChar);
        Matcher matcher = pattern.matcher(word);
        int result = 0;
        while (matcher.find()) {
            result++;
        }
        return result;
    }
}
