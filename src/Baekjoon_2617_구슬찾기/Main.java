/*
 Author : Ruel
 Problem : Baekjoon 2617번 구슬 찾기
 Problem address : https://www.acmicpc.net/problem/2617
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2617_구슬찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 구슬과 m개의 구슬 간 경중 관계가 주어진다.
        // 무게가 전체의 중간((n + 1) / 2번째)인 구슬이 될 수 없는 구슬의 수는?
        //
        // 플로이드 워셜 문제
        // m개의 관계를 통해
        // 한 구슬과 다른 구슬 간의 경중 관계를 유추할 수 있다.
        // 따라서 자신보다 가벼운 구슬의 수, 무거운 구슬의 수를 각각 구한다.
        // 그 개수가 (n + 1)/2개 이하라면 무게 관계를 알 수 없는 구슬들에 의해 해당 구슬이 중간값이 될수도 있지만
        // (n+1)/2개보다 같거나 많다면 무조건 중간값이 될 수 없다.
        // 해당 개수의 수를 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 구슬, m개의 경중 관계
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // adjMatrix[a][b][0] = a보다 b 구슬이 무거운지
        // adjMatrix[a][b][1] = a보다 b 구슬이 가벼운지
        boolean[][][] adjMatrix = new boolean[n][n][2];
        int[][] counts = new int[n][2];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;

            // 중복되는 값이 아닐 경우에만 입력
            if (!adjMatrix[x][y][0]) {
                adjMatrix[x][y][0] = true;
                counts[y][0]++;
                adjMatrix[y][x][1] = true;
                counts[x][1]++;
            }
        }

        // 플로이드 워셜을 자신보다 가벼운 경우에, 무거운 경우에
        // 총 두가지에 대해 계산
        for (int i = 0; i < 2; i++) {
            for (int via = 0; via < n; via++) {
                for (int start = 0; start < n; start++) {
                    if (start == via || !adjMatrix[start][via][i])
                        continue;

                    for (int end = 0; end < n; end++) {
                        if (end == start || end == via || !adjMatrix[via][end][i])
                            continue;

                        if (!adjMatrix[start][end][i]) {
                            adjMatrix[start][end][i] = true;
                            counts[end][i]++;
                        }
                    }
                }
            }
        }

        int cnt = 0;
        int half = (n + 1) / 2;
        // 자신보다 가볍거나 무거운 구슬의 수가 half개를 넘는 구슬의 수를 센다.
        for (int i = 0; i < n; i++) {
            if (Math.max(counts[i][0], counts[i][1]) >= half)
                cnt++;
        }
        // 답 출력
        System.out.println(cnt);
    }
}