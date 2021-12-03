/*
 Author : Ruel
 Problem : Baekjoon 17275번 부족 전쟁
 Problem address : https://www.acmicpc.net/problem/17275
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 부족전쟁;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 부족이 존재하며, m개의 부족 간 동맹이 주어진다
        // 세 개의 부족을 골라 서로 간에 모두 동맹이거나 적대 관계라면, 삼자 동맹, 삼자 적대라고 표시한다
        // 이 때 삼자 동맹이나 삼자 적대가 아닌 관계의 개수를 세어라
        // 네온 사인 문제(https://www.acmicpc.net/problem/8907) 와 동일하지만 n값과 m값이 커 Integer 범위를 벗어날 수 있다
        // 범위에 주의하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            long[] tribes = new long[n + 1];
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());        // 동맹 관계인 두 부족이 주어진다.
                tribes[Integer.parseInt(st.nextToken())]++;
                tribes[Integer.parseInt(st.nextToken())]++;
            }

            long count = 0;
            for (int i = 1; i < tribes.length; i++)
                count += tribes[i] * (n - 1 - tribes[i]);       // 한 동맹에서 동맹인 부족 * 적대인 부족 = 한 부족에서 뽑을 수 있는 삼자 동맹/삼자 적대가 아닌 관계
            // 답은 nC3에서 count/2 값을 빼준다
            // 1 - 2, 2 - 3 부족이 동맹이고 1 - 3이 적대 관계라면, 1번 부족에서 2, 3부족이 서로 동맹, 적대이므로 한번 세지고, 3번 부족에서 2, 3부족이 동맹, 적대이므로 두번 세진다. 따라서 * 1/2를 해준다.
            long answer = (long) n * (n - 1) * (n - 2) / 6 - count / 2;
            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }
}