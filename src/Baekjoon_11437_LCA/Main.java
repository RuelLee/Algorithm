/*
 Author : Ruel
 Problem : Baekjoon 11437번 LCA
 Problem address : https://www.acmicpc.net/problem/11437
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11437_LCA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n(2 <= n <= 50,000)개의 정점으로 이루어진 트리가 주어진다.
        // m( 1<= m <= 10,000)개 쌍의 노드들이 주어질 때, 공통 조상이 몇 번인지 출력하라
        //
        // 트리, 희소 배열 문제
        // 최대 50_000개의 정점이 주어지므로 최대 깊이는 50_000 - 1이 될 수 있다.
        // 따라서 희소배열로 sparseArray[노드개수][log2(노드개수-1)+1]로 희소배열의 크기를 정할 수 있다.
        // 그 후, 루트 노드인 1부터 자식 노드들을 찾아가며
        // 깊이와 희소 배열 값들을 채워나간다.
        // 그 후 쿼리를 처리해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // 희소 배열
        int[][] sparseArray = new int[n + 1][(int) (Math.log(n - 1) / Math.log(2)) + 1];
        
        // 노드 연결 정보
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            connections.get(b).add(a);
        }
        
        // 깊이
        int[] depths = new int[n + 1];
        // BFS 탐색
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            // 부모 노드를 제외한 자식 노드들에 한해
            for (int child : connections.get(current)) {
                if (child == sparseArray[current][0])
                    continue;
                
                // 자식 노드의 조상 노드로 current 기록
                sparseArray[child][0] = current;
                // 큐 추가
                queue.offer(child);
                // 깊이 계산
                depths[child] = depths[current] + 1;
                // 자식 노드의 희소배열을 채운다.
                for (int i = 1; i <= Math.log(depths[child]) / Math.log(2); i++)
                    sparseArray[child][i] = sparseArray[sparseArray[child][i - 1]][i - 1];
            }
        }
        
        // m개의 쿼리 처리
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // a노드와 b노드의 공통 조상을 찾는다.
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 두 노드의 깊이가 다르다면 같게 맞춰준다.
            while (depths[a] != depths[b]) {
                int diff = Math.abs(depths[a] - depths[b]);
                // 희소배열을 통해 2^k 제곱 만큼씩 깊이 차리를 줄인다.
                int jump = (int) (Math.log(diff) / Math.log(2));
                if (depths[a] > depths[b])
                    a = sparseArray[a][jump];
                else
                    b = sparseArray[b][jump];
            }

            // 두 노드의 깊이가 같아졌다.
            // 이제 가장 가까운 공통 조상을 찾는다.
            while (sparseArray[a][0] != sparseArray[b][0]) {
                // 희소 배열을 끝부터 살펴보며, 달라지는 가장 이른 지점으로 두 노드를 이동시킨다.
                for (int j = (int) (Math.log(depths[a]) / Math.log(2)); j >= 0; j--) {
                    if (sparseArray[a][j] != sparseArray[b][j]) {
                        a = sparseArray[a][j];
                        b = sparseArray[b][j];
                        break;
                    }
                }
            }
            // 두 노드의 부모 노드가 같아졌다.
            // a와 b가 같아지는 경우 또한 존재하므로
            // 우선적으로 두 노드가 같은지 살펴보고 같다면 해당 노드를 출력
            // 다르다면 두 노드의 부모 노드를 출력한다.
            sb.append(a == b ? a : sparseArray[a][0]).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}