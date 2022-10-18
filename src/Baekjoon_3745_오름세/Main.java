/*
 Author : Ruel
 Problem : Baekjoon 3745번 오름세
 Problem address : https://www.acmicpc.net/problem/3745
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3745_오름세;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n일 동안 주가를 p1, p2, ... , pn이라 했을 때
        // 오름세란 부분수열 pi1 < pi2 < ... < pik (i1 < ... < ik)를 말한다.
        // n일의 주가가 주어졌을 때 가장 긴 오름세를 찾는 프로그램을 작성하시오.
        //
        // 최장 증가 수열 문제
        // 다만 입력이 '하나 이상의 공백'으로 구분되어있다는 점 때문에 맞는데 왜 틀리지? 가 나왔다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 주어지는 테스트케이스의 n
        String input;
        StringBuilder sb = new StringBuilder();
        // n이 비어있지 않다면(테스트케이스가 주어진다면) 계속해서 반복한다.
        while ((input = br.readLine()) != null) {
            // 주어지는 n일 동안의 주가.
            // 하나 이상의 공백으로 주어지기 때문에 중복된 공백들을 처리해줘야한다.
            // \\s는 공백이나 개행들을 의미하며, +는 이의 반복을 의미한다.
            // 따라서 \\s+ 는 하나 이상의 공백이나 개행이 반복되는 경우를 뜻하며, replaceAll("\\s+", " ") 은 이를 하나의 공백으로 바꿔준다.
            int[] array = Arrays.stream(br.readLine().replaceAll("\\s+", " ").split(" ")).mapToInt(Integer::parseInt).toArray();

            // 순서에 따른 최저 주가를 기록해준다.
            int[] order = new int[array.length];
            // 초기값세팅
            Arrays.fill(order, Integer.MAX_VALUE);
            // 가장 처음엔 첫날 주가.
            order[0] = array[0];

            // 오름세의 길이.
            int length = 1;
            for (int i = 1; i < array.length; i++) {
                // 이분탐색으로 i번째 날의 주가가 최대 몇번째 순서에 올 수 있는지 찾아본다.
                int start = 0;
                int end = length;
                while (start < end) {
                    int mid = (start + end) / 2;
                    if (order[mid] >= array[i])
                        end = mid;
                    else
                        start = mid + 1;
                }
                // 찾아진 순서의 값보다 i번째 주가가 더 적다면 해당 순서에 i번째 주가를 넣어준다.
                order[start] = Math.min(order[start], array[i]);
                // 순서가 최대 길이를 갱신하는지 살펴본다.
                length = Math.max(length, start + 1);
            }
            // 현재 테스트케이스에서 오름세의 최대 길이를 출력한다.
            sb.append(length).append("\n");
        }
        System.out.print(sb);
    }
}