import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static final int ARRAY_CAPACITY = 100_000;
    private static final int AMOUNT_OF_TEXTS = 10;
    private static final int LENGTH_OF_TEXTS = 100_00;
    private static final BlockingQueue<String> aQueue = new ArrayBlockingQueue<>(ARRAY_CAPACITY);
    private static final BlockingQueue<String> bQueue = new ArrayBlockingQueue<>(ARRAY_CAPACITY);
    private static final BlockingQueue<String> cQueue = new ArrayBlockingQueue<>(ARRAY_CAPACITY);

    public static void main(String[] args) throws InterruptedException {
        Thread fillThread = new Thread(() -> {
            for (int i = 0; i < AMOUNT_OF_TEXTS; i++) { //заполняем очереди
                aQueue.add(generateText("abc", LENGTH_OF_TEXTS));
                bQueue.add(generateText("abc", LENGTH_OF_TEXTS));
                cQueue.add(generateText("abc", LENGTH_OF_TEXTS));
            }

        });

        fillThread.start();
        Thread aThread = new Thread(() -> {
            int maxSize = 0;
            while (!(aQueue.isEmpty()) || fillThread.isAlive()) {
                int counter = 0;
                String text = null;
                try {
                    if (!aQueue.isEmpty()) {
                        text = aQueue.take();
                    } else {
                        Thread.sleep(1000);
                        text = aQueue.take();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == 'a') {
                        ++counter;
                    }
                }
                if (counter > maxSize) {
                    maxSize = counter;
                }
            }
            System.out.println("Максимальное количество букв 'a' в слове: " + maxSize);
        });

        Thread bThread = new Thread(() -> {
            int maxSize = 0;
            while (!(bQueue.isEmpty()) || fillThread.isAlive()) {
                int counter = 0;
                String text = null;
                try {
                    if (!bQueue.isEmpty()) {
                        text = bQueue.take();
                    } else {
                        Thread.sleep(1000);
                        text = bQueue.take();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == 'b') {
                        ++counter;
                    }
                }
                if (counter > maxSize) {
                    maxSize = counter;
                }
            }
            while(aThread.isAlive()){}
            System.out.println("Максимальное количество букв 'b' в слове: " + maxSize);
        });

        Thread cThread = new Thread(() -> {
            int maxSize = 0;
            while (!(cQueue.isEmpty()) || fillThread.isAlive()) {
                int counter = 0;
                String text = null;
                try {
                    if (!cQueue.isEmpty()) {
                        text = cQueue.take();
                    } else {
                        Thread.sleep(1000);
                        text = cQueue.take();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == 'c') {
                        ++counter;
                    }
                }
                if (counter > maxSize) {
                    maxSize = counter;
                }
            }
            while(bThread.isAlive()){}
            System.out.println("Максимальное количество букв 'c' в слове: " + maxSize);
        });

        aThread.start();
        bThread.start();
        cThread.start();
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) { //
            text.append(letters.charAt(random.nextInt(letters.length()))); //генерирует строку text из букв aab
        }
        return text.toString();
    }
}