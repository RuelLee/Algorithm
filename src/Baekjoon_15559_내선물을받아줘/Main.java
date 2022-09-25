/*
 Author : Ruel
 Problem : Baekjoon 15559번 내 선물을 받아줘
 Problem address : https://www.acmicpc.net/problem/15559
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15559_내선물을받아줘;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] group;
    static int[] ranks;
    static char[][] map;

    public static void main(String[] args) throws IOException {
        // n *m 의 지도가 주어지며, 각 지도에는 움직일 수 있는 방향이 N, W, E, S으로 주어진다.
        // 지도에서 랜덤한 곳에 선물을 주고하는 사람이 있고, 이 사람은 방향에 따라 계속 움직인다고 한다.
        // 이 사람에게 반드시 선물을 주기 위해서 필요한 최소한의 선물 개수는?
        //
        // 분리 집합 문제
        // 지도에 사이클이 생긴다면, 사이클 중 아무곳, 연결이 단방향이더라도 최종 목적지에 하나를 두면된다.
        // 따라서 지도를 살펴보며 현재 장소와 다음 장소를 무조건 하나의 그룹으로 묶어주자.
        // 최종적으로 남은 그룹의 개수를 센다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력. 지도의 세로, 가로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 지점에서의 방향.
        map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        // 각 지점의 그룹.
        group = new int[n * m];
        for (int i = 0; i < group.length; i++)
            group[i] = i;
        ranks = new int[n * m];

        // 방문 체크.
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 방문하지 않았다면
                if (!visited[i][j]) {
                    // 큐에 현재 장소 삽입.
                    Queue<Integer> queue = new LinkedList<>();
                    queue.offer(i * m + j);
                    // 큐가 빌 때까지 방향에 따라 이동한다.
                    while (!queue.isEmpty()) {
                        int row = queue.peek() / m;
                        int col = queue.poll() % m;
                        // 이미 방문했다면 건너뜀.
                        if (visited[row][col])
                            continue;
                        // 아니라면 방문 체크.
                        visited[row][col] = true;
                        
                        // 다음 장소의 좌표
                        int nextR = row;
                        int nextC = col;
                        switch (map[row][col]) {
                            // 각각 북 서 동 남 일 때의 변화
                            case 'N' -> nextR -= 1;
                            case 'W' -> nextC -= 1;
                            case 'E' -> nextC += 1;
                            case 'S' -> nextR += 1;
                        }

                        // 맵을 벗어나지 않는다면, 현재 장소와 다음 장소를 하나의 그룹으로 묶는다.
                        if (checkArea(nextR, nextC))
                            union(row * m + col, nextR * m + nextC);
                    }
                }
            }
        }

        // 총 그룹의 개수를 센다.
        HashSet<Integer> hashSet = new HashSet<>();
        // 각 그룹의 대표자를 해쉬셋에 추가.
        for (int i = 0; i < group.length; i++)
            hashSet.add(findGroup(i));
        // 전체 해쉬셋의 크기(= 연결된 그룹의 수 = 선물을 놓아야하는 최소 수)를 출력.
        System.out.println(hashSet.size());
    }
    
    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }

    // 두 그룹을 하나의 그룹으로 묶는다.
    static void union(int a, int b) {
        int ga = findGroup(a);
        int gb = findGroup(b);

        if (ranks[ga] >= ranks[gb]) {
            group[gb] = ga;
            if (ranks[ga] == ranks[gb])
                ranks[ga]++;
        } else
            group[ga] = gb;
    }

    // 각 그룹의 대표를 찾는다.
    static int findGroup(int n) {
        if (group[n] == n)
            return n;
        return group[n] = findGroup(group[n]);
    }
}