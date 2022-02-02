/*
 Author : Ruel
 Problem : Baekjoon 13506번 카멜레온 부분 문자열
 Problem address : https://www.acmicpc.net/problem/13506
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 카멜레온부분문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 접두사와 접미사, 그리고 문자열 중간에 한번 더 등장할 때 이를 카멜레온 부분 문자열이라고 하자
        // 예를 들어 fixprefixsuffix 경우, fix가 접두사, 접미사 그리고 중간에도 등장하므로 카멜레온 부분 문자열이다
        // 문자열이 주어질 때 최대 길이의 카멜레온 부분 문자열을 구하라.
        // kmp 알고리즘을 활용하여 풀어야한다
        // pi배열을 구하고, 마지막 문자열의 pi배열 값이 접두사와 접미사가 일치하는 최대 길이가 될 것이다.
        // 이를 통해 중간에 한번 더 등장하는지 여부를 찾고 등장한다면, 해당 길이의 접두사/접미사가 답이 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        // pi 배열 구하기.
        int[] pi = new int[input.length()];
        int j = 0;
        for (int i = 1; i < input.length(); i++) {
            while (j > 0 && input.charAt(i) != input.charAt(j))
                j = pi[--j];
            if (input.charAt(i) == input.charAt(j))
                pi[i] = ++j;
        }

        // pi배열 값이 각각 몇 개가 등장했는지 세준다.
        // 이를 통해 각 길이에 해당하는 접두사가 전체 문자열에서 몇 번 등장했는지 알 수 있다.
        int[] counts = new int[input.length()];
        for (int i = 1; i < pi.length; i++)
            counts[pi[i]]++;

        // 접미사와도 일치해야하므로, 최대길이는 pi[마지막문자]의 값으로 넣고 시작하자.
        int maxLength = pi[pi.length - 1];
        // 접두사/접미사의 길이가 0보다 크며, count가 2보다 작을 경우(총 세번이상 등장하지 않았을 경우)
        // pi[마지막문자 - 1]의 값으로 길이를 바꿔나간다
        // 여기서 바꾸면서 주의해야할 사항이 있는데, maxLength가 0이 아닌 값으로 바뀐다면
        // 이는 접두사/접미사 내에서 반복되는 문자열이 있다는 뜻이 된다
        // 예를 들어 papapapap일 경우, pi배열은 0 0 1 2 3 4 5 6 7이 되고, counts 배열은 2 1 1 1 1 1 1 1이 된다
        // 먼저 길이가 7짜리(papapap)가 3번 이상 등장했는지 본다면, 2번(1번 + 접두사는 세지지 않았으므로 1번)이 등장했으므로 안된다
        // 그렇다면 다음 값은 6번 인덱스가 가르키는 길이가 5짜리 (papap)가 3번이상 등장했는지 봐야한다. counts[5] 역시 1이라 안된다고 판단한다면.... 안된다.
        // 왜냐하면 사실 7로 세었던 papapap가 'papap'ap와 pa'papap'처럼 길이 5짜리의 문자열이 2개 들어있다고 봐야하기 때문이다.
        // 따라서 반복되는 구간이 있는 경우(pi[maxLength -1] != 0) 다음 값을 하나 늘려줘 해당 길이를 취하게 해주면 된다.
        while (maxLength > 0 && counts[maxLength] < 2)
            counts[maxLength = pi[maxLength - 1]]++;

        System.out.println(maxLength == 0 ? -1 : input.substring(0, maxLength));
    }
}