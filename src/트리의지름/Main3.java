/*
 Author : Ruel
 Problem : Baekjoon 1967번 트리의 지름
 Problem address : https://www.acmicpc.net/problem/1967
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 트리의지름;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node {
    int n;
    int distance;

    public Node(int n, int distance) {
        this.n = n;
        this.distance = distance;
    }
}

public class Main3 {
    static List<List<Node>> connection;

    public static void main(String[] args) throws IOException {
        // 전에 동일한 이름의 비슷한 문제를 푼 적이 있다.
        // 트리의 지름을 구하기 위해서는 랜덤한 한 노드로부터 가장 멀리 떨어진 노드를 구한다
        // 이를 A라 칭하고, A로부터 가장 멀리 떨어진 노드 B를 구한다
        // A - B가 서로 가장 멀리 떨어진 노드이다!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());

        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());       // a 노드와
            int b = Integer.parseInt(st.nextToken());       // b 노드는
            int c = Integer.parseInt(st.nextToken());       // c만큼 거리로 연결되어 있다.

            connection.get(a).add(new Node(b, c));
            connection.get(b).add(new Node(a, c));
        }

        Node a = getTheFartherNode(1);      // 랜덤한(1) 노드로부터 가장 멀리 떨어진 노드 a를 구한다
        Node b = getTheFartherNode(a.n);        // a로부터 가장 멀리 떨어진 노드 b를 구한다.
        System.out.println(b.distance);         // 이 때 a와 b 노드 사이의 거리가 트리의 지름이다!
    }

    static Node getTheFartherNode(int n) {
        Queue<Node> queue = new LinkedList<>();
        boolean[] visited = new boolean[connection.size()];     // 방문체크
        Node start = new Node(n, 0);        // 파라미터로부터 받은 시작 노드
        queue.offer(start);
        Node MFN = start;           // 가장 멀리 떨어진 노드를 기록
        visited[n] = true;          // 시작점 방문 체크
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (Node next : connection.get(current.n)) {       // current로부터 연결된 노드들 중에
                if (!visited[next.n]) {     // 방문하지 않았다면
                    visited[next.n] = true;     // 방문 체크 후
                    Node distanceSum = new Node(next.n, current.distance + next.distance);      // 거리를 더해 큐에 다시 넣어주자.
                    queue.offer(distanceSum);
                    if (distanceSum.distance > MFN.distance)        // 혹시 위 거리가 MFN의 길이보다 길다면 갱신해주자.
                        MFN = distanceSum;
                }
            }
        }
        // 최종적으로 가장 멀리 떨어진 노드 MFN을 리턴해준다.
        return MFN;
    }
}