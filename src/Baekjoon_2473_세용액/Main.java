/*
 Author : Ruel
 Problem : Baekjoon 2473번 세 용액
 Problem address : https://www.acmicpc.net/problem/2473
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2473_세용액;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 용액이 주어진다.
        // 세 개의 용액을 골라, 합이 0에 최대한 가깝게 만들고자 한다.
        // 이 때의 세 용액을 골라라
        //
        // 두 포인터 문제
        // 먼저, 용액을 값에 따라 정렬한다.
        // 그 후, 첫번째 용액은 순서대로 골라가며
        // 두번째 용액과 세번째 용액은 첫번째 용액보다 큰 범위 내에서
        // 두 포인터를 활용하여 선택한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 용액
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        long[] solutions = new long[n];
        for (int i = 0; i < solutions.length; i++)
            solutions[i] = Long.parseLong(st.nextToken());
        // 정렬
        Arrays.sort(solutions);
        
        // 답을 기록해둘 배열
        long[] answer = new long[4];
        // 값 초기화
        Arrays.fill(answer, Long.MAX_VALUE);
        // 순서대로 첫번째 용액을 고른다.
        for (int i = 0; i < solutions.length; i++) {
            // 두번째 용액 j와 세번째 용액 k
            int k = solutions.length - 1;
            // 두번째 용액은 차례대로 값을 키워나가며
            for (int j = i + 1; j < k; j++) {
                // 세 용액을 섞어, 값이 0보다 크다면
                // 세번째 용액의 수를 줄이는 방버을 통해 합을 줄여나간다.
                while (solutions[i] + solutions[j] + solutions[k] > 0 &&
                        k > j + 1) {
                    // 현재의 용액의 합과 0과의 차이
                    // 를 비교하여 최솟값을 갱신하는지 확인
                    long diff = Math.abs(solutions[i] + solutions[j] + solutions[k]);
                    if (answer[0] > diff) {
                        answer[0] = diff;
                        answer[1] = i;
                        answer[2] = j;
                        answer[3] = k;
                    }
                    // k값 감소
                    k--;
                }
                // 현재의 값을 비교
                if (answer[0] > Math.abs(solutions[i] + solutions[j] + solutions[k])) {
                    answer[0] = Math.abs(solutions[i] + solutions[j] + solutions[k]);
                    answer[1] = i;
                    answer[2] = j;
                    answer[3] = k;
                }
            }
        }
        // 찾은 값 기록
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < answer.length; i++)
            sb.append(solutions[(int) answer[i]]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}