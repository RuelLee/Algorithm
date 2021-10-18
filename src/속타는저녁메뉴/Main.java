/*
 Author : Ruel
 Problem : Baekjoon 11585번 속타는 저녁 메뉴
 Problem address : https://www.acmicpc.net/problem/11585
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 속타는저녁메뉴;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // KMP 알고리즘 문제
        // KMP는 반복되는 패턴 내에서 글자가 불일치시, 처음으로 돌아가는 것이 아니라, 기존에 같았던 부분을 재활용하여 시간을 줄인다.
        // 예를 들어 ababc라는 패턴이 주어졌다고 하자
        // 비교하는 문자열이 abababc이라고 할 때 5번째 글자, ababa에서 ababc와 a, c가 불일치한다
        // 이 때 다시 처음으로 돌아가는 것이 아니라, ab'aba'와 'aba'bc 가 일치함을 주목하고, 다음 글자를 확일할 때
        // aba'b'부터 시작한다
        // 이를 통해 시간을 줄이는 것이 KMP 알고리즘
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();
        char[] meat = String.join("", sc.nextLine().split(" ")).toCharArray();
        char[] current = String.join("", sc.nextLine().split(" ")).toCharArray();

        int[] pi = new int[n];      // PI 배열 생성. 문자열 매칭 중 불일치하는 문자를 만났을 때 어디까지 돌아가야하는 것인지를 표시한다.
        int j = 0;
        for (int i = 1; i < meat.length; i++) {
            while (j > 0 && meat[i] != meat[j])     // 불일치한다면, 이전 문자열에 기록된 위치로 j값을 보낸다.
                j = pi[j - 1];

            if (meat[i] == meat[j])     // 일치한다면, pi[i]에 (j + 1) 값을 기록해주자.
                pi[i] = ++j;
        }

        int count = 0;
        j = 0;
        // 원형판이므로 시작점은 어디서 시작하든 그 위치로부터 문자열의 길이만큼이 나와야한다. 따라서 길이의 2배 - 1 위치만큼 값을 늘려나가며, 문자열 길이에 % 값을 취한 값을
        // 위치로 삼으면, 문자열의 어느 지점에서 시작하든 길이가 n인 문자열에 대해 계산할 수 있다.
        for (int i = 0; i < current.length * 2 - 1; i++) {
            while (j > 0 && current[i % current.length] != meat[j])     // 문자가 일치하지 않다면
                j = pi[j - 1];      // 이전 PI 배열에 기록된 값으로 이동한다.

            if (current[i % current.length] == meat[j])     // 일치한다면
                j++;        // j 값을 하나 늘려준다.

            if (j == meat.length) {     // 모든 문자열이 일치했다
                count++;        // 하나 성공 케이스를 저장하고,
                j = pi[j - 1];      // j값에는 이전 파일 배열에 기록된 값을 넣어줌으로써, 이번으로 완성된 경우가 아니라, 중간만 완성됐다고 생각하고 계속 계산을 이어가자.
            }
        }
        int gcd = getGCD(n, count);         // 기약분수의 형태로 나타내야하기 때문에 최대공약수를 구해주자.
        System.out.println(count / gcd + "/" + n / gcd);        // 최대공약수로 각각을 나눠준 값을 출력.
    }

    static int getGCD(int a, int b) {           // 유클리드 호제법을 활용한 최대공약수 구하기.
        int big = Math.max(a, b);
        int small = Math.min(a, b);

        while (small > 0) {     // 작은 쪽이 0이 될 때까지
            int remain = big % small;       // 큰 값을 작은 값으로 나눈 나머지를 취해,
            big = small;        // 큰 쪽에는 작은 값으로 바꿔주고,
            small = remain;     // 작은 쪽은 나머지로 값을 바꿔주는
        }
        // 연산을 지속하여, 남는 big 값이 최대공약수.
        return big;
    }
}