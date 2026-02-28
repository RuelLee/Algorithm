/*
 Author : Ruel
 Problem : Baekjoon 17834번 사자와 토끼
 Problem address : https://www.acmicpc.net/problem/17834
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17834_사자와토끼;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수풀과 m개의 오솔길이 주어진다.
        // 토끼와 사자의 위치를 정한다.
        // 각 턴마다 오솔길을 따라 연결된 수풀로 이동한다.
        // 한 수풀에서 토끼와 사자가 만나면 게임이 끝난다.
        // 서로 같은 오솔길에서 만나 반대 반향으로 진행하더라도 게임이 끝나지 않는다.
        // 게임이 끝나지 않는 사자와 토끼의 배치의 개수를 구하라
        //
        // BFS 문제
        // 1번 수풀에서 시작하여, 다른 수풀에 도달하는 턴이 홀수인지 짝수인지 혹은 둘 다 가능한지를 구한다.
        // 홀수 턴에 도착할 수 있는 곳에 토끼, 짝수 턴에 도달할 수 있는 곳에 사자, 혹은 둘을 바꿔 배치한다면
        // 사자와 토끼는 영원히 만날 수 없다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 수풀, m개의 오솔길
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 오솔길 정보
        List<List<Integer>> roads = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            roads.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            roads.get(u).add(v);
            roads.get(v).add(u);
        }

        // 각 수풀에 1번 수풀로부터 홀수번째 혹은 짝수번째에 도달할 수 있는지 여부
        // 1 -> 짝수 번째 2^0, 2 -> 홀수 번째 2^1 , 3 -> 모두
        int[] dp = new int[n + 1];
        // 1번 오솔길은 홀수번째
        dp[1] = 1;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{1, 0});
        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            for (int next : roads.get(current[0])) {
                // 다음 턴의 홀짝
                int nextTurn = (current[1] + 1) % 2;
                // 다음 턴에 nextTurn으로 처음 도착한다면
                // 큐에 추가 후, dp에 기록
                if ((dp[next] & (1 << nextTurn)) == 0) {
                    dp[next] |= (1 << nextTurn);
                    queue.offer(new int[]{next, nextTurn});
                }
            }
        }

        // 1과 2의 개수를 센다.
        int[] counts = new int[4];
        for (int i = 1; i <= n; i++)
            counts[dp[i]]++;

        // 1의 개수 * 2의 개수 * 2(둘이 자리를 바꾸는 경우)
        System.out.println(counts[1] * counts[2] * 2);
    }
}