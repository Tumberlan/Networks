public class ProtokolCodes {

    public enum CodeValue {
        NAME_SUCCESSFULLY_READ,
        FILE_SUCCESSFULLY_READ,
        NAME_DOES_NOT_MATCH_WITH_NAME_LENGTH,
        SIZE_OF_FILE_DOES_NOT_MATCH_WITH_FILE_SIZE
    }

    public int wrapCodeToInt(CodeValue codeValue) {
        switch (codeValue) {
            case NAME_SUCCESSFULLY_READ -> {
                return 0;
            }
            case FILE_SUCCESSFULLY_READ -> {
                return 1;
            }
            case NAME_DOES_NOT_MATCH_WITH_NAME_LENGTH -> {
                return 2;
            }
            case SIZE_OF_FILE_DOES_NOT_MATCH_WITH_FILE_SIZE -> {
                return 3;
            }
        }
        return -1;
    }
}
