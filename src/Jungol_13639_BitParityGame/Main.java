/*
 Author : Ruel
 Problem : Jungol 13639번 Bit parity game
 Problem address : https://jungol.co.kr/problem/13639
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_13639_BitParityGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 칠판과 2^20이내의 수가 적힌 n개의 카드가 주어진다.
        // myongjin과 hambak 두 명의 플레이어가 주어지고, 최종적으로 칠판에 전자는 홀수, 후자는 짝수가 남아있으면 이긴다.
        // 처음 칠판에는 0이 적혀있고, 카드를 통해서 비트연산 and, or, xor을 할 수 있다.
        // 모두가 최선으로 플레이할 때, 이기는 사람을 출력하라
        //
        // 게임 이론.
        // 최종 결과가 짝홀수에 따라 나뉘는데, 하는 연산은 비트연산이므로
        // 결국 중요한건 최하위 비트가 0이냐 1이냐이다. 따라서 수를 입력받을 때, 최하위 비트로 구분하여 0인지 1인지 구분하여 센다.
        // 칠판에 적힌 수와 카드로 할 수 있는 연산을 따져볼 때,
        // 단 한 경우만 제외하고 칠판에 적힌 수와 뽑은 카드가 무엇이든 원하는 수로 만들 수 있다.
        // 현재 짝수인데, 짝수 카드를 뽑은 경우, 홀수로 만드는 것이 불가능하다.
        // 따라서 n이 짝수인 경우, 항상 hambak의 턴에서 끝나고 어떤 카드를 갖고 있든 짝수로 만들 수 있으므로 항상 이긴다.
        // n이 홀수인 경우, 마지막 턴에 myongjin에게 홀수 카드가 쥐어져야만 이길 수 있다.
        // 따라서 n이 홀수이고 홀수 카드가 짝수카드보다 많은 경우에만 myongjin이 이길 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 카드
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 짝홀수로 구분하여 수를 센다.
        int[] cnt = new int[2];
        for (int i = 0; i < n; i++)
            cnt[Integer.parseInt(st.nextToken()) % 2]++;

        // n이 홀수이며, 홀수 카드가 짝수 카드보다 많은 경우에만 myongjin이 이길 수 있다.
        System.out.println((n % 2 == 1 && cnt[0] < cnt[1]) ? "myongjin" : "hambak");
    }
}