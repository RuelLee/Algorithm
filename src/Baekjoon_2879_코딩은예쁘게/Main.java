/*
 Author : Ruel
 Problem : Baekjoon 2879번 코딩은 예쁘게
 Problem address : https://www.acmicpc.net/problem/2879
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2879_코딩은예쁘게;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 소스 코드의 인덴트를 고치고 있다.
        // n개의 줄이 주어지고, 두번째 줄에는 현재 각 줄에 들어간 들여쓰기
        // 세번째 줄에는 올바른 들여쓰기의 수가 주어진다
        // 여러 줄을 선택해 동시에 들여쓰기를 할 수 있다고 할 때
        // 코드를 올바른 들여쓰기로 수정하는데 들어가는 총 들여쓰기 수정 횟수는 몇 번인가?
        //
        // 그리디한 문제
        // 현재 들여쓰기와 올바른 들여쓰기의 차이를 구해
        // 같은 부호(+ or -) 를 갖는 인접한 줄들을 동시에 선택해 한번에 들여쓰기를 해 들여쓰기 수를 최소화해준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 현재 들여쓰기 상태를 한번에 받고
        int[] diffs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 올바른 들여쓰기를 하나씩 입력받아
        StringTokenizer st = new StringTokenizer(br.readLine());
        // diffs에 올바른 들여쓰기와 현재 들여쓰기 상태의 차이를 저장해둔다.
        for (int i = 0; i < n; i++)
            diffs[i] = Integer.parseInt(st.nextToken()) - diffs[i];

        // 전체 들여쓰기 횟수를 센다.
        int action = 0;
        // 0부터 n-1 까지의 diffs들을 살펴본다.
        int start = 0;
        while (start < n) {
            // 만약 diffs[start]가 0이 되었다면, 다음으로 넘어간다.
            if (diffs[start] == 0) {
                start++;
                continue;
            }

            // 한번에 들여쓰기할 크기를 구한다
            int size = diffs[start];
            // start부터 같은 부호를 갖는 범위를 구하여 한번에 들여쓰기를 해줘야한다.
            // end의 시작값은 start + 1
            int end = start + 1;
            while (end < n) {
                // start와 end가 다른 부호를 갖는다면 멈춘다.
                if (size * diffs[end] <= 0)
                    break;

                // 그렇지 않고 같은 부호를 갖는다면 들여쓰기할 크기를 계산해야한다
                // 크기가 양의 부호를 갖고 있다면, diffs[end]중 더 작은 값을 선택하고
                // 크기가 음의 부호를 갖고 있다면 diff[end] 중 더 큰 값(=절대값으로는 더 작은 값)을 선택한다.
                size = size > 0 ? Math.min(size, diffs[end++]) : Math.max(size, diffs[end++]);
            }
            // 최종적으로 start로부터 같은 부호를 갖는 최대 범위를 구했다.
            // start부터 end - 1까지 size 만큼 한번에 들여쓰기를 진행한다.
            for (int i = start; i < end; i++)
                diffs[i] -= size;
            // 그 때의 들여쓰기를 수행한 횟수는 size의 절대값만큼이다.
            // 해당 수를 action에 더해준다.
            action += Math.abs(size);
        }
        // 전체 들여쓰기 횟수를 출력한다.
        System.out.println(action);
    }
}