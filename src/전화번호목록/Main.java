/*
 Author : Ruel
 Problem : Baekjoon 5052번 전화번호 목록
 Problem address : https://www.acmicpc.net/problem/5052
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 전화번호목록;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 여러 전화번호가 주어질 때, 주어지는 전화번호의 접두사가 다른 전화번호에
        // 해당하는지 여부를 판별하는 문제이다
        // 전화번호가 만개까지 주어지므로, 당연히 일일이 계산해서는 안된다.
        // 전화번호가 주어지면, 이를 String 타입으로 받아 정렬을 하자
        // A라는 전화번호가 B의 전화번호의 앞부분에 해당한다면, A는 B의 직전에 올 수밖에 없다.
        // 따라서 정렬 후 -> 이웃한 두 번호의 관계를 살펴보면 된다.
        Scanner sc = new Scanner(System.in);

        int testCase = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = sc.nextInt();
            sc.nextLine();

            String[] strings = new String[n];
            for (int i = 0; i < n; i++)
                strings[i] = sc.nextLine();

            Arrays.sort(strings);
            boolean check = true;
            for (int i = 0; i < strings.length - 1; i++) {
                if (strings[i].length() < strings[i + 1].length() && strings[i + 1].startsWith(strings[i])) {
                    check = false;
                    break;
                }
            }
            sb.append(check ? "YES" : "NO").append("\n");
        }
        System.out.println(sb);
    }
}