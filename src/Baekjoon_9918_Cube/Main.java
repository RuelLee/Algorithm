/*
 Author : Ruel
 Problem : Baekjoon 9918번 Cube
 Problem address : https://www.acmicpc.net/problem/9918
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9918_Cube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Dice {
    int top, bottom, left, right, front, back;

    public Dice() {
        top = bottom = left = right = front = back = 0;
    }

    void rollDown() {
        int temp = top;
        top = front;
        front = bottom;
        bottom = back;
        back = temp;
    }

    void rollUp() {
        int temp = top;
        top = back;
        back = bottom;
        bottom = front;
        front = temp;
    }

    void rollLeft() {
        int temp = top;
        top = right;
        right = bottom;
        bottom = left;
        left = temp;
    }

    void rollRight() {
        int temp = top;
        top = left;
        left = bottom;
        bottom = right;
        right = temp;
    }
}

public class Main {
    // 6 * 6 크기의 격자 안에 주사위의 전개도가 주어진다.
    // 해당 전개도로 주사위를 만들 수 있다면 1의 맞은 편 번호를 출력하고
    // 만들 수 없다면 0을 출력한다
    //
    // 구현 문제
    // 주사위를 객체로 만들고, 하나의 지점을 찾아 해당 지점으로부터 주사위를 굴려가며
    // 만나는 위치에 있는 수들을 주사위 아랫면에 붙이는 작업을 계속했다.
    // 전개도가 맞는 거라면 바닥면이 항상 0인 상태로 새로운 칸을 만나야한다.
    // 바닥면이 0이 아닌 상태로 새로운 칸을 만난다면 주사위로 만드는 것이 불가능한 경우.
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;
        // 6 * 6의 칸
        int[][] map = new int[6][6];
        for (int i = 0; i < map.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 주사위
        Dice dice = new Dice();
        // 방문 여부
        boolean[][] visited = new boolean[6][6];
        // 0이 아닌 숫자를 찾았는가.
        boolean found = false;
        // 주사위를 만들었는가.
        boolean foundDice = true;
        // 0이 아닌 숫자를 하나 찾으면 된다.
        for (int i = 0; i < map.length && !found; i++) {
            for (int j = 0; j < map[i].length && !found; j++) {
                // 0이 아닌 칸을 찾았을 경우
                if (map[i][j] != 0) {
                    found = true;
                    // 해당 지점부터 주사위를 굴리기 시작한다.
                    foundDice = findDice(i, j, map, dice, visited);
                }
            }
        }
        
        // 주사위를 만들었을 경우
        // 1의 맞은 편 수를 출력
        if (foundDice && dice.bottom + dice.top + dice.left + dice.right + dice.back + dice.front == 21) {
            int answer = 0;
            if (dice.top == 1)
                answer = dice.bottom;
            else if (dice.left == 1)
                answer = dice.right;
            else if (dice.front == 1)
                answer = dice.back;
            else if (dice.bottom == 1)
                answer = dice.top;
            else if (dice.right == 1)
                answer = dice.left;
            else
                answer = dice.front;
            System.out.println(answer);
        } else      // 그 외의 경우 0 출력
            System.out.println(0);
    }
    
    // 주사위 찾기
    static boolean findDice(int r, int c, int[][] map, Dice dice, boolean[][] visited) {
        // 방문 체크
        visited[r][c] = true;
        // 밑면이 0이 아니라면 불가능한 경우.
        if (dice.bottom != 0)
            return false;
        
        // 밑면에 해당 값을 복사
        dice.bottom = map[r][c];
        // 오른쪽에 0이 아닌 수가 있는 경우
        if (checkArea(r, c + 1) && !visited[r][c + 1] && map[r][c + 1] != 0) {
            // 오른쪽으로 굴렸다가
            dice.rollRight();
            // 불가능한 경우라면 false 반환
            if (!findDice(r, c + 1, map, dice, visited))
                return false;
            // 그렇지 않다면 다시 왼쪽으로 굴려 원상복귀
            dice.rollLeft();
        }
        
        // 왼쪽에 0이 아닌 수가 있는 경우
        if (checkArea(r, c - 1) && !visited[r][c - 1] && map[r][c - 1] != 0) {
            dice.rollLeft();
            if (!findDice(r, c - 1, map, dice, visited))
                return false;
            dice.rollRight();
        }
        
        // 아래쪽에 0이 아닌 수가 있는 경우
        if (checkArea(r + 1, c) && !visited[r + 1][c] && map[r + 1][c] != 0) {
            dice.rollDown();
            if (!findDice(r + 1, c, map, dice, visited))
                return false;
            dice.rollUp();
        }
        
        // 여기까지 false로 끝나지 않았다면 현재까지 모순되는 경우는 없다.
        // true 반환
        return true;
    }
    
    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < 6 && c >= 0 && c < 6;
    }
}