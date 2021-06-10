package N과M3;

import java.util.Scanner;

public class Main {
    static StringBuilder sb;

    public static void main(String[] args) {
        // 중복 조합
        // 한번 뽑았던 숫자도 다시 뽑을 수 있다.
        // 매번 println()으로 출력 시 시간이 오래 걸리므로,
        // StringBuilder를 사용해서 한번에 출력하자.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        sb = new StringBuilder();
        combination(0, n, m, "");
        System.out.println(sb.toString());
    }

    static void combination(int used, int n, int m, String answer) {
        if (used == m) {
            sb.append(answer).append("\n");
            return;
        }

        for (int i = 0; i < n; i++)
            combination(used + 1, n, m, answer + (i + 1) + " ");
    }
}