/*
 Author : Ruel
 Problem : Baekjoon 6156번 Cow Contest
 Problem address : https://www.acmicpc.net/problem/6156
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6156_CowContest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 소는 각각의 고유한 코딩력을 지니고 있다.
        // 두 소가 대결을 하는 경우, 더 강한 코딩력을 가지고 있는 소가 이긴다.
        // m개의 대결 결과가 주어질 때
        // 정확히 자신의 순위를 알 수 있는 소의 마릿수는?
        //
        // 플로이드 워셜 문제
        // 대결 결과를 통해 자신이 명확히
        // 이길 수 있는 소와 지는 소의 합이 n-1인 소의 개수를 구하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 소, m개의 결과
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        boolean[][] adjMatrix = new boolean[n + 1][n + 1];
        // a소가 b소를 이긴다.
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adjMatrix[a][b] = true;
        }

        // 플로이드 워셜
        // start > via, via > end인 경우
        // start 도 end를 이길 수 있다.
        for (int via = 1; via <= n; via++) {
            for (int start = 1; start <= n; start++) {
                if (start == via || !adjMatrix[start][via])
                    continue;

                for (int end = 1; end <= n; end++) {
                    if (end == start || end == via || !adjMatrix[via][end])
                        continue;

                    adjMatrix[start][end] = true;
                }
            }
        }


        int answer = 0;
        // 모든 소의 대결을 탐색
        for (int i = 1; i <= n; i++) {
            int cnt = 0;
            for (int j = 1; j <= n; j++) {
                if (i == j)
                    continue;

                // i번 소가 j번 소를 이기거나 지는 것이 명확하다면
                // cnt 증가
                if (adjMatrix[i][j] || adjMatrix[j][i])
                    cnt++;
            }
            // 결과가 명확한 상대방이 n-1 마리라면
            // i번 소의 순위는 명확하다.
            if (cnt == n - 1)
                answer++;
        }
        // 답 출력
        System.out.println(answer);
    }
}