/*
 Author : Ruel
 Problem : Baekjoon 1587번 이분 매칭
 Problem address : https://www.acmicpc.net/problem/1587
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1587_이분매칭;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 이분 그래프는 두 정점을 집합 A와 B로 나누고
        // A에 속한 정점과 B에 속한 정점을 잇는 간선들이 주어지는 그래프이다.
        // 정점이 An개, Bn개 주어질 때
        // 거의 이분 그래프는 여기에 Ai와 Ai+1, Bi와 Bi+1을 잇는 간선들이 각각 An - 1개, Bn - 1개씩 주어진다.
        // 이 때 최대 매칭을 구하라
        //
        // 무슨 알고리즘인지도 애매하다.
        // 문제 이름이 이분 매칭이지만 이분 매칭을 사용하지는 않는다.
        // 먼저 양쪽 정점이 짝수개씩 주어진다면 그냥 A1 - A2, ... , An-1 - An, B1 - B2, ... , Bn-1 - Bn
        // 서로 이어버리면 된다.
        // 한 쪽이 홀수일 경우도 위와 같이 매칭해주면 홀수인 쪽에 하나의 정점만 남는 경우가 최대 매칭이다.
        // 양쪽이 홀수일 경우,
        // 서로 홀수번째 정점을 잇는 간선이 존재한다면, 모든 점들을 매칭시켜줄 수 있다.
        // 따라서 각 정점에 대해 같은 집합 내에서 매칭을 이루는 걸 원칙으로 하되,
        // 양쪽이 홀수 일경우, 홀수번째끼리 잇는 간선이 존재할 경우 +1을 해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // A집합 정점의 개수
        int na = Integer.parseInt(st.nextToken());
        // B집합 정점의 개수
        int nb = Integer.parseInt(st.nextToken());
        // 간선의 수
        int m = Integer.parseInt(br.readLine());
        int[][] edges = new int[m][];
        for (int i = 0; i < edges.length; i++)
            edges[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 각 집합 내에서 매칭을 이루는 걸 원칙
        int answer = na / 2 + nb / 2;
        // 으로 하되, 양쪽이 홀수개일 경우
        if (na % 2 == 1 && nb % 2 == 1) {
        // 홀수번째끼리 잇는 간선이 존재하는지 확인
            for (int[] edge : edges) {
                if (edge[0] % 2 == 1 && edge[1] % 2 == 1) {
                    // 존재한다면 answer 1 증가
                    answer++;
                    break;
                }
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}