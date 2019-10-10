## 统一异常处理

Spring在3.2版本增加了一个注解@ControllerAdvice，和@ExceptionHandler一起使用，可以处理异常, 但是仅限于当前Controller中处理异常, @ControllerAdvice可以配置basePackage下的所有controller. 所以结合两者使用,就可以处理全局的异常了


#### 统一异常处理类
```
@Slf4j
@ResponseBody
@ControllerAdvice
public class UnifiedExceptionHandler {
    /**
     * 自定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BaseException.class)
    public ErrorResponse handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getResponseEnum().getCode(), e.getMessage());
    }

    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(CommonResponseEnum.SERVER_ERROR.getCode(), e.getMessage());
    }
}
```
代码中的handleBaseException方法会处理请求中抛出的BaseException异常（包含继承了BaseException子类异常） 
非BaseException 的异常会在 handleException 方法中被处理，如果有其他的异常需要特殊处理返回结果，可以通过@ExceptionHandler注解定义要处理的异常类型，并做特殊的处理
如：
```
    /**
	 *  validation 异常返回信息封装
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.OK)
	public R MethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		Map<String,String> errMap = new HashMap<>();
		List<FieldError> errors = exception.getBindingResult().getFieldErrors();
		for(FieldError fieldError : errors){
			errMap.put(fieldError.getField(),fieldError.getDefaultMessage());
		}
		R r  = R.failure(ResultCode.PARAMETER_ERROR);
		r.setMsg(errMap);
		return r;
	}
```
以上代码是针对 MethodArgumentNotValidException 异常做了特殊的返回结果处理

## 用 Assert(断言) 替换 throw exception 

```
    public void test1() {
        ...
        User user = userDao.selectById(userId);
        Assert.notNull(user, "用户不存在.");
        ...
    }

    public void test2() {
        // 另一种写法
        User user = userDao.selectById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在.");
        }
    }
```
使用 assert 代替 throw exception 或 try…catch 代码 可以让代码只关注逻辑，代码可读性更高，更加整洁

#### Assert 接口
```
public interface Assert {
    /**
     * 创建异常
     * @param args
     * @return
     */
    BaseException newException(Object... args);

    /**
     * 创建异常
     * @param t
     * @param args
     * @return
     */
    BaseException newException(Throwable t, Object... args);

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertNotNull(Object obj) {
        if (obj == null) {
            throw newException(obj);
        }
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     * @param obj 待判断对象
     * @param args message占位符对应的参数列表
     */
    default void assertNotNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(args);
        }
    }
}
```

实现参考 BusinessExceptionAssert 和 CommonExceptionAssert

#### 使用 Enum 封装异常返回结果

定义 IResponseEnum 接口
```
/**
public interface IResponseEnum {
    /**
     * 获取返回码
     * @return 返回码
     */
    int getCode();

    /**
     * 获取返回信息
     * @return 返回信息
     */
    String getMessage();
}

```
实现参考 CommonResponseEnum 和 BusinessResponseEnum 两个类

## 使用
Controller 层 可以使用定义的Enum和assert判断
```
 @GetMapping("/user")
 public R biz(String username){
    // 通用异常，判断字符串是否为空
    CommonResponseEnum.NOT_NULL.assertNotEmpty(username);
    User user = map.get(username);
    // 业务异常，判断用户是否为空
    BusinessResponseEnum.USER_NOT_NULL.assertNotNull(user);
    return new R(user);
}
```

## demo 
#### 1、通用异常

`http://localhost:8080/user?username=`

异常结果：

`{"code":5001,"message":"数据不能为空"}`

#### 2、业务异常
`http://localhost:8080/user?username=admin`

正确结果：

`{"code":0,"message":"SUCCESS","data":{"username":"admin","password":"1234"}}
`

异常结果：

`{"code":4002,"message":"用户不存在"}`

#### 3、未定义异常

正常结果：

`http://localhost:8080/divide?x=1&y=2`

`{"code":0,"message":"SUCCESS","data":"0.50"}`

异常结果：

`http://localhost:8080/divide?x=1&y=0`

`{"code":9999,"message":"/ by zero"}`

