/*
 Author : Ruel
 Problem : Baekjoon 23747번 와드
 Problem address : https://www.acmicpc.net/problem/23747
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23747_와드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int r, c;

    public static void main(String[] args) throws IOException {
        // r * c 크기의 격자가 주어진다.
        // 각 격자들은 할당된 문자를 갖고 있으며, 좌우상하로 인접한 같은 문자를 갖고 있는 경우, 하나의 영역으로 인식된다.
        // 와드는 하나의 영역 전체를 밝힐 수 있다.
        // 주인공의 첫 위치와 이동 궤적이 주어진다.
        // 이동 궤적은 U, D, L, R, W로 주어지고 각각 위, 아래, 왼쪽, 오른쪽으로 한 칸 이동과 와드 설치를 나타낸다.
        // 주인공은 마지막 위치에서는 상하좌우를 볼 수 있다.
        // 밝혀진 영역은 ., 밝혀지지 않은 영역은 #으로 표시하여 출력하라
        //
        // BFS 문제
        // 시키는대로 이동 궤적을 쫓아가며 와드를 설치하면 되는 문제
        // 같은 영역에 복수의 와드를 설치하는 경우, 첫번째 이후는 무시하는게 좋다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 격자의 크기 r, c
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        
        // 격자의 정보
        char[][] map = new char[r][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 처음 시작 위치
        st = new StringTokenizer(br.readLine());
        int hr = Integer.parseInt(st.nextToken()) - 1;
        int hc = Integer.parseInt(st.nextToken()) - 1;
        
        // 이동 궤적
        String history = br.readLine();
        // 밝힌 격자를 표시할 char 배열
        char[][] answer = new char[r][c];
        for (char[] row : answer)
            Arrays.fill(row, '#');

        // 이동궤적을 쫓아간다.
        for (int i = 0; i < history.length(); i++) {
            switch (history.charAt(i)) {
                // 각각 위, 아래, 왼쪽, 오른쪽으로 한칸 이동하는 경우.
                case 'U' -> hr--;
                case 'D' -> hr++;
                case 'L' -> hc--;
                case 'R' -> hc++;
                case 'W' -> {
                    // 와드를 설치하는 경우.
                    // 현재 위치에 해당하는 영역에 와드가 설치된 적이 없을 때만 와드를 설치
                    //
                    if (answer[hr][hc] != '.') {
                        Queue<Integer> queue = new LinkedList<>();
                        queue.offer(hr * c + hc);
                        // 현재 위치 밝힘
                        answer[hr][hc] = '.';
                        while (!queue.isEmpty()) {
                            int current = queue.poll();
                            int currentR = current / c;
                            int currentC = current % c;
                            
                            // 사방탐색
                            for (int d = 0; d < 4; d++) {
                                int nearR = currentR + dr[d];
                                int nearC = currentC + dc[d];
                                
                                // 범위를 벗어나지 않고, 아직 밝히지 않은 격자이며
                                // 같은 영역일 경우
                                // 밝히고, 큐에 추가
                                if (checkArea(nearR, nearC) && answer[nearR][nearC] != '.'
                                        && map[currentR][currentC] == map[nearR][nearC]) {
                                    answer[nearR][nearC] = '.';
                                    queue.offer(nearR * c + nearC);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // 마지막 최종 위치의 상하좌우를 밝힘
        answer[hr][hc] = '.';
        for (int d = 0; d < 4; d++) {
            int nearR = hr + dr[d];
            int nearC = hc + dc[d];

            if (checkArea(nearR, nearC))
                answer[nearR][nearC] = '.';
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (char[] a : answer) {
            for (char c : a)
                sb.append(c);
            sb.append("\n");
        }
        // 출력
        System.out.print(sb);
    }

    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}