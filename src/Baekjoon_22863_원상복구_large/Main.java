/*
 Author : Ruel
 Problem : Baekjoon 22863번 원상 복구 (large)
 Problem address : https://www.acmicpc.net/problem/22863
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22863_원상복구_large;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n까지의 수가 적혀있는 n개의 카드가 있다.
        // d1, d2, ... , dn이 주어지며, 각 i에 대해, di번째 카드를 i번째로 가져오는 작업을 셔플이라고 한다.
        // 예를 들어 1 4 5 3 2로 카드가 주어지고, d1, .. , dn이 4 3 1 2 5라고 할 때
        // 한 번 셔플하면 3 5 1 4 2가 된다.
        // k번 셔플한 카드의 정보와 d의 정보가 주어질 때
        // 원래 카드의 정보를 출력하라
        //
        // 순열 사이클 분할, 희소 배열 문제
        // 셔플 작업에 따라, 일정 카드 그룹들이 각 사이클에 따라, 카드가 순환하게 될 것이다.
        // 이 그룹과 사이클 주기를 알아내어, 연산을 줄이는 것이 중요하다.
        // k가 최대 10^15으로 주어지므로, 희소 배열을 사용하여, 남은 주기를 직접 계산하는 것 또한 줄인다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 카드
        int n = Integer.parseInt(st.nextToken());
        // 셔플한 횟수 k
        long k = Long.parseLong(st.nextToken());

        // 현재 카드의 상태
        int[] currentCards = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < currentCards.length; i++)
            currentCards[i] = Integer.parseInt(st.nextToken());

        // 한 번 셔플한 상태는 희소 배열에 바로 담는다.
        // n이 최대 100만으로 주어지므로, 2^20으로 모두 처리가 된다.
        // 따라서 희소 배열은 sparseArray[카드의종류][21]로 선언
        int[][] sparseArray = new int[n + 1][21];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < sparseArray.length; i++)
            sparseArray[i][0] = Integer.parseInt(st.nextToken());
        // 희소 배열 처리
        for (int j = 1; j < sparseArray[0].length; j++) {
            for (int i = 1; i < sparseArray.length; i++)
                sparseArray[i][j] = sparseArray[sparseArray[i][j - 1]][j - 1];
        }

        // 2의 제곱수들 계산
        int[] pows = new int[21];
        pows[0] = 1;
        for (int i = 1; i < pows.length; i++)
            pows[i] = pows[i - 1] * 2;

        // 순환 사이클 분할
        // 각 그룹이 이루는 사이클 주기를 계산한다.
        int[] cycles = new int[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < sparseArray.length; i++) {
            if (cycles[i] != 0)
                continue;
            // 아직 어느 그룹에도 속하지 않았다면 계산.
            // 시작 idx는 i
            int idx = i;
            queue.offer(idx);
            // 다음 수가 방문한 적이 있지 않은 동안 계속하여 방문.
            while (queue.peek() != sparseArray[idx][0])
                queue.offer(idx = sparseArray[idx][0]);

            // 해당 그룹의 사이클 주기는 해당 그룹원의 수
            cycles[i] = queue.size();
            while (!queue.isEmpty())
                cycles[queue.poll()] = cycles[i];
        }

        // 희소 배열을 통해 원래 카드 정보를 찾는다.
        int[] answer = new int[n + 1];
        for (int i = 1; i < currentCards.length; i++) {
            // 사이클의 나머지 처리한 만큼을 직접 계산해야한다.
            int cycle = (int) (k % cycles[i]);
            int order = i;
            // 희소배열을 살펴보며, cycle이 희소 배열보다 많이 남았다면
            // 해당 개수만큼을 줄여나간다.
            for (int j = pows.length - 1; j >= 0; j--) {
                if (cycle >= pows[j]) {
                    cycle -= pows[j];
                    order = sparseArray[order][j];
                }
            }
            // 최종적으로 i번째 카드가 원래 있던 순서는 order다
            // order번째에 현재 i번째 카드인 currentCards[i]를 넣는다.
            answer[order] = currentCards[i];
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < answer.length - 1; i++)
            sb.append(answer[i]).append(" ");
        sb.append(answer[answer.length - 1]);
        // 답 출력
        System.out.println(sb);
    }
}