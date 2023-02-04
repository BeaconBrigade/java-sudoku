import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Character.isWhitespace;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();

        String strInput = readFile(filePath);

        int[] input = parse(strInput);

        Sudoku solver = new Sudoku(input);
        solver.solve();
        solver.printSolution();
    }

    private static String readFile(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            StringBuilder data = new StringBuilder();
            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine());
            }
            myReader.close();

            return data.toString();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
            throw new RuntimeException(e);
        }
    }

    private static int[] parse(String input) {
        int i = 0;
        int[] puzzle = new int[81];

        for (char c : input.toCharArray()) {
            if (isWhitespace(c)) {
                continue;
            }

            int thisSquare;
            try {
                thisSquare = parseInt(String.valueOf(c), 10);
            } catch (NumberFormatException e) {
                thisSquare = 0;
            }
            puzzle[i] = thisSquare;

            i += 1;
            if (i == 81) {
                break;
            }
        }

        if (i < 81) {
            throw new Error("Not enough input. The file was not long enough to construct a complete puzzle.");
        } else {
            return puzzle;
        }
    }
}