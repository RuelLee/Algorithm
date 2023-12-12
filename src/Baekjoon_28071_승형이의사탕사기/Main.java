/*
 Author : Ruel
 Problem : Baekjoon 28071번 승형이의 사탕 사기
 Problem address : https://www.acmicpc.net/problem/28071
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28071_승형이의사탕사기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // k명의 동생들을 위해 사탕을 사간다.
        // 사탕 가게에는 n종류의 사탕 상자가 있고, 최대 m개의 사탕 상자를 살 수 있다.
        // 사간 모든 사탕을 동생들이 모두 같게 나눠갖고자할 때
        // 그 때 사탕의 총 수는?
        //
        // BFS 문제
        // BFS를 통해 구매할 수 있는 모든 사탕의 수를 구하고
        // 그 중 k로 나눠떨어지는 가장 큰 값을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n 종류의 사탕박스
        int n = Integer.parseInt(st.nextToken());
        // 구매 가능한 m개의 사탕 박스 
        int m = Integer.parseInt(st.nextToken());
        // k명의 동생
        int k = Integer.parseInt(st.nextToken());

        // 각 사탕 박스에 든 사탕의 수
        int[] candies = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 처음엔 0개의 사탕
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        // 방문 체크
        boolean[] visited = new boolean[300 * 300 + 1];
        visited[0] = true;
        // 총 m번 사탕을 구매할 수 있다.
        // 사탕 상자의 종류는 매번 같으므로, 이미 한번 계산한 적 있는 사탕의 수라면
        // 더 이상 계산하지 않는다.
        for (int i = 0; i < m; i++) {
            // i+1 번째 상자를 구매하는 경우.
            Queue<Integer> nextQueue = new LinkedList<>();
            while (!queue.isEmpty()) {
                // i번째 상자까지 구매했을 때 가능한 사탕의 수.
                int current = queue.poll();
                
                for (int candy : candies) {
                    // 만약 이번의 사탕 수가 발견되지 않은 새로운 값이라면
                    if (!visited[current + candy]) {
                        // 방문체크
                        visited[current + candy] = true;
                        // i+2번째 상자를 구매할 때 사용하기 위해 nextQueue에 추가한다.
                        nextQueue.offer(current + candy);
                    }
                }
            }
            // queue에 있는 값을 모두 처리했다면
            // i+1번째 상자에 가능한 경우는 모두 처리.
            // 다음 상자로 넘어간다.
            // queue는 nextQueue로 대체
            queue = nextQueue;
        }

        // 큰 값에서부터 k로 나눠떨어지는 가장 큰 값을 찾고 출력 후, 종료한다.
        for (int i = visited.length - 1; i >= 0; i--) {
            if (visited[i] && i % k == 0) {
                System.out.println(i);
                break;
            }
        }
    }
}