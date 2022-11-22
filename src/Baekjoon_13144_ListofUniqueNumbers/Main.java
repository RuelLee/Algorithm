/*
 Author : Ruel
 Problem : Baekjoon 13144번 List of Unique Numbers
 Problem address : https://www.acmicpc.net/problem/13144
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13144_ListofUniqueNumbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 수열이 주어질 때
        // 수열에서 연속한 1개 이상의 수를 뽑았을 때 같은 수가 여러번 등장하지 않는 경우의 수를 구하라
        //
        // 두 포인터 문제
        // 두 개의 포인터를 갖고서, 포인터 사이 구간에는 항상 중복하지 않는 수가 되도록 관리한다
        // 그렇게하면 두 포인터 사이의 길이만큼이 해당 수 구간에서 뽑을 수 있는
        // 첫번째 수가 포함되고, 중복된 수가 없는 경우의 수이다.
        // 예를 들어 1 2 3 1 2인 경우, 처음에 두 포인터는 1과 1을 가르키고
        // 두 포인터 사이의 길이는 3이고, 이는 1, 1 2, 1 2 3의 3가지의 경우를 의미한다.
        // 다음에는 2 와 2를 가르키고 마찬가지로 길이는 3에 이는 2, 2 3, 2 3 1의 3가지 경우를 뜻한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 두 포인터 내의 수들의 유무를 표시할 boolean 배열
        boolean[] checked = new boolean[100_001];

        // 경우의 수를 센다.
        // n이 최대 10만이므로, 경우 수는 int 범위를 넘을 수 있다.
        long count = 0;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            // j는 수열의 범위를 넘지 않으며, 현재 수가 중복된 수가 아니라면
            // 현재의 수를 포함시키고, j를 하나 증가시킨다.
            while (j < nums.length && !checked[nums[j]])
                checked[nums[j++]] = true;
            // i ~ j 구간이 정해졌다.
            count += j - i;
            // 해당 길이에 있는 경우의 수를 더하고
            // 다음 게산을 위해 i번째 수에 해당하는 수는 배제한다.
            checked[nums[i]] = false;
        }
        // 전체 경우의 수를 출력한다.
        System.out.println(count);
    }
}