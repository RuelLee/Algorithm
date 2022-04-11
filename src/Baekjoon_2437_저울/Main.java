/*
 Author : Ruel
 Problem : Baekjoon 2437번 저울
 Problem address : https://www.acmicpc.net/problem/2437
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2437_저울;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 양팔 저울과 n개의 추가 주어진다
        // 양팔 저울의 한 쪽에는 무게를 재려는 물건만 올릴 수 있고, 다른 한 쪽에는 추만 올릴 수 있다
        // 잴 수 없는 가장 가벼운 무게는 얼마인가
        //
        // 코드로는 간단했지만, 풀이 방법을 떠올리기 어려웠던 문제
        // 잴 수 없는 가장 가벼운 무게이므로, 그 미만의 무게들은 전부 표현이 가능해야한다
        // 따라서 추를 가벼운 순서대로 정렬한 뒤,잴 수 없는 무게들을 차례대로 따져나간다
        // 현재까지 계산된 추들로 잴 수 있는 무게의 범위를 기록한다고 생각해야한다
        // 만약 1 2이라는 추를 계산했다면, 1부터 두 추의 합 3까지의 크기가 3인 범위를 표현이 가능한 것이다
        // 따라서 다음 추는 이 추들로 표현이 불가능한 4나 혹은 그 이하의 추가 들어와야만 한다(5이상의 추밖에 없다면 4가 표현이 불가능하므로 스탑)
        // 계산하는 추들의 무게를 합으로 구하면서, 합 + 1보다 큰 수가 들어온다면 합 + 1을 표현이 불가능하므로 해당 수가 잴 수 없는 가장 작은 무게가 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] weights = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(weights);

        int sum = 0;        // 0 ~ sum까지의 무게를 잴 수 있다.
        for (int weight : weights) {
            // 다음 추가 sum의 바로 다음인 sum + 1 or 그 이하라면 잴 수 있는 무게의 범위를 확장해나가는 것이 가능하다
            // sum에 해당 추의 무게를 더하고 다음 추를 살펴본다
            if (weight <= sum + 1)
                sum += weight;
                // 그렇지 않고 sum + 1 보다 무거운 추가 들어온다면 sum + 1의 무게를 표현이 불가능하다.
            else
                break;
        }
        // 멈춘 시점의 sum + 1을 출력하면 잴 수 없는 가장 가벼운 무게가 된다.
        System.out.println(sum + 1);
    }
}