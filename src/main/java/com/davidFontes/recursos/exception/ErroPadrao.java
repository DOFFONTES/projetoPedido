package com.davidFontes.recursos.exception;

import java.io.Serializable;

public class ErroPadrao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String msg;
	private Long tempo;
	
	public ErroPadrao(Integer status, String msg, Long tempo) {
		this.status = status;
		this.msg = msg;
		this.tempo = tempo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getTempo() {
		return tempo;
	}

	public void setTempo(Long tempo) {
		this.tempo = tempo;
	}
	
}
