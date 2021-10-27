/*
 Author : Ruel
 Problem : Baekjoon 1800번 인터넷 설치
 Problem address : https://www.acmicpc.net/problem/1800
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 인터넷설치;

import java.util.*;

class Cable {
    int end;
    int cost;

    public Cable(int end, int cost) {
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    static List<List<Cable>> connection;
    static final int MAX = 1000001;

    public static void main(String[] args) {
        // 이분 탐색 + 다익스트라 문제
        // 상당히 생각을 해야했다.
        // a - b 연결하는 케이블의 비용이 비싸다고 해서 전체 케이블의 비용이 비쌀 지는 알 수 없다
        // k 개수만큼 케이블 비용이 면제되고, 남은 케이블 중 제일 비싼 것의 가격만 받기 때문
        // 따라서 이분 탐색으로 비용을 정하고, 해당 비용으로 1에서 n까지 연결이 가능한지 살펴야한다
        // 다익스트라 알고리즘을 활용하여 최소 비용으로 n까지 연결해야한다
        // 하지만 여기서 비용은 면제되는 케이블의 개수로 사용해야한다
        // 다시 말해 이분 탐색으로 얻은 middle 값이 있다. 이보다 적은 값의 케이블은 무료이므로 생각하지 않아도 좋다
        // middle 값보다 큰 케이블들을 세어 나가, k개 이하로 n번 컴퓨터까지 연결해야하는 것이다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int p = sc.nextInt();
        int k = sc.nextInt();

        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());

        for (int i = 0; i < p; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int cost = sc.nextInt();

            connection.get(a).add(new Cable(b, cost));
            connection.get(b).add(new Cable(a, cost));
        }
        int start = 0;
        int end = MAX;
        while (start < end) {
            int middle = (start + end) / 2;
            if (dijkstra(middle, k))
                end = middle;
            else
                start = middle + 1;
        }
        // start 값이 MAX 값이 되버렸다면 원하는 답이 없는 경우.
        System.out.println(start == MAX ? -1 : start);
    }

    static boolean dijkstra(int cost, int k) {
        Cable[] cables = new Cable[connection.size()];
        for (int i = 1; i < connection.size(); i++)
            cables[i] = new Cable(i, k + 1);        // k이하의 무료 케이블 사용이 가능하므로, k보다 큰 값으로 초기화해준다.
        cables[1].cost = 0;         // 시작점인 1번 컴퓨터의 비용은 0

        PriorityQueue<Cable> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        priorityQueue.add(cables[1]);
        boolean[] visited = new boolean[connection.size()];
        while (!priorityQueue.isEmpty()) {
            Cable current = priorityQueue.poll();
            if (current.end == connection.size() - 1)       // n번 컴퓨터에 도착했다면 true를 리턴.
                return true;

            if (visited[current.end])       // 이미 방문한 적이 있는 컴퓨터라면 더 고려하지 않아도 된다. continue;
                continue;

            for (Cable c : connection.get(current.end)) {       // current.end 컴퓨터에서 연결할 수 있는 Cable들 중
                if (!visited[c.end]) {          // 방문한 적이 없고,
                    if (c.cost <= cost) {       // middle 값으로 얻은 cost보다 더 작은 cost를 갖고 있다면
                        cables[c.end].cost = current.cost;      // current.cost(무료로 사용된 케이블의 개수)를 그대로 가져가고
                        priorityQueue.remove(cables[c.end]);        // 우선순위큐에서 제거했다가 다시 삽입함으로써 정렬해준다.
                        priorityQueue.offer(cables[c.end]);
                    }
                    // cost보다 큰 값을 갖고 있지만, 아직 무료로 사용할 수 있는 케이블의 여유가 있고
                    // 더 적은 수의 케이블로 해당 컴퓨터를 연결할 수 있는 경우.
                    else if (current.cost < k && cables[c.end].cost > current.cost + 1) {
                        cables[c.end].cost = current.cost + 1;      // 최소 사용 무료 케이블 값을 갱신해주고,
                        priorityQueue.remove(cables[c.end]);        // 우선순위큐에서 제거 후, 재삽입으로 정렬해준다.
                        priorityQueue.offer(cables[c.end]);
                    }
                }
            }
            // current.end 에서의 연산이 모두 끝났다. 방문 체크해주자.
            visited[current.end] = true;
        }
        // 모든 경우를 따졌지만, return true 로 빠져나가지 못한 경우. false 를 반환해주자.
        return false;
    }
}