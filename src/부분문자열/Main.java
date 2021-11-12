/*
 Author : Ruel
 Problem : Baekjoon 16916번 부분 문자열
 Problem address : https://www.acmicpc.net/problem/16916
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 부분문자열;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // KMP 알고리즘 복습
        // 전에 이해를 했더라도 오랫동안 안 보면 까먹는다!
        // KMP 알고리즘은 비교하는 문자열 내에서 반복되는 단어가 있는지 체크하고,
        // 이를 문자열 비교에 활용하는 방법이다
        // 원래 문자열이 acababad이고 비교 문자열이 abad일 때, acaba'b'에서 한번 틀리게 된다. 그럼 다음 acabab'a'차례일 때 다시 처음 a부터 시작하지 않고
        // 비교문자열에서 aba까지는 일치하는 것을 이용해서 세번째 a부터 비교를 시작한다.
        Scanner sc = new Scanner(System.in);

        String original = sc.nextLine();
        String part = sc.nextLine();

        int[] pi = new int[part.length()];      // 해당 글자가 틀렸을 때 어디로 돌아가야하는지 나타내는 PI 배열
        int j = 0;      // 비교열은 0번부터 시작
        for (int i = 1; i < part.length(); i++) {       // 같은 문자열을 비교하므로 첫글자는 무조건 같다.
            while (j > 0 && part.charAt(j) != part.charAt(i))           // j값이 0보다 크면서, i와 j 글자가 다르다면
                j = pi[j - 1];      // j값에 이전 pi배열 값을 넣어준다.

            if (part.charAt(j) == part.charAt(i))       // 두 글자가 일치한다면
                pi[i] = ++j;        // i번째 pi배열에 (j+1)값을 넣어주고, j값을 하나 증가시킨다.
        }

        j = 0;
        boolean found = false;
        for (int i = 0; i < original.length(); i++) {       // 원래 문자열은 처음부터 비교 시작
            while (j > 0 && original.charAt(i) != part.charAt(j))       // j값이 0보다 크면서, 원래 문자열의 i번째, 비교 문자열의 j번째 글자가 서로 다르면
                j = pi[j - 1];          // pi배열에서 맞는 j값을 찾아간다

            if (original.charAt(i) == part.charAt(j))       // 일차하는 문자를 찾았다면
                ++j;        // j값을 하나 늘려준다

            if (j == part.length()) {       // j가 비교하는 문자열의 끝에 다다랐다면, 원래 문자열에 비교 문자열이 포함되어있는 상태.
                found = true;       // 찾았다는 걸 알려주고
                break;      // 종료
            }
        }
        // 찾았다면 1 값을, 못 찾았다면 0값을 출력
        System.out.println(found ? 1 : 0);
    }
}