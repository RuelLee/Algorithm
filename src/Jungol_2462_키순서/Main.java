/*
 Author : Ruel
 Problem : Jungol 2462번 키 순서
 Problem address : https://jungol.co.kr/problem/2462
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2462_키순서;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생과 두 학생의 키 관계가 m개 주어진다.
        // 자신이 정확히 몇 번째 키인지 알 수 있는 학생의 수는?
        //
        // 플로이드 워셜 문제
        // 자신이 정확히 몇번째인 줄 안다 -> 직간접적으로 모든 학생들과 키 관계 비교가 가능하다.
        // 자신과 키 비교가 가능한 학생의 수를 모두 세, n-1인 경우, 해당 학생의 키 순서를 정확히 알 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // N명의 학생, M개의 관계
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 인접행렬로 관계를 정리
        boolean[][] adjMatrix = new boolean[N + 1][N + 1];
        // 직간접적으로 연결된 학생의 수
        int[] connected = new int[N + 1];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            // a가 b보다 작다.
            adjMatrix[a][b] = true;
            // 직간접관계 각각 증가
            connected[a]++;
            connected[b]++;
        }

        // 플로이드 워셜
        for (int via = 1; via <= N; via++) {
            for (int start = 1; start <= N; start++) {
                if (start == via || !adjMatrix[start][via])
                    continue;

                for (int end = 1; end <= N; end++) {
                    if (end == via || end == start || !adjMatrix[via][end])
                        continue;

                    // 처음 관계를 밝힌 경우
                    if (!adjMatrix[start][end]) {
                        // 인접 행렬에 체크 후
                        adjMatrix[start][end] = true;
                        // 각각 직간접관계 증가
                        connected[start]++;
                        connected[end]++;
                    }
                }
            }
        }

        int ans = 0;
        // connected가 N-1인 학생의 수 계산
        for (int i = 1; i <= N; i++) {
            if (connected[i] == N - 1)
                ans++;
        }
        // 답 출력
        System.out.println(ans);
    }
}