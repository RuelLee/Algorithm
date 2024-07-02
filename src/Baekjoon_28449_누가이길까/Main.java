/*
 Author : Ruel
 Problem : Baekjoon 28449번 누가 이길까
 Problem address : https://www.acmicpc.net/problem/28449
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28449_누가이길까;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 hi팀과 m명의 arc팀의 코딩 실력이 주어진다.
        // 각 팀은 서로 다른 팀의 모든 팀원들과 대전을 해, 총 n * m 회의 대전을 한다.
        // 서로 다른 두 사람이 대전을 하면, 실력이 높은 사람이 이긴다.
        // hi팀의 승리, arc팀의 승리, 무승부 횟수를 출력하라.
        //
        // 누적합 문제
        // arc팀을 실력에 따라 누적합을 통해 계산한다.
        // 그 후, hi팀 인원을 하나씩 살펴가며
        // 해당 실력보다 낮은 인원에 대해서는 승리, 같은 인원에 대해선 무승부, 더 높은 인원에 대해서는 패배한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 hi팀, m명의 arc팀
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // hi팀
        int[] hi = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // arc 팀원의 실력을 누적합으로 정리.
        int[] arc = new int[100_001];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++)
            arc[Integer.parseInt(st.nextToken())]++;
        for (int i = 1; i < arc.length; i++)
            arc[i] += arc[i - 1];
        
        long[] scores = new long[3];
        // hi 팀원을 한 명씩 살펴보며
        for (int ability : hi) {
            // 자신보다 낮은 실력에 대해서는 승리
            scores[0] += arc[ability - 1];
            // 높은 실력에 대해서는 패배
            scores[1] += arc[arc.length - 1] - arc[ability];
            // 같은 실력에 대해서는 무승부
            scores[2] += arc[ability] - arc[ability - 1];
        }
        // 답안 출력
        System.out.println(scores[0] + " " + scores[1] + " " + scores[2]);
    }
}