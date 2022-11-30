/*
 Author : Ruel
 Problem : Baekjoon 1818번 책정리
 Problem address : https://www.acmicpc.net/problem/1818
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1818_책정리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 책이 배열된 순서가 주어진다.
        // 해당 책들을 오름차순 배열하고 싶다면 옮겨야하는 책들의 최소 수는?
        //
        // 최장 증가 수열 문제
        // 최장 증가 수열로 순서를 옮기지 않아도 되는 책의 권 수를 센다.
        // 그 후 나머지 책들을 옮겨야하므로, 해당 책들이 답.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] books = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 순서에 따른 가장 작은 책의 번호.
        int[] minValueByOrder = new int[n];
        // 초기값
        Arrays.fill(minValueByOrder, Integer.MAX_VALUE);
        int maxOrder = 0;

        // 순서대로 책을 살펴보며
        for (int i = 0; i < books.length; i++) {
            // 이전에 등장했던 증가 수열들 중에서 해당 책을 배치 가능한 가장 늦은 순서를 찾는다.
            int order = findOrder(books[i], minValueByOrder, maxOrder);
            // 해당 순서에 이번 책이 더 작은 값을 갖고 있다면 해당 값으로 갱신.
            minValueByOrder[order] = Math.min(minValueByOrder[order], books[i]);
            // 증가 수열들 중 가장 긴 길이를 갖고 있다면 maxOrder 값 갱신.
            maxOrder = Math.max(maxOrder, order + 1);
        }
        // 전체 책의 수에서 최장 증가 수열의 길이를 제한 만큼을
        // 재배치해야한다. 해당 값이 답.
        System.out.println(n - maxOrder);
    }

    // 이분 탐색을 통해 해당 책의 가장 늦은 순서를 찾는다.
    static int findOrder(int n, int[] minValueByOrder, int maxOrder) {
        int start = 0;
        int end = maxOrder + 1;
        while (start < end) {
            int mid = (start + end) / 2;
            if (minValueByOrder[mid] < n)
                start = mid + 1;
            else
                end = mid;
        }
        return start;
    }
}