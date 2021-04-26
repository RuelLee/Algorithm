package 폰켓몬;

import java.util.HashSet;

public class Solution {
    public static void main(String[] args) {
        int[] nums = {3, 3, 3, 2, 2, 4};

        HashSet<Integer> hashSet = new HashSet<>();
        for (int i : nums)
            hashSet.add(i);

        System.out.println(Math.min(nums.length / 2, hashSet.size()));
    }
}