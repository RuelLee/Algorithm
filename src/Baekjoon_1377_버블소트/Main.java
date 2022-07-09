/*
 Author : Ruel
 Problem : Baekjoon 1377번 버블 소트
 Problem address : https://www.acmicpc.net/problem/1377
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1377_버블소트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다
        // 이를 버블소트할 때, 몇번째 턴만에 정렬이 끝나는지 출력하는 문제
        //
        // 정렬을 이용한 문제.
        // 버블소트가 진행될 때, 각 턴마다, 숫자들은 최대 한칸씩 앞으로 갈 수 있다.
        // 5 4 3 2 1 -> 4 3 2 1 5 -> 3 2 1 4 5 -> 2 1 3 4 5 -> 1 2 3 4 5
        // 위와 같이 정렬이 진행된다. 따라서 정렬 전의 위치와 정렬 후의 위치를 따져,
        // 가장 많이 앞으로 이동해야하는 수의 이동 횟수가 버블 소트가 끝나는 턴 수가 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 원래 수의 위치를 기록해둔다.
        // 같은 수가 들어오지 않는다는 조건이 없으므로, 같은 수가 여러번 들어올 수 있다.
        // 따라서 해쉬맵과 큐를 이용해서 각 수의 원래 위치를 기록해두자.
        HashMap<Integer, Queue<Integer>> originalOrder = new HashMap<>();
        // 우선 순위큐를 이용하여, 정렬이 끝난 후의 위치를 찾아낼 것이다.
        PriorityQueue<Integer> ascOrder = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(br.readLine());
            // num이 처음 들어온 수라면, 해쉬맵에 큐를 추가하고
            if (!originalOrder.containsKey(num))
                originalOrder.put(num, new LinkedList<>());
            // 큐에 num의 위치 기록
            originalOrder.get(num).offer(i);
            // 우선순위큐에 num 삽입.
            ascOrder.offer(num);
        }

        // 가장 많이 앞으로 가야하는 수의 이동 횟수를 계산한다.
        int maxDiff = 0;
        // 0번 순서부터
        int order = 0;
        // 우선순위큐를 하나씩 수를 뽑아간다.
        // 최소힙이므로 작은 수부터 오름차순으로 살펴본다.
        // 원래 순서 - 정렬이 끝난 순서 = 이동해야하는 위치의 크기
        // 모든 수에 대해 최대값을 찾자.
        while (!ascOrder.isEmpty())
            maxDiff = Math.max(maxDiff, originalOrder.get(ascOrder.poll()).poll() - order++);

        // 정렬이 끝난 후에, 한번은 더 돌아보며 정렬이 끝났다는 것을 확인해야하므로 +1
        System.out.println(maxDiff + 1);
    }
}