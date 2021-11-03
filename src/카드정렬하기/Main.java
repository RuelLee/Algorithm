/*
 Author : Ruel
 Problem : Baekjoon 1715번 카드 정렬하기
 Problem address : https://www.acmicpc.net/problem/1715
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 카드정렬하기;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 두 개의 덱을 합치려면 두 덱에서의 카드 개수만큼의 시간이 소모된다
        // 따라서 카드 덱의 크기가 큰 덱은 최대한 나중에 합쳐 덱의 크기를 천천히 늘려야한다.
        // -> 정렬해서 가장 크기가 작은 두 카드덱을 합치고, 합친 카드덱을 남겨둔다.
        // 하나의 카드덱이 될 때까지 반복
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < n; i++)     // 우선순위큐에 담아준다.
            priorityQueue.offer(sc.nextLong());

        long sum = 0;       // 하나의 카드덱으로 합쳐질 동안의 연산 개수를 센다.
        while (priorityQueue.size() > 1) {
            long a = priorityQueue.poll();      // 가장 작은 카드덱
            long b = priorityQueue.poll();      // 두번째로 작은 카드덱

            sum += a + b;       // 두 카드덱을 합칠 때 필요한 연산의 수는  a+b개
            priorityQueue.offer(a + b);     // 합쳐진 카드덱의 크기는 a+b
        }
        System.out.println(sum);
    }
}