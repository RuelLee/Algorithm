/*
 Author : Ruel
 Problem : Baekjoon 2938번 3으로 나누어 떨어지지 않는 배열
 Problem address : https://www.acmicpc.net/problem/2938
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2938_3으로나누어떨어지지않는배열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 인접한 두 수의 합이 3으로 나누어떨어지지 않도록 배치할 때
        // 가능한 경우를 하나 출력하라
        // 불가능한 경우 -1을 출력한다.
        //
        // 해 구성하기
        // 딱히 이렇다할 알고리즘을 사용하지 않는 문제
        // 합이 3으로 나누어떨어지지 않게 하기 위해
        // 각각을 3의 모듈러 값으로 정리한다.
        // 그 후 모듈러값에 따라 상황들을 생각해보면
        // 나머지가 0인 값들은 서로 이웃할 수 없다.
        // 반드시 옆에 나머지 1이나 2인 값이 와야한다.
        // 나머지가 1인 값들은 0과 1과 이웃할 수 있으며 2와는 이웃할 수 없다.
        // 나머지가 2인 값들도 0과 2와는 이웃할 수 있으며 1과는 이웃할 수 없다.
        // 따라서 0을 우선적으로 배치하며 1 혹은 2를 배치해나간다.
        // 그 후, 0이 하나 남았을 때
        // 현재 마지막으로 나열한 모듈러 값과 같은 수를 연속하여 배치한 후,
        // 0을 배치하고 나머지 모듈러 값이 같은 수들을 연속으로 나열하여
        // 1 ... 1 0 2 ... 2 혹은 2 ... 2 0 1 ... 1 같은 꼴을 만들어준다.
        // 예를 들어
        // 0 0 1 1 2 2 가 주어진다면
        // 0을 우선적으로 배치하고, 1이나 2를 아무거나 하나 배치
        // 그 후 0이 한 개만 남았으므로 이전에 배치한 수를 한번 더 배치하고, 0을 배치한 후 나머지 수 배치
        // 0 1 1 0 2 2 혹은 0 2 2 0 1 1과 같이 배치하면 이웃한 수의 합이 3의 배수가 되지 않는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());

        // 각 수를 3으로 나눴을 때 나머지값으로 정리한다.
        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Queue<Integer>> mods = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            mods.add(new LinkedList<>());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            mods.get(num % 3).offer(num);
        }

        // 모듈러 0의 수가 1과 2의 수의 개수보다 많다면 불가능한 경우.
        // 또한 1과 2과 모두 존재하는데 0이 존재하지 않는다면 이 경우도 불가능하다.
        if ((mods.get(0).size() > mods.get(1).size() + mods.get(2).size() + 1) ||
                (mods.get(0).isEmpty() && !mods.get(1).isEmpty() && !mods.get(2).isEmpty())) {
            System.out.println(-1);
        } else {
            StringBuilder sb = new StringBuilder();
            int preNum = 1;
            // 0을 우선적으로 배치한다.
            // 배치하며 1이나 2중 더 많은 수를 0 뒤에 배치.
            while (mods.get(0).size() > 1) {
                sb.append(mods.get(0).poll()).append(" ");
                preNum = mods.get(1).size() >= mods.get(2).size() ? mods.get(1).poll() : mods.get(2).poll();
                sb.append(preNum).append(" ");
            }

            // 모듈러값만 남겨둠
            preNum %= 3;
            // 이전에 배치된 모듈러 값과 동일한 값을 갖는 수들을 우선적으로 모두 배치
            while (!mods.get(preNum).isEmpty())
                sb.append(mods.get(preNum).poll()).append(" ");
            // 그 후 0을 배치하고
            if (!mods.get(0).isEmpty())
                sb.append(mods.get(0).poll()).append(" ");
            // 나머지 수를 모두 배치한다.
            preNum = preNum == 2 ? 1 : 2;
            while (!mods.get(preNum).isEmpty())
                sb.append(mods.get(preNum).poll()).append(" ");
            sb.deleteCharAt(sb.length() - 1);
            // 답안 출력
            System.out.println(sb);
        }
    }
}