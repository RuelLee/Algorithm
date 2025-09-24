/*
 Author : Ruel
 Problem : Baekjoon 4090번 뱀파이어 숫자
 Problem address : https://www.acmicpc.net/problem/4090
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4090_뱀파이어숫자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.TreeSet;

public class Main {
    static int[][] counts = new int[3][10];

    public static void main(String[] args) throws IOException {
        // 1827 = 21 * 87과 같이
        // 좌변에 등장한 숫자 각각의 개수가 우변의 등장한 숫자 각각의 개수와 같은 경우 
        // 좌변의 수를 뱀파이어 숫자라고 한다.
        // 우변의 곱하는 두 수는 자릿수도 같아야하지만, 이 문제에서는 다른 것도 뱀파이어 수라고 한다.
        // 숫자 x가 최대 100만으로 주어질 때, x보다 같거나 큰 뱀파이어의 수 중 가장 작은 수를 찾아라
        //
        // 트리셋, 브루트 포스
        // 먼저 중복을 제거하기 위해 셋을 사용하되, 정렬되어있다면 좋으므로, 트리셋을 활용한다.
        // 먼저 100만보다 크거나 같으면서 가장 작은 뱀파이어 수가 1000255임을 미리 계산했다.
        // 따라서 해당 수보다 같거나 작은 뱀파이어 수를 브루트 포스로 구한다.
        // 그 후 뱀파이어 수들을 트리셋에 담아
        // 각 쿼리를 트리셋의 ceiling 함수로 처리한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 트리셋에 뱀파이어 수들을 담는다.
        TreeSet<Integer> treeSet = new TreeSet<>();
        // 100만보다 크거나 같은 뱀파이어의 수들 중 가장 작은 수가 1000255임을 미리 확인
        // 해당 수보다 같거나 작은 뱀파이어의 수만 구한다.
        for (int i = 1; i * i <= 1000255; i++) {
            for (int j = i + 1; i * j <= 1000255; j++) {
                if (isVampireNum(i, j, i * j))
                    treeSet.add(i * j);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (true) {
            int x = Integer.parseInt(br.readLine());
            if (x == 0)
                break;
            
            // 트리셋의 ceiling 함수로 간단하게 처리
            sb.append(treeSet.ceiling(x)).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
    
    // a * b = c일 때
    // c가 뱀파이어의 수인지 확인
    static boolean isVampireNum(int a, int b, int c) {
        // 횟수가 많으므로 배열을 일일이 선언해선 안된다.
        // 전역 변수로 선언하고, 매번 초기화해서 사용
        for (int[] co : counts)
            Arrays.fill(co, 0);
        
        // 각각의 수의 개수 계산
        while (a > 0) {
            counts[0][a % 10]++;
            a /= 10;
        }
        while (b > 0) {
            counts[1][b % 10]++;
            b /= 10;
        }
        while (c > 0) {
            counts[2][c % 10]++;
            c /= 10;
        }
        
        // 좌변과 우변의 수의 개수가 일치하는지 확인
        for (int i = 0; i < counts[2].length; i++) {
            if (counts[0][i] + counts[1][i] != counts[2][i])
                return false;
        }
        return true;
    }
}