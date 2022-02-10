/*
 Author : Ruel
 Problem : Baekjoon 1956번 운동
 Problem address : https://www.acmicpc.net/problem/1956
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1956_운동;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] adjMatrix;
    static final int MAX = 400 * 10000 + 1;     // 최대 400개의 지점, 거리는 최대 1만이므로, 최대값을 4백만 + 1로 정해주자.

    public static void main(String[] args) throws IOException {
        // 시작점 -> 다른 곳을 경유 -> 다시 시작점으로 돌아오는 사이클이 최소가 되는 사이클을 찾고자 하는 문제
        // 플로이드-와샬 문제
        // 일반적으로 플로이드-와샬에서 시작점으로 다시 되돌아오는 경우는 없지만, 이번에는 있다는 걸 유의하자
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        adjMatrix = new int[v + 1][v + 1];
        for (int[] am : adjMatrix)
            Arrays.fill(am, MAX);
        // 도로는 일방통행이며, 같은 두 지점을 잇는 도로는 주어지지 않는다 했으므로, 바로 입력.
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            adjMatrix[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = Integer.parseInt(st.nextToken());
        }

        for (int via = 1; via < adjMatrix.length; via++) {
            for (int start = 1; start < adjMatrix.length; start++) {
                if (via == start)
                    continue;
                for (int end = 1; end < adjMatrix.length; end++) {
                    if (end == via)     // start == end 인 경우는 건너뛰지 않는다.
                        continue;
                    else if (adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }

        int minCycleLength = MAX;
        for (int i = 1; i < adjMatrix.length; i++)      // i -> i로 돌아오는 최소 거리를 찾자.
            minCycleLength = Math.min(minCycleLength, adjMatrix[i][i]);
        System.out.println(minCycleLength == MAX ? -1 : minCycleLength);
    }
}