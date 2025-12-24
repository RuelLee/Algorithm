/*
 Author : Ruel
 Problem : Baekjoon 3098번 소셜네트워크
 Problem address : https://www.acmicpc.net/problem/3098
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3090_소셜네트워크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 친구 관계 m개가 주어진다.
        // 친구 관계는 상호적이다.
        // 날마다 친구의 친구를 친구로 신청하며, 익일 친구 신청이 받아들여진다.
        // 친구의 친구는 전날까지 친구인 친구 관계만 볼 수 있다.
        // 모든 인원이 친구가 되는 날과 날마다 새로 생기는 친구 관계의 수를 계산하라
        //
        // 비트마스킹
        // 비트마스킹을 통해 친구 관계를 나타내고, 날마다 친구의 친구 관계를 합집합하는 형식으로 풀 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 인원, m개의 친구 관계
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        long[][] friends = new long[2][n];
        // 친구 관계에 자신을 포함
        for (int i = 0; i < n; i++)
            friends[0][i] |= (1L << i);

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            // 각각 a와 b에게 상대방을 친구로 기록
            friends[0][a] |= (1L << b);
            friends[0][b] |= (1L << a);
        }

        StringBuilder sb = new StringBuilder();
        int turn = 0;
        while (true) {
            // 새로운 관계의 개수
            int newRelationships = 0;

            // 모든 인원을 살펴보며
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // i와 j가 친구라면, i에 친구 관계에 j의 모든 친구를 추가한다.
                    if (i != j && (friends[turn % 2][i] & (1L << j)) != 0)
                        friends[(turn + 1) % 2][i] |= friends[turn % 2][j];
                }
                // 새로 생긴 친구 관계의 수 누적
                newRelationships += countBit(friends[(turn + 1) % 2][i] - friends[turn % 2][i]);
            }
            // 만약 더 이상 새로운 친구 관계가 생기지 않는다면 반복문 종료
            if (newRelationships == 0)
                break;
            // 그 외의 경우, 새로 생긴 친구 관계의 수 / 2를 기록
            // (a와 b가 새로운 친구인 경우, a에서 한 번, b에서 한 번, 총 두 번 세어졌으므로)
            sb.append(newRelationships / 2).append("\n");
            turn++;
        }
        // 총 걸린 턴 출력
        System.out.println(turn);
        // 날마다의 새로운 친구 관계 출력
        System.out.print(sb);
    }

    // 비트의 개수를 센다.
    static int countBit(long n) {
        int cnt = 0;
        while (n > 0) {
            if ((n & 1) == 1)
                cnt++;
            n >>= 1;
        }
        return cnt;
    }
}