/*
 Author : Ruel
 Problem : Baekjoon 12969번 ABC
 Problem address : https://www.acmicpc.net/problem/12969
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12969_ABC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static boolean[][][][] visited;
    static StringBuilder sb;
    static int n, k;

    public static void main(String[] args) throws IOException {
        // n과 k가 주어질 때, 다음 조건을 만족하는 문자열 S를 찾아라.
        // S의 길이는 N이고 'A', 'B', 'C'로만 이루어져 있다.
        // S에는 0 ≤ i < j < N 이면서 S[i] < S[j]를 만족하는 (i, j) 쌍이 K개가 있다.
        //
        // DP문제이긴한데.. 조금 재밌는 문제였다
        // 문자열에 문자를 하나씩 더해갈 때마다 중요한 부분은
        // 현재 A, B, C가 총 몇개가 있는가이다.
        // 만약 A가 1개, B가 2개, C가 2개인 상태에서, A를 추가한다면, 쌍의 개수는 변하지 않을 것이고
        // B를 추가한다면 A의 개수만큼, C를 추가한다면 A, B 개수의 합만큼 쌍이 증가할 것이다
        // 따라서 DP로 A, B, C의 개수를 나타내준다.
        // 하지만 A, B, C의 개수가 같더라도 그 순서에 따라 서로 다른 쌍을 가질 수 있다
        // AB는 쌍의 개수가 1이지만, BA는 쌍의 개수가 0인 것처럼..
        // 따라서 A, B, C의 개수와 현재 형성된 쌍의 개수까지 DP로 표현해주자.
        // DP를 통해 방문표시를 함으로써, 한번 방문했던 곳에 다시 방문한다면 더 이상의 추가 계산을 하지 않는다.
        // (한번 방문했다는 의미는 이미 해당 상태에서 파생되는 것들로는 k = 쌍을 만족하는 경우를 찾을 수 없다는 뜻이므로)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // visited[A의 개수][B의 개수][C의 개수][완성된 쌍의 개수]
        visited = new boolean[n + 1][n + 1][n + 1][n * (n - 1) / 2 + 1];
        sb = new StringBuilder();

        // 모두 0 0 0 0 0에서 시작.
        // 만약 true값이 돌아온다면 해당 상태의 StringBuilder 출력.
        if (findAnswer(0, 0, 0, 0, 0))
            System.out.println(sb);
        // false 값이 돌아온다면 불가능한 경우. -1 출력.
        else
            System.out.println(-1);
    }

    static boolean findAnswer(int length, int a, int b, int c, int couple) {
        // 길이가 n이고
        if (length == n) {
            // 쌍의 개수가 k라면. true 리턴
            // 아니라면 false 리턴.
            return couple == k;
// 이미 방문한 적이 있다면 false 리턴.
        } else if (visited[a][b][c][couple])
            return false;

        // 그렇지 않고 이번이 처음 방문이라면
        // 각각 A, B, C를 추가하는 경우를 계산해본다.
        // A를 추가한 경우.
        sb.append('A');
        // true가 돌아왔다면 그대로 다시 true 리턴.
        if (findAnswer(length + 1, a + 1, b, c, couple))
            return true;
        // 실패했다면 A를 지우고 B로 시도해본다.
        sb.deleteCharAt(sb.length() - 1);
        sb.append('B');
        // 성공했다면 true 리턴.
        if (findAnswer(length + 1, a, b + 1, c, couple + a))
            return true;
        // 실패했다면 B를 지우고 C로 시도해본다.
        sb.deleteCharAt(sb.length() - 1);
        sb.append('C');
        // 성공이라면 true 리턴.
        if (findAnswer(length + 1, a, b, c + 1, couple + a + b))
            return true;

        // C까지 모두 실패한 경우.
        // 마지막 글자인 C를 지우고
        sb.deleteCharAt(sb.length() - 1);
        // 방문 체크를 한 후
        visited[a][b][c][couple] = true;
        // false를 리턴한다.
        return false;
    }
}