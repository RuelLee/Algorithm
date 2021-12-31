/*
 Author : Ruel
 Problem : Baekjoon 3033번 가장 긴 문자열
 Problem address : https://www.acmicpc.net/problem/3033
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 가장긴문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    static String input;
    static final int MOD = 1000003;
    static long[] pow;

    public static void main(String[] args) throws IOException {
        // 문자열이 주어진다
        // 이 문자열 내에서 2번 이상 반복되는 부분 문자열의 최대길이를 구해보자
        // 문자열의 길이가 최대 20만까지 주어지므로 시간적 복잡도를 잘 생각해야한다
        // k라는 길이의 문자열이 n번 반복된다하면, k보다 작은 길이의 문자열은 반드시 n번 이상 반복된다
        // 따라서 2번 이상 반복되는 k라는 값을 구하면 k이하의 길이의 문자열들에 대해서는 보지 않아도 된다
        // -> 이분 매칭을 통해서 빠르게 찾아주자
        // 반복 여부는
        // 특정 구간의 문자열에 대해서 하나의 값(=해쉬값)으로 표현하고 이를 통해 같은 해쉬값이 발견됐을 때, 문자열을 비교하여 일치여부를 판단하자.
        // 그 중 Rabin Karp라는 알고리즘은 길이 n의 부분 문자열에 대해서
        // 가장 우측 값부터, 2^0, 2^1, ... , 2^(n-1)을 곱해 해쉬값을 만들어준다
        // 다음 부분 문자열에 대해서 연산할 때, 첫번째 문자 * 2^(n-1)의 값이 빠지고, 그 상태에서 2배를 한 다음, 새로운 문자열 값이 들어가는 연속적인 형태에 주목하면 된다
        // 예를 들어 abcd라는 문자열에 대해서 길이가 2인 부분 문자열 해쉬값들을 정리한다고 하면
        // ab -> hash[0] = b * 2^0 + a * 2^1
        // bc -> hash[1] = (hash[0] - a * 2^1) * 2 + c
        // cd -> hash[2] = (hash[1] - b * 2^2) * 2 + d
        // 와 같이 앞의 한 숫자를 빼고, 뒤의 한 숫자는 더해가며 연속적으로 해쉬값을 찾는 방법이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int l = Integer.parseInt(br.readLine());
        input = br.readLine();

        // 20만까지 너무 큰 값이므로 2의 제곱들이 int 범위를 가뿐하게 벗어날 것이다
        // 모듈러 연산의 값을 활용하자.
        pow = new long[l];
        pow[0] = 1;
        for (int i = 1; i < pow.length; i++)
            pow[i] = pow[i - 1] * 2 % MOD;

        int left = 1;
        int right = l - 1;
        while (left < right) {      // 이분 매칭으로 최대 길이의 값을 찾아준다.
            int mid = (left + right) / 2;
            if (areThereOverlappedParts(mid))
                left = mid + 1;
            else
                right = mid;
        }
        System.out.println(left - 1);
    }

    static boolean areThereOverlappedParts(int n) {     // 라빈 카프 알고리즘을 통해 찾아보자
        HashMap<Integer, HashSet<Integer>> hashMap = new HashMap<>();       // 해쉬값과 중복된 값이 있다면 저장될 해쉬셋

        long hash = 0;      // 0 ~ n-1까지의 부분 문자열의 해쉬값을 구해준다.
        for (int i = n - 1; i >= 0; i--)
            hash = (hash + input.charAt(i) * pow[n - 1 - i]) % MOD;
        hashMap.put((int) hash, new HashSet<>());
        hashMap.get((int) hash).add(0);     // 그리고 해쉬맵에 저장

        for (int i = 1; i + n - 1 < input.length(); i++) {      // 그 후로는 첫번째 글자는 빼고, 마지막 글자는 추가하며 해쉬값을 갱신해준다.
            // 0보다 작은 값으로 hash값이 생성되는 걸 양수로 바꿔준다.
            if (hash - input.charAt(i - 1) * pow[n - 1] < 0)
                hash += 'z' * MOD;
            hash -= input.charAt(i - 1) * pow[n - 1];
            hash <<= 1;
            hash += input.charAt(i + n - 1);
            hash %= MOD;
            if (hashMap.containsKey((int) hash)) {      // 이미 있던 해쉬값이라면
                for (int sameHash : hashMap.get((int) hash)) {      // 기존에 있는 해쉬값이 가르키는 문자열을 찾아가
                    boolean check = true;
                    for (int j = 0; j < n; j++) {       // 서로 일치하는지 확인한다
                        if (input.charAt(sameHash + j) != input.charAt(i + j)) {
                            check = false;
                            break;
                        }
                    }
                    if (check)      // 일치한다면 true 반환
                        return true;
                }
            } else      // 새로운 해쉬값이라면, 해쉬셋을 추가해주고
                hashMap.put((int) hash, new HashSet<>());
            // 중복됐지만 일치하는 문자열이 없거나, 새로운 해쉬값이라면
            // hash에 해당하는 곳에 현재 문자열의 시작 idx 를 넣어준다.
            hashMap.get((int) hash).add(i);
        }
        return false;
    }
}