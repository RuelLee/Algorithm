/*
 Author : Ruel
 Problem : Baekjoon 8913번 문자열 뽑기
 Problem address : https://www.acmicpc.net/problem/8913
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8913_문자열뽑기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // a와 b로만 이루어진 문자열 s가 주어진다.
        // 연속한 부분 문자열이 모두 같은 문자일 경우 해당 부분 문자열을 중간에서 지울 수 있다.
        // 그럴 경우, 앞부분과 뒷부분이 만나 새로운 문자열이 된다.
        // 적절한 순서로 부분 문자열들을 지워 모든 문자열을 지울 수 있는지 계산하라
        //
        // DP문제
        // s의 길이가 25로 크지 않아 브루트포스로 해결할 수도 있으나
        // dp로도 해결 가능하여 풀어보았다.
        // 문자열을 삭제가능한 경우는
        // 1. 연속해서 같은 문자가 등장하는 경우
        // 2. i ~ j, j+1 ~ k까지의 두 문자열이 서로 삭제 가능한 경우 -> i ~ k까지의 문자열도 삭제 가능
        // 3. i ~ j까지 삭제 가능한 부분 문자열에 (i-1)문자와 j문자가 같거나 i문자와 j+1 문자가 같은 경우
        // 위 경우들을 고려해여 dp로 해결 가능

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스의 수
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            String s = br.readLine();
            // 문자열의 길이로 dp를 세운다.
            boolean[][] dp = new boolean[s.length()][s.length()];
            for (int i = 0; i < s.length() - 1; i++) {
                // 같은 문자가 연속됨을 발견하면
                if (s.charAt(i) == s.charAt(i + 1)) {
                    // 연속한 문자열 모두에 true 표시
                    for (int j = i + 1; j < s.length(); j++) {
                        if (s.charAt(i) != s.charAt(j)) {
                            i = j - 1;
                            break;
                        }
                        dp[i][j] = true;
                    }
                }
            }

            // 길이가 3인 문자열은 3 문자가 모두 일치하는 경우 밖에 없는데
            // 위의 반복문에서 처리 됐다.
            // 따라서 길이가 4이상인 문자열들에 대해 처리한다.
            for (int diff = 3; diff < s.length(); diff++) {
                // 시작 위치
                for (int start = 0; start + diff < s.length(); start++) {
                    // 만약 이미 true값이 있다면 따로 계산하지 않고 건너뛴다.
                    if (dp[start][start + diff])
                        continue;

                    // 서로 다른 연속한 두 문자열이 만나 사라지는 경우
                    for (int mid = start + 1; mid < start + diff - 1; mid++) {
                        if (dp[start][mid] && dp[mid + 1][start + diff])
                            dp[start][start + diff] = true;
                    }
                    // 삭제 가능한 문자열 앞이나 뒤에 반대편 문자와 같은 문자가 와서
                    // 삭제가 가능한 경우.
                    if (s.charAt(start) == s.charAt(start + diff) &&
                            (dp[start + 1][start + diff] || dp[start][start + diff - 1] || dp[start + 1][start + diff - 1])) {
                        dp[start][start + diff] = true;
                    }
                }
            }
            // 전체 문자열이 삭제 가능한지는 dp[0][s의 길이-1]에 기록되어있다.
            // 이번 테스트 케이스의 답 기록
            sb.append(dp[0][s.length() - 1] ? 1 : 0).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}