/*
 Author : Ruel
 Problem : Baekjoon 27651번 벌레컷
 Problem address : https://www.acmicpc.net/problem/27651
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27651_벌레컷;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n 크기의 배열이 주어진다.
        // 세 부분으로 나누어, 머리, 가슴 배로 지칭한다.
        // 가슴의 합은 배보다 크며, 배의 합은 머리보다 크다.
        // 가능한 경우의 수를 계산하라
        //
        // 두 포인터, 누적합, 이분탐색 문제
        // 여러 알고리즘이 녹아있는 문제
        // 먼저 두 포인터를 통해
        // 머리와 가능한 배의 최소 위치를 찾는다.
        // 그 후, 가능한 배의 최대 위치를 이분 탐색을 통해 찾아 경우의 수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n 크기의 배열
        int n = Integer.parseInt(br.readLine());
        int[] array = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 누적합
        long[] psums = new long[n];
        psums[0] = array[0];
        for (int i = 1; i < psums.length; i++)
            psums[i] = psums[i - 1] + array[i];

        // 경우의 수
        int count = 0;
        int thorax = n - 2;
        // 두 포인터로 머리와 배의 최소 위치를 찾는다.
        for (int head = 0; head < thorax; head++) {
            // 배는 머리보다 커야한다.
            while (head < thorax && psums[head] >= psums[n - 1] - psums[thorax])
                thorax--;
            // 만약 두 위치가 겹치거나
            // 가슴의 크기가 배의 크기보다 같거나 작아졌다면
            // 반복문 종료
            if (head >= thorax || psums[thorax] - psums[head] <= psums[n - 1] - psums[thorax])
                break;

            // 이분 탐색을 통해 가슴의 최소 크기 == 배의 최대 크기 위치를 찾는다.
            int start = head + 1;
            int end = thorax;
            while (start < end) {
                int mid = (start + end) / 2;
                if (psums[mid] - psums[head] > psums[n - 1] - psums[mid])
                    end = mid;
                else
                    start = mid + 1;
            }
            // 가슴의 최소 크기 위치(start) ~ thorax까지의 범위에 대해 모두 가능.
            // 해당 경우의 수 추가.
            count += thorax - start + 1;
        }
        // 전체 경우의 수 출력
        System.out.println(count);
    }
}