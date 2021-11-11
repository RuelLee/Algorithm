/*
 Author : Ruel
 Problem : Baekjoon 6091번 핑크 플로이드
 Problem address : https://www.acmicpc.net/problem/6091
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 핑크플로이드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 한 정점에서 다른 한 정점으로 이동할 수 있는 최소 비용이 주어질 때,
        // 한 정점으로부터 근접한 정점만 구하는 문제
        // 보통 인접행렬이 주어지면 이를 바탕으로 한 정점에서 다른 한 정점으로 이동하는 최소 비용을 구하는 문제가 나왔지만
        // 이는 그와 반대이다
        // 한 정점과 인접한 정점은 '반드시' 최소 비용으로 연결된다
        // 한 정점으로부터 다른 정점으로까지의 거리와 한 정점으로부터 경유지를 거쳐 다른 정점으로 이동하는 비용을 비교하여, 전자가 단독으로 최소인 경우에만 두 노드가 인접한 것이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[][] minDistance = new int[n][n];
        StringTokenizer st;
        for (int i = 0; i < minDistance.length - 1; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = i + 1; j < minDistance[i].length; j++)
                minDistance[i][j] = minDistance[j][i] = Integer.parseInt(st.nextToken());
        }
        List<PriorityQueue<Integer>> list = new ArrayList<>();
        for (int i = 0; i < n; i++)
            list.add(new PriorityQueue<>());

        for (int start = 0; start < n; start++) {           // A 노드와
            for (int end = start + 1; end < n; end++) {     // B 노드
                boolean check = true;
                for (int via = 0; via < n; via++) {     // 경유지
                    if (via == start || via == end)         // 중복이 된다면 건너뜀
                        continue;

                    // 만약 A 노드와 B 노드를 연결하는 비용과 같거나 작은 비용을 갖는 경유지가 나타난다면
                    // 두 노드는 인접한 노드가 아니다.
                    if (minDistance[start][end] >= minDistance[start][via] + minDistance[via][end]) {
                        check = false;
                        break;
                    }
                }
                // 그러한 경유지가 발견되지 않았다면
                // A 노드와 B 노드는 서로 인접한 노드이다.
                if (check) {
                    list.get(start).offer(end);
                    list.get(end).offer(start);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (PriorityQueue<Integer> pq : list) {
            sb.append(pq.size()).append(" ");
            while (!pq.isEmpty())
                sb.append(pq.poll() + 1).append(" ");
            sb.append("\n");
        }
        System.out.println(sb);
    }
}