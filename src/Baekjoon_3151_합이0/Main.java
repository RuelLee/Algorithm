/*
 Author : Ruel
 Problem : Baekjoon 3151번 합이 0
 Problem address : https://www.acmicpc.net/problem/3151
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3151_합이0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 합이 0이 되는 세 수의 종류는?
        //
        // 두 포인터 혹은 이분 탐색으로도 풀 수 있지만
        // 각 수의 개수를 센다면 이중 포문으로도 풀 수 있다.
        // 각 수의 개수를 세고, 두 수를 선택한다면 나머지 하나의 수는 자연히 정해진다.
        // 수의 개수를 통해 경우의 수와 조합을 통해 가능한 경우의 수를 세면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 수의 개수 n
        int n = Integer.parseInt(br.readLine());
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // -10000 ~ 10000까지 수가 주어지므로
        // +10000을 하여 값을 보정해서 각 수의 개수를 센다.
        int[] numsCounts = new int[20001];
        for (int num : nums)
            numsCounts[num + 10000]++;

        // 경우의 수가 int 범위를 벗어날 수 있다.
        long count = 0;
        for (int i = 0; i < numsCounts.length - 2; i++) {
            // i의 개수가 0개 일 때는 살펴볼 필요없이 건너뛴다.
            if (numsCounts[i] == 0)
                continue;

            // 다른 한 수는 numsCounts의 범위를 벗어나지 않고
            // 계산될 end보다 작은 수여야한다.
            for (int start = i; start < numsCounts.length && 2 * start <= 30000 - i; start++) {
                // 만약 start의 개수가 0일 때는 마찬가지로 건너뛴다.
                if (numsCounts[start] == 0)
                    continue;

                // 만족하는 end는 30000에서 i와 start를 뺀 값
                int end = 30000 - i - start;
                // 만약 end가 20000을 넘어가거나, 개수가 0이라면 건너뛴다.
                if (end >= numsCounts.length || numsCounts[end] == 0)
                    continue;

                // 만약 세 수가 모두 같다면
                // 조합을 통해 경우의 수를 센다.
                if (i == start && start == end)
                    count += (long) numsCounts[i] * (numsCounts[i] - 1) * (numsCounts[i] - 2) / 3 / 2;
                    // i와 start가 같은 경우
                else if (i == start)
                    count += (long) numsCounts[i] * (numsCounts[i] - 1) / 2 * numsCounts[end];
                    // start와 end가 같은 경우
                else if (start == end)
                    count += (long) numsCounts[i] * numsCounts[start] * (numsCounts[start] - 1) / 2;
                    // 세 수가 모두 다른 경우
                else
                    count += (long) numsCounts[i] * numsCounts[start] * numsCounts[end];
            }
        }
        // 계산한 경우의 수를 출력한다.
        System.out.println(count);
    }
}