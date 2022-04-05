package utils;

import java.io.*;
import java.util.Scanner;

class MyBufferedReader {
    private FileInputStream in;
    private StringBuffer buffer;

    public MyBufferedReader(FileInputStream in, StringBuffer buffer) {
        this.in = in;
        this.buffer = new StringBuffer();
    }

    public String nextLine() throws IOException {
        while (true) {
            int c = in.read();
            if (c == -1 || c == '\n') {
                break;
            }
            buffer.append((char) c);
        }
        String output = buffer.toString();
        buffer = new StringBuffer();
        return output;
    }
}

public class FileIO {
    public static void main(String[] args) throws IOException {
        // try with resource, same as close both streams in finally block
        // reading 8-bit unicode
        try (final FileInputStream in = new FileInputStream("./var/txt/input.txt");
             final FileOutputStream out = new FileOutputStream("./var/txt/output.txt")) {
            int c;
            while ((c = in.read()) != -1) {
                System.out.println((char) c);
                out.write(c);
            }
        }

        try (final FileInputStream in = new FileInputStream("./var/txt/input.txt");
             final FileOutputStream out = new FileOutputStream("./var/txt/output.txt")) {
            byte[] bytes = new byte[7];
            int n = in.read(bytes);
            System.out.println("n = " + n);
            for (byte b : bytes) {
                System.out.println((char) b);
            }
        }

        // what if we use FileInputStream to read 16-bit unicode
        try (final FileReader in = new FileReader("./var/txt/inputWithUnicodeChar.txt");
             final FileWriter out = new FileWriter("./var/txt/outputWithUnicodeChar.txt")) {
            int c;
            while ((c = in.read()) != -1) {
                System.out.println((char) c);
                out.write(c);
            }
        }

        // reading byte by byte and convert it to char, 16-bit
        try (final InputStreamReader cin = new InputStreamReader(System.in)) {
            System.out.println("Enter characters, press 'q' to quit:");
            // StringBuilder is faster but not thread safe
            StringBuffer userInput = new StringBuffer();
            while (true) {
                char c = (char) cin.read();
                if (c == 'q') {
                    break;
                }
                userInput.append(c);
            }
            System.out.println(userInput);
        }

        // reading file line by line
        try (final FileInputStream fis = new FileInputStream("./var/txt/inputWithUnicodeChar.txt")) {
            // construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }

        // standard input
        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please type: ");
            while (true) {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println("Input text: " + line);
            }
        }

        String dirName = "/Users/ctong/Workspace/testdata/";
        File d = new File(dirName);
        // Create directory now
        d.mkdirs();
        // list down all the files and directories available
        String[] paths = d.list();
        for (String p : paths) {
            System.out.println(p);
        }
    }
}
