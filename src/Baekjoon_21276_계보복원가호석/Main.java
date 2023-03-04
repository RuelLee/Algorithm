/*
 Author : Ruel
 Problem : Baekjoon 21276번 계보 복원가 호석
 Problem address : https://www.acmicpc.net/problem/21276
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21276_계보복원가호석;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람들이 살고 있는 마을이 있다.
        // 각 사람들은 자신의 모든 조상들을 완벽하게 기억하고 있다.
        // 몇 개의 가문이 존재했는지와 각 가문의 이름
        // 그리고 사전순으로 각 사람의 이름과 자식의 수 그리고 자식들의 이름을 공백으로 구분하여 출력하라
        //
        // 위상 정렬 문제
        // 각 사람들이 자신의 조상들을 완벽히 기억하고 있으므로
        // 인접리스트로 각 선조로부터 후손들을 정리하고
        // 위상정렬을 통해 시조와 후손들을 찾아내가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // n명의 사람들
        int n = Integer.parseInt(br.readLine());
        String[] names = br.readLine().split(" ");
        // 편의성을 위해 정렬하여 사전순 탐색 순서를 사전순으로 한다.
        Arrays.sort(names);
        // 각 이름과 idx를 연결한다.
        HashMap<String, Integer> idx = new HashMap<>();
        for (int i = 0; i < names.length; i++)
            idx.put(names[i], i);

        // m개의 조상 정보가 주어진다.
        int m = Integer.parseInt(br.readLine());
        // 진입 차수
        int[] inDegree = new int[n];
        // 각 선조가 갖고 있는 후손 정보
        List<List<Integer>> descendants = new ArrayList<>();
        for (int i = 0; i < n; i++)
            descendants.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String descendant = st.nextToken();
            String ascendant = st.nextToken();

            // 선조에게 후손 정보를 추가하고
            descendants.get(idx.get(ascendant)).add(idx.get(descendant));
            // 진입 차수를 증가시킨다.
            inDegree[idx.get(descendant)]++;
        }

        // 위상 정렬을 통해 선조로부터 후손까지 순차적으로 찾아나간다.
        StringBuilder sb = new StringBuilder();
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            // 진입 차수 0인 사람은 가문의 시조
            if (inDegree[i] == 0) {
                // 큐에 추가
                queue.offer(i);
                // 가문명으로 기록
                sb.append(names[i]).append(" ");
            }
        }
        // 모두 찾은 시조의 수만큼을 기록
        sb.insert(0, queue.size() + "\n");
        sb.deleteCharAt(sb.length() - 1).append("\n");
        
        // 각 사람들의 자식을 기록한다.
        List<PriorityQueue<String>> child = new ArrayList<>();
        for (int i = 0; i < n; i++)
            child.add(new PriorityQueue<>());
        // 큐가 빌 때까지
        while (!queue.isEmpty()) {
            // 한 사람을 꺼내
            int current = queue.poll();

            // current의 자손들을 살펴본다.
            for (int descendant : descendants.get(current)) {
                // 만약 진입 차수가 0이 되었다면
                // descendant는 current의 자식이다.
                if (--inDegree[descendant] == 0) {
                    // 자식으로 등록하고
                    child.get(current).offer(names[descendant]);
                    // 큐에 추가
                    queue.offer(descendant);
                }
            }
        }

        // 이름으로 정렬을 하였기 때문에
        // 순서대로 방문하며 각 사람들의 이름과 자식의 수 그리고 자식들의 이름을 답안에 기록한다.
        for (int i = 0; i < child.size(); i++) {
            sb.append(names[i]).append(" ").append(child.get(i).size());
            while (!child.get(i).isEmpty())
                sb.append(" ").append(child.get(i).poll());
            sb.append("\n");
        }

        // 전체 답안 출력
        System.out.print(sb);
    }
}