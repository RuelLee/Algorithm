/*
 Author : Ruel
 Problem : Baekjoon 9577번 토렌트
 Problem address : https://www.acmicpc.net/problem/9577
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9577_토렌트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> slices;
    static int[] allocated;

    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스가 주어진다
        // 파일은 n개의 조각으로 나누어져있고, m명의 시더가 있다.
        // 주인공은 1초에 한 조각을 다운 받을 수 있다.
        // 각 사람별로 접속 시간과, 갖고 있는 조각이 주어질 때
        // 파일을 완전하게 받는 시간은 언제인지 구하라.
        //
        // 이분매칭 문제
        // 조각 <= 100, 시더 <= 100, 접속 시간 <= 100, 가지고 있는 조각의 수 <= n
        // 각 시간을 오름차순으로 살펴가며, 각 시간에 매칭할 수 있는 조각들을 최대한 많이 매칭한다.
        // 모든 조각이 매칭되는 순간이 파일이 완전하게 받을 수 있는 최소 시간.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            // 각 조각들을 얻을 수 있는 시간에 따라 저장해두자.
            slices = new ArrayList<>(100);
            for (int i = 0; i < 100; i++)
                slices.add(new ArrayList<>());

            // 시더에 대한 정보
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                // t1 ~ t2까지 접속해있으며
                int t1 = Integer.parseInt(st.nextToken());
                int t2 = Integer.parseInt(st.nextToken());
                // a개의 조각을 갖고 있다.
                int a = Integer.parseInt(st.nextToken());
                for (int j = 0; j < a; j++) {
                    // 갖고 있는 조각 중 하나.
                    int slice = Integer.parseInt(st.nextToken());
                    // 를 t1 ~ t2 까지에 모두 포함시켜준다.
                    for (int k = t1; k < t2; k++)
                        slices.get(k).add(slice);
                }
            }
            // 각 조각에 할당된 시간을 기록한다.
            allocated = new int[n + 1];
            // 0초를 사용할 것이기 때문에 -1로 초기화.
            Arrays.fill(allocated, -1);

            // 총 받은 조각이 수
            int downloaded = 0;
            // 흐르는 시간.
            int time = 0;
            for (; time < 100; time++) {
                // 현재 시간에 조각을 할당할 수 있다면,
                // downloaded 값을 하나 높이고 현재 받은 조각의 수가 n개인지 확인한다
                // 그렇다면 종료.
                if (bipartiteMatching(time, new boolean[100]) && ++downloaded == n)
                    break;
            }
            // 만약 모든 조각을 받았다면 time + 1(time부터 1초간 받았으므로, 종료 시간은 time + 1)을 출력.
            // 그렇지 않다면 파일을 완전하게 받을 수 없는 경우이므로 -1을 출력.
            sb.append(downloaded == n ? time + 1 : -1).append("\n");
        }
        System.out.print(sb);
    }

    // 이분 매칭
    static boolean bipartiteMatching(int time, boolean[] visited) {
        // 방문 체크.
        visited[time] = true;

        // 받을 수 있는 조각들을 살펴본다.
        for (int slice : slices.get(time)) {
            // 아직 할당되지 않았거나,
            // slice에 할당된 시간(allocated[slice])에 다른 조각을 받게끔할 수 있다면
            if (allocated[slice] == -1 ||
                    (!visited[allocated[slice]] && bipartiteMatching(allocated[slice], visited))) {
                // slice는 time에 받도록 한다.
                allocated[slice] = time;
                // true 반환.
                return true;
            }
        }
        // 할당하는 것이 불가능하다면 false 반환.
        return false;
    }
}