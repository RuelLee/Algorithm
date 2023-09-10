/*
 Author : Ruel
 Problem : Baekjoon 13269번 쌓기나무
 Problem address : https://www.acmicpc.net/problem/13269
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13269_쌓기나무;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 공간에 쌓기 나무가 놓여있는 모양을
        // 위, 앞, 오른쪽 옆에서 본 모습을 보고 쌓기 나무가 쌓인 모양을 유추한다.
        // 그러한 모양이 여러개라면 가장 많이 쌓기 나무가 사용된 것을 출력한다.
        //
        // 그리디 문제
        // 위에서 본 모양에 아무 블럭도 없다면, 그 공간엔 블럭을 놓아서는 안된다.
        // 위에서 본 모양에 쌓기 블럭이 있다면
        // 앞에서 본 모양에서 같은 열과 오른쪽 옆에서 본 모양에서 같은 행을 찾아
        // 두 값 중 더 작은 값을 놓는다. 더 많은 개수가 쌓여있다면 해당 수가 나왔어야하기 때문.
        // 그렇게 모든 블럭을 쌓고,
        // 마지막으로 쌓인 블럭을 보고, 다시 앞에서 본 모양과 옆에서 본 모양과 비교하며 올바르게 놓였는지 확인한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 공간
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 위에서 본 모습
        int[][] upSide = new int[n][];
        for (int i = 0; i < n; i++)
            upSide[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 앞에서 본 모습
        int[] frontSide = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 오른쪽 옆에서 본 모습
        int[] rightSide = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 답안 작성
        int[][] answer = new int[n][m];
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[i].length; j++) {
                // 위에서 본 모습이 0이라면 그 공간엔 쌓기 나무가 놓일 수 없다.
                if (upSide[i][j] == 0)
                    continue;

                // 그렇지 않다면, 앞에서 본 모양의 열과 오른쪽 옆에서 본 모습의 행 값과 비교하여
                // 더 적은 값을 준다.
                answer[i][j] = Math.min(frontSide[j], rightSide[n - 1 - i]);
            }
        }

        // 오른쪽에서 본 모양과 일치하는지 확인
        boolean correct = true;
        for (int i = 0; i < rightSide.length; i++) {
            if (Arrays.stream(answer[i]).max().getAsInt() != rightSide[n - i - 1]) {
                correct = false;
                break;
            }
        }

        // 앞에서 본 모양과 일치하는지 확인.
        for (int col = 0; col < m; col++) {
            int max = 0;
            for (int row = 0; row < n; row++)
                max = Math.max(max, answer[row][col]);
            if (!correct || max != frontSide[col]) {
                correct = false;
                break;
            }
        }
        
        // 일치하지 않는다면
        // 불가능한 경우. -1 출력
        if (!correct)
            System.out.println(-1);
        else {
            // 일치한다면 그대로 답안을 작성해 출력한다.
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < answer.length; i++) {
                for (int j = 0; j < answer[i].length; j++)
                    sb.append(answer[i][j]).append(" ");
                sb.deleteCharAt(sb.length() - 1);
                sb.append("\n");
            }
            // 답안 출력
            System.out.print(sb);
        }
    }
}