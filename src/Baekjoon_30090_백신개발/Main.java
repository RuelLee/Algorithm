/*
 Author : Ruel
 Problem : Baekjoon 30090번 백신 개발
 Problem address : https://www.acmicpc.net/problem/30090
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30090_백신개발;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static String[] words;

    public static void main(String[] args) throws IOException {
        // n개의 단어가 주어진다.
        // n개의 단어를 이어 붙이되, 앞에 오는 단어의 마지막 k개의 글자와, 뒤에 오는 단어의 앞 k개의 글자가 일치하는 경우만 붙일 수 있다.
        // 앞에 오는 단어의 마지막 k개 글자를 지우고, 뒤에 오는 단어를 이어 붙인다. k는 최소 1이상이며, 가능한 k중 가장 큰 값에 대해 시행한다.
        // n개의 단어를 모두 이어 붙였을 때, 가장 짧은 단어의 길이는?
        //
        // 브루트 포스
        // n이 최대 9, 단어의 최대 길이가 10으로 주어지므로 모든 경우를 따지는 브루트 포스를 사용할 수 있다.
        // 앞 단어와 뒷 단어에 중복되는 글자의 최대 길이를 찾아 이어 붙이는 행동을 반복한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 단어들
        int n = Integer.parseInt(br.readLine());
        words = new String[n];
        // 총 길이
        int length = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = br.readLine();
            length += words[i].length();
        }
        
        // 브루트 포스 결과 출력
        System.out.println(bruteForce(0, 0, new boolean[words.length], new char[length]));
    }
    
    // 현재 선택한 단어의 수 selectedWords
    // 이어 붙인 단어의 길이이자, 다음 단어를 이어 붙일 위치 charIdx
    // 현재 선택된 단어를 표시하는 selected
    // 이어 붙인 단어 fullString
    static int bruteForce(int selectedWords, int charIdx, boolean[] selected, char[] fullString) {
        // 모든 단어를 이어 붙였다면 현재 길이를 반환
        if (selectedWords == words.length)
            return charIdx;
        else if (selectedWords == 0) {      // 첫번째 단어의 선택인 경우
            int min = Integer.MAX_VALUE;
            // 해당하는 단어를 가장 앞에 배치
            for (int i = 0; i < words.length; i++) {
                for (int j = 0; j < words[i].length(); j++)
                    fullString[j] = words[i].charAt(j);
                selected[i] = true;
                // 브루트포스로 다음 단어들을 선택하게 하여
                // 얻을 수 있는 길이를 반환 받아 그 중 최솟값을 찾는다.
                min = Math.min(min, bruteForce(1, words[i].length(), selected, fullString));
                selected[i] = false;
            }
            // 찾은 최소 길이 반환.
            return min;
        }

        // 그 외의 경우
        // 뒷 부분의 k개의 글자가 다음 선택하는 단어 앞 부분의 k개 글자와 일치해야한다.
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            // i번째 단어가 선택되지 않았다면
            if (!selected[i]) {
                // 일치하는 글자들의 길이는 두 단어 중 더 짧은 쪽은 길이
                // 해당 길이부터 1까지의 경우 중 가능한 경우를 찾는다.
                for (int length = Math.min(charIdx, words[i].length()); length > 0; length--) {
                    boolean possible = true;
                    for (int j = 0; j < length; j++) {
                        if (words[i].charAt(j) != fullString[charIdx - length + j]) {
                            possible = false;
                            break;
                        }
                    }
                    
                    // length개의 글자가 서로 중복되는 경우
                    if (possible) {
                        selected[i] = true;
                        // fullString에 i번째 단어를 이어 쓴다.
                        for (int j = length; j < words[i].length(); j++)
                            fullString[charIdx + j - length] = words[i].charAt(j);
                        // bruteForce 함수를 다시 call 하여
                        // 이어 붙인 단어들의 길이를 반환받아 최솟값을 갱신
                        min = Math.min(min, bruteForce(selectedWords + 1, charIdx + words[i].length() - length, selected, fullString));
                        selected[i] = false;
                        // 최대 k에 대해서만 시행하므로, break
                        break;
                    }
                }
            }
        }
        // 얻을 수 있는 이어 붙인 단어들의 최소 길이를 반환한다.
        return min;
    }
}