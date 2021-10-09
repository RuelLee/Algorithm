/*
 Author : Ruel
 Problem : Baekjoon 2631번 줄세우기
 Problem address : https://www.acmicpc.net/problem/2631
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 줄세우기;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 흐트러진 순서를 오름차순 정렬할 때 최소한의 삽입 수.
        // 3 7 5 2 6 1 4 를 1 2 3 4 5 6 7로 정렬하고자할 때
        // 가능한 최소한의 이동 수를 구하는 문제.
        // 이미 정렬이 되어있는 수는 건들지 않고서, 나머지 숫자들을 움직이면 된다
        // -> 최장 증가 부분 수열로 풀 수 있다
        // 위 조건에서 현재 순서가 맞는 가장 길이가 긴 수열은
        // 3 5 6 이다. 이를 제외한 4 숫자를 자신의 자리에 맞는 위치에 넣어주면 답이 나온다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] child = new int[n];
        for (int i = 0; i < n; i++)
            child[i] = sc.nextInt();

        int[] orderMinValue = new int[n];
        Arrays.fill(orderMinValue, 300);
        orderMinValue[0] = child[0];
        int end = 0;
        int maxOrder = 1;

        for (int i = 1; i < child.length; i++) {
            if (child[i] > orderMinValue[end]) {        // 이번의 숫자가 나왔던 숫자들보다 크다면
                orderMinValue[++end] = child[i];        // 가장 마지막 순서 자리에 현재 숫자를 넣어준다.
                maxOrder = Math.max(maxOrder, end + 1);     // 그리고 최대 크기를 반영해준다.
            } else {        // 아니라면
                int order = findOrder(end, child[i], orderMinValue);        // 이 숫자에 맞는 최대 순서 위치를 찾아준다.
                orderMinValue[order] = child[i];        // 그 자리에 값을 넣어준다!
            }
        }
        // 마지막까지 돌았을 때 maxOrder에는 최장 증가 부분 수열의 크기가 담겨있다.
        // 이를 제외한 인원이 위치를 옮겨야하는 최소 인원.
        System.out.println(n - maxOrder);
    }

    static int findOrder(int end, int value, int[] orderMinValue) {
        // 이분탐색으로 빠르게 위치를 찾아주자.
        // 사실 N의 크기가 작기 때문에 완전탐색하더라도 큰 차이는 없다.
        int start = 0;
        while (start < end) {
            int middle = (start + end) / 2;

            if (orderMinValue[middle] < value)
                start = middle + 1;
            else if (value <= orderMinValue[middle])
                end = middle;
        }
        return end;
    }
}