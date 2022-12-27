/*
 Author : Ruel
 Problem : Baekjoon 22352번 항체 인식
 Problem address : https://www.acmicpc.net/problem/22352
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22352_항체인식;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 조직이 주어진다.
        // 어느 칸에 항체를 놓으면 항체는 상하좌우의 자신과 같이 같은 값을 갖는 격자들로 퍼져나간 후
        // 퍼져나간 격자의 값을 모두 바꾼다고 한다.(기존과 같은 값이 들어올 수 있다.)
        // 백신을 놓기 전과 후의 데이터가 주어질 때
        // 해당 백신을 맞았을 가능성이 있다면 YES 그렇지 않다면 NO를 출력한다.
        //
        // 그래프 탐색 문제
        // 먼저 항체를 맞을 경우 상하좌우로 퍼져나가므로, 상하좌우에 같은 값을 갖는 격자들을 하나의 그룹으로 묶는다.
        // 그리고 해당 그룹은 이제 값이 안바뀐다면 같이 안 바뀌고, 바뀐다면 같이 바뀌어야한다.
        // 또한 백신을 한번만 맞으므로 두 그룹 이상의 값이 바뀌어선 안된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 백신을 맞기 전 사진.
        int[][] cells = new int[n][];
        for (int i = 0; i < cells.length; i++)
            cells[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 해당 사진을 통해 그룹을 나눈다.
        int[][] group = new int[n][m];
        int groupCounter = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (group[i][j] != 0)
                    continue;

                // i, j와 같은 값을 갖는 상하좌우 격자를 찾아 그룹으로 묶는다.
                group[i][j] = ++groupCounter;
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i * m + j);
                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    int r = current / m;
                    int c = current % m;

                    group[r][c] = groupCounter;
                    for (int d = 0; d < 4; d++) {
                        int nearR = r + dr[d];
                        int nearC = c + dc[d];

                        if (checkArea(nearR, nearC, cells) && group[nearR][nearC] == 0 &&
                                cells[r][c] == cells[nearR][nearC]) {
                            queue.offer(nearR * m + nearC);
                            group[nearR][nearC] = groupCounter;
                        }
                    }
                }
            }
        }
        
        // 백신 투여 후 사진
        int[][] results = new int[n][];
        for (int i = 0; i < results.length; i++)
            results[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int[] groupValues = new int[groupCounter + 1];
        int changedGroups = 0;
        boolean possible = true;
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < results[i].length; j++) {
                // 해당 격자가 속한 그룹의 값이 지정되지 않았다면 처음 만난 그룹
                if (groupValues[group[i][j]] == 0) {
                    // 그룹에 값을 지정하고
                    groupValues[group[i][j]] = results[i][j];
                    // 혹시 값이 변한 그룹이 2개 이상이라면 종료한다.
                    if (cells[i][j] != results[i][j] && ++changedGroups > 1)
                        break;
                } else if (groupValues[group[i][j]] != results[i][j]) {
                    // 그룹의 값이 지정되어있다면 지정되어있는 그룹의 값과 일치하는지 확인한다.
                    // 일치하지 않는다면 같은 그룹의 값은 같이 바뀌어야한다는 규칙을 위배하므로 특정 백신일 가능성이 없다.
                    possible = false;
                    break;
                }
            }
        }

        // 한 그룹에 하나의 값만 존재하고,
        // 값이 바뀐 그룹이 2개 미만이라면 해당 백신일 가능성이 있으므로 YES 출력
        // 그렇지 않다면 NO 출력.
        System.out.println(possible && changedGroups < 2 ? "YES" : "NO");
    }

    static boolean checkArea(int r, int c, int[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}