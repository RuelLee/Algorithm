package 입국심사;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        // 문제에서 주어지듯, 사람의 수와 심사시간이 매우 크므로, DP나 시뮬레이션 X
        // 최대의 시간을 잡고 이진탐색으로 범위를 줄여나가자.
        int n = 10;
        int[] times = {7, 10, 11, 13};

        Arrays.sort(times);

        long maxTime = ((n / times.length) + 1) * (long) times[times.length - 1];   // 심사가 가장 느린 사람이 times.length만큼 있을 때 걸리는 시간을 최대시간으로 잡았다.
        // ↑ 연산에 쓰이는 값들이 모두 Integer 값들이므로 오버플로우가 나타날 수 있다. 값 중 하나에 long 값으로 명시적 형변환을 해주자.

        long left = 0;
        long mid;
        long right = maxTime;

        long processed;

        while (left != right) {     // 탐색이 끝나는 조건은 left 와 right 가 만나는 시점.
            processed = 0;

            mid = (left + right) / 2;

            for (int i : times)
                processed += mid / i;   // 시간이 mid 만큼 주어졌을 때, 처리된 사람의 수

            if (processed >= n)     // 심사 완료된 사람의 수가 n과 동일하거나 더 많다면 좀 더 범위를 줄여나가며 최소인 시간을 찾아야한다. 다음 연산의 right 값은 mid 값으로 하자.
                right = mid;
            else        // 심사 완료된 사람의 수가 부족하다면, 다음 left 값은 mid 값보다 1 큰 값으로 시작하자.
                left = mid + 1;
        }
        System.out.println(left);
    }
}