/*
 Author : Ruel
 Problem : Baekjoon 1944번 복제 로봇
 Problem address : https://www.acmicpc.net/problem/1944
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1944_복제로봇;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seek {
    int idx;
    int r;
    int c;
    int distance;

    public Seek(int idx, int r, int c, int distance) {
        this.idx = idx;
        this.r = r;
        this.c = c;
        this.distance = distance;
    }
}

public class Main {
    static int[] parents, ranks;
    static char[][] map;
    static HashMap<Integer, Integer> keyIdx;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 미로가 주어진다.
        // 빈 공간은 0, 벽은 1, 로봇의 위치는 S, 열쇠의 위치는 K로 주어진다.
        // 로봇의 시작 위치와 열쇠의 위치에는 복제 기계가 있어, 로봇을 복제할 수 있다.
        // 모든 열쇠를 얻는데, 로봇 혹은 복제된 로봇의 이동 거리를 최소화하고자 할 때
        // 이동 거리는?
        //
        // 최소 스패닝 트리, BFS 문제
        // 복제를 할 수 있다 -> 한 장소에서 다른 복수의 열쇠로 이동할 수 있다.
        // 최소 스패닝 트리 문제와 같다.
        // 단 한 위치에서 다른 위치로의 거리를 계산할 때는 BFS를 활용한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 미로
        int n = Integer.parseInt(st.nextToken());
        // m개의 열쇠
        int m = Integer.parseInt(st.nextToken());
        
        // 미로의 각 격자의 값
        map = new char[n][n];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // 각 격자의 값을 살펴보며
        // 시작 지점과 열쇠의 위치를 구분한다.
        int startIdx = -1;
        keyIdx = new HashMap<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'K')
                    keyIdx.put(i * n + j, keyIdx.size() + 1);
                else if (map[i][j] == 'S')
                    startIdx = i * n + j;
            }
        }
        
        // 최소 스패닝 트리.
        // 분리 집합을 위한 세팅
        parents = new int[m + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[m + 1];
        
        // 시작 지점에서 갈 수 있는 열쇠들의 거리
        // 우선순위큐로 거리가 짧은 순으로 살펴본다.
        PriorityQueue<Seek> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));
        for (Seek s : calcDistances(startIdx))
            priorityQueue.offer(s);
        int sum = 0;
        boolean possible = true;
        while (!priorityQueue.isEmpty()) {
            Seek current = priorityQueue.poll();
            // 만약 거리가 초기값인 열쇠가 존재한다면, 모든 열쇠를 획득하는 것이 불가능한 경우
            // possible을 false 처리하고 반복문 종료
            if (current.distance == Integer.MAX_VALUE) {
                possible = false;
                break;
            } else if (findParent(0) == findParent(current.idx))        // 이미 획득한 열쇠라면 건너뛴다.
                continue;
            
            // 이동 거리에 누적시키고, 하나의 집합으로 묶어, 획득했다고 표시하자.
            sum += current.distance;
            union(0, current.idx);
            for (Seek s : calcDistances(current.r * n + current.c))
                priorityQueue.offer(s);
        }

        // 가능한 경우에는 이동 거리를, 불가능한 경우엔 -1을 출력한다.
        System.out.println(possible ? sum : -1);
    }

    // start에서 각 열쇠에 이르는 최소 거리를 BFS를 통해 구한다.
    static Queue<Seek> calcDistances(int start) {
        // 각 위치에 이르는 최소 거리
        int[][] distances = new int[map.length][map.length];
        // 큰 값 세팅
        for (int[] d : distances)
            Arrays.fill(d, Integer.MAX_VALUE);
        // 시작 위치는 0
        distances[start / map.length][start % map.length] = 0;

        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int row = current / map.length;
            int col = current % map.length;

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                if (checkArea(nextR, nextC) && map[nextR][nextC] != '1' &&
                        distances[nextR][nextC] > distances[row][col] + 1) {
                    distances[nextR][nextC] = distances[row][col] + 1;
                    queue.offer(nextR * map.length + nextC);
                }
            }
        }

        // 탐색을 마치고, start에서 각 열쇠에 이르는 거리를
        // Queue로 정리하여 반환한다.
        Queue<Seek> answer = new LinkedList<>();
        for (int key : keyIdx.keySet())
            answer.offer(new Seek(keyIdx.get(key), key / map.length, key % map.length, distances[key / map.length][key % map.length]));
        return answer;
    }

    // a와 b를 한 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // n이 속한 집합의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map.length;
    }
}