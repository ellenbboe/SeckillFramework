package sf.exception;

import sf.result.CodeMsg;

public class BaseException extends RuntimeException{

    private CodeMsg codemsg;
    public BaseException(CodeMsg codeMsg) {
        this.codemsg = codeMsg;
    }

    public CodeMsg getCodemsg() {
        return codemsg;
    }
}
