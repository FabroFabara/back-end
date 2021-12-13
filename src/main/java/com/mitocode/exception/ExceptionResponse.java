package com.mitocode.exception;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class ExceptionResponse {

	//@NotNull
	private LocalDateTime fecha;
	//@NotNull
	private String mensaje;
	//@NotNull
	private String detalles;

	public ExceptionResponse() {
	}

	public ExceptionResponse(LocalDateTime fecha, String mensaje, String detalles) {
		super();
		this.fecha = fecha;
		this.mensaje = mensaje;
		this.detalles = detalles;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

}
