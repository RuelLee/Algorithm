/*
 Author : Ruel
 Problem : Baekjoon 6137번 문자열 생성
 Problem address : https://www.acmicpc.net/problem/6137
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6137_문자열생성;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 문자로 이루어진 문자열 S가 입력된다.
        // 이 문자열의 각 문자들로 새로운 문자열 T를 만든다.
        // 1. 문자열 S의 가장 앞의 문자를 새로운 문자열 가장 뒤에 추가한다.
        // 2. 문자열 S의 가장 뒤의 문자를 새로운 문자열 가장 뒤에 추가한다.
        // 위 두 조건을 통해 사전상 가장 앞에 위치하는 새로운 문자열을 만들고자할 때
        // 그 문자열은?
        //
        // 그리디, 두 포인터 문제
        // 앞 뒤의 문자가 서로 다르다면 당연히 더 사전순으로 이른 문자를 택해 새로운 문자열에 추가하면 된다.
        // 하지만 같다면 아무거나 추가하면 될까?
        // BACB라는 문자열이 주어질 때, 앞 뒤의 문자가 B로 같다.
        // 앞의 B를 먼저 택할 경우, BABC라는 문자열이 되지만
        // 뒤의 문자열을 먼저 택할 경우 BBAC 라는 문자열이 된다.
        // 따라서 앞 뒤가 서로 같은 문자라면, 서로 한칸씩 안의 문자들을 살펴보며 어느 쪽이 더 일찍 사전순으로 이른 문자가 나타나는지
        // 확인하고 해당 방향의 문자를 넣어야한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 문자의 개수
        int n = Integer.parseInt(br.readLine());
        
        // 각 문자들
        char[] letters = new char[n];
        for (int i = 0; i < letters.length; i++)
            letters[i] = br.readLine().charAt(0);
        
        // 새로운 문자열
        StringBuilder sb = new StringBuilder();
        // 두 포인터
        int left = 0;
        int right = letters.length - 1;
        // 포인터가 같은 위치를 가르킬 때까지
        while (left <= right) {
            // 서로 다른 문자를 가르키고 있다면
            // 사전순으로 이른 문자를 추가한다.
            if (letters[left] != letters[right])
                sb.append(letters[left] < letters[right] ? letters[left++] : letters[right--]);
            else {      // 서로 같은 문자를 가르키고 있다면
                boolean found = false;
                // 한칸씩 안쪽을 살펴보며 사전순으로 더 이른 문자가 어느 방향에서 나타나는지 확인하고
                // 해당 방향의 문자를 추가한다.
                for (int i = 1; left + i < right - i; i++) {
                    if (letters[left + i] != letters[right - i]) {
                        sb.append(letters[left + i] < letters[right - i] ? letters[left++] : letters[right--]);
                        found = true;
                        break;
                    }
                }
                // 만약 그러한 방향을 찾지 못했다면
                // (= 양쪽이 계속 같은 문자들이라면)
                // 앞쪽이나 뒤쪽 상관이 없다.
                // 임의로 앞쪽 문자를 추가한다.
                if (!found)
                    sb.append(letters[left++]);
            }

            // 생성된 문자열의 길이를 살펴보며 80글자마다 개행을 해준다.
            if ((left + letters.length - 1 - right) % 80 == 0)
                sb.append("\n");
        }
        // 전체 답안 출력.
        System.out.println(sb);
    }
}