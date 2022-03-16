/*
 Author : Ruel
 Problem : Baekjoon 11377번 열혈강호 3
 Problem address : https://www.acmicpc.net/problem/11377
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11377_열혈강호3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] capacities;
    static int[][] flows;
    static int source, nSource, kSource, sink;

    public static void main(String[] args) throws IOException {
        // n명의 사람과 m개의 일이 주어진다.
        // 각 사람은 1개 일을 할 수 있지만, k명에 한해서는 2개의 일을 할 수 있다.
        // 처리할 수 있는 총 일의 몇 개인가
        //
        // 이분 매칭 내지는 최대 유량 문제
        //        ┌ nSource (n)
        // source ┤
        //        └ kSource (k)
        // 이렇게 큰 소스에서 n소스와 k소스로 나눠서 흘려보내자
        // 그리고 nSoruce,kSource에서 각각의 사람에게 1씩의 용량을 갖게하자
        // 이렇게 용량을 설정해주면, nSource에서 하나씩 가능한 모든 매칭이 이뤄질 것이고
        // kSoruce에서 최대 k명에 대해서 1씩의 일이 추가될 것이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        source = 0;     // 전체 큰 소스는 0번
        nSource = n + m + 1;        // n 소스는 마지막 - 2번
        kSource = n + m + 2;        // k 소스는 마지막 - 1번
        sink = n + m + 3;           // sink는 마지막 번호

        capacities = new int[n + m + 4][n + m + 4];     // 전체 용량
        flows = new int[n + m + 4][n + m + 4];      // 흐름
        capacities[source][nSource] = n;        // source에서 nSource로 n만큼의 용량을 갖게 한다.
        capacities[source][kSource] = k;        // soruce에서 kSource로 k만큼의 용량을 갖게 한다.
        for (int i = 1; i < n + 1; i++) {       // 그 후 각 소스에서 사람에게 1만큼의 용량을 할당한다.
            capacities[nSource][i] = 1;
            capacities[kSource][i] = 1;
        }
        for (int i = n + 1; i < n + m + 1; i++)     // 각 과제에서 sink로 흘려보낼 수 있는 용량은 1이다.
            capacities[i][sink] = 1;

        for (int i = 0; i < n; i++) {       // 각 사람이 할 수 있는 일을 표시한다.
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for (int j = 0; j < num; j++)
                capacities[i + 1][Integer.parseInt(st.nextToken()) + n] = 1;
        }
        int sum = 0;
        while (true) {
            int newFlow = fordFulkerson(source, Integer.MAX_VALUE, new boolean[capacities.length]);     // 소스에서 물을 흘려보냈을 때 생기는 새로운 흐름
            if (newFlow == 0)       // newFlow가 0이라면 더 이상의 과제 할당이 불가능한 경우. break.
                break;
            sum += newFlow;     // 그렇지 않다면 newFlow를 sum에 더해 전체 흐름을 세어가자.
        }
        System.out.println(sum);
    }

    static int fordFulkerson(int n, int flow, boolean[] visited) {      // 포드 풀커슨 알고리즘
        if (n == sink)      // sink에 도달했다면 현재 flow를 리턴.
            return flow;

        visited[n] = true;      // 방문 체크
        int income = flow;      // 원래 처음 들어온 흐름의 양
        for (int i = 0; i < capacities.length; i++) {
            if (flow == 0)      // flow가 0이 됐다면 더 이상 계산 x
                break;

            if (!visited[i] && capacities[n][i] - flows[n][i] > 0) {        // 방문하지 않은 i에 대해 아직 용량 대비 흐름을 봤을 때 여유가 있다면
                // n에서 i로 흐름을 증가시켜본다.
                // 이 때 콜하는 함수의 매개변수 flow는, 남아있는 flow와, 용량 대비 흐름의 여유분 중 작은 값으로 보낸다.
                int newFlow = fordFulkerson(i, Math.min(flow, capacities[n][i] - flows[n][i]), visited);
                if (newFlow > 0) {      // 새로운 흐름이 발생했다면
                    flow -= newFlow;        // 새로운 흐름 만큼을 현재 flow에서 빼주고
                    flows[n][i] += newFlow;     // n -> i 유량 체크
                    flows[i][n] -= newFlow;     // i -> n 음의 유량 체크
                }
            }
        }
        // 최종적으로 흘려보낸 만큼의 flow를 리턴한다.
        return income - flow;
    }
}