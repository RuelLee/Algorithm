/*
 Author : Ruel
 Problem : Baekjoon 27084번 카드 뽑기
 Problem address : https://www.acmicpc.net/problem/27084
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27084_카드뽑기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        // n장의 카드가 일렬로 나열되어있고, 각 카드에는 양의 정수가 적혀있다.
        // 각 카드를 선택할 확률은 1/2이고, 뽑은 카드가 없다면 뽑기를 다시 진행한다.
        // 뽑은 카드의 정수들이 모두 다를 경우 게임에서 승리한다.
        // 게임에서 승리할 확률 * (2^n - 1)을 10^9 + 7로 나눈 나머지를 출력한다.
        //
        // 조합, 경우의 수 문제
        // 카드를 뽑는 조합의 수는 2^n개이다. 이 중 어떠한 카드도 선택하지 않는 경우가 포함되어있으므로
        // 최소 하나 이상의 카드를 뽑는 경우의 수는 2^n - 1이다.
        // 확률 * (2^n - 1)을 출력하므로, 사실 이건 경우의 수를 출력하라는 말과 같다.
        // 게임에서 승리하기 위해서는 같은 값을 갖는 카드는 한 장 혹은 한 장도 선택해서는 안된다.
        // 따라서 카드들을 같은 값을 갖는 카드의 개수로 정리한다.
        // 만약 1 값을 갖는 카드가 3개라면
        // 만약 같은 값을 갖는 카드의 수가 i개라면
        // i개 중 하나를 선택하는 경우 i가지, 선택하지 않는 경우 1가지
        // 총 i + 1 가지가 존재한다.
        // 따라서 1장 이상의 카드들에 대해 (개수 + 1)로 모두 곱해준 후, 어떠한 카드도 선택되지 않는 경우 1를 빼주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 각 카드를 같은 값을 갖는 카드의 수로 정리한다.
        int[] cards = new int[200001];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            cards[Integer.parseInt(st.nextToken())]++;

        // 초기값 1
        long sum = 1;
        for (int card : cards) {
            // 만약 해당 값을 갖는 카드가 존재하지 않는다면 건너뛰기.
            if (card == 0)
                continue;

            // 1개 이상 존재한다면 card + 1가지의 경우가 존재한다.
            sum *= (card + 1);
            // LIMIT으로 모듈러 연산.
            sum %= LIMIT;
        }

        // 최종적으로 구한 경우의 수에서, 어떠한 카드도 선택되지 않는 경우
        // 1가지를 빼준다.
        sum--;

        // 답안 출력
        System.out.println(sum);
    }
}