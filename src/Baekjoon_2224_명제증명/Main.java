/*
 Author : Ruel
 Problem : Baekjoon 2224번 명제 증명
 Problem address : https://www.acmicpc.net/problem/2224
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2224_명제증명;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // p이면 q이다는 명제는 p => q로 표현된다.
        // 삼단 논법에 의해 p => q, q => r 이라면 p => r 역시 성립한다.
        // n개의 명제가 주어질 때 증명될 수 있는 모든 명제를 구해내는 프로그램을 작성하라
        //
        // 플로이드 워셜 문제
        // 명제...? 싶긴하지만 생각해보면 플로이드 워셜 문제
        // 결국 모든 명제들에 대해 p, r에 대해 두 사이를 잇는 q가 있는지 확인해야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // A ~ z까지 모든 명제들에 대해 표시할 행렬
        boolean[][] matrix = new boolean['z' - 'A' + 1]['z' - 'A' + 1];
        // 각 알파벳에 맞는 위치에 명제들을 표시
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = st.nextToken().charAt(0) - 'A';
            st.nextToken();
            int b = st.nextToken().charAt(0) - 'A';
            matrix[a][b] = true;
        }

        // 플로이드 워셜
        for (int via = 0; via < matrix.length; via++) {
            for (int start = 0; start < matrix.length; start++) {
                if (start == via)
                    continue;

                for (int end = 0; end < matrix.length; end++) {
                    // start -> via, via -> end가 성립할 때
                    // start -> end도 성립
                    if (matrix[start][via] && matrix[via][end])
                        matrix[start][end] = true;
                }
            }
        }

        // 사전순으로 살펴보며 답안을 기록한다.
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                // i -> j가 성립하면
                // count 증가, StringBuilder에 기록
                if (i != j && matrix[i][j]) {
                    count++;
                    sb.append((char) (i + 'A')).append(" ").append("=> ").append((char) (j + 'A')).append("\n");
                }
            }
        }
        // 맨 앞에 성립하는 명제의 수를 센 count를 끼워넣고
        sb.insert(0, count + "\n");
        // 전체 답안 출력
        System.out.print(sb);
    }
}