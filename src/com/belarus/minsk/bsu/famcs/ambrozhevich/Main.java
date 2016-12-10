package com.belarus.minsk.bsu.famcs.ambrozhevich;

import java.io.*;
import java.util.*;

public class Main {
    private static List<List<String>> lines = new ArrayList<>();
    private static List<Integer> sizes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        readFile();
        toSizes();
        Collections.sort(lines, (List<String> l1, List<String> l2) -> {
            return l1.get(1).compareTo(l2.get(1));
        });
        writeToFile("bytes1.out", lines.iterator(), 1);
        Collections.sort(lines, (l1, l2) -> toSize(l1.get(0)) - toSize(l2.get(0)));
        writeToFile("bytes2.out", lines.iterator(), 1);
        writeToFile("bytes3.out", sizes.iterator());
    }

    private static void toSizes() {
        for (List<String> list : lines)
            sizes.add(computeSize(list));
    }

    private static int toSize(String string) {
        switch (string) {
            case "double":
            case "long":
                return 8;
            case "float":
            case "int":
                return 4;
            case "short":
            case "char":
                return 2;
            case "byte":
            case "boolean":
                return 1;
        }
        return 0;
    }

    private static int computeSize(List<String> args) {
        int add = 4 + 16 + 4;
        int typesize = toSize(args.get(0));
        args = args.subList(2, args.size());
        ListIterator<String> li = args.listIterator(args.size());
        int size = typesize;
        while (li.hasPrevious()) {
            int lenght = Integer.parseInt(li.previous());
            size *= lenght;
            size += add;
        }
        return size;
    }

    private static void writeToFile(String file, Iterator<?> iterator) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        while (iterator.hasNext()) {
            fileWriter.write(iterator.next().toString());
            fileWriter.write(System.lineSeparator());
            fileWriter.flush();
        }
    }


    private static void writeToFile(String file, Iterator<List<String>> iterator, int index) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        while (iterator.hasNext()) {
            fileWriter.write(iterator.next().get(index));
            fileWriter.write(System.lineSeparator());
            fileWriter.flush();
        }
    }

    private static void readFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("bytes.in"));
        while (scanner.hasNextLine()) {
            Scanner line = new Scanner(scanner.nextLine()).useDelimiter("[\\s;\\[\\]]+");
            lines.add(new ArrayList<>());
            while (line.hasNext()) {
                lines.get(lines.size() - 1).add(line.next());
            }
        }
    }
}




