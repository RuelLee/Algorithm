/*
 Author : Ruel
 Problem : Baekjoon 19950번 3차원 막대기 연결하기
 Problem address : https://www.acmicpc.net/problem/19950
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19950_3차원막대기연결하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 시작점 (x1, y1, z1), 끝점(x2, y2, z2)가 주어진다.
        // 두 점을 n개의 막대들을 이용하여 이으려 한다.
        // 막대들은 서로 겹칠 수 있다.
        // 모든 막대를 사용하여, 두 점을 연결할 수 있는지 출력하라
        //
        // 기하 문제
        // 기하학적 문제를 해결할 수 있는지에 대한 문제
        // 먼저 첫번째 조건.
        // 모든 막대를 이었을 때, 두 점 사이의 거리보다는 같거나 길어야한다.
        // 당연히 가장 길게 이었을 때, 두 점 사이도 잇지 못해서는 안된다.
        // 두번째 조건
        // 가장 긴 막대기의 길이와 나머지 막대기들의 길이 합과의 차가 두 점 사이의 길이보다는 작아야한다.
        // 3차원 공간으로 주어지므로, 각도를 조절하여, 원하는 축의 거리를 조절할 수 있다.
        // 이 때, 최악의 경우, 가장 긴 막대로 두 점 사이를 지나쳤을 때
        // 다시 돌아오는데 나머지 막대를 사용하는 경우를 생각해보면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 주어지는 두 점
        int[][] points = new int[2][3];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++)
                points[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 막대들
        int n = Integer.parseInt(br.readLine());
        int[] sticks = new int[n];
        // 가장 긴 막대
        int max = 0;
        // 막대 길이의 합
        int sum = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < sticks.length; i++) {
            sum += sticks[i] = Integer.parseInt(st.nextToken());
            max = Math.max(max, sticks[i]);
        }
        
        // 두 점 사이의 거리
        double distance = 0;
        for (int i = 0; i < points[0].length; i++)
            distance += Math.pow(points[1][i] - points[0][i], 2);
        distance = Math.sqrt(distance);

        // 막대 길이의 합이 distance보단 같거나 커야하고
        // 가장 긴 막대와 (나머지 막대 길이 합)의 차가 distance보단 같거나 작아야한다
        // 두 조건을 만족한다면 YES
        // 그렇지 않다면 NO를 출력한다.
        System.out.println(sum >= distance && max * 2 - sum <= distance ? "YES" : "NO");
    }
}