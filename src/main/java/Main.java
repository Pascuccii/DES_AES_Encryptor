import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ввыберите метод шифрования:\n1.DES\n2.AES");
        switch (scan.nextInt()) {
            case 1:
                des();
                break;
            case 2:
                aes();
                break;
        }
    }

    private static void aes() {
        BufferedReader passReader = null;
        try {
            passReader = new BufferedReader(new FileReader("source.txt"));
            AES aes = new AES();
            String originalString = passReader.readLine();
            String encryptedString = aes.encrypt(originalString);
            String decryptedString = aes.decrypt(encryptedString);

            System.out.println(originalString);
            System.out.println(encryptedString);
            System.out.println(decryptedString);

            FileWriter resWriter = new FileWriter(new File("result.txt"), false);
            resWriter.write(decryptedString);
            resWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void des() throws Exception {
        String text = "";
        String digest;
        DES en = null;
        Scanner scan = new Scanner(System.in);
        System.out.println("Выберите режим загрузки данных:\n1.Из файлов\n2.С клавиатуры");
        switch (scan.nextInt()) {
            case 1:
                BufferedReader passReader = new BufferedReader(new FileReader("source.txt"));
                text = passReader.readLine();

                en = new DES();
                break;
            case 2:
                System.out.println("Введите текст для шифрования:");
                do {
                    text = scan.nextLine();
                } while (text.length() < 2 || text.length() > 30);

                System.out.println("Введите ключ для шифрования:");
                do {
                    digest = scan.nextLine();
                } while (digest.length() < 2 || digest.length() > 30);

                en = new DES(digest.getBytes(StandardCharsets.UTF_8));
                break;
        }

        assert en != null;
        byte[] codedtext = en.encrypt(text);
        String decodedtext = en.decrypt(codedtext);

        System.out.println(decodedtext);
        FileWriter resWriter = new FileWriter(new File("result.txt"), false);
        System.out.println(Arrays.toString(codedtext));
        resWriter.write(decodedtext);
        resWriter.flush();
    }
}