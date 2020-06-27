package encryptdecrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, String> keys = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            keys.put(args[i], args[i + 1]);
        }

        String operation = keys.getOrDefault("-mode", "enc");
        String text = keys.getOrDefault("-data", "");
        int number = Integer.parseInt(keys.getOrDefault("-key", "0"));
        String pathToOutFile = keys.getOrDefault("-out", null);
        String algorithm= keys.getOrDefault("-alg", "shift");

        if (keys.containsKey("-in") && !keys.containsKey("-data")) {
            try {
                text = readTextFromFile(keys.get("-in"));
            } catch (IOException e) {
                System.out.println("Error " + e);
                return;
            }
        }

        Codec codec = CodecStaticFactory.newCodec(algorithm);

        if (codec != null) {
            switch (operation) {
                case "enc":
                    printResult(codec.encrypt(text, number), pathToOutFile);
                    break;
                case "dec":
                    printResult(codec.decrypt(text, number), pathToOutFile);
                    break;
            }
        }
    }

    private static void printResult(String text, String pathToOutFile) {
        if (pathToOutFile == null) {
            System.out.println(text);
        } else {
            try (PrintWriter writer = new PrintWriter(pathToOutFile)) {
                writer.print(text);
            } catch (IOException e) {
                System.out.println("Error " + e);
            }
        }
    }

    private static String readTextFromFile(String pathToInFile) throws IOException {
        try (Scanner scanner = new Scanner(Paths.get(pathToInFile))) {
            return scanner.nextLine();
        }
    }
}

class CodecStaticFactory {

    public static Codec newCodec(String algorithm) {
        if (algorithm.equals("shift")) {
            return new ShiftCodec();
        } else if (algorithm.equals("unicode")) {
            return new UnicodeCodec();
        }
        return null; // if not a suitable type
    }
}

interface Codec {
    String encrypt(String text, int key);
    String decrypt(String text, int key);
}

class ShiftCodec implements Codec {

    @Override
    public String encrypt(String text, int key) {
        StringBuilder builder = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                builder.append((char) ((c - 97 + key) % 26 + 97));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    @Override
    public String decrypt(String text, int key) {
        StringBuilder builder = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                builder.append((char) ((c + (26 * (1 + key / 26)) - 97 - key) % 26 + 97));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}

class UnicodeCodec implements Codec {

    @Override
    public String encrypt(String text, int key) {
        StringBuilder builder = new StringBuilder();
        for (char c : text.toCharArray()) {
            builder.append((char) (c + key));
        }
        return builder.toString();
    }

    @Override
    public String decrypt(String text, int key) {
        StringBuilder builder = new StringBuilder();
        for (char c : text.toCharArray()) {
            builder.append((char) (c - key));
        }
        return builder.toString();
    }
}
