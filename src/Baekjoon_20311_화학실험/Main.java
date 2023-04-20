/*
 Author : Ruel
 Problem : Baekjoon 20311번 화학 실험
 Problem address : https://www.acmicpc.net/problem/20311
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20311_화학실험;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // k종류의 색을 갖고 있는 n개의 시험관이 있다.
        // 해당 시험관을 이웃한 시험관끼리는 같은 색이 되지 않도록 나열하고자 한다.
        // 그렇게 할 수 있다면, 시험관의 색깔 번호를 공백으로 구분하여 출력한다.
        // 그렇지 않다면 -1을 출력한다
        //
        // 그리디 문제
        // 가장 많은 색의 시험관을 우선적으로 배치해야한다.
        // 따라서 최대힙 우선순위큐를 통하여 가장 많은 색의 시험관을 우선 배치하고
        // 그 후에, 이웃한 시험관이 같은 색을 갖게된다면 두번쨰로 많은 색의 시험관을 배치한다.
        // 위와 같이 배치하다 마지막으로 배치한 시험관과 이번에 배치할 시험관의 색이 같은데
        // 두번째 많은 시험관이 없다면 해당 경우는 불가능한 경우가 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 주어지는 시험관의 수와 색의 수
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 색에 따른 시험관의 개수
        int[] solutions = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 최대 힙 우선순위큐에 넣는다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(solutions[o2], solutions[o1]));
        for (int i = 0; i < k; i++)
            priorityQueue.offer(i);
        
        // 가장 많은 시험관의 색을 우선적으로 배치
        Deque<Integer> deque = new LinkedList<>();
        int max = priorityQueue.poll();
        deque.offerLast(max);
        solutions[max]--;
        priorityQueue.offer(max);
        // 위에 하나를 배치했으므로, n - 1번 동안 시험관을 배치한다.
        for (int i = 1; i < n; i++) {
            // 가장 많은 시험관의 색
            int mostMaximum = priorityQueue.poll();
            // 만약 마지막의 배치한 시험관의 색과 다르다면
            if (deque.peekLast() != mostMaximum) {
                // 해당 색을 바로 배치한다.
                deque.offerLast(mostMaximum);
                // 개수 감소
                solutions[mostMaximum]--;
            } else if (priorityQueue.isEmpty())     // 만약 색이 같은데, 두번째로 많은 시험관이 없다면 종료.
                break;
            else {      // 두번째로 많은 색의 시험관이 존재하는 경우
                // 두번째 시험관을 꺼내고
                int secondMaximum = priorityQueue.poll();
                // 배치
                deque.offerLast(secondMaximum);
                // 개수 감소
                solutions[secondMaximum]--;
                // 두번째로 많은 색의 시험관이 아직 남았다면 다시 우선순위큐에 추가.
                if (solutions[secondMaximum] > 0)
                    priorityQueue.offer(secondMaximum);
            }
            // 첫번째로 많은 시험관이 아직 개수가 남았다면 우선순위큐에 추가.
            if (solutions[mostMaximum] > 0)
                priorityQueue.offer(mostMaximum);
        }
        
        // 모든 과정이 끝나고 배치된 개수가 시험관들의 총합 개수와 일치하지 않는다면
        // 배치가 불가능한 경우. -1 출력
        if (deque.size() != n)
            System.out.println(-1);
        else {      // 배치가 가능했다면
            // 데크에서 앞에서부터 하나씩 뽑아내어 답안을 작성하고
            StringBuilder sb = new StringBuilder();
            while (!deque.isEmpty())
                sb.append(deque.pollFirst() + 1).append(" ");
            sb.deleteCharAt(sb.length() - 1);
            // 답안 출력.
            System.out.println(sb);
        }
    }
}