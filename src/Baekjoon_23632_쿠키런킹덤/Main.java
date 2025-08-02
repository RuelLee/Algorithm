/*
 Author : Ruel
 Problem : Baekjoon 23632번 쿠키런 킹덤
 Problem address : https://www.acmicpc.net/problem/23632
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23632_쿠키런킹덤;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 건물과 자원이 있다.
        // 각 건물을 짓는데 자원이 필요하며, 건물은 지어진 후엔 자원을 생산한다.
        // 각 건물을 짓는데 필요한 자원과 생산하는 자원들이 주어지며 그 수는 각 30개 이하이다.
        // 자원을 만드는데는 0초, 건물을 짓는데는 1초가 소요된다.
        // 각 자원들과 건물들은 동시에 생산과 건설이 가능하다.
        // m개의 건물은 미리 지어져있다.
        // 제한 시간 t 이내 가장 많은 건물을 올리고자할 때, 가능한 수와 그 건물들은?
        //
        // 위상 정렬 문제
        // 각 건물이 생산하는 자원과 해당 자원이 필요한 건물들을 리스트에 정리해둔다.
        // 그리고 건물이 지어지면, 각 건물이 생산하는 자원들을 살펴보며,
        // 이미 생산디고 있는 자원인지, 혹은 새롭게 생산되는 자원인지 판별하여
        // 새롭게 생산되는 자원이라면, 해당 자원으로 미건설 건물의 진입차수를 줄인다.
        // 해당 미건설 건물의 진입차수가 0이 되면 해당 건물을 지을 수 있게 된다.
        // 해당 작업은 t 시간 동안 반복하며 지을 수 있는 건물들을 짓는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 건물과 자원, m개의 미리 건설된 건물
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 제한 시간 t
        int t = Integer.parseInt(st.nextToken());
        
        // 각 자원으로 지을 수 있는 건물
        List<List<Integer>> comToBuild = new ArrayList<>();
        // 건물이 생산하는 자원
        List<List<Integer>> buildToCom = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            comToBuild.add(new ArrayList<>());
            buildToCom.add(new ArrayList<>());
        }
        
        // 건설된 건물
        boolean[] constructed = new boolean[n + 1];
        // 생산되고 있는 자원
        boolean[] produced = new boolean[n + 1];
        
        // m개의 미리 건설된 건물
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++)
            constructed[Integer.parseInt(st.nextToken())] = true;
        
        // 각 건물이 생산하는 자원
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for (int j = 0; j < num; j++)
                buildToCom.get(i + 1).add(Integer.parseInt(st.nextToken()));
        }

        int[] indegree = new int[n + 1];
        for (int i = 0; i < n - m; i++) {
            st = new StringTokenizer(br.readLine());
            // num번 건물을 짓는데 sort개의 자원이 필요.
            int num = Integer.parseInt(st.nextToken());
            int sort = Integer.parseInt(st.nextToken());
            // sort개의 자원으로 건물 num을 지을 수 있다고 표시
            for (int j = 0; j < sort; j++)
                comToBuild.get(Integer.parseInt(st.nextToken())).add(num);
            // num의 진입 차수는 sort
            indegree[num] = sort;
        }
        
        // 미리 건설된 건물들로부터 생산되는 자원들 체크
        Queue<Integer>[] queues = new Queue[2];
        for (int i = 0; i < queues.length; i++)
            queues[i] = new LinkedList<>();
        for (int i = 1; i < constructed.length; i++) {
            if (constructed[i]) {
                for (int com : buildToCom.get(i)) {
                    if (!produced[com]) {
                        produced[com] = true;
                        for (int build : comToBuild.get(com)) {
                            if (--indegree[build] == 0)
                                queues[0].offer(build);
                        }
                    }
                }
            }
        }
        
        // 시간 t 동안 반복
        int time = 0;
        while (time < t) {
            // 이번 큐가 빌 때까지
            while (!queues[time % 2].isEmpty()) {
                // build 건물 완성
                int build = queues[time % 2].poll();
                // 건물의 수 증가
                m++;
                // 건설 표시
                constructed[build] = true;
                
                // 해당 건물이 만드는 자원들 
                for (int com : buildToCom.get(build)) {
                    // 미생산되던 자원이라면
                    if (!produced[com]) {
                        // 생산 표시
                        produced[com] = true;
                        // 해당 자원이 들어가는 건물들의 진입 차수 차감
                        // 0이 된 건물은 바로 건설에 들어가고, 다음 큐에 추가
                        for (int nextBuild : comToBuild.get(com)) {
                            if (--indegree[nextBuild] == 0)
                                queues[(time + 1) % 2].offer(nextBuild);
                        }
                    }
                }
            }
            // 시간 증가
            time++;
        }
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        // 건설된 건물의 수
        sb.append(m).append("\n");
        // 건물의 종류를 기록
        for (int i = 1; i < constructed.length; i++) {
            if (constructed[i])
                sb.append(i).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}