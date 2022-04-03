/*
 Author : Ruel
 Problem : Baekjoon 10026번 적록색약
 Problem address : https://www.acmicpc.net/problem/10026
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10026_적록색약;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static char[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] sections;
    static int[][] blindSections;

    public static void main(String[] args) throws IOException {
        // R G B로 나타내진 그림이 주어진다
        // 일반인들은 RGB를 모두 구분할 수 있지만, 적록색약의 경우, RG을 구분하지 못한다고 한다
        // 일반인과 적록색약이 해당 그림을 봤을 때, 구분할 수 있는 구역의 개수를 나타내라
        //
        // 그래프 탐색 문제
        // DFS나 DFS를 통해 완전탐색으로 이웃한 같은 색을 하나의 색으로 묶어 그룹화해나가며 구역의 수를 센다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        map = new char[n][];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();

        sections = new int[n][n];       // 일반인이 보는 구역
        blindSections = new int[n][n];      // 적록색약이 보는 구역.

        int count = 0;
        int blindCount = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (sections[i][j] == 0) {      // 일반인이 봤을 때 아직 세지 않은 구역이라면
                    sections[i][j] = ++count;       // 새로운 구역으로 지정하고, findSections 메소드를 불러온다.
                    findSection(i, j, false);
                }

                if (blindSections[i][j] == 0) {     // 적록색약이 봤을 때 아직 세지 않은 구역이라면
                    blindSections[i][j] = ++blindCount;     // 새로운 구역으로 지정하고, findSections 메소드를 불러온다.
                    findSection(i, j, true);
                }
            }
        }
        // 최종적으로 일반인은 count 만큼의 구역으로
        // 적록색약은 blindCount 만큼의 구역으로 그림을 볼 수 있다.
        System.out.println(count + " " + blindCount);
    }

    static void findSection(int r, int c, boolean colorBlind) {     // 이웃한 같은 색을 DFS를 이용하여 하나의 그룹으로 표시하는 메소드
        for (int d = 0; d < 4; d++) {       // 4방 탐색
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            if (checkArea(nextR, nextC)) {      // 배열의 범위를 넘지 않으며
                // 일반인이고, 구역 표시가 안됐으며, (r, c)와 (nextR, nextC)가 같은 색이라면
                if (!colorBlind && sections[nextR][nextC] == 0 && map[r][c] == map[nextR][nextC]) {
                    // (nextR, nextC)에 (r, c)와 같은 구역으로 표시한다.
                    sections[nextR][nextC] = sections[r][c];
                    // 그리고 재귀적으로 메소드를 부른다.
                    findSection(nextR, nextC, false);
                }
                // 적록색약이고, 아직 구역 표시가 안됐으며
                // 현재 지점과 다음 지점의 색이 같거나, R - G 혹은 G - R의 색이라면
                else if (colorBlind && blindSections[nextR][nextC] == 0 &&
                        (map[r][c] == map[nextR][nextC] || (map[r][c] == 'R' && map[nextR][nextC] == 'G') || (map[r][c] == 'G' && map[nextR][nextC] == 'R'))) {
                    // 같은 구역으로 표시하고
                    blindSections[nextR][nextC] = blindSections[r][c];
                    // 재귀적으로 메소드를 부른다.
                    findSection(nextR, nextC, true);
                }
            }
        }
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}