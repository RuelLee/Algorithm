/*
 Author : Ruel
 Problem : Jungol 2303번 세 용액
 Problem address : https://jungol.co.kr/problem/2303
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2303_세용액;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 용액이 -10억 ~ 10억까지의 값으로 주어진다.
        // 세 용액의 합이 최대한 0에 가깝게 만들고자할 때, 그 세 용액은?
        //
        // 정렬, 두 포인터 문제
        // n이 최대 5000으로 주어지므로, 브루트포스로 하는 건 500C3으로 불가능하다.
        // 용액을 정렬하고, 두 개의 용액을 브루트 포스로 구한다.
        // 정렬 기준에 따라 두 용액의 합이 증가하거나 감소하는 방향으로 탐색을 하는데,
        // 이를 이용하여 다른 포인터를 끝에 배치한 후, 두번째 용액에 다가오지만 겹치지 않을 정도까지 움직이며 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 용액
        int n = Integer.parseInt(br.readLine());
        long[] solutions = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            solutions[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(solutions);

        long[] answer = new long[4];
        answer[0] = Long.MAX_VALUE;
        // i, j로 두 용액을 브루트 포스로 고름
        for (int i = 0; i < n; i++) {
            // 오름차순으로 정렬되어있으므로, i, j번째는 순차적으로
            // k는 끝에서부터 줄여나가며 탐색
            int k = n - 1;
            for (int j = i + 1; j < n - 1; j++) {
                // k는 j보다는 커야한다.
                k = Math.max(k, j + 1);
                // i, j, k번째 용액의 합보다 i, j, k-1번째 용액의 합이 더 0에 가까운 경우
                // k를 하나 감소
                while (k - 1 > j && Math.abs(solutions[i] + solutions[j] + solutions[k]) > Math.abs(solutions[i] + solutions[j] + solutions[k - 1]))
                    k--;

                // i, j와 다른 한 용액을 섞은 값 중 0에 최대한 가까운 값
                // 역대 기록보다 더 가깝다면 기록
                long sum = solutions[i] + solutions[j] + solutions[k];
                if (Math.abs(answer[0]) > Math.abs(sum)) {
                    answer[0] = sum;
                    answer[1] = solutions[i];
                    answer[2] = solutions[j];
                    answer[3] = solutions[k];
                }
            }
        }
        // 답 출력
        System.out.println(answer[1] + " " + answer[2] + " " + answer[3]);
    }
}