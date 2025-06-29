/*
 Author : Ruel
 Problem : Baekjoon 25393번 교집합 만들기
 Problem address : https://www.acmicpc.net/problem/25393
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25393_교집합만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {
        // 구간 [l, r]이란 l이상 r이하의 실수로 이루어진 집합으로 말한다.
        // 구간 n개가 주어질 때,
        // 주어진 l과 r에 대해, 구간을 1개 이상 선택하여 교집합이 [l, r]이 되도록 할 수 있다면
        // 선택해야하는 최소 구간의 개수는?
        //
        // 트리셋, 맵 문제
        // 조금 생각해보면 구간의 교집합을 정확히 l, r로 만들기 위해 필요한 구간 개수의 최솟값의 최댓값은 2개다.
        // 3개 이상 구간의 교집합으로 표현된다면 이는 2개의 구간으로도 표현할 수 있다.
        // 따라서 답은 -1(불가능한 경우), 1, 2이다.
        // 맵과 트리셋을 활용하여 정확히 해당하는 구간이 있는지
        // 혹은 l을 포함하며 r보다 큰 구간과, r을 포함하며 l보다 작은 구간이 존재하는지 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 구간
        int n = Integer.parseInt(br.readLine());
        // 시작점과 끝점에 따라 해쉬맵, 트리셋으로 기록
        HashMap<Integer, TreeSet<Integer>> start = new HashMap<>();
        HashMap<Integer, TreeSet<Integer>> end = new HashMap<>();
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            if (!start.containsKey(l))
                start.put(l, new TreeSet<>());
            start.get(l).add(r);
            if (!end.containsKey(r))
                end.put(r, new TreeSet<>());
            end.get(r).add(l);
        }
        
        // q개의 쿼리 처리
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // 구간 [l, r]
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            int answer = -1;
            // l부터 시작하는 구간이 있고
            if (start.containsKey(l)) {
                // r이상으로 끝나는 구간이 있는 경우
                if (start.get(l).higher(r - 1) != null) {
                    // 정확히 r로 끝날 땐 하나로 표현가능
                    if (start.get(l).higher(r - 1) == r)
                        answer = 1;
                    // 그 외에는 다른 구간 또한 살펴봐
                    // r부터 l -1이하를 포함하는 구간이 있는지 확인한다.
                    else if (end.containsKey(r) && end.get(r).lower(l + 1) != null)
                        answer = 2;
                }
            }
            sb.append(answer).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}