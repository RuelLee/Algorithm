/*
 Author : Ruel
 Problem : Baekjoon 14466번 소가 길을 건너간 이유 6
 Problem address : https://www.acmicpc.net/problem/14466
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 소가길을건너간이유6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<HashSet<Integer>> road;
    static int[][] farm;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 문제 이해가 좀 걸렸던 문제
        // n * n 크기의 농장이 주어지고, 각 칸에서 다른 칸으로 자유롭게 이동이 가능하다
        // 어느 칸끼리는 길로 연결이 되어있다.
        // 칸 들 중 일부 칸에 소가 들어있다.
        // 서로 만나는데 반드시 길을 지나야만 하는 소는 몇 쌍인지 구하는 문제
        // 길이 연결되어있는 칸이 있더라도 우회로가 있다면 길을 건너지 않고 만날 수 있다
        // 따라서 각 경우에 대해서 계산해주어야한다. 완전탐색 문제
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        farm = new int[n][n];
        road = new ArrayList<>();           // 길을 나타내줄 리스트.
        for (int i = 0; i < n * n; i++)
            road.add(new HashSet<>());      // 그 안엔 해쉬셋으로 받아주자.

        // 길이 (r1, c1), (r2, c2) 형태로 주어지므로, 하나의 값으로 받기 위해
        // r*n + c 형태로 바꿔서 담아주자.
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int a = (Integer.parseInt(st.nextToken()) - 1) * n + (Integer.parseInt(st.nextToken()) - 1);
            int b = (Integer.parseInt(st.nextToken()) - 1) * n + (Integer.parseInt(st.nextToken()) - 1);
            road.get(a).add(b);
            road.get(b).add(a);
        }

        // 소들이 주어진다.
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            farm[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1]++;
        }

        int count = 0;
        for (int i = 0; i < farm.length; i++) {
            for (int j = 0; j < farm[i].length; j++)
                count += calcMinRoad(i, j); // 각 칸에 있는 소가 다른 소들 중 반드시 길을 건너야만 만날 수 있는 소의 수를 계산
        }
        System.out.println(count);

    }

    static int calcMinRoad(int r, int c) {      // r, c의 소가 다른 소를 만나는데 반드시 길을 건너야만 하는 경우를 센다.
        if (farm[r][c] != 1)        // r, c에 소가 없다면 0
            return 0;

        farm[r][c] = 0;     // 현재 칸을 지워 다음 번에는 이 소가 세어지지 않게 한다.
        int[][] minRoad = new int[farm.length][farm.length];        // 각 칸에 도달하는 최소 도로의 수를 기록
        for (int[] mr : minRoad)
            Arrays.fill(mr, Integer.MAX_VALUE);     // Integer.MAX_VALUE로 초기화
        minRoad[r][c] = 0;          // 시작칸의 초기값은 0
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(r * farm.length + c);       // 현재 위치를 큐에 담고
        while (!queue.isEmpty()) {
            int current = queue.poll();     // 꺼낸다
            int row = current / farm.length;        // 현재의 row
            int col = current % farm.length;        // 현재의 col

            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];    // 다음 칸의 row
                int nextC = col + dc[d];    // 다음 칸의 col
                int next = nextR * farm.length + nextC;     // 다음 칸의 idx 번호

                if (checkArea(nextR, nextC)) {      // 칸이 범위 안이고
                    if (road.get(current).contains(next)) {     // 길로 연결이 되어있다면
                        if (minRoad[nextR][nextC] > minRoad[row][col] + 1) {    // 현재 위치에서 다음 위치로 가는데 길을 하나 건너는게 최소값이라면
                            minRoad[nextR][nextC] = minRoad[row][col] + 1;      // 갱신
                            queue.offer(next);      // 다음 위치를 큐에 담아준다.
                        }
                    } else {        // 길이 있지 않다면 자유롭게 이동 가능.
                        if(minRoad[nextR][nextC] > minRoad[row][col]){      // 현재 최소 도로와 다음 칸의 최소 도로의 수 비교
                            minRoad[nextR][nextC] = minRoad[row][col];      // 갱신
                            queue.offer(next);      // 큐에 담아준다.
                        }
                    }
                }
            }
        }
        // 최종적으로 소가 있고, 그 때의 최소 경유 도로가 1개 이상이라면 카운트 해준다
        int count = 0;
        for (int i = 0; i < farm.length; i++) {
            for (int j = 0; j < farm[i].length; j++) {
                if (farm[i][j] == 1 && minRoad[i][j] > 0)
                    count++;
            }
        }
        // 해당 값을 리턴.
        return count;
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < farm.length & c >= 0 && c < farm[r].length;
    }
}