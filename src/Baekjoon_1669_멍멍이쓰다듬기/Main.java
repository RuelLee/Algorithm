/*
 Author : Ruel
 Problem : Baekjoon 1669번 멍멍이 쓰다듬기
 Problem address : https://www.acmicpc.net/problem/1669
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1669_멍멍이쓰다듬기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 원숭이와 멍멍이의 키가 주어진다. 멍멍이의 키는 원숭이보다 크거나 같다
        // 원숭이는 자신의 키를 변화시킬 수 있는데, 하루에 늘릴 수 있는 키의 양을 1cm씩 조절할 수 있다고 한다
        // 예를 들어 전 날에 3cm를 키웠다면 다음 날엔 2cm, 3cm, 4cm를 키울 수 있다.
        // 원숭이와 개가 키가 같아지는 최소 소요일은 며칠인가
        //
        // o(1)로 한번에 풀려고 하니 생각이 많이 엉켜서 고민했던 문제
        // 기본 개념은 등차수열의 합이다.
        // 키의 변화량을 1cm씩 증가시킬 수 있으므로
        // 1 -> 1               하루
        // 2 -> 1 + 1           이틀
        // 3 -> 1 + 1 + 1       사흘
        // 4 -> 1 + 2  + 1      사흘
        // 5 -> 1 + 2 + 1 + 1   나흘
        // 6 -> 1 + 2 + 2 + 1   나흘
        // 처럼 서서히 변할 수 있다.
        // 따라서  변화량은 1 -> n까지 서서히 증가시킨 후, n -> 1까지 다시 서서히 감소한다.
        // 최대 변화량에 따라 생각해보면
        // 최대 변화량 1 -> 총 변화량은 1까지
        // 최대 변화량 2 -> 총 변화량은 4(= 1 + 2 + 1)까지
        // 최대 변화량 3 -> 총 변화량 9(= 1 + 2 + 3 + 2 + 1)까지 가능한 규칙이 보인다.
        // 따라서 차이(=총 변해야하는 키의 크기)의 제곱근을 구한 후, 이를 올림하여 정수값을 취해 최대 변화량을 구한다
        // 최대 변화량의 값을 통해 1 ~ 최대 변화량 ~ 1의 형태를 취할 것이므로 이 때의 소요일은 최대 변활 + (최대 변화량 -1)이다
        // 위에서 올림을 취해 구했기 때문에, 사실 최대변화량 - 1의 값에서 끝날 수도 있다.
        // 이를 확인하기 위해 다시 최대 변화량의 제곱을 취한 후 그 값에서 최대변화량을 빼, 최대변화량 - 1에서도 총 변화량을 만들 수 있는지 확인한다
        // 가능하다면 계산된 날짜에서 -1일을 해주고, 그렇지 않다면 계산된 날짜를 출력한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());

        long diff = y - x;
        // 만약 차이가 없다면 키를 변화시키지 않아도 된다.
        if (diff == 0)
            System.out.println(0);
        else {
            // 제곱 단위로 계산할 수 있는 최대 변화량의 크기를 구한다.
            long maxLength = (int) Math.ceil(Math.sqrt(diff));
            // 그 때의 소요일.
            long day = maxLength * 2 - 1;

            // 위에서 올림을 취했기 때문에, 최대 변화량의 제곱이 총 변화량보다 크지만 가장 작은 값을 구했다.
            // 하지만 최대변화량 - 1 까지만 증가시켜서 총 변화량을 만들 수도 있는지 확인해야한다.
            // (예를 들어 6의 경우, 최대변화량이 3으로 계산되지만, 1 2 2 1 과 같이 2의 연속된 형태로 총 변화량 6을 맞출 수 있다.)
            // 만약 총 변화량이 최대 변화량의 제곱 - 최대변화량 값보다 같거나 작다면 최대변화량 -1까지만 가더라도 총 변화량을 맞출 수 있다.
            // 그 경우 소요일이 하루 적어진다.
            if (diff <= maxLength * maxLength - maxLength)
                System.out.println(day - 1);
            // 그렇지 않다면 그대로 출력.
            else
                System.out.println(day);
        }
    }
}