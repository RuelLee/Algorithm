/*
 Author : Ruel
 Problem : Baekjoon 12875번 칙령
 Problem address : https://www.acmicpc.net/problem/12875
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12875_칙령;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람들과 그들의 친구관계가 주어진다.
        // 친구 사이에는 갖고 있는 돈이 최대 d원까지만 차이날 수 있다.
        // 돈을 가장 많이 가진 사람과 가장 많이 가진 사람의 차이를 크도록 돈을 분배하고자 할 때
        // 그 때의 차이값은?
        //
        // 플로이드 워셜 문제
        // 몇 번의 친구관계를 거치느냐에 차이값이 d배만큼씩 커진다.
        // 따라서 플로이드 워셜을 통해 가장 먼 친구 관계를 거치는 경우를 구한다.
        // 친구 관계를 거치더라도 도달할 수 없는 사람이 존재한다면
        // 두 사람의 돈은 무한대로 차이가 날 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 친구, 친구 관계의 소지금 차이 제한 d
        int n = Integer.parseInt(br.readLine());
        int d = Integer.parseInt(br.readLine());
        
        // 인접 행렬
        char[][] adjMatrix = new char[n][];
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = br.readLine().toCharArray();

        int[][] distances = new int[n][n];
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[i].length; j++) {
                // 친구 관계라면 1
                if (adjMatrix[i][j] == 'Y')
                    distances[i][j] = 1;
                else        // 그 외엔 큰 값
                    distances[i][j] = Integer.MAX_VALUE;
            }
        }
        
        // 플로이드 워셜
        for (int via = 0; via < distances.length; via++) {
            for (int start = 0; start < distances.length; start++) {
                if (start == via || distances[start][via] == Integer.MAX_VALUE)
                    continue;
                for (int end = 0; end < distances.length; end++) {
                    if (end == start || end == via || distances[via][end] == Integer.MAX_VALUE)
                        continue;

                    distances[start][end] = distances[end][start] =
                            Math.min(distances[start][end], distances[start][via] + distances[via][end]);
                }
            }
        }

        int maxDistance = 0;
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                // 자기 자신인 경우를 제외하고
                if (i == j)
                    continue;

                // 가장 큰 친구 관계 거리값을 계산한다.
                maxDistance = Math.max(maxDistance, distances[i][j]);
            }
        }
        // 만약 Integer.MAX_VALUE 값이 존재한다면
        // 친구 관계를 거치더라도 도달할 수 없는 관계인 두 사람이 존재
        // 위 경우 -1을 출력
        // 그 외의 경우는 maxDistance * d 값을 출력한다.
        System.out.println(maxDistance == Integer.MAX_VALUE ? -1 : maxDistance * d);
    }
}