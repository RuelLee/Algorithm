/*
 Author : Ruel
 Problem : Baekjoon 27740번 시프트 연산
 Problem address : https://www.acmicpc.net/problem/27740
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27740_시프트연산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0과 1로 이루어진 수열이 주어지고, 두 가지의 명령어를 할 수 있다.
        // 1. L 시프트 : 수들을 왼쪽으로 한 칸씩 옮기고, 첫번째 수는 사라지고, 마지막 수는 0이 된다.
        // 2. R 시프트 : 수들을 오른쪽으로 한  칸씩 옮기고, 첫번째 수는 0, 마지막 수는 사라진다.
        // 최소 연산으로 모든 수를 0으로 만들고자할 때
        // 명령의 횟수와 그 방법을 구하라
        //
        // 브루트 포스 문제
        // 먼저, 한쪽 방향으로만 시프트하여 모든 수를 0으로 만드는 경우를 생각해볼 수 있다.
        // 그 후, 한쪽으로 약간 시프트한 뒤, 반대 방향으로 시프트 하는 경우를 생각해볼 수 있다.
        // 두 경우 모두 따져보면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 수열의 크기
        int n = Integer.parseInt(br.readLine());

        // 수열
        int[] array = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < array.length; i++)
            array[i] = Integer.parseInt(st.nextToken());

        // shift[0] = L 시프트만 할 경우, 모든 수를 0으로 만드는 횟수
        // shift[1] = R 시프트만 할 경우, 모든 수를 0으로 만드는 횟수
        // shift[2], shift[3] = 두 시프트를 섞어서할 경우, 각각의 횟수
        int[] shifts = new int[4];

        // L 시프트만 할 경우, 가장 마지막에 오는 1까지 L 시프트를 해야한다.
        // 그 횟수 계산
        for (int i = array.length - 1; i < array.length; i--) {
            if (array[i] == 1) {
                shifts[0] = shifts[2] = i + 1;
                break;
            }
        }
        // R 시프트만 하는 경우
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                shifts[1] = shifts[3] = n - i;
                break;
            }
        }

        // 자신보다 오른쪽에 있는 가장 빠른 1의 idx를 계산해둔다.
        int[] toRight = new int[n];
        for (int i = shifts[0] - 2; i >= 0; i--) {
            if (array[i + 1] == 1)
                toRight[i] = i + 1;
            else
                toRight[i] = toRight[i + 1];
        }

        // 두 시프트를 섞어서 하는 경우.
        for (int i = 0; i < array.length; i++) {
            // i번째 수가 1인 경우
            if (array[i] == 1) {
                // 현재 i번까지 0으로 만드는데 해야하는 L 시프트 횟수
                int left = i + 1;
                // 원래 상태에서 i번보다 가장 빠르게 등장하는 1을 0으로 만드는데 드는 R 시프트 횟수
                int right = (toRight[i] == 0 ? 0 : n - toRight[i]);
                // shift[2]과 shift[3] 중 더 적은 횟수만큼 시프트를 했다가, 반대방향으로 두 시트트 횟수를 더한 만큼 하는 경우.
                // 최솟값을 갱신하는지 확인.
                if (Math.min(shifts[2], shifts[3]) + shifts[2] + shifts[3] >
                        Math.min(left, right) + left + right) {
                    shifts[2] = left;
                    shifts[3] = right;
                }
            }
        }
        
        // 답안 작성
        // 모든 수를 0으로 만드는데 필요한 최소 명령 횟수
        int min = Math.min(Math.min(shifts[0], shifts[1]), Math.min(shifts[2], shifts[3]) + shifts[2] + shifts[3]);
        StringBuilder sb = new StringBuilder();
        sb.append(min).append("\n");
        // L 시프트만 하는 경우가 최소인 경우
        if (min == shifts[0]) {
            for (int i = 0; i < shifts[0]; i++)
                sb.append('L');
        } else if (min == shifts[1]) {          // R 시프트만 하는 경우가 최소인 경우
            for (int i = 0; i < shifts[1]; i++)
                sb.append('R');
        } else {        // 두 시프트를 섞어서 쓰는 것이 최소인 경우
            // L 시프트를 먼저한 후, R 시프트를 하는 경우
            if (shifts[2] <= shifts[3]) {
                for (int i = 0; i < shifts[2]; i++)
                    sb.append('L');
                for (int i = 0; i < shifts[2] + shifts[3]; i++)
                    sb.append('R');
            } else {        // R 시프트를 먼저 하고, L 시프트를 하는 경우
                for (int i = 0; i < shifts[3]; i++)
                    sb.append('R');
                for (int i = 0; i < shifts[3] + shifts[2]; i++)
                    sb.append('L');
            }
        }
        // 답 출력
        System.out.println(sb);
    }
}