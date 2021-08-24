/*
 Author : Ruel
 Problem : Baekjoon 14499번 주사위 굴리기
 Problem address : https://www.acmicpc.net/problem/14499
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 주사위굴리기;

import java.util.Scanner;

class DIce {
    int r;
    int c;

    int front = 0;
    int back = 0;
    int left = 0;
    int right = 0;
    int top = 0;
    int bottom = 0;

    public DIce(int r, int c) {     // 생성할 때 주사위의 위치를 입력받자.
        this.r = r;
        this.c = c;
    }

    int move(int[][] map, int i) {
        int temp;
        switch (i) {
            case 1 -> {
                if (c + 1 >= map[r].length)     // 범위를 벗어난다면
                    return -1;      // -1을 리턴
                c++;
                temp = right;
                right = top;
                top = left;
                left = bottom;
                bottom = temp;
            }
            case 2 -> {
                if (c - 1 < 0)
                    return -1;
                c--;
                temp = left;
                left = top;
                top = right;
                right = bottom;
                bottom = temp;
            }
            case 3 -> {
                if (r - 1 < 0)
                    return -1;
                r--;
                temp = front;
                front = top;
                top = back;
                back = bottom;
                bottom = temp;
            }
            case 4 -> {
                if (r + 1 >= map.length)
                    return -1;
                r++;
                temp = back;
                back = top;
                top = front;
                front = bottom;
                bottom = temp;
            }
        }
        // 값과 위치를 모두 변경해주고
        if (map[r][c] == 0)     // 지도의 값이 0인지 확인
            map[r][c] = bottom;     // 맞다면 주사위의 숫자를 밑면에 복사
        else {
            bottom = map[r][c];     // 아니라면 지도의 값을 주사위 밑면에 이동
            map[r][c] = 0;
        }
        return top;     // 그리고 현재 윗면의 숫자를 리턴해준다.
    }
}

public class Main {
    public static void main(String[] args) {
        // 구현 문제
        // 각 면이 0이 채워진 주사위고 있고, 0또는 숫자가 적힌 지도가 주어진다
        // 각 주사위를 굴려 0인 지점에 도달하면 주사위의 밑면의 숫자가 지점에 복사되고, 지점이 0이 아니라면 지점의 숫자가 주사위 밑면에 복사되고, 지점은 0이 된다.
        // 주사위를 동1 서2 북3 남4으로 굴렸을 때, 주사위 윗면의 숫자를 출력한다. 단 지도를 벗어나는 명령은 무시.
        // 주사위를 클래스 만들어주면 간단!
        // 6면이 0인 주사위를 만들고, 4방으로 굴릴 때마다 값을 변경시켜준다!
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        int x = sc.nextInt();
        int y = sc.nextInt();
        int k = sc.nextInt();

        int[][] map = new int[n][m];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = sc.nextInt();
        }

        DIce dIce = new DIce(x, y);         // x, y지점에 주사위 만들기
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            int returned = dIce.move(map, sc.nextInt());        // 범위를 벗어나면 -1값이 들어온다. 아니라면 윗면의 숫자
            if (returned == -1)     // 범위를 벗어나므로 무시
                continue;
            sb.append(returned).append("\n");       // 아니라면 윗면의 숫자이므로 StringBuilder에 모아주자.
        }
        System.out.println(sb);
    }
}