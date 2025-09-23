/*
 Author : Ruel
 Problem : Baekjoon 32759번 준근이와 마법 공방
 Problem address : https://www.acmicpc.net/problem/32759
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32759_준근이와마법공장;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int LIMIT = 1_000_000_000 + 7;

    public static void main(String[] args) throws IOException {
        // m개의 마법석이 주어진다.
        // 마법석을 합성하는데는 다음 과정을 따른다.
        // 1. 모든 마력석들을 기계에 넣는다.
        // 2. 마력석들 중 두개를 선택한다.
        // 3. 두 개의 마력석을 합쳐 새로운 마력석을 만든다. 이 마력석은 재료가 되는 두 마력석 마나의 합이고,
        //    재료가 되는 두 마력석은 사라지지 않고 반환된다. 마력석을 만들 때는, 만들 수 있는 마나 수치가 가장 큰 마나석을 만든다.
        // 합성하는 과정을 n번 거쳤을 때, 마지막에 만들어지는 마력석의 마나 수치를 구하라
        // 그 값이 매우 클 수 있으므로 10^9 + 7로 나눈 나머지 값을 출력한다.
        //
        // 애드 혹 문제
        // 합성할 때마다, 마나의 수치가 가장 큰 마력석을 만들므로 사실 마나 수치가 가장 큰 두 돌만 가지고 값만 가지고서 비교하면 된다.
        // 다만 이 때의 고려할 점은
        // 1. 두 재료 마력석 모두 마나 수치가 0보다 작은 경우
        // 결과물이 재료 마력석들보다 더 작아진다. 합성을 시행하더라도 합성 결과물은 항상 버려진다.
        // 2. 두 재료 마력석 모두 0보다 큰 경우
        // 계속 합성을 지속해나가다보면 그 수가 기하급수적으로 커진다. 따라서 mod를 해줘야하는데,
        // 무턱대고 10^9 + 7보다 커졌다고 다 mod를 해버렸다가는 마력석 두 개의 마나 수치 대소 관계가 뒤바뀔 수 있다.
        // 따라서 가장 많은 마나 수치를 갖는 마력석은 10^9 + 7 이상 2 * 10^9 + 7의 미만의 값을 갖도록 유지하고
        // 두번째로 많은 마나 수치를 갖는 마력석은 10^9 + 7 미만의 값을 갖도록 유지하며 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n번의 합성 시행, m개의 돌
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 가장 마나 수치가 높은 두 돌
        long first = -LIMIT;
        long second = -LIMIT;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            long a = Long.parseLong(st.nextToken());
            if (a > first) {
                second = first;
                first = a;
            } else if (a > second)
                second = a;
        }

        // first가 0보다 작다면 두 돌 모두 0보다 작으므로
        // 합성 돌의 값은 second보다도 작게되어 항상 버려지므로 n-1번 합성을 할 필요가 없다.

        // first가 0보다 큰 경우
        // 마나 수치의 상승이 일어나며, 시행 횟수가 많아진다면 두 돌 모두 0보다 큰 값을 갖게되어
        // 값이 기하급수적으로 커진다.
        if (first > 0) {
            // 마지막 n번째 시행 때는 나오는 돌이 first보다 큰 값을 갖게 될지
            // second보다 큰 값을 갖게 될지는 알 수 없다.
            // 따라서 n-1번 반복문을 통해 first와 second를 관리해주고
            // 마지막 n번째 시행에는 나오는 값을 직접 출력한다.
            for (int i = 0; i < n - 1; i++) {
                // 합성 돌
                long synthesis = first + second;
                // second 또한 0보다 크다면, 합성 돌은 first보다 크다.
                // second에는 first를, first에는 synthesis를 넣되
                // 대소 관계를 유지하기 위해
                // first는 LIMIT 이상, 2 * LIMIT 미만의 범위로 표현하고
                // second는 LIMIT 미만의 범위로 표현한다.
                if (second > 0) {
                    second = first % LIMIT;
                    first = synthesis % LIMIT + LIMIT;
                } else  // second가 0보다 작다면 synthesis는 first보다는 같거나 작고, second보다는 큰 값을 갖게 될 것이다.
                    second = synthesis % LIMIT;
            }
        }

        // 마지막 n번째 시행에 나온 돌의 마력 수치를 출력한다.
        System.out.println((LIMIT + first + second) % LIMIT);
    }
}