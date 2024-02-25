/*
 Author : Ruel
 Problem : Baekjoon 24508번 나도리팡
 Problem address : https://www.acmicpc.net/problem/24508
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24508_나도리팡;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 바구니에 각각 나도리들이 담겨있다.
        // 나도리들은 k마리가 한 바구니에 모이면 터져버린다.
        // t이하의 나도리들을 다른 바구니로 옮겨 모든 나도리들을 터뜨릴 수 있는가?
        //
        // 두 포인터, 정렬 문제
        // 나도리들을 옮기는 횟수를 최소화해야한다.
        // 따라서 적은 나도리들이 담겨있는 바구니에서 많은 나도리들이 담겨있는 바구니로 나도리들을 옮겨야한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 바구니, 나도리들이 모이면 터지는 수 k, 옮길 수 있는 횟수 t
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 나도리들
        int[] nadoris = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 정렬
        Arrays.sort(nadoris);
        
        // 두 포인터
        int left = 0;
        int right = nadoris.length - 1;
        while (left < right && t >= nadoris[left]) {
            // 적은 나도리가 담긴 바구니에서
            // 많은 나도리가 담긴 바구니로 나도리들을 옮긴다.
            while (left != right && nadoris[right] + nadoris[left] <= k && t >= nadoris[left]) {
                nadoris[right] += nadoris[left];
                t -= nadoris[left];
                nadoris[left++] = 0;
            }
            int diff = k - nadoris[right];
            if (left != right && t >= diff) {
                nadoris[right] += diff;
                nadoris[left] -= diff;
                right--;
                t -= diff;
            }
        }
        
        // 최종적으로 모든 바구니에는
        // 0 혹은 k의 나도리만 담겨있어야한다.
        boolean answer = true;
        for (int na : nadoris) {
            if (na != 0 && na != k) {
                answer = false;
                break;
            }
        }
        
        // 답안 출력
        System.out.println(answer ? "YES" : "NO");
    }
}