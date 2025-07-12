import java.util.*;

/*
 Imagine you have a secret way of making a sequence of digits (called the "config").
 You start with the number 0.
 At each step, you can either add x or y to your current number.
 After each addition, you write down the last digit (the unit digit) of your current number.
 You keep doing this to build a sequence.

 Now, someone gives you a partial or dirty version of that sequence (some digits might be missing).
 Your job is to figure out:
 What is the smallest possible number you could have written down, step by step, that would produce the given sequence (even if you had to fill in some missing steps)?
*/

/* ############################## solution ##########################
 BFS + State Pruning
 We'll use a Queue for BFS, and a Set to record visited (cur, idx) pairs to avoid cycles.
 We always try both x and y at each step, and as soon as we reach the end of the config, we return the result.
 */

public class RegenerateConfig {

    public static String regenerateSmallest(String config, int x, int y) throws InterruptedException {
        class State {
            int cur;
            int idx;
            String seq;
            State(int cur, int idx, String seq) {
                this.cur = cur;
                this.idx = idx;
                this.seq = seq;
            }
        }

        Queue<State> queue = new LinkedList<>();
        // Use a set to avoid cycles: (cur, idx)
        Set<String> visited = new HashSet<>();

        queue.add(new State(0, 0, ""));
        while (!queue.isEmpty()) {
            State state = queue.poll();
            int cur = state.cur;
            int idx = state.idx;
            String seq = state.seq;

            System.out.println(cur + " " + idx + " " + seq);

            // If we've matched the config, return the result
            if (idx == config.length()) {
                return seq;
            }

            // Try x
            int unitX = (cur + x) % 10;
            String seqX = seq + unitX;
            if (unitX == config.charAt(idx) - '0') {
                // Matched config digit, move to next
                String keyNext = unitX + "," + (idx + 1);
                if (!visited.contains(keyNext)) {
                    visited.add(keyNext);
                    queue.add(new State(unitX, idx + 1, seqX));
                }
            } else {
                // Not matched, stay on this config digit
                String keyX = unitX + "," + idx;
                if (!visited.contains(keyX)) {
                    visited.add(keyX);
                    queue.add(new State(unitX, idx, seqX));
                }
            }

            // Try y (if x != y)
            if (x != y) {
                int unitY = (cur + y) % 10;
                String seqY = seq + unitY;
                if (unitY == config.charAt(idx) - '0') {
                    String keyNext = unitY + "," + (idx + 1);
                    if (!visited.contains(keyNext)) {
                        visited.add(keyNext);
                        queue.add(new State(unitY, idx + 1, seqY));
                    }
                } else {
                    String keyY = unitY + "," + idx;
                    if (!visited.contains(keyY)) {
                        visited.add(keyY);
                        queue.add(new State(unitY, idx, seqY));
                    }
                }
            }
        }
        return "-1";
    }

    public static void main(String[] args) throws InterruptedException {
        String config = "324";
        int x = 2, y = 3;
        System.out.println(regenerateSmallest(config, x, y)); // Output: 36924

         /*
         cur = 0
         cur += y = 3, res=3;
         cur+=y = 6, res=36;
         cur+=y = 9, res=369;
         cur+=y = 12 %10 = 2, res=3692;
         cur+=x = 4, res=36924;
          */
    }
}
