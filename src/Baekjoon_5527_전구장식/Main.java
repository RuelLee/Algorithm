/*
 Author : Ruel
 Problem : Baekjoon 5527번 전구 장식
 Problem address : https://www.acmicpc.net/problem/5527
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5527_전구장식;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 전구가 켜져있거나 꺼져있다.
        // 이 때 한 기계를 사용하면, 선택한 범위의 전구들이 반전된다고 한다.
        // 학생들은 불이 켜져있는 전구와 꺼져있는 전구가 번갈아가면서 나타나는 패턴을 좋아한다.
        // 기계를 한 번만 사용하여 가장 긴 교대 패턴을 만들고자할 때, 그 길이는?
        //
        // 애드 혹? 문제
        // 먼저 교대 패턴의 길이를 차례대로 세어본다.
        // 가령 1 0 1 1 1 0 0 1 이라고 주어진다면
        // 1 0 1 -> 3, 1 -> 1, 1 0 -> 2, 0 1 -> 2
        // 3 1 2 2 가 된다.
        // 여기서 기계를 사용한다는 의미는 구분되어지는 교대 패턴들을 앞 뒤로 잇는다는 말과 같다.
        // 3에 사용할 경우, 뒤의 1과 붙어 3 + 1 = 4가 되고
        // 1에 사용할 경우, 앞의 3과 뒤의 2가 붙어 6이 된다.
        // 2는 1 + 2 + 2  = 5가 되고,
        // 마지막 2는 2 + 2 = 4가 된다.
        // 따라서 찾을 수 있는 길이는 6이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 주어지는 전구의 개수와 상태
        int n = Integer.parseInt(br.readLine());
        int[] bulbs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 리스트를 통해 구분되는 교대 패턴을 세어나간다.
        List<Integer> list = new ArrayList<>();
        int idx = 0;
        int count = 1;
        while (idx < bulbs.length - 1) {
            // 뒤의 전구와 상태가 다르다면 count만 증가
            if (bulbs[idx] != bulbs[idx + 1])
                count++;
            else {
                // 상태가 같다면 idx에서 교대 패턴 종료
                // 현재까지의 교대 패턴의 길이인 count를 list에 추가하고
                // count를 다시 1로 초기화한다.
                list.add(count);
                count = 1;
            }
            // 다음 전구로 이동
            idx++;
        }
        // 마지막 전구에서는 뒤의 전구가 없으므로 무조건 교대패턴이 종료된다.
        // 마지막 전구까지의 count도 추가한다.
        list.add(count);

        // 번갈아가면서 등장하는 교대 패턴들의 수가 1개만 존재할 수도 있다.
        // 따라서 교대패턴의 길이 초기값은 첫번째와 존재한다면 두번째 길이까지 더해준다.
        int sum = list.get(0) + (list.size() > 1 ? list.get(1) : 0);
        // 최대 교대 패턴의 길이
        int max = sum;

        // 구해진 교대 패턴들에 기계를 사용했을 경우(= 앞 뒤의 교대 패턴과 이었을 경우)
        // 그 때의 교대 패턴 길이를 구하고 최대 길이와 비교한다.
        for (int i = 1; i < list.size() - 1; i++) {
            sum += list.get(i + 1);
            max = Math.max(max, sum);
            sum -= list.get(i - 1);
        }
        // 최대 교대 패턴의 길이 출력
        System.out.println(max);
    }
}