package Q124나라의숫자;

public class Solution {
    static StringBuilder sb;
    static char[] digit = {'4', '1', '2'};

    public static void main(String[] args) {
        // 1, 2, 4의 숫자만 존재하는 나라.
        // 0이라는 개념이 없기 때문에
        // 3이라는 숫자는 '4'로 표현되고, 4라는 숫자는 '11'으로 표현된다
        // 여기서 '4'도 숫자 3을 가르키고, '11'의 앞의 '1'도 숫자 3을 가르킨다.
        // 따라서 원 숫자가 딱 3일 때는 '4'로 표현해야하고, 4이상일 때는 3진법과 마찬가지로 한자리 앞에 '1'로 표현해야한다.
        // 따라서 10진법 -> 3진법으로 표현하는 것과 유사한 방법을 따라가되, 나머지가 0일 경우는 '4'로 써주고, 대신 앞자리의 값을 하나 낮춰준다
        // == 현재 숫자가 3으로 나누어떨어지는 것이므로, -1을 해서 3으로 나눴을 경우 값이 하나 줄어들도록 조정해준다.
        // 이를 재귀적으로 수행하고 나머지 값을 역순으로 배치한다면 값을 표현할 수 있다.

        int n = 3;
        sb = new StringBuilder();
        recursion(n);
        System.out.println(sb.toString());
    }

    static void recursion(int num) {
        if (num > 3)
            recursion((num - 1) / 3);
        sb.append(digit[num % 3]);
    }
}