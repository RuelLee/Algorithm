/*
 Author : Ruel
 Problem : Baekjoon 16719번 ZOAC
 Problem address : https://www.acmicpc.net/problem/16719
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16719_ZOAC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        // 문자열이 주어진다.
        // 아직 보여주지 않은 문자 중에 추가했을 때 문자열이 사전 순으로 가장 앞에 오는 문자열을 순서대로 보여주고자 한다.
        // 만약 ZOAC이라면 A -> AC -> OAC -> ZOAC 이다.
        // 해당 순서대로 출력하는 프로그램을 작성하시오.
        //
        // 재귀문제
        // 먼저 문자를 찾을 때 기준이 되는 문자의 앞부분이냐 뒷부분이느냐에 따라 다르다.
        // ZOAC일 경우, 가장 알파벳 순서가 이른 문자는 A이다.
        // A를 찾고 나서, A의 뒤에 어떤 알파벳을 붙이더라도 B로 시작하는 문자열보다 늦어질 일은 없다.
        // 따라서 가장 이른 문자 기준을 찾고, 해당 문자의 뒷부분에 대해서 위 과정을 반복한 뒤
        // 그 다음에서야 기준의 앞부분으로 넘어가야한다.
        // 따라서 재귀를 통해 범위를 좁혀나가며 찾는다.
        // 처음에는 전체 범위 -> 그 다음에는 기준부터 뒷 범위 -> ... -> 맨 처음 부터 기준까지의 범위 -> ...

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        findAnswer(0, input.length() - 1, input, new boolean[input.length()]);
        System.out.print(sb);
    }

    // 재귀를 통해 답을 찾는다.
    static void findAnswer(int startIdx, int endIdx, String input, boolean[] selected) {
        // 기준점.
        int criteria = -1;
        // start ~ end 까지 중 아직 선택되지 않은 문자 중 사전 상 가장 이른 문자를 찾는다.
        for (int i = startIdx; i <= endIdx; i++) {
            if (!selected[i] &&
                    (criteria == -1 || input.charAt(i) < input.charAt(criteria)))
                criteria = i;
        }

        // 문자를 찾는 것이 불가능했다면 해당 범위의 모든 문자를 사용한 것.
        // 끝낸다.
        if (criteria == -1)
            return;
        
        // 기준을 찾았다면 해당 문자를 선택하고
        selected[criteria] = true;
        // 현재 상태의 문자열 상태를 StringBuilder에 기록한다.
        sb.append(makeLine(input, selected)).append("\n");
        // 그리고 기준 + 1 ~ end 까지의 범위에 대해 먼저 탐색하고
        findAnswer(criteria + 1, endIdx, input, selected);
        // 위 과정이 끝나고 나서, start ~ 기준 - 1 까지의 범위에 대해 탐색한다.
        findAnswer(startIdx, criteria - 1, input, selected);
    }

    // boolean 배열을 통해 선택된 문자들로 문자열을 만든다.
    static String makeLine(String input, boolean[] selected) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < selected.length; i++)
            if (selected[i])
                line.append(input.charAt(i));
        return line.toString();
    }
}