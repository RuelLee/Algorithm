/*
 Author : Ruel
 Problem : Baekjoon 20647번 Cowntagion
 Problem address : https://www.acmicpc.net/problem/20647
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20647_Cowntagion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 농장, 농장들을 잇는 n-1개의 도로가 주어진다.
        // 도로들로 통해 모든 농장들은 연결되어있다.
        // 1번 농장에서 질병이 발발했고, 이 때 날마다
        // 1. 한 농장에서 질병에 감염된 소가 2배가 된다.
        // 2. 농장에서 다른 농장으로 한 마리의 소가 이동한다.
        // 둘 중 하나의 행동을 한다.
        // 모든 농장에 최소 1마리의 감염된 소가 퍼지는 최소 시일을 구하라
        //
        // BFS 문제
        // 각 농장에서 자신과 연결된 감염된 소가 퍼지지 않은 농장의 수 + 1(현재 농장)의 수보다
        // 같거나 커질 때까지 1번 행동을 반복하며
        // 연결된 농장들로 2번 행동을 통해 1말씩 소를 보낸다.
        // 위 같은 과정을 반복하는데, 이를 BFS를 통해 구하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 농장
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st;
        // 연결 정보
        List<List<Integer>> lists = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            lists.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            lists.get(a).add(b);
            lists.get(b).add(a);
        }

        // 제곱을 미리 계산해둠
        int[] pows = new int[18];
        pows[0] = 1;
        for (int i = 1; i < pows.length; i++)
            pows[i] = pows[i - 1] * 2;
        
        // 방문 체크
        boolean[] visited = new boolean[n + 1];
        // 소요 시일
        int days = 0;
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            // 현재 소
            int count = 1;
            // 연결된 농장들 중 감염된 소가 퍼지지 않은 농장을 계산하고
            // 해당 농장으로 소를 보낸다
            for (int next : lists.get(current)) {
                if (!visited[next]) {
                    days++;
                    queue.offer(next);
                    count++;
                }
            }
            
            // 현재 농장에서 필요한 만큼의 감염된 소가 되기 위해
            // 1번 행동을 통해 소요되는 시일 계산
            for (int i = 0; i < pows.length; i++) {
                if (pows[i] >= count) {
                    days += i;
                    break;
                }
            }
            // 방문 체크
            visited[current] = true;
        }
        // 답 출력
        System.out.println(days);
    }
}