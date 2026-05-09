/*
 Author : Ruel
 Problem : Jungol 1748번 주식거래자
 Problem address : https://jungol.co.kr/problem/1748
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1748_주식거래자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] adjMatrix;

    public static void main(String[] args) throws IOException {
        // 여러 개의 테스트케이스가 존재한다. 0명인 케이스일 경우 종료한다.
        // n명의 주식거래자가 존재한다.
        // 주식 거래자들은 서로 정보를 공유한다.
        // n개의 줄이 주어지며, 각 줄마다 첫번째 수는 공유하는 주식거래자의 수, 그리고 n개의 쌍이 이어지며 각 공유하는 대상과 전파 시간이 주어진다.
        // 모든 사람에게 정보를 가장 빠르게 전파시키고자할 때
        // 첫 정보를 주어야하는 사람과, 전체 전파가 끝나는 시간을 구하라
        //
        // 플로이드 워셜 문제
        // 플로이드 워셜로 각 개인이 다른 모든 사람에게 전파는 시간들 중 최대 시간을 찾고
        // 그 최대 시간이 최소인 사람을 찾는다.
        // 해당 사람의 번호와 전파 완료 시간을 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 인접 행렬
        adjMatrix = new int[101][101];
        // n 주식 거래자의 수
        int num = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        // 주식 거래자가 1명 이상인 경우에만
        while (num > 0) {
            // 배열 초기화
            for (int i = 1; i <= num; i++)
                Arrays.fill(adjMatrix[i], 1, num + 1, Integer.MAX_VALUE);

            // 입력 처리
            for (int i = 1; i <= num; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                for (int j = 0; j < a; j++) {
                    int b = Integer.parseInt(st.nextToken());
                    int c = Integer.parseInt(st.nextToken());
                    adjMatrix[i][b] = c;
                }
            }

            // 플로이드 워셜
            for (int via = 1; via <= num; via++) {
                for (int start = 1; start <= num; start++) {
                    if (start == via || adjMatrix[start][via] == Integer.MAX_VALUE)
                        continue;

                    for (int end = 1; end <= num; end++) {
                        if (end == start || end == via || adjMatrix[via][end] == Integer.MAX_VALUE)
                            continue;

                        adjMatrix[start][end] = Math.min(adjMatrix[start][end], adjMatrix[start][via] + adjMatrix[via][end]);
                    }
                }
            }

            // 모든 사람들에게 전파하는 시간이 최소인 사람과 전파 시간을 찾는다.
            int start = -1;
            int min = Integer.MAX_VALUE;
            for (int i = 1; i <= num; i++) {
                int maxTime = 0;
                for (int j = 1; j <= num; j++) {
                    if (i == j)
                        continue;
                    maxTime = Math.max(maxTime, adjMatrix[i][j]);
                }

                if (maxTime < min) {
                    start = i;
                    min = maxTime;
                }
            }
            // 불가능할 경우 disjoint, 그 외의 경우 해당 사람과 시간을 출력한다.
            sb.append(min == Integer.MAX_VALUE ? "disjoint" : (start + " " + min)).append("\n");
            // 다음 n의 입력
            num = Integer.parseInt(br.readLine());
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}