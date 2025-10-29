/*
 Author : Ruel
 Problem : Baekjoon 5175번 문제없는 문제
 Problem address : https://www.acmicpc.net/problem/5175
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5175_문제없는문제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m;
    static int[] problems;

    public static void main(String[] args) throws IOException {
        // k개의 테스트케이스가 주어진다.
        // n개의 문제와 m개의 알고리즘 종류가 주어진다.
        // 각 문제는 하나 혹은 여러개의 알고리즘 요소로 구성되어있다.
        // 가능한 적은 문제를 통해 모든 알고리즘 요소를 출제하고 싶다.
        // 출제해야하는 문제들을 출력하라
        // 그러한 종류가 여러개라면 사전순으로 앞서는 걸 출력한다
        //
        // 브루트 포스, 비트마스킹 문제
        // n, m이 최대 20으로 그리 크지 않으므로, 모든 경우의 수를 따지며
        // 그 때, 포함된 알고리즘의 종류를 비트마스킹으로 표현하며 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // k개의 테스트케이스
        int k = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        problems = new int[20];
        for (int t = 0; t < k; t++) {
            st = new StringTokenizer(br.readLine());
            // n개의 문제, m개의 알고리즘 종류
            m = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());

            Arrays.fill(problems, 0);
            // 문제에 포함된 알고리즘도 비트마스킹 처리
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                while (st.hasMoreTokens())
                    problems[i] |= (1 << (Integer.parseInt(st.nextToken()) - 1));
            }
            
            // 찾은 답
            int bitmask = findAnswer(0, 0, 0);
            sb.append("Data Set ").append(t + 1).append(": ");
            // 답에 속한 문제들을 체크하여 답안 작성
            for (int i = 0; i < n; i++) {
                if (((1 << i) & bitmask) != 0)
                    sb.append((char) ('A' + i)).append(" ");
            }
            sb.deleteCharAt(sb.length() - 1).append("\n");
            if (t != k - 1)
                sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
    
    // 현재 ~idx-1번까지의 문제들을 살펴봤고, idx번째의 순서이며
    // 현재까지 포함된 문제들은 problemBitmask, 알고리즘은 algorithmBitmask으로 표현
    static int findAnswer(int idx, int problemBitmask, int algorithmBitmask) {
        // 모든 알고리즘으 포함됐다면 현재 상태 바로 반환
        if (algorithmBitmask == (1 << m) - 1)
            return problemBitmask;
        // 그렇지 않은데 모든 문제를 살펴봤다면 -1 반환
        else if (idx == n)
            return -1;
        
        // idx 문제를 선택했을 때
        int r1 = findAnswer(idx + 1, problemBitmask | (1 << idx), algorithmBitmask | problems[idx]);
        // 선택하지 않았을 때
        int r2 = findAnswer(idx + 1, problemBitmask, algorithmBitmask);
        
        // r1이 불가능한 경우 r2 반환
        if (r1 == -1)
            return r2;
        // r2가 불가능한 경우, r1 반환
        else if (r2 == -1)
            return r1;
        // 둘 다 가능한 경우엔, 포함된 문제의 수를 세어
        // 적은 쪽을 반환. 같은 경우엔 사전순 우선이므로 r1이 우선권을 갖는다.
        else if (count(r1) <= count(r2))
            return r1;
        else
            return r2;
    }
    
    // bitmask에 포함된 비트의 개수 반환
    static int count(int bitmask) {
        int cnt = 0;
        while (bitmask > 0) {
            cnt += (bitmask & 1);
            bitmask >>= 1;
        }
        return cnt;
    }
}