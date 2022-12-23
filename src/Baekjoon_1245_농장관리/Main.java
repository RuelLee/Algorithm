/*
 Author : Ruel
 Problem : Baekjoon 1245번 농장 관리
 Problem address : https://www.acmicpc.net/problem/1245
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1245_농장관리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 농장이 주어진다.
        // 이 농장의 각 격자마다 높이가 있고, 산봉우리마다 경비원을 배치하려한다.
        // 산봉우리는 같은 높이를 갖는 하나 혹은 인접한 격자의 집합이고,
        // 산봉우리와 인접한 격자는 모두 산봉우리 높이보다 작아야한다.
        // 산봉우리의 수를 구하라
        //
        // 완전 탐색 문제
        // 푸는 여러가지 방법이 있겠지만
        // 먼저 높이 역순으로 격자를 살펴보며, 자신보다 작거나 같은 격자들을 하나의 그룹으로 묶었다.
        // 그 후, 그룹에 속하지 않은 것들만 계속적으로 살펴보며 총 몇개의 그룹이 나오는지 세었다.
        // 이 그룹의 수가 산봉우리의 수.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 입력 처리
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 모든 칸에 대해 높이에 대한 역순으로 방문한다.
        // 최대 힙 우선순위큐 활용.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(map[o2 / m][o2 % m], map[o1 / m][o1 % m]));
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++)
                priorityQueue.offer(i * m + j);
        }

        // 봉우리 수
        int counter = 0;
        // 그룹
        int[][] group = new int[n][m];
        while (!priorityQueue.isEmpty()) {
            // 높이 역순으로 격자를 하나 꺼내
            int idx = priorityQueue.poll();
            // 이미 그룹이 있거나 높이가 0 이라면 건너뛴다.
            if (group[idx / m][idx % m] != 0 || map[idx / m][idx % m] == 0)
                continue;
            
            // 아니라면 counter 하나 증가
            counter++;
            // 해당 격자에 counter 그룹 부여
            group[idx / m][idx % m] = counter;
            // idx와 인접한 격자들은 모두 탐색한다.
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(idx);
            while (!queue.isEmpty()) {
                int r = queue.peek() / m;
                int c = queue.poll() % m;
                
                // 8방 탐색
                for (int d = 0; d < 8; d++) {
                    int nearR = r + dr[d];
                    int nearC = c + dc[d];

                    // 인접한 격자가 범위를 벗어나지 않으며
                    // 아직 그룹에 속하지 않고
                    // 현재 격자의 높이보다 작거나 같다면
                    if (checkArea(nearR, nearC, map) && group[nearR][nearC] == 0 && map[r][c] >= map[nearR][nearC]) {
                        // 같은 그룹에 넣고 queue에 추가한다.
                        group[nearR][nearC] = counter;
                        queue.offer(nearR * m + nearC);
                    }
                }
            }
            // 하나의 그룹 탐색 완료
        }
        // 전체 그룹의 수(산봉우리의 수) 출력.
        System.out.println(counter);
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}