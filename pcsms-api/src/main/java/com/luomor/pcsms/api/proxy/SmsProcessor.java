package com.luomor.pcsms.api.proxy;


import com.luomor.pcsms.api.entity.SmsResponse;
import com.luomor.pcsms.comm.exception.SmsBlendException;

import java.lang.reflect.Method;
/**
 * 执行器接口
 *
 * @author Peter
 * @since 2025/10/27 13:03
 */
public interface SmsProcessor extends Order {
    /**
     *  preProcessor
     * <p> 前置拦截方法 此方法将在短信发送方法之前进行执行，但请勿在此方法中修改实例对象或者方法对象，否则可能会导致并发问题
     * @param method 方法对象
     * @param source 实例对象
     * @param param  参数列表
     * @author Peter
    */
    default Object[] preProcessor(Method method, Object source, Object[] param) {
        return null;
    }

    /**
     *  postProcessor
     * <p> 后置拦截方法 此方法执行在发送方法执行完毕之后获取到返回值之后
     * @param result 返回值
     * @param param  参数列表
     * @author Peter
    */
    default Object postProcessor(SmsResponse result, Object[] param) {
        return null;
    }

    /**
     *  exceptionHandleProcessor
     * <p> 异常拦截执行器，在发送方法执行过程中发生异常，将会通过此方法进行反馈。
     * <p> 请注意，此方法一旦捕捉到相应异常并抛出新异常后，会中断后续执行器的进行 如果在所有的异常执行器中均没有抛出异常，则后续会进入后置方法执行器
     * @param method 方法对象
     * @param source 实例对象
     * @param param  参数列表
     * @param exception 异常
     * @author Peter
    */
    default void exceptionHandleProcessor(Method method, Object source, Object[] param, Exception exception) throws RuntimeException {
        throw new SmsBlendException(exception);
    }
}
