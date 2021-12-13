/*
 Author : Ruel
 Problem : Baekjoon 1135번 뉴스 전하기
 Problem address : https://www.acmicpc.net/problem/1135
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 뉴스전하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> underlings;

    public static void main(String[] args) throws IOException {
        // 상사로부터 부하 직원으로 지시 사항을 전달하는데 1분이 걸린다고 한다
        // 최고 직장 상사로부터 지시 사항을 모든 사원까지 전달하는데 걸리는 최소 시간을 구하라.
        // 트리 형태의 문제
        // 부모 노드로부터 전체 자식 노드로 전달하는데 자식 노드의 수만큼 시간이 걸린다.
        // a에게 b c d의 자식 노드가 있고, 각각 전파하는데 i, j, k분 걸린다고 하자
        // a가 b, c, d에게 순서대로 전달하면 b는 모두 전달하는데 (i + 1)분, c는 (j + 2)분, d는 (k + 3)분 걸린다.
        // 이 중 가장 큰 값이 가장 늦게 전달 받는 시간이며 이 값을 최소로 해야한다.
        // 따라서 b c d 중 가장 시간이 오래 걸리는 자식 노드에게 먼저 전달하고, 가장 일찍 끝나는 자식노드에게 마지막으로 전달해야한다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        underlings = new ArrayList<>();
        for (int i = 0; i < n; i++)
            underlings.add(new ArrayList<>());
        StringTokenizer st = new StringTokenizer(br.readLine());
        st.nextToken();
        for (int i = 1; i < n; i++)
            underlings.get(Integer.parseInt(st.nextToken())).add(i);
        System.out.println(calcMinTransmitTime(0));
    }

    static int calcMinTransmitTime(int supervisor) {
        // 우선순위큐로 부하 직원에게 전달하는데 걸리는 총 시간을 내림차순 정렬하자.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));

        for (int underling : underlings.get(supervisor))
            priorityQueue.offer(calcMinTransmitTime(underling));

        int order = 1;
        int max = 0;
        // 시간이 오래 걸리는 부하직원부터 전달하고, 그 때 걸리는 최대 시간을 max에 기록해두도록 하자.
        while (!priorityQueue.isEmpty())
            max = Math.max(max, priorityQueue.poll() + order++);

        // supervisor가 자신 이하의 부하 직원에게 모두 전파하는데 걸리는 시간은 max 분이다.
        return max;
    }
}