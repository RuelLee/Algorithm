/*
 Author : Ruel
 Problem : Baekjoon 32354 덱 조작과 쿼리
 Problem address : https://www.acmicpc.net/problem/32354
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32354_덱조작과쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 카드 게임의 덱에 다음과 같은 쿼리들을 시행한다.
        // push x : x가 적힌 카드를 최상단에 둔다(0 <= x <= 10^9)
        // pop : 최상단의 카드를 제거한다. 카드가 없는 경우, pop이 주어지지 않는다.
        // restore i : i번째 행동 이후의 상태와 동일하게 만든다. 0인 경우, 초기 상태로 되돌린다.
        // print : 현재 덱의 모든 카드 합을 출력한다.
        //
        // 누적 합, 연결 리스트 문제
        // 스택 문제... 같아 보이지만 스택의 구조를 알 필요는 있지만 스택을 사용하지는 않아도 되는 문제
        // 매 행동 마다, 현재 최상단의 카드, 최상단 아래 카드를 가르키는 idx, 현재까지의 누적합
        // 세가지 값을 가지고 계산하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n번의 쿼리
        int n = Integer.parseInt(br.readLine());
        
        // 최상단 카드, 최상단 바로 아래 카드의 idx, 누적합
        int[] topCard = new int[n + 1];
        int[] preTopCardIdx = new int[n + 1];
        long[] psums = new long[n + 1];

        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            // 쿼리
            String query = st.nextToken();
            // 출력이라면
            if (query.equals("print")) {
                // 최상단, 최상단 아래 카드 idx, 누적합 모두 변하지 않으며
                topCard[i] = topCard[i - 1];
                preTopCardIdx[i] = preTopCardIdx[i - 1];
                psums[i] = psums[i - 1];
                // 현재 누적합을 기록
                sb.append(psums[i]).append("\n");
            } else if (query.equals("push")) {
                // 최상단에 카드 추가
                // 값
                int value = Integer.parseInt(st.nextToken());
                // 최상단의 카드 값을 추가하고
                topCard[i] = value;
                // 이전 최상단 카드는 i - 1번째를 가르키도록 한다.
                preTopCardIdx[i] = i - 1;
                // 누적합은 값을 합산하여 누적
                psums[i] = psums[i - 1] + value;
            } else if (query.equals("pop")) {
                // 최상단 카드를 제거하는 경우
                // 누적합에서 최상단 카드 값을 제거하고
                psums[i] = psums[i - 1] - topCard[i - 1];
                // 최상단 카드는 이전 최상단 카드 값을 가져오고
                topCard[i] = topCard[preTopCardIdx[i - 1]];
                // 이전 최상단 카드는, 이전 상태의 이전 최상단 카드 idx를 가져온다.
                preTopCardIdx[i] = preTopCardIdx[preTopCardIdx[i - 1]];
            } else {
                // value 상태로 되돌리는 경우
                int value = Integer.parseInt(st.nextToken());
                // value에 저장되어있는 값을 모두 가져온다.
                topCard[i] = topCard[value];
                preTopCardIdx[i] = preTopCardIdx[value];
                psums[i] = psums[value];
            }
        }
        // 답 출력
        System.out.print(sb);
    }
}