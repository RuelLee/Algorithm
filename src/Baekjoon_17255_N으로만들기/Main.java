/*
 Author : Ruel
 Problem : Baekjoon 17255번 N으로 만들기
 Problem address : https://www.acmicpc.net/problem/17255
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17255_N으로만들기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static HashMap<String, Integer> hashMap;

    public static void main(String[] args) throws IOException {
        // 수를 만들 때, 처음에 어느 한 수를 적고, 다음 두 가지 방법
        // 1. 왼쪽에 하나의 수를 적는다.
        // 2. 오른쪽에 하나의 수를 적는다.
        // 을 통해 모든 수를 만들 수 있다.
        // 수 n이 주어질 때, n을 만드는 방법의 수는?
        //
        // 백트래킹, 메모이제이션 문제
        // 처음 수 n으로 부터 왼쪽의 수를 없애거나,
        // 오른쪽 수를 없애는 경우를 경우를 모두 구해 더해준다.
        // 백트래킹을 통해 긴 수부터 시작하며, 
        // 메모이제이션을 통해 이미 계산했던 수는 계산 없이 빠르게 참조를 한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 수 n
        String n = br.readLine();
        
        // 메모이제이션
        hashMap = new HashMap<>();
        // 답안 출력
        System.out.println(findAnswer(n));
    }
    
    // 백트래킹
    static int findAnswer(String n) {
        // 만약 길이가 1이라면 해당 수는 처음에 적는 수가 된다.
        // 해당 경우의 수는 무조건 1
        if (n.length() == 1)
            return 1;
        // 이미 계산한 결과가 있다면 더 들어가지 않고 바로 참조한다.
        else if (hashMap.containsKey(n))
            return hashMap.get(n);
        
        int sum = 0;
        // 왼쪽에 수를 더해 수 n을 만드는 경우
        String preMinus = n.substring(1);
        // 오른쪽에 수를 더해 n을 만드는 경우
        String postMinus = n.substring(0, n.length() - 1);

        // 왼쪽에 수를 더해 n을 만드는 경우의 수를 센다.
        sum += findAnswer(preMinus);
        // preMinus 와 postMinus가 같다면
        // 앞에 수를 더하나, 뒤에 수를 더하나 구분이 안되는 경우.
        // 다를 경우에만 경우의 수를 센다.
        if (!preMinus.equals(postMinus))
            sum += findAnswer(postMinus);
        // 결과값을 메모해주고
        hashMap.put(n, sum);
        // 반환한다.
        return sum;
    }
}