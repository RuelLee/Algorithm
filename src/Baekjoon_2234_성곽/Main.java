/*
 Author : Ruel
 Problem : Baekjoon 2234번 성곽
 Problem address : https://www.acmicpc.net/problem/2234
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2234_성곽;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] castle;
    static int[] dr = {0, -1, 0, 1};
    static int[] dc = {-1, 0, 1, 0};

    public static void main(String[] args) throws IOException {
        // m, n 크기의 성곽이 주어진다
        // 각 방에 대해 벽에 대한 정보가 주어진다.
        // 서 1, 북 2, 동, 4, 남 8 값이 더해진 값이 주어진다.
        // 예를 들어 서북남 방향에 벽이 있는 방이 주어진다면 1 + 2 + 8 = 11 값이 주어진다.
        // 1. 이 성에 있는 방의 개수
        // 2. 가장 넓은 방의 넓이
        // 3. 벽을 하나 제거하고 얻을 수 있는 가장 넓은 방의 크기
        // 를 구해 출력하라
        //
        // 구현, 비트마스킹, 그래프 탐색 문제
        // 먼저 방들을 비트마스킹 값을 확인하며 연결된 구역들을 하나의 그룹으로 묶어준다.
        // 그 후, 할당된 그룹의 구역의 수를 센 후,
        // 인접한 그룹인 경우, 두 그룹을 하나로 합쳤을 때의 최대값을 찾는다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n은 가로, m은 세로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 주어지는 구역의 벽의 정보
        castle = new int[m][];
        for (int i = 0; i < castle.length; i++)
            castle[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 각 칸에 대한 그룹을 찾는다.
        int[][] group = new int[m][n];
        int groupCounter = 1;
        for (int i = 0; i < castle.length; i++) {
            for (int j = 0; j < castle[i].length; j++) {
                // 이미 그룹이 할당되어있다면 건너뛰기.
                if (group[i][j] > 0)
                    continue;
                
                // 그렇지 않다면 너비우선 탐색을 시작한다.
                Queue<Integer> queue = new LinkedList<>();
                // 현재 위치를 큐에 넣고
                queue.offer(i * n + j);
                // 그룹 할당.
                group[i][j] = groupCounter;
                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    int row = current / n;
                    int col = current % n;
                    
                    // 비트값을 확인하여 벽의 상태를 확인한다.
                    int bit = castle[row][col];
                    for (int d = 0; d < 4; d++) {
                        // 해당하는 방향에 벽이 없다면
                        if ((bit & 1) != 1) {
                            int nextR = row + dr[d];
                            int nextC = col + dc[d];

                            // 해당하는 옆 공간에 그룹이 할당되어있는지 확인하고,
                            // 아직 안되어있다면
                            if (group[nextR][nextC] == 0) {
                                // i, j와 같은 그룹 할당.
                                group[nextR][nextC] = groupCounter;
                                // 그리고 큐에 삽입.
                                queue.offer(nextR * n + nextC);
                            }
                        }
                        // 다음 방향으로 넘어간다.
                        bit >>= 1;
                    }
                }
                // 해당 그룹에 대한 탐색이 끝났으므로 다음 그룹으로 넘어간다.
                groupCounter++;
            }
        }

        // 각 그룹의 구역 수를 센다.
        int[] groupMembers = new int[groupCounter];
        for (int i = 0; i < group.length; i++) {
            for (int j = 0; j < group[i].length; j++)
                groupMembers[group[i][j]]++;
        }

        // 인접한 두 그룹을 합칠 때의 최대 크기를 구한다.
        int maxSize = 0;
        for (int i = 0; i < group.length; i++) {
            for (int j = 0; j < group[i].length; j++) {
                // 사방탐색
                for (int d = 0; d < 4; d++) {
                    int nearR = i + dr[d];
                    int nearC = j + dc[d];

                    // 인접한 두 구역이 서로 다른 그룹이라면 합쳤을 때의 크기가
                    // 최대값을 갱신하는지 확인한다.
                    if (checkArea(nearR, nearC) && group[i][j] != group[nearR][nearC])
                        maxSize = Math.max(maxSize, groupMembers[group[i][j]] + groupMembers[group[nearR][nearC]]);
                }
            }
        }

        // 답 출력.
        StringBuilder sb = new StringBuilder();
        sb.append(groupCounter - 1).append("\n");
        sb.append(Arrays.stream(groupMembers).max().getAsInt()).append("\n");
        sb.append(maxSize).append("\n");
        System.out.print(sb);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < castle.length && c >= 0 && c < castle[r].length;
    }
}