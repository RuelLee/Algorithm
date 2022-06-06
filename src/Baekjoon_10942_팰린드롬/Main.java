/*
 Author : Ruel
 Problem : Baekjoon 10942 팰린드롬?
 Problem address : https://www.acmicpc.net/problem/10942
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10942_팰린드롬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 자연수가 주어진다
        // 그리고 s부터 e까지가 회문인지에 대한 질문이 m개 주어진다
        // 그 때의 대답을 출력하라
        //
        // 회문은 문자열의 중앙을 기점으로 대칭을 이루는 문장이다.
        // 예를 들어 1 3 1 이나 2 4 4 2 같은 경우는 회문이라고 할 수 있다.
        // 모든 경우에 대해 회문 여부를 조사할 때, 자신보다 앞의 한자리가 적고, 뒤에 한자리가 적은
        // 문자열의 회문 여부를 참고하여 검사할 수 있다.
        // 가령 1 5 3 5 1인 경우, 5 3 5가 회문인지를 검사한 후, 양 끝에 1이 같은지 검사하면 되고, 5 3 5는 3이 회문인지를 참고하면 된다.
        // 따라서 DP를 통해 해결이 가능하다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        boolean[][] palindrome = new boolean[n][n];

        for (int i = 0; i < palindrome.length - 1; i++) {
            palindrome[i][i] = true;        // 한 글자인 경우 무조건 회문.
            if (nums[i] == nums[i + 1])     // 문자열의 문자가 2개인 경우, 같은지 확인한다.
                palindrome[i][i + 1] = true;
        }
        palindrome[n - 1][n - 1] = true;

        // 회문의 길이를 점점 늘려가며 검사한다.
        // 처음 길이는 3
        for (int diff = 2; diff < nums.length; diff++) {
            // 회문의 첫 문자 idx.
            for (int start = 0; start + diff < nums.length; start++) {
                // 회문의 끝 문자 idx.
                int end = start + diff;
                // start ~ end 문자열의 회문여부를 알아내기 위해서
                // start + 1 ~ end -1 문자열의 회문인지를 먼저 알아내고
                // 회문이라면 start 문자와 end 문자가 같은지를 확인 후 팰린드롬 여부를 결정한다.
                if (palindrome[start + 1][end - 1] &&
                        nums[start] == nums[end])
                    palindrome[start][end] = true;
            }
        }

        // m개의 쿼리.
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()) - 1;
            int e = Integer.parseInt(st.nextToken()) - 1;

            // palindrome[s][e]에 s ~ e의 문자열이 팰린드롬인지 저장되어있다.
            // 회문이라면 1, 아니라면 0 출력.
            sb.append(palindrome[s][e] ? "1" : "0").append("\n");
        }
        System.out.print(sb);
    }
}