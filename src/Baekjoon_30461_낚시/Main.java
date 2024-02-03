/*
 Author : Ruel
 Problem : Baekjoon 30461번 낚시
 Problem address : https://www.acmicpc.net/problem/30461
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30461_낚시;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 호수의 공간이 n * m 으로 표현된다.
        // (a, b)에 미끼를 던지면 미끼는 1 <= i <= a의 모든 i에 대해 (i, b)에 존재하는 물고기를 사로잡는다.
        // 낚싯줄을 한번 감아올릴 때마다 (a, b)에 위치한 미끼는 (a-1, b-1)로 이동한다.
        // 호수의 공간을 벗어나면 미끼는 즉시 회수된다.
        // 각 호수 공간에 있는 물고기와
        // q번의 미끼를 던지는 장소가 주어질 때
        // 각각 사로잡는 물고기의 수를 구하라
        //
        // 누적합 문제
        // 낚싯줄을 한번 감아올릴 때마다 (a, b) -> (a-1, b-1)로 이동하므로
        // 누적합을 삼각형 형태로 구해야한다.
        // 1 2 3
        // 4 5 6
        // 7 8 9 같이 주어질 때, 9번 위치의 해당하는 누적합은 1 2 3 5 6 9의 값을 포함해야한다.
        // 따라서 계산할 때
        // (a, b)의 누적합은 (a, b)에 해당하는 물고기 + (a-1 , b-1) + ((a-1, b) - (a-2, b-1))로 나타낼 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 공간과 q번의 던짐
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 각 칸의 물고기
        int[][] fishes = new int[n][];
        for (int i = 0; i < fishes.length; i++)
            fishes[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 누적합
        int[][] psums = new int[n + 1][m + 1];
        for (int i = 1; i < psums.length; i++) {
            for (int j = 1; j < psums[i].length; j++)   // i-2가 0보다 작은 경우에는 값이 없으므로 0.
                psums[i][j] = fishes[i - 1][j - 1] + psums[i - 1][j - 1] + psums[i - 1][j] - (i - 2 >= 0 ? psums[i - 2][j - 1] : 0);
        }
        
        // q개의 던짐 처리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            sb.append(psums[w][p]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}