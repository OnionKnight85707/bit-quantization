package com.mzwise.common.util;

/**
 * @author piao
 */
public final class IdCardVerifyUtil {
    private static final int FIFTEEN = 15;
    private static final int SEVENTEEN = 17;
    private static final int EIGHTEEN = 18;
    /**
     * 18 位身份证验证器
     */
    private static final int[] WI = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    private static final int[] VI = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};

    /**
     * 验证输入的身份证号
     *
     * @param idcard 身份证号
     * @return boolean
     */
    public static boolean verify(String idcard) {
        if (idcard.length() == FIFTEEN) {
            idcard = upToEighteen(idcard);
        }
        if (idcard.length() != EIGHTEEN) {
            return false;
        }
        String verify = idcard.substring(17);
        return verify.equals(getVerify(idcard));
    }

    public static String getVerify(String eighteenCardId) {
        int tail = 0;
        int[] arrays = new int[SEVENTEEN];
        if (eighteenCardId.length() == EIGHTEEN) {
            eighteenCardId = eighteenCardId.substring(0, 17);
        }
        if (eighteenCardId.length() == SEVENTEEN) {
            int sum = 0;
            for (int i = 0; i < SEVENTEEN; i++) {
                String k = eighteenCardId.substring(i, i + 1);
                arrays[i] = Integer.parseInt(k);
            }
            for (int i = 0; i < SEVENTEEN; i++) {
                sum = sum + WI[i] * arrays[i];
            }
            tail = sum % 11;
        }
        return tail == 2 ? "X" : String.valueOf(VI[tail]);
    }

    public static String upToEighteen(String fifteenCardId) {
        StringBuilder eighteenCardId = new StringBuilder(fifteenCardId.substring(0, 6));
        eighteenCardId.append("19");
        eighteenCardId.append(fifteenCardId, 6, 15);
        eighteenCardId.append(getVerify(eighteenCardId.toString()));
        return eighteenCardId.toString();
    }

}
