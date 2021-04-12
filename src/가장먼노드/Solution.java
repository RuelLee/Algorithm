package 가장먼노드;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        int n = 6;
        int[][] edge = {{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}};

        int[] distance = new int[n];    // 거리의 최소값을 저장할 distance 공간
        boolean[] check = new boolean[n];   // 해당 노드를 방문했는지 체크할 boolean 공간
        check[0] = true;    // 1번 -> 1번까지의 거리를 체크할 필요없으므로 true값 저장

        List<Integer>[] lists = new List[n];    // 각 지점들로부터 연결된 곳을 저장할 List 배열
        for (int i = 0; i < lists.length; i++)
            lists[i] = new ArrayList<>();

        for (int[] arr : edge) {    // 각 지점으로부터 연결된 곳을 저장한다
            int a = arr[0];
            int b = arr[1];

            lists[a - 1].add(b - 1);
            lists[b - 1].add(a - 1);
        }
        Queue<Integer> order = new LinkedList<>();  // BFS로 순회를 돌자!
        order.add(0);   // 0(1번 지점)에서 시작

        while (!order.isEmpty()) {  // 큐가 빌 때까지
            int cur = order.poll(); // 값을 꺼내서
            for (int next : lists[cur]) {   // 연결된 지점들을 체크
                if (!check[next]) {     // 아직 방문하지 않은 곳이라면
                    distance[next] = distance[cur] + 1;     // cur지점부터 하나 떨어진 곳이므로, distance[cur]+1 값을 저장해주자.
                    check[next] = true;     // 방문했다고 체크!
                    order.add(next);    // 그리고 다음 번엔 해당 지점으로부터 연결된 곳을 돌자!
                }
            }
        }
        int max = 0;    // 완성된 distance 배열에서 최대값의 개수를 찾자.
        int count = 0;
        for (int i : distance) {
            if (max < i) {
                max = i;
                count = 1;
            } else if (max == i)
                count++;
        }
        System.out.println(count);
    }
}