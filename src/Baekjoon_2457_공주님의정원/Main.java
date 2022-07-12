/*
 Author : Ruel
 Problem : Baekjoon 2457번 공주님의 정원
 Problem address : https://www.acmicpc.net/problem/2457
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2457_공주님의정원;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    // 월과 일을 하나의 수로 취급하기 위해 각 달에 대해 일을 누적합으로 저장해둔다.
    static int[] daysPSum = {0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    static final int startDay = daysPSum[3] + 1;
    static final int finishDay = daysPSum[11] + 30;
    public static void main(String[] args) throws IOException {
        // n개의 꽃의 개화일, 낙화일이 주어진다
        // 정원에 3월 1일부터 11월 30일까지 매일 한가지 이상의 꽃이 피어있도록 하고 싶다.
        // 이 때 필요한 최소한의 꽃의 종류는?
        //
        // 그리디 문제
        // 현재까지 꽃이 피어있는 날보다 이른 날짜에 개화하는 꽃들 중 가장 낙화일이 늦은 꽃을 계속해서 선택해나가며,
        // 마지막 꽃의 낙화일이 11월 30일보다 늦게끔 맞춰주면 끝난다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[][] flowers = new int[n][2];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int sMonth = Integer.parseInt(st.nextToken());
            int sDay = Integer.parseInt(st.nextToken());
            int fMonth = Integer.parseInt(st.nextToken());
            // fDay - 1일까지만 피어있는다.
            int fDay = Integer.parseInt(st.nextToken()) - 1;

            flowers[i][0] = daysPSum[sMonth] + sDay;
            flowers[i][1] = daysPSum[fMonth] + fDay;
        }
        // 꽃을 개화일에 따라 정렬해준다.
        Arrays.sort(flowers, Comparator.comparing(f -> f[0]));

        // 현재 정원에 꽃이 피어있는 마지막 날짜
        // 3월 1일부터만 피어있으면 된다.
        int covered = startDay - 1;
        // 현재 고려된 꽃들 중 가장 늦은 날짜를 최대힙으로 뽑아낸다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(flowers[o2][1], flowers[o1][1]));
        // 다음 순서에 고려할 첫번째 꽃
        int startFlower = 0;
        // 선택된 꽃의 종류
        int flowerSum = 0;
        // startFlower가 n의 범위를 벗어나지 않으면서, 아직 finishiDay까지 꽃이 피어있지 않다면
        while (startFlower < n && covered < finishDay) {
            // startFlower가 n보다 작고, covered + 1일보다 같거나 작은 개화일을 갖는 꽃들을 우선순위큐에 담아간다.
            while (startFlower < n && flowers[startFlower][0] <= covered + 1)
                priorityQueue.offer(startFlower++);
            // 만약 우선순위큐가 비어있거나, 가장 늦은 낙화일의 꽃이 이미 정원에 선택된 꽃의 낙화일과 같거나 작을 경우
            // 반복을 종료한다.
            if (priorityQueue.isEmpty() || flowers[priorityQueue.peek()][1] <= covered)
                break;

            // 그렇지 않다면, covered를 새롭게 설정하고(=커진 값으로)
            covered = flowers[priorityQueue.poll()][1];
            // 선택된 꽃의 종류를 늘려준다.
            flowerSum++;
        }
        // 최종적으로 covered가 finishDay보다 작다면 방법이 없는 경우. 0 출력
        // 그렇지 않다면 꽃들 종류의 개수를 출력한다.
        System.out.println(covered < finishDay ? 0 : flowerSum);
    }
}