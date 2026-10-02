/*
 Author : Ruel
 Problem : Jungol 4183번 순열정복 7
 Problem address : https://jungol.co.kr/problem/4183
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4183_순열정복7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        // n가지 종류의 옷이 주어진다.
        // 각 종류마다 mi개의 서로 다른 옷이 주어진다.
        // 옷을 입을 수 있는 모든 경우의 수를 출력하라
        //
        // 순열, 백트래킹 문제
        // 종류마다 구분하여, 가능한 모든 경우의 수를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n종류의 옷
        n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        sb = new StringBuilder();
        // 각 옷들
        String[][] clothes = new String[n][];
        for (int i = 0; i < clothes.length; i++) {
            // 서로 다른 옷의 개수
            int m = Integer.parseInt(br.readLine());
            // 각 옷
            clothes[i] = new String[m];
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < clothes[i].length; j++)
                clothes[i][j] = st.nextToken();
        }
        findAnswer(0, clothes, new int[n]);
        // 답 출력
        System.out.print(sb);
    }

    // idx번째 종류의 옷을 하나 고른다.
    static void findAnswer(int idx, String[][] clothes, int[] selected) {
        // 모든 옷을 고른 경우
        if (idx == n) {
            // 답 작성
            sb.append('(');
            sb.append(clothes[0][selected[0]]);
            for (int i = 1; i < selected.length; i++)
                sb.append(',').append(clothes[i][selected[i]]);
            sb.append(')').append("\n");
            return;
        }

        // idx번째 옷을 고른다.
        for (int i = 0; i < clothes[idx].length; i++) {
            selected[idx] = i;
            findAnswer(idx + 1, clothes, selected);
        }
    }
}