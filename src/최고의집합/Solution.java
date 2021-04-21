package 최고의집합;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        // n개의 자연수 합이 s인 집합 중 곱이 가장 큰 집합을 구하여라
        // 합이 같지만 곱이 크다 -> 가장 균등한 자연수들의 집합을 구하여라
        // n개의 자연수에게 각각 s / n보다 작지만 가장 큰 자연수 값을 할당해주고,
        // 남은 나머지들을 하나씩 나눠준다.
        int n = 2;
        int s = 8;

        int surplus = s % n;
        int numOfSmall = n - surplus;

        int[] answer;
        if (s / n < 1)
            answer = new int[]{-1};
        else {
            answer = new int[n];
            for (int i = 0; i < n; i++) {
                if (i < numOfSmall)
                    answer[i] = s / n;
                else
                    answer[i] = s / n + 1;
            }
        }
        System.out.println(Arrays.toString(answer));
    }
}