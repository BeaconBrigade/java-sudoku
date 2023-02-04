import java.util.Optional;

public class Sudoku {
    private final Node root;
    private Optional<Node> solution;

    public Sudoku(int[] input) {
        this.root = new Node(input);
        this.solution = Optional.empty();
    }

    public Optional<Node> solve() {
        this.solution = this.root.backtrack();
        return this.solution;
    }

    public void printSolution() {
        if (this.solution.isEmpty()) {
            throw new Error("Puzzle wasn't solved");
        }

        this.solution.get().print();
    }
}
