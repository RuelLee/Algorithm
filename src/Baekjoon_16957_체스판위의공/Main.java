/*
 Author : Ruel
 Problem : Baekjoon 16957번 체스판 위의 공
 Problem address : https://www.acmicpc.net/problem/16957
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16957_체스판위의공;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] ends;
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws IOException {
        // r * c인 체스판이 있고, 칸마다 정수가 적혀있다.
        // 각 칸에는 공이 놓여있으며, 공은 8방향(가로, 세로, 대각선)을 살펴보고 자신보다 작은 정수가 있는 곳으로
        // 이동한다고 한다.
        // 최종적으로 각 칸에 있는 공의 개수를 출력하라.
        //
        // 그래프 탐색 문제..?
        // 해당 칸에 도착한 공이 가는 곳은 항상 같으므로,
        // 그냥 각 칸마다 가장 처음 이동하는 위치를 기록해둔다.
        // 그 후, 각 칸마다 최종 도착하는 위치를 찾으며, 경로 단축을 이용한다.
        // 그리고 각 칸마다 도착한 공의 개수를 세고, 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 체스판
        int[][] board = new int[r][];
        for (int i = 0; i < board.length; i++)
            board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 해당 위치의 공이 이동하는 위치를 표시한다.
        ends = new int[r][c];
        // 초기값으로는 자신의 위치.
        for (int i = 0; i < ends.length; i++) {
            for (int j = 0; j < ends[i].length; j++)
                ends[i][j] = i * c + j;
        }
        
        // 각 칸마다 인접한 최소값을 찾고
        // 해당 칸으로 이동함을 기록해두자.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // 초기값은 자기 자신의 값과 위치
                int minValue = board[i][j];
                int loc = i * c + j;
                // 8방 탐색을 하며 가장 작은 정수값을 갖는 위치를 찾는다.
                for (int d = 0; d < 8; d++) {
                    int nearR = dr[d] + i;
                    int nearC = dc[d] + j;

                    if (checkArea(nearR, nearC) && minValue > board[nearR][nearC]) {
                        minValue = board[nearR][nearC];
                        loc = nearR * c + nearC;
                    }
                }

                // i, j에 있는 공은 항상 loc의 위치로 간다.
                ends[i][j] = loc;
            }
        }

        // 각 칸에 모이는 공의 수를 센다.
        int[][] counts = new int[r][c];
        for (int i = 0; i < ends.length; i++) {
            for (int j = 0; j < ends[i].length; j++) {
                // i, j에 도달한 공은 항상 loc으로 이동한다.
                int loc = findEnds(i, j);
                // loc에 모이는 공의 개수 추가.
                counts[loc / c][loc % c]++;
            }
        }

        // 각 칸에 모인 공의 개수를 기록
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ends.length; i++) {
            for (int j = 0; j < ends[i].length; j++)
                sb.append(counts[i][j]).append(" ");
            sb.deleteCharAt(sb.length() - 1).append("\n");
        }
        
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 8방 탐색을 할 때, 범위를 벗어나는지 여부를 체크한다.
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < ends.length && c >= 0 && c < ends[r].length;
    }

    // ends[][]에 기록된 값이 자신의 위치가 될 때까지 올라간다.
    // 이는 r, c에 있는 공이 계속해서 굴러감을 의미
    // 경로 단축을 사용하여, 거치는 모든 칸들에게 최종 위치를 기록하여
    // 다음에 참고할 경우, 연산을 줄인다.
    static int findEnds(int r, int c) {
        if (ends[r][c] == r * ends[r].length + c)
            return r * ends[r].length + c;
        return ends[r][c] = findEnds(ends[r][c] / ends[r].length, ends[r][c] % ends[r].length);
    }
}