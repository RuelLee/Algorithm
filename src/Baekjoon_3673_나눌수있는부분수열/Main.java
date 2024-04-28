/*
 Author : Ruel
 Problem : Baekjoon 3673번 나눌 수 있는 부분 수열
 Problem address : https://www.acmicpc.net/problem/3673
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3673_나눌수있는부분수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // c개의 테스트케이스가 주어진다.
        // 크기가 n인 수열이 주어질 때
        // 연속하는 부분 수열의 합이 d로 나누어 떨어지는 것의 개수를 구하라
        // 
        // 누적합 문제
        // 주어지는 수열의 값을 누적하며
        // 모듈러 값이 d인 것의 개수를 세어나간다.
        // 구해진 d 중 2개를 짝짓는다면 해당 구간의 합이 d로 나누었을 때
        // 나누어떨어지는 부분수열에 해당하게 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // c개의 테스트케이스
        int c = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < c; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 나누는 값 d
            int d = Integer.parseInt(st.nextToken());
            // 수열의 크기 n
            int n = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            // 나머지값들을 세어나간다.
            int[] mod = new int[d];
            // 0에 대해서는 초기값을 1로 주어
            // 0 ~ i까지의 합도 계산되게끔한다.
            mod[0] = 1;
            // 누적합
            int sum = 0;
            // 부분수열의 개수
            int answer = 0;
            for (int i = 0; i < n; i++) {
                int num = Integer.parseInt(st.nextToken());
                // 누적합을 하고 d로 나눈 나머지 값을 구한다.
                sum = (sum + num) % d;
                // 이전에 등장한 나머지가 sum인 개수는 mod[sum]으로 계산되어있다.
                // 이전에 등장한 나머지가 sum인 것과 현재 i를 한 구간으로 보면
                // 해당 구간에서의 합이 d로 나눴을 때 나누어떨어진다.
                // 따라서 answer에 mod[sum]값 만큼 증가.
                answer += mod[sum];
                // 그리고 현재 i까지의 누적합도 sum이므로
                // mod[sum] 증가
                mod[sum]++;
            }
            // 답안 기록
            sb.append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}