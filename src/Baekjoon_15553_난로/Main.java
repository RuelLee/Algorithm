/*
 Author : Ruel
 Problem : Baekjoon 15553번 난로
 Problem address : https://www.acmicpc.net/problem/15553
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15553_난로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 친구들이 방문하는 시간들이 주어진다.
        // 각 친구들은 t에 도착해 t+1에 나가며, 친구들이 있을 때는 난로에 불이 들어와야한다.
        // 성냥이 k개 있어, 난로에 불을 k번 밖에 붙이지 못한다할 때, 난로에 불이 들어와있는 최소 시간은?
        //
        // 정렬, 그리디 문제
        // 각 친구들이 올 때마다 난로를 켜고 끈다면, 총 n 동안 난로에 불을 피워야한다.
        // 하지만 불을 붙일 수 있는 횟수가 한정적이므로, 친구들 간의 방문 시간 차이가 적은 쪽에서부터
        // 불을 앞 친구가 방문했을 때, 불을 켜두었다가, 다음 친구가 왔을 때 불을 꺼야한다.
        // 따라서 친구들의 방문 시간 순으로 정렬한 후, 방문 시간 간격이 적은 순 위주로 n - k 개를 뽑아
        // 해당 시간만큼 추가로 불을 피워야하는 시간을 더해주면 된다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력으로 주어지는 n, k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 친구들의 방문 시간
        int[] visitTimes = new int[n];
        for (int i = 0; i < visitTimes.length; i++)
            visitTimes[i] = Integer.parseInt(br.readLine());
        // 정렬
        Arrays.sort(visitTimes);

        // 첫번째 친구부터 마지막 친구까지, 이웃한 친구들의 방문 시간 차이를
        // 최소힙 우선순위큐를 사용하여 순서대로 살펴본다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> visitTimes[o + 1] - visitTimes[o]));
        for (int i = 0; i < visitTimes.length - 1; i++)
            priorityQueue.offer(i);

        // 기본적으로 난로에 불을 피워야하는 시간은 n
        // 여기에 성냥이 부족해 이웃한 친구들 방문 시간 사이에도 불을 피워야하는 시간을 더한다.
        int timeSum = n;
        // n - k 개 만큼을 줄여야한다.
        // 먼저 방문 친구의 시각을 t1, 다음에 방문한 친구의 시간을 t2라 했을 때
        // t1 ~ t1 + 1, t2 ~ t2 + 1을 이미 timeSum에 포함되어있다.
        // 따라서 t1 + 1 ~ t2까지의 시간을 추가해주면 된다.

        while ((n - 1) - priorityQueue.size() < n - k)
            timeSum += visitTimes[priorityQueue.peek() + 1] - (visitTimes[priorityQueue.poll()] + 1);

        // 전체 난로에 불을 피워야하는 시간을 출력한다.
        System.out.println(timeSum);
    }
}