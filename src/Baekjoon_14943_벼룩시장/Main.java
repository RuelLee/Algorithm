/*
 Author : Ruel
 Problem : Baekjoon 14943번 벼룩 시장
 Problem address : https://www.acmicpc.net/problem/14943
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14943_벼룩시장;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 벼룩시장에서 벼룩을 사고 판다.
        // 총 구매하는 벼룩과 판매하는 벼룩의 수는 일치하며
        // 인접한 가게 사이의 거리는 1이다.
        // 벼룩을 판매하는 가게는 양수로, 구매하는 가게는 음수로 주어진다
        // 두 가게 사이에 벼룩을 배달하는 비용은 거리 * 벼룩의 수 이다.
        // 모든 가게에 벼룩을 배달하고자할 때 최소 비용은?
        //
        // 그리디 문제
        // 일일이 공급과 수요를 묶어 거리 별로 생각할 필요없이
        // 순차적으로 방문을 하며
        // 판매하는 가게에서는 모든 벼룩을 구매하고, 구매하는 가게에서는 모든 벼룩을 판매한다.
        // 현재 들고 있는 것보다 많은 양의 벼룩을 가게에서 구매하더라도 음수로 처리한다.
        // 그리고 매 가게를 들릴 때마다, 현재 소지하고 있는 혹은 빚지고 있는 벼룩의 양 만큼을 총 비용에 추가해나간다.
        // 예를 들어
        // 1번 가게에서 300개의 벼룩을 판매하고 3번 가게에서 300개의 벼룩을 구매한다면
        // 각 가게를 들릴 때마다
        // 1. 300 
        // 2. 300
        // 3. 300 - 300
        // 비용이 소모되어 총 600의 비용이 청구된다. 반대인 경우도 마찬가지
        // 1번 가게에서 300개의 벼룩을 구매하고, 3번 가가에서 300개의 벼룩을 판매한다면
        // 1. -300
        // 2. -300
        // 3. -300 + 300
        // 으로 총 -600이고, 이 때의 비용은 600
        // 음수일 경우 빚지고 있는 것이고, 그 때의 크기만 취급해야하므로 -300이 아니라 사실 300임에 유의한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 각 가게의 벼룩에 대한 수요와 공급
        int[] fleas = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 총 배달 비용
        long sum = 0;
        // 현재 갖고 있는 혹은 빚지고 있는 벼룩의 양
        long carrying = 0;
        for (int flea : fleas) {
            // i번 가게의 수요 혹은 공급만큼 벼룩을 적재
            carrying += flea;
            // 현재 소유 혹은 빚진 만큼의 크기만큼을 배달 비용에 추가
            sum += Math.abs(carrying);
        }

        // 전체 배달 비용 출력
        System.out.println(sum);
    }
}