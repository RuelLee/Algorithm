/*
 Author : Ruel
 Problem : Baekjoon 14658번 하늘에서 별똥별이 빗발친다
 Problem address : https://www.acmicpc.net/problem/14658
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14658_하늘에서별똥별이빗발친다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 가로 n, 세로 m 길이의 공간에 k개의 별똥별이 떨어진다.
        // 여기에 가로 세로의 길이가 l인 트램벌린을 공간의 변에 평행하게 설치하여 별똥별을 튕겨내고자 한다.
        // 막지 못하고 떨어지는 별똥별의 수는?
        //
        // 브루트포스 문제
        // n과 m의 크기가 작다면, 누적합으로 푸는 방법도 생각했으나, n과 m이 최대 50만으로 주어진다.
        // 대신 k의 크기가 최대 100으로 작으므로
        // 매번 두 개의 별을 집어, 하나에선 x좌표, 다른 하나에선 y좌표를 뽑아, 해당 점을 기준으로 트램벌린을 펼쳤을 때
        // 튕겨낼 수 있는 별의 수를 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 가로 n, 세로 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 트램벌린 한 변의 길이 l
        int l = Integer.parseInt(st.nextToken());
        // 별똥별의 개수 k
        int k = Integer.parseInt(st.nextToken());
        
        // 별이 떨어지는 위치
        int[][] stars = new int[k][2];
        for (int i = 0; i < stars.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < stars[i].length; j++)
                stars[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 트램벌린으로 받을 수 있는 별의 최대 개수
        int maxCount = 0;
        // x에서 x좌표를 가져오고
        for (int[] x : stars) {
            // y에서 y좌표를 가져와
            for (int[] y : stars) {
                int count = 0;
                // 해당 트램벌린 위치에 들어오는 별의 개수를 센다.
                for (int i = 0; i < stars.length; i++) {
                    if (stars[i][0] >= x[0] && stars[i][0] <= x[0] + l &&
                            stars[i][1] >= y[1] && stars[i][1] <= y[1] + l)
                        count++;
                }
                // 별을 받아낸 개수가 최대값을 갱신하는지 확인
                maxCount = Math.max(maxCount, count);
            }
        }
        // 전체 별의 개수에서 튕겨낸 가장 많은 별의 개수를 뺀 값이
        // 막을 수 없는 별의 최소 개수
        System.out.println(k - maxCount);
    }
}