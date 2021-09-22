/*
 Author : Ruel
 Problem : Baekjoon 1939번 중량제한
 Problem address : https://www.acmicpc.net/problem/1939
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 중량제한;

import java.util.*;

class Path {
    int end;
    int weight;

    public Path(int end, int weight) {
        this.end = end;
        this.weight = weight;
    }
}

public class Main3 {
    static final int MAX = 1_000_000_000;
    static List<List<Path>> paths;

    public static void main(String[] args) {
        // 이분 탐색을 활용한 경우
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        paths = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            paths.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int w = sc.nextInt();
            paths.get(a).add(new Path(b, w));
            paths.get(b).add(new Path(a, w));
        }
        int start = sc.nextInt();
        int end = sc.nextInt();

        int binaryStart = 1;
        int binaryEnd = MAX;
        while (binaryStart <= binaryEnd) {
            int middle = (binaryStart + binaryEnd) / 2;
            if (bfs(middle, start, end))
                binaryStart = middle + 1;
            else
                binaryEnd = middle - 1;
        }
        System.out.println(binaryEnd);      // middle값이 통과했을 때, middle + 1로 값을 하나 더 커져버리니, 이 값이 보정되어 낮아지는 binaryEnd 값이 정답.
    }

    static boolean bfs(int value, int start, int end) {     // value 값으로 목표 지점에 도달할 수 있는지 확인한다.
        int[] maxWeight = new int[paths.size()];
        maxWeight[start] = MAX;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);         // start 지점에서 시작
        boolean[] visited = new boolean[paths.size()];
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (visited[current])       // 방문했다면 패쓰
                continue;
            visited[current] = true;        // 방문 체크
            for (Path next : paths.get(current)) {
                int currentWeight = Math.min(maxWeight[current], next.weight);      // 현재 옮길 수 있는 최대무게
                if (currentWeight < value)      // 그 무게가 value 보다 작다면 해당 다리는 살펴볼 필요가 없다
                    continue;
                if (currentWeight > maxWeight[next.end]) {      // 다음 지점의 최대 무게가 갱신된다면
                    maxWeight[next.end] = currentWeight;        // 값을 넣고
                    queue.offer(next.end);      // queue 에 다음 지점을 삽입
                }
            }
        }
        // 최대무게가 value 보다 같거나 크다면 true
        return maxWeight[end] >= value;// 아니라면 false
    }
}