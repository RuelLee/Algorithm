/*
 Author : Ruel
 Problem : Baekjoon 18233번 러버덕을 사랑하는 모임
 Problem address : https://www.acmicpc.net/problem/18233
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18233_러버덕을사랑하는모임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n, p, e;
    static int[][] members;

    public static void main(String[] args) throws IOException {
        // 러사모 회원은 n명이 있으며 러버덕은 e개 갖고 있다.
        // 각 러사모 회원은 받을 수 있는 러버덕의 개수에 대한 범위가 있고,
        // 이 중 정확히 p명에게 e개의 러버덕을 모두 나눠주려고 한다.
        // 가능한 경우 중 하나를 출력한다.
        // 불가능하다면 -1을 출력한다.
        //
        // 브루트 포스 문제
        // n이 20개 이하로 크지 않으므로 각 회원들에게 러버덕을 주거나 주지 않았을 때를
        // 모두 계산하는 것이 가능하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 회원
        n = Integer.parseInt(st.nextToken());
        // 나눠주려는 인원 p명
        p = Integer.parseInt(st.nextToken());
        // 러버덕의 개수 e
        e = Integer.parseInt(st.nextToken());

        // 각 회원이 받는 러버덕의 개수에 대한 범위
        members = new int[n][];
        for (int i = 0; i < n; i++)
            members[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // bruteForce에 대한 결과값
        // {비트마스크, 최소한도로만 나눠줬을 때 남는 개수}
        int[] result = bruteForce(0, 0, 0, 0, 0);
        StringBuilder sb = new StringBuilder();
        // 만약 bitmask가 0이 돌아왔다면 불가능한 경우.
        // -1 출력
        if (result[0] == 0)
            sb.append(-1);
        else {
            // 비트마스크 값을 보고 나눠줄 러버덕의 개수를 정한다.
            for (int i = 0; i < members.length; i++) {
                // 표시가 안되어있다면 0개
                if ((result[0] & (1 << i)) == 0)
                    sb.append(0).append(" ");
                else {
                    // 표시가 되어있다면
                    // i번째 사람이 받을 수 있는 범위와,
                    // 최소한도로 나눠줬을 때의 남는 개수(result[1])과 비교하여 줄 수 있는 가장 많은
                    // 러버덕을 준다.
                    int added = Math.min(members[i][1] - members[i][0], result[1]);
                    // 그 때의 개수 기록
                    sb.append(members[i][0] + added).append(" ");
                    // result[1]에서 더 나눠준 개수만큼을 차감
                    result[1] -= added;
                }
            }
            // 마지막 공백 문자 삭제
            sb.deleteCharAt(sb.length() - 1);
        }
        // 답안 출력
        System.out.println(sb);
    }

    // bruteForce
    // 비트마스킹을 통해 러버덕을 받는 사람을 표시한다.
    static int[] bruteForce(int idx, int bitmask, int selected, int start, int end) {
        // 마지막 사람까지 모두 분배가 끝났다면
        if (idx == members.length) {
            // 현재 범위가 e를 포함하는지,
            // 선택된 인원이 정확히 p명이 맞는지 확인하고
            // 맞다면 해당하는 비트마스킹 값과 최소한도로 나눠줬을 때의 잉여분을 반환한다.
            if (e >= start && e <= end && selected == p)
                return new int[]{bitmask, e - start};
            // 불가능한 경우 0만 반환.
            return new int[]{0};
        }

        int[] answer;
        // 아직 선택한 인원이 p명 미만일 경우
        if (selected < p) {
            // idx번째 인원을 포함시켰을 때의 결과값을 받는다.
            answer = bruteForce(idx + 1, bitmask | (1 << idx), selected + 1, start + members[idx][0], end + members[idx][1]);
            // 값이 0 이상이라면 가능한 경우.
            // 해당 값을 바로 반환.
            if (answer[0] > 0)
                return answer;
        }

        // 선택하지 않았을 때의 결과값을 받아 반환한다.
        return bruteForce(idx + 1, bitmask, selected, start, end);
    }
}