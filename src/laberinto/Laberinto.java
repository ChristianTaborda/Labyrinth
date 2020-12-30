/**
 * Introducción al Java 2D - Programación Interactiva 2017-I
 * Profesora Paola J. Rodríguez C.
 */
package laberinto;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * Ventana que gestiona el Laberinto (matriz de Celdas)
 * Responsable de:
 * -Visualizar el Laberinto
 * -Visualizar el movimiento de la bola verde
 * -Dar mensajes al usuario.
 * 
 * @author paolajr-EISC
 *
 */
public class Laberinto extends JFrame{
	
	/** Número de filas y columnas que tendrá la matriz de celdas
	 * define el tamaño del laberinto */
	private static final int ROWS=5, COLS=5;
	
	/** Matriz de celdas. */
	private Celda[][] celdas = new Celda[ROWS][COLS];
	
	/** Contenedor en el que se asignará la matriz de Celdas. 
	 * este contenedor es el que se adiciona la ventana*/
	private JPanel malla = new JPanel();
	
	private int fila, columna;
	
	public void buscarActual(){
		for(int x=0; x<ROWS; x++){
			for(int y=0; y<COLS; y++){
				if(celdas[x][y].getActual()){
					fila = x;
					columna = y;
				}
			}
		}
	}	
	
	/*public void buscarFinal(){
		for(int x=0; x<ROWS; x++){
			for(int y=0; y<COLS; y++){
				if(celdas[x][y].getLlegada()){
					fila = x;
					columna = y;
				}
			}
		}
	}	*/
	
	/**
	 * Constructor de la GUI.
	 */
	public Laberinto(){
		
		initGUI();
		EscuchaTeclado escucha = new EscuchaTeclado();
		addKeyListener(escucha);
		//configuracion estándar para toda ventana
		setTitle("Laberinto");
		setResizable(false);
		pack();    
		setLocationRelativeTo(null);    
		setVisible(true);
	}
	
	/**
	 * Método para establecer las condiciones de inicio de la ventana
	 */
	public void initGUI(){
		initLaberinto();
		add(malla,BorderLayout.CENTER);
	}
	
	/**
	 * Construye el laberinto para cada juego .
	 */
	public void initLaberinto(){

		malla.setLayout(new GridLayout(ROWS,COLS));
		
		//se crea la matriz de celdas
		for(int i=0;i<ROWS;i++){
			for(int j=0;j<COLS;j++){
				celdas[i][j]=new Celda(i,j,(ROWS-1));
				malla.add(celdas[i][j]);
			}
		}
		
		generarLaberinto();
		
		buscarActual();
		//buscarFinal();

		
	}
	
	/**
	 * Construye aleatoriamente un laberinto recorriendo aleatoriamete la matriz de
	 * celdas
	 */
	private void generarLaberinto(){
		
		ArrayList<Celda> celdasAVisitar = new ArrayList<Celda>();
		
		int totalCeldas=ROWS*COLS;
		int celdasVisitadas=1;
		
		//seleccionar una celda aleatoriamente
		Random aleatorio = new Random();
		int fila = aleatorio.nextInt(ROWS);
		int columna = aleatorio.nextInt(COLS);
		
		while(celdasVisitadas<totalCeldas){
		
			ArrayList<Celda> vecinasDisponibles = new ArrayList<Celda>();

			//buscar la vecinas disponibles y almacenarlas
			//vecina de arriba
			if(isAvailable(fila-1,columna)){
				vecinasDisponibles.add(celdas[fila-1][columna]);
			}
			//vecina de abajo
			if(isAvailable(fila+1,columna)){
				vecinasDisponibles.add(celdas[fila+1][columna]);
			}
			//vecina de la izquierda
			if(isAvailable(fila,columna-1)){
				vecinasDisponibles.add(celdas[fila][columna-1]);
			}
			//vecina de la derecha
			if(isAvailable(fila,columna+1)){
				vecinasDisponibles.add(celdas[fila][columna+1]);
			}

			//Si hay vecinas disponibles 		
			if(vecinasDisponibles.size()>0){
				//si hay mas de 1 vecina disponible guardar la celda actual como futura celda a visitar
				if(vecinasDisponibles.size()>1){
					celdasAVisitar.add(celdas[fila][columna]);
				}
				//abrir camino entre la celda actual y una de las vecinas disponibles
				//hacer a la celda vecina la actual y aumentar el contador de celdas visitadas
				int index = aleatorio.nextInt(vecinasDisponibles.size());
				Celda vecina = vecinasDisponibles.get(index);
				celdas[fila][columna].abrirCamino(vecina);
				fila=vecina.getRow();
				columna=vecina.getCol();
				celdasVisitadas++;
			}
			else{
				//Tomar una celda de las que se guardaron para futura visita y hacerla la celda actual
				if(celdasAVisitar.size()>0){
					Celda actual = celdasAVisitar.remove(0);
					fila=actual.getRow();
					columna=actual.getCol();
				}
		}//fin while
	}
		
		
	}
	
	private boolean isAvailable(int fila, int columna){
	  //valida si la fila y columna son válidas y 
	  //si la celda ubicada en esa posición tiene todos sus muros visibles
	  return fila>=0 && fila<ROWS && columna>=0 && columna<COLS && celdas[fila][columna].isAllMuros(); 
	}
	
	public void mover(int lado){
		switch(lado){
			case Celda.TOP:
				if(!celdas[fila][columna].hayMuro(Celda.TOP) && fila > 0){
					fila--;
				}
				break;
			case Celda.RIGHT:
				if(!celdas[fila][columna].hayMuro(Celda.RIGHT) && columna < COLS){
					columna++;
				}
				break;
			case Celda.BOTTOM:
				if(!celdas[fila][columna].hayMuro(Celda.BOTTOM) && fila < ROWS){
					fila++;
				}
				break;
			case Celda.LEFT:
				if(!celdas[fila][columna].hayMuro(Celda.LEFT) && columna > 0){
					columna--;
				}
				break;
		}
	}
	
	private class EscuchaTeclado extends KeyAdapter{
		public void keyPressed(KeyEvent T){
			int tecla = T.getKeyCode();
			switch(tecla){
				case KeyEvent.VK_UP:
					celdas[fila][columna].setActual(false);
					mover(Celda.TOP);	
					celdas[fila][columna].setActual(true);
					break;
				case KeyEvent.VK_DOWN:
					celdas[fila][columna].setActual(false);
					mover(Celda.BOTTOM);	
					celdas[fila][columna].setActual(true);
					break;
				case KeyEvent.VK_LEFT:
					celdas[fila][columna].setActual(false);
					mover(Celda.LEFT);	
					celdas[fila][columna].setActual(true);
					break;
				case KeyEvent.VK_RIGHT:
					celdas[fila][columna].setActual(false);
					mover(Celda.RIGHT);	
					celdas[fila][columna].setActual(true);
					break;
			}
			repaint();
		}		
	}
	

}//Fin Laberinto
