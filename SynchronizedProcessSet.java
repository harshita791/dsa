import java.util.*;

// synchronized set — a subset of intervals where one interval overlaps with all others in the subset.
// Find the largest subset of intervals that all intersect with a common interval.
// Find minimum number of processes that need to drop so that the remaining set is a synchronized set


public class SynchronizedProcessSet {

    // TC = O(n^2)
    public static int findMinimumDrops(List<Integer> starts, List<Integer> ends) {
        int numOfProcess = starts.size();
        int maxOverlaps = 0;
        for(int i=0; i<numOfProcess; i++) {
            int current = 0;
            for(int j=0; j<numOfProcess; j++) {
                if(!(starts.get(i) > ends.get(j) || starts.get(j) > ends.get(i))) {
                    current++;
                }
            }
            maxOverlaps = Math.max(maxOverlaps, current);
        }

        return numOfProcess - maxOverlaps;
    }

    // TC = O(nlogn)
    public static int findMinimumDropsOptimised(List<Integer> starts, List<Integer> ends) {
        int numOfProcess = starts.size();
        List<List<Integer>> processes = new ArrayList<>();
        for(int i=0;i<numOfProcess;i++) {
            List<Integer> temp = new ArrayList<>();
            temp.add(starts.get(i));
            temp.add(ends.get(i));
            processes.add(temp);
        }

        processes.sort(Comparator.comparing((List<Integer> a) -> a.get(0)).thenComparing(a -> a.get(1)));

        int maxOverlap = 0;

        Collections.sort(starts);
        Collections.sort(ends);

        for(int i=0; i<numOfProcess; i++) {
            // Count intervals that start <= e
            int left = upperBound(starts, ends.get(i));
            // Count intervals that end < s → non-overlapping from left
            int right = lowerBound(ends, starts.get(i));

            maxOverlap = Math.max(maxOverlap, left - right);
        }

        return numOfProcess - maxOverlap;
    }

    public static int upperBound(List<Integer> arr, int target) {
        int low = 0, high = arr.size();
        while(low < high) {
            int mid = (low + high)/2;
            if(arr.get(mid) <= target) low = mid+1;
            else high = mid;
        }
        return low;
    }

    public static int lowerBound(List<Integer> arr, int target) {
        int low = 0, high = arr.size();
        while(low < high) {
            int mid = (low + high)/2;
            if(arr.get(mid) < target) low = mid+1;
            else high = mid;
        }
        return low;
    }

    public static void main(String[] args) {
        List<Integer> starts = Arrays.asList(3, 4, 6, 9, 1);
        List<Integer> ends = Arrays.asList(8, 5, 7, 10, 2);

        System.out.println("Brute force: " + findMinimumDrops(starts, ends));
        System.out.println("Optimised: " + findMinimumDropsOptimised(starts, ends));
    }
}
