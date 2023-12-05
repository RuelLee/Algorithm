/*
 Author : Ruel
 Problem : Baekjoon 6505번 문자열 디코딩
 Problem address : https://www.acmicpc.net/problem/6505
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6506_문자열디코딩;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ n까지의 수열이 주어지며 이것은 순열이 된다.
        // 문자열을 순열에 따라 재배치하는 과정을 m회 반복한다.
        // 만약 "hello"를 순열 2 3 1 5 4 에 따라 3회 반복한다면
        // hello -> elhol -> lhelo -> helol 이 된다.
        // 위 과정을 인코딩이라 부를 때
        // 인코딩된 문자열이 주어졌을 때, 인코딩 하기 전 문자열을 구하는 프로그램을 작성하시오.
        //
        // 순열 사이클 분할 문제
        // 를 연속해서 풀어보았다.
        // 재배치하는 과정을 역순으로 따라가야함에 주의하자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            // 문자열, 순열의 크기인 n
            int n = Integer.parseInt(st.nextToken());
            // 순열에 따라 m회 재배치 한다.
            int m = Integer.parseInt(st.nextToken());
            // 두 값이 모두 0이 들어온 경우 종료.
            if (n == 0 && m == 0)
                break;
            
            // 해당 위치의 문자가 속한 순열 사이클의 크기
            int[] cycleLength = new int[n + 1];
            st = new StringTokenizer(br.readLine());
            // 순열
            int[] ps = new int[n + 1];
            // 역순
            int[] pre = new int[n + 1];
            for (int i = 1; i < ps.length; i++) {
                ps[i] = Integer.parseInt(st.nextToken());
                pre[ps[i]] = i;
            }

            for (int i = 1; i < ps.length; i++) {
                // 이미 순열 사이클의 크기를 구했다면 건너뛴다.
                if (cycleLength[i] != 0)
                    continue;
                
                // 그렇지 않다면 탐색하며 순열 사이클을 구하고
                HashSet<Integer> hashSet = new HashSet<>();
                int idx = i;
                while (!hashSet.contains(idx)) {
                    hashSet.add(idx);
                    idx = pre[idx];
                }

                // 구성원 모두에게 같은 크기임을 기록한다.
                for (int j : hashSet)
                    cycleLength[j] = hashSet.size();
            }
            
            // 암호화된 문자열
            String encoded = br.readLine();
            for (int i = 0; i < encoded.length(); i++) {
                // 을 순열 사이클 역순으로 돌며 해당 위치의 문자를 찾는다.
                int idx = i + 1;
                // 사이클의 크기를 알고 있으므로, 나머지를 취해 해당 값만큼만 재배치 작업을 반복한다.
                int cycle = m % cycleLength[idx];
                while (cycle > 0) {
                    idx = pre[idx];
                    cycle--;
                }
                // 해당하는 문자 기록
                sb.append(encoded.charAt(idx - 1));
            }
            // 다음 테스트케이스를 위해 줄바꿈.
            sb.append("\n");
            
            // 다음 값을 입력 받음
            st = new StringTokenizer(br.readLine());
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}