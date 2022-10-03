/*
 Author : Ruel
 Problem : Baekjoon 17141번 연구소 2
 Problem address : https://www.acmicpc.net/problem/17141
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17141_연구소2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] lab;
    static int[][][] minTimes;
    static List<Integer> viruses;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int minTime = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 연구소가 주어진다.
        // 0 빈 공간, 1 벽, 2 바이러스를 놓을 수 있는 빈 공간으로 주어지며
        // 바이러스는 매 1초 상화좌우로 퍼진다고 한다.
        // m개의 위치에 바이러스를 놓아, 벽이 아닌 모든 공간에 퍼뜨리려고할 때, 필요한 최소 시간은?
        //
        // 조합과 BFS를 활용하는 구현 문제
        // 먼저 2의 위치들에 바이러스를 놓았을 때, 각각 공간에 바이러스가 퍼지는 시간을 구한다.
        // 그 후 조합을 통해 m개의 위치를 선정하고, 각각의 공간에 대해서는 선정된 바이러스들 중 가장 먼저 도착하는 바이러스들을 기록한다.
        // 그 후, 각 공간들에 바이러스가 퍼지는 시간들 중 가장 큰 값을 가져온다.
        // 각각의 조합들에 대해 전체 공간에 바이러스가 퍼지는 시간들 중 가장 작은 값을 출력한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 입력으로 주어지는 연구소.
        lab = new int[n][];
        for (int i = 0; i < n; i++)
            lab[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 바이러스를 놓을 수 있는 위치.
        viruses = new ArrayList<>();
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                if (lab[i][j] == 2)
                    viruses.add(i * n + j);
            }
        }

        // 각 바이러스를 놓을 수 있는 위치에 따라 BFS로 각 공간에 바이러스가 퍼지는  시간을 구한다.
        minTimes = new int[viruses.size()][n][n];
        for (int i = 0; i < minTimes.length; i++) {
            for (int[] minTime : minTimes[i]) {
                Arrays.fill(minTime, Integer.MAX_VALUE);
            }

            int virus = viruses.get(i);
            minTimes[i][virus / n][virus % n] = 0;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(virus);
            while (!queue.isEmpty()) {
                int current = queue.poll();
                int row = current / n;
                int col = current % n;

                for (int d = 0; d < 4; d++) {
                    int nextR = row + dr[d];
                    int nextC = col + dc[d];

                    if (checkArea(nextR, nextC, lab) && lab[nextR][nextC] != 1 && minTimes[i][nextR][nextC] > minTimes[i][row][col] + 1) {
                        minTimes[i][nextR][nextC] = minTimes[i][row][col] + 1;
                        queue.offer(nextR * n + nextC);
                    }
                }
            }
        }

        // 조합을 통해 2의 위치들 중 m개를 선택해 바이러스를 퍼뜨리고, 그 때의 모든 공간에 바이러스가 퍼지는 최소시간을 구한다.
        selectLocations(0, 0, m, 0);
        // 답 출력.
        System.out.println(minTime == Integer.MAX_VALUE ? -1 : minTime);
    }

    // m개의 위치를 선정해 바이러스를 퍼뜨렸을 때 모든 공간에 바이러스가 퍼지는 시간을 구한다.
    static void selectLocations(int idx, int selected, int maxViruses, int bitmask) {
        // idx가 바이러스 개수를 넘어가면 불가능한 경우. 종료.
        if (idx > viruses.size())
            return;

        // m개의 바이러스를 모두 선택했다면
        if (selected == maxViruses) {
            // 벽이 아닌 각 공간들에 퍼지는 시간들 중 가장 큰 값
            // = 모든 공간에 바이러스가 퍼지는 시간을 구한다.
            int maxTime = 0;
            for (int i = 0; i < lab.length; i++) {
                for (int j = 0; j < lab[i].length; j++) {
                    // 만약 벽이라면 건너뛴다.
                    if (lab[i][j] == 1)
                        continue;

                    // i, j 공간이 벽이 아니라면
                    int min = Integer.MAX_VALUE;
                    // i, j 공간에 선택된 바이러스들 중 가장 빨리 퍼지는 시간을 구한다.
                    for (int bit = 0; bit < viruses.size(); bit++) {
                        if (((1 << bit) & bitmask) != 0)
                            min = Math.min(min, minTimes[bit][i][j]);
                    }
                    // i, j 공간에 가장 빨리 바이러스가 퍼지는 시간은 min이다.
                    // 이 값이 모든 공간에 바이러스가 퍼지는 시간들 중 가장 큰 값인지 확인한다.
                    maxTime = Math.max(maxTime, min);
                }
            }
            // 모든 공간에 바이러스가 퍼지는 시간들 중 가장 큰 값은 maxTime으로 계산되었다.
            // 모든 공간에 바이러스가 퍼지는 시간들 중에서, maxTime이 최소값인지 확인한다.
            minTime = Math.min(minTime, maxTime);
            return;
        }

        selectLocations(idx + 1, selected, maxViruses, bitmask);
        selectLocations(idx + 1, selected + 1, maxViruses, bitmask | (1 << idx));
    }

    static boolean checkArea(int row, int col, int[][] lab) {
        return row >= 0 && row < lab.length && col >= 0 && col < lab[row].length;
    }
}