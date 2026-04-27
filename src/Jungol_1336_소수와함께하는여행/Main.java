/*
 Author : Ruel
 Problem : Jungol 1336번 소수와 함께 하는 여행
 Problem address : https://jungol.co.kr/problem/1336
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1336_소수와함께하는여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 네 자리의 소수로 이루어진 버스 노선이 주어진다.
        // 각 버스 노선은 서로의 네 개의 수 중 하나만 다른 경우에 갈아탈 수 있다.
        // 시작 노선과 도착 노선이 주어질 때, 최소 몇 번을 갈아타야 원하는 노선으로 갈아탈 수 있는가?
        //
        // 에라토스테네스의 체, BFS 문제
        // 에라토스테네스의 체로 4자리 소수들을 모두 구한다.
        // 그 후, 시작 위치로부터 BFS를 통해 도착 위치까지 최소 환승 횟수를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 시작 노선과 도착 노선
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        // 소수
        boolean[] notPrimeNums = new boolean[10000];
        for (int i = 2; i < notPrimeNums.length; i++) {
            if (notPrimeNums[i])
                continue;
            for (int j = 2; i * j < notPrimeNums.length; j++)
                notPrimeNums[i * j] = true;
        }

        List<Integer> primeNums = new ArrayList<>();
        for (int i = 1000; i < notPrimeNums.length; i++) {
            if (!notPrimeNums[i])
                primeNums.add(i);
        }

        // 각 노선에 도달하는 최소 환승 횟수
        int[] transfer = new int[10000];
        Arrays.fill(transfer, Integer.MAX_VALUE);
        // 시작 노선
        transfer[s] = 0;
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // 다음 노선
            for (int next : primeNums) {
                // 갈아타는 것이 가능하고, 최소 환승 횟수를 갱신하는 경우
                if (canGo(current, next) && transfer[next] > transfer[current] + 1) {
                    // 값 갱신 및 큐 추가
                    transfer[next] = transfer[current] + 1;
                    queue.offer(next);
                }
            }
        }
        // 도착 노선에 도달하는 최소 환승 횟수 출력
        System.out.println(transfer[e]);
    }

    // a와 b가 서로 한 자리만 다른 소수인지 판별한다.
    static boolean canGo(int a, int b) {
        int notSame = 0;
        while (a > 0) {
            if (a % 10 != b % 10)
                notSame++;
            a /= 10;
            b /= 10;
        }
        return notSame <= 1;
    }
}