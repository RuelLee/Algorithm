/*
 Author : Ruel
 Problem : Baekjoon 20366번 같이 눈사람 만들래?
 Problem address : https://www.acmicpc.net/problem/20366
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20366_같이눈사람만들래;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 눈덩이가 주어지고, 이를 쌓아 눈사람을 2개 만들려고 한다.
        // 이 때 두 눈 사람의 크기 차이가 최소가 되게 하고 싶을 때, 크기 차이는?
        //
        // 두 포인터를 활용한 문제.
        // n이 최대 600까지 주어지므로, 한 쪽의 눈 사람은 조합을 통해 모든 경우의 수를 따진다.
        // 눈덩이들은 정렬하고, 조합으로 선택된 눈사람의 두 눈덩이 사이에서 다시 2개를 골라
        // 눈사람들의 크기 차이가 최소가 되게끔 만든다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 눈덩이의 수
        int n = Integer.parseInt(br.readLine());
        // 눈덩이들
        int[] snows = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 정렬
        Arrays.sort(snows);

        // 최소 차이를 계산
        int minDiff = Integer.MAX_VALUE;
        // 조합으로 선택하는 작은 눈덩이.
        for (int aLeft = 0; aLeft < snows.length - 3; aLeft++) {
            // 조합으로 선택하는 큰 눈덩이.
            // 사이에 최소 2개의 눈덩이가 있어야 투포인터로 다른 하나의 눈덩이를 선택할 수 있다.
            for (int aRight = aLeft + 3; aRight < snows.length; aRight++) {
                // 투포인터로 찾는 눈사람의 눈덩이들
                // 하나는 왼쪽 끝
                int bLeft = aLeft + 1;
                // 하나는 오른쪽 끝에서 시작.
                int bRight = aRight - 1;

                // 조합으로 선택된 눈덩이의 크기.
                int aSize = snows[aLeft] + snows[aRight];
                while (bLeft < bRight) {
                    // 투포인터로 찾는 눈덩이의 크기.
                    int bSize = snows[bLeft] + snows[bRight];
                    // 두 눈사람 크기 차이값이 이전에 계산됐던 눈사람들의 크기 차이보다 작다면 갱신.
                    minDiff = Math.min(minDiff, Math.abs(aSize - bSize));
                    // 만약 조합으로 선택한 눈사람이 투포인터로 찾은 눈사람보다 크다면
                    // 투포인터의 눈사람 크기를 키워주기 위해, bLeft를 하나 증가시켜준다.
                    if (aSize > bSize)
                        bLeft++;
                    // 같거나 작다면 눈사람 크기를 줄여주기 위해 bRight를 하나 줄여준다.
                    else
                        bRight--;
                }
            }
        }
        // 두 눈사람 크기의 최소값을 출력한다.
        System.out.println(minDiff);
    }
}