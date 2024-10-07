/*
 Author : Ruel
 Problem : Baekjoon 1889번 선물 교환
 Problem address : https://www.acmicpc.net/problem/1889
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1889_선물교환;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생이 각각 두 명에게 선물을 줄 수 있다.
        // 그리고 각각 학생이 선물을 주는 두 학생이 주어진다.
        // 이 중 몇 명의 학생을 제외하여, 학생들이 모두, 두 번의 선물을 주고, 두 개의 선물을 받게끔하고자 한다.
        // 참여할 수 있는 최대 학생의 수와 학생 번호를 출력하라
        //
        // 위상 정렬 문제
        // 조금 생각이 필요한 문제.
        // 모든 학생이 두 개의 선물을 받기 위해서, 3개 이상의 선물을 받은 학생에게 선물을 준 학생들을 제외해가는 것이 아닌
        // 2개 미만의 선물을 받은 학생들을 우선적으로 제외해나간다.
        // 2개 미만의 선물을 받은 학생은 어떤 방법을 쓰더라도 2개를 받을 수 없기 때문.
        // 그러한 학생들을 순차적으로 모두 제외한다면, 모든 학생들이 2개의 선물을 주고, 두 개의 선물을 받을 때까지 진행된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // 각 학생이 선물을 준 학생들의 정보
        int[][] students = new int[n + 1][];
        // 진입 차수
        int[] indegree = new int[n + 1];
        for (int i = 1; i < students.length; i++) {
            students[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < 2; j++)
                indegree[students[i][j]]++;
        }

        // 진입차수가 2 미만인 학생들을 모두 큐에 담는다.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < indegree.length; i++) {
            if (indegree[i] < 2)
                queue.offer(i);
        }

        while (!queue.isEmpty()) {
            // 제외할 학생 current
            int current = queue.poll();

            // current가 준 선물 또한 제외한다
            for (int gift : students[current]) {
                // 그러다 받은 선물이 1개가 된 학생을 발견하면 해당 학생도 큐에 담는다.
                if (--indegree[gift] == 1)
                    queue.offer(gift);
            }
        }

        StringBuilder sb = new StringBuilder();
        // 최종적으로 진입차수가 2인 학생들을 세어, 출력한다.
        int count = 0;
        for (int i = 1; i < indegree.length; i++) {
            if (indegree[i] == 2) {
                count++;
                sb.append(i).append(" ");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        
        // 답 출력
        System.out.println(count);
        System.out.println(sb);
    }
}