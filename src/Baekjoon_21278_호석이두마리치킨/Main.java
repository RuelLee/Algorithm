/*
 Author : Ruel
 Problem : Baekjoon 21278번 호석이 두 마리 치킨
 Problem address : https://www.acmicpc.net/problem/21278
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21278_호석이두마리치킨;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시와 이 도시들을 잇는 m개의 도로가 주어진다.
        // 각 도로들로 도시 간에는 한 시간 거리로 이동할 수 있다고 한다.
        // 이 때 나머지 도시들에서 치킨집으로 왕복하는 시간이 최소인 도시 2곳에 치킨집을 세우고자 한다.
        // 해당하는 도시와 모든 도시에서의 왕복 시간 합을 출력하라.
        // 도시 선택이 다양하다면, 작은 번호가 더 작은 것을, 작은 번호가 같다면 큰 번호가 더 작은 것을 선택한다.
        //
        // 간단한 플로이드 와샬 문제
        // 플로이드 와샬로 각 도시에서 다른 도시로 이동하는데 걸리는 최소 시간을 구하고
        // 브루트포스로 n개의 도시중 2개의 도시를 선택해 각 도시의 왕복거리를 모두 계산해
        // 답을 찾는다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 인접 행렬
        int[][] adjMatrix = new int[n][n];
        // 최대 100개의 도시가 주어지므로 초기값으로는 100보다 큰 값으로, 오버플로우가 발생하지 않도록
        // 적절히 설정한다.
        for (int[] am : adjMatrix)
            Arrays.fill(am, 1000);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            
            // 도로가 있을 경우에는 서로 간의 이동 시간 1시간
            adjMatrix[a][b] = adjMatrix[b][a] = 1;
        }

        // 플로이드 와샬
        for (int via = 0; via < adjMatrix.length; via++) {
            for (int start = 0; start < adjMatrix.length; start++) {
                if (start == via)
                    continue;
                for (int end = 0; end < adjMatrix.length; end++) {
                    if (end == via || end == start)
                        continue;

                    // start -> end로 가는 경우보다 (사실상 이 경우는 초기값인 상황)
                    // start -> via -> end로 가는 경우가 더 짧은 시간이 소요되는 경우 갱신.
                    if (adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }
        
        // 답안
        int[] answer = {0, 0, Integer.MAX_VALUE};
        // 첫번째 도시
        for (int i = 0; i < n; i++) {
            // 두번째 도시는 첫번째 도시들보다 큰 값으로만.
            for (int j = i + 1; j < n; j++) {
                int sum = 0;

                // i, j가 아닌 모든 도시들에 왕복하는데 걸리는 시간을 더한다.
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j)
                        continue;

                    // k도시로 이동하는데 k <-> i, k <-> j 중 더 짧은 것을 택하고 왕복시간을 더해준다.
                    sum += Math.min(adjMatrix[i][k], adjMatrix[j][k]) * 2;
                }

                // 모든 도시를 왕복하는데 소요시간이 기존의 계산값보다 짧다면
                // 해당 i, j, k 값을 기록해둔다.
                if (sum < answer[2]) {
                    answer[0] = i;
                    answer[1] = j;
                    answer[2] = sum;
                }
            }
        }

        // 최종 답안을 출력한다.
        System.out.println((answer[0] + 1) + " " + (answer[1] + 1) + " " + answer[2]);
    }
}