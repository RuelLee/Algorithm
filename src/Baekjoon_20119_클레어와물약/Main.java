/*
 Author : Ruel
 Problem : Baekjoon 20119번 클레어와 물약
 Problem address : https://www.acmicpc.net/problem/20119
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20119_클레어와물약;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 세상에 n 종류의 물약과 m개의 레시피가 존재한다.
        // 현재 무한한 l 종류의 물약을 갖고 있을 때
        // 만들 수 있는 물약의 종류를 출력하라
        //
        // 위상 정렬 문제
        // 한번 꼬임이 들어간다.
        // 같은 물약을 만드는 서로 다른 레시피가 존재할 수 있다.
        // 따라서 물약을 기준으로 위상정렬을 하는 것이 아닌
        // 레시피 기준으로 위상 정렬을 한 후, 이를 다시 물약에 매칭한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 물약과 m개의 레시피
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 레시피에 대해서 위상 정렬을 한다.
        int[] indegrees = new int[m];
        // 해당 레시피로 만들 수 있는 물약
        int[] recipeMatching = new int[m];
        List<List<Integer>> lists = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            lists.add(new ArrayList<>());
        
        // 물약 레시피
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // k개의 물약이 들어가며
            int k = Integer.parseInt(st.nextToken());
            // 그 종류
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < k; j++)
                queue.offer(Integer.parseInt(st.nextToken()));
            
            // 완성되는 물약의 종류
            int r = Integer.parseInt(st.nextToken());
            // i번 레시피를 만드는데는 k개의 물약이 필요하고
            indegrees[i] = k;
            // i번 레시피로는 r 물약을 만들 수 있다.
            recipeMatching[i] = r;

            // queue에 해당하는 물약이 생긴다면
            // r의 진입차수를 하나씩 낮춰준다.
            while (!queue.isEmpty())
                lists.get(queue.poll()).add(i);
        }
        
        // 현재 만들 수 있는 물약
        boolean[] possible = new boolean[n + 1];
        // l개의 물약을 현재 갖고 있다.
        int l = Integer.parseInt(br.readLine());
        Queue<Integer> queue = new LinkedList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < l; i++) {
            int potion = Integer.parseInt(st.nextToken());
            // 현재 potion은 바로 갖고 있으며
            possible[potion] = true;
            // 큐를 통해 위상 정렬을 한다.
            queue.offer(potion);
        }

        // 현재 만들 수 있는 물약의 수는 갖고 있는 물약의 수 l
        int counter = l;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // 리스트를 보고 해당하는 레시피의 진입 차수를 하나씩 낮춰준다.
            for (int next : lists.get(current)) {
                // 아직 레시피에 해당하는 물약이 만들어지지 않았고
                // 진입차수를 낮춰 0이 된다면 이제 만들 수 있게 된 경우.
                if (!possible[recipeMatching[next]] && --indegrees[next] == 0) {
                    // 카운터 증가
                    counter++;
                    // 해당 물약 제조 가능 체크
                    possible[recipeMatching[next]] = true;
                    // 큐에 해당하는 물약 추가.
                    queue.offer(recipeMatching[next]);
                }
            }
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        // 현재 counter 종류의 물약을 만들 수 있고
        sb.append(counter).append("\n");
        for (int i = 0; i < possible.length; i++) {
            // 해당 종류를 기록
            if (possible[i])
                sb.append(i).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 전체 답안 출력
        System.out.println(sb);
    }
}