/*
 Author : Ruel
 Problem : Baekjoon 1619번 두 동전
 Problem address : https://www.acmicpc.net/problem/16197
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16197_두동전;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int n, m;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 보드가 주어진다.
        // 보드 위에는 두 개의 동전이 있으며, 빈 공간과 벽이 존재한다.
        // 각 턴마다 상하좌우 중 하나를 골라 이동할 수 있으며
        // 두 동전 모두에게 같은 방향이 입력된다.
        // 방향에 따라 보드 밖으로 동전이 나갈 수 있으며
        // 두 동전이 연속하여 있더라도 같이 이동할 수 있다면 이동한다.
        // 벽에 막힌 경우는 이동하지 않는다.
        // 두 동전 중 하나만 떨어뜨리고자 할 때, 최소 턴 수는?
        // 10턴보다 많은 턴 수를 요구하거나 불가능한 경우는 -1을 출력한다.
        //
        // 시뮬레이션, 브루트포스, BFS 문제
        // 동전이 두 개밖에 되지 않고, 턴 또한 10턴으로 제한 적이므로
        // 각 턴마다 네 방향을 모두 입력하며 모든 결과를 뽑는다.
        // 동전의 위치에 따른 중복 제거는 할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 보드
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        // 초기 상태
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();

        // 두 동전의 위치
        int[] coins = new int[]{-1, -1};
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'o')
                    coins[coins[0] == -1 ? 0 : 1] = i * m + j;
            }
        }

        // 턴마다 큐를 달리하여 번갈아면서 사용
        Queue<int[]>[] queues = new Queue[2];
        for (int i = 0; i < queues.length; i++)
            queues[i] = new LinkedList<>();
        // 처음 상태
        queues[0].offer(coins.clone());

        int turn = 0;
        // 하나의 동전만 떨어뜨리는 경우를 찾았는지 여부
        boolean found = false;
        // 10턴까지만 진행
        while (turn < 11 && !found) {
            // 중복되는 동전의 상태를 해쉬셋으로 관리
            HashSet<Integer> hashSet = new HashSet<>();
            while (!queues[turn % 2].isEmpty() && !found) {
                // 현재 두 동전의 상태
                int[] current = queues[turn % 2].poll();

                // 네 방향으로 두 동전을 굴려본다.
                for (int d = 0; d < 4 && !found; d++) {
                    int[] temp = new int[2];
                    for (int i = 0; i < coins.length; i++) {
                        // i번째 동전의 위치
                        int nextR = current[i] / m + dr[d];
                        int nextC = current[i] % m + dc[d];

                        // 맵의 범위를 벗어난 경우
                        if (!checkArea(nextR, nextC))
                            temp[i] = -1;
                        // 벽에 막힌 경우
                        else if (map[nextR][nextC] == '#')
                            temp[i] = current[i];
                        else        // 그 외의 경우
                            temp[i] = nextR * m + nextC;
                    }
                    // 정렬
                    Arrays.sort(temp);

                    // 하나의 동전만 떨어진 경우, 원하는 답을 찾음.
                    if ((temp[0] == -1 || temp[1] == -1) && temp[0] + temp[1] != -2)
                        found = true;
                    // 그 외의 경우. 두 동전 모두 떨어지지 않았고, 중복되는 경우가 아닌 경우
                    else if (temp[0] != temp[1] && !hashSet.contains(temp[0] * 500 + temp[1])) {
                        queues[(turn + 1) % 2].offer(temp);
                        hashSet.add(temp[0] * 500 + temp[1]);
                    }
                }
            }
            // 턴 증가
            turn++;
        }
        // 턴이 10 이하인 경우, 해당 턴 출력. 그 외의 경우 10턴보다 크거나 불가능한 경우이므로 -1 출력
        System.out.println(turn <= 10 ? turn : -1);
    }

    // 동전의 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}