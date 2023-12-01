/*
 Author : Ruel
 Problem : Baekjoon 14369번 전화번호 수수께끼 (Small)
 Problem address : https://www.acmicpc.net/problem/14369
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14369_전화번호수수께끼_Small;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] nums;

    public static void main(String[] args) throws IOException {
        // 전화번호를 영어단어로 바꾸고, 철자를 바꾼 문자열 s가 주어진다.
        // 전화번호는 수의 크기에 따라 오름차순으로 정렬되어있다.
        // t개의 s가 주어질 때
        // 해당하는 전화번호들을 구하라
        //
        // 브루트포스, 백트래킹 문제
        // 각 수를 영어단어로 바꿨을 때, 알파벳의 개수를 세고
        // s에 해당하는 알파벳이 충분히 있다면 배정해나가다, 더 이상 배정할 수 없다면
        // 다시 뒤로 돌아가는 식의 백트래킹을 사용한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 수들의 철자
        String[] numbers = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};
        // 수들을 알파벳의 개수로 바꾼다.
        nums = new int[10][26];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length(); j++)
                nums[i][numbers[i].charAt(j) - 'A']++;
        }
        
        // 테스트 케이스의 수
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // 주어지는 문자열s
            String s = br.readLine();
            // 문자열을 알파벳의 개수로 바꾼다.
            int[] alphabets = new int[26];
            for (int i = 0; i < s.length(); i++)
                alphabets[s.charAt(i) - 'A']++;
            
            // 해당하는 답을 찾아 기록
            sb.append("Case #").append(t + 1).append(": ").append(findAnswer(alphabets, new int[10])).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 백트래킹을 사용하여 해당하는 전화번호를 찾는다.
    static StringBuilder findAnswer(int[] alphabets, int[] pickedNum) {
        // 남은 알파벳을 모두 사용했다면, 답을 찾은 경우.
        if (Arrays.stream(alphabets).sum() == 0) {
            // 오름차순으로 정렬되어있으므로
            // 작은 수부터 차례대로 이어붙여 문자열을 만든다.
            StringBuilder sb = new StringBuilder();
            int idx = 0;
            while (idx < pickedNum.length) {
                if (pickedNum[idx] > 0) {
                    pickedNum[idx]--;
                    sb.append(idx);
                } else
                    idx++;
            }
            // 찾은 전화번호를 반환.
            return sb;
        }

        // 아직 남은 알파벳이 존재하는 경우.
        // 고를 수 있는 수가 있는지 확인한다.
        for (int i = 0; i < nums.length; i++) {
            boolean possible = true;
            for (int j = 0; j < alphabets.length; j++) {
                // 알파벳이 부족해 i번째 수를 배정할 수 없는 경우.
                if (alphabets[j] < nums[i][j]) {
                    possible = false;
                    break;
                }
            }

            // 배정하는 것이 가능한 경우.
            if (possible) {
                // 해당하는 알파벳들을 남은 알파벳에서 빼주고
                for (int j = 0; j < alphabets.length; j++)
                    alphabets[j] -= nums[i][j];
                // i번째 수를 선택했다는 표시.
                pickedNum[i]++;
                // 남은 알파벳과, 선택한 수를 인자로 함수를 재귀호출.
                StringBuilder returned = findAnswer(alphabets, pickedNum);
                // 만약 돌아온 StringBuilder가 내용이 있다면 답을 찾은 경우.
                // 해당하는 값을 리턴한다.
                if (!returned.isEmpty())
                    return returned;

                // 그렇지 않다면 답을 찾지 못한 경우.
                // 다시 사용한 알파벳들 복구
                for (int j = 0; j < alphabets.length; j++)
                    alphabets[j] += nums[i][j];
                // 고른 수 또한 다시 복구.
                pickedNum[i]--;
            }
        }
        // 답을 찾지 못했다면 비어있는 StringBuilder 반환.
        return new StringBuilder();
    }
}