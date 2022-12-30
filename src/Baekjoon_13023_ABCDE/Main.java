/*
 Author : Ruel
 Problem : Baekjoon 13023번 ABCDE
 Problem address : https://www.acmicpc.net/problem/13023
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13023_ABCDE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> friends;

    public static void main(String[] args) throws IOException {
        // n명의 사람과 m개의 친구 관계가 주어진다.
        // 친구 관계를 비교하여 5명이 다음과 같은 친구 관계가 성립하는지 알고자한다.
        // A <-> B <-> C <-> D <-> E
        //
        // DFS 문제
        // DFS 탐색을 하며, 탐색 깊이가 5가 되는 경우가 있는지 살펴본다.
        
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 입력 처리
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 친구 관계
        friends = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            friends.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            friends.get(a).add(b);
            friends.get(b).add(a);
        }

        // DFS로 해당 관계가 있는지 찾는다.
        boolean answer = false;
        boolean[] friendship = new boolean[n];
        for (int i = 0; i < n; i++) {
            friendship[i] = true;
            // i부터 DFS 탐색을 하여 깊이 5의 탐색을 할 수 있는지 살펴본다.
            if (findAnswer(i, 4, friendship)) {
                answer = true;
                break;
            }
            friendship[i] = false;
        }

        // 답안 출력
        System.out.println(answer ? 1 : 0);
    }
    
    // DFS 탐색
    static boolean findAnswer(int friend, int remain, boolean[] friendship) {
        // 깊이 5까지 들어온 경우. TRUE 리턴
        if (remain == 0)
            return true;

        // 아닌 경우 친구 관계를 통해 다음 깊이 탐색을 한다.
        boolean answer = false;
        for (int next : friends.get(friend)) {
            // next를 현 친구 관계에서 탐색한 적이 없고 
            if (!friendship[next]) {
                // next를 친구 관계에 포함시키고
                friendship[next] = true;
                // 깊이 5의 탐색이 이뤄진다면 
                if (findAnswer(next, remain - 1, friendship)) {
                    // true를 남기고 반목문 종료
                    answer = true;
                    break;
                }
                // 친구 관계 복구
                friendship[next] = false;
            }
        }
        // 탐색 결과 반환.
        return answer;
    }
}