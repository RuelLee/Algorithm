/*
 Author : Ruel
 Problem : Baekjoon 20208번 진우의 민트초코우유
 Problem address : https://www.acmicpc.net/problem/20208
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20208_진우의민트초코우유;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

class Point {
    int r;
    int c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static Point home;
    static int h;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 맵이 주어지며, 집 주변에 우유가 떨어져있다.
        // 집 주변의 우유를 최대한 많이 마시고 돌아오고자 한다.
        // 좌우상하로 한 칸씩 움직일 수 있으며, 그 때 체력이 하나씩 감소한다.
        // 처음 체력은 m이며, 우유를 마실 때마다 h만큼씩 증가한다. 이 때 m보다 더 많은 양이 될 수 있다.
        //
        // 백트래킹 문제
        // 현재 도달할 수 있는 우유의 위치에 간 후,
        // 다음 우유의 위치 혹은 집으로 돌아가는 경우를 계산하며
        // 마실 수 있는 최대 우유의 양을 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 맵의 크기
        int n = Integer.parseInt(st.nextToken());
        // 초기 체력
        int m = Integer.parseInt(st.nextToken());
        // 우유를 마시면 증가하는 체력의 양
        h = Integer.parseInt(st.nextToken());
        
        // 우유의 위치
        List<Point> milks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[] row = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < n; j++) {
                // 집
                if (row[j] == 1)
                    home = new Point(i, j);
                // 우유
                else if (row[j] == 2)
                    milks.add(new Point(i, j));
            }
        }

        // 집에서 초기 체력으로 시작하여 최대 마실 수 있는 우유의 양을 출력한다.
        System.out.println(drinkMaxMilk(home.r, home.c, m, 0, 0, milks));
    }
    
    // 백트래킹을 활용한 메소드
    static int drinkMaxMilk(int row, int col, int health, int drank, int bitmask, List<Point> milks) {
        int max = 0;
        // 우유들을 순서대로 살펴보며
        for (int i = 0; i < milks.size(); i++) {
            // 현재 위치에서 i번 우유까지의 거리
            int distance = Math.abs(milks.get(i).r - row) + Math.abs(milks.get(i).c - col);
            // 아직 i번 우유를 마시지 않았으며, 도달하는 거리가 체력보다 적거나 같다면
            // 위치, 체력, 마신 우유의 개수, 비트마스크 등을 고려해여 바뀐 변수로
            // 메소드를 재호출한다.
            // 이 때 반환되는 값은 해당 경로로 진행했을 때 마실 수 있는 최대 우유의 개수이므로
            // max값이 갱신되는지 확인한다.
            if ((bitmask & (1 << i)) == 0 && distance <= health)
                max = Math.max(max, drinkMaxMilk(milks.get(i).r, milks.get(i).c, health - distance + h, drank + 1, bitmask | (1 << i), milks));
        }

        // 다른 우유들을 마시러 간 경우와
        // 현재 위치에서 체력이 가능하다면 집으로 돌아간 경우
        // 를 비교하여 더 많은 우유를 마신 경우의 개수를 반환한다.
        return Math.max(max, Math.abs(row - home.r) + Math.abs(col - home.c) <= health ? drank : 0);
    }
}