/*
 Author : Ruel
 Problem : Jungol 1279번 후보 추천하기
 Problem address : https://jungol.co.kr/problem/1279
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1279_후보추천하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 사진틀의 개수와 총 추천 횟수가 주어진다.
        // 추천을 받으면 반드시 사진틀에 걸리게 된다.
        // 이미 모든 사진틀에 사진이 걸려있는 경우, 사진틀에 걸려있는 사진들 중 추천 수가 가장 적은 것이
        // 그러한 것이 여러개라면 게시된 지 가장 오래된 사진이 내려오고, 그 사진틀에 새로운 사진이 걸린다.
        // 사진틀에서 내려오게된 사진은 추천 수가 0으로 바뀐다.
        // 모든 추천이 끝난 후, 걸려있는 사진들의 번호는?
        //
        // 시뮬레이션, 정렬, 우선순위큐 문제
        // 우선순위큐를 통해, 추천 수에 따라 오름차순으로, 추천 수가 같다면, 게시된 날짜에 대해 오름차순으로 정렬한다.
        // 그리고, 게시되지 않은 사진이 추천될 때, 사진틀의 개수를 비교하고, 모두 차있다면 우선순위큐가 가르키는 값을 제거하고 새로운 사진을 추가한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 사진틀의 수
        int size = Integer.parseInt(br.readLine());
        // 총 추천 횟수
        int recommend = Integer.parseInt(br.readLine());

        // 각 사진 별 추천 받은 횟수
        int[] recommends = new int[101];
        // 사진틀에 걸리게 된 시점
        int[] enqueued = new int[101];
        // 우선순위큐를 추천 수에 따라 오름차순으로, 추천 수가 같다면, 게시된 날짜에 대해 오름차순으로 정렬한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (recommends[o1] == recommends[o2])
                return Integer.compare(enqueued[o1], enqueued[o2]);
            return Integer.compare(recommends[o1], recommends[o2]);
        });

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= recommend; i++) {
            // 추천 받은 사진
            int num = Integer.parseInt(st.nextToken());
            // 추천 수 증가
            recommends[num]++;

            // 게시되지 않은 사진이라면
            if (enqueued[num] == 0) {
                // 사진틀에 사진이 모두 찼다면
                // 조건에 맞는 사진을 하나 제거한다.
                // 그리고 추천 수와 게시일을 초기화 한다.
                if (priorityQueue.size() == size) {
                    int pullOut = priorityQueue.poll();
                    recommends[pullOut] = enqueued[pullOut] = 0;
                }
                // 새로운 사진 게시
                enqueued[num] = i;
                priorityQueue.offer(num);
            } else {
                // 이미 게시된 사진이라면, 값을 제거 후, 다시 넣어
                // 새로운 추천 수를 반영한다.
                priorityQueue.remove(num);
                priorityQueue.offer(num);
            }
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        while (priorityQueue.size() > 1)
            sb.append(priorityQueue.poll()).append(" ");
        sb.append(priorityQueue.poll());
        // 답 출력
        System.out.println(sb);
    }
}