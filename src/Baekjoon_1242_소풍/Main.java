/*
 Author : Ruel
 Problem : Baekjoon 1242번 소풍
 Problem address : https://www.acmicpc.net/problem/1242
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1242_소풍;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명이 소풍에 참가했고, 이들은 게임을 진행한다.
        // 1번부터 수를 외치며 k를 외친 사람은 퇴장한다.
        // k+1번부터 다시 1의 숫자를 외치며 n번 순서 이후에는 다시 1번이 이어진다.
        // n과 k 그리고 m이 주어질 때
        // m번째 사람은 몇번째에 퇴장하는가?
        //
        // 요세푸스 문제
        // 인데 단순히 계산으로 풀 수 있다.
        // 먼저 단순히 m이 k의 배수일 경우. m / k번째에 끝난다.
        // 그 외의 경우.
        // 왼쪽에는 m - 1명의 사람, 가운데는 m, 오른쪽에는 n - m명의 사람이 있다고 하자.
        // 첫번째 숫자를 외치는 사람을 기억하고, 왼쪽에서 몇 명, 오른쪽에서 몇 명이 사라지는지 안다면
        // 현재 인원 / k 번의 턴을 한번에 계산할 수 있다.
        // 그런데 왼쪽, 오른쪽 구분하기 귀찮으므로, 처음에 첫번째 숫자를 외치는 사람을 m번 이후의 첫번째 숫자를 외치는 사람으로 바꿔두자.
        // 그러면 n - m명의 사람, m - 1명의 사람, m번으로 m이 가장 마지막에 오게끔 순서를 바꾸어 계산하면 보다 편하게 계산할 수 있다.
        // 그리고 매턴 남은 인원과 1을 외치는 사람을 계산하여, m이 k를 외치는지 확인하고, 배수라면, 해당만큼의 턴을 더해 m이 퇴장하는 순서를 계산할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 인원, 숫자 텀 k, m번째 사람이 퇴장당하는 순서를 구한다.
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int firstIdx, turn;
        // 만약 m이 k의 배수인 경우, 바로 턴 계산 가능.
        // 그 외의 경우, k < m인 경우는 (m / k + 1)번, k > m인 경우는 1번 턴을 진행한다.
        int size = (m % k == 0) ? m / k : (k < m) ? (m / k + 1) : 1;
        // size번 턴을 진행한 후의 1을 외칠 사람의 idx
        firstIdx = (size * k - m) % n;
        // 진행한 턴
        turn = size;
        // 남은 인원
        n -= size;
        // 1을 외칠 사람이 0보다 큰 경우
        // 0인 경우는 m이 k를 외친 경우이다. 그대로 반복문 종료
        while (firstIdx > 0) {
            // 현재 인원과 1을 외치는 사람을 보정하였을 때
            // m이 k를 외치는 경우.
            if ((n - firstIdx + 1) % k == 0) {
                // firstIdx는 0
                // 해당하는 만큼 turn을 증가.
                firstIdx = 0;
                turn += (n - firstIdx + 1) / k;
            } else {
                // 그 외의 경우.
                // 현재 인원이 k를 외치는 횟수에 한 턴을 더 진행하여, m의 순서를 넘긴다.
                size = (n - firstIdx + 1) / k + 1;
                turn += size;
                // 그 때의 1을 외치는 사람의 idx
                firstIdx = (firstIdx - 1 + size * k) % n;
                // 남은 인원
                n -= size;
            }
        }
        // m이 k를 외친 턴 출력
        System.out.println(turn);
    }
}