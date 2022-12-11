/*
 Author : Ruel
 Problem : Baekjoon 2258번 정육점
 Problem address : https://www.acmicpc.net/problem/2258
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2258_정육점;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 주인공은 m 무게의 고기를 사려고 한다.
        // 정육점은 세일을 하고 있으며, n개의 덩어리로 미리 고기를 나누어 팔고 있다.
        // n개의 덩어리에 대해 무게, 가격이 주어진다.
        // 또한 어떤 덩어리를 샀을 때, 해당 가격보다 싼 덩어리들은 얼마든지 추가 비용 없이 얻을 수 있다고 한다.
        // m 무게의 고기를 구매하는데 드는 최소 비용은?
        //
        // 그리디 문제
        // 가격 오름차순, 무게 내림차순으로 살펴보며 m 무게를 만족시키는 최소 가격을 찾는다.
        // 한가지 더 고려해야할 점은, 구매한 덩어리 보다 '싼' 덩어리만 무료로 얻을 수 있으므로
        // 같은 가격의 덩어리는 구매해야한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 처리
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 고기들
        int[][] meats = new int[n][];
        for (int i = 0; i < meats.length; i++)
            meats[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 덩어리에 대해, 우선적으로 가격 오름차순, 가격이 같다면 무게 내림차순으로 정렬한다.
        Arrays.sort(meats, (o1, o2) -> {
            if (o1[1] == o2[1])
                return Integer.compare(o2[0], o1[0]);
            return Integer.compare(o1[1], o2[1]);
        });
        
        // 현재 계산한 고기의 총 무게
        int meatSum = 0;
        // 총 가격
        int costSum = 0;
        // 가장 비싼 덩어리의 가격
        int maxCost = 0;
        // 답안
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < meats.length; i++) {
            // 만약 이전 덩어리와 이번 덩어리의 가격이 같다면
            // 이전 덩어리들을 그냥 얻을 수 없으므로, 이번 덩어리의 가격을 추가한다.
            if (maxCost == meats[i][1])
                costSum += meats[i][1];
            // 만약 이번 덩어리가 더 비싸다면 이전 덩어리들은 모두 얻을 수 있다.
            // 따라서 가격은 이번 덩어리의 가격이 된다.
            else
                maxCost = costSum = meats[i][1];
            // 구매한 고기 덩어리의 무게 증가.
            meatSum += meats[i][0];

            // 만약 구매한 고기의 총 무게가 m을 넘었다면
            if (meatSum >= m) {
                // 이전에 기록한 가격보다 더 적은 가격인지 확인한다.
                answer = Math.min(answer, costSum);
                // 이번이 첫 덩어리이거나, 이전 덩어리의 가격보다 현재 덩어리의 가격이 더 비쌀 경우
                // (= 덩어리들끼리 같은 가격으로 쌓인 가격이 아닌, 이번 덩어리로 이전 덩어리들을 모두 덤으로 얻은 경우일 경우)
                // 같은 가격의 여러 덩어리를 구매하는 것보다 하나의 비싼 덩어리를 사는 것이 더 저렴할 수 있으므로
                // 반복 종료.
                if (i == 0 || meats[i - 1][1] < meats[i][1])
                    break;
            }
        }
        // 최종적으로 m 이상의 무게를 달성했는지 살펴보고
        // 답안 출력.
        System.out.println(meatSum < m ? -1 : answer);
    }
}