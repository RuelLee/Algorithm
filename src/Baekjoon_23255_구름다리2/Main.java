/*
 Author : Ruel
 Problem : Baekjoon 23255번 구름다리 2
 Problem address : https://www.acmicpc.net/problem/23255
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23255_구름다리2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 건물이 주어지고, 이들을 잇는 m개의 구름다리가 있다.
        // 각각에 색을 칠하되, 구름다리로 연결된 건물들은 서로 다른 색으로 칠하고자한다.
        // 색들은 1이상의 정수로 표현하며, 사전순으로 가장 앞서도록 칠한다.
        //
        // 그리디문제
        // 구름 다리 연결된 건물들에 대해 큰 번호의 건물에 대해서만 작은 건물에 대해 저장해두고
        // 그리고 연결된 앞 건물에 대해서 같은 색을 피하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 건물, m개의 구름 다리
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());

        // 뒷 건물에 대해서만
        // 앞 건물과의 연결 정보를 저장한다.
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int max = Math.max(a, b);
            int min = Math.min(a, b);

            connections.get(max).add(min);
        }

        // 각 건물에 구름다리 연결 여부를 보며 색을 정한다.
        int[] colors = new int[n + 1];
        for (int i = 1; i < colors.length; i++) {
            HashSet<Integer> hashSet = new HashSet<>();
            // 연결된 건물들의 색을 해쉬셋에 저장한다.
            for (int near : connections.get(i))
                hashSet.add(colors[near]);

            // 해쉬셋에 저장되지 않은 가장 작은 번호의 색을 할당한다.
            int color = 1;
            while (true) {
                if (!hashSet.contains(color)) {
                    colors[i] = color;
                    break;
                }
                color++;
            }
        }

        // 답안 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < colors.length; i++)
            sb.append(colors[i]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }
}