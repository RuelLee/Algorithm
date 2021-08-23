/*
 Author : Ruel
 Problem : Baekjoon 5213번 과외맨
 Problem address : https://www.acmicpc.net/problem/5213
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 과외맨;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class Tile {
    int r;
    int c;
    boolean leftTile;

    public Tile(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public Tile(int r, int c, boolean leftTile) {
        this.r = r;
        this.c = c;
        this.leftTile = leftTile;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] map;
    static int[][] tileNum;
    static int[][] minDistance;
    static Tile[][] trace;

    public static void main(String[] args) {
        // 구현 문제!
        // 홀수번째 row와 짝수 번째 row의 시작 타일의 위치가 다르고, 끝나는 지점도 다르다는 것을 고려
        // BFS로 최소거리를 찾아가며 탐색하고, 이를 거꾸로 추적하며 경로를 알아내면 된다.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        map = new int[n][n * 2];        // 값을 입력받을 공간
        tileNum = new int[n][n * 2];    // 타일의 번호를 미리 저장해둘 공간

        double count = 1;
        for (int i = 0; i < map.length; i++) {
            for (int j = i % 2; j < map[i].length - i % 2; j++) {       // 홀수번째 row는 0부터 시작해서 length-1까지 입력받고, 짝수번째 row은 1부터 시작해서 length-2까지 입력 받는다.
                map[i][j] = sc.nextInt();
                tileNum[i][j] = (int) count;        // count는 0.5씩 증가시키며 이를 int로 변환하여, 같은 값을 두 공간에 저장해주자.
                count += 0.5;
            }
        }

        minDistance = new int[n][n * 2];        // 해당 타일에 도착한 최소거리를 저장
        minDistance[0][0] = minDistance[0][1] = 1;      // 시작 지점은 1
        trace = new Tile[n][n * 2];         // 자신이 도착하기 직전 타일을 저장할 공간
        Queue<Tile> queue = new LinkedList<>();
        queue.add(new Tile(0, 0, true));
        queue.add(new Tile(0, 1, false));

        Tile maxTile = new Tile(0, 0, true);
        while (!queue.isEmpty()) {
            Tile current = queue.poll();

            if (tileNum[maxTile.r][maxTile.c] < tileNum[current.r][current.c])      // 타일 번호의 최댓값을 갱신
                maxTile = current;

            if (tileNum[current.r][current.c] == tileNum[tileNum.length - 1][tileNum[tileNum.length - 1].length - 1])       // 마지막 타일에 도착했다면 bfs 종료
                break;

            for (int d = 0; d < 4; d++) {       // 4방 탐색
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                // 맵을 벗어나지 않고, 같은 숫자이며, 최소거리를 갱신할 때만
                if (checkArea(nextR, nextC) && map[current.r][current.c] == map[nextR][nextC] && (minDistance[nextR][nextC] == 0 || minDistance[nextR][nextC] > minDistance[current.r][current.c] + 1)) {
                    if (current.leftTile) {     // 현재 왼쪽 타일이었다면
                        queue.add(new Tile(nextR, nextC, false));       // 다음은 오른쪽 타일
                        queue.add(new Tile(nextR, nextC - 1, true));        // 한쌍인 타일도 넣어준다.
                        minDistance[nextR][nextC - 1] = minDistance[nextR][nextC] = minDistance[current.r][current.c] + 1;      // 최소거리 갱신
                        trace[nextR][nextC - 1] = new Tile(current.r, current.c);       // 다음 타일에 현재 타일을 기록해서 나중에 추적할 수 있게 하자
                    } else {
                        queue.add(new Tile(nextR, nextC, true));
                        queue.add(new Tile(nextR, nextC + 1, false));
                        minDistance[nextR][nextC] = minDistance[nextR][nextC + 1] = minDistance[current.r][current.c] + 1;
                        trace[nextR][nextC + 1] = new Tile(current.r, current.c);
                    }
                    trace[nextR][nextC] = new Tile(current.r, current.c);
                }
            }
        }
        Stack<Integer> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        Tile current = maxTile;     // 도착한 최대 타일로부터
        sb.append(minDistance[current.r][current.c]).append("\n");      // 거리를 알아내고
        while (current != null) {
            stack.push(tileNum[current.r][current.c]);      // 스택에 값을 넣어가며
            current = trace[current.r][current.c];          // 거꾸로 추적
        }
        while (!stack.isEmpty())        // 스택에서 값을 꺼내어 출력하면 = 경로
            sb.append(stack.pop()).append(" ");
        System.out.println(sb);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}