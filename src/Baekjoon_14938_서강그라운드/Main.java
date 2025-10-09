/*
 Author : Ruel
 Problem : Baekjoon 14938번 서강그라운드
 Problem address : https://www.acmicpc.net/problem/14938
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14938_서강그라운드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 지점과 해당 지점에 놓인 아이템의 개수,
        // 그리고 플레이어가 시작 지점에서 움직일 수 있는 거리 m, 각 지점들 간의 r개의 길이 주어진다
        // 최대한 많은 아이템을 얻는다고 할 때, 그 개수는?
        // 어차피 모든 지점에 대해서 최소 거리를 계산해야한다 -> 플로이드-와샬
        // 플로이드 와샬로 각 지점들 간의 최소거리를 계산한다.
        // 그 후, 각 지점부터 m이내에 갈 수 있는 다른 지점들의 아이템 개수 합을 구해, 최대 획득 가능 아이템 수를 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] items = new int[n + 1];
        for (int i = 1; i < items.length; i++)
            items[i] = Integer.parseInt(st.nextToken());

        int[][] adjMatrix = new int[n + 1][n + 1];
        for (int[] am : adjMatrix)      // 지점은 최대 100개, 거리는 최대 15이므로, 인접행렬의 초기값을 최대값으로 세팅.
            Arrays.fill(am, 15 * 100 + 1);
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());
            adjMatrix[a][b] = adjMatrix[b][a] = distance;
        }

        for (int via = 0; via < adjMatrix.length; via++) {      // 플로이드 와샬은 3개의 for문으로 구성하되, 가장 바깥은 경유지
            for (int start = 0; start < adjMatrix.length; start++) {        // 중간 for문은 출발지
                if (start == via)
                    continue;

                for (int end = 0; end < adjMatrix.length; end++) {      // 가장 안쪽 for문은 도착지로 설정한다.
                    if (end == start || end == via)
                        continue;

                    if (adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])        // 경유지를 거쳤을 때, 더 적은 이동거리라면, 갱신.
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }

        int maxItems = 0;
        // 이제 각 지점을 돌아다니면서 얻을 수 있는 아이템의 개수를 구한다.
        for (int i = 1; i < items.length; i++) {
            int item = items[i];        // 시작지점에서 얻을 수 있는 아이템수를 초기값으로.
            for (int j = 1; j < adjMatrix[i].length; j++) {     // 다른 지점을 돌며
                if (adjMatrix[i][j] <= m)       // 거리가 m이하인 곳의 아이템 개수들을 더한다.
                    item += items[j];
            }
            // i지점에서 얻을 수 있는 모든 아이템의 개수를 구했다. 최대로 얻을 수 있는 아이템 개수가 갱신되는지 확인하고 반영.
            maxItems = Math.max(maxItems, item);
        }
        // 획득 가능 최대 아이템 개수 출력.
        System.out.println(maxItems);
    }
}