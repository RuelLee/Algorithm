/*
 Author : Ruel
 Problem : Baekjoon 32133번 가위바위보
 Problem address : https://www.acmicpc.net/problem/32133
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32133_가위바위보;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // rps에 공격 순서를 바위, 보, 가위로 했으므로
    // 해당 공격을 한 인원이 남으려면, 해당 공격에 지는
    // 가위, 바위, 보 순서로 내야한다.
    static char[] order = new char[]{'S', 'R', 'P'};
    static int k;
    static long[][] rps;
    static long minTurn = Integer.MAX_VALUE;
    static char[] answer;

    public static void main(String[] args) throws IOException {
        // 친구 n명에게 k(k < n)개의 선물을 나눠주고자 한다.
        // k명 이하의 친구들을 가위 바위 보를 통해 뽑고자 한다.
        // 매 턴, 주인공을 이긴 친구만 남겨, 최종적으로 k명 이하의 친구들에게 선물한다.
        // m개의 턴을 지나거나, 남은 친구가 0명이라면 선물하지 못한다.
        // 각 친구가 각 턴에 낼 가위 바위 보를 미리 알고 있다고 한다.
        // 가장 빠르게 k명 이하의 친구를 뽑아, 선물하고자 할 때
        // 해당 턴의 수와 내야하는 가위바위보는?
        //
        // 브루트 포스 문제
        // 친구들이 무엇을 낼 지 미리 알고 있기 때문에
        // 모든 가짓수를 찾아 친구들을 k명 이하로 남길 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 친구, m개의 턴, k개의 선물
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // 각 친구들이 내는 가위 바위 보를 비트마스킹 처리 한다.
        // rps[턴][바위 = 0, 보 = 1, 가위 2] = 해당 턴에, 해당 공격을 낸 친구들을 나타낸 비트마스크
        rps = new long[m][3];
        for (int i = 0; i < n; i++) {
            String input = br.readLine();
            // j번째 턴에 i번 친구가 낸 공격을 기록한다.
            for (int j = 0; j < input.length(); j++) {
                switch (input.charAt(j)) {
                    case 'R' -> rps[j][0] |= (1L << i);
                    case 'P' -> rps[j][1] |= (1L << i);
                    case 'S' -> rps[j][2] |= (1L << i);
                }
            }
        }

        // 최소 턴으로 k명 이하를 남기기 위해 내야하는 공격들
        answer = new char[m];
        bruteForce(0, (1L << n) - 1, new char[m]);
        // m턴 이내에 k명 이하로 남기는 것이 불가능한 경우
        if (minTurn == Integer.MAX_VALUE)
            System.out.println(-1);
        else {      // 가능하다면 답안 작성하여 출력
            StringBuilder sb = new StringBuilder();
            sb.append(minTurn).append("\n");
            for (int i = 0; i < minTurn; i++)
                sb.append(answer[i]);
            System.out.println(sb);
        }
    }
    
    // 비트마스크를 보고 사람의 수를 계산
    static int countPeople(long bitmask) {
        int count = 0;
        while (bitmask > 0) {
            count += bitmask % 2 == 0 ? 0 : 1;
            bitmask /= 2;
        }
        return count;
    }
    
    // 브루트 포스
    static void bruteForce(int turn, long bitmask, char[] history) {
        // 현재 남은 인원
        int currentPeople = countPeople(bitmask);
        // 이전까지 계산된 최소턴보다 같거나 크거나
        // 남은 인원이 없거나
        // 마지막 턴까지 진행했는데 k명보다 많은 인원이 남은 경우
        // 그냥 끝낸다.
        if (turn >= minTurn || currentPeople == 0 ||
                (turn == rps.length && currentPeople > k))
            return;
        else if (currentPeople <= k) {      // k명 이하로 남은 경우
            // 위의 if절에 따라 minTurn보다 같거나 큰 경우는 솎아졌으므로
            // 더 작은 턴만 남았다.
            // 최소 턴 수 갱신 및 내야하는 공격 기록 후, 종료
            minTurn = Math.min(minTurn, turn);
            answer = history.clone();
            return;
        }

        // 아직 턴도 남아있고, 인원도 k명 초과로 남은 경우
        // 3가지 공격을 했을 때, 경우를 모두 계산.
        for (int i = 0; i < 3; i++) {
            history[turn] = order[i];
            bruteForce(turn + 1, bitmask & rps[turn][i], history);
        }
    }
}