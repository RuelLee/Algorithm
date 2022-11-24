/*
 Author : Ruel
 Problem : Baekjoon 18119번 단어 암기
 Problem address : https://www.acmicpc.net/problem/18119
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18119_단어암기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 단어와 m개의 쿼리가 주어진다.
        // 쿼리는
        // 1 x : 알파벳 x를 잊는다
        // 2 x : 알파벳 x를 기억해낸다.
        // 단어의 모든 알파벳을 알고 있을 때, 해당 단어를 안다고 할 때
        // 각 쿼리마다 아는 단어의 개수를 출력하라.
        //
        // 비트마스킹 문제
        // n이 최대 10_000개, m이 최대 50_000개, 단어의 길이가 최대 1000으로 주어지므로
        // 일일이 계산해서는 안된다.
        // 따라서 비트마스킹을 통해 해당 단어에 속해있는 알파벳의 유무만 비트마스킹해준다.
        // 그 후, 내가 기억하고 있는 단어의 상태 또한 비트마스킹을 통해 관리하면서
        // 단어와 내가 기억하고 있는 단어를 & 연산해서 단어가 나올 경우, 해당 단어를 알고 있는 경우이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 단어들을 비트마스킹을 통해 관리한다.
        int[] words = new int[n];
        for (int i = 0; i < words.length; i++)
            words[i] = stringToBitcode(br.readLine());

        // 현재 내가 기억하고 있는 알파벳들을 비트마스킹을 통해 관리한다.
        int memoBitcode = (1 << 27) - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            char x = st.nextToken().charAt(0);
            // 해당 알파벳을 잊는 경우
            // 해당하는 1을 0으로 바꿔주어야하므로
            // xor 연산을 사용한다.
            if (o == 1)
                memoBitcode ^= 1 << (x - 'a');
            // 해당 알파벳을 기억하는 경우
            // 해당 0을 1로 바꿔주어야하므로
            // or 연산을 사용한다.
            else
                memoBitcode |= 1 << (x - 'a');

            // 기억 상태가 갱신되었다면 단어들과 memoBitcode를 비교해나간다.
            int count = 0;
            for (int word : words) {
                // word & memoBitcode가 word로 나올 경우
                // 해당 단어를 알고 있는 상태
                // count 증가.
                if ((word & memoBitcode) == word)
                    count++;
            }
            // 알고 있는 단어의 수 기록.
            sb.append(count).append("\n");
        }
        // 전체 결과 출력.
        System.out.println(sb);
    }

    // 단어를 비트마스킹을 통해 bitcode로 바꿔준다.
    static int stringToBitcode(String s) {
        int bitcode = 0;
        // 해당하는 알파벳을 bitcode에 기록해나간다.
        for (int i = 0; i < s.length(); i++)
            bitcode |= (1 << (s.charAt(i) - 'a'));
        // 최종값 반환.
        return bitcode;
    }
}