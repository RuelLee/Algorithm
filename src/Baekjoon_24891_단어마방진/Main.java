/*
 Author : Ruel
 Problem : Baekjoon 24891번 단어 마방진
 Problem address : https://www.acmicpc.net/problem/24891
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24891_단어마방진;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이가 l인 n개의 단어들이 주어진다.
        // 이들을 적절히 선택 배치하여, l * l 크기의 단어 마방진을 만들라
        // 단어마방진은 가로와 세로로 읽었을 때, 순서와 단어가 일치하는 행렬이다
        // ex)
        // HEART
        // EMBER
        // ABUSE
        // RESIN
        // TREND
        //
        // 브루트포스, 백트래킹 문제
        // 단어들을 이전에 배치한 단어와 단어 마벙진으로써 모순이 발생하는지 확인하며
        // 배치해간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 l인 n개의 단어
        int l = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        String[] words = new String[n];
        for (int i = 0; i < words.length; i++)
            words[i] = br.readLine();
        // 사전순으로 가장 이른 단어 마방진이어야하므로 정렬
        Arrays.sort(words);

        String[] answer = new String[l];
        StringBuilder sb = new StringBuilder();
        // 가능하다면
        // 순서대로 답안 작성
        if (bruteforce(0, l, answer, 0, words)) {
            for (String a : answer)
                sb.append(a).append("\n");
        } else      // 불가능할 경우 NONE 기록
            sb.append("NONE").append("\n");
        // 답안 출력
        System.out.print(sb);
    }

    static boolean bruteforce(int idx, int end, String[] answer, int bitmask, String[] words) {
        // end개의 단어를 모두 배치했다면 가능한 경우
        // true 반환
        if (idx == end)
            return true;

        // idx번째 단어로 적합한 단어를 찾는다.
        for (int i = 0; i < words.length; i++) {
            // 이미 선택했다면 건너뛴다.
            if ((bitmask & (1 << i)) != 0)
                continue;

            boolean possible = true;
            // 단어 마방진으로써
            // 이전에 선택한 단어들과 모순이 발생하는지 확인한다.
            for (int j = 0; j < idx; j++) {
                if (answer[j].charAt(idx) != words[i].charAt(j)) {
                    possible = false;
                    break;
                }
            }
            // 모순이 발생하지 않는다면
            if (possible) {
                // idx번째에 i번 단어 선택
                answer[idx] = words[i];
                // 그 후 end번째까지 진행할 수 있다면
                // true 반환.
                if (bruteforce(idx + 1, end, answer, bitmask | (1 << i), words))
                    return true;
            }
        }
        // true로 반환되어 메소드가 끝나지 않고 여기까지 왔다면
        // idx번째 단어를 선택하는 것이 불가능한 경우
        // false 반환.
        return false;
    }
}