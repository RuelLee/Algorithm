/*
 Author : Ruel
 Problem : Baekjoon 1422번 숫자의 신
 Problem address : https://www.acmicpc.net/problem/1422
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 숫자의신;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 두 수의 대소를 어떻게 비교할까 고민해봐야한다.
        // 서로 log10 값이 갖다면(자릿수가 같다면) 대소만 비교하면 되지만, 서로의 자릿수가 다를 때가 문제이다.
        // 서로의 자릿수가 다르다면, 작은쪽을 자신의 형태가 반복되게 이어붙여 자릿수를 맞춰줘야한다
        // 예를 들어 101 101103을 비교한다면, 101이 자릿수가 작기 때문에 101101 형태로 바꾼 후, 101101과 비교를 해야한다
        // 그런데 우리가 원하는 건 두 수를 붙인 형태가 더 큰수가 되는 것을 원하는 것이므로 굳이 저렇게 할 필요가 있을까?
        // 그냥 두 수를 이어붙인 형태로 값을 비교해도 되지 않을까? 라는 생각으로 코딩했고, 정답을 받았다.
        // 101과 101103의 대소를 비교할 때, 그냥 101101101 과 101103101의 대소를 비교했다
        // 물론 값의 범위가 Integer는 물론 Long의 범위도 벗어나므로, Double로 비교했다.
        Scanner sc = new Scanner(System.in);

        int k = sc.nextInt();
        int n = sc.nextInt();

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            int logO1 = (int) Math.log10(o1);       // o1의 자릿수
            int logO2 = (int) Math.log10(o2);       // o2의 자릿수

            if (logO1 == logO2)     // 같다면 그냥 큰 값 먼저.
                return Integer.compare(o2, o1);

            double o1First = o1 * Math.pow(10, logO2 + 1) + o2;     // o1을 앞에 붙인 값
            double o2First = o2 * Math.pow(10, logO1 + 1) + o1;     // o2를 앞에 붙인 값

            return Double.compare(o2First, o1First);
        });

        int max = 0;
        for (int i = 0; i < k; i++) {
            int input = sc.nextInt();
            priorityQueue.offer(input);
            max = Math.max(max, input);
        }
        for (int i = 0; i < n - k; i++)     // n이 k보다 크다면 n-k번 만큼 가장 큰 수를 더 사용하면 된다.
            priorityQueue.offer(max);

        StringBuilder sb = new StringBuilder();
        while (!priorityQueue.isEmpty())
            sb.append(priorityQueue.poll());
        System.out.println(sb);
    }
}