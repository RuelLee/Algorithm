/*
 Author : Ruel
 Problem : Baekjoon 16225번 제271회 웰노운컵
 Problem address : https://www.acmicpc.net/problem/16225
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16225_제271회웰노운컵;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 선수 A, B가 있다
        // 이 선수들이 n개 문제에 대해 자신 있는 정도에 대한 수치들이 주어진다
        // A 선수가 두 문제를 고르면, B 선수는 두 문제 중 자신이 자신 있는 문제를 가져가고, 나머지 문제는 A가 가져간다고 한다
        // A 선수가 얻을 수 있는 자신 있는 수치들의 최대합은 얼마인가?
        //
        // 그리디와 정렬 문제라는 감은 오지만 이를 어떻게 풀어야하는가에 대한 고민이 많이 필요하다.
        // 선택권은 B 선수에게 있으므로, B선수가 가장 자신 없는 문제는 '무조건' A 선수가 풀게 된다.(B 선수가 선택하지 않을 것이므로)
        // B 선수가 가장 자신 있는 문제는 무조건 B 선수가 푼다(B 선수가 무조건 선택할 것이므로)
        // 그렇다면 B 선수의 자신도를 기반으로 문제들을 오름차순 정렬해보자
        // 첫번째 문제는 무조건 무조건 A가 가져간다
        // 그 이후가 문제인데, 두 문제씩 끊어서 생각해보자
        // 두번째, 세번째 문제 중 하나를 A가 선택할 수 있다. 마지막 문제와 같이 제시하면 무조건 B는 마지막 문제를 선택할 것이므로.
        // 다음으로는 두번째부터 다섯번째 문제 중 아직 선택되지 않은 문제들 중 하나를 A가 선택할 수 있다.
        // (2, 3)번 문제에서 마음에 드는 것과 마지막 문제를 제시했다고 했지만, 여기서 마지막 문제를 (4, 5)번 문제에 A가 선택하지 않은 문제로 대체할 수 있다.
        // 만약 A가 2, 3번 문제를 선택하고 싶다면, (2, 4)과, (3, 5)번을 제시하면 되므로.
        // 이런식으로 두개씩 범위를 확장해나가며 A의 자신도가 제일 높은 문제를 하나씩 선택해가면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] problems = new int[n][2];
        StringTokenizer aValue = new StringTokenizer(br.readLine());
        StringTokenizer bValue = new StringTokenizer(br.readLine());
        // B의 자신도에 따라 오름차순으로 문제를 정렬.
        PriorityQueue<Integer> bAsc = new PriorityQueue<>(Comparator.comparingInt(o -> problems[o][1]));
        for (int i = 0; i < problems.length; i++) {
            problems[i][0] = Integer.parseInt(aValue.nextToken());
            problems[i][1] = Integer.parseInt(bValue.nextToken());
            bAsc.offer(i);
        }

        // A가 선택할 수 있는 문제들을 A자신도에 따라 내림차순으로 정렬한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));
        long sum = problems[bAsc.poll()][0];        // 첫번째 문제는 A가 반드시 풀어야한다.
        while (bAsc.size() > 1) {       // 마지막 문제는 B가 풀어야하므로, 하나가 남아야한다.
            // 두개씩 문제를 추가해나가며
            for (int i = 0; i < 2; i++)
                priorityQueue.offer(problems[bAsc.poll()][0]);
            // 그 중 A가 가장 자신 있는 문제를 푼다.
            sum += priorityQueue.poll();
        }
        // 최종적으로 sum이 A가 자신 있는 문제들의 자신도 합.
        System.out.println(sum);
    }
}