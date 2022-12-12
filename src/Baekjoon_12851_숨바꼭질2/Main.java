/*
 Author : Ruel
 Problem : Baekjoon 12851번 숨바꼭질 2
 Problem address : https://www.acmicpc.net/problem/12851
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12851_숨바꼭질2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수빈이는 n, 동생은 k 점에 있다.
        // 수빈이는 매 초, -1, +1, *2의 위치로 움직일 수 있다고 한다.
        // 동생의 위치까지 가는 가장 빠른 시간과 찾아가는 방법이 몇 가지인지 출력하라
        //
        // BFS 문제
        // n으로부터 모든 위치에 대해 탐색을 한다.
        // n과 k가 최대 10만까지 주어졌지만, 10만보다 더 큰 위치에서 -1로 되돌아오는 경우가
        // 더 빠른 경우가 생길 수 있음에 유의하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력 처리.
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // n이 k보다 같거나 큰 위치라면 되돌아가는 방법밖에 없다.
        // 최대 크기는 n + 1로 설정하자
        // 그 외의 경우라면 n < k인데, n으로부터 *2를 한 후, -1로 k까지 가는 경우가 빠를 수 있다.
        // 따라서 이 때 크기는 k * 2 / 3 * 2값을 해주자.
        // k의 2/3 지점에서 *2를 할 경우, k의 4/3 지점이 되는데, k까지의 거리가 1/3 * k로 같기 때문이다.
        int limit = (n >= k ? n + 1 : (int) Math.ceil((double) 2 * k / 3) * 2);
        // 도착하는 최소 시간
        int[] minSecs = new int[limit];
        Arrays.fill(minSecs, Integer.MAX_VALUE);
        minSecs[n] = 0;
        // 최소 시간으로 도달하는 경우의 수
        int[] counts = new int[limit];
        counts[n] = 1;

        // n으로부터 BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n);
        boolean[] visited = new boolean[limit];
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 방문했다면 건너 뛴다.
            if (visited[current])
                continue;

            // +1 이동을 하는 경우.
            if (current + 1 < minSecs.length) {
                if (minSecs[current + 1] == minSecs[current] + 1)
                    counts[current + 1] += counts[current];
                else if (minSecs[current + 1] > minSecs[current] + 1) {
                    minSecs[current + 1] = minSecs[current] + 1;
                    counts[current + 1] = counts[current];
                    queue.offer(current + 1);
                }
            }

            // -1 이동을 하는 경우.
            if (current - 1 >= 0) {
                if (minSecs[current - 1] == minSecs[current] + 1)
                    counts[current - 1] += counts[current];
                else if (minSecs[current - 1] > minSecs[current] + 1) {
                    minSecs[current - 1] = minSecs[current] + 1;
                    counts[current - 1] = counts[current];
                    queue.offer(current - 1);
                }
            }

            // *2 이동을 하는 경우.
            if (current * 2 < minSecs.length) {
                if (minSecs[current * 2] == minSecs[current] + 1)
                    counts[current * 2] += counts[current];
                else if (minSecs[current * 2] > minSecs[current] + 1) {
                    minSecs[current * 2] = minSecs[current] + 1;
                    counts[current * 2] = counts[current];
                    queue.offer(current * 2);
                }
            }
            // 방문 체크
            visited[current] = true;
        }

        // k지점에 도달하는 최소 시간과 경우의 수를 출력한다.
        System.out.println(minSecs[k]);
        System.out.println(counts[k]);
    }
}