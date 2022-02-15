/*
 Author : Ruel
 Problem : Baekjoon 16496번 큰 수 만들기
 Problem address : https://www.acmicpc.net/problem/16496
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16496_큰수만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0이상 10억 이하의 수가 최대 1000개 주어질 때
        // 수를 붙여만들 수 있는 가장 큰 수는?
        // 일정 기준에 따라 정렬하여 이어붙이면 된다. 이 때 그 기준이란
        // 숫자의 길이가 같을 경우 큰 순서대로 사용하면 되지만 길이가 다른 경우가 생각할 거리가 된다
        // 예를 들어 3과 32의 경우 어느 숫자가 더 먼저 오는게 유리한가
        // 크기 자체가 큰 것은 32이지만 먼저올 때 더 유리한건 3이다
        // 그렇다면 3과 34은 어떠한가? 3보다 34가 먼저 오는 것이 유리하다
        // 따라서 길이가 다른 숫자의 경우 해당 숫자를 계속하여 이어붙인 값과 비교를 해야한다
        // 예를 들어 3과 325이라면 3(33)과 325를 비교한다. 333이 더 크므로 3이 먼저 오도록한다
        // 하지만 또 다른 직접적인 기준으로, 실제로 두 숫자를 이어붙인 두 개의 경우를 비교하는 방법도 있다
        // 3325와 3253을 비교하면 전자가 크므로 3이 우선한다
        // 사실 이 방법이 더 직관적이고 코딩도 간단하다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();      // n값에 대한 정보가 주어지긴 하지만 사실 필요없다.
        String[] strings = br.readLine().split(" ");        // 숫자들이 " "을 기준으로 주어지므로 " "기준으로 잘라내어 Strings 배열에 저장해준다.
        Arrays.sort(strings, (o1, o2) -> {      // 배열을 정렬한다.
            String o2First = o2 + o1;       // o2를 앞에 붙인 스트링을
            return o2First.compareTo(o1 + o2);      // o1을 앞에 붙인 스트링과 비교한다.(큰 값이 우선적으로 와야하므로)
        });
        if (strings[0].equals("0"))     // 가장 큰 값이 0이라면 뒤의 숫자들도 모두 0일 것이다. 0을 이어붙인들 0이다.
            System.out.println(0);
        else        // 아니라면 배열에 정렬된 순서대로 이어붙이면 된다. 스트림을 사용해서 한줄로 끝내보자.
            System.out.println(Arrays.stream(strings).collect(Collectors.joining()));
    }
}