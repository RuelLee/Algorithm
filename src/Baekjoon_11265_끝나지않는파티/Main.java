/*
 Author : Ruel
 Problem : Baekjoon 11265번 끝나지 않는 파티
 Problem address : https://www.acmicpc.net/problem/11265
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11265_끝나지않는파티;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 파티장과 m명의 손님이 주어진다.
        // n개의 줄에 각 파티장으로 가는데 직접 연결된 도로로 가는 시간이 주어진다.
        // 이 시간은 다른 파티장을 거쳐 원하는 파티장으로 가는 것이 더 빠를 수도 있다.
        // m개의 줄에 a b c 형태로 쿼리가 주어지며, a 파티장에 있는 사람이 b 파티장으로 c 시간 이내에 갈 수 있는지 여부이다.
        // 갈 수 있다면 Enjoy other party, 갈 수 없다면 Stay here를 출력하자.
        //
        // 플로이드 와샬 문제
        // 각 파티장에서 다른 파티장으로 이동하는데 경유를 해서 가는 경우가 더 빠른지
        // 모든 경우를 구해둔 뒤, 쿼리에 대해 답하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 인접행렬
        int[][] adjMatrix = new int[n][];
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 플로이드 와샬
        // 경유지
        for (int via = 0; via < adjMatrix.length; via++) {
            // 출발지
            for (int start = 0; start < adjMatrix.length; start++) {
                // 출발지와 도착지가 같다면 건너 뛴다.
                if (start == via || adjMatrix[start][via] == 0)
                    continue;

                // 도착지
                for (int end = 0; end < adjMatrix.length; end++) {
                    // 출발지와 도착지, 경유지와 도착지가 같다면 건너뛴다.
                    if (end == start || end == via || adjMatrix[via][end] == 0)
                        continue;

                    // 출발지 -> 도착지보다 출발지 -> 경유지 -> 도착지가 더 빠른 시간에 도착할 수 있따면
                    // 해당 내용을 기록해준다.
                    if (adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }

        // m개의 쿼리에 대해 답한다.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());

            // a -> b로 시간 내에 도착할 수 있다면 Enjoy other party
            // 그렇지 않다면 Stay here를 출력한다.
            sb.append(adjMatrix[a][b] <= c ? "Enjoy other party" : "Stay here").append("\n");
        }
        System.out.print(sb);
    }
}