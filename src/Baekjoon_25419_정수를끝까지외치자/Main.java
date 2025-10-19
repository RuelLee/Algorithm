/*
 Author : Ruel
 Problem : Baekjoon 25419번 정수를 끝까지 외치자
 Problem address : https://www.acmicpc.net/problem/25419
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25419_정수를끝까지외치자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] memo;
    static int k;

    public static void main(String[] args) throws IOException {
        // 두 학생이 n이하의 번호를 순서대로 외친다.
        // 처음의 경우, 1 ~ k까지의 번호 a를 외치고
        // 그 이후로는 a+1 ~ a + k까의 번호를 외칠 수 있다.
        // 더 이상 번호를 외치 못하는 사람이 진다.
        // 두 사람이 외칠 수 없는 번호 리스트가 주어진다.
        // 두 사람이 최적의 방법으로 게임을 할 때
        // 첫번째 학생이 이기면 1, 두번째 학생이 이기면 0을 출력한다.
        //
        // 게임 이론, 메모이제이션
        // 두 사람이 서로 최적의 방법으로 게임을 하기 때문에
        // 한 번호를 외쳤을 때, 상대방이 외칠 수 있는 어떤 수를 외치더라도 질 때만 내가 이길 수 있다.
        // memo 배열을 선언하고, memo[idx]를 내가 idx를 외쳤을 때, 외친 사람이 이기는지를 기록해둔다.
        // 그리고 1 ~ k 내에 이길 수 있다면, 1을 그렇지 않다면 0을 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 숫자의 범위 n
        // 외칠 수 있는 숫자의 간격 k
        int n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // 1 ~ n까지의 수를 외칠 수 있다.
        memo = new int[n + 1];
        // 외칠 수 없는 수 처리
        st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens())
            memo[Integer.parseInt(st.nextToken())] = -1;

        boolean answer = false;
        // 1 ~ k까지의 수 중 이길 수 있는 수가 하나라도 있다면
        // 첫번째 학생이 이긴다.
        for (int i = k; i > 0; i--) {
            if (findAnswer(i) == 1)
                answer = true;
        }
        // 답 출력
        System.out.println(answer ? 1 : 0);
    }
    
    // 현재 current를 외쳤을 때
    // 이기는지 여부
    static int findAnswer(int current) {
        // current가 범위를 벗어나면 무조건 진다.
        if (current >= memo.length)
            return -1;
        // 이미 기록된 결과가 있다면 결과 참조
        else if (memo[current] != 0)
            return memo[current];

        memo[current] = 1;
        // 상대방은 current + 1 ~ current + k까지의 수를 외칠 수 있다.
        // 그 중 하나라도 이길 수 있는 수가 있다면 상대방은 그 수를 외칠 것이다.
        // 따라서 모든 수가 질 때만, current를 외친 사람이 이길 수 있다.
        for (int next = current + k; next > current; next--) {
            if (findAnswer(next) == 1) {
                memo[current] = -1;
                break;
            }
        }
        // 결과값 반환
        return memo[current];
    }
}