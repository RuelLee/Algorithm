/*
 Author : Ruel
 Problem : Baekjoon 2818번 숙제하기 싫을 때
 Problem address : https://www.acmicpc.net/problem/2818
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2818_숙제하기싫을때;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Dice {
    int up = 1;
    int down = 6;
    int front = 2;
    int back = 5;
    int left = 4;
    int right = 3;

    void rollLeft() {
        int temp = left;
        left = up;
        up = right;
        right = down;
        down = temp;
    }

    void rollRight() {
        int temp = right;
        right = up;
        up = left;
        left = down;
        down = temp;
    }

    void rollDown() {
        int temp = front;
        front = up;
        up = back;
        back = down;
        down = temp;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 왼면 4, 오른면 3, 윗면 1, 아랫면 6, 앞면 2, 뒷면 4인 주사위가 주어진다.
        // 해당 주사위를 r * c 격자 위에서 굴린다.
        // 주사위를 (0, 0) 위치에 두고선 마지막 열에 도착할 때까지 오른쪽으로 굴린다.
        // 마지막 열에 도착할 경우, 아래로 한 칸 굴린다.
        // 첫번째 열에 도착할 때까지 왼쪽으로 굴린다.
        // 첫번째 열에 도착할 경우, 아래로 한  칸 굴린다.
        // 각 칸에서는 주사위의 윗면에 해당하는 수를 적는다.
        // 모든 칸의 수를 더하면?
        //
        // 구현, 시뮬레이션 문제
        // 먼저 주사위를 객체로 만들어, 각 면에 적힌 수와, 오른쪽 왼쪽, 아래로 굴렸을 때 바뀌는 수 정보를 메소드로 구현한다.
        // 먼저 첫번째 열에서 마지막 열로 가거나, 마지막 열에서 첫번째 열로 오는 경우
        // 일일이 계산할 필요없이, 주사위가 한 바퀴 구르는 4번 구름을 기준으로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c 크기의 격자
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 주사위
        Dice dice = new Dice();
        long answer = 0;
        for (int i = 0; i < r; i++) {
            // 옆으로 굴릴 경우, 한 바퀴마다 적히는 수는 윗면, 오른쪽 면, 아랫면, 왼쪽 면이다.
            answer += (dice.up + dice.right + dice.down + dice.left) * (c / 4);

            // 짝수번째 행일 경우는 오른쪽으로 
            // 홀수번째 행일 경우는 왼쪽으로 굴리기 때문에
            // 4의 배수로 끝난 경우, 마지막 면에 대해
            // 짝수 행의 경우에는 오른쪽으로 3번 구른 상태, 홀수행의 경우에는 왼쪽으로 3번 구른 상태이므로
            // 해당 상태를 맞춰준다.
            if (i % 2 == 0)
                dice.rollLeft();
            else
                dice.rollRight();

            // 4의 배수로 굴리고 남은 나머지 만큼을 마저 굴려준다.
            for (int j = 0; j < c % 4; j++) {
                if (i % 2 == 0)
                    dice.rollRight();
                else
                    dice.rollLeft();
                answer += dice.up;
            }
            // 아래로 굴린다.
            dice.rollDown();
        }
        // 답 출력
        System.out.println(answer);
    }
}