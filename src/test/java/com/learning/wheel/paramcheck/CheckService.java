package com.learning.wheel.paramcheck;

/**
 * @author mazhenjie
 * @since 2019/5/8
 */
public class CheckService {

    /**
     * 测试校验参数
     *
     * @param paramTestDTO
     * @return
     */
    public ValidResult checkParam(ParamTestDTO paramTestDTO) {
        return ValidationUtil.validate(paramTestDTO);
    }
}
