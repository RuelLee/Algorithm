/*
 Author : Ruel
 Problem : Baekjoon 31997번 즐거운 회의
 Problem address : https://www.acmicpc.net/problem/31997
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31997_즐거운회의;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람과 m개의 친한 사람 정보가 주어진다.
        // 회의는 총 t분까지 진행된다.
        // n명의 사람이 회의 오는 시간과 떠나는 시간이 주어진다.
        // 0.5분부터, 1분 간격으로 회의에 참석한 사람들 중 친한 사람 쌍의 개수를 출력하라
        //
        // 우선순위큐, 정렬, 누적합 문제
        // 먼저, 사람들을 참석시간이 이른 순서대로, 같다면 퇴석 시간이 이른 순서대로 정렬을 하여 살펴본다.
        // 먼저 i번째 사람을 살펴보기 전,
        // i번째 사람보다 먼저 퇴석한 사람을 처리하며, 줄어든 친밀한 쌍의 개수를 계산한다.
        // 그 후, i번째 사람이 참석한 뒤, 생긴 친밀한 쌍의 개수를 계산해나간다.
        // 연산을 줄이기 위해 누적합으로, 줄어들고, 늘어난 쌍의 개수를 imos 법으로 처리

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 사람, m개의 친밀한 쌍 정보, 회의 시간 t
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 각 사람의 참석 정보
        int[][] people = new int[n][3];
        for (int i = 0; i < people.length; i++) {
            st = new StringTokenizer(br.readLine());
            people[i][0] = i + 1;
            for (int j = 1; j < people[i].length; j++)
                people[i][j] = Integer.parseInt(st.nextToken());
        }
        // 참석, 퇴석 시간으로 정렬
        Arrays.sort(people, (o1, o2) -> {
            if (o1[1] == o2[1])
                return Integer.compare(o1[2], o2[2]);
            return Integer.compare(o1[1], o2[1]);
        });
        
        // 친밀한 쌍 정보
        List<List<Integer>> intimates = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            intimates.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            intimates.get(a).add(b);
            intimates.get(b).add(a);
        }

        // 우선순위큐로 참석한 사람들 중, 먼저 퇴석할 사람들을 찾아낸다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> people[o][2]));
        // 각 시간마다, 늘어나가 줄어든, 친밀한 쌍을 imos로 계산
        int[] times = new int[t + 1];
        // 현재 참석하고 있는 사람들
        boolean[] attendance = new boolean[n + 1];
        for (int i = 0; i < people.length; i++) {
            // i번째 사람의 참석보다 먼저 퇴석할 사람들 처리
            while (!priorityQueue.isEmpty() && people[priorityQueue.peek()][2] <= people[i][1]) {
                int order = priorityQueue.poll();
                
                // people[oder][0]번과 친밀한 사람들 중 현재 참석하고 있는 사람의 수를 계산하여
                // 그만큼 차감
                for (int friend : intimates.get(people[order][0])) {
                    if (attendance[friend])
                        times[people[order][2]]--;
                }
                // people[oder][0]번 퇴석 처리
                attendance[people[order][0]] = false;
            }
            
            // people[i][0]번 참석 처리
            priorityQueue.offer(i);
            attendance[people[i][0]] = true;
            // people[i][0]와 친밀한 사람들 중 참석한 사람이 있다면
            // 해당 쌍 처리
            for (int friend : intimates.get(people[i][0])) {
                if (attendance[friend])
                    times[people[i][1]]++;
            }
        }
        // 더 이상 참석할 사람이 없으므로
        // 남은 사람들을 모두 퇴석 처리
        while (!priorityQueue.isEmpty()) {
            int order = priorityQueue.poll();
            for (int friend : intimates.get(people[order][0])) {
                if (attendance[friend])
                    times[people[order][2]]--;
            }
            attendance[people[order][0]] = false;
        }
        
        // imos에 따라 누적합 처리
        for (int i = 1; i < times.length; i++)
            times[i] += times[i - 1];

        // 답안 기록
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times.length - 1; i++)
            sb.append(times[i]).append("\n");
        // 답 출력
        System.out.print(sb);
    }
}