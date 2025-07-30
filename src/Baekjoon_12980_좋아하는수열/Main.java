/*
 Author : Ruel
 Problem : Baekjoon 12980번 좋아하는 수열
 Problem address : https://www.acmicpc.net/problem/12980
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12980_좋아하는수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, s;
    static int[] arr;
    static boolean[] selected;

    public static void main(String[] args) throws IOException {
        // 1 ~ n의 수로 이루어진 길이 n의 수열이 주어진다.
        // i < j이면서 si < sj인 쌍의 개수가 점수가 된다고 한다.
        // 수열의 최대 5곳이 빈칸으로 주어지고, 점수가 주어질 때
        // 가능한 경우의 수를 출력하라
        //
        // 브루트 포스, 백트래킹 문제
        // 빈 칸이 최대 5개이므로 그리 많은 경우의 수가 필요하지 않다.
        // 따라서 브루트 포스로 모든 경우의 수를 고려해 볼 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 수열의 길이 n, 점수 s
        n = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());
        
        // 수열
        arr = new int[n];
        st = new StringTokenizer(br.readLine());
        // 사용된 수 표시
        selected = new boolean[n + 1];
        for (int i = 0; i < n; i++)
            selected[arr[i] = Integer.parseInt(st.nextToken())] = true;

        System.out.println(findAnswer(0));
    }
    
    // 백트래킹, 브루트 포스
    static int findAnswer(int idx) {
        // 모든 칸에 수를 채워넣었다면 점수를 계산하여
        // s점일 경우 1, 아닌 경우 0을 반환
        if (idx == n) {
            int score = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (arr[i] < arr[j])
                        score++;
                }
            }
            return score == s ? 1 : 0;
        }
        
        // idx 칸이 빈 칸인 경우
        // 가능한 수들을 모두 채워본다.
        if (arr[idx] == 0) {
            int sum = 0;
            for (int i = 1; i <= n; i++) {
                // i 수를 넣을 수 있다면, 해당 경우의 수를 계산.
                if (!selected[i]) {
                    arr[idx] = i;
                    selected[i] = true;
                    // 돌아온 결과값을 누적
                    sum += findAnswer(idx + 1);
                    selected[i] = false;
                    arr[idx] = 0;
                }
            }
            // 경우의 수 반환
            return sum;
        } else      // 빈 칸이 아닌 경우, 다음 수로 넘어간다.
            return findAnswer(idx + 1);
    }
}