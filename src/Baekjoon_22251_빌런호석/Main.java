/*
 Author : Ruel
 Problem : Baekjoon 22251번 빌런 호석
 Problem address : https://www.acmicpc.net/problem/22251
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22251_빌런호석;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] matrix;
    static int n, k, p, x;

    public static void main(String[] args) throws IOException {
        // 1 ~ n층까지 운행하는 엘리베이터가 주어지고,
        // 층수를 보여주는 디스플레이는 각 자릿수마다 7개의 표시등으로 이루어져있다.
        // 흔히 아는 디지털 숫자를 생각하면 된다.
        // 디스플레이는 k 자리의 수로 이루어져있다.
        // 이 중 1 ~ p개의 표시등을 바꿔 실제로 존재하는 층이되, 1이상 n이하가 되도록 바꾸고자 한다.
        // 바꿀 수 있는 경우의 수는?
        //
        // 브루트 포스 문제
        // 먼저 7개의 표시등에 따라 각 수를 비트마스크로 나타내고
        // 이를 통해 서로 다른 수로 바꿀 경우, 몇 개의 표시등을 반전시켜야하는지 계산한다.
        // 그 후, 주어진 수를 p번 제한 이내에 모든 경우의 수에 대해 수를 바꾸고
        // 모든 작업이 끝났을 때, 해당 수가 n이하이며, 바꾼 표시등의 개수가 1이상이고, 층이 1층 이상인지만 체크해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 각 수에 켜져있는 표시등
        int[][] nums = new int[][]{{0, 1, 2, 4, 5, 6}, {2, 5}, {0, 2, 3, 4, 6}, {0, 2, 3, 5, 6}, {1, 2, 3, 5}, {0, 1, 3, 5, 6}, {0, 1, 3, 4, 5, 6}, {0, 2, 5}, {0, 1, 2, 3, 4, 5, 6}, {0, 1, 2, 3, 5, 6}};
        // 각 수의 표시등을 비트마스크
        int[] bitmasks = new int[10];
        for (int i = 0; i < bitmasks.length; i++) {
            for (int j = 0; j < nums[i].length; j++)
                bitmasks[i] |= (1 << nums[i][j]);
        }
        
        // 해당 수가 다른 수로 바뀌려면 반전해야하는 표시등의 개수 계산
        matrix = new int[10][10];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix[i].length; j++)
                matrix[i][j] = matrix[j][i] = countBits((bitmasks[i] ^ bitmasks[j]));
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 1 ~ n 층까지 운행하는 엘리베이터이며
        // k개의 자릿수를 갖고 있고, 최대 p개의 표시등을 반전시켜 n이하의 층을 만든다.
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        
        // 경우의 수 출력
        System.out.println(bruteForce(k - 1, 0, 0));
    }
    
    // 브루트 포스
    // 10의 idx 자리 수를 바꿀 차례이며, 현재 수는 current, 바꾼 표시등의 개수는 changed
    static int bruteForce(int idx, int current, int changed) {
        // 모든 자릿수를 살펴봤다면
        // 현재 수가 n보다는 같거나 작고, changed가 0보다 크며, current가 0보다 큰지 체크
        // 그렇다면 가능한 경우의 수 1개이며, 그렇지 않다면 0개
        if (idx == -1)
            return current <= n && changed > 0 && current > 0 ? 1 : 0;
        
        // 현재 바꾸려는 수
        int targetNum = (x / (int) Math.pow(10, idx)) % 10;
        // 가능한 경우의 수
        int sum = 0;
        // targetNum을 i로 바꿀 때
        for (int i = 0; i < matrix[targetNum].length; i++) {
            // 아직 반전시킬 수 있는 표시등의 개수가 남았는지 확인 후,
            // 해당 상태를 매개 변수로 싣어 재귀.
            // 그 때의 가능한 경우의 수를 sum에 누적
            if (matrix[targetNum][i] + changed <= p)
                sum += bruteForce(idx - 1, current * 10 + i, changed + matrix[targetNum][i]);
        }
        // 현재 가능한 경우의 수를 반환.
        return sum;
    }

    // bitmask의 bit를 센다.
    static int countBits(int bitmask) {
        int count = 0;
        while (bitmask > 0) {
            count += bitmask % 2;
            bitmask /= 2;
        }
        return count;
    }
}