/*
 Author : Ruel
 Problem : Jungol 1318번 못생긴 수
 Problem address : https://jungol.co.kr/problem/1318
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1318_못생긴수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] multi = {2, 3, 5};

    public static void main(String[] args) throws IOException {
        // 소인수 분해했을 때, 2, 3, 5만 포함된 수를 못생긴 수라고 부른다.
        // 편의상 1도 못생긴 수에 포함시킨다.
        // n(1500이하)이 주어질 때, n번째 못생긴 수를 출력하라
        // n은 10만개 이하로 주어지며, 마지막엔 0이 주어진다.
        //
        // 우선순위큐 문제
        // 우선순위큐로 작은 수부터 탐색해나가며, 2배, 3배, 5배한 수들은 다음 번에 탐색한다.
        // 최대 1500개까지 탐색하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 우선순위큐
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(1L);
        // 해쉬셋으로 방문 체크
        HashSet<Long> hashSet = new HashSet<>();
        hashSet.add(1L);
        // 순서대로 리스트에 담는다.
        List<Long> list = new ArrayList<>();
        // 리스트에 1500개가 담길 때까지
        while (list.size() < 1500) {
            // 현재 우선순위큐의 가장 작은 수를 꺼내
            long current = priorityQueue.poll();
            // 리스트에 담고
            list.add(current);

            // 2, 3, 5배 한 수를 방문한 적이 없다면
            // 체크 후, 우선순위큐에 추가한다.
            for (int m : multi) {
                if (!hashSet.contains(current * m)) {
                    hashSet.add(current * m);
                    priorityQueue.offer(current * m);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        // n을 입력 받는다.
        String input = br.readLine().trim();
        while (true) {
            int n = Integer.parseInt(input);
            // 0이 나오면 종료
            if (n == 0)
                break;

            // 답 기록
            sb.append(list.get(n - 1)).append("\n");
            // 다음 입력
            input = br.readLine().trim();
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}