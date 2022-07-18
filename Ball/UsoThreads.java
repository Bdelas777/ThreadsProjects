package UsoThreads;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UsoThreads {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame marco=new MarcoRebote();
		
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		marco.setVisible(true);

	}
}
//Movimiento de la pelota-----------------------------------------------------------------------------------------

class PelotaHilos implements Runnable{//Paso 1
	
	public PelotaHilos(Pelota unaPelota, Component uncomponente) {
		
		pelota = unaPelota;
		
		componente = uncomponente;
		
	}
	// Paso 2
	public void run() {
		//for (int i=1; i<=3000; i++){
		
		System.out.println("El estado del hilo al comenzar es: " + Thread.currentThread().isInterrupted());
		
		//while(!Thread.interrupted())	{
		while(!Thread.currentThread().isInterrupted())	{
			
			pelota.mueve_pelota(componente.getBounds());
			
			componente.paint(componente.getGraphics());
			
			/*try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Hilo bloqueado");
				//System.exit(0);
			}*/
		}
		
		System.out.println("El estado del hilo al acabar es: " + Thread.currentThread().isInterrupted());

	}
	private Pelota pelota;
	
	private Component componente;
}
	
class Pelota{
	
	// Mueve la pelota invirtiendo posicin si choca con limites
	
	public void mueve_pelota(Rectangle2D limites){
		
		x+=dx;
		
		y+=dy;
		
		if(x<limites.getMinX()){
			
			x=limites.getMinX();
			
			dx=-dx;
		}
		
		if(x + TAMX>=limites.getMaxX()){
			
			x=limites.getMaxX() - TAMX;
			
			dx=-dx;
		}
		
		if(y<limites.getMinY()){
			
			y=limites.getMinY();
			
			dy=-dy;
		}
		
		if(y + TAMY>=limites.getMaxY()){
			
			y=limites.getMaxY()-TAMY;
			
			dy=-dy;
			
		}
		
	}
	
	//Forma de la pelota en su posici�n inicial
	
	public Ellipse2D getShape(){
		
		return new Ellipse2D.Double(x,y,TAMX,TAMY);
		
	}	
	
	private static final int TAMX=15;
	
	private static final int TAMY=15;
	
	private double x=0;
	
	private double y=0;
	
	private double dx=1;
	
	private double dy=1;
	
	
}

//Lamina que dibuja las pelotas----------------------------------------------------------------------


class LaminaPelota extends JPanel{
	
	//Añadimos pelota a lamina
	
	public void add(Pelota b){
		
		pelotas.add(b);
	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		for(Pelota b: pelotas){
			
			g2.fill(b.getShape());
		}
		
	}
	
	private ArrayList<Pelota> pelotas=new ArrayList<Pelota>();
}


//Marco con l�mina y botones------------------------------------------------------------------------------

class MarcoRebote extends JFrame{
	
	public MarcoRebote(){
		
		setBounds(400,200,600,350);
		
		setTitle ("Rebotes");
		
		lamina=new LaminaPelota();
		
		add(lamina, BorderLayout.CENTER);
		
		JPanel laminaBotones=new JPanel();
		
		//-----------------------Boton 1-----------
		inicia1 = new JButton("Hilo 1");
		
		inicia1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evento) {
				
				comienza_el_juego(evento);
				
			}
			
		});
		
		laminaBotones.add(inicia1);
		
		//-----------------------Boton 2-----------
				inicia2 = new JButton("Hilo 2");
				
				inicia2.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent evento) {
						
						comienza_el_juego(evento);
						
					}
					
				});
				
				laminaBotones.add(inicia2);
				
				//-----------------------Boton 3-----------
				inicia3 = new JButton("Hilo 3");
				
				inicia3.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent evento) {
						
						comienza_el_juego(evento);
						
					}
					
				});
				
				laminaBotones.add(inicia3);
				
				//-----------------------Stop 1-----------
				stop1 = new JButton("Stop 1");
				stop1.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent evento) {
						
						detener(evento);
						
					}
					
				});
				
				laminaBotones.add(stop1);
				
				//-----------------------Stop 2-----------
				stop2 = new JButton("Stop 2");
				stop2.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent evento) {
						
						detener(evento);
						
					}
					
				});
				
				laminaBotones.add(stop2);
				
				//-----------------------Stop 3-----------
				stop3 = new JButton("Stop 3");
				stop3.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent evento) {
						
						detener(evento);
						
					}
					
				});
				
				laminaBotones.add(stop3);
		
		add(laminaBotones, BorderLayout.SOUTH);
	}
	
	
	//Ponemos botones
	
	public void ponerBoton(Container c, String titulo, ActionListener oyente){
		
		JButton boton=new JButton(titulo);
		
		c.add(boton);
		
		boton.addActionListener(oyente);
		
	}
	
	//Añade pelota y la bota 1000 veces
	
	public void comienza_el_juego (ActionEvent e){
		
					
			Pelota pelota=new Pelota();
			
			lamina.add(pelota);
			//Paso 3 
			
			Runnable run = new PelotaHilos(pelota,lamina);
			
			if(e.getSource().equals(inicia1)) {
				//Paso 4
				t1 = new Thread(run);
				// Paso 5
				t1.start();
			} else if(e.getSource().equals(inicia2)) {
				//Paso 4
				t2 = new Thread(run);
				// Paso 5
				t2.start();
			}else if(e.getSource().equals(inicia3)) {
				//Paso 4
				t3 = new Thread(run);
				// Paso 5
				t3.start();
			}
				
	}	
	
	public void detener(ActionEvent e) {
		//Metodo obsoleto no lo uses
		//t.stop();
		if(e.getSource().equals(stop1)) {
			
			t1.interrupt();
			
		} else if(e.getSource().equals(stop2)) {
			
			t2.interrupt();
			
		}else if(e.getSource().equals(stop3)) {
			
			t3.interrupt();
			
		}
		
		
	}
	
	Thread t1,t2,t3;
	
	JButton inicia1,inicia2,inicia3,stop1,stop2,stop3;
	
	private LaminaPelota lamina;
	
	
}
/*
 * Pasos para hacer hilos:
 * 1- Crear una clase que implemente la interfaz Runnable(metodo run())
 * 2- Escribir el codigo de la tarea dentro del metodo run
 * 3- Instanciar la clase creada y almacenarla instancia en variable de tipo Runnable
 * 4- Crear una instancia de la clase Thread pasando como parametro al constructor
 * de Thread el objeto Runnable anterior
 * 5- Poner en marcha el hilo de ejecucion con el metodo start() de la clase Thread
 * 
 * Interrupcion de Thread:
 * void interrupt() Este metodo solicita la interrupcion de un hilo
 * boolean isinterrupted() hilo en concreto y el otro a cualquiera
 * static boolean interrupted() Ambos nos devuelve true si esta interrumpido y false sino
 * esta interrupido 
 * stop () detiene la ejecucion de un hilo no se aconseja 
 * 
 * Metodo sleep () se toma con milis o milis y nanos
 */

