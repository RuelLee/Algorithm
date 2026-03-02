/*
 Author : Ruel
 Problem : Baekjoon 22967번 구름다리
 Problem address : https://www.acmicpc.net/problem/22967
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22967_구름다리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n의 건물이 n-1개의 구름다리로 모두 직간접적으로 연결되어있다.
        // 추가로 최대 n-1개의 구름 다리를 건설하여 지름을 최소화하고 싶다.
        // 지름은 가장 먼 두 건물 사이의 거리이다.
        // 추가로 건설해야하는 다리의 수, 지름의 길이, 건설하는 다리들을 출력하라
        //
        // 애드혹 문제
        // 모든 건물들을 서로 직접적으로 연결할 수 있다면(= 완전 그래프)
        // 지름은 1이 된다. 이 때 필요한 다리의 수는 n(n-1)/2 이고, 처음에 n-1개가 주어지고, 최대 n-1개를 추가로 건설할 수 있다.
        // 따라서 n(n-1) / 2 = 2 * (n - 1)
        // n = 4
        // n이 4이하인 경우에는 모든 건물들을 서로 직접적으로 연결할 수 있음며 이 때 지름은 1이다.
        // 5이상인 경우는 해당 경우가 불가능하며
        // 모든 건물을 1번 건물에 연결하여, 1번을 경유하여 다른 건물로 가게끔하면 된다.
        // 물론 직접적으로 연결되는 건물들도 있을 것이나 지름은 2가 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 건물
        int n = Integer.parseInt(br.readLine());

        // 다리 입력
        boolean[][] roads = new boolean[n + 1][n + 1];
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            roads[x][y] = roads[y][x] = true;
        }

        // 추가로 건설하는 다리의 수
        int count = 0;
        StringBuilder sb = new StringBuilder();
        // n이 4이하인 경우
        // 모든 건물을 직접적으로 연결
        if (n <= 4) {
            // 지름은 1
            sb.append(1).append("\n");
            for (int i = 1; i <= n; i++) {
                for (int j = i + 1; j <= n; j++) {
                    // i와 j 건물 사이에 다리가 없다면 건설
                    if (!roads[i][j]) {
                        count++;
                        roads[i][j] = roads[j][i] = true;
                        sb.append(i).append(" ").append(j).append("\n");
                    }
                }
            }
        } else {
            // 모든 건물을 직접적으로 연결하는 것이 불가능한 경우
            // 지름은 2
            sb.append(2).append("\n");
            // 모든 건물을 1번 건물과 연결한다.
            for (int i = 2; i <= n; i++) {
                if (!roads[1][i]) {
                    count++;
                    roads[1][i] = roads[i][1] = true;
                    sb.append(1).append(" ").append(i).append("\n");
                }
            }
        }
        // 추가로 건설한 다리의 수와
        System.out.println(count);
        // 지름, 그리고 다리들을 출력
        System.out.print(sb);
    }
}