/*
 Author : Ruel
 Problem : Baekjoon 17220번 마약수사대
 Problem address : https://www.acmicpc.net/problem/17220
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17220_마약수사대;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 마약 공급책과 마약 공급책의 관계 m개가 주어진다.
        // 몇몇의 마약 공급책의 공급을 끊더라도,
        // 마약을 공급하는 공급책의 수를 출력하라
        //
        // 그래프 탐색 문제
        // 주어지는 관계를 통해 공급책들을 연결한 후
        // 누구에게도 공급받지 않은 채, 공급을 하는 생산책을 찾는다.
        // 그 후, 상산책부터 탐색을 하며, 공급이 끊긴 공급책을 제외한 공급책의 수를 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 공급책과 m개의 관계
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 공급받지 않는 생산책을 찾는다.
        boolean[] source = new boolean[n];
        Arrays.fill(source, true);
        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i < n; i++)
            child.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = st.nextToken().charAt(0) - 'A';
            int b = st.nextToken().charAt(0) - 'A';

            // b는 a에게 공급을 받으며
            child.get(a).add(b);
            // b는 공급을 받았으므로 생산책이 될 수는 없다.
            source[b] = false;
        }

        // 경찰이 검거한 공급책들로부터는 더 이상 공급이 될 수 없다.
        boolean[] visited = new boolean[n];
        st = new StringTokenizer(br.readLine());
        int block = Integer.parseInt(st.nextToken());
        for (int i = 0; i < block; i++)
            visited[st.nextToken().charAt(0) - 'A'] = true;

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < source.length; i++) {
            // 경찰로부터 차단되지 않은 생산책들을 큐에 추가한다.
            if (!visited[i] && source[i]) {
                queue.offer(i);
                visited[i] = true;
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // current로부터 연결된 차단되지 않은 공급책들을 탐색한다.
            for (int next : child.get(current)) {
                if (!visited[next]) {
                    // 공급책 카운터 증가.
                    count++;
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
        
        // 아직 마약을 공급하는 공급책의 수 출력
        System.out.println(count);
    }
}