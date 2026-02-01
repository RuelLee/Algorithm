/*
 Author : Ruel
 Problem : Baekjoon 2661번 좋은수열
 Problem address : https://www.acmicpc.net/problem/2661
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2661_좋은수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] answer;

    public static void main(String[] args) throws IOException {
        // 1 2 3 으로만 이루어진 수열이 있다.
        // 임의의 길이의 인접한 두 개의 부분 수열이 동일하다면 그 수열은 나쁜 수열이라고 부른다.
        // 그렇지 않은 수열은 좋은 수열이라고 부른다.
        // 길이 n이 주어질 때, 사전순으로 가장 앞서는 좋은 수열을 구하라
        //
        // 백트래킹 문제
        // 사전순으로 가장 앞서야하므로, 앞에 오는 숫자가 작을 수록 좋다.
        // 앞에서부터 작은 순서대로 수를 넣어보며, 현재 수열의 길이에 따라,
        // 마지막 수를 포함하는 두 개의 부분 수열로 나누어보고
        // 같은 부분수열이 인접하는지를 체크해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 수열
        int n = Integer.parseInt(br.readLine());

        // answer에 기록
        answer = new int[n];
        findAnswer(0);
        // 답 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answer.length; i++)
            sb.append(answer[i]);
        // 답 출력
        System.out.println(sb);
    }

    // 백트래킹을 통해 답을 찾는다.
    // 현재 idx번째 수를 정할 차례
    static boolean findAnswer(int idx) {
        // 모든 수를 정했다면 true 반환
        if (idx == answer.length)
            return true;

        // 1부터 3까지의 수를 넣어본다.
        for (int i = 1; i <= 3; i++) {
            answer[idx] = i;

            boolean possible = true;
            // 현재 길이 idx + 1
            // 마지막 수를 포함하는 두 개의 부분수열로 나누었을 때, (length + 1) / 2의 길이까지 두 개의 부분수열로 나눌 수 있다.
            for (int length = 1; length <= (idx + 1) / 2 && possible; length++) {
                boolean allSame = true;
                // 해당 인접한 두 부분 수열이 일치하는지 확인
                for (int j = 0; j < length; j++) {
                    if (answer[idx - length * 2 + 1 + j] != answer[idx - length + 1 + j]) {
                        allSame = false;
                        break;
                    }
                }
                // 일치한다면 다른 길이를 살펴볼 필요 없이 불가능.
                if (allSame) {
                    possible = false;
                    break;
                }
            }
            // 모두 일치 하지 않고, 이로 파생되는 경우에서 n길이의 좋은 수열을 찾는 것이 가능하다면
            // true 반환
            if (possible && findAnswer(idx + 1))
                return true;
        }
        // 못 찾았다면 false 반환
        return false;
    }
}