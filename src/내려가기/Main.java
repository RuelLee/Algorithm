/*
 Author : Ruel
 Problem : Baekjoon 2096번 내려가기
 Problem address : https://www.acmicpc.net/problem/2096
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 내려가기;

import java.util.Scanner;

public class Main {
    static int[] dc = {-1, 0, 1};

    public static void main(String[] args) {
        // DP를 활용하여 푸는 무제
        // answer 라는 int[2][3] 공간 중 answer[0] 배열은 최소값을, answer[1] 배열은 최대값을 저장해둘 것이다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][] answer = new int[2][3];
        for (int i = 0; i < 3; i++)     // 처음 answer 공간은 가장 윗줄 입력값으로 초기화해준다.
            answer[0][i] = answer[1][i] = sc.nextInt();

        int[][] temp = new int[2][3];
        for (int i = 0; i < n - 1; i++) {       // 2번째 줄부터, n번째 줄까지
            for (int j = 0; j < 3; j++) {       // 각 칸에 도달하는 최소/최대 값은
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                int input = sc.nextInt();
                // i-1줄까지의 누적된 결과가 저장되어있는 answer에서 찾는다.
                // 자신보다 바로 윗칸과, 그 양옆 칸 중에 최소/최대 값을 찾아 자신과 더한다.
                for (int d = 0; d < 3; d++) {
                    if (checkArea(j + dc[d])) {
                        min = Math.min(min, input + answer[0][j + dc[d]]);
                        max = Math.max(max, input + answer[1][j + dc[d]]);
                    }
                }
                temp[0][j] = min;       // answer[0][j]값을 지금 바꿔버리면 answer[0][j+1]의 값이 영향을 받는다. temp에 임시로 저장해뒀다가 마지막에 answer로 다시 불러들이자.
                temp[1][j] = max;
            }
            copy(answer, temp);
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {   // 최종적으로 남은 answer[0]에서 최소값을, answer[1]에서 최대값을 가져와 출력.
            min = Math.min(min, answer[0][i]);
            max = Math.max(max, answer[1][i]);
        }
        System.out.println(max + " " + min);
    }

    static void copy(int[][] answer, int[][] temp) {
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[i].length; j++)
                answer[i][j] = temp[i][j];
        }
    }

    static boolean checkArea(int c) {
        return c >= 0 && c < 3;
    }
}