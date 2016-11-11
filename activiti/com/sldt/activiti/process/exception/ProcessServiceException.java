package com.sldt.activiti.process.exception;

/**
 * <p> Title:ProcessServiceException </p>
 * <p> Description: 工作流异常处理类,错误代码存放在表中</p>
 */
public class ProcessServiceException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProcessServiceException() {
		super();
	}

	public ProcessServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcessServiceException(String message) {
		super(message);
	}

	public ProcessServiceException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
