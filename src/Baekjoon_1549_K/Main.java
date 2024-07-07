/*
 Author : Ruel
 Problem : Baekjoon 1549 K
 Problem address : https://www.acmicpc.net/problem/1549
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1549_K;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열 a가 주어지고
        // s(i, k) = a[i] + ... + a[i+k-1]이라 한다.
        // i+k <= j일 때 s(i, k)와 s(j, k)의 차이를 최소로 하는 프로그램을 작성하라
        // 그 때의 k와 두 부분수열 합의 차이를 출력하라
        //
        // 누적합, 브루트포스 문제
        // 결국엔 구간합을 구해 차이를 구하는 것이므로 누적합을 구한다
        // 가능한 모든 경우에 대해 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 수열
        int n = Integer.parseInt(br.readLine());
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 누적합
        long[] psums = new long[n + 1];
        for (int i = 1; i < psums.length; i++)
            psums[i] = psums[i - 1] + nums[i - 1];

        // 최소 차이 diff
        long diff = Long.MAX_VALUE;
        // 그 때의 길이
        int answer = 0;
        // 첫 부분 수열의 시작
        for (int first = 1; first < psums.length; first++) {
            // 수열의 크기
            for (int size = 1; first + 2 * size - 1 < psums.length; size++) {
                // 두번째 부분 수열의 시작
                for (int second = first + size; second + size - 1 < psums.length; second++) {
                    // 두 부분 수열 합의 차이
                    long currentDiff = Math.abs((psums[first + size - 1] - psums[first - 1] - (psums[second + size - 1] - psums[second - 1])));
                    // 차이 값 갱신시
                    if (currentDiff < diff) {
                        diff = currentDiff;
                        answer = size;
                    } else if (currentDiff == diff)     // 차이가 같은 값일 경우
                        answer = Math.max(answer, size);
                }
            }
        }
        // 답안 출력
        System.out.println(answer);
        System.out.println(diff);
    }
}