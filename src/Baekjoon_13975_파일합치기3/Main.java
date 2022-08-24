/*
 Author : Ruel
 Problem : Baekjoon 13975번 파일 합치기 3
 Problem address : https://www.acmicpc.net/problem/13975
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13975_파일합치기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스
        // 소설을 구성하는 장의 수 k, 그리고 k개의 장의 크기가 주어진다.
        // 장을 하나로 합치는데는 두 장의 크기만큼의 비용이 소모된다고 한다.
        // 모든 장을 하나로 합치는데 드는 최소 비용을 구하라.
        //
        // 그리디한 문제.
        // 합치려는 두 장의 크기만큼 비용이 발생하기 때문에 크기가 큰 장 수는 최대한 적게 합쳐야한다.
        // 반대로 말하면 크기가 작은 장들을 우선적으로 합쳐야한다는 뜻.
        // 따라서 장들의 크기를 우선순위큐에 담고
        // 가장 작은 두 개의 장을 꺼내 합친 후, 합친 장을 다시 우선순위큐에 넣는다
        // 이러한 과정을 하나의 장이 될 때까지 반복하며 비용을 산출한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int k = Integer.parseInt(br.readLine());

            // 파일의 크기가 최대 1만, k가 최대 100만 까지 주어지므로 Long 타입을 사용하자.
            PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < k; i++)
                priorityQueue.offer(Long.parseLong(st.nextToken()));

            // 총 비용.
            long sum = 0;
            // 우선순위큐에 두 개 이상의 장이 담겨일 때만.
            while (priorityQueue.size() >= 2) {
                // 두 장을 꺼내
                long a = priorityQueue.poll();
                long b = priorityQueue.poll();
                // 합치는 비용.
                sum += (a + b);
                // 합친 장을 다시 우선순위큐에 담는다.
                priorityQueue.offer(a + b);
            }
            // 이번 테스트케이스의 비용.
            sb.append(sum).append("\n");
        }
        // 총 테스트케이스의 비용 출력.
        System.out.print(sb);
    }
}