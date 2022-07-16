/*
 Author : Ruel
 Problem : Baekjoon 2643번 색종이 올려 놓기
 Problem address : https://www.acmicpc.net/problem/2643
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2643_색종이올려놓기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    static int[][] papers;
    static int[] memo;

    public static void main(String[] args) throws IOException {
        // n개의 색종이와 색종이의 가로, 세로 길이가 주어진다
        // 색종이를 포개되, 자신의 가로 세로 길이보다 크지 않은 색종이만 포갤 수 있다
        // 각 색종이는 90도로 회전이 가능하다.
        // 최대한 많은 색종이를 포개려고 할 때, 그 수는?
        //
        // DP, 메모이제이션, 정렬 문제
        // 먼저, 색종이들을 세로, 길이의 합으로 정렬한다.
        // 더 큰 합을 갖고 있는 색종이가 더 작은 합을 갖은 색종이 위에 포개질 일은 없기 때문이다.
        // 그리고 bottom up 방식으로 메모이제이션을 활용하여 탐색한다.
        // 대응되는 가로, 세로의 길이가 같거나 큰 색종이일 경우에만 탐색하며, 메모이제이션으로 계산 결과가 있다면 해당 결과값을 바로 참고한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 색종이의 가로, 세로 길이
        papers = new int[n][2];
        for (int i = 0; i < papers.length; i++)
            papers[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 가로, 세로 길이의 합에 따라 정렬.
        Arrays.sort(papers, Comparator.comparingInt(o -> o[0] + o[1]));

        // 메모이제이션 공간
        memo = new int[n];
        int max = 0;
        // 각 색종이를 살펴보며 해당 색종이부터 포갤 수 있는 최대 색종이의 수를 구한다.
        for (int i = 0; i < papers.length; i++)
            max = Math.max(max, findMaxOverlap(i));
        System.out.println(max);
    }

    // n번 색종이부터 포갤 수 있는 최대 색종이의 수를 구한다.
    static int findMaxOverlap(int n) {
        // 만약 계산 결과가 없다면
        if (memo[n] == 0) {
            // 최소 1장이므로 1로 초기화.
            memo[n] = 1;
            // 가로,세로 길이의 합으로 정렬했으므로, 자신보다 큰 번호의 색종이만 살펴본다.
            for (int i = n + 1; i < papers.length; i++) {
                // 대응되는 가로, 세로의 길이가 각각 더 같거나 크거나
                // 90도 돌렸을 때인, (i번의 가로, n번의 세로), (i번의 세로, n번의 가로)가 더 같거나 더 크다면
                // 포갤 수 있는 경우이므로, i번 색종이로부터 최대로 포갤 수 있는 개수 + 1개가
                // n번 색종이의 최대 포갤 수 있는 색종이의 수를 갱신하는지 확인한다.
                if ((papers[i][0] >= papers[n][0] && papers[i][1] >= papers[n][1]) ||
                        (papers[i][0] >= papers[n][1] && papers[i][1] >= papers[n][0])) {
                    memo[n] = Math.max(memo[n], findMaxOverlap(i) + 1);
                }
            }
        }
        // 최종적으로 기록된 메모이제이션 값 혹은 이미 계산되어있는 값을 반환한다.
        return memo[n];
    }
}