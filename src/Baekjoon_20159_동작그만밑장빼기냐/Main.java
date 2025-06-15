/*
 Author : Ruel
 Problem : Baekjoon 20159번 동작 그만. 밑장 빼기냐?
 Problem address : https://www.acmicpc.net/problem/20159
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20159_동작그만밑장빼기냐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 플레이어와 정훈이 카드 게임을 한다.
        // 짝수 n개의 카드가 있으며, 플레이어부터 한 장씩 카드를 가져간다.
        // 플레이어는 최대 한 번 밑장 빼기를 할 수 있다.
        // 플레이어가 얻을 수 있는 최대 점수는?
        //
        // 누적 합
        // 번갈아가면서 한 장씩 카드를 가져가기 때문에
        // 홀수별, 짝수별로 누적합을 왼쪽에서부터와 오른쪽에서부터를 구한다.
        // 그 후, 밑장 빼기를 할 시점을 선택을 해야한다.
        // 밑장 빼기를 아예 안 할 수도 있다.
        // 그리고 밑장 빼기를 플레이어의 턴에 할지, 상대 턴에 할 지도 고려한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n장의 카드
        int n = Integer.parseInt(br.readLine());

        int[] cards = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < cards.length; i++)
            cards[i] = Integer.parseInt(st.nextToken());

        // 왼쪽에서부터 누적합
        int[] psumsFromLeft = new int[n];
        psumsFromLeft[0] = cards[0];
        psumsFromLeft[1] = cards[1];
        // 오른쪽에서부터 누적합
        int[] psumsFromRight = new int[n];
        psumsFromRight[n - 1] = cards[n - 1];
        psumsFromRight[n - 2] = cards[n - 2];

        for (int i = 2; i < cards.length; i++) {
            psumsFromLeft[i] = psumsFromLeft[i - 2] + cards[i];
            psumsFromRight[n - 1 - i] = psumsFromRight[n + 1 - i] + cards[n - 1 - i];
        }

        // 밑장 빼기를 안할 때, 밑장 빼기를 처음에 할 때의 두 값을 비교.
        int max = Math.max(psumsFromLeft[n - 2], psumsFromRight[1]);
        // 플레이어의 턴에 밑장 빼기를 하는 경우.
        // i번째까지 카드를 원래대로 받고,
        // 그 이후로는 i + 3, i+5, ... n 카드를 받는 경우
        for (int i = 0; i + 3 < cards.length; i += 2)
            max = Math.max(max, psumsFromLeft[i] + psumsFromRight[i + 3]);
        // 상대 턴에 밑장 빼기를 하는 경우
        // 플레이어가 i-1번째 카드까지를 받고서, 상대방에게 n번째 카드를 주고, 플레이어가 i, i+2, ... 카드를 받는다.
        for (int i = 1; i < cards.length - 2; i += 2)
            max = Math.max(max, psumsFromLeft[i - 1] + psumsFromRight[i] - cards[n - 1]);
        // 답 출력
        System.out.println(max);
    }
}