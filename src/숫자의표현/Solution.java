package 숫자의표현;

public class Solution {
    public static void main(String[] args) {
        // 주어진 n을 연속된 자연수의 합으로 표현할 때 몇가지로 표현할 수 있는지에 대한 문제이다.
        // 공차가 1인 등차수열의 합을 생각해보자.
        // 숫자의 갯수 * (첫 숫자 + 마지막 숫자 ) / 2 = n
        // 첫 숫자 + 마지막 숫자 = 2 * n / 숫자의 갯수
        // 이 때 첫 숫자와 마지막 숫자는 숫자의 갯수 - 1만큼의 차이가 나야한다
        // 따라서 a + (a - (숫자의 갯수 - 1)) = 2 * n / 숫자의 갯수
        // 2a - 숫자의 갯수 + 1 = 2 * n /  숫자의 갯수
        // 2a = 2 * n / 숫자의 갯수 + 숫자의 갯수 - 1
        // 이 때 2a는 반드시 짝수 이므로 %2 연산을 했을 때 0이 나와야만 한다.
        // 숫자의 갯수 값을 1부터 하나씩 올려가며 계산하되, 숫자의 갯수가 첫 숫자 + 마지막 숫자 값보다 커지면 값이 없으므로 의미가 없다.

        int n = 15;

        int count = 1;
        int numOfCases = 0;
        while (true) {
            if (n * 2 % count == 0) {
                int value = n * 2 / count;
                if (value <= count)
                    break;
                if ((value + count - 1) % 2 == 0)
                    numOfCases++;
            }
            count++;
        }
        System.out.println(numOfCases);
    }
}