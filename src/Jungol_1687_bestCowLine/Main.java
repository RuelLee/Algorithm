/*
 Author : Ruel
 Problem : Jungol 1687번 best cow line
 Problem address : https://jungol.co.kr/problem/1687
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1687_bestCowLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static char[] cows;

    public static void main(String[] args) throws IOException {
        // 대문자로 이루어진 문자열이 주어진다.
        // 해당 문자열에서 맨 앞 혹은 맨 뒤의 문자를 꺼내 새로운 문자열 뒤에 추가해나간다.
        // 사전순으로 가장 앞서는 문자열을 만들고자한다.
        // 만들어지는 문자열은?
        //
        // 그리디 문제
        // 두 개의 포인터를 통해 현재 가르키는 문자 중 사전순으로 앞서는 문자를 꺼내 뒤에 붙여나간다.
        // 대신 가르키는 문자가 같은 경우, 다음 문자를 서로 비교해야한다.
        // 더 빠른 문자를 빨리 가르키는 포인터를 우선적으로 사용해야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 문자열의 크기 n
        int n = Integer.parseInt(br.readLine());
        cows = new char[n];
        for (int i = 0; i < cows.length; i++)
            cows[i] = br.readLine().charAt(0);

        // 두 포인터
        int front = 0;
        int rear = n - 1;
        StringBuilder sb = new StringBuilder();
        // 두 포인터가 만날 때까지
        while (front <= rear) {
            // 앞 문자가 유리한 경우
            if (frontFirst(front, rear))
                sb.append(cows[front++]);
            else    // 뒤 문자가 유리한 경우
                sb.append(cows[rear--]);

            // 80개마다 개행
            if ((front + (n - 1) - rear) % 80 == 0)
                sb.append("\n");
        }
        // 만약 문자열의 길이가 80의 배수라면 마지막에 개행이 추가되어있으므로 제거
        if (n % 80 == 0)
            sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }

    // 앞 포인터가 유리한지, 뒤 포인터가 유리한지 판별한다.
    static boolean frontFirst(int front, int rear) {
        while (front < rear) {
            // 앞 포인터가 유리한 경우
            if (cows[front] < cows[rear])
                return true;
            else if (cows[front] > cows[rear])      // 뒤 포인터가 유리한 경우
                return false;
            else {
                // 같다면 서로 다음 글자를 비교한다.
                front++;
                rear--;
            }
        }
        // 결국 모두 같은 경우
        // true나 false 아무거나 반환해도 사실 결과는 같다.
        return true;
    }
}