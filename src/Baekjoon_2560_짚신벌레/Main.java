/*
 Author : Ruel
 Problem : Baekjoon 2560번 짚신벌레
 Problem address : https://www.acmicpc.net/problem/2560
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2560_짚신벌레;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree;
    static final int LIMIT = 1000;

    public static void main(String[] args) throws IOException {
        // 짚신벌레는 태어난지 a일째에 성체가 되어 매일 한마리씩 새로운 개체를 만들기 시작하며
        // b일째부터는 더 이상 새로운 개체를 만들지 않고,
        // d일째에는 죽는다.
        // 0일째에 1마리의 짚신벌레가 있다면
        // n일에 살아있는 짚신벌레의 수를 1000으로 나눈 나머지를 출력하라
        //
        // 누적합 문제
        // d일간 생존하므로, 각 짚신벌레의 생존일에 따라 구분하면 DP로도 풀 수 있으나
        // d가 최대 1만, n이 최대 100만으로 주어지므로 메모리 공간이 부족하다.
        // 따라서 새로 태어나는 짚신벌레들만을 누적합으로 구해나가며
        // 최종적으로 n - d + 1일부터 n일까지 태어난 짚신 벌레들이, n일째에 살아있는 짚신벌레들이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 성체가 되는 일 a
        int a = Integer.parseInt(st.nextToken());
        // 더 이상 개체를 만들지 않는 일 b
        int b = Integer.parseInt(st.nextToken());
        // 죽는 일 d
        int d = Integer.parseInt(st.nextToken());
        // n일차의 살아있는 짚신벌레의 수를 구한다.
        int n = Integer.parseInt(st.nextToken());

        // 펜윅트리로 누적합 처리
        // 펜윅트리에선 0이 비어있어야하므로 n+2개의 공간을 통해 구한다.
        fenwickTree = new int[n + 2];
        // 0일(펜윅트리에선 0을 사용해야하므로 1일차로)
        inputValue(1, 1);
        for (int i = 2; i < fenwickTree.length; i++) {
            // i일에 새로 태어나는 짚신벌레의 수
            // i - b + 1일부터 i - a일에 태어난 짚신 벌레들은 새로운 개체를 만들 수 있다.
            int birth = getSumFromZeroToN(Math.max(i - a, 0)) - getSumFromZeroToN(Math.max(i - b, 0));
            // 음수가 아닌 양수가 되게끔 처리
            birth = (birth + LIMIT) % LIMIT;
            inputValue(i, birth);
        }
        
        // n + 1일 차에 살아있는 짚신 벌레의 수는
        // n + 1 - d ~ n + 1일에 태어난 짚신 벌레들
        int alive = getSumFromZeroToN(n + 1) - getSumFromZeroToN(Math.max(n + 1 - d, 0));
        System.out.println((alive + LIMIT) % LIMIT);
    }
    
    // n까지의 누적합
    static int getSumFromZeroToN(int n) {
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum % LIMIT;
    }

    // idx에 value값을 추가한다.
    static void inputValue(int idx, int value) {
        while (idx < fenwickTree.length) {
            fenwickTree[idx] = (fenwickTree[idx] + value) % LIMIT;
            idx += (idx & -idx);
        }
    }
}