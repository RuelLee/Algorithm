/*
 Author : Ruel
 Problem : Baekjoon 1005번 ACM Craft
 Problem address : https://www.acmicpc.net/problem/1005
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1005_ACMCraft;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Construction {
    int n;
    int finishedTime;

    public Construction(int n, int finishedTime) {
        this.n = n;
        this.finishedTime = finishedTime;
    }
}

public class Main {
    public static void main(String[] args) {
        // 위상 정렬 문제
        // 다만 같은 진입차수더라도 시간에 따라 우선순위가 있다! 우선순위큐를 사용하자!
        Scanner sc = new Scanner(System.in);

        int testCase = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = sc.nextInt();   // 전체 건물의 수
            int k = sc.nextInt();   // 주어지는 건물 상관관계 수

            int[] d = new int[n];       // 각 건물의 건설 소요 시간
            for (int i = 0; i < n; i++)
                d[i] = sc.nextInt();

            List<Integer>[] next = new List[n];     // 각 건물의 후속 건물을 저장할 리스트
            for (int i = 0; i < next.length; i++)
                next[i] = new ArrayList<>();

            int[] inDegree = new int[n];        // 각 건물의 진입차수를 저장할 공간
            for (int i = 0; i < k; i++) {
                int a = sc.nextInt() - 1;
                int b = sc.nextInt() - 1;
                next[a].add(b);
                inDegree[b]++;
            }
            int w = sc.nextInt() - 1;       // 마지막 승리 건물

            PriorityQueue<Construction> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.finishedTime, o2.finishedTime));
            for (int i = 0; i < inDegree.length; i++) {
                if (inDegree[i] == 0)       // 현재 진입차수가 0인 건물들을 모두 짓는다
                    priorityQueue.add(new Construction(i, d[i]));       // i번째 건물을 짓고, 이 건물이 완성되는 시간은 d[i]
            }

            while (!priorityQueue.isEmpty()) {
                Construction current = priorityQueue.poll();    // finishedTime이 적은 건물부터 꺼내서
                if (current.n == w) {       // 승리 건물이라면 값을 기록하고 종료.
                    sb.append(current.finishedTime).append("\n");
                    break;
                }

                for (int i : next[current.n]) {     // 아니라면 후속 건물들의 진입차수를 하나씩 낮춰주고
                    if (inDegree[i] > 0)
                        inDegree[i]--;

                    if (inDegree[i] == 0) {     // 진입차수가 0이 된 후속 건물은 바로 지어준다.
                        priorityQueue.add(new Construction(i, current.finishedTime + d[i]));
                    }
                }
            }
        }
        System.out.println(sb);
    }
}