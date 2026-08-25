/*
 Author : Ruel
 Problem : Jungol 8608번 가방
 Problem address : https://jungol.co.kr/problem/8608
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8608_가방;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 상점에 N개의 물건이 있고, 도둑이 K개의 물건을 훔쳐간다고 한다.
        // 도둑은 편하게 훔치기 위해 가장 가벼운 물건 K개를 훔쳐간다. 물건이 K개 이하라면 모두 훔쳐간다.
        // 주인에게 1 ~ C만큼의 가방이 주어져, 상점의 물건을 집으로 가져갈 수 있고, 도둑이 훔쳐가는 무게를 최대화하고자 한다.
        // 각 경우마다 도둑이 훔치는 물건의 최대 무게 합은?
        //
        // 그리디, 슬라이딩 윈도우 문제
        // 일단 물건들을 오름차순 정렬한 뒤, 무거운 물건 K를 남겨둔 후, C에 따라 가장 가벼운 물건들을 차례대로 담아가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // N개의 물건, 훔치는 물건의 수 K, 1 ~ C까지의 가방
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        // 물건들
        int[] stuffs = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++)
            stuffs[i] = Integer.parseInt(st.nextToken());
        // 오름차순 정렬
        Arrays.sort(stuffs);

        // 가방에 담긴 물건의 무게 함
        long bagWeightSum = 0;
        // 마지막으로 담긴 물건의 idx
        int lastBagIdx = -1;

        // 도둑이 훔쳐가는 물건의 범위
        int start = 0;
        int end = 0;
        // 도둑이 훔친 물건의 무게 합
        long robberWeight = stuffs[0];
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= C; i++) {
            // 만약 가방에 여유가 있어, 다음 물건을 다음을 수 있고
            // 남은 물건의 개수가 K + 1 이상인 경우
            // 다음 물건을 가방에 담는다.
            if (i - bagWeightSum >= stuffs[lastBagIdx + 1] &&
                    N - 2 - lastBagIdx >= K) {
                bagWeightSum += stuffs[++lastBagIdx];
            }

            // 도둑이 훔치는 물건은 lastBagIdx보다 커야한다.
            // 중복되는 범위는 제거
            while (start <= lastBagIdx)
                robberWeight -= stuffs[start++];
            // 마지막으로 담는 물건은 start를 포함한 K개의 물건.
            while (end < N - 1 && end - start + 1 < K)
                robberWeight += stuffs[++end];
            // 도둑이 훔치는 무게 기록
            sb.append(robberWeight).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}