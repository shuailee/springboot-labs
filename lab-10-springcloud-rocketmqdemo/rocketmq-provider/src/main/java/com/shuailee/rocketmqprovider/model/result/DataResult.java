package com.shuailee.rocketmqprovider.model.result;
import org.springframework.util.StringUtils;


public class DataResult<T> extends Result<T> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2751613606306765518L;

	protected DataResult() {
		super.setMsg("执行成功");
	}

	protected DataResult(String message) {
		super.setCode(-1);
		if (StringUtils.isEmpty(message)) {
			super.setMsg("执行失败");
		} else {
			super.setMsg(message);
		}
	}

	protected DataResult(int status, String message) {
		super.setCode(status);
		if (StringUtils.isEmpty(message)) {
			super.setMsg("执行失败");
		} else {
			super.setMsg(message);
		}
	}

	protected DataResult(T result) {
		super.setMsg("执行成功");
		super.setData(result);
	}

	protected DataResult(T result, int status, String message) {
		super.setCode(status);
		if (StringUtils.isEmpty(message)) {
			super.setMsg("执行失败");
		} else {
			super.setMsg(message);
		}
		super.setData(result);
	}

	public static <T> DataResult<T> fail(T result, int status, String message) {
		return new DataResult<T>(result, status, message);
	}

	public static <T> DataResult<T> fail(String message) {
		return new DataResult<T>(message);
	}

	public static <T> DataResult<T> fail(int status, String message) {
		return new DataResult<T>(status, message);
	}

	public static <T> DataResult<T> ok(T result) {
		return new DataResult<T>(result);
	}

	public static <T> DataResult<T> ok() {
		return new DataResult<T>();
	}

	public static <T> Result<T> toResult(T result) {
		return new Result<T>(result);
	}

}
