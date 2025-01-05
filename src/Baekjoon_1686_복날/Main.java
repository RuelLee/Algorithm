/*
 Author : Ruel
 Problem : Baekjoon 1686번 복날
 Problem address : https://www.acmicpc.net/problem/1686
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1686_복날;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Loc {
    double r;
    double c;

    public Loc(double r, double c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static long v, m;
    static List<Loc> bunkers;

    public static void main(String[] args) throws IOException {
        // 닭이 도망치려고 한다.
        // 닭은 v m/s으로 이동할 수 있고, m분 이상 벙커 밖을 돌아다닌다면 잡힌다.
        // 출발지와 도착지 그리고 중간에 있는 벙커들의 위치가 주어질 때
        // 도착지로 도달할 수 있다면, 거쳐야하는 최소 벙커의 수를 Yes, visiting n other holes. 같은 형식으로 출력하고
        // 불가능하다면 No.을 출력한다.
        //
        // BFS 문제
        // BFS인데, 거리 계산을 살짝 곁들인 문제.
        // 그냥 출발지에서 다른 벙커들까지를 BFS를 통해 탐색하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 속도와 외부에서 있을 수 있는 분
        v = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 출발지
        Loc start = new Loc(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
        st = new StringTokenizer(br.readLine());
        // 도착지
        Loc end = new Loc(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
        
        // 벙커들
        bunkers = new ArrayList<>();
        // 계산상 편의를 위해 첫번째 벙커를 출발지
        bunkers.add(start);
        String input = br.readLine();
        while (input != null) {
            st = new StringTokenizer(input);
            bunkers.add(new Loc(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken())));
            input = br.readLine();
        }
        // 마지막 벙커를 도착지로 한다.
        bunkers.add(end);
        
        // BFS
        // 값 초기화
        int[] dp = new int[bunkers.size()];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int i = 0; i < bunkers.size(); i++) {
                if (dp[i] > dp[current] + 1 && possible(current, i)) {
                    dp[i] = dp[current] + 1;
                    queue.offer(i);
                }
            }
        }
        // 결과 출력
        System.out.println(dp[dp.length - 1] == Integer.MAX_VALUE ? "No" : "Yes, visiting " + (dp[dp.length - 1] - 1) + " other holes.");
    }
    
    // from 벙커에서 to 벙커로 이동 가능한지 계산한다.
    static boolean possible(int from, int to) {
        // 거리
        double distance = Math.sqrt(Math.pow(bunkers.get(from).r - bunkers.get(to).r, 2) +
                Math.pow(bunkers.get(from).c - bunkers.get(to).c, 2));
        // 거리가 m * 60 * v보다 같거나 작아야 이동이 가능
        return distance <= m * 60 * v;
    }
}