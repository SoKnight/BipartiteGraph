import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static java.lang.String.format;

public final class BipartiteTest {

    /*
    #4. Определение двудольности графа.

    Входные данные:
        В первой строке записано одно число n – количество вершин в графе.
        Далее располагается матрица смежности графа (n строк по n чисел в каждой).

    Выходные данные:
        Если граф является двудольным, то:
            вывести в первой строке список номеров вершин первой доли,
            во второй строке – список номеров вершин второй доли.
        Если граф не является двудольным, то:
            в первой строке вывести «NOT BIPARTITE»,
            а во второй строке вывести какой-нибудь цикл нечетной длины в виде последовательности номеров вершин.
     */

    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String OUTPUT_FILE_NAME = "output.txt";

    private static final String NOT_BIPARTITE_RESULT = "NOT BIPARTITE";

    public static void main(String[] args) throws IOException {
        AdjacencyMatrix matrix = loadMatrix();

        List<String> result = new ArrayList<>();
        long start = System.currentTimeMillis();

        try {
            matrix.checkForBipartite();
            matrix.findColorParts().forEach((color, part) -> result.add(part.toString()));
        } catch (BipartiteGraphException ex) {
            result.add(NOT_BIPARTITE_RESULT);
            result.add(ex.getChain().toString());
        }

        saveResult(result);
        long time = System.currentTimeMillis() - start;

        System.out.printf("Time elapsed: %.2f second(s)%n", time / 1000D);
    }

    private static AdjacencyMatrix loadMatrix() throws IOException {
        Path inputFile = Paths.get(INPUT_FILE_NAME);
        if (!Files.isRegularFile(inputFile))
            throw new IOException("Input file not exists!");

        List<String> lines = Files.readAllLines(inputFile);

        Iterator<String> iterator = lines.iterator();
        if (!iterator.hasNext())
            throw new IOException("Input file is empty!");

        try {
            int countOfNodes = Integer.parseInt(iterator.next());
            boolean[][] matrix = new boolean[countOfNodes][countOfNodes];

            for (int i = 0; i < countOfNodes; i++) {
                if (!iterator.hasNext())
                    throw new IOException("Input file contains a bad matrix!");

                String[] digits = iterator.next().split(" ");
                if (digits.length != countOfNodes)
                    throw new IOException("Input file contains a bad matrix!");

                for (int j = 0; j < countOfNodes; j++) {
                    if (digits[j].equals("1")) {
                        matrix[i][j] = true;
                    }
                }
            }

            return new AdjacencyMatrix(countOfNodes, matrix);
        } catch (NumberFormatException ex) {
            throw new IOException("Input file must contain only digits!");
        }
    }

    private static void saveResult(List<String> result) throws IOException {
        Path outputFile = Paths.get(OUTPUT_FILE_NAME);
        Files.write(outputFile, result, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

}
