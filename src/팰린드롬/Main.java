package 팰린드롬;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 팰린드롬 = 좌우가 대칭인 문자열
        // 길이가 1일 때는 자기 자신만 판단하므로 항상 팰린드롬
        // 길이가 2일 때는 자신과 옆의 수가 같은지 판단.
        // 길이가 n일 때는
        // 1. 첫 수와 마지막 수가 같은지 확인
        // 2. 두번째 숫자로부터 크기가 n-2인 숫자가 팰린드롬인지 확인
        // 1, 2 조건이 맞다면 팰린드롬
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] nums = new int[N + 1];
        for (int i = 1; i < nums.length; i++)
            nums[i] = sc.nextInt();

        boolean[][] check = new boolean[N + 1][N + 1];

        for (int i = 1; i < check.length; i++)      // 길이가 1인 숫자는 항상 팰린드롬
            check[i][i] = true;

        for (int i = 1; i < check.length - 1; i++) {    // 길이가 2인 숫자는 옆 숫자와 같은지 확인.
            if (nums[i] == nums[i + 1])
                check[i][i + 1] = true;
        }

        // 길이가 3이상 일 때부터(diff = 2일 떄는 길이가 3), 주어진 전체 숫자의 길이까지
        for (int diff = 2; diff < nums.length - 1; diff++) {
            // i + diff 값이 숫자의 범위를 넘지 않는 한
            for (int i = 1; i + diff < nums.length; i++) {
                // 첫 숫자와 마지막 숫자가 같은지 보고, i + 1부터 i + diff - 1 까지의 길이가 diff - 2인 숫자가 팰린드롬인지 확인.
                if (nums[i] == nums[i + diff] && check[i + 1][i + diff - 1])
                    check[i][i + diff] = true;
            }
        }
        int M = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            if (check[sc.nextInt()][sc.nextInt()])
                sb.append(1);
            else
                sb.append(0);
            sb.append("\n");
        }
        System.out.println(sb);
    }
}