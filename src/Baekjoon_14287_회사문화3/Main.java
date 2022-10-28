/*
 Author : Ruel
 Problem : Baekjoon 14287번 회사 문화 3
 Problem address : https://www.acmicpc.net/problem/14287
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14287_회사문화3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어느 회사에는 직속 상사를 칭찬하는 문화가 있다.
        // 상사가 칭찬을 받으면 자신의 직속 상사도 칭찬을 한다.
        // 상사는 부하 직원보다 작은 번호를 갖는다.
        // 회사에는 n명의 직원이 있고, m개의 쿼리가 주어진다.
        // 1 i w : i번째 직원이 부하 직원에게 w의 칭찬을 받는다. (1 ≤ i ≤ n, 1 ≤ w ≤ 1,000)
        // 2 i : i번째 직원이 받은 정도를 출력한다.
        //
        // 세그먼트 트리....? 문제?
        // 트리 형태로 직원 관계도가 짜이긴 하지만 하위가 아니라 상위로 접근하는 형태이기 떄문에
        // 코드가 간단해졌다.
        // 느린 전파와 같이 2번 쿼리가 나올 때까지 notYet이라는 배열에 전파되지 않은 칭찬을 저장해둔다.
        // 그 후, 2번 쿼리가 나온다면, 여태까지 누적된 notYet 배열의 칭찬들을 큰 순서대로 praises로 넘겨준다.
        // 그리고 해당하는 직원의 칭찬 누적값을 출력한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 자신의 직속 상사 번호.
        int[] superiors = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < superiors.length; i++)
            superiors[i] = Integer.parseInt(st.nextToken());
        // 누적된 칭찬들.
        int[] praises = new int[n + 1];
        // 아직 전파되지 않은 칭찬.
        int[] notYet = new int[n + 1];
        // 2번 쿼리가 나올 때까지 위로 전파해야할 칭찬을 가진 직원들을 최대힙 우선순위큐에 담는다.
        PriorityQueue<Integer> propagation = new PriorityQueue<>(Comparator.reverseOrder());
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < m; j++) {
            st = new StringTokenizer(br.readLine());
            // 칭찬을 받는 경우.
            if (Integer.parseInt(st.nextToken()) == 1) {
                int i = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                // i번 직원에게 w 칭찬을 받는다.
                // notYet에 기록해두고, 우선순위큐에 담는다.
                notYet[i] += w;
                propagation.offer(i);
            } else {
                // 우선순위큐가 빌 때까지
                while (!propagation.isEmpty()) {
                    int current = propagation.poll();
                    // 전파할 칭찬이 없다면 건너뛴다.
                    if (notYet[current] == 0)
                        continue;

                    // 아직 전파되지 않은 칭찬을 자신에게도 더하고,
                    praises[current] += notYet[current];
                    // 자신의 직속 상사가 있다면
                    if (superiors[current] != -1) {
                        // 자신의 직속 상사에게도 미전파 칭찬을 누적시킨다.
                        notYet[superiors[current]] += notYet[current];
                        // 우선순위큐에 직속 상사를 담는다.
                        propagation.offer(superiors[current]);
                    }
                    // 자신의 미전파 칭찬을 없앤다.
                    notYet[current] = 0;
                }
                // 모든 미전파 칭찬을 전파시켰다면, i번 직원에 대해 칭찬 값을 StringBuilder에 기록.
                sb.append(praises[Integer.parseInt(st.nextToken())]).append("\n");
            }
        }
        // 전체 답안 출력.
        System.out.println(sb);
    }
}