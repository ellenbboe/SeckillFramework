package sf.exception;


import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sf.result.CodeMsg;
import sf.result.Result;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class BaseExceptionHandler {

    @ExceptionHandler
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        if( e instanceof BaseException)
        {
            BaseException ex =(BaseException)  e;
            return Result.error(ex.getCodemsg());
        }
        else if( e instanceof BindingResult)
        {
            List<ObjectError> errors = ((BindingResult) e).getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.findAll(msg));
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

}
