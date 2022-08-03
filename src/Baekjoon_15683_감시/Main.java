/*
 Author : Ruel
 Problem : Baekjoon 15683번 감시
 Problem address : https://www.acmicpc.net/problem/15683
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15683_감시;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] map;
    // 기존적인 네 방향.
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    // 각 카메라 번호에 따른 볼 수 있는 방향들.
    static int[][] cameraDirections = {{}, {1}, {1, 3}, {0, 1}, {0, 1, 3}, {0, 1, 2, 3}};
    // 각 카메라의 방향 별 볼 수 있는 구역들.
    static HashSet<Integer>[][] coverages;

    public static void main(String[] args) throws IOException {
        // n, m 크기의 사무실에 대한 정보가 주어진다
        // 1 ~ 5는 카메라들이고, 6은 벽이다.
        // 카메라는 번호에 따라
        // 1 →
        // 2 ←→
        // 3 ↑→
        // 4 ←↑→
        // 5 ←↑→↓
        // 방향으로 벽이 있지 않는 한 사무실 벽까지 볼 수 있다고 한다.
        // 각 카메라는 90도씩 360도 회전이 가능하다고 한다
        // 카메라의 방향을 적절히 조절하여 사각 지대를 최소화하려고 한다.
        // 이 때 사각 지역의 크기를 구하라.
        //
        // 구현 + 브루트 포스 문제
        // n, m의 크기가 작고, cctv 역시 최대 8개가 주어지므로 일일이 모든 경의 수를 따져
        // 사각 지대가 가장 적은 경우를 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 사무실 정보.
        map = new int[n][m];
        int walls = 0;
        List<Integer> cameras = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                // 벽일 경우, 나중에 사각 지대에서 제외해준다.
                if (map[i][j] == 6)
                    walls++;
                    // 카메라의 위치를 따로 저장하자.
                else if (map[i][j] > 0)
                    cameras.add(i * m + j);
            }
        }

        // 각 카메라 별로 90, 180. 270, 360도 회전시켰을 때 볼 수 있는 구역에 대한 정보들을 담자.
        coverages = new HashSet[cameras.size()][4];
        for (int i = 0; i < cameras.size(); i++) {
            // 카메라의 row, col
            int row = cameras.get(i) / m;
            int col = cameras.get(i) % m;
            // 카메라의 종류.
            int type = map[row][col];

            // 기본적으로 네 방향에 대해 한번씩 탐색하자.
            HashSet<Integer>[] coverageByDirections = new HashSet[4];
            for (int d = 0; d < 4; d++) {
                coverageByDirections[d] = new HashSet<>();
                int nextR = row + dr[d];
                int nextC = col + dc[d];
                // 해당 방향으로 벽을 만날 때까지
                while (checkArea(nextR, nextC)) {
                    if (map[nextR][nextC] == 6)
                        break;

                    // 빈 공간이라면 해당 정보를 저장한다
                    // 카메라는 저장 x
                    if (map[nextR][nextC] == 0)
                        coverageByDirections[d].add(nextR * m + nextC);
                    nextR += dr[d];
                    nextC += dc[d];
                }
            }
            // 네 방향으로 벽을 만나기 전까지 카메라가 볼 수 있는 위치가 저장됐다.

            // 이제 카메라 종류를 보고, 0도, 90도, 180도, 270도 회전시켰을 때 볼 수 있는 구역을 저장하자.
            // rotate * 90도 회전했을 때
            for (int rotate = 0; rotate < 4; rotate++) {
                // 해당 정보를 coverages에 저장한다.
                coverages[i][rotate] = new HashSet<>();
                // 카메라 타입에 따라 볼 수 있는 방향들이 다르다.
                // 각 방향들에 + rotate 만큼을 더한 뒤, 모듈러 연산을 통해 방향들을 계산해고,
                // 해당하는 지역들을 coverages에 추가시켜준다.
                for (int j = 0; j < cameraDirections[type].length; j++)
                    coverages[i][rotate].addAll(coverageByDirections[(cameraDirections[type][j] + rotate) % 4]);
            }
        }

        // bruteForce 메소드를 통해, 각 카메라들을 회전시키는 경우를 포함하여 최소 사각지역의 개수를 구한다.
        int maxCoverage = bruteForce(0, new int[cameras.size()]);
        int shadeArea = n * m - maxCoverage - walls - cameras.size();
        System.out.println(shadeArea);
    }

    static int bruteForce(int camera, int[] directions) {
        // 마지막 카메라까지 방향이 정해졌다면
        if (camera == directions.length) {
            // 해쉬셋에 해당 지역들을 중복 없이 구한다.
            HashSet<Integer> hashSet = new HashSet<>();
            // i번 카메라가 directions[i] 방향일 때 볼 수 있는 지역들을 hashSet에 저장한다.
            for (int i = 0; i < coverages.length; i++)
                hashSet.addAll(coverages[i][directions[i]]);
            // 그 크기를 반환.
            return hashSet.size();
        }

        // 최대 감시 지역
        int max = 0;
        // camera를 rotate * 90도 회전시켰을 때
        for (int rotate = 0; rotate < 4; rotate++) {
            // camera 방향을 배열에 저장해주고
            directions[camera] = rotate;
            // bruteForce 메소드를 재귀적으로 콜하며, 다음 카메라로 넘긴다.
            // 그 때의 최대 감시 지역과 이전 최대 지역을 비교하며 최대 감시 지역을 구한다.
            max = Math.max(max, bruteForce(camera + 1, directions));
        }

        // 최대 감시 지역 크기 반환.
        return max;
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}