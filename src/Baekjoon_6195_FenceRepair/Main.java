/*
 Author : Ruel
 Problem : Baekjoon 6195번 Fence Repair
 Problem address : https://www.acmicpc.net/problem/6195
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6195_FenceRepair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 하나의 널빤지를 잘라 n개로 만들고자한다.
        // 널빤지를 자르는 비용은, 원래 널빤지의 길이와 같다고 한다.
        // 예를 들어 5인 널빤지를 1, 4로 나누거나 2, 3으로 나누는 경우 모두 5만큼이 소비된다.
        // n개의 널빤지 각각의 길이가 주어질 때, 필요한 최소 비용은?
        //
        // 그리디 문제
        // 한 널빤지를 두 널빤지로 나눌 때의 비용은 처음 널빤지의 비용이다.
        // 반대로 거꾸로 생각한다면 두 널빤지를 한 널빤지로 합치는 비용은 합쳐진 널빤지의 길이다.
        // 따라서 역으로 필요한 n개의 널빤지들을 가장 작은 두 널빤지를
        // 계속해서 합쳐나가는 과정이라고도 볼 수 있다.
        // 위 과정을 진행하며 비용 합을 구하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 필요한 n개의 널빤지
        int n = Integer.parseInt(br.readLine());

        // 최소힙 우선순위큐를 활용한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < n; i++)
            priorityQueue.offer(Integer.parseInt(br.readLine()));

        // 총 비용은 int 범위를 벗어날 수 있다.
        long sum = 0;
        // 하나의 널빤지가 남을 때까지
        while (priorityQueue.size() > 1) {
            // 두 널빤지를 꺼내, 합치는 과정을 진행한다.
            int notSplit = priorityQueue.poll() + priorityQueue.poll();
            // 해당 비용 누적.
            sum += notSplit;
            // 합쳐진 널빤지를 다시 큐에 추가.
            priorityQueue.offer(notSplit);
        }
        // 계산된 총 비용 출력
        System.out.println(sum);
    }
}