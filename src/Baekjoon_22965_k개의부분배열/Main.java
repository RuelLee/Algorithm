/*
 Author : Ruel
 Problem : Baekjoon 22965번 k개의 부분 배열
 Problem address : https://www.acmicpc.net/problem/22965
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22965_k개의부분배열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 서로 다른 수를 원소로 갖는 배열 a가 주어진다.
        // 이를 최대 k개의 부분으로 나누어 원소들을 정렬하고자 한다.
        // 잘라서 재배치하는 행동은 무한히 할 수 있다고 한다.
        // k의 최솟값을 구하라
        //
        // 정렬 문제
        // 조금만 생각해보면, k개 3일 때는 모든 경우에 대해 정렬하는 것이 가능하다.
        // 원하는 부분만 잘라, 맨 앞이든 맨 뒤든 보낼 수 있기 때문.
        // 따라서 답이 1 혹은 2가 되는 경우를 이제 생각해보자.
        // 이미 정렬 모두 되어있다면 k를 여러 개로 나눌 필요가 없으므로 1이다.
        // 답이 2가 되기 위해서는 첫번째 원소가 가장 작은 아니되, 모든 원소들이 원형으로 정렬되어 있는 경우이다.
        // 예를 들어 1 ~ 4까지의 원소가 주어진다면
        // 1 2 3 4 인 경우는 답이 1인 경우이고
        // 3 4 1 2인 경우는 답이 2인 경우다.
        // 1 2 4 3인 경우는 답이 3이 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 원소
        int n = Integer.parseInt(br.readLine());
        int[][] array = new int[n][2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < array.length; i++) {
            array[i][0] = Integer.parseInt(st.nextToken());
            array[i][1] = i;
        }
        // 원소들을 크기에 따라 정렬
        Arrays.sort(array, Comparator.comparingInt(a -> a[0]));

        boolean continuous = true;
        // 원래 순서가 원형 형태로 연속성을 갖는지 확인한다.
        for (int i = 0; i < array.length; i++) {
            if ((array[i][1] + 1) % n != array[(i + 1) % n][1]) {
                continuous = false;
                break;
            }
        }
        
        // 원래 수열이 연속성을 갖고
        if (continuous) {
            // 첫번째 원소가 가장 작았다면
            // 원래 수열을 잘라서 재배치할 필요가 없다.
            // 답은 1
            if (array[0][1] == 0)
                System.out.println(1);
            // 첫번째 원소가 제일 작지 않다면
            // 두 부분으로만 나누어 정렬하는 것이 가능
            else
                System.out.println(2);
        } else      // 그 외의 경우는 세 부분으로 나누면 모든 경우에 대해 정렬 가능
            System.out.println(3);
    }
}