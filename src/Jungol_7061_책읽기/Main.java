/*
 Author : Ruel
 Problem : Jungol 7061번 책 읽기
 Problem address : https://jungol.co.kr/problem/7061
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_7061_책읽기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n권의 책이 매력도에 따라 비내림차순으로 주어진다.
        // 총 t분이 주어지고, 한 권의 책을 읽는데는 a분, 표지만 보고 넘기는데는 b분이 걸린다고 한다.
        // 순차적으로 책을 읽거나, 표지만 보고 넘긴다고 할 때, 읽은 책들의 매력도 합을 최대화하고자할 때 그 값은?
        //
        // 두 포인터 문제
        // 비내림차순으로 매력도가 주어지기 때문에 가능한 뒷부분의 책을 읽는 것이 좋다.
        // 따라서 두 포인터를 활용하여
        // i ~ j번째까지의 책을 읽고, 0 ~ i-1까지의 책을 표지만 읽는다고 생각하고 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n권의 책, 주어지는 시간 t
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        // 책을 읽는 시간 a, 표지만 보고 넘기는 시간 b
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        // 책의 매력도
        int[] books = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            books[i] = Integer.parseInt(st.nextToken());

        long ans = 0;
        // 현재 소모 시간
        int timeSum = 0;
        // 읽은 책들의 매력도 합
        long charmSum = 0;
        // 현재 읽은 책의 마지막 번호
        int j = -1;
        // 책을 읽지 않고 넘길 때 얻는 시간 차
        int diff = a - b;
        // 순차적으로 넘기되, i가 j+1보다는 작거나 같아야한다.
        // 읽지도 않은 책을 넘길 수는 없기 때문
        for (int i = 0; i < n && i <= j + 1; i++) {
            // 시간이 허락하는 한 최대한 뒷부분의 책을 읽는다.
            while (j + 1 < n && t - timeSum >= a) {
                timeSum += a;
                charmSum += books[++j];
            }
            // 현재 매력도를 ans에 반영
            ans = Math.max(ans, charmSum);

            // i번 책을 읽지 않고 넘기는 경우로 바꾼다.
            // 그 때의 소모 시간과 합산 매력도 계산
            timeSum -= diff;
            charmSum -= books[i];
        }
        // 답 출력
        System.out.println(ans);
    }
}