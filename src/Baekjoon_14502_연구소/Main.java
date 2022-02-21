/*
 Author : Ruel
 Problem : Baekjoon 14502번 연구소
 Problem address : https://www.acmicpc.net/problem/14502
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14502_연구소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Loc {
    int r;
    int c;

    public Loc(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static int[][] lab;
    static List<Loc> viruses;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 연구소의 상태가 빈 상태 0, 벽 1, 바이러스 2로 주어진다
        // 바이러스는 사방으로 퍼져나간다. 이 때 3개의 벽을 세워 최대한 많은 안전 공간을 확보하고자 한다. 안전 공간의 최대값은?
        // 시뮬레이션 문제
        // n과 m이 최대 8로 그리 크지 않다. 완전 탐색으로 해결하더라도 큰 문제가 없을 것으로 보인다
        // 0인 공간 3개를 골라 벽을 세우고, 바이러스를 퍼뜨려본 후, 여전히 0으로 안전한 공간이 몇개인지 세어보며 최대값을 찾자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        lab = new int[n][m];
        viruses = new ArrayList<>();        // 바이러스의 위치들
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                lab[i][j] = Integer.parseInt(st.nextToken());
                if (lab[i][j] == 2)
                    viruses.add(new Loc(i, j));
            }
        }
        System.out.println(setWall(3, 0, 0));
    }

    static int setWall(int remain, int preWallR, int preWallC) {        // 벽을 세운다. remain은 아직 세우지 않은 벽의 개수
        if (remain == 0) {      // 벽을 모두 세웠다면
            int[][] mapClone = new int[lab.length][lab[0].length];
            for (int i = 0; i < lab.length; i++) {
                for (int j = 0; j < lab[i].length; j++)
                    mapClone[i][j] = lab[i][j];
            }
            // 현재 상태의 연구소를 복사한 후, getSafeAreaSize로 보내 안전 공간의 수를 세어 받아온 후 리턴한다.
            return getSafeAreaSize(mapClone);
        }

        int maxArea = 0;        // 현재 상태에서 얻을 수 있는 최대 안전 공간의 개수.
        for (int i = preWallR; i < lab.length; i++) {
            int j = (i == preWallR ? preWallC : 0);
            for (; j < lab[i].length; j++) {
                if (lab[i][j] == 0) {       // 빈 공간을 찾았다면
                    lab[i][j] = 1;      // 벽을 세우고
                    maxArea = Math.max(maxArea, setWall(remain - 1, i, j));       // setWall에 잔여 벽을 하나 줄이고 넘긴다.
                    // 끝났다면 해당 벽을 다시 제거.
                    lab[i][j] = 0;
                }
            }
        }
        return maxArea;
    }

    static int getSafeAreaSize(int[][] mapClone) {
        Queue<Loc> queue = new LinkedList<>(viruses);      // 바이러스들을 추가.
        while (!queue.isEmpty()) {
            Loc current = queue.poll();     // 현재 위치

            for (int d = 0; d < 4; d++) {       // 사방탐색
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC) && mapClone[nextR][nextC] == 0) {       // 연구소 안이고, 해당 공간이 0이라면
                    mapClone[nextR][nextC] = 2;     // 바이러스를 채우고,
                    queue.offer(new Loc(nextR, nextC));     // 다음엔 이 위치에서 탐색한다.
                }
            }
        }

        int size = 0;       // 안전 공간의 개수를 센다.
        for (int i = 0; i < mapClone.length; i++) {
            for (int j = 0; j < mapClone[i].length; j++) {
                if (mapClone[i][j] == 0)        // 0으로 바이러스가 퍼져있지 않다면
                    size++;     // 개수 증가
            }
        }
        // 그 후 size 리턴
        return size;
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < lab.length && c >= 0 && c < lab[r].length;
    }
}