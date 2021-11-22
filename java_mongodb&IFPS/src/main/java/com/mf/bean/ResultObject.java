package com.mf.bean;

/**
 * This class is used to customize the output of jonsn string
 * @param <T>
 */
public class ResultObject<T> {

    /**
     * Information code
     */
    private String code;
    /**
     * Information text
     */
    private String msg;
    /**
     * Included data
     */
    private T data;
    /**
     * Number of data
     */
    private Long count;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
