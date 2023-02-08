/*
 Author : Ruel
 Problem : Baekjoon 5012 불만 정렬
 Problem address : https://www.acmicpc.net/problem/5012
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5012_불만정렬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열이 주어진다.
        // 그 때 ai > aj > ak와 i < j < k를 만족하는 세 원소의 개수를 구하는 프로그램을 작성하라
        //
        // DP + 세그먼트 트리 문제
        // 세그먼트 트리에 DP를 더한 문제
        // stackedFenwickTree[a][b]로 행렬을 만든다.
        // DP의 a,b의 의미는 b 숫자가 a + 1번째 올 수 있는 경우의 수이다.
        // 예를 들어 stackedFenwickTree[1][2] 라면
        // 숫자 2가 두번째(j)에 위치할 수 있는 경우의 수이다.
        // 순차적으로 수를 세그먼트 트리에 추가시켜나가며 해당 수가 1, 2, 3번째의 위치할 경우
        // 가능한 경우의 수를 계산해나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        // DP
        // 간단하게 구현할 수 있는 세그먼트 트리 이용
        long[][] stackedFenwickTree = new long[3][n + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            // 들어온 수 num
            int num = Integer.parseInt(st.nextToken());
            // num이 1번째 수로 오는 경우는 항상 존재
            // stackedFenwickTree[0]에 추가.
            inputValue(num, 1, stackedFenwickTree[0]);

            // 2, 3번째 오는 경우 계산.
            for (int j = 1; j < stackedFenwickTree.length; j++) {
                // j - 1번째에 수를 세운 경우들 중
                // num보다 큰 수가 있는 경우가 총 몇 개인지 센다.
                long count = countBiggerN(num, stackedFenwickTree[j - 1]);
                // 만약 해당 경우가 없다면 더 이상 num을 i, j, k 중 포함시킬 수 없는 경우.
                // 반복문 종료
                if (count == 0)
                    break;
                // 아니라면 j - 1번째에 num보다 큰 수가 count 개수 만큼 있으므로
                // j번째에 num이 count번 만큼의 경우를 만들 수 있다.
                // 해당 값 추가.
                inputValue(num, count, stackedFenwickTree[j]);
            }
        }

        // 모든 수들에 대해 3번째까지 수들을 뽑을 수 있는 경우를 센다.
        System.out.println(countSmallerN(n, stackedFenwickTree[2]));
    }

    // 펜윅 트리 n위치에 value 값을 추가한다.
    static void inputValue(int n, long value, long[] fenwickTree) {
        while (n < fenwickTree.length) {
            fenwickTree[n] += value;
            n += (n & -n);
        }
    }

    // n보다 큰 수가 펜윅트리에 몇 개 있는지 센다.
    static long countBiggerN(int n, long[] fenwickTree) {
        return countSmallerN(fenwickTree.length - 1, fenwickTree)
                - countSmallerN(n, fenwickTree);
    }

    // n보다 같거나 작은 수가 펜윅 트리에 몇 개 있는지 센다.
    static long countSmallerN(int n, long[] fenwickTree) {
        long sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum;
    }
}