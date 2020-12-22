package main;

public class Config{
	public static final boolean WRITE_DEBUG_INFO_TO_CONSOLE = false;
	
	public static final boolean PROCESS_MODS = true;
	public static final boolean PROCESS_PRIMES = true;
	public static final boolean PROCESS_RELICS = true && PROCESS_PRIMES; //We can't process the relics without processing the primes
}