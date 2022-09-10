/*
 Author : Ruel
 Problem : Baekjoon 14567번 선수과목 (Prerequisite)
 Problem address : https://www.acmicpc.net/problem/14567
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14567_선수과목_Prerequisite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 과목 m개의 선수 조건 수가 주어진다.
        // 각 과목을 들을 수 있는 최소 학기를 출력하라.
        //
        // 위상 정렬 문제
        // 위상 정렬로 학기 순서를 계산하되, 각 과목의 이수 가능 최소 학기는
        // 선수 과목들 중 가장 큰 값 + 1 학기가 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 주어지는 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 진입 차수
        int[] inDegree = new int[n + 1];
        // 선수 과목 정보
        List<List<Integer>> list = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            list.add(new ArrayList<>());

        // 주어지는 선수 과목 정보
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a의 후수 과목은 b
            list.get(a).add(b);
            // b의 진입 차수 증가.
            inDegree[b]++;
        }

        // 각 과목의 이수 가능 최소 학기.
        int[] minSemesters = new int[n + 1];
        // 초기값은 1로 세팅.
        Arrays.fill(minSemesters, 1);
        Queue<Integer> queue = new LinkedList<>();
        // 진입 차수가 0인 과목을 찾아 큐에 삽입.
        for (int i = 1; i < inDegree.length; i++) {
            if (inDegree[i] == 0)
                queue.offer(i);
        }
        
        while (!queue.isEmpty()) {
            // 현재 이수 가능한 과목
            int current = queue.poll();

            // current의 후수 과목들을 살펴보며
            for (int next : list.get(current)) {
                // 선수 과목들이 모두 끝나야 후수 과목을 이수할 수 있으므로
                // 기존에 next의 이수 가능 학기와, current의 이수 가능 학기 + 1과 비교해 큰 값을 기록한다.
                minSemesters[next] = Math.max(minSemesters[next], minSemesters[current] + 1);
                // 만약 진입 차수가 0이 되었다면 큐에 삽입해서 다음 순서에 처리한다.
                if (--inDegree[next] == 0)
                    queue.offer(next);
            }
        }

        // 기록된 모든 과목의 이수 가능 최소 학기들을 출력한다.
        StringBuilder sb = new StringBuilder();
        sb.append(minSemesters[1]);
        for (int i = 2; i < minSemesters.length; i++)
            sb.append(" ").append(minSemesters[i]);

        System.out.println(sb);
    }
}