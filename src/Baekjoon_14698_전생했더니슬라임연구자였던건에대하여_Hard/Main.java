/*
 Author : Ruel
 Problem : Baekjoon 14698번 전생했더니 슬라임 연구자였던 건에 대하여 (Hard)
 Problem address : https://www.acmicpc.net/problem/14698
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14698_전생했더니슬라임연구자였던건에대하여_Hard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다.
        // n개의 슬라임이 주어지며, 해당 슬라임을 하나의 슬라임으로 합치려고 한다.
        // 슬라임들을 합칠 때는 두 슬라임의 에너지의 곱만큼의 전기 에너지가 필요하다.
        // 각 슬라임을 합칠 때 필요한 전기 에너지들의 곱만큼의 비용이 청구된다고 할 때
        // 필요한 최소한의 비용은? 청구될 비용의 최솟값을 1,000,000,007로 나눈 나머지를 출력한다.
        //
        // 그리디 문제
        // n이 60이하, 처음 주어지는 슬라임은 2 이상, 2백경 이하로 주어진다.
        // 값 자체가 매우 크기 때문에 당연히 int는 물론 long의 범위도 넘어간다.
        // 따라서 값의 범위 예측이 중요했다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            long electricEnergy = 1;

            // n개의 슬라임.
            int n = Integer.parseInt(br.readLine());
            // 우선순위큐를 최소힙을 오름차순으로 살펴본다.
            PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++)
                priorityQueue.offer(Long.parseLong(st.nextToken()));

            // 현재 있는 슬라임들 중 가장 슬라임 에너지가 작은 두 슬라임을 골라낸다.
            while (priorityQueue.size() > 1) {
                // 두 슬라임을 합쳐 하나의 슬라임을 만든다.
                long newSlime = priorityQueue.poll() * priorityQueue.poll();
                // 새로운 슬라임이 LIMIT을 넘어갈 수 있다.
                // 따라서 새로은 슬라임을 곱하기보다는 새로운 슬라임에 먼저 모듈러 연산을 한 값을 곱한다.
                electricEnergy *= (newSlime % LIMIT);
                // 그 이후 에너지 곱 또한 LIMIT을 넘어갈 수 있으니 다시 모듈러 연산을 한다.
                electricEnergy %= LIMIT;
                // 슬라임은 LIMIT을 넘어가더라도 모듈러 연산한 값을 넣어서는 안된다.
                // 슬라임 에너지의 크기 순서가 중요하기 때문에 모듈러 연산한다면 순서가 바뀔 수 있다.
                priorityQueue.offer(newSlime);
            }
            // 최종 필요한 전기 에너지들의 곱을 기록한다.
            sb.append(electricEnergy).append("\n");
        }
        // 모든 테스트케이스들의 값 출력.
        System.out.print(sb);
    }
}