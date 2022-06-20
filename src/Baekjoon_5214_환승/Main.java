/*
 Author : Ruel
 Problem : Baekjoon 5214번 환승
 Problem address : https://www.acmicpc.net/problem/5214
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5214_환승;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int LIMIT = 100_002;

    public static void main(String[] args) throws IOException {
        // 하이퍼튜브는 k개의 역을 서로 연결한다
        // n개의 역, k개의 한 튜브가 연결하는 역의 수, m개의 하이퍼튜브가 주어질 때
        // 1번역에서 n번역까지 도달하는 최소 경유 역의 수는 몇 개인가
        // 1 ≤ N ≤ 100,000, 1 ≤ K, M ≤ 1000
        //
        // BFS 문제이긴한데
        // 튜브가 1 : 1로 역을 연결하는 것이 아니라 여러개의 역을 동시에 연결하는 것이 재밌는 점이다
        // 가령 1 - 3 - 5가 한 튜브로 연결되어있다면, 1-3은 1과 3 두 역을 거치지는 것이라고 쉽게 알 수 있지만
        // 1-5 또한 1과 5 두 역만 거친다고 센다. (3을 건너뛰는 것 같다)
        // 따라서 한 역을 방문했을 때, 해당 역과 연결된 튜브들을 찾고, 튜브에 속한 역들을 모두 고려해야한다.
        // 하지만 이전에 한번 계산된 튜브는 더 적은 경유 역을 갖고 있을 것이므로(BFS니까) 방문체크를 통해 중복 연산을 막자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 튜브에 속한 역들을 저장한다.
        List<List<Integer>> tubes = new ArrayList<>(m);
        // 역에 연결된 튜브들을 저장한다.
        List<List<Integer>> stations = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            stations.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            tubes.add(new ArrayList<>(k));
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < k; j++) {
                int station = Integer.parseInt(st.nextToken());
                // 해당 역은 i번째 튜브에 연결되어있다.
                tubes.get(i).add(station);
                stations.get(station).add(i);
            }
        }

        // 각 역에 도달하는 최소 경유 역의 수.
        int[] minVia = new int[n + 1];
        Arrays.fill(minVia, LIMIT);
        // 시작 지점은 1번 역.
        minVia[1] = 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        // 튜브의 방문 체크. 한번 계산한 튜브는 다시 계산하지 않는다.
        boolean[] visitedTubes = new boolean[m];
        while (!queue.isEmpty()) {
            // 이번 역.
            int current = queue.poll();

            // current에 연결된 튜브들을 모두 살펴본다.
            for (int tube : stations.get(current)) {
                // 아직 계산하지 않은 튜브라면
                if (!visitedTubes[tube]) {
                    // 해당 튜브에 연결된 모든 역에 대해 살펴본다.
                    for (int nextStation : tubes.get(tube)) {
                        // nextStation의 최소 경유 역이 current를 통해 가는 경로로 갱신된다며
                        if (minVia[nextStation] > minVia[current] + 1) {
                            // 값 갱신
                            minVia[nextStation] = minVia[current] + 1;
                            // 큐 삽입
                            queue.offer(nextStation);
                        }
                    }
                    // 튜브 방문 체크.
                    visitedTubes[tube] = true;
                }
            }
        }
        // minVia[n]이 초기값을 갖고 있다면 도달이 불가능하므로 -1 출력.
        // 아니라면 최소 경유 역의 개수 출력.
        System.out.println(minVia[n] == LIMIT ? -1 : minVia[n]);
    }
}