/*
 Author : Ruel
 Problem : Baekjoon 14907번 프로젝트 스케줄링
 Problem address : https://www.acmicpc.net/problem/14907
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14907_프로젝트스케줄링;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 최대 26개의 작업이 주어진다.
        // 각 작업마다 작업을 수행하는 시간이 있으며,
        // 해당 작업을 하기 전에 처리되야하는 작업들이 있다면 그 역시 같이 주어진다.
        // 모든 작업들을 완료하는데 걸리는 최소 시간은?
        //
        // 위상 정렬 문제
        // 선행 작업들이 있는 작업들에 대해 선행 작업들을 우선적으로 처리해야
        // 해당 작업을 처리할 수 있다.
        // 따라서 선행 작업들이 있는 작업에 대해 진입 차수를 계산하여
        // 진입 차수가 모든 사라진 시점에서 작업을 시작해야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        
        // 후행 작업들을 담아둔다.
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 26; i++)
            list.add(new ArrayList<>());
        // 각 작업 시간
        int[] works = new int[26];
        // 진입 차수
        int[] inDegrees = new int[26];
        while (input != null) {
            StringTokenizer st = new StringTokenizer(input);
            
            // 작업 번호, 작업 시간
            int workNum = st.nextToken().charAt(0) - 'A';
            int workTime = Integer.parseInt(st.nextToken());
            works[workNum] = workTime;
            // 선행 작업 처리
            if (st.hasMoreTokens()) {
                String priorWorks = st.nextToken();
                for (int i = 0; i < priorWorks.length(); i++)
                    list.get(priorWorks.charAt(i) - 'A').add(workNum);
                inDegrees[workNum] = priorWorks.length();
            }
            input = br.readLine();
        }

        // 선행 작업이 없는 작업들을 큐에 담는다.
        Queue<Integer> queue = new LinkedList<>();
        // 각 작업의 시작 시간
        int[] startTime = new int[26];
        for (int i = 0; i < inDegrees.length; i++) {
            if (works[i] != 0 && inDegrees[i] == 0)
                queue.offer(i);
        }
        
        // 전체 종료 시간
        int totalEndTime = 0;
        while (!queue.isEmpty()) {
            // 현재 처리되는 작업
            int current = queue.poll();
            // 작업 종료 시간
            int endTime = startTime[current] + works[current];
            // 전체 작업 종료 시간에
            // 현재 작업 종료 시간으로 더 큰 값 갱신이 되는지 확인.
            totalEndTime = Math.max(totalEndTime, endTime);

            // 현재 작업이 종료된 이후로 후행 작업들을 실행할 수 있다.
            // next 작업에 대해서 다른 선행 작업들이 존재할 수 있으므로
            // 해당 작업의 시작 시간은 모든 선행 작업들이 종료된 시간이다.
            // 따라서 가장 늦은 작업 종료 시간을 startTime[next]에 계산한다.
            for (int next : list.get(current)) {
                startTime[next] = Math.max(startTime[next], endTime);
                // 진입 차수가 0이 되었다면 next 작업을 시작할 수 있으므로 큐에 추가
                if (--inDegrees[next] == 0)
                    queue.offer(next);
            }
        }
        // 전체 작업 종료 시간 출력
        System.out.println(totalEndTime);
    }
}