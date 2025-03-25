/*
 Author : Ruel
 Problem : Baekjoon 17135번 캐슬 디펜스
 Problem address : https://www.acmicpc.net/problem/17135
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17135_캐슬디펜스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m, d;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        // 캐슬 디펜스는 성을 향해 몰려오는 적을 잡는 턴 방식의 게임이다
        // n * m 크기의 격자판이 주어진다.
        // 격자판의 n번행 아래(n+1번 행)의 모든 칸에는 성이 있고
        // 이 성들 중 3칸에 궁수들을 배치하고자 한다.
        // 한 턴이 지날 때마다, 적들은 성에 한 칸씩 다가온다.
        // 궁수들이 쏠 수 있는 거리는 d이며, 적들이 성에 도달하면 그대로 사라진다고 한다.
        // 궁수들은 자기 사거리 내에 있는 적들 중 가장 가까운 적, 그러한 적이 여럿이라면
        // 가장 왼쪽에 있는 적을 공격한다. 궁수들은 동시에 공격하므로 같은 적을 맞출 수도 있다.
        // 최대한 많이 잡을 수 있는 적의 수는?
        //
        // 시뮬레이션, 구현, 브루트 포스, 조합 문제
        // 조합을 통해 성들의 위치 중 3곳에 궁수들을 배치한다.
        // 그 후, 그 위치에서 잡을 수 있는 적들의 최대 수를 구한다.
        // 고려해야하는 점은, 턴이 지날 때마다 적이 한 칸씩 다가온다는 점.
        // 또한 궁수들이 맞추는 적에 대한 조건이 주어져있고, 복수의 궁수가 하나의 적을 맞출 수도 있다는 점이다.
        // 따라서 이번에 맞추는 적을 탐색할 때
        // △과 같은 모양으로 탐색한다
        // 가령 4 * 3의 위치에서 두번째 궁수가 맞출 적을 탐색한다면
        // □□□      □□□     □□□
        // □□□      □□□     □■□
        // □□□      □■□     ■■■
        // □■□      ■■■     ■■■ 과 같이 삼각형 모양을 확장해나가는 형태로 탐색하는 것이 좋다.
        // 물론 순서는 왼쪽 아래서부터, 위로 올라갔다가, 다시 오른쪽 아래로 내려가야 왼쪽에 있는 적을 우선 탐색한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자와 사거리 d
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        // 적들의 상태
        map = new int[n][m];
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 잡을 수 있는 최대 적의 수
        int answer = 0;
        // i, j, k 위치에 궁수를 배치한다.
        for (int i = 0; i < m - 2; i++) {
            for (int j = i + 1; j < m - 1; j++) {
                for (int k = j + 1; k < m; k++)
                    answer = Math.max(answer, countMax(((1 << i) | (1 << j) | (1 << k))));
            }
        }
        // 답 출력
        System.out.println(answer);
    }

    // 현재 궁수들의 배치로 잡을 수 있는 최대 적의 수를 계산한다.
    static int countMax(int archers) {
        // 최대 적의 수
        int count = 0;
        // 해당 적을 잡은 턴을 기록한다.
        int[][] attackTurn = new int[n][m];
        // 현재 턴
        int turn = 1;
        // 모든 적이 성에 도달하면 더 이상 계산할 필요가 없다
        // 따라서 n번 동안 반복하면, 가장 마지막 줄에 있던 적들도 성에 도달하게 되므로
        // 해당 시점까지 계산
        while (turn <= n) {
            for (int i = 0; i < m; i++) {
                // i번 위치에 궁수가 배치되었다면 
                if ((archers & (1 << i)) != 0) {
                    // 사거리 내에 가장 가까우면서 왼쪽에 있는 적을 타겟으로 설정한다.
                    boolean found = false;
                    // 가까운 것이 우선이므로 첫번째 조건은 거리
                    for (int distance = 1; distance <= d; distance++) {
                        // 해당 거리일 때, 가장 왼쪽 아래의 점의 위치부터 가장 오른쪽 아래의 점까지를 탐색ㅎ
                        for (int c = Math.max(i - (distance - 1), 0); c < Math.min(i + (distance - 1) + 1, m); c++) {
                            // 그 때의 r값
                            // 턴마다 적이 가까워짐을 보정해준다.
                            int r = n - turn - (distance - 1 - Math.abs(i - c));
                            // 해당 위치가 범위를 벗어나지 않으며
                            // 아직 적이 쓰러지지 않았거나 이번 턴에 다른 궁수와 같이 쏘게 된다면
                            if (r >= 0 && map[r][c] == 1 && (attackTurn[r][c] == 0 || attackTurn[r][c] == turn)) {
                                // 쓰러지지 않은 경우
                                if (attackTurn[r][c] == 0) {
                                    // 해당 턴을 기록하고, count 증가
                                    attackTurn[r][c] = turn;
                                    count++;
                                }
                                // 쓰러지지 않았건, 다른 궁수가 동시에 쏘건
                                // 일단 타겟은 설정되었다.
                                found = true;
                                // 해당 거리에서의 반복문 종료
                                break;
                            }
                        }
                        // 타겟 설정을 했다면 반복문 종료
                        if (found)
                            break;
                    }
                }
            }
            // 턴 증가
            turn++;
        }
        // 총 쓰러뜨린 적의 수 반환
        return count;
    }
}