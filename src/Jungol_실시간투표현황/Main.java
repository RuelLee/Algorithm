/*
 Author : Ruel
 Problem : Jungol 8039번 실시간 투표현황
 Problem address : https://jungol.co.kr/problem/8039
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_실시간투표현황;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {
        // N명의 후보자, q개의 쿼리가 주어진다.
        // 쿼리는 다음 두 종류이다.
        // 0 n k : n번 후보에게 k표를 투표한다.
        // 1 x : x표인 후보자들을 오름차순으로 출력한다.
        //
        // 해쉬 맵, 트리 셋 문제
        // 해쉬 맵을 통해, 각 표 수에 따른 후보자들을 트리 셋으로
        // 오름차순 정렬하여 저장하며 쿼리들을 처리한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // N명의 후보자, q개의 쿼리
        int N = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 투표 수
        int[] votes = new int[N + 1];
        // 해쉬 맵
        HashMap<Integer, TreeSet<Integer>> map = new HashMap<>();
        // 초기 상태 0표 세팅
        map.put(0, new TreeSet<>());
        for (int i = 1; i <= N; i++)
            map.get(0).add(i);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // n번 후보에게 k표 투표
            if (Integer.parseInt(st.nextToken()) == 0) {
                int n = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());

                // 기존 투표 수에서 제거하고
                map.get(votes[n]).remove(n);
                votes[n] += k;
                // 다음 투표 수에 추가
                if (!map.containsKey(votes[n]))
                    map.put(votes[n], new TreeSet<>());
                map.get(votes[n]).add(n);
            } else {
                // x표인 후보자들을 출력ㄴ
                int x = Integer.parseInt(st.nextToken());
                // x표인 후보가 있는지 확인.
                if (!map.containsKey(x) || map.get(x).isEmpty())
                    sb.append("None");
                else {
                    // 있다면 오름차순으로 기록
                    for (int v : map.get(x))
                        sb.append(v).append(' ');
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append("\n");
            }
        }
        // 답 출력
        System.out.print(sb);
    }
}