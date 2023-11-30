/*
 Author : Ruel
 Problem : Baekjoon 14718번 용감한 용사 진수
 Problem address : https://www.acmicpc.net/problem/14718
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14718_용감한용사진수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 적 병사가 있고, 힘, 민첩, 지능 3가지의 능력치를 갖는다.
        // 모든 스탯이 적 병사보다 높거나 같아야만 해당 병사를 이길 수 있다고 한다.
        // 최소 k명의 병사를 이기는 최소 스탯의 합을 구하라
        //
        // 브루트 포스 문제
        // 능력치의 범위가 100만까지 주어지므로, 3차원 배열로 표현하는 것은 불가능.
        // 하지만 병사의 수가 최대 100명이기 때문에 개수 자체는 적다.
        // 생각해보면 병사를 이기기 위한 최소 스탯은 해당 병사와 같은 수치가 되는 것이므로
        // 모든 병사들의 힘, 민첩, 지능들을 각각 값으로 보고, 해당 값을 줬을 때
        // 이길 수 있는 병사의 수를 계산하여, 최소 스탯의 합을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 병사와 최소 이겨야하는 병사의 수 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 병사들 능력치
        int[][] soldiers = new int[n][];
        for (int i = 0; i < soldiers.length; i++)
            soldiers[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 능력치 각각을 구분한다.
        HashSet<Integer> xs = new HashSet<>();
        HashSet<Integer> ys = new HashSet<>();
        HashSet<Integer> zs = new HashSet<>();
        for (int[] soldier : soldiers) {
            xs.add(soldier[0]);
            ys.add(soldier[1]);
            zs.add(soldier[2]);
        }
        
        // 최소 능력치 합
        int minSum = Integer.MAX_VALUE;
        // 모든 힘에 대해
        for (int x : xs) {
            // 모든 민첩에 대해
            for (int y : ys) {
                // 모든 지능에 대해
                for (int z : zs) {
                    // 이미 찾은 값보다 더 큰 능력치 합을 갖는다면 건너뛴다.
                    if (x + y + z >= minSum)
                        continue;

                    // x, y, z 능력치로 이길 수 있는 병사의 수를 센다.
                    int count = 0;
                    for (int[] soldier : soldiers) {
                        if (soldier[0] <= x && soldier[1] <= y && soldier[2] <= z)
                            count++;

                        if (count >= k)
                            break;
                    }

                    // 만약 k명에 도달했다면 minSum 값을 갱신한다.
                    if (count == k)
                        minSum = x + y + z;
                }
            }
        }

        // 답 출력
        System.out.println(minSum);
    }
}