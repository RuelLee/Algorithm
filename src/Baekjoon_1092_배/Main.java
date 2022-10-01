/*
 Author : Ruel
 Problem : Baekjoon 1092번 배
 Problem address : https://www.acmicpc.net/problem/1092
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1092_배;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 항구에 n개의 크레인, m개의 짐이 존재한다.
        // 크레인은 각각 옮길 수 있는 최대 중량 제한이 있으며
        // m개의 짐 또한 무게가 다르다.
        // 크레인은 1분에 하나의 짐을 옮길 수 있다 했을 때, 모든 짐을 옮기는데 드는 최소 시간은?
        // 모든 짐을 옮기는 것이 불가능하다면 -1 출력.
        //
        // 그리디 문제
        // 중량 제한이 큰 크레인은 무게가 적은 짐도 옮길 수 있으므로
        // 각 크레인은 자신이 옮길 수 있는 가장 무거운 짐 순서대로 옮기는 것이 좋다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 크레인에 대한 입력
        int n = Integer.parseInt(br.readLine());
        int[] cranes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 정렬한다.
        Arrays.sort(cranes);

        // 짐에 대한 입력
        int m = Integer.parseInt(br.readLine());
        int[] boxes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 최대힙을 통해 각 크레인이 옮길 수 있는 짐들을 내림차순으로 살펴보자.
        List<PriorityQueue<Integer>> list = new ArrayList<>();
        // 최대힙 우선순위큐
        for (int i = 0; i < n; i++)
            list.add(new PriorityQueue<>((o1, o2) -> Integer.compare(boxes[o2], boxes[o1])));

        // 모든 짐을 살펴보며
        // 해당 짐을 옮길 수 있는 크레인의 우선순위큐에 모두 삽입한다.
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < cranes.length; j++) {
                if (boxes[i] <= cranes[j])
                    list.get(j).offer(i);
            }
        }

        // 옮겨진 짐을 표시한다.
        boolean[] handled = new boolean[m];
        // 이번 턴에 짐을 옮겼는지 확인한다.
        // 짐을 옮길 수 없다면, 모든 짐을 옮기는 것이 불가능한 경우.
        boolean moved = true;
        // 옮겨진 짐의 개수.
        // moved와 함께 모든 짐을 옮겼는지 확인한다.
        int handledBoxes = 0;
        // 시간.
        int time = 0;
        // 아직 모든 짐을 안 옮겼고, 전 턴에 짐을 하나 이상 옮겼다면.
        while (handledBoxes < m && moved) {
            moved = false;
            // 가장 무거운 짐을 옮길 수 있는 크레인부터 살펴본다.
            for (int i = list.size() - 1; i >= 0; i--) {
                // 이미 옮겨진 짐이 우선순위큐 최상단에 있다면, 모두 빼준다.
                while (!list.get(i).isEmpty() && handled[list.get(i).peek()])
                    list.get(i).poll();
                
                // 아직 안 옮겨진 짐 중에 i번이 옮길 수 있는 가장 무거운 짐을 옮긴다.
                if (!list.get(i).isEmpty()) {
                    // 해당 짐에 대해 옮김 표시.
                    handled[list.get(i).poll()] = true;
                    // 옮겨진 짐 개수 추가.
                    handledBoxes++;
                    // 크레인 작동 여부 추가.
                    moved = true;
                }
            }
            // 시간 추가.
            time++;
        }
        // 최종적으로 옮겨진 짐이 m개라면 시간 출력.
        // 그렇지 않다면 불가능한 경우이므로 -1 출력.
        System.out.println(handledBoxes == m ? time : -1);
    }
}