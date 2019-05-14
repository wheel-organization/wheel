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
        return ValidationUtil.fastFailValidate(paramTestDTO);
    }

    /**
     * 测试校验参数(全部校验)
     *
     * @param paramTestDTO
     * @return
     */
    public ValidResult checkAllParam(ParamTestDTO paramTestDTO) {
        return ValidationUtil.allCheckValidate(paramTestDTO);
    }

    public void checkNormal(ParamTestDTO paramTestDTO) {
        if (paramTestDTO == null) {
            return;
        }
        if (!paramTestDTO.getTestFalse()) {
            return;
        }
        if (!paramTestDTO.getTestTrue()) {
            return;
        }
        if (paramTestDTO.getTestNonNull() == null) {
            return;
        }
        if (paramTestDTO.getTestSize().size() < 1 || paramTestDTO.getTestSize().size() > 10) {
            return;
        }
    }
}
