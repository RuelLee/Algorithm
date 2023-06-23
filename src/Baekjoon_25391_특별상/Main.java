/*
 Author : Ruel
 Problem : Baekjoon 25391번 특별상
 Problem address : https://www.acmicpc.net/problem/25391
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25391_특별상;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명 학생의 미술품 작품들에 대해 주최자와 심판이 각각 점수를 매긴다.
        // 주최자와 심판, 모두 서로 다른 두 작품에 같은 점수를 매기지 않는다.
        // 주최자는 m명을 골라 특별상을 수여한다.
        // 심판은 자신이 매긴 점수가 가장 높은 상위 k명에 대해 본상을 수여한다.
        // 주최자는 상을 받은 사람들이 자신에게 받은 점수가 최대가 되게끔하고자 한다.
        // 이 때 합의 최대값을 구하라
        //
        // 그리디 문제
        // 우선 심판이 매긴 점수 상위 k명에 대해서는 무조건 상이 수여된다. 그 상이 본상이든 특별상이든.
        // 상위 k명에 대해 본상을 못 받게하려면 특별상을 수여하는 수밖에 없는데 이는 수상자들에 주최자 점수 합에 도움이 되지 않는다.
        // 그 외의 n - k명 중 주최자가 부여한 점수가 갖아 높은 m명을 찾아 그들에게 특별상을 수여하는 것이 유리하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n명의 학생과 작품
        int n = Integer.parseInt(st.nextToken());
        // m명의 특별상 수상자
        int m = Integer.parseInt(st.nextToken());
        // k명의 본상 수상자
        int k = Integer.parseInt(st.nextToken());
        
        // 점수
        int[][] scores = new int[n][];
        for (int i = 0; i < scores.length; i++)
            scores[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 심판 점수로 내림차순 정렬
        Arrays.sort(scores, (o1, o2) -> Integer.compare(o2[1], o1[1]));

        long sum = 0;
        // 상위 k명이 본상을 수상한다.
        // 그 때의 주최자 점수를 합한다.
        for (int i = 0; i < k; i++)
            sum += scores[i][0];

        // 나머지 인원들을 주최자 점수로
        // 최소힙 우선순위큐에 점수를 담는다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = k; i < scores.length; i++)
            priorityQueue.offer(scores[i][0]);

        // 나머지 인원들 중 m명이 특별상을 수상한다.
        for (int i = 0; i < m; i++)
            sum += priorityQueue.poll();

        // 답안 출력.
        System.out.println(sum);
    }
}