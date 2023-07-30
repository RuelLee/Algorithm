/*
 Author : Ruel
 Problem : Baekjoon 10711번 모래성
 Problem address : https://www.acmicpc.net/problem/10711
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10711_모래성;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, -1, -1, 0, 1, 1, 1, 0};
    static int[] dc = {-1, 0, 1, 1, 1, 0, -1, -1};
    static int h, w;

    public static void main(String[] args) throws IOException {
        // h, w 크기의 모래사장이 주어진다.
        // 각 격자에는 모래가 쌓여 모래성을 이루고 있다.
        // 모래성에 파도가 치는데, 각 격자의 모래는 격자의 값보다 
        // 8방향 중 빈공간의 수가 같거나 더 클 경우 무너진다고 한다.
        // 몇 번의 파도가 몰려오고나서, 더 이상 모래성의 모양이 변하지 않는지 출력하라
        //
        // BFS 문제
        // 문제에서 말하는대로 각 모래의 위치에서 주위 8방향을 탐색하고 그 때마다 모래를 무너뜨리는 경우
        // 시간 초과가 난다.
        // 약간 생각의 전환이 필요했는데
        // 빈 공간들을 8방향을 탐색해, 주위에 모래가 있을 경우, 그 모래의 값을 하나 깎는다.
        // 그러다 모래가 무너져 빈 공간이 되면 다음 번엔 그 빈 공간을 탐색하는 식으로
        // 모래가 주체가 아니라 빈 공간을 주체로 계산해야했다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // h * w 크기의 모래사장
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        // 모래성
        int[][] sandCastle = new int[h][w];
        // 빈 공간의 좌표
        Queue<Integer> empties = new LinkedList<>();
        for (int i = 0; i < sandCastle.length; i++) {
            String input = br.readLine();
            for (int j = 0; j < sandCastle[i].length; j++) {
                if (input.charAt(j) != '.')
                    sandCastle[i][j] = input.charAt(j) - '0';
                else
                    empties.offer(i * w + j);
            }
        }

        // 시간
        int time = 0;
        // 모래성의 변화 유무
        boolean changed = true;
        while (changed) {
            changed = false;

            // 현재 들어있는 빈 공간들만 체크한다.
            int size = empties.size();
            for (int i = 0; i < size; i++) {
                int current = empties.poll();
                // 빈 공간의 좌표
                int row = current / w;
                int col = current % w;

                // 8방 탐색
                for (int d = 0; d < 8; d++) {
                    int nearR = row + dr[d];
                    int nearC = col + dc[d];

                    // 해당 공간이 맵 범위 안이고
                    // 모래가 쌓여있으며
                    // 이번에 하나를 깎아 값이 0이 된다면
                    if (checkArea(nearR, nearC) && sandCastle[nearR][nearC] > 0 && --sandCastle[nearR][nearC] == 0) {
                        // 큐에 추가
                        empties.offer(nearR * w + nearC);
                        // 변화 유무 체크
                        changed = true;
                    }
                }
            }

            // 이번 파도에 모래성이 변화했다면 횟수를 증가시킨다.
            if (changed)
                time++;
        }

        // 더 이상 모래성이 변화하지 않는 파도 횟수를 출력한다.
        System.out.println(time);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < h && c >= 0 && c < w;
    }
}