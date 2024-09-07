/*
 Author : Ruel
 Problem : Baekjoon 15554번 전시회
 Problem address : https://www.acmicpc.net/problem/15554
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15554_전시회;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 미술품이 주어진다.
        // 미술품은 크기 A와 가치 B로 나뉜다.
        // 전시한 미술품 중 가장 큰 크기를 Amax, 가장 작은 크기를 Amin
        // 전시한 미술품들의 가치합을 S라 할 때
        // 전시회의 가치를 S - (Amax - Amin)이라고 한다.
        // 전시회의 가치를 가장 크게하고자 할 때, 그 값은?
        //
        // 그리디, 정렬, 누적합 문제
        // 크기의 범위가 주어진다면, 해당하는 크기에 속하는 미술품들은 모두 전시되는 것이 유리하다.
        // 따라서 그림은 크기 순으로 정렬을 한다.
        // 먼저 가치는 S - (Amax - Amin)라고 주어지므로
        // S는 누적합을 통해 빠르게 구하자.
        // i >= j일 때 i ~ j까지 속하는 그림들의 가치를 나타내면
        // Si - Sj-1 - (Ai - Aj)로 나타낼 수 있고, 
        // (Si - Ai) - (Sj-1 + Aj)로 다시 쓸 수 있다.
        // i는 순차적으로 살펴보며, 등장했던 j들 중 Sj-1 + Aj가 최소인 값을 찾아가며 값을 계산해나가면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 미술품
        int n = Integer.parseInt(br.readLine());

        long[][] paintings = new long[n + 1][2];
        for (int i = 1; i < paintings.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            paintings[i][0] = Long.parseLong(st.nextToken());
            paintings[i][1] = Long.parseLong(st.nextToken());
        }
        // 크기순 정렬
        Arrays.sort(paintings, Comparator.comparingLong(o -> o[0]));
        // 가치 누적합 처리
        for (int i = 1; i < paintings.length; i++)
            paintings[i][1] += paintings[i - 1][1];
        
        // 전시회의 가치 최대값
        long max = 0;
        // Sj-1 + Aj의 최소값
        long min = Long.MAX_VALUE;
        for (int i = 1; i < paintings.length; i++) {
            // min이 i일 때, 성립하는지 계산
            min = Math.min(min, paintings[i - 1][1] - paintings[i][0]);
            // Ai ~ min까지 계산했을 때, 전시회의 가치 총합 계산 후, 최대값 갱신 여부 확인
            max = Math.max(max, paintings[i][1] - paintings[i][0] - min);
        }
        // 답 출력
        System.out.println(max);
    }
}