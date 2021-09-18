/*
 Author : Ruel
 Problem : Baekjoon 9576번 책 나눠주기
 Problem address : https://www.acmicpc.net/problem/9576
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 책나눠주기;

import java.util.Scanner;

public class Main {
    static int[] books;
    static int[][] application;

    public static void main(String[] args) {
        // 간단한 이분 매칭 문제!
        // 각 지원자가 받을 수 있는 책이 범위로 주어지므로, 전부 체크해준다.
        Scanner sc = new Scanner(System.in);

        int testCase = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = sc.nextInt();
            int m = sc.nextInt();

            books = new int[n + 1];     // 1 ~ n 까지 책에 할당된 지원자를 기록할 공간
            application = new int[m + 1][2];        // 1 ~ m 까지 지원자들의 책 범위를 나타낼 공간

            for (int i = 1; i < application.length; i++) {
                for (int j = 0; j < 2; j++)
                    application[i][j] = sc.nextInt();
            }

            int count = 0;
            for (int i = 1; i < application.length; i++) {
                if (allocateBook(i, new boolean[n + 1]))        // 1 ~ m 까지 지원자들에게 책을 이분매칭으로 나눠준다. 할당됐다면 +1
                    count++;
            }
            sb.append(count).append("\n");
        }
        System.out.println(sb);
    }

    static boolean allocateBook(int applicant, boolean[] visited) {
        // 각 지원자의 책 범위 동안
        for (int i = application[applicant][0]; i <= application[applicant][1]; i++) {
            if (visited[i])     // 이미 방문한 적이 있는 책이라면 건너뛰기
                continue;

            visited[i] = true;      // 방문한 책으로 표시
            if (books[i] == 0) {        // 할당된 사람이 없다면
                books[i] = applicant;       // applicant로 할당하고 true 리턴
                return true;
            } else {
                if (allocateBook(books[i], visited)) {      // 아니라면 해당 책에 할당된 사람이 다른 책으로 할당할 수 있는지 체크
                    books[i] = applicant;       // 다른 책으로 할당된다면, 이번 책은 applicant 로 할당
                    return true;
                }
            }
        }
        return false;       // 할당할 수 있는 책이 없다면 false 리턴.
    }
}