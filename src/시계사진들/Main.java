/*
 Author : Ruel
 Problem : Baekjoon 10266번 시계 사진들
 Problem address : https://www.acmicpc.net/problem/10266
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 시계사진들;

import java.util.Scanner;

public class Main {
    static final int angleLength = 360000;

    public static void main(String[] args) {
        // KMP를 사용활용하여 일치여부를 검사하는 문제!
        // 각도를 동일하게 +- 를 주더라도 시게 바늘 간의 간격은 변하지 않기 때문에
        // 360도만 검사하는 것이 아니라, 720도 중에서, 360도가 일치하는지 확인하면 된다!

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        boolean[][] input = new boolean[2][angleLength];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++)
                input[i][sc.nextInt()] = true;      // 바늘에 해당 하는 곳만 true
        }

        int[] pi = new int[angleLength];
        int j = 0;
        for (int i = 1; i < angleLength; i++) {     // input1을 기준으로 pi 배열 생성
            while (j > 0 && input[0][i] != input[0][j])
                j = pi[j - 1];
            if (input[0][i] == input[0][j])
                pi[i] = ++j;
        }
        j = 0;
        boolean possible = false;
        for (int i = 0; i < angleLength * 2; i++) {     // 두바퀴를 돌리는데!
            while (j > 0 && input[1][i % angleLength] != input[0][j])       // 360도가 넘어가면, % 360도 연산으로 0부터 다시 시작되게끔 해준다
                j = pi[j - 1];
            if (input[1][i % angleLength] == input[0][j])       // 일치한다면
                j++;        // j값 전진
            if (j == angleLength) {     // j값이 360도에 도달했다면, 일치!
                possible = true;
                break;
            }
        }
        System.out.println(possible ? "possible" : "impossible");
    }
}