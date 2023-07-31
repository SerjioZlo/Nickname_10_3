import java.util.Random; import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger counter3 = new AtomicInteger(0);
    static AtomicInteger counter4 = new AtomicInteger(0);
    static AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    count(text);
                }
            }
        });

        Thread sameLetterThread = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    count(text);
                }
            }
        });

        Thread increasingLetterThread = new Thread(() -> {
            for (String text : texts) {
                if (isIncreasingLetter(text)) {
                    count(text);
                }
            }
        });

        palindromeThread.start();
        sameLetterThread.start();
        increasingLetterThread.start();

        try {
            palindromeThread.join();
            sameLetterThread.join();
            increasingLetterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + counter3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter5.get() + " шт");
    }

    public static void count(String text){
        if (text.length() == 3) {
            counter3.incrementAndGet();
        } else if (text.length() == 4) {
            counter4.incrementAndGet();
        } else {
            counter5.incrementAndGet();
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        int length = text.length();
        for (int i = 0; i < length / 2; i++) {
            if (text.charAt(i) != text.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSameLetter(String text) {
        int length = text.length();
        for (int i = 1; i < length; i++) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIncreasingLetter(String text) {
        int length = text.length();
        char currentLetter = text.charAt(0);
        for (int i = 1; i < length; i++) {
            if (text.charAt(i) < currentLetter) {
                return false;
            }
            currentLetter = text.charAt(i);
        }
        return true;
    }
}