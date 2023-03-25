/*
 Author : Ruel
 Problem : Baekjoon 24041번 성싶당 밀키트
 Problem address : https://www.acmicpc.net/problem/24041
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24041_성싶당밀키트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 밀키트에는 n개의 재료가 들어있고,
        // 각 재료에 대해 부패 속도 s, 유통 기한 l, 중요한 재료 여부 o가 주어진다
        // x일에 재료에 들어있는 세균의 수는 s * max(1, x - l)로 구한다
        // 세균 수의 합이 g 이하일 때는 안심하고 먹을 수 있다. 최대 k개의 재료를 빼서 조리한다고 할 때
        // 밀키는 구매일부터 며칠 후까지 먹을 수 있을까?
        //
        // 이분 탐색 + 우선순위큐 문제
        // x일에 대해 이분 탐색으로 최대 먹을 수 있는 날짜를 구한다.
        // 구하면서 최대 k개의 재료를 제외할 수 있으므로
        // 빼는 재료들에 대해서는 최소 힙 우선순위큐를 활용하여 
        // 가장 많은 세균 수를 갖고 있는 재료들을 k개 제외하는데 활용하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 문제에서 주어지는 재료의 개수 n, 허용되는 최대 세균의 수 g, 제외하는 재료의 수 k
        int n = Integer.parseInt(st.nextToken());
        int g = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 밀키트의 재료들
        int[][] ingredients = new int[n][];
        for (int i = 0; i < ingredients.length; i++)
            ingredients[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 날짜 범위
        // mid 값이 int 범위를 벗어날 수 있음에 유의하자.
        long start = 0;
        long end = Integer.MAX_VALUE;
        while (start <= end) {
            long mid = (start + end) / 2;
            
            // 현재 누적된 전체 세균의 수
            long totalGerms = 0;
            // 제외된 재료들의 세균 수를 최소힙 우선순위큐로 관리한다.
            PriorityQueue<Long> germsSubtracted = new PriorityQueue<>();
            for (int[] ingredient : ingredients) {
                // 허용 범위를 지났다면 더 이상 다른 재료들을 살펴보지 않고 반복문을 종료한다.
                if (totalGerms > g)
                    break;
                
                // 현재 재료에 대한 세균의 수
                long currentGerms = (long) ingredient[0] * Math.max(1, mid - ingredient[1]);

                // 중요하지 않은 재료라면 일단 우선순위큐에 추가한다.
                if (ingredient[2] == 1)
                    germsSubtracted.offer(currentGerms);
                else        // 중요한 재료라면 선택의 여지가 없이 전체 세균 수에 더한다.
                    totalGerms += currentGerms;

                // 우선순위큐에 담긴 재료의 수가 허용 개수인 k의 초과분에 대해서는
                // 해당하는 세균들의 수를 전체 세균 수에 더해준다.
                while (germsSubtracted.size() > k)
                    totalGerms += germsSubtracted.poll();
            }
            
            // 모든 재료들을 살펴본 후, 전체 세균의 수가 g이하라면
            // 더 큰 날짜에 대해서도 가능한지 살펴본다.
            if (totalGerms <= g)
                start = mid + 1;
            else        // 그렇지 않다면 더 작은 날짜에 대해서 탐색한다.
                end = mid - 1;
        }

        // 찾은 end 값이 밀키를 섭취할 수 있는 최대 날짜이다.
        // end 출력.
        System.out.println(end);
    }
}