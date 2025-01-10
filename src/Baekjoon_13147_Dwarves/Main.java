/*
 Author : Ruel
 Problem : Baekjoon 13147번 Dwarves
 Problem address : https://www.acmicpc.net/problem/13147
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13147_Dwarves;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 드워프 키 비교가 있다.
        // s1 < s2 혹은 s1 > s2 꼴이다.
        // 모든 상관 관계에 대해 오류가 존재하는지 여부를 밝혀라
        //
        // 위상 정렬 문제
        // 드워프 간의 키 비교이므로, 가장 큰 쪽이든, 가장 작은 쪽이든에서부터 시작하여
        // 반대쪽으로 나아가되, 이전에 등장한 사람이 다시 등장해서는 안된다.
        // 또한 순환 사이클이 발생할 수도 있으므로, 모든 탐색이 끝난 뒤에는
        // 모든 드워프에 대해 1회씩 탐색을 했어야한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 키 비교
        int n = Integer.parseInt(br.readLine());

        // 드워프들의 이름을 수로 치환한다.
        HashMap<String, Integer> dwarves = new HashMap<>();
        List<List<Integer>> smaller = new ArrayList<>();
        for (int i = 0; i < 10_000; i++)
            smaller.add(new ArrayList<>());
        
        // 진입 차수
        int[] indegree = new int[10_000];
        for (int i = 0; i < n; i++) {
            // 두 드워프에 대한 키 비교
            StringTokenizer st = new StringTokenizer(br.readLine());
            String s1 = st.nextToken();
            char comparison = st.nextToken().charAt(0);
            String s2 = st.nextToken();

            // 처음 등장한 드워프일 경우, 수를 할당.
            if (!dwarves.containsKey(s1))
                dwarves.put(s1, dwarves.size());
            if (!dwarves.containsKey(s2))
                dwarves.put(s2, dwarves.size());
            
            // 비교값에 따라, 더 작은 쪽을 더 큰 쪽 탐색이 끝나면 탐색하도록
            // 연결시키고, 진입 차수 조정
            if (comparison == '>') {
                smaller.get(dwarves.get(s1)).add(dwarves.get(s2));
                indegree[dwarves.get(s2)]++;
            } else {
                smaller.get(dwarves.get(s2)).add(dwarves.get(s1));
                indegree[dwarves.get(s1)]++;
            }
        }
        
        // 진입 차수가 0인 드워프부터 탐색 시작
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < dwarves.size(); i++) {
            if (indegree[i] == 0)
                queue.offer(i);
        }

        // 오류 여부
        boolean possible = true;
        // 방문 체크
        boolean[] visited = new boolean[dwarves.size()];
        while (possible && !queue.isEmpty()) {
            // 현재 드워프
            int current = queue.poll();
            
            // 비교가 존재했고, current보다 작은 다음 드워프
            for (int next : smaller.get(current)) {
                // 다음 드워프는 미방문이어야한다.
                // 이미 방문했다면 현재보다 드워프보다 크므로, 모순 발생.
                if (visited[next]) {
                    possible = false;
                    break;
                } else if (--indegree[next] == 0)       // 다음 드워프의 진입 차수 조정 후, 0이 될 경우 큐에 추가
                    queue.offer(next);
            }
            // 방문 체크
            visited[current] = true;
        }
        
        // 모든 드워프를 살펴보며 한번씩 방문했는지 체크
        for (int i = 0; i < dwarves.size(); i++) {
            if (!visited[i]) {
                possible = false;
                break;
            }
        }
        
        // 위상 정렬 탐색에서 모순이 발생했거나, 탐색하지 않은 드워프가 존재한다면
        // possible 값은 false가 되어 오류가 존재함을 나타낸다.
        // 결과 값에 따라 답 출력
        System.out.println(possible ? "possible" : "impossible");
    }
}