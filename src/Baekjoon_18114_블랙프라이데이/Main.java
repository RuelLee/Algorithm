/*
 Author : Ruel
 Problem : Baekjoon 18114번 블랙 프라이데이
 Problem address : https://www.acmicpc.net/problem/18114
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18114_블랙프라이데이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] stuffs;

    public static void main(String[] args) throws IOException {
        // 최대 3개의 물건을 골라, 무게의 합이 정확히 c라면 해당 상품들을 만원에 준다고 한다.
        // n개의 물건들에 대한 무게들이 주어질 때, 만원에 구매할 수 있는 조합이 존재하는지 구하라
        //
        // 브루트포스, 이분탐색 문제
        // n이 최대 5000개로 주어지므로 모든 물건에 대한 조합을 고려해서는 5000^3의 시간이 걸린다.
        // 따라서 이분탐색을 통해 채워야하는 무게보다 작지만 가장 큰 물건부터 내림차순으로 고려해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n개의 물건
        int n = Integer.parseInt(st.nextToken());
        // 채워야하는 무게 c
        int c = Integer.parseInt(st.nextToken());
        
        // 물건들
        stuffs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 정렬
        Arrays.sort(stuffs);

        // 조합이 존재한다면 1, 그렇지 않다면 0을 출력한다.
        System.out.println(findWeights(0, c, n - 1) ? 1 : 0);

    }

    // 최대 3개의 물건의 무게 합이 weight가 되는 조합이 존재하는지 찾는다.
    static boolean findWeights(int selected, int weight, int limitIdx) {
        // 남은 무게가 0이라면 조합을 찾은 경우.
        // true 반환
        if (weight == 0)
            return true;
        // 무게가 0이 아닌데, 3개를 모두 골랐거나,
        // 이미 가장 가벼운 물건까지 고려했거나
        // 현재 고를 수 있는 가장 무거운 물건 * 잔여 선택 횟수로도 남은 무게를 채울 수 없다면
        // false 반환
        else if (selected == 3 || limitIdx < 0 || stuffs[limitIdx] * (3 - selected) < weight)
            return false;

        // 이분 탐색
        // 잔여 무게보다 가볍지만 그 중 가장 무거운 물건을 찾는다.
        int start = 0;
        int end = limitIdx;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (stuffs[mid] > weight)
                end = mid - 1;
            else
                start = mid + 1;
        }
        // 찾은 물건의 idx = end

        // end부터 차근차근 내려가며
        // 남은 선택 횟수 동안 잔여 무게를 채울 수 있는지 확인한다.
        while (end >= 0) {
            // 그러한 경우를 찾으면 true 반환
            if (findWeights(selected + 1, weight - stuffs[end], end - 1))
                return true;
            end--;
        }

        // 그러한 조합을 찾지 못했다면 false 반환.
        return false;
    }
}