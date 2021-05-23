package 최댓값과최솟값;

public class Solution {
    public static void main(String[] args) {
        String s = "1 2 3 4";
        String[] nums = s.split(" ");

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (String num : nums) {
            int n = Integer.parseInt(num);
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        String answer = min + " " + max;
        System.out.println(answer);
    }
}