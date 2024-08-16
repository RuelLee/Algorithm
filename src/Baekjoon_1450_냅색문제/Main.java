/*
 Author : Ruel
 Problem : Baekjoon 1450번 냅색문제
 Problem address : https://www.acmicpc.net/problem/1450
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1450_냅색문제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 물건을 갖고 있으며, 최대 c만큼의 무게를 담을 수 있는 가방이 주어진다.
        // 가방에 물건을 담을 수 있는 방법의 가짓수는?
        //
        // 이분 탐색 문제
        // n이 최대 30까지 주어지므로 일일이 모든 경우를 계산해서는
        // 2^30 가지가 나온다.
        // 따라서 두 개의 그룹으로 나누고 한 그룹에서 나올 수 있는 무게
        // 또 다른 한 그룹에서 나올 수 있는 무게로 나누어
        // 두 그룹의 무게 합이 k이하인 경우의 수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 물건, 가방에 담을 수 있는 무게 c
        int n = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // 물건들
        int[] things = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 두 개의 그룹으로 나누어 가능한 무게를 계산한다.
        long[] part1 = new long[(int) Math.pow(2, things.length / 2)];
        long[] part2 = new long[(int) Math.pow(2, n - (things.length / 2))];

        // 첫번째 그룹에서 가능한 무게들
        int preSize = 1;
        int idx = 1;
        for (int i = 0; i < things.length / 2; i++) {
            for (int j = 0; j < preSize; j++)
                part1[idx++] = part1[j] + things[i];
            preSize = idx;
        }
        
        // 두번째 그룹에서 가능한 무게들
        preSize = 1;
        idx = 1;
        for (int i = things.length / 2; i < things.length; i++) {
            for (int j = 0; j < preSize; j++)
                part2[idx++] = part2[j] + things[i];
            preSize = idx;
        }
        // 두번째 그룹에선 이분 탐색을 할 것이기 때문에 정렬
        Arrays.sort(part2);

        long sum = 0;
        // 첫번째 그룹에서 가능한 무게 하나와
        for (long groupOne : part1) {
            // 두번째 그룹에서 가능한 최대 무게의 idx를
            // 이분 탐색을 통해 찾는다.
            int start = 0;
            int end = part2.length - 1;

            while (start <= end) {
                int mid = (start + end) / 2;
                if (part2[mid] > c - groupOne)
                    end = mid - 1;
                else
                    start = mid + 1;
            }
            // groupOne과
            // 두번째 그룹에서 0 ~ end까지의 모든 무게들에 대해 가방에 담을 수 있다.
            // 해당 경우의 수를 누적시켜나간다.
            sum += end + 1;
        }
        // 답 출력
        System.out.println(sum);
    }
}