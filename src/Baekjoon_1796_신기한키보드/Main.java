/*
 Author : Ruel
 Problem : Baekjoon 1796번 신기한 키보드
 Problem address : https://www.acmicpc.net/problem/1796
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1796_신기한키보드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 키보드에는 버튼 3개와 LCD 창이 하나 달려있다.
        // 그리고 LCD 창에는 문자열 S가 쓰여있다.
        // 버튼은 왼쪽, 오른쪽, 엔터 키이다.
        // LCD에 적혀있는 문자들을 알파벳 순서대로 적고자할 때
        // 눌러야하는 버튼의 최소 수는?
        //
        // DP 문제
        // 알파벳을 넘어가는 건 어떻게 처리해야하나 고민했는데 그냥 무시하면 됐다.
        // 가령 ab라는 문자열이 있다면
        // 엔터 -> 오른쪽 -> 엔터
        // 와 같이 3이 답이다.
        // 알파벳 순서대로 처리를 하므로 문자열을 알파벳대로 분류한다.
        // 등장횟수와, 가장 먼저 등장한 위치, 가장 마자막에 등장한 위치.
        // 최소 버튼으로 처리해야하므로, 등장 횟수만큼은 엔터키를 치게 될 것이고
        // 시작위치와 종료위치의 길이만큼 오른쪽 내지 왼쪽 키를 눌러야한다.
        // 그렇다면 내가 이 알파벳을 왼쪽에서 오른쪽으로 처리할지, 오른쪽에서 왼쪽으로 처리할지에 따라서
        // 다음 알파벳을 처리할 때의 커서 위치가 달라지게 된다.
        // 위 경우를 고려한 DP를 세우고 처리하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        
        // 알파벳
        int[][] alphabets = new int[26][3];
        for (int i = 0; i < s.length(); i++) {
            int order = s.charAt(i) - 'a';
            // 만약 처음 등장한 알파벳이라면
            if (alphabets[order][0] == 0) {
                // 시작과 종료 위치 초기화
                alphabets[order][1] = Integer.MAX_VALUE;
                alphabets[order][2] = Integer.MIN_VALUE;
            }
            // 알파벳의 등장 횟수
            alphabets[order][0]++;
            // 첫 등장 위치
            alphabets[order][1] = Math.min(alphabets[order][1], i);
            // 마지막 등장 위치
            alphabets[order][2] = Math.max(alphabets[order][2], i);
        }
        
        // 각 알파벳을 처리하는데까지 드는 최소 키보드 입력 횟수.
        int[][] counts = new int[26][2];
        // 일단 큰 값으로 초기화
        for (int[] c : counts)
            Arrays.fill(c, Integer.MAX_VALUE);
        
        // a알파벳에 대한 처리
        // a 알파벳이 등장하지 않았다면 alphabets[0] 배열의 모든 값이 0으로 채워져있으므로
        // 어차피 아래 값들도 0으로 채워진다.
        // counts[i][0]은 i번째 알파벳을 왼쪽에서 오른쪽으로 처리할 때
        // counts[i][1]은 오른쪽에서 왼쪽으로 처리할 때이다
        // 현재 커서의 위치는 0 위치이므로 a알파벳의 등장 횟수 + 길이만 더해주면 된다.
        counts[0][0] = alphabets[0][0] + alphabets[0][2];
        // 현재 커서의 위치를 마지막 등장 위치로 이동시키고, 처음 위치로 이동시키면서 입력하는 경우
        // 이동 + 길이 + 엔터 입력 횟수
        counts[0][1] = alphabets[0][2] + alphabets[0][2] - alphabets[0][1] + alphabets[0][0];
        // b부터는 반복문을 통해 처리한다.
        for (int i = 1; i < counts.length; i++) {
            // 만약 i번째 알파벳이 등장하지 않았다면
            if (alphabets[i][0] == 0) {
                // counts[i]에 counts[i-1]값을 그대로 가져와 연속성을 갖게 한다.
                for (int j = 0; j < counts[i].length; j++)
                    counts[i][j] = counts[i - 1][j];
                // 알파벳도 마찬가지.
                // 이전 알파벳의 시작, 종료 위치를 참조하므로
                // alphabets[i]에 alphabets[i-1] 값을 그대로 가져온다.
                for (int j = 0; j < alphabets[i].length; j++)
                    alphabets[i][j] = alphabets[i - 1][j];
            } else {
                // 알파벳이 등장한 경우
                // i번째 알파벳이 등장한 길이(마지막 위치 - 첫 위치)
                int length = alphabets[i][2] - alphabets[i][1];
                // 왼쪽에서 오른쪽으로 처리하는 경우
                // 이전 알파벳의 왼 -> 오, 오 -> 왼을 모두 고려한다.
                // 이전 알파벳의 왼 -> 오인 경우, 마지막 위치에서 이번 알파벳의 첫 위치까지 이동 시켜야하고
                // 이전 알파벳의 오 -> 왼인 경우, 첫 위치에서 이번 알파벳의 첫 위치까지 커서를 이동해야한다.
                // 해당 이동을 고려하고 + 길이 + 이번 알파벳의 개수를 한 값을 기록한다.
                counts[i][0] = Math.min(counts[i - 1][0] + Math.abs(alphabets[i - 1][2] - alphabets[i][1]),
                        counts[i - 1][1] + Math.abs(alphabets[i - 1][1] - alphabets[i][1])) + length + alphabets[i][0];
                // 오른쪽에서 왼쪽으로 처리하는 경우
                // 위 경우와 마찬가지로 계산한다.
                counts[i][1] = Math.min(counts[i - 1][0] + Math.abs(alphabets[i - 1][2] - alphabets[i][2]),
                        counts[i - 1][1] + Math.abs(alphabets[i - 1][1] - alphabets[i][2])) + length + alphabets[i][0];
            }
        }
        // 가장 마지막 알파벳까지 처리했을 때, 가장 적은 이동 횟수를 출력한다.
        System.out.println(Arrays.stream(counts[25]).min().getAsInt());
    }
}