/*
 Author : Ruel
 Problem : Baekjoon 27210번 신을 모시는 사당
 Problem address : https://www.acmicpc.net/problem/27210
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27210_신을모시는사당;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 돌상이 일렬로 서 있다.
        // 각 돌상은 왼쪽 혹은 오른쪽을 보고 있다.
        // 연속한 돌상들을 금칠하여 가능한 많은 금색 돌상들이 같은 방향을 보고 있을수록 깨달음의 수치가 커진다고 한다.
        // |왼쪽을 바라보는 금색 돌상 - 오른쪽을 바라보는 금색 돌상| = 깨달음의 수치
        // 라고 할 때, 얻을 수 있는 최대 깨달음은?
        //
        // 누적합 문제
        // 연속한 돌상들을 칠하므로
        // 연속하여 같은 곳을 바라보고 있는 돌상들을 하나의 값으로 압축하는 것이 가능하다.
        // 왜냐하면 한 쪽을 바라보는 돌상의 개수를 최대화해야하기 때문에,
        // 같을 곳을 보는 연속한 돌상들 중 부분만 선택하는 경우는 없기 때문이다.
        // 그 후, 처음부터 시작하여, 깨달음의 수치가 0 이상인 경우 더해가며, 그 최대값을 구하고
        // 음수가 된 경우(=반대쪽을 더 많이 보는 경우)는 현재값을 버리고, 다음 구간부터 다시 더해가며 최대값을 구한다.
        // 왼쪽을 보는 석상들과 오른쪽을 보는 석상들을 각각 진행하며 둘 중 더 큰 값을 가져온다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 석상들
        int[] statues = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        List<Integer> list = new ArrayList<>();
        int count = 1;
        // 연속하여 같은 곳을 바라보는 석상들을 하나의 값으로 압축한다.
        for (int i = 0; i < statues.length; i++) {
            if (i + 1 < statues.length && statues[i] == statues[i + 1])
                count++;
            else {
                list.add(statues[i] == 1 ? count : count * -1);
                count = 1;
            }
        }
        
        // 왼쪽을 보는 석상들을 위주로 금칠을 하며 최대값을 찾는 경우
        int leftSum = 0;
        int rightSum = 0;
        // 오른쪽을 보는 석상들을 위주로 금칠을 하며 최대값을 찾는 경우
        int leftMax = 0;
        int rightMax = 0;
        for (int i = 0; i < list.size(); i++) {
            // 현재 값을 더한다.
            leftSum += list.get(i);
            // 오른쪽을 보는 석상의 경우, 위에서 음수 누적합으로 처리했으므로, 빼준다.
            rightSum -= list.get(i);

            // 만약 왼쪽을 보는 석상들의 합이 음수가 되었다면
            // (= 오른쪽을 보는 석상이 더 많아졌다면)
            // leftSum에 0을 주고 다음 석상부터 다시 합을 계산하기 시작한다.
            if (leftSum < 0)
                leftSum = 0;
            // 그렇지 않은 경우, 현재 값이 최대값인지 확인.
            else
                leftMax = Math.max(leftMax, leftSum);

            // 마찬가지로 오른쪽을 보는 석상들에 대해서도 같은 처리를 한다.
            if (rightSum < 0)
                rightSum = 0;
            else
                rightMax = Math.max(rightMax, rightSum);
        }

        // 그렇게 구해진 두 방향에 대한 최대 깨달음 수치들 중 더 큰 값을 출력한다.
        System.out.println(Math.max(leftMax, rightMax));
    }
}