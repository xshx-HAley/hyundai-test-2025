package hyundai.web.global.util;

public class PhoneNumberFormatter {

    /**
     * 01012345678 -> 010-1234-5678 형태로 변환
     * 잘못된 형식의 문자열은 그대로 반환
     */
    public static String format(String rawNumber) {
        if (rawNumber == null || !rawNumber.matches("\\d{11}")) {
            return rawNumber; // 유효하지 않으면 그대로 반환
        }

        String prefix = rawNumber.substring(0, 3);
        String middle = rawNumber.substring(3, 7);
        String suffix = rawNumber.substring(7);

        return String.format("%s-%s-%s", prefix, middle, suffix);
    }
}
