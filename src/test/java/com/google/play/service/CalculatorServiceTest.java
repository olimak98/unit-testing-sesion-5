package com.google.play.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.regex.Matcher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


public class CalculatorServiceTest {
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void sumarDosNumeros() {
		
		//Arrange
		double numero1 	 = 10.0;
		double numero2 	 = 5.0; 	
		double resultado = 0.0;
		double esperado  = 15.0;
		
		//Act
		CalculadoraService calculadora = new CalculadoraService();
		
		resultado = calculadora.suma(numero1,numero2);
		
		//Assert
		Assert.assertEquals(esperado, resultado,0.1);
		
	}
	
	@Test
	public void sumarDosNumerosMatcher() {
		CalculadoraService calculadora = Mockito.mock(CalculadoraService.class);
		
		Mockito.when(calculadora.suma(Mockito.eq(9.0), Mockito.anyDouble()))
		.thenReturn(20.0);
		
		System.out.println(calculadora.suma(10.0, 7.0));
	}

	
	@Test
	public void sumarDosNumerosMatcher2() {
		//Arrange
		CalculadoraService calculadora = Mockito.mock(CalculadoraService.class);
		ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
		
		Mockito.when(calculadora.suma(captor.capture(),captor.capture()))
		.thenReturn(20.0);
		
		//act
		double suma =  calculadora.suma(5.0, 1.0);
		
		//Assert
		Assert.assertEquals(20.0,suma,0.1);
		
		System.out.println(captor.getAllValues());
	}
	
	
	@Mock
	CalculadoraService calculadoraServiceMock; 
	
	@Spy
	CalculadoraService calculadoraServiceSpy;
	
	@Test
	public void sumarDosNumerosMockSpy() {
		
		when(calculadoraServiceMock.suma(2.0, 2.0)).thenReturn(4.0);
		when(calculadoraServiceSpy.suma(2.0, 2.0)).thenReturn(4.0);
		
		
		assertEquals(4.0, calculadoraServiceMock.suma(2.0, 2.0),0.1);
		assertEquals(4.0, calculadoraServiceSpy.suma(2.0, 2.0),0.1);
		
		assertEquals(0.0, calculadoraServiceMock.suma(3.0, 2.0),0.1);
		assertEquals(5.0, calculadoraServiceSpy.suma(3.0, 2.0),0.1);
		
		when(calculadoraServiceMock.suma(10.0, 2.0)).thenCallRealMethod();
		
		
		System.out.println(calculadoraServiceMock.suma(10.0, 2.0));
		
	}
	
	
	
	
}
