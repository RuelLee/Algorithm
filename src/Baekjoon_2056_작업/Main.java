/*
 Author : Ruel
 Problem : Baekjoon 2056번 작업
 Problem address : https://www.acmicpc.net/problem/2056
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2056_작업;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 작업이 주어진다
        // 각 작업은 작업에 소요되는 시간과, 해당 작업을 하기 위해 선행되어야하는 작업들이 주어진다
        // 서로 연관이 없는 작업들은 동시에 진행하는 것이 가능하다할 때, 모든 작업을 완료하는데 드는 시간은 얼마인가
        //
        // 작업들 간의 선행 관계가 있으므로 위상 정렬로 풀 수 있는 문제다
        // 선행 관계가 없는 작업들은 먼저 queue에 담아 우선적으로 실행시킨다
        // 그러면서 자신의 후행 작업들의 진입차수를 하나씩 남겨주며, 후행 작업이 실행될 수 있는 가장 이른 시간을
        // 선행 작업들의 완료 시간들 중 최대값으로 갱신해나간다.
        // 최종적으로 가장 늦은 완료 시간을 출력해준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 후행 작업들을 담아준다.
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());

        int[] works = new int[n + 1];       // 해당 작업의 소요 시간
        int[] finishTime = new int[n + 1];      // 해당 작업이 완료된 시간
        int[] inDegree = new int[n + 1];        // 진입 차수.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < n + 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            works[i] = Integer.parseInt(st.nextToken());

            inDegree[i] = Integer.parseInt(st.nextToken());     // 진입 차수가 바로 주어진다.
            if (inDegree[i] == 0)       // 진입차수가 0이라면 바로 실행될 수 있으므로, 큐에 바로 담아준다.
                queue.offer(i);
            for (int j = 0; j < inDegree[i]; j++)
                connections.get(Integer.parseInt(st.nextToken())).add(i);
        }

        // 큐에 있는 작업들을 실행하며, 진입 차수가 0이 되는 작업들도 큐에 담아 실행한다.
        while (!queue.isEmpty()) {
            int current = queue.poll();     // 이번에 실행된 작업.
            // finishTime[current]에는 current 작업이 시작되는 시간이 기록되어있다
            // 여기에 current 작업의 소요 시간을 더해 완료 시간으로 바꿔준다.
            finishTime[current] += works[current];

            // 후행 작업들을 살펴본다.
            for (int nextWork : connections.get(current)) {
                // nextWork가 시작될 수 있는 시간은 최소 current 작업이 완료된 이후에 할 수 있다
                // 이보다 적은 값을 갖고 있다면, finishTime[current]로 갱신해주자.
                finishTime[nextWork] = Math.max(finishTime[nextWork], finishTime[current]);
                // nextWork의 진입 차수가 0이 되었다면 큐에 담는다.
                if (--inDegree[nextWork] == 0)
                    queue.offer(nextWork);
            }
        }
        // finishTime에 담긴 시간들 중 가장 큰 값을 출력한다.
        System.out.println(Arrays.stream(finishTime).max().getAsInt());
    }
}