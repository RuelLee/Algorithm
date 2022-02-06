/*
 Author : Ruel
 Problem : Baekjoon 6086번 최대 유량
 Problem address : https://www.acmicpc.net/problem/6086
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6086_최대유량;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] capacity;
    static int[][] flows;
    static final int SIZE = 'z' - 'A' + 1;          // 대소문자가 모두 등장하므로 전체를 아우를 수 있는 크기로 지정.

    public static void main(String[] args) throws IOException {
        // 오랜만에 다시 풀어본 네트워크 유량 문제
        // 하나의 파이프로 물을 흘렸을 때, 반대방향으로 음의 유량이 흐른다고 표시해주는게 중요하다
        // 만약 양쪽으로 같은 유량이 흐른다면, 유량이 흐르지 않더라도 같은 결과가 되기 때문(쓸데없이 파이프만 사용하고 있는 경우)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        capacity = new int[SIZE][SIZE];     // 파이프의 용량
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = st.nextToken().charAt(0) - 'A';
            int b = st.nextToken().charAt(0) - 'A';
            int c = Integer.parseInt(st.nextToken());
            capacity[a][b] += c;        // 시작점과 도착점이 같지만 다른 파이프일 수 있다. 차곡차곡 더해주자.
            capacity[b][a] += c;        // 파이프는 양방향.
        }
        flows = new int[SIZE][SIZE];        // 파이프에 흐르는 유량.

        int sum = 0;
        while (true) {
            int newFlow = fordFulkerson(0, SIZE * 1000, new boolean[SIZE]);     // 시작점은 0, 아무리 많은 유량이 흘러도, 파이프 크기 * 1000만큼, 각 지점에 대한 방문체크용 visited 배열.
            if (newFlow == 0)       // 더 이상 흐를 유량이 없다면 종료
                break;
            else        // 새로 추가된 유량을 더함.
                sum += newFlow;
        }
        System.out.println(sum);
    }

    static int fordFulkerson(int n, int flow, boolean[] visited) {
        if ('Z' - 'A' == n)     // 'Z' 지점에 도착했다면, 현재 flow를 반환.
            return flow;

        visited[n] = true;      // 방문 체크.
        int newFlowsSum = 0;
        for (int i = 0; i < flows[n].length; i++) {
            if (!visited[i] && capacity[n][i] - flows[n][i] > 0) {      // i가 방문하지 않았고, 파이프의 용량보다 유량이 적어 더 흐를 가능성이 있다면
                int nextFlow = fordFulkerson(i, Math.min(flow, capacity[n][i] - flows[n][i]), visited);     // i로 flow와 잔여용량 중 작은 만큼을 흘려본다.
                flows[n][i] += nextFlow;        // nextFlow만큼 더 흘릴 수 있다면 n -> i에는 nextFlow를 더하고
                flows[i][n] -= nextFlow;        // i -> n으로는 -nextFlow 만큼을 빼준다
                newFlowsSum += nextFlow;        // 새로운 유량으로 더해주고,
                flow -= nextFlow;           // n에서 흘릴 수 있는 flow에선 nextFlow만큼 빼준다.
            }
        }
        // 이번 계산으로 새로 찾은 newFlowSum만큼의 유량을 반환한다.
        return newFlowsSum;
    }
}