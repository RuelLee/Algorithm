/*
 Author : Ruel
 Problem : Jungol 3937번 Palindromic Paths (Bronze)
 Problem address : https://jungol.co.kr/problem/3937
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3937_PalindromicPaths_Bronze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    static int n;
    static char[][] map;
    static HashSet<String> hashSet;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자가 주어지고 가장 왼윗칸에서 시작하여 가장 아랫오른칸으로 이동하고자 한다.
        // 이동은 오른쪽 혹은 아래로만 가능하고, 각 격자 칸마다 알파벳이 배치되어있다.
        // 이동한 경로를 따라 만들어지는 단어가 회문인 경우를 찾고자한다.
        // 중복하지 않는 회문의 개수를 구하라
        //
        // DFS, 브루트 포스 문제
        // n이 18까지 주어지므로 완전히 브루트 포스로는 안되고 가지치기를 해야한다.
        // 최대 단어의 길이는 18 + 17 = 35이므로 전체 탐색은 불가능하다.
        // 단 우리가 구하는 단어는 회문이므로, 반이 완성된 시점에서 나머지 반의 값은 고정이되고
        // 중복하지 않으므로 고정된 것들 중에서도 만족하는 것 하나만 찾으면 된다.
        // 따라서 약 2^18으로 반 가량을 찾은 뒤, 나머지 고정된 결과를 하나만 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        n = Integer.parseInt(br.readLine());
        map = new char[n][];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();

        // 결과값 저장 공간
        hashSet = new HashSet<>();
        // bfs
        findAnswer(0, 0, new char[2 * n - 1]);
        // 해쉬셋의 크기(= 중복하지 않는 경우의 수) 출력
        System.out.println(hashSet.size());
    }

    // bfs
    // r, c까지 완성된 단어 word
    static boolean findAnswer(int r, int c, char[] word) {
        // r + c 위치에 r, c의 단어 기입
        word[r + c] = map[r][c];
        // 만약 현재 단어의 길이가 반 이상 완성된 경우
        if (r + c >= n) {
            // 현재 위치와 중앙을 기준으로한 맞은 편 문자가 일치하지 않는다면 false
            if (word[r + c] != word[2 * n - 2 - (r + c)])
                return false;
            // 일치하면서, 마지막 문자라면 해쉬셋이 추가 후, true 반환
            else if (r == n - 1 && c == n - 1) {
                hashSet.add(Arrays.toString(word));
                return true;
            } else
                return (r + 1 < n && findAnswer(r + 1, c, word)) ||
                        (c + 1 < n && findAnswer(r, c + 1, word));
            // 그 외의 경우, 일치하는 경우 하나만 찾으면 되므로 || 연산을 통해 r + 1로 탐색을 진행해 만족하는 경우를 찾고
            // 찾는다면 그대로 true 반환 없다면 c + 1로도 탐색을 진행
        }

        // 단어가 반 미만으로 완성된 경우는 r+1, c+1 모두 진행
        if (r + 1 < n)
            findAnswer(r + 1, c, word);
        if (c + 1 < n)
            findAnswer(r, c + 1, word);
        // 이 때의 결과값은 중요하지 않다. 그냥 아무 값 반환
        return true;
    }
}