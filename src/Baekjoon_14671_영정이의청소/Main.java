/*
 Author : Ruel
 Problem : Baekjoon 14671번 영정이의 청소
 Problem address : https://www.acmicpc.net/problem/14671
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14671_영정이의청소;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 자취방에 곰팡이들이 증식을 한다.
        // □□□      ■□■
        // □■□ ->   □□□
        // □□□      ■□■
        //
        // ■□□      ■□■
        // □■□ ->   □■□
        // □□□      ■□■
        // 위와 같이 원래 곰팡이가 있던 곳에서는 사라지고, 대각선으로 증식을 한다.
        // 방 전체에 곰팡이가 생길 가능성이 존재한다면 방 청소를 하려고 한다.
        // 청소 여부를 출력하라
        //
        // 애드 혹, 홀짝성
        // 풀이 자체는 간단하다.
        // 곰팡이가 퍼져나가는 방법을 보면, 한 턴이 지날 때마다 자신의 (x, y)에서
        // (x +-1, y+-1)로 퍼져나간다. 따라서 홀짝성을 갖으며 퍼져나간다고 볼 수 있다.
        // (짝, 짝)에서 한 턴이 지나면 (홀, 홀)이 되고 다시 한 턴이 지나면 (짝, 짝)이 된다.
        // 따라서 현재 퍼져있는 곰팡이들의 (홀, 홀), (홀, 짝), (짝, 홀), (짝, 짝)에 해당하는 곰팡이가 모두 있다면
        // 시간이 경과하면 방 전체에 곰팡이가 퍼질 수 있게 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 방, k개의 곰팡이
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 홀짝성 판별
        boolean[][] check = new boolean[2][2];
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;

            check[x % 2][y % 2] = true;
        }

        // 4가지 경우들 모두 곰팡이가 존재하는지 확인
        boolean possible = true;
        for (int i = 0; i < check.length; i++) {
            for (int j = 0; j < check[i].length; j++) {
                if (!check[i][j])
                    possible = false;
            }
        }
        // 방 전체에 곰팡이가 퍼질 수 있는가에 대한 대답 출력
        System.out.println(possible ? "YES" : "NO");
    }
}