/*
 Author : Ruel
 Problem : Baekjoon 1019 책 페이지
 Problem address : https://www.acmicpc.net/problem/1019
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 책페이지;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 숫자가 주어질 때, 1부터 해당 숫자까지 0부터 9까지의 숫자가 몇번이나 등장하는지 묻는 문제이다
        // 예를 들어 11이 주어진다면, 1 ~ 9까지 한번씩 등장하고, 10에서 1과 0, 11에서 1이 2번 등장하여
        // 1 4 1 1 1 1 1 1 1 1
        // 가 답으로 나와야한다.
        // 1321 이라는 숫자가 주어졌을 때 십의 자리를 살펴보자 (계산의 편의성을 위해 맨 앞자리가 0인 숫자가 존재한다고 치자. ex) 0021, 0055, 0103)
        // 여기서 십의 자리를 보자
        // 십의 자리에서 2미만의 숫자가 나오는 경우는
        // (13 - 0 + 1) * 10 이 된다. (ex 십의 자리가 1일 때, 01* 10개, 11* 10개,,,, 131* 10개)
        // 십의 자리에서 2 이상의 숫자가 나오는 경우는
        // (13 - 0 ) * 10 이 된다.  (ex 십의 자리가 3일 때, 030... ~ 1239)
        // 여기서 정확히 십의 자리가 2일 때는, 1320일 때와, 1321인 경우, 즉 1321 % 10 + 1인 경우가 더 존재한다
        // 위와 같은 연산을 각 자릿수마다 모두 반복해준다
        // 그 후 우리가 있다고 0이 붙어있다고 가정했던 가상의 숫자에서 각 자리에 붙어있는 0들을 0의 개수에서 빼준다.
        // 0000 ~ 0999 까지 천개 -> 천의 자리에 붙은 0만 빼준다. 백이하의 자리들은 아래의 연산에서 제해진다.
        // 000부터 099까지 100개 -> 백의 자리의 0만..
        // 00부터 09 까지 10개 -> 십의 자리의 0만..
        // 0부터 0까지 1개 -> 0페이지는 포함하지 않으므로 같이 빼준다.
        Scanner sc = new Scanner(System.in);
        String stringNumber = sc.nextLine();
        int number = Integer.parseInt(stringNumber);

        int[] nums = new int[10];

        for (int i = 0; i < stringNumber.length(); i++) {
            int num = stringNumber.charAt(i) - '0';
            int multi = (int) Math.pow(10, stringNumber.length() - 1 - i);

            for (int j = 0; j < num; j++)
                nums[j] += (number / (multi * 10) + 1) * multi;
            for (int j = num; j < 10; j++)
                nums[j] += (number / (multi * 10)) * multi;
            nums[num] += number % multi + 1;
        }
        for (int i = 0; i < stringNumber.length(); i++)
            nums[0] -= (int) Math.pow(10, i);
        StringBuilder sb = new StringBuilder();
        for (int num : nums)
            sb.append(num).append(" ");
        System.out.println(sb);
    }
}