package 다음큰숫자;

public class Solution {
    public static void main(String[] args) {
        // 범위가 100만까지 이므로 그리 크지 않다.
        // 주어진 숫자 n을 2진법 했을 때의 1의 개수를 세고,
        // n+1부터 1의 개수를 확인해 다음 숫자를 찾아낸다.
        int n = 78;

        int amount = amountOfOne(n);
        int answer = 0;
        for (int i = n + 1; i < 1000000; i++) {
            if (amountOfOne(i) == amount) {
                answer = i;
                break;
            }
        }
        System.out.println(answer);
    }

    static int amountOfOne(int n) {
        String num = Integer.toBinaryString(n);

        int count = 0;
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) == '1')
                count++;
        }
        return count;
    }
}