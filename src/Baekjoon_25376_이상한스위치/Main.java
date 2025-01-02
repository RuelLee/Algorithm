/*
 Author : Ruel
 Problem : Baekjoon 25376번 이상한 스위치
 Problem address : https://www.acmicpc.net/problem/25376
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25376_이상한스위치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 스위치가 주어지며, 각각의 스위치 상태가 주어진다.
        // 몇몇 스위치들은 연결되어있어, 한 스위치를 '직접' 켠다면, 연결된 스위치들의 상태가 반전된다.
        // 여러분들은 꺼져있는 스위치만 켤 수 있다할 때
        // 모든 스위치를 켜는데 필요한 행동의 수를 출력하라
        // 불가능하다면 -1을 출력한다
        //
        // 비트마스킹, BFS 문제
        // 스위치가 최대 20개로 주어지므로, int 범위 내의 비트마스킹으로 처리가 가능하다.
        // 처음 상태와 스위치를 누름에 따라 반전되는 상태들을 비트마스킹 처리해주고
        // 모든 상태에 대해 BFS 탐색해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 스위치
        int n = Integer.parseInt(br.readLine());
        // 처음 스위치의 상태
        int originalBitmask = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            if (Integer.parseInt(st.nextToken()) == 1)
                originalBitmask |= 1 << i;
        }

        // 각 스위치를 누르면 바뀌는 스위치들에 대한 정보
        int[] switches = new int[n];
        for (int i = 0; i < switches.length; i++) {
            // i번 스위치를 직접 누르므로, i번 스위치에 상태가 바뀌는 건 당연.
            switches[i] |= 1 << i;

            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            // 연결된 스위치들도 표시
            for (int j = 0; j < num; j++)
                switches[i] |= 1 << (Integer.parseInt(st.nextToken()) - 1);
        }

        // BFS 탐색
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 처음 상태
        dp[originalBitmask] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(originalBitmask);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // 꺼진 스위치만 켤 수 있으므로
            for (int i = 0; i < n; i++) {
                // 꺼져있는 스위치를 찾고
                if ((current & (1 << i)) == 0) {
                    int next = (current | switches[i]) - (current & switches[i]);
                    // 해당 스위치를 눌렀을 때, 생기는 상태에 도달하는 행동 횟수가
                    // 최소값을 갱신하는지 확인하고
                    // 그렇다면 dp에 표시 후, 큐에 추가.
                    if (dp[next] > dp[current] + 1) {
                        dp[next] = dp[current] + 1;
                        queue.offer(next);
                    }
                }
            }
        }

        // 모든 스위치가 켜진 상태
        int allOnBitmask = (1 << n) - 1;
        // 모든 스위치를 켤 수 있다면 그 행동의 최소 횟수, 그렇지 않다면 -1을 출력
        System.out.println(dp[allOnBitmask] == Integer.MAX_VALUE ? -1 : dp[allOnBitmask]);
    }
}
