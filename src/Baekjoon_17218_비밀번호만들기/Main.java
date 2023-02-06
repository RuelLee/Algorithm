/*
 Author : Ruel
 Problem : Baekjoon 17218번 비밀번호 만들기
 Problem address : https://www.acmicpc.net/problem/17218
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17218_비밀번호만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 문자열이 주어진다.
        // 두 문자열에서 공통으로 존재하는 가장 긴 부분문자열을 만들고자한다.
        // 예를 들어
        // S'E''T''A'PPLE 
        // E'A''T'M'A'NY
        // 같이 두 문자열이 주어질 경우 ETA가 정답이 된다.
        //
        // DP 문제
        // row는 첫 문자열, col은 다음 문자열의 문자들로 매칭을 하고, 
        // dp[i][j]를 첫문자열에서 i번째 문자까지, 두번째 문자열에서 j번째 문자까지 봤을 때 최대 길이의 부분문자열의 길이라고 정하자.
        // 만약 i, j에서 두 문자열의 문자들이 서로 같다면 i-1, j-1번째 기록보다 하나 큰 값을 가질 수 있다.
        // 하지만 연속된 문자들이 아여도 상관이 없기 때문에 
        // dp[i-1][j-1]값이 dp[i-1][j]나 dp[i][j-1]보다 크다는 보장이 없다는 점에 유의하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String firstLine = br.readLine();
        String secondLine = br.readLine();
        
        // DP 설정
        // 처음 시작을 위해 문자열의 길이보다 하나씩 길게 설정하고
        // 0번 문자열은 공백이라 생각하고 시작하자.
        int[][] dp = new int[firstLine.length() + 1][secondLine.length() + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                // 문자들이 연속적일 필요는 없기 때문에
                // dp[i][j]의 초기값은 dp[i-1][j] 나 dp[i][j-1] 중 더 큰 값을 가져오자.
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                // 만약 i, j번째 문자들이 서로 같다면 dp[i-1][j-1]의 값과 현재 있는 dp[i][j]값을 비교해 큰 값을 남겨둔다.
                if (firstLine.charAt(i - 1) == secondLine.charAt(j - 1))
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
            }
        }

        // 스택을 통해 문자들을 역추적하자.
        Stack<Integer> stack = new Stack<>();
        int r = dp.length - 1;
        int c = dp[r].length - 1;
        // r, c가 0보다 같거나 클 때까지.
        while (r >= 0 && c >= 0) {
            // 같은 값을 갖는 DP들 중 가장 좌측으로 이동한다.
            while (c > 0 && dp[r][c] == dp[r][c - 1])
                c--;
            // 같은 값을 갖는 DP들 가장 위로 이동한다.
            while (r > 0 && dp[r][c] == dp[r - 1][c])
                r--;
            // 해당하는 위치의 r값을 stack에 담는다.
            stack.push(r);
            // r, c 둘 다 값 감소.
            r--;
            c--;
        }
        // 최종적으로 최상단에는 0이 담겨있지만 필요없는 인덱스이다.
        // 0번은 공백이라 지정했으므로
        stack.pop();

        // 스택에 저장되어있는 r값들을 바탕으로 첫번째 문자열에서 해당하는 문자들을 뽑아
        // 가장 긴 길이의 부분 문자열을 만들어낸다.
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty())
            sb.append(firstLine.charAt(stack.pop() - 1));
        System.out.println(sb);
    }
}