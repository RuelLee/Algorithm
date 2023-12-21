/*
 Author : Ruel
 Problem : Baekjoon 27172번 수 나누기 게임
 Problem address : https://www.acmicpc.net/problem/27172
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27172_수나누기게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ 100만까지의 카드가 주어진다.
        // n명의 플레이어는 각각 하나의 카드를 가지며
        // a가 가진 카드가 b가 가진 카드로 나뉘어 떨어진다면 b가 승리한다고 한다.
        // 이 때 점수는 a는 -1, b는 +1 이 된다.
        // 모든 플레이어들이 다른 모든 플레이어와 대전을 한다고 할 때
        // 각 플레이어들의 점수는?
        //
        // 에라토스테네스의 체, 브루트 포스
        // n이 최대 10만까지 주어지므로 플레이어들을 실제로 대전시켜서는 안된다.
        // 다만 카드가 100만까지 주어지므로
        // 모든 카드들에 대해서 플레이어가 갖고 있는지에 대한 체크를 한 후
        // 각 카드의 배수인 카드가 존재하는지 센다.
        // 배수인 카드가 존재한다면 자신의 점수 +1, 상대의 점수 -1이다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 플레이어
        int n = Integer.parseInt(br.readLine());
        
        // 각 카드의 존재 유무
        boolean[] cards = new boolean[1_000_001];
        // 플레이어가 가진 카드
        int[] players = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            players[i] = Integer.parseInt(st.nextToken());
            cards[players[i]] = true;
        }

        // 점수를 카드 수에 따라 계산한다.
        int[] scores = new int[1_000_001];
        // 플레이어가 가진 카드 player
        for (int player : players) {
            // player의 배수 카드가 존재하는지 확인
            for (int j = 2; j * player < cards.length; j++) {
                // 존재한다면
                if (cards[j * player]) {
                    // player 점수 +1
                    scores[player]++;
                    // 배수의 점수 -1
                    scores[j * player]--;
                }
            }
        }
        
        // 플레이어의 순서대로 점수를 기록
        StringBuilder sb = new StringBuilder();
        for (int player : players)
            sb.append(scores[player]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 전체 답안 출력.
        System.out.println(sb);
    }
}