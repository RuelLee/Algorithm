/*
 Author : Ruel
 Problem : Baekjoon 7512번 연속하는 소수의 합
 Problem address : https://www.acmicpc.net/problem/7512
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7512_연속하는소수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수가 m개 주어지며 각각을 ni라고 한다.
        // 모든 i에 대하여 연속하는 소수 ni개의 합으로 나타낼 수 있는
        // 가장 작은 소수를 찾아라
        // 테스트케이스는 여러개 주어진다.
        //
        // 에라토스테네스의 체, 슬라이딩 윈도우 문제
        // 정답이 10^7보다는 작으므로
        // 10^7 -1 까지는 에라토스테네스의 체로 소수임을 판별해야한다.
        // 그 후, 주어지는 ni들에 대하여 슬라이딩 윈도우를 통해
        // 소수 ni개의 합이 소수인 경우, 해당 값을 저장한다.
        // 그리고 모든 ni개로 표현할 수 있는 소수의 합을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        // 에라토스테네스의 체
        boolean[] notPrimeNumbers = new boolean[10000000];
        for (int i = 2; i < notPrimeNumbers.length; i++) {
            if (notPrimeNumbers[i])
                continue;
            for (int j = 2; i * j < notPrimeNumbers.length; j++)
                notPrimeNumbers[i * j] = true;
        }
        // 소수
        List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i < notPrimeNumbers.length; i++) {
            if (!notPrimeNumbers[i])
                primeNumbers.add(i);
        }

        for (int t = 0; t < testCase; t++) {
            // m개의 수
            int m = Integer.parseInt(br.readLine());
            int[] n = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // n[i]개의 소수의 합을 슬라이딩 윈도우를 통해 찾는다.
            List<List<Integer>> primeNumberSums = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                primeNumberSums.add(new ArrayList<>());
                // 합
                int sum = 0;
                // 큐에 해당하는 범위의 수들을 담는다.
                Queue<Integer> queue = new LinkedList<>();
                // 소수
                for (int p : primeNumbers) {
                    // sum에 p를 더하고 큐에 p추가
                    sum += p;
                    queue.offer(p);
                    // 큐에 들어있는 수가 n[i]개보다 많은 경우
                    // 해당하는 수만큼을 sum에서 빼고 큐에서도 제거한다.
                    while (!queue.isEmpty() && queue.size() > n[i])
                        sum -= queue.poll();

                    // 만약 합이 10^7을 넘어갔다면 답이 아니므로 더 이상 찾지 않는다.
                    if (sum >= 10000000)
                        break;

                    // 큐의 사이즈가 n[i]개이고, sum이 소수일 경우
                    // 해당하는 수를 리스트에 담는다.
                    if (queue.size() == n[i] && !notPrimeNumbers[sum])
                        primeNumberSums.get(i).add(sum);
                }
            }

            sb.append("Scenario ").append(t + 1).append(":").append("\n");
            // n0에 해 당하는 소수의 합이 소수인 수들을 모두 살펴본다.
            for (int s : primeNumberSums.get(0)) {
                boolean found = true;
                // 해당하는 s가 다른 ni들에 모두 포함되어있는지 확인.
                for (int i = 1; i < primeNumberSums.size(); i++) {
                    if (!primeNumberSums.get(i).contains(s)) {
                        found = false;
                        break;
                    }
                }
                // 모두 포함되어있다면 답을 찾은 경우
                // 답안 기록
                if (found) {
                    sb.append(s).append("\n").append("\n");
                    break;
                }
            }
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}