/*
 Author : Ruel
 Problem : Baekjoon 16498번 작은 벌점
 Problem address : https://www.acmicpc.net/problem/16498
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16498_작은벌점;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 세 명이 여러장의 카드를 들고 있다.
        // 세 명이 카드를 한 장씩 테이블에 내려놨을 때,
        // | max(a,b,c) – min(a,b,c) | 로 벌점이 정해진다.
        // 벌점을 최소화하고자할 때, 그 값은?
        //
        // 정렬, 세 포인터...?
        // 각 플레이어가 갖고 있는 카드들을 정렬한 후
        // 가장 낮은 값을 갖고 있는 카드를 다음 카드로 바꿔가며 최소값을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 플레이어가 갖고 있는 카드 수
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        // 각 카드들을 입력 받고 정렬
        int[][] cards = new int[3][];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            Arrays.sort(cards[i]);
        }
        
        // 최소 벌점
        int answer = Integer.MAX_VALUE;
        // 각 포인터들
        int[] idxes = new int[3];
        // 한 포인터라도 끝까지 도달하지 않은 포인터가 있는 동안
        while (idxes[0] < cards[0].length - 1 || idxes[1] < cards[1].length - 1 || idxes[2] < cards[2].length - 1) {
            // 벌점 계산
            answer = Math.min(answer,
                    Math.max(Math.max(cards[0][idxes[0]], cards[1][idxes[1]]), cards[2][idxes[2]]) -
                            Math.min(Math.min(cards[0][idxes[0]], cards[1][idxes[1]]), cards[2][idxes[2]]));

            // 가장 낮은 카드를 가르키는 포인터 찾기.
            // 가장 낮은 카드가 마지막 카드일 수 있으므로
            // 낮은 순서대로 모두 살펴본다.
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> cards[o][idxes[o]]));
            for (int i = 0; i < 3; i++)
                priorityQueue.offer(i);
            while (!priorityQueue.isEmpty()) {
                int current = priorityQueue.poll();
                // 이번 카드가 아직 마지막 카드가 아니라면
                // 뒷 카드로 꺼내고 다음 벌점 계산으로 넘긴다.
                if (idxes[current] < cards[current].length - 1) {
                    idxes[current]++;
                    break;
                }
            }
        }
        // 찾은 최소 벌점 출력
        System.out.println(answer);
    }
}