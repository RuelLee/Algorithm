/*
 Author : Ruel
 Problem : Baekjoon 25346번 다오와 트리플 멕스 게임
 Problem address : https://www.acmicpc.net/problem/25346
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25346_다오와트리플멕스게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 배열 A와 빈 배열 B와 C가 주어진다.
        // 두 가지 행동을 할 수 있는데
        // 1. A의 subsequence를 잡아 이들의 mex값을 배열 B 맨 뒤에 적는다.
        // 2. B의 subarray를 잡아 이들의 mex값을 C의 맨 뒤에 적는다.
        // 플레이어가 얻는 점수는 C에 존재하는 모든 원소들의 mex 값이다.
        // 최대 점수는 얼마인가?
        //
        // 조건이 많은 분기
        // 먼저 A에 0이 포함되는가가 중요하다
        // 0이 포함되지 않는다면, B에는 0밖에 추가가 되지 않고, C는 1뿐이다. 따라서 답은 0이 될 수밖에 없다
        // 그 후, 0이 포함된다면, 존재하는 모든 원소가 0인지 여부가 중요하다
        // 모든 원소가 0인 경우, B가 1밖에 추가되지 않고, 마찬가지로 C는 0이고, 답은 1로 정해진다.
        // 그 외의 경우는 0부터 연속한 수 중 가장 큰 값을 세어나가며, 가장 큰 연속한 수를 max라 할 때
        // B의 mex 값은 max + 1, C에서는 max + 2, 답은 max + 3이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 크기 n의 배열 A
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(a);

        // 가장 작은 수가 0이 아니라면
        // 답은 0이 될 수밖에 없다.
        if (a[0] != 0)
            System.out.println(0);
        else if (n == 1 || a[n - 1] == 0) // n이 1이거나, 모든 원소가 0이라면 답은 1뿐이다.
            System.out.println(1);
        else {
            // 그 외의 경우에는
            // A에서 0부터 연속한 최댓값을 구한 뒤
            // 그 값 + 3이 답이 된다.
            int current = 0;
            for (int i = 1; i < a.length; i++) {
                if (a[i] == current + 1)
                    current++;
                else if (a[i] > current + 1)
                    break;
            }
            System.out.println(current + 3);
        }
    }
}