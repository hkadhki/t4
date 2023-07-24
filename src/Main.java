import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    static ArrayBlockingQueue<String> array1 = new ArrayBlockingQueue<>(100);
    static ArrayBlockingQueue<String> array2 = new ArrayBlockingQueue<>(100);
    static ArrayBlockingQueue<String> array3 = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++){
                String text = generateText("abc",10000);
                try {
                    array1.put(text);
                    array2.put(text);
                    array3.put(text);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        new Thread(() -> {
            System.out.println(countSymbol('a',array1) + "-a");
        }).start();

        new Thread(() -> {
            System.out.println(countSymbol('b',array2) + "-b");
        }).start();

        new Thread(() -> {
            System.out.println(countSymbol('c',array3) + "-c");
        }).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int countSymbol(char ch, ArrayBlockingQueue<String> array){
        int count = 0;
        for (int i = 0; i < 100; i++){
            try {
                String text = array.take();
                int countSym = 0;
                for (char element : text.toCharArray()){
                    if (element == ch) countSym++;
                }
                if (count < countSym){
                    count = countSym;
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        return count;
    }
}