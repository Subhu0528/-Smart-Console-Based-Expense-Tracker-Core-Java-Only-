package utils;

import java.io.*;
import datastructures.MyLinkedList;

public class FileUtil {

    public static MyLinkedList<String> readLines(String filename) {
        MyLinkedList<String> lines = new MyLinkedList<>();
        File file = new File(filename);

        if (!file.exists()) return lines;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }

        return lines;
    }

    public static void writeLines(String filename, MyLinkedList<String> lines, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) {
            for (int i = 0; i < lines.size(); i++) {
                bw.write(lines.get(i));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + filename);
        }
    }

    public static void writeLine(String filename, String line, boolean append) {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add(line);
        writeLines(filename, list, append);
    }
}
