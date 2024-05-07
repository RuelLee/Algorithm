/*
 Author : Ruel
 Problem : Baekjoon 11917번 이상한 수열
 Problem address : https://www.acmicpc.net/problem/11917
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11917_이상한수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개 길이의 수열이 주어진다.
        // n+1번째부터는 이전에 등장한 서로 다른 수의 개수가 해당 수가 된다.
        // 예를 들어 2 13 -7 2일 경우, 5번째는 3, 6번째는 4이다.
        // m번째 수를 구하라
        //
        // 정렬 문제
        // 조금 생각해보면
        // n개의 수열에서 서로 다른 수의 개수를 j라 할 때
        // j보다 크지만 가장 작은 수열에서 등장한 수를 k라 하면
        // m의 값에 따라 n+1번째 수는 j, j+1, j+2, ..., k, k, k, ...
        // 만약 k가 존재하지 않는다면 계속 증가하고,
        // 존재하면 k에 이를 때까지 하나씩 값이 증가하고, 이후로는 k로 수렴한다.
        // 위와 같은 규칙을 생각하면 푼다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        int[] array = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 구하고자 하는 순서
        int m = Integer.parseInt(br.readLine());

        // m이 n보다 같거나 작으면 바로 해당 값 출력
        if (m <= n)
            System.out.println(array[m - 1]);
        else {      // 그렇지 않고 그 이후라면
            // 정렬하여
            Arrays.sort(array);
            // 서로 다른 수의 개수를 센다.
            int count = 1;
            for (int i = 1; i < array.length; i++) {
                if (array[i - 1] != array[i])
                    count++;
            }

            // 그리고 count보다는 같거나 크지만
            // 가장 작은 수를 찾는다.
            int idx = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] >= count) {
                    idx = i;
                    break;
                }
            }
            // 만약 count보다 같거나 큰 수가 n번째 수까지 등장하지 않거나
            // 구하고자 하는 m번째가 idx번째 수까지 도달하지 않는다면
            // 1씩 증가하는 구간에 있는 수이다.
            // 따라서 count에서 해당 개수만큼 증가시킨 값을 출력한다.
            if (array[idx] < count ||
                    count + (m - n) < array[idx])
                System.out.println(count + (m - n - 1));
            else        // 그 외의 경우에는 idx번째 수에 수렴한 경우. idx번째 수 출력
                System.out.println(array[idx]);
        }
    }
}