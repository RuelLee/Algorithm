/*
 Author : Ruel
 Problem : Baekjoon 13422번 도둑
 Problem address : https://www.acmicpc.net/problem/13422
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13422_도둑;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 원형 배치된 n개의 집과 각 집에 보관된 돈이 주어진다.
        // 도둑은 m개의 연속된 집의 돈을 모두 털되, 총합이 k원 미만으로 하고자한다.
        // 훔칠 수 있는 방법의 수는?
        //
        // 슬라이딩 윈도우 문제
        // 1 ~ m번 까지의 집까지의 돈의 합을 구하고, 한칸씩 옆으로 밀어가며 가능한 방법의 수를 센다.
        // 단 n과 m이 같을 경우, 1 ~ m을 세든, 2 ~ m ~ 1, 3 ~ m ~ 2를 세든 모두 같은 경우임을 유의하자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // 테스트케이스별 입력 처리.
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int[] homes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            
            // 연속한 m개의 집의 돈의 합
            int sum = 0;
            // 경우의 수
            int count = 0;
            // 0 ~ m - 1번까지 돈의 합.
            for (int i = 0; i < m; i++)
                sum += homes[i];

            // 만약 n == m인 경우, 경우의 수는 한가지.
            if (n == m) {
                // 총합이 k 미만인지 확인.
                if (sum < k)
                    count = 1;
            } else {
                // n != m 인 경우
                // 마지막 집과 첫 집이 맞닿아 있음을 유의하며 슬라이딩 윈도우를 사용하여
                // 경우의 수를 센다.
                for (int i = m; i < n + m; i++) {
                    // 마지막 집의 돈 추가
                    sum += homes[i % n];
                    // 첫 집의 돈 제거
                    sum -= homes[(i + (n - m)) % n];
                    // 합 비교
                    if (sum < k)
                        count++;
                }
            }
            // 이번 테스트케이스 경우의 수 출력
            sb.append(count).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}