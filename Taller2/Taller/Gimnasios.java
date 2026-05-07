package Taller;

import java.util.ArrayList;

public class Gimnasios {
	// N°Gimnasio;Lider;Estado;cantPokemons;Pokemons....
	private int numGym;
	private String lider;
	private boolean derrotado;
	private int cantPokemons;
	private ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();

	public Gimnasios(int numGym, String lider, int cantPokemons) {
		this.numGym = numGym;
		this.lider = lider;
		this.derrotado = false;
		this.cantPokemons = cantPokemons;
	}

	public void derrotado() {
		this.derrotado = true;
	}

	public boolean isDerrotado() {
		return derrotado;
	}

	public void añadirPokemon(Pokemon poke) {
		pokemons.add(poke);
	}

	public String getLider() {
		return lider;
	}

}
