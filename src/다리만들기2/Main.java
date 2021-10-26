/*
 Author : Ruel
 Problem : Baekjoon 17472번 다리 만들기 2
 Problem address : https://www.acmicpc.net/problem/17472
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 다리만들기2;

import java.util.*;

class State {
    int r;
    int c;
    int direction;
    int distance;
    int origin;

    public State(int r, int c, int direction, int distance, int origin) {
        this.r = r;
        this.c = c;
        this.direction = direction;
        this.distance = distance;
        this.origin = origin;
    }
}

class Bridge {
    int start;
    int end;
    int cost;

    public Bridge(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
}

public class Main {
    static int[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static Queue<State> queue;
    static int[][] minDistance;
    static int[] parents;
    static int[] ranks;


    public static void main(String[] args) {
        // 시뮬레이션 문제.
        // 여러가지 고려할 것이 많은 문제였다
        // 먼저 각 위치에 따른 사방으로 연결된 섬을 하나로 묶을 수 있어야한다.
        // 그리고 각 섬으로부터 다른 섬으로 직선으로의 거리를 구해야한다
        // 그 후 섬 간의 거리에 따라 최소 스패닝 트리로 연결해야한다
        // 구현 사항도 많고 알고리즘도 쓰이는 문제

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        map = new int[n][m];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = sc.nextInt();
        }

        int count = 2;
        queue = new LinkedList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 1)     // 하나의 땅(1)을 발견하면 그로부터 연결된 곳을 모두 (2이상의) 같은 숫자로 채워준다.
                    setDifferentNum(i, j, count++);
            }
        }

        minDistance = new int[count - 2][count - 2];    // 각 섬이 2부터 서로 다른 번호로 할당되어있다. 서로 간의 거리를 저장해둘 것이다.
        for (int[] md : minDistance)
            Arrays.fill(md, Integer.MAX_VALUE);
        while (!queue.isEmpty()) {
            State current = queue.poll();
            int destination = map[current.r][current.c];        // 현재 위치는 destination

            if (map[current.r][current.c] != 0 && destination != current.origin) {      // 만약 출발점으로부터 다른 위치의 섬에 도착했다면
                if (current.distance > 2 && minDistance[destination - 2][current.origin - 2] > current.distance - 1) {      // 거리가 2이상이고, 최소 거리를 갱신하는 경우에,
                    minDistance[destination - 2][current.origin - 2]
                            = minDistance[current.origin - 2][destination - 2] = current.distance - 1;      // 값을 채워넣어준다.
                }
                continue;
            }

            if (current.direction == -1) {      // 방향이 -1이라면 처음 시작점. 여기서부터 4방을 둘러보며
                for (int d = 0; d < 4; d++) {
                    int nextR = current.r + dr[d];
                    int nextC = current.c + dc[d];

                    if (checkArea(nextR, nextC) && map[nextR][nextC] != current.origin && map[nextR][nextC] == 0)        // 다음 위치가 맵 안에 있고, 물일 때만
                        queue.offer(new State(nextR, nextC, d, current.distance + 1, current.origin));      // queue에 다음 위치를 담아준다.
                }
                // 방향이 주어진 경우, 직선으로만 이동해야한다
                // 다음 위치가 맵 안에 있고, 그 값이 0이거나, 출발 섬과 다른 섬일 때만 큐에 담아준다.
            } else if (checkArea(current.r + dr[current.direction], current.c + dc[current.direction]) &&
                    (map[current.r + dr[current.direction]][current.c + dc[current.direction]] == 0 || map[current.r + dr[current.direction]][current.c + dc[current.direction]] != current.origin))
                queue.offer(new State(current.r + dr[current.direction], current.c + dc[current.direction], current.direction, current.distance + 1, current.origin));
        }

        // 위까지 각 섬으로부터 다른 섬까지의 최소 거리를 구했다.
        // 여기서부턴 각 섬들 간에 다리를 연결해준다. Kruskal 알고리즘을 사용해주자.
        parents = new int[count - 2];
        ranks = new int[count - 2];
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;

        PriorityQueue<Bridge> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        for (int i = 0; i < minDistance.length; i++) {      // 우선순위큐에 섬들간에 다리를 놓을 수 있는 경우를 모두 넣어주자.
            for (int j = i + 1; j < minDistance[i].length; j++) {
                if (minDistance[i][j] != Integer.MAX_VALUE)
                    priorityQueue.offer(new Bridge(i, j, minDistance[i][j]));
            }
        }
        int sum = 0;
        while (!priorityQueue.isEmpty()) {      // 우선순위큐가 빌 때까지
            Bridge current = priorityQueue.poll();
            if (findParents(current.start) == findParents(current.end))     // 두 섬이 이미 연결되어있다면 패쓰
                continue;

            sum += current.cost;        // 아니라면 거리를 더하고
            union(current.start, current.end);      // 두 섬에 다리를 놓자.
        }
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < count - 2; i++)
            hashSet.add(findParents(i));        // 각 섬들의 그룹을 찾아
        // 한 그룹이라면(= 모두 연결되었다면) 그 때의 비용(sum)을 출력하고, 그렇지 않다면 불가능한 경우이므로 -1을 출력하자.
        System.out.println(hashSet.size() == 1 ? sum : -1);
    }

    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[a] > ranks[b])
            parents[pb] = pa;
        else {
            parents[pa] = pb;
            if (ranks[pa] == ranks[pb])
                ranks[pb]++;
        }
    }

    static int findParents(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParents(parents[n]);
    }

    static void setDifferentNum(int r, int c, int num) {
        map[r][c] = num;
        queue.offer(new State(r, c, -1, 0, num));
        for (int d = 0; d < 4; d++) {
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            if (checkArea(nextR, nextC) && map[nextR][nextC] == 1)
                setDifferentNum(nextR, nextC, num);
        }
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}