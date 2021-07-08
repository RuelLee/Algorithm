/*
 Author : Ruel
 Problem : Baekjoon 9177번 단어 섞기
 Problem address : https://www.acmicpc.net/problem/9177
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 단어섞기;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 처음엔 세번째 단어와 같은 길이를 갖는 int 배열을 할당하고
        // 각 글자마다 1번째 단어에서 글자를 따왔는지, 2번재 단어를 따왔는지 표시하고
        // 만약 1, 2번째 단어 모두에서 따올수 있다면, 우선적으로 1번째 단어를 따오고,
        // 현재 1번 단어의 idx, 2번 단어의 idx, 3번 단어의 idx 를 저장해둔 후,
        // 나중에 1, 2번 단어 모두랑 일치하는 문자가 없다면, 저장해둔 것을 불러와서 2번 단어의 문자를 선택하는 식으 구현했었다
        // 하지만 이런 식으로 할 경우, aaaaaaaaaaaaaa aaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaab
        // 와 같은 경우에는 너무 많은 저장 지점을 갖게 되어 풀이가 불가능했다.
        //    │"" t r e e       세번째 단어 : tcraete
        //  ─┼──────
        // "" │T
        //  c │
        //  a │
        //  t │
        // 여기서 row + col - 1은 세번째 단어의 문자 순서를 나타낸다
        // row - 1 은 첫번째 단어의 문자 순서이고, col - 1은 두번째 단어의 문자 순서이다.
        // 위와 같이 2차 boolean 배열을 선언한 뒤
        // 만약 자신의 왼쪽 칸이 T이고, 세번째 단어의 (row + col - 1)번째 글자와 두번째 단어의 (col - 1) 글자가 일치한다면 T
        // 만약 자신의 위쪽 칸이 T이고, 세번째 단어의 (row + col - 1)번재 글자와 첫번째 단어의 (row - 1) 글자가 일치한다면 T
        // 이런 식으로 점차 계산해나가는 방식이 좀 더 빠르게 답에 도달할 수 있었다.
        //    │"" t r e e       세번째 단어 : tcraete
        //  ─┼──────
        // "" │ T T F F F
        //  c │ F T T F F
        //  a │ F F T T F
        //  t │ F F F T T
        // 와 같이 채워지게 된다.
        // t -> (0, 1), c -> (1, 1), r -> (1, 2), a -> (2, 2), e -> (2, 3), t -> (3, 3), e -> (3, 4)
        Scanner sc = new Scanner(System.in);

        int testCase = sc.nextInt();
        sc.nextLine();
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            String[] words = sc.nextLine().split(" ");

            boolean[][] dp = new boolean[words[0].length() + 1][words[1].length() + 1];
            dp[0][0] = true;        // dp의 첫번째 열과 첫번째 행은 빈문자이다. T를 넣어 시작해주자.
            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j < dp[i].length; j++) {
                    if (checkArea(i - 1, j, dp) && dp[i - 1][j] && words[0].charAt(i - 1) == words[2].charAt(i + j - 1))    // 자신의 윗 칸의 T이고, 이번 순서의 문자가 첫번째 단어의 문자와 일치한다면
                        dp[i][j] = true;
                    else if (checkArea(i, j - 1, dp) && dp[i][j - 1] && words[1].charAt(j - 1) == words[2].charAt(i + j - 1))   // 자신의 왼쪽 칸이 T이고, 이번 순서의 문자가 두번째 단어의 문자와 일치한다면
                        dp[i][j] = true;
                }
            }
            sb.append("Data set ").append(t + 1).append(": ");
            sb.append(dp[dp.length - 1][dp[dp.length - 1].length - 1] ? "yes" : "no").append("\n");
        }
        System.out.println(sb);
    }

    static boolean checkArea(int r, int c, boolean[][] dp) {
        return r >= 0 && r < dp.length && c >= 0 && c < dp[r].length;
    }
}