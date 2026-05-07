package Taller;

import java.util.ArrayList;

public class AltoMando {
	// N°AltoMando;Nombre;Pokemons....
	private int numAltoMando;
	private String nombre;
	private ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();

	public AltoMando(int numAltoMando, String nombre) {
		this.numAltoMando = numAltoMando;
		this.nombre = nombre;
	}

	public void añadirPokemon(Pokemon poke) {
		pokemons.add(poke);
	}

	public String toString() {
		return "AltoMando [numAltoMando=" + numAltoMando + ", nombre=" + nombre + ", pokemons=" + pokemons + "]";
	}

}
