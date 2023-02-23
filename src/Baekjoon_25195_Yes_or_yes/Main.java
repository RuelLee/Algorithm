/*
 Author : Ruel
 Problem : Baekjoon 25195번 Yes or yes
 Problem address : https://www.acmicpc.net/problem/25195
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25195_Yes_or_yes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// 현재 위치와 지나온 경로에서
// 팬클럽을 만났는지 여부를 변수로 갖는 클래스를 만든다.
class State {
    int loc;
    boolean metFan;

    public State(int loc, boolean metFan) {
        this.loc = loc;
        this.metFan = metFan;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정점, m개의 간선으로 이루어진 사이클 없는 방향 그래프가 주어진다.
        // 이 중 몇몇 정점에서는 팬클럽들이 숨어있다.
        // 1번 정점에서 시작해서, 더 이상 이동할 수 없을 때까지 간선을 통해 이동한다.
        // 그러한 경로들 중 팬클럽을 항상 만나는 경우에는 Yes, 하나의 경로라도 팬클럽을 만나지 않는다면 yes를 출력한다
        //
        // 그래프 탐색 문제
        // 모든 경로에 대해 탐색을 하고, 더 이상 진행할 수 없는 정점에 도달했을 때
        // 지나온 경로에서 팬클럽을 만났는지 여부를 확인해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 간선들
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            edges.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            edges.get(u).add(v);
        }
        
        // 팬 클럽의 개수
        int s = Integer.parseInt(br.readLine());
        // 위치
        boolean[] fanClub = new boolean[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < s; i++)
            fanClub[Integer.parseInt(st.nextToken())] = true;

        // 모든 경로에서 팬클럽을 만나는가
        boolean alwaysMeetFanClub = true;
        // BFS를 통해 탐색한다.
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(1, fanClub[1]));
        while (!queue.isEmpty()) {
            // 현재 위치
            State current = queue.poll();
            // 더 이상 이동 가능한 정점이 없고
            // 지나온 경로에서 팬클럽을 만난 적이 없다면
            if (edges.get(current.loc).isEmpty() && !current.metFan) {
                // alwaysMeetFanClub 값을 false로 만들고 BFS 탐색을 종료한다.
                alwaysMeetFanClub = false;
                break;
            }

            // current에서 진행 가능한 정점들을 탐색한다.
            for (int next : edges.get(current.loc))
                queue.offer(new State(next, current.metFan || fanClub[next]));
        }

        // 팬클럽을 만나지 않는 경로가 존재한다면 yes
        // 모든 경로에서 팬클럽을 만난다면 Yes를 출력한다.
        System.out.println(alwaysMeetFanClub ? "Yes" : "yes");
    }
}