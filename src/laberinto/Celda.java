/**
 * Introducción al Java 2D - Programación Interactiva 2017-I
 * Profesora Paola J. Rodríguez C.
 */
package laberinto;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * Clase que representa cada celda que conforma el laberinto
 * -Debe saber graficarse según las condiciones del juego
 * -Debe guardar información de su estado:
 * 	-muros activos
 *  -Si contiene o no la bola verda o la bola roja.
 *
 * @author paolajr-EISC
 */
public class Celda extends JPanel{
	
	/**  tamaño de la celda. */
	public static final int SIZE=50;
	
	/**  muros o lados de la celda. */
	public static final int TOP=0, RIGHT=1, BOTTOM=2,LEFT=3;
	
	
	/**  Fila y columna en que se ubica la celda. */
	private int row, col;
	
	private boolean actual, llegada;
	
	/** Estado de los muros o lado.  
	 * true si son Visibles
	 * false si son Invisibles
	 */
	private boolean[] muro = {true,true,true,true};
	
	/**
	 * Constructor de la Celda.
	 *
	 * @param row la fila en que se ubicó la celda
	 * @param col La columna en que se ubicó la celda
	 */
	public Celda(int row, int col, int ladoCeldas){
		this.row=row;
		this.col=col;
		
		if(row == 0 && col == 0){
			actual = true;
		}
		else{
			actual = false;
		}
		
		if(row == ladoCeldas && col == ladoCeldas){
			llegada = true;
		}
		else{
			llegada = false;
		}
	}
	
	/**
	 * Me indica si el miro es visible o no.
	 *
	 * @param lado muro a analizar
	 * @return true, Si el muro está visible
	 */
	public boolean hayMuro(int lado){
		return muro[lado];
	}
	
	
	/**
	 * Devuelve la fila en que está ubicada la celda.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Devuelve la columna en que está ubicada la celda.
	 *
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * Método que se encarga del pintado de la celda
	 */
	public void paintComponent(Graphics g){
		
		//dibujar cuadro
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SIZE, SIZE);

		//dibujar muro
		//solo dibuja el muro TOP y LEFT si estos son visibles
		g.setColor(Color.BLACK);

		if(muro[TOP]){
			g.drawLine(0, 0, SIZE, 0);
		}
		if(muro[LEFT]){
			g.drawLine(0, 0, 0, SIZE);
		}
		
		
		//pintar bola
		
		if(actual){
			g.setColor(Color.GREEN);
			g.fillOval(3, 3, SIZE-6, SIZE-6);
		}	
		
		if(llegada){
			g.setColor(Color.RED);
			g.fillOval(3, 3, SIZE-6, SIZE-6);
		}		
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 * Se redifine el método para establecer el tamaño que 
	 * se prefiere para la celda.
	 */
	public Dimension getPreferredSize(){
		Dimension size = new Dimension(SIZE,SIZE);
		return size;
	}
	
	/**
	 * Revisa que todos los muros sean visibles.
	 *
	 * @return true, Si todos los muros son visibles
	 */
	public boolean isAllMuros(){
		return muro[TOP]&&muro[RIGHT]&&muro[BOTTOM]&&muro[LEFT];
	}
	
	
	/**
	 * Abrir camino. Elimina muros entre celdas dependiendo cual sea su 
	 * vecindad
	 *
	 * @param vecina the vecina
	 */
	public void abrirCamino(Celda vecina){	
		//si es vecina superior
		if(vecina.getRow()<row){
			vecina.removeMuro(BOTTOM);
			removeMuro(TOP);
		}
		//si es vecina inferior
		if(vecina.getRow()>row){
			vecina.removeMuro(TOP);
			removeMuro(BOTTOM);
		}
		//si es vecina izquierda
		if(vecina.getCol()<col){
			vecina.removeMuro(RIGHT);
			removeMuro(LEFT);
		}
		//si es vecina derecha
		if(vecina.getCol()>col){
			vecina.removeMuro(LEFT);
			removeMuro(RIGHT);
		}
	}
	
	private void removeMuro(int lado){
		muro[lado]=false;
	}
	
	public boolean getActual(){
		return actual;
	}	
	
	public boolean getLlegada(){
		return llegada;
	}	
	
	public void setActual(boolean actual){
		this.actual = actual;
	}
	
}
