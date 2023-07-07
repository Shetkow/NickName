import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static AtomicInteger counterFor3 = new AtomicInteger();
    public static AtomicInteger counterFor4 = new AtomicInteger();
    public static AtomicInteger counterFor5 = new AtomicInteger();


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread palindrome = new Thread(() -> {
            for (String text : texts) {
                if (isPalendrome(text) && !isOneChar(text)) {
                    isIncrement(text.length());
                }
            }
        });

        Thread oneChar = new Thread(() -> {
            for (String text : texts) {
                if (isOneChar(text)) {
                    isIncrement(text.length());
                }
            }
        });

        Thread orderChar = new Thread(() -> {
            for (String text : texts) {
                if (!isPalendrome(text) && isOrderChar(text)) {
                    isIncrement(text.length());
                }
            }
        });
        palindrome.start();
        orderChar.start();
        oneChar.start();
        palindrome.join();
        orderChar.join();
        oneChar.join();
        System.out.println("Красивых слов с длиной 3: " + counterFor3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counterFor4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counterFor5 + " шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalendrome(String text) {
        return text.equals(new StringBuffer(text).reverse().toString());
    }

    public static boolean isOneChar(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOrderChar(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void isIncrement(int length) {
        if (length == 3) {
            counterFor3.getAndIncrement();
        } else if (length == 4) {
            counterFor4.getAndIncrement();

        } else if (length == 5) {
            counterFor5.getAndIncrement();

        }
    }
}