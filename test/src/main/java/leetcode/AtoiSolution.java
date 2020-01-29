package leetcode;

public class AtoiSolution {
    public static void main(String[] args) {
        int result = myAtoi("-    234324");
        System.out.println(result);
    }

    public static int myAtoi(String str) {
        char[] charArr = str.toCharArray();
        Long result = 0L;
        int startIndex = 0;
        boolean flag = true;//正数
        int length = 0;
        for (int i = 0; i < charArr.length; i++) {
            if (startIndex == i) {
                if (charArr[i] == ' ') {
                    startIndex++;
                    continue;
                }
                if (charArr[i] == '+' || charArr[i] == '0') {
                    continue;
                }
                if (charArr[i] == '-') {
                    flag = false;
                    continue;
                }
            }
            if (charArr[i] >= '0' && charArr[i] <= '9') {
                result = result * 10 + charArr[i] - '0';
                length++;
                if (length > 10) {
                    break;
                }
            } else {
                break;
            }
        }
        if (flag) {
            if (result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
        } else {
            result = -result;
            if (result < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
        }
        return result.intValue();
    }
}