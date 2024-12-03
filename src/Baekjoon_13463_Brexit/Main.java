/*
 Author : Ruel
 Problem : Baekjoon 13463번 Brexit
 Problem address : https://www.acmicpc.net/problem/13463
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13463_Brexit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // c개의 국가가 연합하고 있다.
        // 국가 간에 총 p개의 파트너쉽이 있고, 자신의 파트너 국가들 중 절반 이상이 연합을 탈퇴한다면
        // 해당 국가 또한 연합을 탈퇴한다고 한다.
        // 가장 먼저 l번째 국가가 연합을 탈퇴한다고 할 때
        // x번째 국가가, 최종적으로 연합에 남아있는지 알고 싶다.
        // 남아있다면 stay, 탈퇴한다면 leave를 출력하라
        //
        // BFS 문제
        // l부터 시작하여, 파트너쉽을 살펴보며 국가들을 탐색하며 연합에서 탈퇴 혹은 남기면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // c개의 국가, p개의 파트너쉽, 최종 연합 잔존 확인 국가 x, 최초 탈퇴 국가 l
        int c = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        
        // 파트너쉽
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < c + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            connections.get(a).add(b);
            connections.get(b).add(a);
        }
        
        // 탈퇴 여부
        boolean[] left = new boolean[c + 1];
        // 큐에 있는지 확인
        boolean[] enqueued = new boolean[c + 1];
        // l은 탈퇴
        left[l] = true;
        Queue<Integer> queue = new LinkedList<>();
        // l과 파트너쉽을 맺은 국가들을 확인해야한다.
        for (int country : connections.get(l)) {
            queue.offer(country);
            enqueued[country] = true;
        }
        
        while (!queue.isEmpty()) {
            // 확인 국가
            int country = queue.poll();
            // 큐에서 꺼냄
            enqueued[country] = false;
            
            // 파트너쉽들 중 탈퇴한 국가의 수 계산
            int leftCount = 0;
            for (int partner : connections.get(country)) {
                if (left[partner])
                    leftCount++;
            }
            
            // 파트너쉽들 중 탈퇴한 국가의 수가 전체 파트너쉽의 반 이상일 경우
            if (leftCount * 2 >= connections.get(country).size()) {
                // country도 탈퇴.
                left[country] = true;

                // country와 파트너쉽을 맺은 국가들 중
                // 아직 연합을 탈퇴하지 않았고, 큐에 들어있지 않은 국가들을
                // 큐에 넣는다.
                for (int partner : connections.get(country)) {
                    if (!left[partner] && !enqueued[partner]) {
                        queue.offer(partner);
                        enqueued[partner] = true;
                    }
                }
            }
        }
        
        // 최종적으로 x가 연합 남았는지 여부 출력
        System.out.println(left[x] ? "leave" : "stay");
    }
}