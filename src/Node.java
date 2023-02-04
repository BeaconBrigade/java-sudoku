import java.util.Optional;

public class Node {
    public int[] candidate;
    public Optional<Node>[] children;
    private int mostRecent;

    public Node(int[] input) {
        this.candidate = input;
        this.children = new Optional[9];
        this.mostRecent = 0;
    }

    private static boolean hasDoubles(int[] counter) {
        for (int i = 1; i < 10; i++) {
            if (counter[i] > 1) {
                return true;
            }

            counter[i] = 0;
        }

        return false;
    }

    private static boolean containsZero(int[] input) {
        for (int i : input) {
            if (i == 0) {
                return true;
            }
        }
        return false;
    }

    private static int findZero(int[] input) {
        for (int i = 0; i < input.length; i++) {
            if (input[i] == 0) {
                return i;
            }
        }

        return -1;
    }

    public Optional<Node> backtrack() {
        if (this.reject()) {
            return Optional.empty();
        } else if (this.accept()) {
            return Optional.of(this);
        }

        int toChange = this.first();
        Optional<Node> next = this.children[this.mostRecent];
        Optional<Node> child;

        while (next.isPresent()) {
            child = next;

            Optional<Node> maybe = child.get().backtrack();
            if (maybe.isPresent()) {
                return maybe;
            }

            if (this.next(toChange)) {
                next = this.children[this.mostRecent];

            } else {
                break;
            }
        }
        return Optional.empty();
    }

    public int first() {
        Node child = new Node(this.candidate.clone());

        int toChange = findZero(child.candidate);
        child.candidate[toChange] = 1;

        this.children[0] = Optional.of(child);
        return toChange;
    }

    public boolean next(int toChange) {
        if (this.mostRecent >= 8) {
            return false;
        }

        Optional<Node> prev = this.children[this.mostRecent];
        if (prev.isEmpty()) {
            throw new Error("child was not present when it should have been");
        }

        Node child = new Node(prev.get().candidate.clone());
        child.candidate[toChange]++;

        this.mostRecent++;
        this.children[mostRecent] = Optional.of(child);

        return true;
    }

    public boolean accept() {
        return !containsZero(this.candidate);
    }

    public boolean reject() {
        int[] counter = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] candidate = this.candidate;

        // Horizontal rows
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                counter[candidate[i * 9 + j]]++;
            }

            if (hasDoubles(counter)) {
                return true;
            }
        }

        // Vertical rows
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                counter[candidate[j * 9 + i]]++;
            }

            if (hasDoubles(counter)) {
                return true;
            }
        }

        // traverse rows
        for (int i = 0; i < 3; i++) {
            // traverse columns
            for (int j = 0; j < 3; j++) {
                int offset = (i * 27) + (j * 3);

                // traverse row in each square
                for (int k = 0; k < 3; k++) {
                    counter[candidate[offset + k * 9]]++;
                    counter[candidate[offset + k * 9 + 1]]++;
                    counter[candidate[offset + k * 9 + 2]]++;
                }

                if (hasDoubles(counter)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void print() {
        int[] puzzle = this.candidate;

        System.out.println("+-------+-------+-------+");
        for (int i = 0; i < 3; i++) {
            int offset = i * 27;

            for (int j = 0; j < 3; j++) {
                System.out.format("| %d %d %d | %d %d %d | %d %d %d |\n", puzzle[offset], puzzle[offset + 1], puzzle[offset + 2], puzzle[offset + 3], puzzle[offset + 4], puzzle[offset + 5], puzzle[offset + 6], puzzle[offset + 7], puzzle[offset + 8]);
                offset += 9;
            }
            System.out.println("+-------+-------+-------+");
        }
    }
}
