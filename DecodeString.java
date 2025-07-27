/*
Given a string s containing only digits, return the number of ways to decode it, where:
'1' → 'A'
'2' → 'B'
...
'26' → 'Z'

Approach: Dynamic Programming
Let dp[i] represent the number of ways to decode the string up to index i.
Steps:
Base Case:
dp[0] = 1 (Empty string has one way)
dp[1] = 1 if s[0] != '0', else 0 (Cannot decode string starting with '0')

For each i from 2 to n:
If s[i-1] != '0', then dp[i] += dp[i-1]
If the substring s[i-2..i-1] forms a valid 2-digit number between 10 and 26, then dp[i] += dp[i-2]

 */

public class DecodeString {

        public static int numDecodings(String s) {
            if (s == null || s.length() == 0) return 0;

            int n = s.length();
            int[] dp = new int[n + 1];

            dp[0] = 1; // empty string
            dp[1] = 1; // first character (already checked it’s not '0')

            for (int i = 2; i <= n; i++) {
                int oneDigit = Integer.parseInt(s.substring(i - 1, i));
                int twoDigit = Integer.parseInt(s.substring(i - 2, i));

                if (oneDigit >= 1 && oneDigit <= 9) {
                    dp[i] += dp[i - 1];
                }
                if (twoDigit >= 10 && twoDigit <= 26) {
                    dp[i] += dp[i - 2];
                }
            }

            return dp[n];
        }

        public static void main(String[] args) {
            System.out.println(numDecodings("12"));   // Output: 2
            System.out.println(numDecodings("226"));  // Output: 3
            System.out.println(numDecodings("2112"));    // Output: 5
            System.out.println(numDecodings("2101"));   // Output: 1
        }

}

/*
    Time: O(n)
    Space: O(n) (can be optimized to O(1) using two variables instead of array)

    https://leetcode.com/problems/decode-ways/description/
    https://leetcode.com/problems/decode-ways-ii/
 */