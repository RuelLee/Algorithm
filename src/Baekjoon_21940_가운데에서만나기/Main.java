/*
 Author : Ruel
 Problem : Baekjoon 21940번 가운데에서 만나기
 Problem address : https://www.acmicpc.net/problem/21940
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21940_가운데에서만나기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 준형이와 친구들은 서로 다시 도시에 살고 있다.
        // a -> b로 가는 시간과 b -> a로 가는 시간은 서로 다를 수 있다.
        // 왕복 시간은 a라는 도시에서 b로 가는 시간과 b에서 다시 a로 돌아오는 시간의 합이다.
        // 준형이와 친구들은 한 도시를 선정해 만나기로 했다.
        // 해당 도시는 각 친구들의 왕복 시간들 중 최대값이 최소가 되는 도시를 선정한다.
        // 해당 도시가 여러개인 경우 오름차순으로 출력한다.
        //
        // 모든 도시에서 모든 도시로 가는 경우를 모두 계산해야하므로
        // 플로이드-와샬을 적용하여 푼다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 인접 행렬
        int[][] adjMatrix = new int[n][n];
        // 큰 값으로 초기화
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);
        // 자기 도시가 선정될 경우, 이동 거리는 0
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i][i] = 0;
        
        // a -> b로 가는 도로의 소요 시간.
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int t = Integer.parseInt(st.nextToken());

            adjMatrix[a][b] = t;
        }
        
        // 플로이드 와샬
        for (int via = 0; via < adjMatrix.length; via++) {
            for (int start = 0; start < adjMatrix.length; start++) {
                if (via == start)
                    continue;
                for (int end = 0; end < adjMatrix.length; end++) {
                    if (end == via || end == start ||
                            adjMatrix[start][via] == Integer.MAX_VALUE ||
                            adjMatrix[via][end] == Integer.MAX_VALUE)
                        continue;

                    if (adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }
        
        // 친구들의 위치
        int k = Integer.parseInt(br.readLine());
        int[] locations = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).map(operand -> operand - 1).toArray();

        // 도시에 도달하는 친구들의 왕복시간들의 최대값이 가장 작은 도시의 왕복시간 값.
        int minTime = Integer.MAX_VALUE;
        // 해당 도시들
        List<Integer> list = new ArrayList<>();

        // 모든 도시에 대해 계산한다.
        for (int i = 0; i < adjMatrix.length; i++) {
            // i라는 도시에 친구들이 도달하는 왕복시간들 중 가장 큰 값
            int maxTime = 0;
            for (int l : locations) {
                // 한 친구라도 길이 없다면, 종료.
                if (adjMatrix[i][l] == Integer.MAX_VALUE || adjMatrix[k][i] == Integer.MAX_VALUE) {
                    maxTime = Integer.MAX_VALUE;
                    break;
                }
                // 아니라면 최대값 갱신.
                maxTime = Math.max(maxTime, adjMatrix[i][l] + adjMatrix[l][i]);
            }
            // 한 친구라도 길이 없다면 건너뛴다.
            if (maxTime == Integer.MAX_VALUE)
                continue;
            
            // 해당 최대값이 기존에 등장했던 최대값들보다 같거나 작다면
            if (minTime >= maxTime) {
                // 새로운 최소값이 나왓다면
                if (minTime > maxTime) {
                    // 값 갱신 후
                    minTime = maxTime;
                    // 리스트 초기화
                    list = new ArrayList<>();
                }
                // 갱신이든, 추가든 i 도시 추가.
                list.add(i);
            }
        }

        // 기록된 전체 도시들 출력.
        StringBuilder sb = new StringBuilder();
        for (int i : list)
            sb.append(i + 1).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }
}